package com.ec.prontiauto.dao;

import java.util.List;

import com.ec.prontiauto.abstracts.AbstractResponseDao;

public class GrupoContableResponseDao extends AbstractResponseDao {

	private String nombre;
	private String descripcion;
	private List<SubgrupoContableResponseDao> SubgrupoContableCollection;


	public GrupoContableResponseDao() {

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

	public List<SubgrupoContableResponseDao> getSubgrupoContableCollection() {
		return SubgrupoContableCollection;
	}

	public void setSubgrupoContableCollection(List<SubgrupoContableResponseDao> subgrupoContableCollection) {
		SubgrupoContableCollection = subgrupoContableCollection;
	}


}
