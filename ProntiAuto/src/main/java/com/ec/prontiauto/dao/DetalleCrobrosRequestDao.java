package com.ec.prontiauto.dao;

import java.math.BigDecimal;

public class DetalleCrobrosRequestDao {

    private BigDecimal noCuota;
    private String descripcion;
    private String fecha;
    private BigDecimal valor;
    private BigDecimal aCobrar;
    private BigDecimal cantidadregistradaEnCobro;
    private String tipo;

    public BigDecimal getNoCuota() {
        return noCuota;
    }

    public void setNoCuota(BigDecimal noCuota) {
        this.noCuota = noCuota;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getaCobrar() {
        return aCobrar;
    }

    public void setaCobrar(BigDecimal aCobrar) {
        this.aCobrar = aCobrar;
    }

    public BigDecimal getCantidadregistradaEnCobro() {
        return cantidadregistradaEnCobro;
    }

    public void setCantidadregistradaEnCobro(BigDecimal cantidadregistradaEnCobro) {
        this.cantidadregistradaEnCobro = cantidadregistradaEnCobro;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
