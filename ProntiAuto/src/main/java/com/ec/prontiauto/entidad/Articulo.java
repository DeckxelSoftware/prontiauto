package com.ec.prontiauto.entidad;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

@Entity
@Table(name = "articulo")
public class Articulo extends AbstractEntities {
    @Column(name = "placa", length = 255, unique = true, nullable = false)
    @CsvBindByName(column = "placa")
    private String placa;

    @Column(name = "chasis", length = 255, unique = true, nullable = false)
    @CsvBindByName(column = "chasis")
    private String chasis;

    @Column(name = "marca", length = 255, nullable = false)
    @CsvBindByName(column = "marca")
    private String marca;

    @Column(name = "modelo", length = 255, nullable = false)
    @CsvBindByName(column = "modelo")
    private String modelo;

    @Column(name = "anio", length = 255, nullable = false)
    @CsvBindByName(column = "anio")
    private String anio;

    @Column(name = "color", length = 255, nullable = false)
    @CsvBindByName(column = "color")
    private String color;

    @Column(name = "observacion", length = 255, nullable = false)
    @CsvBindByName(column = "observacion")
    private String observacion;

    @Column(name = "estado", length = 2255, nullable = false)
    @CsvBindByName(column = "estado")
    private String estado;

    @Column(name = "\"ubicacionFisica\"", length = 255, nullable = false)
    @CsvBindByName(column = "ubicacion_fisica")
    private String ubicacionFisica;

    @Column(name = "\"fechaAdjudicacion\"")
    @CsvDate(value = "yyyy-MM-dd")
    @CsvBindByName(column = "fecha_adjudicacion")
    private Date fechaAdjudicacion;

    @OneToOne(mappedBy = "idArticulo")
    private OrdenCompra idOrdenCompra;

    @OneToOne(mappedBy = "idArticulo")
    private Revision idRevision;

    public Articulo() {
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getChasis() {
        return chasis;
    }

    public void setChasis(String chasis) {
        this.chasis = chasis;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUbicacionFisica() {
        return ubicacionFisica;
    }

    public void setUbicacionFisica(String ubicacionFisica) {
        this.ubicacionFisica = ubicacionFisica;
    }

    public OrdenCompra getIdOrdenCompra() {
        return idOrdenCompra;
    }

    public void setIdOrdenCompra(OrdenCompra idOrdenCompra) {
        this.idOrdenCompra = idOrdenCompra;
    }

    public Revision getIdRevision() {
        return idRevision;
    }

    public void setIdRevision(Revision idRevision) {
        this.idRevision = idRevision;
    }

    public Articulo setValoresDiferentes(Articulo registroAntiguo, Articulo registroActualizar) {
        if (registroActualizar.getPlaca() != null) {
            registroAntiguo.setPlaca(registroActualizar.getPlaca());
        }
        if (registroActualizar.getChasis() != null) {
            registroAntiguo.setChasis(registroActualizar.getChasis());
        }
        if (registroActualizar.getMarca() != null) {
            registroAntiguo.setMarca(registroActualizar.getMarca());
        }
        if (registroActualizar.getModelo() != null) {
            registroAntiguo.setModelo(registroActualizar.getModelo());
        }
        if (registroActualizar.getAnio() != null) {
            registroAntiguo.setAnio(registroActualizar.getAnio());
        }
        if (registroActualizar.getColor() != null) {
            registroAntiguo.setColor(registroActualizar.getColor());
        }
        if (registroActualizar.getObservacion() != null) {
            registroAntiguo.setObservacion(registroActualizar.getObservacion());
        }
        if (registroActualizar.getEstado() != null) {
            registroAntiguo.setEstado(registroActualizar.getEstado());
        }
        if (registroActualizar.getUbicacionFisica() != null) {
            registroAntiguo.setUbicacionFisica(registroActualizar.getUbicacionFisica());
        }
        if (registroActualizar.getSisHabilitado() != null) {
            registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
        }
        if (registroActualizar.getFechaAdjudicacion() != null) {
            registroAntiguo.setFechaAdjudicacion(registroActualizar.getFechaAdjudicacion());
        }
        return registroAntiguo;
    }

    public Date getFechaAdjudicacion() {
        return fechaAdjudicacion;
    }

    public void setFechaAdjudicacion(Date fechaAdjudicacion) {
        this.fechaAdjudicacion = fechaAdjudicacion;
    }
}
