package com.ec.prontiauto.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ec.prontiauto.abstracts.AbstractController;
import com.ec.prontiauto.abstracts.GenericMethods;
import com.ec.prontiauto.dao.RolPagoRequestDao;
import com.ec.prontiauto.dao.RolPagoResponseDao;
import com.ec.prontiauto.entidad.RolPago;
import com.ec.prontiauto.exception.ApiRequestException;
import com.ec.prontiauto.mapper.RolPagoMapper;
import com.ec.prontiauto.repositoryImpl.RolPagoRepositoryImpl;

import io.swagger.annotations.Api;

import com.ec.prontiauto.validations.RolPagoValidation;

@RestController
@RequestMapping("/api/rol-pago")
@Api(tags = "RolPago", description = "Gestion de RolPago")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class RolPagoController
        extends AbstractController<RolPago, RolPagoRequestDao, RolPagoResponseDao, Integer> {

    private GenericMethods genericMethods = new GenericMethods();
    @Autowired
    private RolPagoRepositoryImpl RolPagoRepositoryImpl;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public RolPagoResponseDao devolverRespuestaDao(RolPago entity) {
        return RolPagoMapper.setEntityToDaoResponse.apply(entity);
    }

    @Override
    @Transactional
    public RolPago devolverRespuestaUpdate(RolPago entity, Integer id) {
        RolPago antiguo = (RolPago) genericMethods.findById("RolPago",
                entityManager, id);
        if (antiguo != null) {
            antiguo = antiguo.setValoresDiferentes(antiguo, entity);
        }
        return antiguo;
    }

    @Override
    public RolPago setDaoRequestToEntity(RolPagoRequestDao dao) {
        return RolPagoMapper.setDaoRequestToEntity.apply(dao);
    }

    @Override
    public RolPago validationRequestEntityOnlyPost(RolPago entity) {

        RolPagoValidation rolPagoValidation = new RolPagoValidation(this.entityManager, entity, false);

        entity = rolPagoValidation.getRolPago();
        return entity;

    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> findBySearchAndUpdate(
            String busqueda,
            String sisHabilitado,
            Integer id,
            Integer skip,
            Integer take,
            String sortField,
            Boolean sortAscending) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("busqueda", busqueda == null ? "" : busqueda);
            params.put("sisHabilitado", sisHabilitado == null ? "" : sisHabilitado);
            params.put("id", id == null ? "" : id);

            String sortFieldDefault = "id";
            sortField = sortField == null ? sortFieldDefault : sortField;
            sortAscending = sortAscending == null ? true : sortAscending;

            List<Object> list = this.findWithSkipAndTake(
                    this.RolPagoRepositoryImpl,
                    params,
                    skip,
                    take,
                    sortField,
                    sortAscending);

            return new ResponseEntity<>(list, httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("-------------------\n" + e.getMessage());
            throw new ApiRequestException(e.getMessage());
        }
    }

}
