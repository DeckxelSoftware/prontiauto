package com.ec.prontiauto;

import com.ec.prontiauto.enums.EnumConsulta;
import com.ec.prontiauto.facturacion.dao.FacturaResponseDao;
import com.ec.prontiauto.facturacion.dao.GenerarArchivoRequestDao;
import com.ec.prontiauto.facturacion.entidad.Factura;
import com.ec.prontiauto.facturacion.respository.FacturaRepositoryImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@EnableScheduling
@Configuration
@Transactional
public class SendingPendingInvoicesScheduler {

    private final FacturaRepositoryImpl invoiceRepository;
    private final RestTemplate restTemplate;
    private final String invoiceAuthorizationService;
    private final String invoiceForwardingService;
    private final String invoiceGenerationService;
    private final Map<String,String> authorizationInfo;

    private final Logger logger = LogManager.getLogger(SendingPendingInvoicesScheduler.class);

    public SendingPendingInvoicesScheduler(FacturaRepositoryImpl invoiceRepository,
                                           RestTemplate restTemplate,
                                           @Value("${service.invoiceAuthorization.url}") String invoiceAuthorizationService,
                                           @Value("${service.resendInvoice.url}") String invoiceForwardingService,
                                           @Value("${service.generateInvoiceFile.url}") String invoiceGenerationService,
                                           @Value("#{${service.invoiceAuthorization.authorizationInfo}}") Map<String,String> authorizationInfo){
        this.invoiceRepository = invoiceRepository;
        this.restTemplate = restTemplate;
        this.invoiceAuthorizationService = invoiceAuthorizationService;
        this.invoiceForwardingService = invoiceForwardingService;
        this.invoiceGenerationService = invoiceGenerationService;
        this.authorizationInfo = authorizationInfo;
    }


    @Scheduled(cron = "${spring.task.scheduling.invoice-manager.cron-expression}")
    public void sendInvoiceToApprove(){

        logger.info("====INIT sendInvoiceToApprove====");

        Map<String, Object> params = buildQueryResquest();

        List<ResponseEntity<JsonObject>> invoicesSentForApproval = getPendingInvoices(params)
                .stream()
                .map(invoice -> sendInvoiceToApprove(invoice, false))
                .filter(isASuccessfulResponse())
                .collect(Collectors.toList());


        List<Factura> invoicesUpdated = invoicesSentForApproval
                .stream()
                .map(HttpEntity::getBody)
                .filter(Objects::nonNull)
                .map(this::updateInvoice)
                .collect(Collectors.toList());


        logger.debug("Invoices updated {} ", invoicesUpdated.stream().map(Factura::getId));

        logger.info("generating the files...");

        invoicesUpdated.stream()
                .map(this::generateInvoiceFile)
                .map(HttpEntity::getBody)
                .forEach(this::saveInvoiceFile);



        logger.info("====ENDING sendInvoiceToApprove====");

    }

    private static Map<String, Object> buildQueryResquest() {
        Map<String, Object> params = new HashMap<>();
        params.put("busqueda", "PENDIENTE");
        params.put("desde", "");
        params.put("hasta", "");
        params.put("itNumeroDocumento", "");
        return params;
    }

    private static Predicate<ResponseEntity<JsonObject>> isASuccessfulResponse() {
        return response -> Objects.equals(response.getStatusCode(), HttpStatus.OK);
    }

    private Factura updateInvoice(JsonObject response) {
        int id = response.get("id").getAsInt();
        JsonObject jsonResponse = new Gson().fromJson(response.get("approvalResponse").getAsJsonObject(), JsonObject.class);
        Optional<Factura> invoiceOpt = this.invoiceRepository.findById(id);

        if(invoiceOpt.isPresent()){
            Factura invoice = invoiceOpt.get();

            String authorizationKey = jsonResponse.get("claveAutorizacion").getAsString();
            String newStatus = jsonResponse.get("estadoSri").getAsString();

            if(!"AUTORIZADO".equalsIgnoreCase(newStatus)){
                invoice.setMensajeError(jsonResponse.get("detalleError").getAsString());
            }else {
                OffsetDateTime fechaAutorizacion = OffsetDateTime.parse(jsonResponse.get("fechaAtorizacion").getAsString());
                invoice.setFechaAutorizacion(Timestamp.from(fechaAutorizacion.toInstant()));
                invoice.setClaveAcceso(authorizationKey);
            }

            invoice.setEstado(newStatus.toUpperCase());


            return this.invoiceRepository.updateAndReturnValue(invoice);
        }

        return null;
    }

