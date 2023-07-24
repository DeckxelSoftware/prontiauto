package com.ec.prontiauto.dao;

import java.math.BigDecimal;
import java.sql.Date;

import com.ec.prontiauto.abstracts.AbstractResponseDao;

public class CuotaResponseDao extends AbstractResponseDao {

	private Date fechaCobro;
	private Date fechaMora;
	private Integer numeroCuota;
	private BigDecimal valorCuota;
	private BigDecimal valorPagadoCuota;
	private BigDecimal valorTasaAdministrativa;
	private BigDecimal valorImpuesto;
	private BigDecimal abonoCapital;
	private String estaPagado;
	private String estaMora;
	private HistoricoPlanContratoResponseDao idHistoricoPlanContrato;

	public CuotaResponseDao() {
	}

	public Date getFechaCobro() {
		return fechaCobro;
	}

	public void setFechaCobro(Date fechaCobro) {
		this.fechaCobro = fechaCobro;
	}

	public Date getFechaMora() {
		return fechaMora;
	}

	public void setFechaMora(Date fechaMora) {
		this.fechaMora = fechaMora;
	}

	public Integer getNumeroCuota() {
		return numeroCuota;
	}

	public void setNumeroCuota(Integer numeroCuota) {
		this.numeroCuota = numeroCuota;
	}

	public BigDecimal getValorCuota() {
		return valorCuota;
	}

	public void setValorCuota(BigDecimal valorCuota) {
		this.valorCuota = valorCuota;
	}

	public BigDecimal getValorPagadoCuota() {
		return valorPagadoCuota;
	}

	public void setValorPagadoCuota(BigDecimal valorPagadoCuota) {
		this.valorPagadoCuota = valorPagadoCuota;
	}

	public BigDecimal getValorTasaAdministrativa() {
		return valorTasaAdministrativa;
	}

	public void setValorTasaAdministrativa(BigDecimal valorTasaAdministrativa) {
		this.valorTasaAdministrativa = valorTasaAdministrativa;
	}

	public BigDecimal getValorImpuesto() {
		return valorImpuesto;
	}

	public void setValorImpuesto(BigDecimal valorImpuesto) {
		this.valorImpuesto = valorImpuesto;
	}

	public BigDecimal getAbonoCapital() {
		return abonoCapital;
	}

	public void setAbonoCapital(BigDecimal abonoCapital) {
		this.abonoCapital = abonoCapital;
	}

	public String getEstaPagado() {
		return estaPagado;
	}

	public void setEstaPagado(String estaPagado) {
		this.estaPagado = estaPagado;
	}

	public String getEstaMora() {
		return estaMora;
	}

	public void setEstaMora(String estaMora) {
		this.estaMora = estaMora;
	}

	public HistoricoPlanContratoResponseDao getIdHistoricoPlanContrato() {
		return idHistoricoPlanContrato;
	}

	public void setIdHistoricoPlanContrato(HistoricoPlanContratoResponseDao idHistoricoPlanContrato) {
		this.idHistoricoPlanContrato = idHistoricoPlanContrato;
	}
}
