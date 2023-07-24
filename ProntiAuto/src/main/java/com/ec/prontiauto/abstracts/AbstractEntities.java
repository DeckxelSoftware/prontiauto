package com.ec.prontiauto.abstracts;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.ec.prontiauto.dao.ArchivoRequestDao;
import com.opencsv.bean.CsvBindByName;

@MappedSuperclass
public abstract class AbstractEntities implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @CreatedDate
    @Column(name = "\"sisCreado\"")
    private Date sisCreado;

    @LastModifiedDate
    @Column(name = "\"sisActualizado\"")
    private Date sisActualizado;

    @Column(name = "\"sisHabilitado\"", nullable = false, length = 1)
    @CsvBindByName(column = "sis_habilitado")
    private String sisHabilitado;

    @Transient
    private ArchivoRequestDao sisArchivo;
    @Transient
    private ArchivoRequestDao sisImagen;

    @Transient
    private Boolean onlyChildrenData;

    @Transient
    private Boolean updateEntity;

    @PrePersist
    protected void prePersist() {
        if (this.sisCreado == null)
            sisCreado = new Date();
        if (this.sisActualizado == null)
            sisActualizado = new Date();
    }

    @PreUpdate
    protected void preUpdate() {
        this.sisActualizado = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSisHabilitado() {
        return sisHabilitado;
    }

    public void setSisHabilitado(String sisHabilitado) {
        this.sisHabilitado = sisHabilitado;
    }

    public Date getSisCreado() {
        return sisCreado;
    }

    public void setSisCreado(Date sisCreado) {
        this.sisCreado = sisCreado;
    }

    public Date getSisActualizado() {
        return sisActualizado;
    }

    public void setSisActualizado(Date sisActualizado) {
        this.sisActualizado = sisActualizado;
    }

    public ArchivoRequestDao getSisArchivo() {
        return sisArchivo;
    }

    public void setSisArchivo(ArchivoRequestDao sisArchivo) {
        this.sisArchivo = sisArchivo;
    }

    public ArchivoRequestDao getSisImagen() {
        return sisImagen;
    }

    public void setSisImagen(ArchivoRequestDao sisImagen) {
        this.sisImagen = sisImagen;
    }

    public Boolean getOnlyChildrenData() {
        return onlyChildrenData;
    }

    public void setOnlyChildrenData(Boolean onlyChildrenData) {
        this.onlyChildrenData = onlyChildrenData;
    }

    public Boolean getUpdateEntity() {
        return updateEntity;
    }

    public void setUpdateEntity(Boolean updateEntity) {
        this.updateEntity = updateEntity;
    }
}