    private void saveInvoiceFile(JsonObject response){
        int id = response.get("id").getAsInt();
        Optional<Factura> invoiceOpt = this.invoiceRepository.findById(id);
        if(invoiceOpt.isPresent()){
            Factura invoice = invoiceOpt.get();
            invoice.setUrlArchivo(response.get("urlReporte").getAsString());
            this.invoiceRepository.update(invoice);
        }
    }

    @NotNull
    private ResponseEntity<JsonObject> sendInvoiceToApprove(FacturaResponseDao pendingInvoice, boolean isReturned) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JsonObject jsonRequest = new Gson().fromJson(pendingInvoice.getJsonFactura(), JsonObject.class);
        jsonRequest.add("infoAutoriza", new Gson().toJsonTree(authorizationInfo));
        HttpEntity<Object> request = new HttpEntity<>(jsonRequest.toString(), headers);
        ResponseEntity<String> httpResponse = this.restTemplate.postForEntity(!isReturned ? invoiceAuthorizationService : invoiceForwardingService, request, String.class);
        JsonObject responseBody = new Gson().fromJson(httpResponse.getBody(), JsonObject.class);
        String newStatus = responseBody.get("estadoSri").getAsString();

        if("DEVUELTA".equalsIgnoreCase(newStatus) && "CLAVE ACCESO REGISTRADA".equalsIgnoreCase(responseBody.get("detalleError").getAsString())){
            return sendInvoiceToApprove(pendingInvoice, true);
        }

        Map<String,Object> bodyResponseEnrichment = new HashMap<>();
        bodyResponseEnrichment.put("id", pendingInvoice.getId());
        bodyResponseEnrichment.put("approvalResponse", new Gson().fromJson(httpResponse.getBody(), JsonObject.class));

        return ResponseEntity.status(httpResponse.getStatusCode())
                .body(new Gson().fromJson(bodyResponseEnrichment.toString(), JsonObject.class));
    }

    private ResponseEntity<JsonObject> generateInvoiceFile(Factura entity) {
        Gson jsonParser = new Gson();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JsonObject jsonRequest = buildFileGenerationRequest(entity.getJsonFactura(), jsonParser);
        HttpEntity<Object> request = new HttpEntity<>(jsonRequest.toString(), headers);
        ResponseEntity<String> httpResponse = this.restTemplate.postForEntity(invoiceGenerationService, request, String.class);
        JsonObject invoiceFileResponse = new Gson().fromJson(httpResponse.getBody(), JsonObject.class);
        Map<String,Object> bodyResponseEnrichment = new HashMap<>();
        bodyResponseEnrichment.put("id", entity.getId());
        bodyResponseEnrichment.put("urlReporte", invoiceFileResponse.get("urlReporte"));
        return ResponseEntity.status(httpResponse.getStatusCode())
                .body(new Gson().fromJson(bodyResponseEnrichment.toString(), JsonObject.class));
    }

    private JsonObject buildFileGenerationRequest(String jsonInvoice, Gson jsonParser) {
        JsonObject invoiceData = jsonParser.fromJson(jsonInvoice, JsonObject.class);
        GenerarArchivoRequestDao generateFileRequest = new GenerarArchivoRequestDao(invoiceData.get("establecimientoEmpresa").getAsString(),
                invoiceData.get("puntoEmisionEmpresa").getAsString(), invoiceData.get("facNumero").getAsInt() , invoiceData.get("rucEmpresa").getAsString(),
                authorizationInfo.getOrDefault("rutaLogo",""), authorizationInfo.getOrDefault("rutaArchivo",""),
                invoiceData.get("identificacionComprador").getAsString());

        JsonObject jsonRequest = jsonParser.fromJson(jsonParser.toJson(generateFileRequest), JsonObject.class);
        return jsonRequest;
    }

    @NotNull
    private List<FacturaResponseDao> getPendingInvoices(Map<String, Object> params) {
        List<Object> response = this.invoiceRepository.findBySearchAndFilter(params,
                PageRequest.of(EnumConsulta.SKIP.getValor(),
                        EnumConsulta.TAKE.getValor(),
                        Sort.by("if_fecha_emision").ascending())
        );

        return new ObjectMapper().convertValue(response.get(0), new TypeReference<List<FacturaResponseDao>>() {});
    }
}
