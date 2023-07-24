package com.ec.prontiauto.entidad;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

@Entity
@Table(name = "periodo_contable")
public class PeriodoContable extends AbstractEntities {

	@Column(name = "\"fechaInicio\"")
	@CsvDate(value = "yyyy-MM-dd")
	@CsvBindByName(column = "fecha_inicio")
	private Date fechaInicio;

	@Column(name = "\"fechaFin\"")
	@CsvDate(value = "yyyy-MM-dd")
	@CsvBindByName(column = "fecha_fin")
	private Date fechaFin;

	@Column(name = "anio", nullable = false, unique = true)
	@CsvBindByName(column = "anio")
	private Integer anio;

	@Column(name = "\"esPeriodoActual\"", nullable = false)
	@CsvBindByName(column = "es_periodo_actual")
	private String esPeriodoActual;

	@JsonIgnore
	@OneToMany(mappedBy = "idPeriodoContable")
	private List<CuentaContable> cuentaContableCollection;

	public PeriodoContable() {
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

	public List<CuentaContable> getCuentaContableCollection() {
		return cuentaContableCollection;
	}

	public void setCuentaContableCollection(List<CuentaContable> cuentaContableCollection) {
		this.cuentaContableCollection = cuentaContableCollection;
	}

	public PeriodoContable setValoresDiferentes(PeriodoContable registroAntiguo, PeriodoContable registroActualizar) {

		if (registroActualizar.getFechaInicio() != null) {
			registroAntiguo.setFechaInicio(registroActualizar.getFechaInicio());
		}
		if (registroActualizar.getFechaFin() != null) {
			registroAntiguo.setFechaFin(registroActualizar.getFechaFin());
		}
		if (registroActualizar.getAnio() != null) {
			registroAntiguo.setAnio(registroActualizar.getAnio());
		}
		if (registroActualizar.getEsPeriodoActual() != null) {
			registroAntiguo.setEsPeriodoActual(registroActualizar.getEsPeriodoActual());
		}
		if (registroActualizar.getSisHabilitado() != null) {
			registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
		}
		return registroAntiguo;
	}

}
