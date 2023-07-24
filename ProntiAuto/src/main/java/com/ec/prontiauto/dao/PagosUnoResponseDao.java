package com.ec.prontiauto.dao;

import com.ec.prontiauto.abstracts.AbstractResponseDao;

import java.sql.Date;
import java.util.List;

public class PagosUnoResponseDao extends AbstractResponseDao {

    private Date fechaUltimoPago;
    private Date fechaInicio;
    private Date fechaFin;
    private String nombre;
    private String periodo;

    private List<PagosDosResponseDao> pagosDosCollection;

    public PagosUnoResponseDao() {
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

    public List<PagosDosResponseDao> getPagosDosCollection() {
        return pagosDosCollection;
    }

    public void setPagosDosCollection(List<PagosDosResponseDao> pagosDosCollection) {
        this.pagosDosCollection = pagosDosCollection;
    }
}
