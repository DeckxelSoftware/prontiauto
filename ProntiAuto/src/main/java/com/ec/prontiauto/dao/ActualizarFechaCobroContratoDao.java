package com.ec.prontiauto.dao;

import java.util.Date;

public class ActualizarFechaCobroContratoDao {
    private Date fechaIniciaCobro;
    private Integer idHistoricoPlanContrato;

    public ActualizarFechaCobroContratoDao() {
    }

    public Date getFechaIniciaCobro() {
        return fechaIniciaCobro;
    }

    public void setFechaIniciaCobro(Date fechaIniciaCobro) {
        this.fechaIniciaCobro = fechaIniciaCobro;
    }

    public Integer getIdHistoricoPlanContrato() {
        return idHistoricoPlanContrato;
    }

    public void setIdHistoricoPlanContrato(Integer idHistoricoPlanContrato) {
        this.idHistoricoPlanContrato = idHistoricoPlanContrato;
    }

}
