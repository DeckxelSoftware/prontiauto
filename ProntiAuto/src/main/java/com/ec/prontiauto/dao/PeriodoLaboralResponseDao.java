package com.ec.prontiauto.dao;

import java.sql.Date;
import java.util.List;

import com.ec.prontiauto.abstracts.AbstractResponseDao;

public class PeriodoLaboralResponseDao extends AbstractResponseDao {

	private Integer anio;
	private String mes;
	private Date fechaInicio;
	private Date fechaFin;
	private String activo;
	private List<DetalleNovedadRolPagoResponseDao> detalleNovedadRolPagoCollection;

	public PeriodoLaboralResponseDao() {

	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
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

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}

	public List<DetalleNovedadRolPagoResponseDao> getDetalleNovedadRolPagoCollection() {
		return detalleNovedadRolPagoCollection;
	}

	public void setDetalleNovedadRolPagoCollection(
			List<DetalleNovedadRolPagoResponseDao> detalleNovedadRolPagoCollection) {
		this.detalleNovedadRolPagoCollection = detalleNovedadRolPagoCollection;
	}

}
