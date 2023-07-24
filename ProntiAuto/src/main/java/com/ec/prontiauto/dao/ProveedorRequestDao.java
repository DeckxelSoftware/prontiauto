package com.ec.prontiauto.dao;

import com.ec.prontiauto.abstracts.AbstractRequestDao;

public class ProveedorRequestDao extends AbstractRequestDao {
    private String tipoProveedor;
    private String nombrePersonaReferencia;
    private String contactoReferencia;
    private String tipoCuentaContable;
    private String claseContribuyente;
    private String obligadoLLevarContabilidad;
    private String agenteRetencion;

    private Object idUsuario;
    private Object idEmpresa;

    public ProveedorRequestDao() {
    }

    public String getTipoProveedor() {
        return tipoProveedor;
    }

    public void setTipoProveedor(String tipoProveedor) {
        this.tipoProveedor = tipoProveedor;
    }

    public String getNombrePersonaReferencia() {
        return nombrePersonaReferencia;
    }

    public void setNombrePersonaReferencia(String nombrePersonaReferencia) {
        this.nombrePersonaReferencia = nombrePersonaReferencia;
    }

    public String getcontactoReferencia() {
        return contactoReferencia;
    }

    public void setcontactoReferencia(String contactoReferencia) {
        this.contactoReferencia = contactoReferencia;
    }

    public String getTipoCuentaContable() {
        return tipoCuentaContable;
    }

    public void setTipoCuentaContable(String tipoCuentaContable) {
        this.tipoCuentaContable = tipoCuentaContable;
    }

    public String getClaseContribuyente() {
        return claseContribuyente;
    }

    public void setClaseContribuyente(String claseContribuyente) {
        this.claseContribuyente = claseContribuyente;
    }

    public String getObligadoLLevarContabilidad() {
        return obligadoLLevarContabilidad;
    }

    public void setObligadoLLevarContabilidad(String obligadoLLevarContabilidad) {
        this.obligadoLLevarContabilidad = obligadoLLevarContabilidad;
    }

    public String getAgenteRetencion() {
        return agenteRetencion;
    }

    public void setAgenteRetencion(String agenteRetencion) {
        this.agenteRetencion = agenteRetencion;
    }

    public Object getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Object idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Object getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Object idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

}
