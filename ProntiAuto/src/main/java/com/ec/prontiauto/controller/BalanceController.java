package com.ec.prontiauto.controller;

import antlr.collections.impl.IntRange;
import com.ec.prontiauto.repositoryImpl.BalanceRepository;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.swagger.annotations.Api;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping(value = "/api/balances")
@Api(tags = "Balances", description = "Gestion de balances de la empresa")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class BalanceController {

    private final BalanceRepository balanceRepository;
    public BalanceController(BalanceRepository balanceRepository){
        this.balanceRepository = balanceRepository;
    }


    @RequestMapping(method =  RequestMethod.GET)
    public ResponseEntity<?> calcularBalanceGeneral(Date fechaIncio, Date fechaFin, Integer anio){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        List<Object> response = IntStream.range(1, 4)
                .mapToObj(identificador -> balanceRepository.calcularBalanceGeneral(identificador, anio))
                .map(respuesta -> new Gson().fromJson((String) respuesta, JsonElement.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(response, headers, HttpStatus.OK);

    }


}
