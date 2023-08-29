package com.ec.prontiauto.controller;

import com.ec.prontiauto.abstracts.AbstractController;
import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.FiniquitoRequestDao;
import com.ec.prontiauto.entidad.Finiquito;
import com.ec.prontiauto.repositoryImpl.FiniquitoRepositoryImpl;
import io.swagger.annotations.Api;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/finiquito")
@Api(tags = "Finiquito", description = "Gestion de finiquitos de trabajadores")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class FiniquitoController extends AbstractController<Finiquito, FiniquitoRequestDao, String, Integer> {

    private final FiniquitoRepositoryImpl finiquitoRepository;
    public FiniquitoController(FiniquitoRepositoryImpl finiquitoRepository){
        this.finiquitoRepository = finiquitoRepository;
    }


    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> findBySearchAndUpdate(
            String busqueda,
            String sisHabilitado,
            Integer id,
            Integer skip,
            Integer take, String sortField, Boolean sortAscending) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> params = new HashMap<>();
        params.put("busqueda", busqueda == null ? "" : busqueda);
        params.put("sisHabilitado", sisHabilitado == null ? "" : sisHabilitado);
        params.put("id", id == null ? "" : id);

        String sortFieldDefault = "id";
        sortField = sortField == null ? sortFieldDefault : sortField;
        sortAscending = sortAscending == null || sortAscending;

        List<Object> response = this.findWithSkipAndTake(
                this.finiquitoRepository,
                params,
                skip,
                take,
                sortField,
                sortAscending);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

}
