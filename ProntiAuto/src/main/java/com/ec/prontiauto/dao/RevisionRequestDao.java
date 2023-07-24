package com.ec.prontiauto.dao;

import java.sql.Date;

public class RevisionRequestDao {

    private Integer id;
    private String calificacion;
    private String compresionMotor;
    private String caja;
    private String observaciones;
    private Date fechaFirmaAprobacion;

    private Integer idArticulo;

    public RevisionRequestDao(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCalificacion() {
        return calificacion;
    }
    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }
    public String getCompresionMotor() {
        return compresionMotor;
    }
    public void setCompresionMotor(String compresionMotor) {
        this.compresionMotor = compresionMotor;
    }
    public String getCaja() {
        return caja;
    }
    public void setCaja(String caja) {
        this.caja = caja;
    }
    public String getObservaciones() {
        return observaciones;
    }
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    public Date getFechaFirmaAprobacion() {
        return fechaFirmaAprobacion;
    }
    public void setFechaFirmaAprobacion(Date fechaFirmaAprobacion) {
        this.fechaFirmaAprobacion = fechaFirmaAprobacion;
    }  

    public Integer getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(Integer idArticulo) {
        this.idArticulo = idArticulo;
    }
    
}
