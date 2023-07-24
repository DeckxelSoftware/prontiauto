package com.ec.prontiauto.dao;

import java.util.Date;

import com.ec.prontiauto.abstracts.AbstractResponseDao;

public class LicitacionResponseDao extends AbstractResponseDao {

	private Float valorOferta;
	private Float porcentajeOferta;
	private Date fechaOferta;
	private String observacion;
	private String estado;
	private String planSeleccionado;
	private Float precioPlan;
	private Float totalMontoCobrado;
	private String aprobadoPorGerencia;
	
	private ContratoResponseDao idContrato;

	public LicitacionResponseDao() {

	}

	public Float getValorOferta() {
		return valorOferta;
	}

	public void setValorOferta(Float valorOferta) {
		this.valorOferta = valorOferta;
	}

	public Float getPorcentajeOferta() {
		return porcentajeOferta;
	}

	public void setPorcentajeOferta(Float porcentajeOferta) {
		this.porcentajeOferta = porcentajeOferta;
	}

	public Date getFechaOferta() {
		return fechaOferta;
	}

	public void setFechaOferta(Date fechaOferta) {
		this.fechaOferta = fechaOferta;
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

	public String getPlanSeleccionado() {
		return planSeleccionado;
	}

	public void setPlanSeleccionado(String planSeleccionado) {
		this.planSeleccionado = planSeleccionado;
	}

	public Float getPrecioPlan() {
		return precioPlan;
	}

	public void setPrecioPlan(Float precioPlan) {
		this.precioPlan = precioPlan;
	}

	public Float getTotalMontoCobrado() {
		return totalMontoCobrado;
	}

	public void setTotalMontoCobrado(Float totalMontoCobrado) {
		this.totalMontoCobrado = totalMontoCobrado;
	}

	public String getaprobadoPorGerencia() {
		return aprobadoPorGerencia;
	}

	public void setaprobadoPorGerencia(String aprobadoPorGerencia) {
		this.aprobadoPorGerencia = aprobadoPorGerencia;
	}

	public ContratoResponseDao getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(ContratoResponseDao idContrato) {
		this.idContrato = idContrato;
	}
	
}
