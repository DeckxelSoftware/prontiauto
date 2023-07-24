package com.ec.prontiauto.dao;

public class ActualizarPasswordDao {
    private String passwordActual;
    private String passwordNuevo;

    public ActualizarPasswordDao() {
    }

    public String getPasswordActual() {
        return passwordActual;
    }

    public void setPasswordActual(String passwordActual) {
        this.passwordActual = passwordActual;
    }

    public String getPasswordNuevo() {
        return passwordNuevo;
    }

    public void setPasswordNuevo(String passwordNuevo) {
        this.passwordNuevo = passwordNuevo;
    }

}
