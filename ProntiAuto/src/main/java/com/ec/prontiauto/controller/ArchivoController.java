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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ec.prontiauto.abstracts.AbstractController;
import com.ec.prontiauto.abstracts.GenericMethods;
import com.ec.prontiauto.dao.ArchivoRequestDao;
import com.ec.prontiauto.entidad.Archivo;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.mapper.ArchivoMapper;
import com.ec.prontiauto.repositoryImpl.ArchivoRepositoryImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/archivo")
// @CrossOrigingin(origins = "*", methods = { RequestMethod.GET,
// RequestMethod.POST, RequestMethod.PUT })
@Api(tags = "Archivos", description = "Metodos para el manejo de archivos")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class ArchivoController extends AbstractController<Archivo, ArchivoRequestDao, ArchivoRequestDao, Integer> {

    private GenericMethods genericMethods = new GenericMethods();

    @Autowired
    private ArchivoRepositoryImpl archivoRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Archivo devolverRespuestaUpdate(Archivo entity, Integer id) {
        Archivo antiguo = (Archivo) genericMethods.findById("Archivo", entityManager, id);
        if (antiguo != null) {
            antiguo = antiguo.setValoresDiferentes(antiguo, entity);
        }
        return antiguo;
    }

    @Override
    public Archivo setDaoRequestToEntity(ArchivoRequestDao dao) {
        return ArchivoMapper.setDaoRequestToEntity.apply(dao);
    }

    @Override
    public ResponseEntity<?> create(@RequestBody ArchivoRequestDao dao) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        try {
            httpHeaders.add("STATUS", "200");
            Archivo newEntity = setDaoRequestToEntity(dao);
            if (archivoRepository.existFilePrimary(newEntity, null)) {
                httpHeaders.add("STATUS", "400");
                return new ResponseEntity<ExceptionResponse>(
                        new ExceptionResponse("400", "Ya existe un archivo principal para esta tabla"), httpHeaders,
                        HttpStatus.BAD_REQUEST);
            }
            entityManager.persist(newEntity);
            return new ResponseEntity<>(ArchivoMapper.setEntityToDaoResponse.apply(newEntity), httpHeaders,
                    HttpStatus.OK);
        } catch (Exception e) {

            httpHeaders.add("STATUS", "400");
            return new ResponseEntity<ExceptionResponse>(new ExceptionResponse("400", e.getMessage()), httpHeaders,
                    HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> update(@RequestBody ArchivoRequestDao dao, @PathVariable("id") Integer id) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        try {
            httpHeaders.add("STATUS", "200");
            Archivo newEntity = setDaoRequestToEntity(dao);
            if (archivoRepository.existFilePrimary(newEntity, id)) {
                httpHeaders.add("STATUS", "400");
                return new ResponseEntity<ExceptionResponse>(
                        new ExceptionResponse("400", "Ya existe un archivo principal para esta tabla"), httpHeaders,
                        HttpStatus.BAD_REQUEST);
            }
            Archivo entity = this.devolverRespuestaUpdate(newEntity, id);
            entityManager.merge(entity);
            return new ResponseEntity<>(ArchivoMapper.setEntityToDaoResponse.apply(newEntity), httpHeaders,
                    HttpStatus.OK);
        } catch (Exception e) {

            httpHeaders.add("STATUS", "400");
            return new ResponseEntity<ExceptionResponse>(new ExceptionResponse("400", e.getMessage()), httpHeaders,
                    HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(tags = "Archivos", value = "buscar archivos secundarios con skip, take y filtro de sisHabilitado")
    public ResponseEntity<?> findBySearchAndUpdate(String sisHabilitado, String idTabla,
            String nombreTabla,
            Integer skip,
            Integer take, String sortField, Boolean sortAscending) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("sisHabilitado", sisHabilitado == null ? "" : sisHabilitado);
            params.put("idTabla", idTabla == null ? "" : idTabla);
            params.put("nombreTabla", nombreTabla == null ? "" : nombreTabla);
            String sortFieldDefault = "id";
            sortField = sortField == null ? sortFieldDefault : sortField;
            sortAscending = sortAscending == null ? true : sortAscending;
            List<Object> list = this.findWithSkipAndTake(
                    this.archivoRepository,
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
