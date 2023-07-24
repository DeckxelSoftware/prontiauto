package com.ec.prontiauto.dao;

import java.math.BigDecimal;
import java.util.Date;

import com.ec.prontiauto.abstracts.AbstractRequestDao;

public class AbonoPrestamoRequestDao extends AbstractRequestDao {
    private Date fechaPago;
    private Integer numeroPago;
    private String modalidadDescuento;
    private String mes;
    private Integer anio;
    private BigDecimal valorCuota;
    private BigDecimal valorCapital;
    private BigDecimal valorTasaInteres;
    private String estaPagado;
    private Integer idPrestamo;

    public AbonoPrestamoRequestDao() {
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Integer getNumeroPago() {
        return numeroPago;
    }

    public void setNumeroPago(Integer numeroPago) {
        this.numeroPago = numeroPago;
    }

    public String getModalidadDescuento() {
        return modalidadDescuento;
    }

    public void setModalidadDescuento(String modalidadDescuento) {
        this.modalidadDescuento = modalidadDescuento;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public BigDecimal getValorCuota() {
        return valorCuota;
    }

    public void setValorCuota(BigDecimal valorCuota) {
        this.valorCuota = valorCuota;
    }

    public BigDecimal getValorCapital() {
        return valorCapital;
    }

    public void setValorCapital(BigDecimal valorCapital) {
        this.valorCapital = valorCapital;
    }

    public BigDecimal getValorTasaInteres() {
        return valorTasaInteres;
    }

    public void setValorTasaInteres(BigDecimal valorTasaInteres) {
        this.valorTasaInteres = valorTasaInteres;
    }

    public String getEstaPagado() {
        return estaPagado;
    }

    public void setEstaPagado(String estaPagado) {
        this.estaPagado = estaPagado;
    }

    public Integer getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(Integer idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

}
