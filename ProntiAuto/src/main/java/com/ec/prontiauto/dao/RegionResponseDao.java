package com.ec.prontiauto.dao;

import java.util.List;

import com.ec.prontiauto.abstracts.AbstractResponseDao;

public class RegionResponseDao extends AbstractResponseDao {
    private String nombre;
    private String provincia;
    private String ciudad;
    private List<AgenciaResponseDao> agenciaCollection;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public List<AgenciaResponseDao> getAgenciaCollection() {
        return agenciaCollection;
    }

    public void setAgenciaCollection(List<AgenciaResponseDao> agenciaCollection) {
        this.agenciaCollection = agenciaCollection;
    }

}
