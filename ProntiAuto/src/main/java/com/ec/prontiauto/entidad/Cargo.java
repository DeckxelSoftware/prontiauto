package com.ec.prontiauto.entidad;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "Cargo")
public class Cargo extends AbstractEntities {

	
	@Column(name = "nombre", nullable = false, length = 255, unique = true)
	@CsvBindByName(column = "nombre")
	private String nombre;

	@Column(name = "sueldo", nullable = true)
	@CsvBindByName(column = "sueldo")
	private Float sueldo;

	@JsonIgnore
	@OneToMany(mappedBy = "idCargo")
	private List<HistorialLaboral> historialLaboralCollection;
	
	@ManyToOne
	@JoinColumn(name = "\"idArea\"", referencedColumnName = "id", nullable = false)
	private Area idArea;

	@Transient
	@CsvBindByName(column = "id_area")
	private Integer idArea1;
	
	public Cargo() {
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Float getSueldo() {
		return sueldo;
	}

	public void setSueldo(Float sueldo) {
		this.sueldo = sueldo;
	}

	public List<HistorialLaboral> getHistorialLaboralCollection() {
		return historialLaboralCollection;
	}

	public void setHistorialLaboralCollection(List<HistorialLaboral> historialLaboralCollection) {
		this.historialLaboralCollection = historialLaboralCollection;
	}


	public Area getIdArea() {
		return idArea;
	}

	public void setIdArea(Area idArea) {
		this.idArea = idArea;
	}

	public Integer getIdArea1() {
		return idArea1;
	}

	public void setIdArea1(Integer idArea1) {
		this.idArea1 = idArea1;
	}

	public Cargo setValoresDiferentes(Cargo registroAntiguo, Cargo registroActualizar) {
		
		if (registroActualizar.getNombre() != null) {
			registroAntiguo.setNombre(registroActualizar.getNombre());
		}
		if (registroActualizar.getSueldo() != null) {
			registroAntiguo.setSueldo(registroActualizar.getSueldo());
		}
		if (registroActualizar.getSisHabilitado() != null) {
			registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
		}
		if (registroActualizar.getIdArea().getId() != null) {
			registroAntiguo.setIdArea(registroActualizar.getIdArea());
		}
		return registroAntiguo;
	}

}
