package com.ec.prontiauto.dao;

import com.ec.prontiauto.abstracts.AbstractResponseDao;
import com.ec.prontiauto.entidad.Recurso;

public class RecursoResponseDao extends AbstractResponseDao {

    private String nombre;

    public RecursoResponseDao(){}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public RecursoResponseDao(Recurso recurso){
        this.setId(recurso.getId());
        this.setNombre(recurso.getNombre());
        this.setSisHabilitado(recurso.getSisHabilitado());
    }
}
