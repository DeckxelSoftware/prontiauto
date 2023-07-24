package com.ec.prontiauto.dao;

import com.ec.prontiauto.abstracts.AbstractRequestDao;

public class SubgrupoContableRequestDao extends AbstractRequestDao {

	private String nombre;
	private String descripcion;
	private Integer idGrupoContable;

	public SubgrupoContableRequestDao() {

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getIdGrupoContable() {
		return idGrupoContable;
	}

	public void setIdGrupoContable(Integer idGrupoContable) {
		this.idGrupoContable = idGrupoContable;
	}

	
}
