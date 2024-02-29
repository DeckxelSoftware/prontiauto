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
@Table(name = "periodo_laboral")
public class PeriodoLaboral extends AbstractEntities {

	@Column(name = "\"anio\"", nullable = false)
	@CsvBindByName(column = "anio")
	private Integer anio;

	@Column(name = "\"mes\"", nullable = false, length = 255)
	@CsvBindByName(column = "mes")
	private String mes;

	@Column(name = "\"fechaInicio\"")
	@CsvDate(value = "yyyy-MM-dd")
	@CsvBindByName(column = "fecha_inicio")

	private Date fechaInicio;

	@Column(name = "\"fechaFin\"")
	@CsvDate(value = "yyyy-MM-dd")
	@CsvBindByName(column = "fecha_fin")

	private Date fechaFin;

	@Column(name = "\"activo\"", nullable = false, length = 255)
	@CsvBindByName(column = "activo")
	private String activo;

	@OneToMany(mappedBy = "idPeriodoLaboral")
	private List<DetalleNovedadRolPago> detalleNovedadRolPagoCollection;
	
	@JsonIgnore
	@OneToMany(mappedBy = "idPeriodoLaboral")
	private List<RolPago> rolPagoCollection;

	@OneToMany(mappedBy = "idPeriodoLaboral")
	private List<PagosDos> pagosDosCollection;

	@JsonIgnore
	@OneToMany(mappedBy = "idPeriodoLaboral")
	private List<HistoricoRol> historicoRolCollection;

	public PeriodoLaboral() {
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

	public List<DetalleNovedadRolPago> getDetalleNovedadRolPagoCollection() {
		return detalleNovedadRolPagoCollection;
	}

	public void setDetalleNovedadRolPagoCollection(List<DetalleNovedadRolPago> detalleNovedadRolPagoCollection) {
		this.detalleNovedadRolPagoCollection = detalleNovedadRolPagoCollection;
	}

	public List<RolPago> getRolPagoCollection() {
		return rolPagoCollection;
	}

	public void setRolPagoCollection(List<RolPago> rolPagoCollection) {
		this.rolPagoCollection = rolPagoCollection;
	}

	public PeriodoLaboral setValoresDiferentes(PeriodoLaboral registroAntiguo, PeriodoLaboral registroActualizar) {

		if (registroActualizar.getAnio() != null) {
			registroAntiguo.setAnio(registroActualizar.getAnio());
		}
		if (registroActualizar.getMes() != null) {
			registroAntiguo.setMes(registroActualizar.getMes());
		}
		if (registroActualizar.getFechaInicio() != null) {
			registroAntiguo.setFechaInicio(registroActualizar.getFechaInicio());
		}
		if (registroActualizar.getFechaFin() != null) {
			registroAntiguo.setFechaFin(registroActualizar.getFechaFin());
		}
		if (registroActualizar.getActivo() != null) {
			registroAntiguo.setActivo(registroActualizar.getActivo());
		}
		if (registroActualizar.getSisHabilitado() != null) {
			registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
		}
		return registroAntiguo;
	}

	public List<PagosDos> getPagosDosCollection() {
		return pagosDosCollection;
	}

	public void setPagosDosCollection(List<PagosDos> pagosDosCollection) {
		this.pagosDosCollection = pagosDosCollection;
	}

	public List<HistoricoRol> getHistoricoRolCollection() {
		return historicoRolCollection;
	}

	public void setHistoricoRolCollection(List<HistoricoRol> historicoRolCollection) {
		this.historicoRolCollection = historicoRolCollection;
	}
}
