package com.ec.prontiauto.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.ec.prontiauto.validations.HistoricoRolValidation;
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
import com.ec.prontiauto.dao.HistoricoRolRequestDao;
import com.ec.prontiauto.dao.HistoricoRolResponseDao;
import com.ec.prontiauto.entidad.HistoricoRol;
import com.ec.prontiauto.exception.ApiRequestException;
import com.ec.prontiauto.mapper.HistoricoRolMapper;
import com.ec.prontiauto.repositoryImpl.HistoricoRolRepositoryImpl;

import io.swagger.annotations.Api;


@RestController
@RequestMapping("/api/historico-rol")
@Api(tags = "HistoricoRol", description = "Gestion de HistoricoRol")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class HistoricoRolController
        extends AbstractController<HistoricoRol, HistoricoRolRequestDao, HistoricoRolResponseDao, Integer> {

    private GenericMethods genericMethods = new GenericMethods();
    @Autowired
    private HistoricoRolRepositoryImpl historicoRolRepositoryImpl;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public HistoricoRolResponseDao devolverRespuestaDao(HistoricoRol entity) {
        return HistoricoRolMapper.setEntityToDaoResponse.apply(entity);
    }

    @Override
    @Transactional
    public HistoricoRol devolverRespuestaUpdate(HistoricoRol entity, Integer id) {
        HistoricoRol antiguo = (HistoricoRol) genericMethods.findById("HistoricoRol",
                entityManager, id);
        if (antiguo != null) {
            antiguo = antiguo.setValoresDiferentes(antiguo, entity);
        }
        return antiguo;
    }

    @Override
    public HistoricoRol setDaoRequestToEntity(HistoricoRolRequestDao dao) {
        return HistoricoRolMapper.setDaoRequestToEntity.apply(dao);
    }

    public HistoricoRol validationRequestEntityOnlyPost(HistoricoRol entity) {
        HistoricoRolValidation validation = new HistoricoRolValidation(entityManager, entity);
        return validation.getEntity();
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
                    this.historicoRolRepositoryImpl,
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
