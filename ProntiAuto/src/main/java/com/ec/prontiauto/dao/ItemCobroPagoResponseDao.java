package com.ec.prontiauto.dao;

import com.ec.prontiauto.abstracts.AbstractResponseDao;

public class ItemCobroPagoResponseDao extends AbstractResponseDao {
    private String nombreItem;
    private String nombreCuenta;
    private CuentaContableResponseDao idCuentaContable;

    public ItemCobroPagoResponseDao(){}

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

    public CuentaContableResponseDao getIdCuentaContable() {
        return idCuentaContable;
    }

    public void setIdCuentaContable(CuentaContableResponseDao idCuentaContable) {
        this.idCuentaContable = idCuentaContable;
    }
    
}
