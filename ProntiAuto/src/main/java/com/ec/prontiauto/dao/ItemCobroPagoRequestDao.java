package com.ec.prontiauto.dao;

import com.ec.prontiauto.abstracts.AbstractRequestDao;

public class ItemCobroPagoRequestDao extends AbstractRequestDao {
     private String nombreItem;
    private String nombreCuenta;
    private Integer idCuentaContable;

    public Integer getIdCuentaContable() {
        return idCuentaContable;
    }

    public void setIdCuentaContable(Integer idCuentaContable) {
        this.idCuentaContable = idCuentaContable;
    }

    public ItemCobroPagoRequestDao(){}

    public String getNombreItem() {
        return nombreItem;
    }

    public void setNombreItem(String nombreItem) {
        this.nombreItem = nombreItem;
    }

    public String getNombreCuenta() {
        return nombreCuenta;
    }

    public void setNombreCuenta(String nombreCuenta) {
        this.nombreCuenta = nombreCuenta;
    }
}
