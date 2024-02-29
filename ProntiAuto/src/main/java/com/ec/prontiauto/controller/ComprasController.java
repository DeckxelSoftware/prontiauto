package com.ec.prontiauto.controller;


import com.ec.prontiauto.dao.CabeceraCompraRequestDao;
import com.ec.prontiauto.repositoryImpl.ComprasRepositoryImpl;
import io.swagger.annotations.Api;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/compras")
@Api(tags = "Compras", description = "Gestion de facturas fisicas o electronicas por compras realizadas por la empresa")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class ComprasController {

    private final ComprasRepositoryImpl comprasRepository;

    public ComprasController(ComprasRepositoryImpl comprasRepository){
        this.comprasRepository = comprasRepository;
    }

    @PostMapping(value = "/factura")
    public ResponseEntity<?> crearFactura(@RequestBody CabeceraCompraRequestDao cabeceraCompraRequestDao){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Object response = comprasRepository.crearFactura(cabeceraCompraRequestDao);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

}
