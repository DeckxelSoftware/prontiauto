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
@Table(name = "division_administrativa")
public class DivisionAdministrativa extends AbstractEntities {

	@Column(name = "\"nombreDivision\"", nullable = false, length = 255)
	@CsvBindByName(column = "nombre_division")
	private String nombreDivision;

	@JsonIgnore
	@OneToMany(mappedBy = "idDivisionAdministrativa")
	private List<HistorialLaboral> historialLaboralCollection;

	public DivisionAdministrativa() {
	}

	public String getNombreDivision() {
		return nombreDivision;
	}

	public void setNombreDivision(String nombreDivision) {
		this.nombreDivision = nombreDivision;
	}

	public List<HistorialLaboral> getHistorialLaboralCollection() {
		return historialLaboralCollection;
	}

	public void setHistorialLaboralCollection(List<HistorialLaboral> historialLaboralCollection) {
		this.historialLaboralCollection = historialLaboralCollection;
	}

	public DivisionAdministrativa setValoresDiferentes(DivisionAdministrativa registroAntiguo,
			DivisionAdministrativa registroActualizar) {
		
		if (registroActualizar.getNombreDivision() != null) {
			registroAntiguo.setNombreDivision(registroActualizar.getNombreDivision());
		}
		if (registroActualizar.getSisHabilitado() != null) {
			registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
		}
		return registroAntiguo;
	}

}
