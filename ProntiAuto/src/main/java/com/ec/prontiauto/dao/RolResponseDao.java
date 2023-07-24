package com.ec.prontiauto.dao;

import java.util.List;

import com.ec.prontiauto.abstracts.AbstractResponseDao;

public class RolResponseDao extends AbstractResponseDao {
	// Ejemplo de alguna propiedad
	// private String nombres;
	// Papa a hijos
	// private List<NombreHijoResponseDao> nombreHijoCollection;
	// Hijos a papa
	// private Integer idNombreRelacion;
	private String nombre;
	private List<RolPermisoResponseDao> rolPermisoCollection;
	private List<RolUsuarioResponseDao> rolUsuarioCollection;

	public RolResponseDao() {
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<RolPermisoResponseDao> getRolPermisoCollection() {
		return rolPermisoCollection;
	}

	public void setRolPermisoCollection(List<RolPermisoResponseDao> rolPermisoCollection) {
		this.rolPermisoCollection = rolPermisoCollection;
	}

	public List<RolUsuarioResponseDao> getRolUsuarioCollection() {
		return rolUsuarioCollection;
	}

	public void setRolUsuarioCollection(List<RolUsuarioResponseDao> rolUsuarioCollection) {
		this.rolUsuarioCollection = rolUsuarioCollection;
	}

}
