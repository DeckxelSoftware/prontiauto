package com.ec.prontiauto.entidad;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "rol")
public class Rol extends AbstractEntities {
	@Column(name = "nombre", nullable = false, length = 50)
	@CsvBindByName(column = "nombre")
	private String nombre;

	@OneToMany(mappedBy = "idRol")
	private List<RolPermiso> rolPermisoCollection;
	@OneToMany(mappedBy = "idRol")
	private List<RolUsuario> rolUsuarioCollection;

	public Rol() {
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<RolPermiso> getRolPermisoCollection() {
		return rolPermisoCollection;
	}

	public void setRolPermisoCollection(List<RolPermiso> rolPermisoCollection) {
		this.rolPermisoCollection = rolPermisoCollection;
	}

	public List<RolUsuario> getRolUsuarioCollection() {
		return rolUsuarioCollection;
	}

	public void setRolUsuarioCollection(List<RolUsuario> rolUsuarioCollection) {
		this.rolUsuarioCollection = rolUsuarioCollection;
	}

	public Rol setValoresDiferentes(Rol registroAntiguo, Rol registroActualizar) {
		if (registroActualizar.getNombre() != null) {
			registroAntiguo.setNombre(registroActualizar.getNombre());
		}
		if (registroActualizar.getSisHabilitado() != null) {
			registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
		}
		return registroAntiguo;
	}

}
