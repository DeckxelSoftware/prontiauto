package com.ec.prontiauto.controller;

import java.sql.Date;
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
import com.ec.prontiauto.dao.AbonoPrestamoRequestDao;
import com.ec.prontiauto.dao.AbonoPrestamoResponseDao;
import com.ec.prontiauto.entidad.AbonoPrestamo;
import com.ec.prontiauto.entidad.Prestamo;
import com.ec.prontiauto.exception.ApiRequestException;
import com.ec.prontiauto.mapper.AbonoPrestamoMapper;
import com.ec.prontiauto.repositoryImpl.AbonoPrestamoRepositoryImpl;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/abono-prestamo")
@Api(tags = "AbonoPrestamo", description = "Gestion de AbonoPrestamo")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class AbonoPrestamoController
        extends AbstractController<AbonoPrestamo, AbonoPrestamoRequestDao, AbonoPrestamoResponseDao, Integer> {

    private GenericMethods genericMethods = new GenericMethods();
    @Autowired
    private AbonoPrestamoRepositoryImpl abonoPrestamoRepositoryImpl;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public AbonoPrestamoResponseDao devolverRespuestaDao(AbonoPrestamo entity) {
        return AbonoPrestamoMapper.setEntityToDaoResponse.apply(entity);
    }

    @Override
    @Transactional
    public AbonoPrestamo devolverRespuestaUpdate(AbonoPrestamo entity, Integer id) {
        AbonoPrestamo antiguo = (AbonoPrestamo) genericMethods.findById("AbonoPrestamo",
                entityManager, id);
        if (antiguo != null) {
            antiguo = antiguo.setValoresDiferentes(antiguo, entity);
        }
        return antiguo;
    }

    @Override
    public AbonoPrestamo setDaoRequestToEntity(AbonoPrestamoRequestDao dao) {
        return AbonoPrestamoMapper.setDaoRequestToEntity.apply(dao);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> findBySearchAndUpdate(String busqueda,
            String sisHabilitado,
            String tipoPrestamo,
            Date fechaPrestamo,
            Integer idPrestamo,
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
            params.put("tipoPrestamo", tipoPrestamo == null ? "" : tipoPrestamo);
            params.put("fechaPrestamo", fechaPrestamo == null ? "" : fechaPrestamo);
            Prestamo prestamo = new Prestamo();
            prestamo.setId(idPrestamo);
            params.put("idPrestamo", idPrestamo == null ? "" : prestamo);
            String sortFieldDefault = "id";
            sortField = sortField == null ? sortFieldDefault : sortField;
            sortAscending = sortAscending == null ? true : sortAscending;

            List<Object> list = this.findWithSkipAndTake(
                    this.abonoPrestamoRepositoryImpl,
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
