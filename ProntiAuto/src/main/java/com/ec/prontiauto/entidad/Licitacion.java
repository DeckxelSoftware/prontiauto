package com.ec.prontiauto.entidad;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

@Entity
@Table(name = "licitacion")
public class Licitacion extends AbstractEntities {

	@Column(name = "\"valorOferta\"", precision = 12, scale = 4, nullable = false)
	@CsvBindByName(column = "valor_oferta")
	private Float valorOferta;

	@Column(name = "\"porcentajeOferta\"", precision = 12, scale = 4, nullable = false)
	@CsvBindByName(column = "porcentaje_oferta")
	private Float porcentajeOferta;

	@Column(name = "\"fechaOferta\"", nullable = false)
	@CsvDate(value = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	@CsvBindByName(column = "fecha_oferta")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaOferta;

	@Column(name = "\"observacion\"", length = 255)
	@CsvBindByName(column = "observacion")
	private String observacion;

	@Column(name = "\"estado\"", length = 255, nullable = false)
	@CsvBindByName(column = "estado")
	private String estado;

	@Column(name = "\"planSeleccionado\"", length = 255, nullable = false)
	@CsvBindByName(column = "plan_seleccionado")
	private String planSeleccionado;

	@Column(name = "\"precioPlan\"", precision = 12, scale = 4, nullable = false)
	@CsvBindByName(column = "precio_plan")
	private Float precioPlan;

	@Column(name = "\"totalMontoCobrado\"", precision = 12, scale = 4, nullable = false)
	@CsvBindByName(column = "total_monto_cobrado")
	private Float totalMontoCobrado;

	@Column(name = "\"aprobadoPorGerencia\"", length = 1, nullable = false)
	@CsvBindByName(column = "aprobado_por_gerencia")
	private String aprobadoPorGerencia;

	@ManyToOne
	@JoinColumn(name = "\"idContrato\"", referencedColumnName = "id")
	private Contrato idContrato;

	@Transient
	@CsvBindByName(column = "id_contrato")
	private Integer idContrato1;

	public Licitacion() {

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

	public Contrato getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Contrato idContrato) {
		this.idContrato = idContrato;
	}

	public Integer getIdContrato1() {
		return idContrato1;
	}

	public void setIdContrato1(Integer idContrato1) {
		this.idContrato1 = idContrato1;
	}

	public Licitacion setValoresDiferentes(Licitacion registroAntiguo,
			Licitacion registroActualizar) {
		if (registroActualizar.getValorOferta() != null) {
			registroAntiguo.setValorOferta(registroActualizar.getValorOferta());
		}
		if (registroActualizar.getPorcentajeOferta() != null) {
			registroAntiguo.setPorcentajeOferta(registroActualizar.getPorcentajeOferta());
		}
		if (registroActualizar.getFechaOferta() != null) {
			registroAntiguo.setFechaOferta(registroActualizar.getFechaOferta());
		}
		if (registroActualizar.getObservacion() != null) {
			registroAntiguo.setObservacion(registroActualizar.getObservacion());
		}
		if (registroActualizar.getEstado() != null) {
			registroAntiguo.setEstado(registroActualizar.getEstado());
		}
		if (registroActualizar.getPlanSeleccionado() != null) {
			registroAntiguo.setPlanSeleccionado(registroActualizar.getPlanSeleccionado());
		}
		if (registroActualizar.getPrecioPlan() != null) {
			registroAntiguo.setPrecioPlan(registroActualizar.getPrecioPlan());
		}
		if (registroActualizar.getTotalMontoCobrado() != null) {
			registroAntiguo.setTotalMontoCobrado(registroActualizar.getTotalMontoCobrado());
		}
		if (registroActualizar.getaprobadoPorGerencia() != null) {
			registroAntiguo.setaprobadoPorGerencia(registroActualizar.getaprobadoPorGerencia());
		}
		if (registroActualizar.getSisHabilitado() != null) {
			registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
		}
		if (registroActualizar.getIdContrato() != null && registroActualizar.getIdContrato().getId() != null) {
			registroAntiguo.setIdContrato(registroActualizar.getIdContrato());
		}
		return registroAntiguo;
	}

}
