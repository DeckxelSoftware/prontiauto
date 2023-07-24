package com.ec.prontiauto.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ec.prontiauto.dao.CobroRequestDao;
import com.ec.prontiauto.exception.ApiRequestException;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.repositoryImpl.CobroRepositoryImpl;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/cobro")
@Transactional
@Api(tags = "Cobros", description = "Cobros del Contrato")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class CobroController {
    @Autowired
    private CobroRepositoryImpl cobroRepository;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> actualizarPlazo(@RequestBody CobroRequestDao dao) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        try {
            httpHeaders.add("STATUS", "200");
            String response = this.cobroRepository.abstractProcedure(dao, "cobros_contrato");

            return new ResponseEntity<ExceptionResponse>(
                    new ExceptionResponse("200", response),
                    httpHeaders,
                    HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("-------------------\n" + e.getMessage());
            throw new ApiRequestException(e.getMessage());
        }
    }

    @RequestMapping(value = "/anulacion-cobro/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> anulacionCobro(@PathVariable("id") Integer id) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        try {
            httpHeaders.add("STATUS", "200");
            IdRequestDao dao = new IdRequestDao();
            dao.setId(id);
            String response = this.cobroRepository.abstractProcedure(dao, "anulacion_cobro");

            return new ResponseEntity<ExceptionResponse>(
                    new ExceptionResponse("200", response),
                    httpHeaders,
                    HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("-------------------\n" + e.getMessage());
            throw new ApiRequestException(e.getMessage());
        }
    }

}
