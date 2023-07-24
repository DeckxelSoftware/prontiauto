package com.ec.prontiauto.dao;

import java.sql.Date;

import com.ec.prontiauto.abstracts.AbstractResponseDao;

public class MemorandumResponseDao extends AbstractResponseDao {

	private Date fecha ;
	private String motivo;
	private String observaciones;
	private TrabajadorResponseDao idTrabajador;

	public MemorandumResponseDao() {

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

	public TrabajadorResponseDao getIdTrabajador() {
		return idTrabajador;
	}

	public void setIdTrabajador(TrabajadorResponseDao idTrabajador) {
		this.idTrabajador = idTrabajador;
	}

}
