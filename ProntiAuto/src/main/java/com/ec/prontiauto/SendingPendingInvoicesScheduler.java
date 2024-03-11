package com.ec.prontiauto;

import com.ec.prontiauto.enums.EnumConsulta;
import com.ec.prontiauto.facturacion.dao.FacturaResponseDao;
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
    private final Map<String,String> authorizationInfo;

    private final Logger logger = LogManager.getLogger(SendingPendingInvoicesScheduler.class);

    public SendingPendingInvoicesScheduler(FacturaRepositoryImpl invoiceRepository,
                                           RestTemplate restTemplate,
                                           @Value("${service.invoiceAuthorization.url}") String invoiceAuthorizationService,
                                           @Value("#{${service.invoiceAuthorization.authorizationInfo}}") Map<String,String> authorizationInfo){
        this.invoiceRepository = invoiceRepository;
        this.restTemplate = restTemplate;
        this.invoiceAuthorizationService = invoiceAuthorizationService;
        this.authorizationInfo = authorizationInfo;
    }


    @Scheduled(cron = "${spring.task.scheduling.invoice-manager.cron-expression}")
    public void sendInvoiceToApprove(){

        logger.info("====INIT sendInvoiceToApprove====");

        Map<String, Object> params = buildQueryResquest();

        List<ResponseEntity<JsonObject>> invoicesSentForApproval = getPendingInvoices(params)
                .stream()
                .map(this::sendInvoiceToApprove)
                .filter(isASuccessfulResponse())
                .collect(Collectors.toList());


        List<Factura> invoicesUpdated = invoicesSentForApproval
                .stream()
                .map(HttpEntity::getBody)
                .filter(Objects::nonNull)
                .map(this::updateInvoice)
                .collect(Collectors.toList());

        logger.debug("Invoices updated {} ", invoicesUpdated.stream().map(Factura::getId));

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

    @NotNull
    private ResponseEntity<JsonObject> sendInvoiceToApprove(FacturaResponseDao pendingInvoice) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JsonObject jsonRequest = new Gson().fromJson(pendingInvoice.getJsonFactura(), JsonObject.class);
        jsonRequest.add("infoAutoriza", new Gson().toJsonTree(authorizationInfo));
        HttpEntity<Object> request = new HttpEntity<>(jsonRequest.toString(), headers);
        ResponseEntity<String> httpResponse = this.restTemplate.postForEntity(invoiceAuthorizationService, request, String.class);

        Map<String,Object> bodyResponseEnrichment = new HashMap<>();
        bodyResponseEnrichment.put("id", pendingInvoice.getId());
        bodyResponseEnrichment.put("approvalResponse", new Gson().fromJson(httpResponse.getBody(), JsonObject.class));

        return ResponseEntity.status(httpResponse.getStatusCode())
                .body(new Gson().fromJson(bodyResponseEnrichment.toString(), JsonObject.class));
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
