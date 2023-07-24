package com.ec.prontiauto.dao;

import java.math.BigDecimal;
import java.util.List;

import com.ec.prontiauto.abstracts.AbstractResponseDao;

public class PlanResponseDao extends AbstractResponseDao {

	private BigDecimal cuotaMes12;
	private BigDecimal cuotaMes24;
	private BigDecimal cuotaMes36;
	private BigDecimal cuotaMes48;
	private BigDecimal cuotaMes60;
	private BigDecimal cuotaMes72;
	private BigDecimal cuotaMes84;
	private BigDecimal precio;
	private String modelo;
	private BigDecimal inscripcion;
	private List<HistoricoPlanContratoResponseDao> historicoPlanContratoCollection;

	public PlanResponseDao() {
	}

	public BigDecimal getCuotaMes12() {
		return cuotaMes12;
	}

	public void setCuotaMes12(BigDecimal cuotaMes12) {
		this.cuotaMes12 = cuotaMes12;
	}

	public BigDecimal getCuotaMes24() {
		return cuotaMes24;
	}

	public void setCuotaMes24(BigDecimal cuotaMes24) {
		this.cuotaMes24 = cuotaMes24;
	}

	public BigDecimal getCuotaMes36() {
		return cuotaMes36;
	}

	public void setCuotaMes36(BigDecimal cuotaMes36) {
		this.cuotaMes36 = cuotaMes36;
	}

	public BigDecimal getCuotaMes48() {
		return cuotaMes48;
	}

	public void setCuotaMes48(BigDecimal cuotaMes48) {
		this.cuotaMes48 = cuotaMes48;
	}

	public BigDecimal getCuotaMes60() {
		return cuotaMes60;
	}

	public void setCuotaMes60(BigDecimal cuotaMes60) {
		this.cuotaMes60 = cuotaMes60;
	}

	public BigDecimal getCuotaMes72() {
		return cuotaMes72;
	}

	public void setCuotaMes72(BigDecimal cuotaMes72) {
		this.cuotaMes72 = cuotaMes72;
	}

	public BigDecimal getCuotaMes84() {
		return cuotaMes84;
	}

	public void setCuotaMes84(BigDecimal cuotaMes84) {
		this.cuotaMes84 = cuotaMes84;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public BigDecimal getInscripcion() {
		return inscripcion;
	}

	public void setInscripcion(BigDecimal inscripcion) {
		this.inscripcion = inscripcion;
	}

	public List<HistoricoPlanContratoResponseDao> getHistoricoPlanContratoCollection() {
		return historicoPlanContratoCollection;
	}

	public void setHistoricoPlanContratoCollection(
			List<HistoricoPlanContratoResponseDao> historicoPlanContratoCollection) {
		this.historicoPlanContratoCollection = historicoPlanContratoCollection;
	}
}
