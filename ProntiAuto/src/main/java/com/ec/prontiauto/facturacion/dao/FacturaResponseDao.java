package com.ec.prontiauto.facturacion.dao;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import com.ec.prontiauto.abstracts.AbstractResponseDao;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FacturaResponseDao extends AbstractResponseDao {
    private String itRuc;
    private String itRazonSocial;
    private String itNombreComercial;
    private String itCodDoc;
    private String itEstab;
    private String itPtoEmision;
    private String itNumeroDocumento;
    private String ifIdentificacionComprador;
    private String ifRazonSocialComprador;
    private String ifDirEstablecimiento;
    private Date ifFechaEmision;
    private BigDecimal ifImporteTotal;
    private String jsonFactura;
    private String estado;
    private String claveAcceso;
    private Timestamp fechaAutorizacion;
    private String mensajeRespuesta;

    private String urlArchivo;

    public FacturaResponseDao() {
    }

    public String getUrlArchivo() {
        return urlArchivo;
    }

    public void setUrlArchivo(String urlArchivo) {
        this.urlArchivo = urlArchivo;
    }

    public String getItRuc() {
        return itRuc;
    }

    public void setItRuc(String itRuc) {
        this.itRuc = itRuc;
    }

    public String getItRazonSocial() {
        return itRazonSocial;
    }

    public void setItRazonSocial(String itRazonSocial) {
        this.itRazonSocial = itRazonSocial;
    }

    public String getItNombreComercial() {
        return itNombreComercial;
    }

    public void setItNombreComercial(String itNombreComercial) {
        this.itNombreComercial = itNombreComercial;
    }

    public String getItCodDoc() {
        return itCodDoc;
    }

    public void setItCodDoc(String itCodDoc) {
        this.itCodDoc = itCodDoc;
    }

    public String getItEstab() {
        return itEstab;
    }

    public void setItEstab(String itEstab) {
        this.itEstab = itEstab;
    }

    public String getItPtoEmision() {
        return itPtoEmision;
    }

    public void setItPtoEmision(String itPtoEmision) {
        this.itPtoEmision = itPtoEmision;
    }

    public String getItNumeroDocumento() {
        return itNumeroDocumento;
    }

    public void setItNumeroDocumento(String itNumeroDocumento) {
        this.itNumeroDocumento = itNumeroDocumento;
    }

    public String getIfIdentificacionComprador() {
        return ifIdentificacionComprador;
    }

    public void setIfIdentificacionComprador(String ifIdentificacionComprador) {
        this.ifIdentificacionComprador = ifIdentificacionComprador;
    }

    public String getIfRazonSocialComprador() {
        return ifRazonSocialComprador;
    }

    public void setIfRazonSocialComprador(String ifRazonSocialComprador) {
        this.ifRazonSocialComprador = ifRazonSocialComprador;
    }

    public String getIfDirEstablecimiento() {
        return ifDirEstablecimiento;
    }

    public void setIfDirEstablecimiento(String ifDirEstablecimiento) {
        this.ifDirEstablecimiento = ifDirEstablecimiento;
    }

    public Date getIfFechaEmision() {
        return ifFechaEmision;
    }

    public void setIfFechaEmision(Date ifFechaEmision) {
        this.ifFechaEmision = ifFechaEmision;
    }

    public BigDecimal getIfImporteTotal() {
        return ifImporteTotal;
    }

    public void setIfImporteTotal(BigDecimal ifImporteTotal) {
        this.ifImporteTotal = ifImporteTotal;
    }

    public String getJsonFactura() {
        return jsonFactura;
    }

    public void setJsonFactura(String jsonFactura) {
        this.jsonFactura = jsonFactura;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getClaveAcceso() {
        return claveAcceso;
    }

    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
    }

    public Timestamp getFechaAutorizacion() {
        return fechaAutorizacion;
    }

    public void setFechaAutorizacion(Timestamp fechaAutorizacion) {
        this.fechaAutorizacion = fechaAutorizacion;
    }

    public String getMensajeRespuesta() {
        return mensajeRespuesta;
    }

    public void setMensajeRespuesta(String mensajeRespuesta) {
        this.mensajeRespuesta = mensajeRespuesta;
    }
}
