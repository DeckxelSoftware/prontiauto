package com.ec.prontiauto.dao;

import com.ec.prontiauto.abstracts.AbstractRequestDao;

import java.sql.Date;

public class PagosUnoRequestDao extends AbstractRequestDao {

    private Date fechaUltimoPago;
    private Date fechaInicio;
    private Date fechaFin;
    private String nombre;
    private String periodo;
    private Integer idPeriodoLaboral;

    public PagosUnoRequestDao() {
    }

    public Date getFechaUltimoPago() {
        return fechaUltimoPago;
    }

    public void setFechaUltimoPago(Date fechaUltimoPago) {
        this.fechaUltimoPago = fechaUltimoPago;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Integer getIdPeriodoLaboral() {
        return idPeriodoLaboral;
    }

    public void setIdPeriodoLaboral(Integer idPeriodoLaboral) {
        this.idPeriodoLaboral = idPeriodoLaboral;
    }
}
