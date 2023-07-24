package com.ec.prontiauto.abstracts;

public class AbstractRequestDao {
    private Integer id;

    private String sisHabilitado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSisHabilitado() {
        return sisHabilitado;
    }

    public void setSisHabilitado(String sisHabilitado) {
        this.sisHabilitado = sisHabilitado;
    }
}
