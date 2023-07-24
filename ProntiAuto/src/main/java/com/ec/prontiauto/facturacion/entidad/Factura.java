package com.ec.prontiauto.facturacion.entidad;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

@Entity
@Table(name = "factura")
public class Factura extends AbstractEntities {

    @Column(name = "it_ruc", nullable = false, length = 255)
    @CsvBindByName(column = "it_ruc")
    private String itRuc;

    @Column(name = "it_razon_social", nullable = false, length = 255)
    @CsvBindByName(column = "it_razon_social")
    private String itRazonSocial;

    @Column(name = "it_nombre_comercial", nullable = false, length = 255)
    @CsvBindByName(column = "it_nombre_comercial")
    private String itNombreComercial;

    @Column(name = "it_cod_doc", nullable = false, length = 255)
    @CsvBindByName(column = "it_cod_doc")
    private String itCodDoc;

    @Column(name = "it_estab", nullable = false, length = 255)
    @CsvBindByName(column = "it_estab")
    private String itEstab;

    @Column(name = "it_pto_emision", nullable = false, length = 255)
    @CsvBindByName(column = "it_pto_emision")
    private String itPtoEmision;

    @Column(name = "if_numero_documento", nullable = false, length = 255)
    @CsvBindByName(column = "it_numero_documento")
    private String itNumeroDocumento;

    @Column(name = "if_identificacion_comprador", nullable = false, length = 255)
    @CsvBindByName(column = "if_identificacion_comprador")
    private String ifIdentificacionComprador;

    @Column(name = "if_razon_social_comprador", nullable = false, length = 255)
    @CsvBindByName(column = "if_razon_social_comprador")
    private String ifRazonSocialComprador;

    @Column(name = "if_dir_establecimiento", nullable = false, length = 255)
    @CsvBindByName(column = "if_dir_establecimiento")
    private String ifDirEstablecimiento;

    @Column(name = "if_fecha_emision", nullable = false)
    @CsvDate(value = "yyyy-MM-dd")
    @CsvBindByName(column = "if_fecha_emision")
    private Date ifFechaEmision;

    @Column(name = "if_importe_total", nullable = false)
    @CsvBindByName(column = "if_importe_total")
    private BigDecimal ifImporteTotal;

    @Lob
    @Column(name = "json_factura", nullable = false)
    @CsvBindByName(column = "json_factura")
    private String jsonFactura;

    public Factura() {
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

}
