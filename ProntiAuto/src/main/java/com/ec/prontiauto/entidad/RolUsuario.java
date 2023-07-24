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
@Table(name = "rol_usuario", uniqueConstraints = @UniqueConstraint(columnNames = { "\"idRol\"", "\"idUsuario\"" }))
public class RolUsuario extends AbstractEntities {
	@ManyToOne
	@JoinColumn(name = "\"idRol\"", referencedColumnName = "id", nullable = false)
	private Rol idRol;

	@ManyToOne
	@JoinColumn(name = "\"idUsuario\"", referencedColumnName = "id", nullable = false)
	private Usuario idUsuario;

	@Transient
	@CsvBindByName(column = "id_rol")
	private Integer idRol1;

	@Transient
	@CsvBindByName(column = "id_usuario")
	private Integer idUsuario1;

	public RolUsuario() {
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

	public Integer getIdUsuario1() {
		return idUsuario1;
	}

	public void setIdUsuario1(Integer idUsuario1) {
		this.idUsuario1 = idUsuario1;
	}

	public Usuario getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Usuario idUsuario) {
		this.idUsuario = idUsuario;
	}

	public RolUsuario setValoresDiferentes(RolUsuario registroAntiguo, RolUsuario registroActualizar) {
		if (registroActualizar.getIdRol().getId() != null) {
			registroAntiguo.setIdRol(registroActualizar.getIdRol());
		}
		if (registroActualizar.getIdUsuario().getId() != null) {
			registroAntiguo.setIdUsuario(registroActualizar.getIdUsuario());
		}
		if (registroActualizar.getSisHabilitado() != null) {
			registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
		}
		return registroAntiguo;
	}
}
