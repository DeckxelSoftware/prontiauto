package com.ec.prontiauto.entidad;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "rol_permiso", uniqueConstraints = @UniqueConstraint(columnNames = {  "\"idRol\"", "\"idPermiso\"" }))
public class RolPermiso extends AbstractEntities {
	@ManyToOne
	@JoinColumn(name = "\"idRol\"", referencedColumnName = "id", nullable = false)
	private Rol idRol;

	@ManyToOne
	@JoinColumn(name = "\"idPermiso\"", referencedColumnName = "id", nullable = false)
	private Permiso idPermiso;

	@Transient
	@CsvBindByName(column = "id_rol")
	private Integer idRol1;

	@Transient
	@CsvBindByName(column = "id_permiso")
	private Integer idPermiso1;

	public RolPermiso() {
	}

	public Rol getIdRol() {
		return idRol;
	}

	public void setIdRol(Rol idRol) {
		this.idRol = idRol;
	}

	public Integer getIdRol1() {
		return idRol1;
	}

	public void setIdRol1(Integer idRol1) {
		this.idRol1 = idRol1;
	}

	public Integer getIdPermiso1() {
		return idPermiso1;
	}

	public void setIdPermiso1(Integer idPermiso1) {
		this.idPermiso1 = idPermiso1;
	}

	public Permiso getIdPermiso() {
		return idPermiso;
	}

	public void setIdPermiso(Permiso idPermiso) {
		this.idPermiso = idPermiso;
	}

	public RolPermiso setValoresDiferentes(RolPermiso registroAntiguo, RolPermiso registroActualizar) {
		if (registroActualizar.getIdRol().getId() != null) {
			registroAntiguo.setIdRol(registroActualizar.getIdRol());
		}
		if (registroActualizar.getIdPermiso().getId() != null) {
			registroAntiguo.setIdPermiso(registroActualizar.getIdPermiso());
		}
		if (registroActualizar.getSisHabilitado() != null) {
			registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
		}
		return registroAntiguo;
	}
}
