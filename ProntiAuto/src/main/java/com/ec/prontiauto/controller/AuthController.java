package com.ec.prontiauto.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ec.prontiauto.dao.ResetearPasswordDao;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.repositoryImpl.UsuarioRepositoryImpl;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/auth")
//@CrossOrigingin(origins = "*", methods = { RequestMethod.POST })
@Api(tags = "Resetear contraseña", description = "Metodo para resetear la contraseña")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class AuthController {

    @Autowired
    private UsuarioRepositoryImpl usuarioRepository;

    @RequestMapping(path = "/reset", method = RequestMethod.POST)
    @Transactional
    public ResponseEntity<?> reset(@RequestBody ResetearPasswordDao resetPasswordDao) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        try {
            httpHeaders.add("STATUS", "200");
            usuarioRepository.resetPassword(resetPasswordDao.getCorreo());
            return new ResponseEntity<>(
                    new ExceptionResponse("200", "Revice su correo para obtener su contraseña temporal"), httpHeaders,
                    HttpStatus.OK);
        } catch (Exception e) {
            httpHeaders.add("STATUS", "400");
            return new ResponseEntity<ExceptionResponse>(new ExceptionResponse("400", e.getMessage()), httpHeaders,
                    HttpStatus.BAD_REQUEST);
        }
    }

}
