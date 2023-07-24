package com.ec.prontiauto.dao;

import java.math.BigDecimal;

public class PrestamoDao {
    private Integer idTrabajador;
    private String tipoPrestamo;
    private BigDecimal valor;
    private Integer cuotas;
    private BigDecimal tasaInteres;
    private String concepto;
    private String nombreApellidoResponsable;
    private String comprobanteEgreso;
    private String modalidadDescuento;

    public PrestamoDao() {
    }

    public Integer getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(Integer idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public String getTipoPrestamo() {
        return tipoPrestamo;
    }

    public void setTipoPrestamo(String tipoPrestamo) {
        this.tipoPrestamo = tipoPrestamo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Integer getCuotas() {
        return cuotas;
    }

    public void setCuotas(Integer cuotas) {
        this.cuotas = cuotas;
    }

    public BigDecimal getTasaInteres() {
        return tasaInteres;
    }

    public void setTasaInteres(BigDecimal tasaInteres) {
        this.tasaInteres = tasaInteres;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getNombreApellidoResponsable() {
        return nombreApellidoResponsable;
    }

    public void setNombreApellidoResponsable(String nombreApellidoResponsable) {
        this.nombreApellidoResponsable = nombreApellidoResponsable;
    }

    public String getComprobanteEgreso() {
        return comprobanteEgreso;
    }

    public void setComprobanteEgreso(String comprobanteEgreso) {
        this.comprobanteEgreso = comprobanteEgreso;
    }

    public String getModalidadDescuento() {
        return modalidadDescuento;
    }

    public void setModalidadDescuento(String modalidadDescuento) {
        this.modalidadDescuento = modalidadDescuento;
    }

}
