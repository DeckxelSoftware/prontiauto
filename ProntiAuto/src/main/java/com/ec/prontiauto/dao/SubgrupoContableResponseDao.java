package com.ec.prontiauto.dao;

import java.util.List;

import com.ec.prontiauto.abstracts.AbstractResponseDao;

public class SubgrupoContableResponseDao extends AbstractResponseDao {

	private String nombre;
	private String descripcion;
	private GrupoContableResponseDao idGrupoContable;
	private List<AsientoContableCabeceraResponseDao> AsientoContableCabeceraCollection;


	public SubgrupoContableResponseDao() {

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

	public GrupoContableResponseDao getIdGrupoContable() {
		return idGrupoContable;
	}

	public void setIdGrupoContable(GrupoContableResponseDao idGrupoContable) {
		this.idGrupoContable = idGrupoContable;
	}

	public List<AsientoContableCabeceraResponseDao> getAsientoContableCabeceraCollection() {
		return AsientoContableCabeceraCollection;
	}

	public void setAsientoContableCabeceraCollection(
			List<AsientoContableCabeceraResponseDao> asientoContableCabeceraCollection) {
		AsientoContableCabeceraCollection = asientoContableCabeceraCollection;
	}


}
