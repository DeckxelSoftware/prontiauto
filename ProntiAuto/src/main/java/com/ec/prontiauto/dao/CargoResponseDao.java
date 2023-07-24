package com.ec.prontiauto.dao;

import java.util.List;

import com.ec.prontiauto.abstracts.AbstractResponseDao;

public class CargoResponseDao extends AbstractResponseDao {

	private String nombre;
	private Float sueldo;

	private List<HistorialLaboralResponseDao> historialLaboralCollection;
	private AreaResponseDao idArea;

	public CargoResponseDao() {

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Float getSueldo() {
		return sueldo;
	}

	public void setSueldo(Float sueldo) {
		this.sueldo = sueldo;
	}

	public List<HistorialLaboralResponseDao> getHistorialLaboralCollection() {
		return historialLaboralCollection;
	}

	public void setHistorialLaboralCollection(List<HistorialLaboralResponseDao> historialLaboralCollection) {
		this.historialLaboralCollection = historialLaboralCollection;
	}

	public AreaResponseDao getIdArea() {
		return idArea;
	}

	public void setIdArea(AreaResponseDao idArea) {
		this.idArea = idArea;
	}

	
}
