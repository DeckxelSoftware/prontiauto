package com.ec.prontiauto.dao;

import com.ec.prontiauto.abstracts.AbstractRequestDao;

public class TransaccionAsientoContableRequestDao extends AbstractRequestDao {

	private String detalle;
	private Float valorDebito;
	private Float valorCredito;
	private Object idAsientoContableCabecera;
	private Integer idCuentaContable;



	private String numeroFactura;

	public TransaccionAsientoContableRequestDao() {

	}

	public String getNumeroFactura() {
		return numeroFactura;
	}

	public void setNumeroFactura(String numeroFactura) {
		this.numeroFactura = numeroFactura;
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

	public Object getIdAsientoContableCabecera() {
		return idAsientoContableCabecera;
	}

	public void setIdAsientoContableCabecera(Object idAsientoContableCabecera) {
		this.idAsientoContableCabecera = idAsientoContableCabecera;
	}
	public Integer getIdCuentaContable() {
		return idCuentaContable;
	}

	public void setIdCuentaContable(Integer idCuentaContable) {
		this.idCuentaContable = idCuentaContable;
	}

}
