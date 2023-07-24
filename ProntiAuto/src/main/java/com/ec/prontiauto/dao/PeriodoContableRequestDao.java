package com.ec.prontiauto.dao;


import java.sql.Date;

import com.ec.prontiauto.abstracts.AbstractRequestDao;

public class PeriodoContableRequestDao extends AbstractRequestDao {

	private Date fechaInicio;
	private Date fechaFin;
	private Integer anio;
	private String esPeriodoActual;
	
	public PeriodoContableRequestDao() {

	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public String getEsPeriodoActual() {
		return esPeriodoActual;
	}

	public void setEsPeriodoActual(String esPeriodoActual) {
		this.esPeriodoActual = esPeriodoActual;
	}

}
