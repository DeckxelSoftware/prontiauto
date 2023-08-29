package com.ec.prontiauto.dao;


import java.sql.Date;

public class UtilidadesRequestDao {
    private Integer idPeriodoLaboral;
    private Integer idPagoUno;
    private Date fechaInicio;
    private Date fechaFin;

    public Integer getIdPeriodoLaboral() {
        return idPeriodoLaboral;
    }

    public void setIdPeriodoLaboral(Integer idPeriodoLaboral) {
        this.idPeriodoLaboral = idPeriodoLaboral;
    }

    public Integer getIdPagoUno() {
        return idPagoUno;
    }

    public void setIdPagoUno(Integer idPagoUno) {
        this.idPagoUno = idPagoUno;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
}
