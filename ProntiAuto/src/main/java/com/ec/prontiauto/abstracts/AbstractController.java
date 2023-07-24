package com.ec.prontiauto.abstracts;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ec.prontiauto.enums.EnumConsulta;
import com.ec.prontiauto.exception.ApiRequestException;
import com.ec.prontiauto.helper.ValidateReqMapper;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Transactional
public class AbstractController<Entity, DaoRequest, DaoResponse, IdType> {

    @PersistenceContext
    private EntityManager em;

    public DaoResponse devolverRespuestaDao(Entity entity) {
        return null;
    }

    public Entity devolverRespuestaUpdate(Entity entity, IdType id) {
        return entity;
    }

    public Entity setDaoRequestToEntity(DaoRequest dao) {
        return null;
    }

    public DaoRequest deleteIdOnSave(DaoRequest dao) {
        return dao;
    }

    public DaoRequest validationRequest(DaoRequest dao) {
        return dao;
    }
    public DaoRequest validationRequestUpdate(DaoRequest dao, IdType id) {
        return dao;
    }

    public Entity validationRequestEntity(Entity entity) {
        return entity;
    }

    public Entity validationRequestEntityOnlyPost(Entity entity) {
        return entity;
    }

    public Entity validationRequestEntityOnlyUpdate(Entity entity) {
        return entity;
    }

    public Boolean ValidateFields(ValidateReqMapper validations) {
        return true;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody DaoRequest dao) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        try {

            DaoRequest daoMap = deleteIdOnSave(dao);
            DaoRequest daoVal = validationRequest(daoMap);

            httpHeaders.add("STATUS", "200");
            Entity newEntity = setDaoRequestToEntity(daoVal);
            Entity entityPost = validationRequestEntityOnlyPost(newEntity);
            Entity entity = validationRequestEntity(entityPost);

            em.persist(entity);
            Object response = devolverRespuestaDao(entity);

            return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("-------------------\n" + e.getMessage());
            throw new ApiRequestException(e.getMessage());
        }
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody DaoRequest dao, @PathVariable("id") IdType id) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        try {
            httpHeaders.add("STATUS", "200");
            DaoRequest daoVal = validationRequestUpdate(dao, id);
            Entity newEntity = setDaoRequestToEntity(daoVal);
            Entity entity = this.devolverRespuestaUpdate(newEntity, id);
            Entity entityUpdate = validationRequestEntityOnlyUpdate(entity);
            Entity entityVal = validationRequestEntity(entityUpdate);

            em.merge(entityVal);
            return new ResponseEntity<>(devolverRespuestaDao(
                    entityVal), httpHeaders,
                    HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("-------------------\n" + e.getMessage());
            throw new ApiRequestException(e.getMessage());
        }
    }

    public List<Object> findWithSkipAndTake(
            AbstractRepository<Entity, IdType> repository,
            Map<String, Object> params,
            Integer skip,
            Integer take,
            String sortField, Boolean sortAscending) {
        return repository
                .findBySearchAndFilter(params, this.getPageRequest(skip, take, sortField, sortAscending));
    }

    public PageRequest getPageRequest(Integer skip, Integer take, String sortField, Boolean sortAscending) {
        return PageRequest.of(skip == null ? EnumConsulta.SKIP.getValor() : skip,
                take == null ? EnumConsulta.TAKE.getValor() : take,
                sortAscending ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
    }
}
