package com.ec.prontiauto.dao;

import java.math.BigDecimal;
import java.util.Date;

public class CrearContratoDao {
    private Integer idCliente;
    private Integer idVendedor;
    private Integer idPlan;
    private Double numeroContrato;
    private Date fechaInicio;
    private BigDecimal dsctoInscripcion;
    private BigDecimal dsctoPrimeraCuota;
    private String observacion;
    private Date fechaInicioCobro;
    private Integer plazoMesSeleccionado;
    private BigDecimal cuota;

    public CrearContratoDao() {
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(Integer idVendedor) {
        this.idVendedor = idVendedor;
    }

    public Integer getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(Integer idPlan) {
        this.idPlan = idPlan;
    }

    public Double getNumeroContrato() {
        return numeroContrato;
    }

    public void setNumeroContrato(Double numeroContrato) {
        this.numeroContrato = numeroContrato;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public BigDecimal getDsctoInscripcion() {
        return dsctoInscripcion;
    }

    public void setDsctoInscripcion(BigDecimal dsctoInscripcion) {
        this.dsctoInscripcion = dsctoInscripcion;
    }

    public BigDecimal getDsctoPrimeraCuota() {
        return dsctoPrimeraCuota;
    }

    public void setDsctoPrimeraCuota(BigDecimal dsctoPrimeraCuota) {
        this.dsctoPrimeraCuota = dsctoPrimeraCuota;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getFechaInicioCobro() {
        return fechaInicioCobro;
    }

    public void setFechaInicioCobro(Date fechaInicioCobro) {
        this.fechaInicioCobro = fechaInicioCobro;
    }

    public Integer getPlazoMesSeleccionado() {
        return plazoMesSeleccionado;
    }

    public void setPlazoMesSeleccionado(Integer plazoMesSeleccionado) {
        this.plazoMesSeleccionado = plazoMesSeleccionado;
    }

    public BigDecimal getCuota() {
        return cuota;
    }

    public void setCuota(BigDecimal cuota) {
        this.cuota = cuota;
    }

}
