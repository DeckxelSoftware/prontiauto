
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
import org.springframework.web.bind.annotation.*;

import com.ec.prontiauto.abstracts.AbstractController;
import com.ec.prontiauto.abstracts.GenericMethods;
import com.ec.prontiauto.dao.PagosUnoRequestDao;
import com.ec.prontiauto.dao.PagosUnoResponseDao;
import com.ec.prontiauto.entidad.PagosUno;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.mapper.PagosUnoMapper;
import com.ec.prontiauto.repositoryImpl.PagosDosRepositoryImpl;
import com.ec.prontiauto.repositoryImpl.PagosUnoRepositoryImpl;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/pagos1")
@Api(tags = "Pagos Uno", description = "Gestion Pagos Uno")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class PagosUnoController
        extends AbstractController<PagosUno, PagosUnoRequestDao, PagosUnoResponseDao, Integer> {

    private GenericMethods genericMethods = new GenericMethods();

    @Autowired
    private PagosUnoRepositoryImpl PagosUnoRepositoryImpl;

    @Autowired
    private PagosDosRepositoryImpl PagosDosRepositoryImpl;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PagosUnoResponseDao devolverRespuestaDao(PagosUno entity) {
        return PagosUnoMapper.setEntityToDaoResponse.apply(entity);
    }

    @Override
    @Transactional
    public PagosUno devolverRespuestaUpdate(PagosUno entity, Integer id) {
        PagosUno antiguo = (PagosUno) genericMethods.findById("PagosUno", entityManager, id);
        if (antiguo != null) {
            antiguo = antiguo.setValoresDiferentes(antiguo, entity);
        }
        return antiguo;
    }

    @Override
    public PagosUno setDaoRequestToEntity(PagosUnoRequestDao dao) {
        return PagosUnoMapper.setDaoRequestToEntity.apply(dao);
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
                    this.PagosUnoRepositoryImpl,
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
