package com.ec.prontiauto.dao;

import java.util.List;

import com.ec.prontiauto.abstracts.AbstractResponseDao;

public class PermisoResponseDao extends AbstractResponseDao {
	// Ejemplo de alguna propiedad
	// private String nombres;
	// private Integer idNombreRelacion;
	private String nombre;
	private List<RolPermisoResponseDao> rolPermisoCollection;

	public PermisoResponseDao() {
	}

	/*
	 * //Ejemplo setter getters propiedades
	 * public String getNombres() {
	 * return nombres;
	 * }
	 * 
	 * public void setNombres(String nombres) {
	 * this.nombres = nombres;
	 * }
	 */

	/*
	 * //Ejemplo setter getters relacion
	 * public Integer getIdNombreRelacion() {
	 * return idNombreRelacion;
	 * }
	 * 
	 * public void setIdNombreRelacion(Integer idNombreRelacion) {
	 * this.idNombreRelacion = idNombreRelacion;
	 * }
	 */

	public List<RolPermisoResponseDao> getRolPermisoCollection() {
		return rolPermisoCollection;
	}

	public void setRolPermisoCollection(List<RolPermisoResponseDao> rolPermisoCollection) {
		this.rolPermisoCollection = rolPermisoCollection;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
