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
import com.ec.prontiauto.dao.ImpuestoRentaRequestDao;
import com.ec.prontiauto.dao.ImpuestoRentaResponseDao;
import com.ec.prontiauto.entidad.ImpuestoRenta;
import com.ec.prontiauto.exception.ApiRequestException;
import com.ec.prontiauto.mapper.ImpuestoRentaMapper;
import com.ec.prontiauto.repositoryImpl.ImpuestoRentaRepositoryImpl;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/impuestoRenta")
@Api(tags = "ImpuestoRenta", description = "Gestion de ImpuestoRenta")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class ImpuestoRentaController
        extends AbstractController<ImpuestoRenta, ImpuestoRentaRequestDao, ImpuestoRentaResponseDao, Integer> {

    private GenericMethods genericMethods = new GenericMethods();
    @Autowired
    private ImpuestoRentaRepositoryImpl impuestoRentaRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ImpuestoRentaResponseDao devolverRespuestaDao(ImpuestoRenta entity) {
        return ImpuestoRentaMapper.setEntityToDaoResponse.apply(entity);
    }

    @Override
    @Transactional
    public ImpuestoRenta devolverRespuestaUpdate(ImpuestoRenta entity, Integer id) {
        ImpuestoRenta antiguo = (ImpuestoRenta) genericMethods.findById("ImpuestoRenta", entityManager, id);
        if (antiguo != null) {
            antiguo = antiguo.setValoresDiferentes(antiguo, entity);
        }
        return antiguo;
    }

    @Override
    public ImpuestoRenta setDaoRequestToEntity(ImpuestoRentaRequestDao dao) {
        return ImpuestoRentaMapper.setDaoRequestToEntity.apply(dao);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> findBySearchAndUpdate(String busqueda,
            Integer anio,
            String sisHabilitado,
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
            params.put("anio", anio == null ? "" : anio);

            String sortFieldDefault = "id";
            sortField = sortField == null ? sortFieldDefault : sortField;
            sortAscending = sortAscending == null ? true : sortAscending;
            List<Object> list = this.findWithSkipAndTake(
                    this.impuestoRentaRepository,
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
