package com.ec.prontiauto.dao;

import java.util.Date;

import com.ec.prontiauto.abstracts.AbstractResponseDao;

public class ArticuloResponseDao extends AbstractResponseDao {
	private String placa;
	private String chasis;
	private String marca;
	private String modelo;
	private String anio;
	private String color;
	private String observacion;
	private String estado;
	private String ubicacionFisica;
	private Date fechaAdjudicacion;

	public ArticuloResponseDao() {
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getChasis() {
		return chasis;
	}

	public void setChasis(String chasis) {
		this.chasis = chasis;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getUbicacionFisica() {
		return ubicacionFisica;
	}

	public void setUbicacionFisica(String ubicacionFisica) {
		this.ubicacionFisica = ubicacionFisica;
	}

	public Date getFechaAdjudicacion() {
		return fechaAdjudicacion;
	}

	public void setFechaAdjudicacion(Date fechaAdjudicacion) {
		this.fechaAdjudicacion = fechaAdjudicacion;
	}

}
