package com.ec.prontiauto.entidad;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "area")
public class Area extends AbstractEntities {

    @Column(name = "nombre", length = 255, nullable = false)
    @CsvBindByName(column = "nombre")
    private String nombre;

    @JsonIgnore
    @OneToMany(mappedBy = "idArea")
    private List<Cargo> cargoCollection;

    public Area() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Cargo> getCargoCollection() {
		return cargoCollection;
	}

	public void setCargoCollection(List<Cargo> cargoCollection) {
		this.cargoCollection = cargoCollection;
	}

	public Area setValoresDiferentes(Area registroAntiguo, Area registroActualizar) {
        if (registroActualizar.getNombre() != null) {
            registroAntiguo.setNombre(registroActualizar.getNombre());
        }
        if (registroActualizar.getSisHabilitado() != null) {
            registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
        }
        return registroAntiguo;
    }
}
