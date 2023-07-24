package com.ec.prontiauto.entidad;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

@Entity
@Table(name = "abono_prestamo")
public class AbonoPrestamo extends AbstractEntities {
    @Column(name = "\"fechaPago\"", nullable = false)
    @CsvDate(value = "yyyy-MM-dd")
    @CsvBindByName(column = "fecha_pago")
    private Date fechaPago;

    @Column(name = "\"numeroPago\"", nullable = false)
    @CsvBindByName(column = "numero_pago")
    private Integer numeroPago;

    @Column(name = "\"modalidadDescuento\"", nullable = false)
    @CsvBindByName(column = "modalidad_descuento")
    private String modalidadDescuento;

    @Column(name = "\"mes\"", nullable = false)
    @CsvBindByName(column = "mes")
    private String mes;

    @Column(name = "\"anio\"", nullable = false)
    @CsvBindByName(column = "anio")
    private Integer anio;

    @Column(name = "\"valorCuota\"", nullable = false)
    @CsvBindByName(column = "valor_cuota")
    private BigDecimal valorCuota;

    @Column(name = "\"valorCapital\"", nullable = false)
    @CsvBindByName(column = "valor_capital")
    private BigDecimal valorCapital;

    @Column(name = "\"valorTasaInteres\"", nullable = false)
    @CsvBindByName(column = "valor_tasa_interes")
    private BigDecimal valorTasaInteres;

    @Column(name = "\"estaPagado\"", nullable = false)
    @CsvBindByName(column = "esta_pagado")
    private String estaPagado;

    @ManyToOne
    @JoinColumn(name = "\"idPrestamo\"", referencedColumnName = "\"id\"", nullable = false)
    private Prestamo idPrestamo;

    @Transient
    @CsvBindByName(column = "id_prestamo")
    private Integer idPrestamo1;

    public AbonoPrestamo() {
    }

    public Prestamo getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(Prestamo idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public Integer getIdPrestamo1() {
        return idPrestamo1;
    }

    public void setIdPrestamo1(Integer idPrestamo1) {
        this.idPrestamo1 = idPrestamo1;
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

    public AbonoPrestamo setValoresDiferentes(AbonoPrestamo antiguo, AbonoPrestamo actual) {
        if (actual.getFechaPago() != null) {
            antiguo.setFechaPago(actual.getFechaPago());
        }
        if (actual.getNumeroPago() != null) {
            antiguo.setNumeroPago(actual.getNumeroPago());
        }
        if (actual.getModalidadDescuento() != null) {
            antiguo.setModalidadDescuento(actual.getModalidadDescuento());
        }
        if (actual.getMes() != null) {
            antiguo.setMes(actual.getMes());
        }
        if (actual.getAnio() != null) {
            antiguo.setAnio(actual.getAnio());
        }
        if (actual.getValorCuota() != null) {
            antiguo.setValorCuota(actual.getValorCuota());
        }
        if (actual.getValorCapital() != null) {
            antiguo.setValorCapital(actual.getValorCapital());
        }
        if (actual.getValorTasaInteres() != null) {
            antiguo.setValorTasaInteres(actual.getValorTasaInteres());
        }
        if (actual.getEstaPagado() != null) {
            antiguo.setEstaPagado(actual.getEstaPagado());
        }
        if (actual.getIdPrestamo() != null && actual.getIdPrestamo().getId() != null) {
            antiguo.setIdPrestamo(actual.getIdPrestamo());
        }
        if (actual.getSisHabilitado() != null) {
            antiguo.setSisHabilitado(actual.getSisHabilitado());
        }
        return antiguo;
    }
}
