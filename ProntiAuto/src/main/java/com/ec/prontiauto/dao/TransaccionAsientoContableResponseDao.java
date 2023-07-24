package com.ec.prontiauto.dao;

import com.ec.prontiauto.abstracts.AbstractResponseDao;

public class TransaccionAsientoContableResponseDao extends AbstractResponseDao {

	private String detalle;
	private Float valorDebito;
	private Float valorCredito;
	private String numeroFactura;
	private AsientoContableCabeceraResponseDao idAsientoContableCabecera;
	private CuentaContableResponseDao idCuentaContable;

	public TransaccionAsientoContableResponseDao() {

	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public Float getValorDebito() {
		return valorDebito;
	}

	public void setValorDebito(Float valorDebito) {
		this.valorDebito = valorDebito;
	}

	public Float getValorCredito() {
		return valorCredito;
	}

	public void setValorCredito(Float valorCredito) {
		this.valorCredito = valorCredito;
	}

	public AsientoContableCabeceraResponseDao getIdAsientoContableCabecera() {
		return idAsientoContableCabecera;
	}

	public void setIdAsientoContableCabecera(AsientoContableCabeceraResponseDao idAsientoContableCabecera) {
		this.idAsientoContableCabecera = idAsientoContableCabecera;
	}

	public String getNumeroFactura() {
		return numeroFactura;
	}

	public void setNumeroFactura(String numeroFactura) {
		this.numeroFactura = numeroFactura;
	}

	public CuentaContableResponseDao getIdCuentaContable() {
		return idCuentaContable;
	}

	public void setIdCuentaContable(CuentaContableResponseDao idCuentaContable) {
		this.idCuentaContable = idCuentaContable;
	}
}
