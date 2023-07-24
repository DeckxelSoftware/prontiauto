package com.ec.prontiauto.dao;

import java.sql.Date;

import com.ec.prontiauto.abstracts.AbstractRequestDao;

public class MemorandumRequestDao extends AbstractRequestDao {

	private Date fecha ;
	private String motivo;
	private String observaciones;
	private Integer idTrabajador;

	public MemorandumRequestDao() {

	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Integer getIdTrabajador() {
		return idTrabajador;
	}

	public void setIdTrabajador(Integer idTrabajador) {
		this.idTrabajador = idTrabajador;
	}


}
