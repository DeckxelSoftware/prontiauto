package com.ec.prontiauto.dao;

import java.sql.Date;

import com.ec.prontiauto.abstracts.AbstractRequestDao;

public class AsientoContableCabeceraRequestDao extends AbstractRequestDao {

	private Date fecha;
	private Integer anio;
	private String mesPeriodo;
	private String tipoTransaccion;
	private String tipoAsientoContable;
	private String codigoReferencialAsientoContable;
	private Float totalDebito;
	private Float totalCredito;
	private Float totalSaldoActualFecha;
	private String asientoCerrado;
	private String serie;
	private String descripcion;
	private String beneficiario;

	private Integer idSugrupoContable;
	private Integer idCheque;
	private Integer idCuentaContable;

	public AsientoContableCabeceraRequestDao() {

	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public String getMesPeriodo() {
		return mesPeriodo;
	}

	public void setMesPeriodo(String mesPeriodo) {
		this.mesPeriodo = mesPeriodo;
	}

	public String getTipoTransaccion() {
		return tipoTransaccion;
	}

	public void setTipoTransaccion(String tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}

	public String getTipoAsientoContable() {
		return tipoAsientoContable;
	}

	public void setTipoAsientoContable(String tipoAsientoContable) {
		this.tipoAsientoContable = tipoAsientoContable;
	}

	public String getCodigoReferencialAsientoContable() {
		return codigoReferencialAsientoContable;
	}

	public void setCodigoReferencialAsientoContable(String codigoReferencialAsientoContable) {
		this.codigoReferencialAsientoContable = codigoReferencialAsientoContable;
	}

	public Float getTotalDebito() {
		return totalDebito;
	}

	public void setTotalDebito(Float totalDebito) {
		this.totalDebito = totalDebito;
	}

	public Float getTotalCredito() {
		return totalCredito;
	}

	public void setTotalCredito(Float totalCredito) {
		this.totalCredito = totalCredito;
	}

	public Float getTotalSaldoActualFecha() {
		return totalSaldoActualFecha;
	}

	public void setTotalSaldoActualFecha(Float totalSaldoActualFecha) {
		this.totalSaldoActualFecha = totalSaldoActualFecha;
	}

	public String getAsientoCerrado() {
		return asientoCerrado;
	}

	public void setAsientoCerrado(String asientoCerrado) {
		this.asientoCerrado = asientoCerrado;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getIdSugrupoContable() {
		return idSugrupoContable;
	}

	public void setIdSugrupoContable(Integer idSugrupoContable) {
		this.idSugrupoContable = idSugrupoContable;
	}

	public Integer getIdCheque() {
		return idCheque;
	}

	public void setIdCheque(Integer idCheque) {
		this.idCheque = idCheque;
	}

	public String getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(String beneficiario) {
		this.beneficiario = beneficiario;
	}

	public Integer getIdCuentaContable() {
		return idCuentaContable;
	}

	public void setIdCuentaContable(Integer idCuentaContable) {
		this.idCuentaContable = idCuentaContable;
	}

}
