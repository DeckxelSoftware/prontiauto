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
@Table(name = "permiso")
public class Permiso extends AbstractEntities {

	@Column(name = "nombre", nullable = false, length = 50)
	@CsvBindByName(column = "nombre")
	private String nombre;

	@JsonIgnore
	@OneToMany(mappedBy = "idPermiso")
	private List<RolPermiso> rolPermisoCollection;

	public Permiso() {
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombres) {
		this.nombre = nombres;
	}

	public List<RolPermiso> getRolPermisoCollection() {
		return rolPermisoCollection;
	}

	public void setRolPermisoCollection(List<RolPermiso> rolPermisoCollection) {
		this.rolPermisoCollection = rolPermisoCollection;
	}

	public Permiso setValoresDiferentes(Permiso registroAntiguo, Permiso registroActualizar) {

		if (registroActualizar.getNombre() != null) {
			registroAntiguo.setNombre(registroActualizar.getNombre());
		}
		if (registroActualizar.getSisHabilitado() != null) {
			registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
		}
		return registroAntiguo;
	}

}
