package com.ec.prontiauto.dao;

import java.util.List;

import com.ec.prontiauto.abstracts.AbstractResponseDao;

public class ListaValoresTipoResponseDao extends AbstractResponseDao {
	private String codigoPrimario;
	private String codigoSecundario;
	private String nombre;
	private String descripcion;
	private List<ListaValoresDetalleResponseDao> listaValorDetalleCollection;

	public ListaValoresTipoResponseDao() {
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

	public List<ListaValoresDetalleResponseDao> getListaValorDetalleCollection() {
		return listaValorDetalleCollection;
	}

	public void setListaValorDetalleCollection(List<ListaValoresDetalleResponseDao> listaValorDetalleCollection) {
		this.listaValorDetalleCollection = listaValorDetalleCollection;
	}

}
