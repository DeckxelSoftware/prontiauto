package com.ec.prontiauto.dao;


import java.sql.Date;
import java.util.List;

import com.ec.prontiauto.abstracts.AbstractResponseDao;

public class PeriodoContableResponseDao extends AbstractResponseDao {

	private Date fechaInicio;
	private Date fechaFin;
	private Integer anio;
	private String esPeriodoActual;
	private List<CuentaContableResponseDao> CuentcontableCollection;


	public PeriodoContableResponseDao() {

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

	public List<CuentaContableResponseDao> getCuentcontableCollection() {
		return CuentcontableCollection;
	}

	public void setCuentcontableCollection(List<CuentaContableResponseDao> cuentcontableCollection) {
		CuentcontableCollection = cuentcontableCollection;
	}

}
