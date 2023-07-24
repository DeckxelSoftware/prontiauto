
package com.ec.prontiauto.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.baomidou.mybatisplus.annotation.IdType;
import com.ec.prontiauto.exception.ApiRequestException;
import com.ec.prontiauto.validations.PagosDosValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ec.prontiauto.abstracts.AbstractController;
import com.ec.prontiauto.abstracts.GenericMethods;
import com.ec.prontiauto.dao.PagosDosRequestDao;
import com.ec.prontiauto.dao.PagosDosResponseDao;
import com.ec.prontiauto.entidad.PagosDos;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.mapper.PagosDosMapper;
import com.ec.prontiauto.repositoryImpl.PagosDosRepositoryImpl;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/pagos2")
@Api(tags = "Pagos Dos", description = "Gestion Pagos Dos")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class PagosDosController
        extends AbstractController<PagosDos, PagosDosRequestDao, PagosDosResponseDao, Integer> {

    private GenericMethods genericMethods = new GenericMethods();

    @Autowired
    private PagosDosRepositoryImpl PagosDosRepositoryImpl;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PagosDosResponseDao devolverRespuestaDao(PagosDos entity) {
        return PagosDosMapper.setEntityToDaoResponse.apply(entity);
    }

    @Override
    @Transactional
    public PagosDos devolverRespuestaUpdate(PagosDos entity, Integer id) {
        PagosDos antiguo = (PagosDos) genericMethods.findById("PagosDos", entityManager, id);
        if (antiguo != null) {
            antiguo = antiguo.setValoresDiferentes(antiguo, entity);
        }
        return antiguo;
    }

    @Override
    public PagosDos setDaoRequestToEntity(PagosDosRequestDao dao) {
        return PagosDosMapper.setDaoRequestToEntity.apply(dao);
    }

    @Override
    public PagosDosRequestDao deleteIdOnSave(PagosDosRequestDao dao) {
        dao.setId(null);
        return dao;
    }


    /*
    @RequestMapping(value = "/decimo-tercero-anual/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> createDecimoTerceroAnual(@RequestBody PagosDosRequestDao dao, @PathVariable("id") Integer id) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        try {
            httpHeaders.add("STATUS", "200");
            PagosDos newEntity = setDaoRequestToEntity(dao);
            PagosDos entityVal = this.devolverRespuestaUpdate(newEntity, id);

            PagosDosValidation val = new PagosDosValidation(entityManager, entityVal);
            PagosDos entity = val.calculoDecimoTerceroAnual();

            this.entityManager.merge(entity);
            return new ResponseEntity<>(devolverRespuestaDao(
                    entity), httpHeaders,
                    HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("-------------------\n" + e.getMessage());
            throw new ApiRequestException(e.getMessage());
        }
    }

    @RequestMapping(value = "/decimo-tercero-mensual/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> createDecimoTerceroMensual(@RequestBody PagosDosRequestDao dao, @PathVariable("id") Integer id) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        try {
            httpHeaders.add("STATUS", "200");
            PagosDos newEntity = setDaoRequestToEntity(dao);
            PagosDos entityVal = this.devolverRespuestaUpdate(newEntity, id);

            PagosDosValidation val = new PagosDosValidation(entityManager, entityVal);
            PagosDos entity = val.calculoDecimoTerceroMensual();

            this.entityManager.merge(entity);
            return new ResponseEntity<>(devolverRespuestaDao(
                    entity), httpHeaders,
                    HttpStatus.OK);

        } catch (Exception e) {
            System.out.println("-------------------\n" + e.getMessage());
            throw new ApiRequestException(e.getMessage());
        }
    }
*/

    @RequestMapping(value="/decimo-tercero-anual",method = RequestMethod.POST)
    public ResponseEntity<?> createDecimoTerceroAnual(@RequestBody PagosDosRequestDao dao) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        try {

            PagosDosRequestDao daoMap = deleteIdOnSave(dao);
            PagosDosRequestDao daoVal = validationRequest(daoMap);

            httpHeaders.add("STATUS", "200");
            PagosDos newEntity = setDaoRequestToEntity(daoVal);
            PagosDos entityPost = validationRequestEntityOnlyPost(newEntity);
            PagosDos entity = validationRequestEntity(entityPost);

            PagosDosValidation val = new PagosDosValidation(entityManager, entity);
            PagosDos decimoTerceroAnual = val.calculoDecimoTerceroAnual();
            entityManager.persist(decimoTerceroAnual);
            Object response = devolverRespuestaDao(decimoTerceroAnual);

            return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("-------------------\n" + e.getMessage());
            throw new ApiRequestException(e.getMessage());
        }
    }

    @RequestMapping(value="/decimo-tercero-mensual",method = RequestMethod.POST)
    public ResponseEntity<?> createDecimoTerceroMensual(@RequestBody PagosDosRequestDao dao) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        try {

            PagosDosRequestDao daoMap = deleteIdOnSave(dao);
            PagosDosRequestDao daoVal = validationRequest(daoMap);

            httpHeaders.add("STATUS", "200");
            PagosDos newEntity = setDaoRequestToEntity(daoVal);
            PagosDos entityPost = validationRequestEntityOnlyPost(newEntity);
            PagosDos entity = validationRequestEntity(entityPost);

            PagosDosValidation val = new PagosDosValidation(entityManager, entity);
            PagosDos decimoTerceroMensual = val.calculoDecimoTerceroMensual();

            entityManager.persist(decimoTerceroMensual);
            Object response = devolverRespuestaDao(decimoTerceroMensual);

            return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("-------------------\n" + e.getMessage());
            throw new ApiRequestException(e.getMessage());
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> findBySearchAndUpdate(
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

            params.put("sisHabilitado", sisHabilitado == null ? "" : sisHabilitado);
            params.put("id", id == null ? "" : id);

            String sortFieldDefault = "id";
            sortField = sortField == null ? sortFieldDefault : sortField;
            sortAscending = sortAscending == null ? true : sortAscending;

            List<Object> list = this.findWithSkipAndTake(
                    this.PagosDosRepositoryImpl,
                    params,
                    skip,
                    take,
                    sortField,
                    sortAscending);
            return new ResponseEntity<>(list, httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
            httpHeaders.add("STATUS", "400");
            return new ResponseEntity<ExceptionResponse>(new ExceptionResponse("400", e.getMessage()), httpHeaders,
                    HttpStatus.BAD_REQUEST);
        }
    }
}
