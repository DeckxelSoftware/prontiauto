package com.ec.prontiauto.controller;


import com.ec.prontiauto.abstracts.AbstractController;
import com.ec.prontiauto.dao.RecursoRequestDao;
import com.ec.prontiauto.dao.RecursoResponseDao;
import com.ec.prontiauto.entidad.Recurso;
import com.ec.prontiauto.mapper.RecursoMapper;
import com.ec.prontiauto.repositoryImpl.RecursoRepositoryImpl;
import io.swagger.annotations.Api;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recurso")
@Api(tags = "Recurso", value = "ProntiAuto", description = "Creación, actualización y  consulta de recurso")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class RecursoController extends AbstractController<Recurso, RecursoRequestDao, RecursoResponseDao, Integer> {

    private final RecursoRepositoryImpl recursoRepository;
    public RecursoController(RecursoRepositoryImpl recursoRepository){
        this.recursoRepository = recursoRepository;
    }

    @Override
    public RecursoResponseDao devolverRespuestaDao(Recurso recurso) {
        return RecursoMapper.setEntityToDaoResponse.apply(recurso);
    }

    @Override
    public Recurso setDaoRequestToEntity(RecursoRequestDao dao) {
        return RecursoMapper.setDaoRequestToEntity.apply(dao);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> findObject(String busqueda, Integer id, String sisHabilitado,
                                        Integer skip,
                                        Integer take,
                                        String sortField,
                                        Boolean sortAscending){
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> params = new HashMap<>();
        params.put("busqueda", busqueda == null ? "" : busqueda);
        params.put("sisHabilitado", sisHabilitado == null ? "" : sisHabilitado);
        params.put("id", id == null ? "" : id);
        String sortFieldDefault = "id";
        sortField = sortField == null ? sortFieldDefault : sortField;
        sortAscending = sortAscending == null ? true : sortAscending;


        List<Object> list = this.findWithSkipAndTake(
                this.recursoRepository,
                params,
                skip,
                take,
                sortField,
                sortAscending);

        return new ResponseEntity<>(list,headers,HttpStatus.OK);
    }


}
