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
import com.ec.prontiauto.facturacion.dao.NotaCreditoRequestDao;
import com.ec.prontiauto.facturacion.dao.NotaCreditoResponseDao;
import com.ec.prontiauto.facturacion.entidad.NotaCredito;
import com.ec.prontiauto.facturacion.mapper.NotaCreditoMapper;
import com.ec.prontiauto.facturacion.respository.NotaCreditoRespositoryImpl;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/nota-credito")
@Api(tags = "Nota de credito", description = "Gestion de nota-credito")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
@Transactional
public class NotaCreditoController {

    @Autowired
    private NotaCreditoRespositoryImpl notaCreditoRepository;

    @PersistenceContext
    private EntityManager em;

    public NotaCreditoResponseDao devolverRespuestaDao(NotaCredito entity) {
        return NotaCreditoMapper.setEntityToDaoResponse.apply(entity);
    }

    public NotaCredito setDaoRequestToEntity(NotaCreditoRequestDao dao) {
        return NotaCreditoMapper.setDaoRequestToEntity.apply(dao);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody NotaCreditoRequestDao dao) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        try {
            httpHeaders.add("STATUS", "200");
            NotaCredito newEntity = setDaoRequestToEntity(dao);
            em.persist(newEntity);
            Object response = devolverRespuestaDao(newEntity);
            return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("-------------------\n" + e.getMessage());
            throw new ApiRequestException(e.getMessage());
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> findBySearchAndUpdate(String busqueda, Date desde, Date hasta,
            Integer skip,
            Integer take,
            String inNumDocModificado,
            String sortField,
            Boolean sortAscending) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("busqueda", busqueda != null ? busqueda : "");
            params.put("desde", desde != null ? desde : "");
            params.put("hasta", hasta != null ? hasta : "");
            params.put("inNumDocModificado", inNumDocModificado != null ? inNumDocModificado : "");
            String sortFieldDefault = "id";
            sortField = sortField == null ? sortFieldDefault : sortField;
            sortAscending = sortAscending == null ? true : sortAscending;

            List<Object> list = this.notaCreditoRepository.findBySearchAndFilter(params,
                    this.getPageRequest(skip, take, sortField, sortAscending));

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
