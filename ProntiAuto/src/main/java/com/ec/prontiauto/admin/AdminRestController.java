package com.ec.prontiauto.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;


@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
@Api(tags = "Admin", value = "Admin", description = "Gestion de admin")
public class AdminRestController {


    @RequestMapping(method = RequestMethod.GET)
    public String find() {
        return "This resource is protected si invoca al recurso";
    }
    
}

