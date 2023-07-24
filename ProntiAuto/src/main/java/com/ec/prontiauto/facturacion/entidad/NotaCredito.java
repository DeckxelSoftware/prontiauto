package com.ec.prontiauto.facturacion.entidad;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

@Entity
@Table(name = "nota_credito")
public class NotaCredito extends AbstractEntities {

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

    @Column(name = "it_numero_documento", nullable = false, length = 255)
    @CsvBindByName(column = "it_numero_documento")
    private String itNumeroDocumento;

    @Column(name = "in_identificacion_comprador", nullable = false)
    @CsvBindByName(column = "in_identificacion_comprador")
    private String inIdentificacionComprador;

    @Column(name = "in_razon_social_comprador", nullable = false, length = 255)
    @CsvBindByName(column = "in_razon_social_comprador")
    private String inRazonSocialComprador;

    @Column(name = "in_cod_doc_modificado", nullable = false, length = 255)
    @CsvBindByName(column = "in_cod_doc_modificado")
    private String inCodDocModificado;

    @Column(name = "in_num_doc_modificado", nullable = false, length = 255)
    @CsvBindByName(column = "in_num_doc_modificado")
    private String inNumDocModificado;

    @Column(name = "in_dir_establecimiento", nullable = false, length = 255)
    @CsvBindByName(column = "in_dir_establecimiento")
    private String inDirEstablecimiento;

    @Column(name = "in_fecha_emision", nullable = false)
    @CsvDate(value = "yyyy-MM-dd")
    @CsvBindByName(column = "in_fecha_emision")
    private Date inFechaEmision;

    @Column(name = "in_total_sin_impuestos", nullable = false)
    @CsvBindByName(column = "in_total_sin_impuestos")
    private Double inTotalSinImpuestos;

    @Column(name = "in_valor_modificado", nullable = false)
    @CsvBindByName(column = "in_valor_modificado")
    private Double inValorModificado;

    @Column(name = "in_motivo", nullable = false)
    @CsvBindByName(column = "in_motivo")
    private String inMotivo;

    @Lob
    @Column(name = "json_factura", nullable = false)
    @CsvBindByName(column = "json_factura")
    private String jsonFactura;

    public NotaCredito() {
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
