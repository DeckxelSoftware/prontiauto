package com.ec.prontiauto.dao;

public class RefinanciamientoContratoDao {
    private Integer idContrato;
    private Integer idPlan;
    private Integer idHistoricoPlanContrato;
    private Integer plazoMesSeleccionado;

    public RefinanciamientoContratoDao() {

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

    public Integer getIdHistoricoPlanContrato() {
        return idHistoricoPlanContrato;
    }

    public void setIdHistoricoPlanContrato(Integer idHistoricoPlanContrato) {
        this.idHistoricoPlanContrato = idHistoricoPlanContrato;
    }

    public Integer getPlazoMesSeleccionado() {
        return plazoMesSeleccionado;
    }

    public void setPlazoMesSeleccionado(Integer plazoMesSeleccionado) {
        this.plazoMesSeleccionado = plazoMesSeleccionado;
    }

}
