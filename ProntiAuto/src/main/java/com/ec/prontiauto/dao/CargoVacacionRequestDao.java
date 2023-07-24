package com.ec.prontiauto.dao;

import java.sql.Date;

import com.ec.prontiauto.abstracts.AbstractRequestDao;

public class CargoVacacionRequestDao extends AbstractRequestDao {

	private Date fechaDesde;
	private Date fechaHasta;
	private Integer diasVacaciones;
	private Integer diasAntiguedad;
	private Integer diasTeoricos;
	private Integer diasTomados;
	private Integer diasSaldo;
	private Float valorVacacion;
	private Float valorDias;
	private Float valorAntiguedad;
	private Float valorTeorico;
	private Float valorTomado;
	private Float valorSaldo;
	private Float totalIngresosAnio;
	private Integer numAnioAcumulado;

	private Integer idTrabajador;

	public CargoVacacionRequestDao() {
	}

	public Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public Integer getDiasVacaciones() {
		return diasVacaciones;
	}

	public void setDiasVacaciones(Integer diasVacaciones) {
		this.diasVacaciones = diasVacaciones;
	}

	public Integer getDiasAntiguedad() {
		return diasAntiguedad;
	}

	public void setDiasAntiguedad(Integer diasAntiguedad) {
		this.diasAntiguedad = diasAntiguedad;
	}

	public Integer getDiasTeoricos() {
		return diasTeoricos;
	}

	public void setDiasTeoricos(Integer diasTeoricos) {
		this.diasTeoricos = diasTeoricos;
	}

	public Integer getDiasTomados() {
		return diasTomados;
	}

	public void setDiasTomados(Integer diasTomados) {
		this.diasTomados = diasTomados;
	}

	public Integer getDiasSaldo() {
		return diasSaldo;
	}

	public void setDiasSaldo(Integer diasSaldo) {
		this.diasSaldo = diasSaldo;
	}

	public Float getValorVacacion() {
		return valorVacacion;
	}

	public void setValorVacacion(Float valorVacacion) {
		this.valorVacacion = valorVacacion;
	}

	public Float getValorDias() {
		return valorDias;
	}

	public void setValorDias(Float valorDias) {
		this.valorDias = valorDias;
	}

	public Float getValorAntiguedad() {
		return valorAntiguedad;
	}

	public void setValorAntiguedad(Float valorAntiguedad) {
		this.valorAntiguedad = valorAntiguedad;
	}

	public Float getValorTeorico() {
		return valorTeorico;
	}

	public void setValorTeorico(Float valorTeorico) {
		this.valorTeorico = valorTeorico;
	}

	public Float getValorTomado() {
		return valorTomado;
	}

	public void setValorTomado(Float valorTomado) {
		this.valorTomado = valorTomado;
	}

	public Float getValorSaldo() {
		return valorSaldo;
	}

	public void setValorSaldo(Float valorSaldo) {
		this.valorSaldo = valorSaldo;
	}

	public Float getTotalIngresosAnio() {
		return totalIngresosAnio;
	}

	public void setTotalIngresosAnio(Float totalIngresosAnio) {
		this.totalIngresosAnio = totalIngresosAnio;
	}

	public Integer getNumAnioAcumulado() {
		return numAnioAcumulado;
	}

	public void setNumAnioAcumulado(Integer numAnioAcumulado) {
		this.numAnioAcumulado = numAnioAcumulado;
	}

	public Integer getIdTrabajador() {
		return idTrabajador;
	}

	public void setIdTrabajador(Integer idTrabajador) {
		this.idTrabajador = idTrabajador;
	}

}
