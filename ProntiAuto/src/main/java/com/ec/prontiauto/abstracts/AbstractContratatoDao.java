package com.ec.prontiauto.abstracts;

import java.math.BigDecimal;
import java.util.Date;

public abstract class AbstractContratatoDao {
    private Integer idHistoricoPlanContrato;
    private Integer idContrato;
    private Integer idPlan;
    private Double numeroContrato;
    private Integer plazoMesSeleccionado;
    private String observacion;
    private Date fechaInicio;
    private Date fechaInicioCobro;
    private BigDecimal cuota;

    public Integer getIdHistoricoPlanContrato() {
        return idHistoricoPlanContrato;
    }

    public void setIdHistoricoPlanContrato(Integer idHistoricoPlanContrato) {
        this.idHistoricoPlanContrato = idHistoricoPlanContrato;
    }

    public Integer getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(Integer idContrato) {
        this.idContrato = idContrato;
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

    public Integer getPlazoMesSeleccionado() {
        return plazoMesSeleccionado;
    }

    public void setPlazoMesSeleccionado(Integer plazoMesSeleccionado) {
        this.plazoMesSeleccionado = plazoMesSeleccionado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaInicioCobro() {
        return fechaInicioCobro;
    }

    public void setFechaInicioCobro(Date fechaInicioCobro) {
        this.fechaInicioCobro = fechaInicioCobro;
    }

    public BigDecimal getCuota() {
        return cuota;
    }

    public void setCuota(BigDecimal cuota) {
        this.cuota = cuota;
    }

}
