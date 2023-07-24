package com.ec.prontiauto.dao;

import com.ec.prontiauto.abstracts.AbstractRequestDao;

public class ListaValoresTipoRequestDao extends AbstractRequestDao {
	private String codigoPrimario;
	private String codigoSecundario;
	private String nombre;
	private String descripcion;

	public ListaValoresTipoRequestDao() {
	}

	public String getCodigoPrimario() {
		return codigoPrimario;
	}

	public void setCodigoPrimario(String codigoPrimario) {
		this.codigoPrimario = codigoPrimario;
	}

	public String getCodigoSecundario() {
		return codigoSecundario;
	}

	public void setCodigoSecundario(String codigoSecundario) {
		this.codigoSecundario = codigoSecundario;
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

}
