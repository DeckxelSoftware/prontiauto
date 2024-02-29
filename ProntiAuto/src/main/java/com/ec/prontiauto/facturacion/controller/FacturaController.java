package com.ec.prontiauto.facturacion.controller;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ec.prontiauto.enums.EnumConsulta;
import com.ec.prontiauto.exception.ApiRequestException;
import com.ec.prontiauto.facturacion.dao.FacturaRequestDao;
import com.ec.prontiauto.facturacion.dao.FacturaResponseDao;
import com.ec.prontiauto.facturacion.entidad.Factura;
import com.ec.prontiauto.facturacion.mapper.FacturaMapper;
import com.ec.prontiauto.facturacion.respository.FacturaRepositoryImpl;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/factura")
@Api(tags = "Factura", description = "Gestion de factura")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
@Transactional
public class FacturaController {

    @Autowired
    private FacturaRepositoryImpl facturaRepository;

    @PersistenceContext
    private EntityManager em;

    public FacturaResponseDao devolverRespuestaDao(Factura entity) {
        return FacturaMapper.setEntityToDaoResponse.apply(entity);
    }

    public Factura setDaoRequestToEntity(FacturaRequestDao dao) {
        return FacturaMapper.setDaoRequestToEntity.apply(dao);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody FacturaRequestDao dao) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        try {
            httpHeaders.add("STATUS", "200");
            Factura newEntity = setDaoRequestToEntity(dao);
            this.em.persist(newEntity);
            Object response = devolverRespuestaDao(newEntity);
            return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("-------------------\n" + e.getMessage());
            throw new ApiRequestException(e.getMessage());
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> findBySearchAndUpdate(String busqueda, Date desde, Date hasta,
            String itNumeroDocumento,
            Integer skip,
            Integer take,
            String sortField,
            Boolean sortAscending) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("busqueda", busqueda != null ? busqueda : "");
            params.put("desde", desde != null ? desde : "");
            params.put("hasta", hasta != null ? hasta : "");
            params.put("itNumeroDocumento", itNumeroDocumento != null ? itNumeroDocumento : "");

            String sortFieldDefault = "id";
            sortField = sortField == null ? sortFieldDefault : sortField;
            sortAscending = sortAscending == null ? true : sortAscending;

            List<Object> list = this.facturaRepository.findBySearchAndFilter(params,
                    PageRequest.of(EnumConsulta.SKIP.getValor(),
                            EnumConsulta.TAKE.getValor(),
                            Sort.by("if_fecha_emision").ascending()));

            return new ResponseEntity<>(list, httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("-------------------\n" + e.getMessage());
            throw new ApiRequestException(e.getMessage());
        }
    }

    public PageRequest getPageRequest(Integer skip, Integer take, String sortField, Boolean sortAscending) {
        return PageRequest.of(skip == null ? EnumConsulta.SKIP.getValor() : skip,
                take == null ? EnumConsulta.TAKE.getValor() : take,
                sortAscending ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
    }
}
