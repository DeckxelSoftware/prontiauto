package com.ec.prontiauto.entidad;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "region")
public class Region extends AbstractEntities {

    @Column(name = "nombre", nullable = false, length = 255)
    @CsvBindByName(column = "nombre")
    private String nombre;

    @Column(name = "provincia", nullable = false, length = 255)
    @CsvBindByName(column = "provincia")
    private String provincia;

    @Column(name = "ciudad", nullable = false, length = 255)
    @CsvBindByName(column = "ciudad")
    private String ciudad;

    @OneToMany(mappedBy = "idRegion")
    private List<Agencia> agenciaCollection;

    public Region() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public List<Agencia> getAgenciaCollection() {
        return agenciaCollection;
    }

    public void setAgenciaCollection(List<Agencia> agenciaCollection) {
        this.agenciaCollection = agenciaCollection;
    }

    public Region setValoresDiferentes(Region registroAntiguo, Region registroActualizar) {

        if (registroActualizar.getNombre() != null) {
            registroAntiguo.setNombre(registroActualizar.getNombre());
        }

        if (registroActualizar.getProvincia() != null) {
            registroAntiguo.setProvincia(registroActualizar.getProvincia());
        }

        if (registroActualizar.getCiudad() != null) {
            registroAntiguo.setCiudad(registroActualizar.getCiudad());
        }

        if (registroActualizar.getSisHabilitado() != null) {
            registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
        }

        return registroAntiguo;

    }
}
