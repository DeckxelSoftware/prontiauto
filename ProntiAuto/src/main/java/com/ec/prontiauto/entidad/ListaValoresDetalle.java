package com.ec.prontiauto.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "lista_valores_detalle")
public class ListaValoresDetalle extends AbstractEntities {

	@Column(name = "\"codigoPrimario\"", nullable = false, length = 255, unique = true)
	@CsvBindByName(column = "codigo_primario")
	private String codigoPrimario;

	@Column(name = "\"codigoSecundario\"", nullable = false, unique = true, length = 255)
	@CsvBindByName(column = "codigo_secundario")
	private String codigoSecundario;

	@Column(name = "nombre", nullable = false, length = 50)
	@CsvBindByName(column = "nombre")
	private String nombre;

	@Column(name = "descripcion", length = 2255)
	@CsvBindByName(column = "descripcion")
	private String descripcion;

	@ManyToOne
	@JoinColumn(name = "\"idListaValoresTipo\"", referencedColumnName = "id", nullable = false)
	private ListaValoresTipo idListaValoresTipo;

	@CsvBindByName(column = "id_lista_valores_tipo")
	private Integer idListaValoresTipo1;

	public ListaValoresDetalle() {
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

	public ListaValoresTipo getIdListaValoresTipo() {
		return idListaValoresTipo;
	}

	public void setIdListaValoresTipo(ListaValoresTipo idListaValoresTipo) {
		this.idListaValoresTipo = idListaValoresTipo;
	}

	public Integer getIdListaValoresTipo1() {
		return idListaValoresTipo1;
	}

	public void setIdListaValoresTipo1(Integer idListaValoresTipo1) {
		this.idListaValoresTipo1 = idListaValoresTipo1;
	}

	public ListaValoresDetalle setValoresDiferentes(ListaValoresDetalle registroAntiguo,
			ListaValoresDetalle registroActualizar) {
		if (registroActualizar.getCodigoPrimario() != null) {
			registroAntiguo.setCodigoPrimario(registroActualizar.getCodigoPrimario());
		}
		if (registroActualizar.getCodigoSecundario() != null) {
			registroAntiguo.setCodigoSecundario(registroActualizar.getCodigoSecundario());
		}
		if (registroActualizar.getNombre() != null) {
			registroAntiguo.setNombre(registroActualizar.getNombre());
		}
		if (registroActualizar.getDescripcion() != null) {
			registroAntiguo.setDescripcion(registroActualizar.getDescripcion());
		}
		if (registroActualizar.getSisHabilitado() != null) {
			registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
		}
		if (registroActualizar.getIdListaValoresTipo().getId() != null) {
			registroAntiguo.setIdListaValoresTipo(registroActualizar.getIdListaValoresTipo());
		}
		return registroAntiguo;
	}

}
