package com.ec.prontiauto.dao;

import java.math.BigDecimal;
import java.util.Date;

public class UnificacionContratoDao {
    private Integer idHistoricoPlanContrato1;
    private Integer idHistoricoPlanContrato2;
    private Integer idContrato1;
    private Integer idContrato2;
    private Integer idPlan1;
    private Integer idPlan2;
    private Integer idNuevoPlan;
    private Integer plazoMesSeleccionado;
    private Double numeroContrato;
    private String observacion;
    private Date fechaInicio;
    private Date fechaInicioCobro;
    private BigDecimal dsctoRecargo;
    private Integer idVendedor;
    private BigDecimal cuota;

    public UnificacionContratoDao() {
    }

    public Integer getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(Integer idVendedor) {
        this.idVendedor = idVendedor;
    }

    public Date getFechaInicioCobro() {
        return fechaInicioCobro;
    }

    public void setFechaInicioCobro(Date fechaInicioCobro) {
        this.fechaInicioCobro = fechaInicioCobro;
    }

    public Integer getIdHistoricoPlanContrato1() {
        return idHistoricoPlanContrato1;
    }

    public void setIdHistoricoPlanContrato1(Integer idHistoricoPlanContrato1) {
        this.idHistoricoPlanContrato1 = idHistoricoPlanContrato1;
    }

    public Integer getIdHistoricoPlanContrato2() {
        return idHistoricoPlanContrato2;
    }

    public void setIdHistoricoPlanContrato2(Integer idHistoricoPlanContrato2) {
        this.idHistoricoPlanContrato2 = idHistoricoPlanContrato2;
    }

    public Integer getIdContrato1() {
        return idContrato1;
    }

    public void setIdContrato1(Integer idContrato1) {
        this.idContrato1 = idContrato1;
    }

    public Integer getIdContrato2() {
        return idContrato2;
    }

    public void setIdContrato2(Integer idContrato2) {
        this.idContrato2 = idContrato2;
    }

    public Integer getIdPlan1() {
        return idPlan1;
    }

    public void setIdPlan1(Integer idPlan1) {
        this.idPlan1 = idPlan1;
    }

    public Integer getIdPlan2() {
        return idPlan2;
    }

    public void setIdPlan2(Integer idPlan2) {
        this.idPlan2 = idPlan2;
    }

    public Integer getIdNuevoPlan() {
        return idNuevoPlan;
    }

    public void setIdNuevoPlan(Integer idNuevoPlan) {
        this.idNuevoPlan = idNuevoPlan;
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

    public BigDecimal getDsctoRecargo() {
        return dsctoRecargo;
    }

    public void setDsctoRecargo(BigDecimal dsctoRecargo) {
        this.dsctoRecargo = dsctoRecargo;
    }

    public BigDecimal getCuota() {
        return cuota;
    }

    public void setCuota(BigDecimal cuota) {
        this.cuota = cuota;
    }

}
