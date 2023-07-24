package com.ec.prontiauto.entidad;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

@Entity
@Table(name = "memorandum")
public class Memorandum extends AbstractEntities {

	@Column(name = "fecha")
	@CsvDate(value = "yyyy-MM-dd")
	@CsvBindByName(column = "fecha")
	private Date fecha ;
	
	@Column(name = "motivo", nullable = false, length = 255)
	@CsvBindByName(column = "motivo")
	private String motivo;

	@Column(name = "observaciones", nullable = false, length = 255)
	@CsvBindByName(column = "observaciones")
	private String observaciones;
	
	@ManyToOne
	@JoinColumn(name = "\"idTrabajador\"", referencedColumnName = "id")
	private Trabajador idTrabajador;

	@Transient
	@CsvBindByName(column = "id_trabajador")
	private Integer idTrabajador1;

	public Memorandum() {
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
	
	public Trabajador getIdTrabajador() {
		return idTrabajador;
	}

	public void setIdTrabajador(Trabajador idTrabajador) {
		this.idTrabajador = idTrabajador;
	}

	public Integer getIdTrabajador1() {
		return idTrabajador1;
	}

	public void setIdTrabajador1(Integer idTrabajador1) {
		this.idTrabajador1 = idTrabajador1;
	}

	public Memorandum setValoresDiferentes(Memorandum registroAntiguo, Memorandum registroActualizar) {
		if (registroActualizar.getFecha() != null) {
			registroAntiguo.setFecha(registroActualizar.getFecha());
		}
		if (registroActualizar.getMotivo() != null) {
			registroAntiguo.setMotivo(registroActualizar.getMotivo());
		}
		if (registroActualizar.getObservaciones() != null) {
			registroAntiguo.setObservaciones(registroActualizar.getObservaciones());
		}
		if (registroActualizar.getSisHabilitado() != null) {
			registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
		}
		if (registroActualizar.getIdTrabajador() != null
				&& registroActualizar.getIdTrabajador().getId() != null) {
			registroAntiguo.setIdTrabajador(registroActualizar.getIdTrabajador());
		}
		return registroAntiguo;
	}

}