package com.ec.prontiauto.dao;

import com.ec.prontiauto.abstracts.AbstractRequestDao;

public class PermisoRequestDao extends AbstractRequestDao {
	// Ejemplo de alguna propiedad
	// private String nombres;
	// private Integer idNombreRelacion;
	private String nombre;

	public PermisoRequestDao() {
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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
}
