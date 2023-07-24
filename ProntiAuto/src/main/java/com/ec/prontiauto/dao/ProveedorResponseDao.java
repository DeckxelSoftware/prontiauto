package com.ec.prontiauto.dao;

import java.util.List;

import com.ec.prontiauto.abstracts.AbstractRequestDao;

public class ProveedorResponseDao extends AbstractRequestDao {

    private String tipoProveedor;
    private String nombrePersonaReferencia;
    private String contactoReferencia;
    private String tipoCuentaContable;
    private String claseContribuyente;
    private String obligadoLLevarContabilidad;
    private String agenteRetencion;

    private UsuarioResponseDao idUsuario;
    private EmpresaResponseDao idEmpresa;
    private List<OrdenCompraResponseDao> ordenCompraCollection;

    public ProveedorResponseDao() {
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

    public String getContactoReferencia() {
        return contactoReferencia;
    }

    public void setContactoReferencia(String contatoReferencia) {
        this.contactoReferencia = contatoReferencia;
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

    public UsuarioResponseDao getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(UsuarioResponseDao idUsuario) {
        this.idUsuario = idUsuario;
    }

    public EmpresaResponseDao getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(EmpresaResponseDao idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public List<OrdenCompraResponseDao> getOrdenCompraCollection() {
        return ordenCompraCollection;
    }

    public void setOrdenCompraCollection(List<OrdenCompraResponseDao> ordenCompraCollection) {
        this.ordenCompraCollection = ordenCompraCollection;
    }

}
