package com.ec.prontiauto.facturacion.dao;

import java.sql.Date;

import com.ec.prontiauto.abstracts.AbstractResponseDao;

public class NotaCreditoResponseDao extends AbstractResponseDao {

    private String itRuc;
    private String itRazonSocial;
    private String itNombreComercial;
    private String itCodDoc;
    private String itEstab;
    private String itPtoEmision;
    private String itNumeroDocumento;
    private String inIdentificacionComprador;
    private String inRazonSocialComprador;
    private String inCodDocModificado;
    private String inNumDocModificado;
    private String inDirEstablecimiento;
    private Date inFechaEmision;
    private Double inTotalSinImpuestos;
    private Double inValorModificado;
    private String inMotivo;
    private String jsonFactura;

    public NotaCreditoResponseDao() {
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

    public String getInIdentificacionComprador() {
        return inIdentificacionComprador;
    }

    public void setInIdentificacionComprador(String inIdentificacionComprador) {
        this.inIdentificacionComprador = inIdentificacionComprador;
    }

    public String getInRazonSocialComprador() {
        return inRazonSocialComprador;
    }

    public void setInRazonSocialComprador(String inRazonSocialComprador) {
        this.inRazonSocialComprador = inRazonSocialComprador;
    }

    public String getInCodDocModificado() {
        return inCodDocModificado;
    }

    public void setInCodDocModificado(String inCodDocModificado) {
        this.inCodDocModificado = inCodDocModificado;
    }

    public String getInNumDocModificado() {
        return inNumDocModificado;
    }

    public void setInNumDocModificado(String inNumDocModificado) {
        this.inNumDocModificado = inNumDocModificado;
    }

    public String getInDirEstablecimiento() {
        return inDirEstablecimiento;
    }

    public void setInDirEstablecimiento(String inDirEstablecimiento) {
        this.inDirEstablecimiento = inDirEstablecimiento;
    }

    public Date getInFechaEmision() {
        return inFechaEmision;
    }

    public void setInFechaEmision(Date inFechaEmision) {
        this.inFechaEmision = inFechaEmision;
    }

    public Double getInTotalSinImpuestos() {
        return inTotalSinImpuestos;
    }

    public void setInTotalSinImpuestos(Double inTotalSinImpuestos) {
        this.inTotalSinImpuestos = inTotalSinImpuestos;
    }

    public Double getInValorModificado() {
        return inValorModificado;
    }

    public void setInValorModificado(Double inValorModificado) {
        this.inValorModificado = inValorModificado;
    }

    public String getInMotivo() {
        return inMotivo;
    }

    public void setInMotivo(String inMotivo) {
        this.inMotivo = inMotivo;
    }

    public String getJsonFactura() {
        return jsonFactura;
    }

    public void setJsonFactura(String jsonFactura) {
        this.jsonFactura = jsonFactura;
    }

}
