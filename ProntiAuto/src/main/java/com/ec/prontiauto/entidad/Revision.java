package com.ec.prontiauto.entidad;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "revision")
public class Revision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "calificacion", nullable = false)
    @CsvBindByName(column = "calificacion")
    private String calificacion;

    @Column(name = "\"compresionMotor\"", nullable = false)
    @CsvBindByName(column = "compresion_motor")
    private String compresionMotor;

    @Column(name = "caja", nullable = false)
    @CsvBindByName(column = "caja")
    private String caja;

    @Column(name = "observaciones", nullable = false)
    @CsvBindByName(column = "observaciones")
    private String observaciones;

    @Column(name = "\"fechafirmaaprobacion\"", nullable = false)
    @CsvBindByName(column = "fecha_firma_aprobacion")
    private Date fechaFirmaAprobacion;

    @OneToOne
	@JoinColumn(name = "\"idArticulo\"", referencedColumnName = "id", nullable = false)
	private Articulo idArticulo;

    @Transient
    @CsvBindByName(column = "id_articulo")
    private Integer idArticulo1;
    

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

    public Articulo getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(Articulo idArticulo) {
        this.idArticulo = idArticulo;
    }

    public Integer getIdArticulo1() {
        return idArticulo1;
    }

    public void setIdArticulo1(Integer idArticulo1) {
        this.idArticulo1 = idArticulo1;
    }

    public Revision setValoresDiferentes(Revision registroAntiguo, Revision registroActualizar) {
        if (registroActualizar.getCalificacion() != null) {
            registroAntiguo.setCalificacion(registroActualizar.getCalificacion());
        }

        if (registroActualizar.getCompresionMotor() != null) {
            registroAntiguo.setCompresionMotor(registroActualizar.getCompresionMotor());
        }

        if (registroActualizar.getCaja() != null) {
            registroAntiguo.setCaja(registroActualizar.getCaja());
        }

        if (registroActualizar.getObservaciones() != null) {
            registroAntiguo.setObservaciones(registroActualizar.getObservaciones());
        }

        if (registroActualizar.getFechaFirmaAprobacion() != null) {
            registroAntiguo.setFechaFirmaAprobacion(registroActualizar.getFechaFirmaAprobacion());
        }

        return registroAntiguo;

    }
}
