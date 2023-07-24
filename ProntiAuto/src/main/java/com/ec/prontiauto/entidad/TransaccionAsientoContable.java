package com.ec.prontiauto.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "transaccion_asiento_contable")
public class TransaccionAsientoContable extends AbstractEntities {

	@Column(name = "detalle", length = 255)
	@CsvBindByName(column = "detalle")
	private String detalle;

	@Column(name = "\"valorDebito\"")
	@CsvBindByName(column = "valor_debito")
	private Float valorDebito;

	@Column(name = "\"valorCredito\"")
	@CsvBindByName(column = "valor_credito")
	private Float valorCredito;

	@Column(name = "\"numeroFactura\"")
	@CsvBindByName(column = "numero_factura")
	private String numeroFactura;

	@ManyToOne
	@JoinColumn(name = "\"idAsientoContableCabecera\"", referencedColumnName = "id", nullable = false)
	private AsientoContableCabecera idAsientoContableCabecera;

	@ManyToOne
	@JoinColumn(name = "\"idCuentaContable\"", referencedColumnName = "id", nullable = false)
	private CuentaContable idCuentaContable;

	@Transient
	@CsvBindByName(column = "id_asiento_contable_cabecera")
	private Integer idAsientoContableCabecera1;

	@Transient
	@CsvBindByName(column = "id_cuenta_contable")
	private Integer idCuentaContable1;

	public TransaccionAsientoContable() {
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

	public String getNumeroFactura() {
		return numeroFactura;
	}

	public void setNumeroFactura(String numeroFactura) {
		this.numeroFactura = numeroFactura;
	}

	public AsientoContableCabecera getIdAsientoContableCabecera() {
		return idAsientoContableCabecera;
	}

	public void setIdAsientoContableCabecera(AsientoContableCabecera idAsientoContableCabecera) {
		this.idAsientoContableCabecera = idAsientoContableCabecera;
	}

	public CuentaContable getIdCuentaContable() {
		return idCuentaContable;
	}

	public void setIdCuentaContable(CuentaContable idCuentaContable) {
		this.idCuentaContable = idCuentaContable;
	}

	public Integer getIdAsientoContableCabecera1() {
		return idAsientoContableCabecera1;
	}

	public void setIdAsientoContableCabecera1(Integer idAsientoContableCabecera1) {
		this.idAsientoContableCabecera1 = idAsientoContableCabecera1;
	}

	public Integer getIdCuentaContable1() {
		return idCuentaContable1;
	}

	public void setIdCuentaContable1(Integer idCuentaContable1) {
		this.idCuentaContable1 = idCuentaContable1;
	}

	public TransaccionAsientoContable setValoresDiferentes(TransaccionAsientoContable registroAntiguo,
			TransaccionAsientoContable registroActualizar) {

		if (registroActualizar.getDetalle() != null) {
			registroAntiguo.setDetalle(registroActualizar.getDetalle());
		}
		if (registroActualizar.getValorDebito() != null) {
			registroAntiguo.setValorDebito(registroActualizar.getValorDebito());
		}
		if (registroActualizar.getValorCredito() != null) {
			registroAntiguo.setValorCredito(registroActualizar.getValorCredito());
		}
		if (registroActualizar.getNumeroFactura() != null) {
			registroAntiguo.setNumeroFactura(registroActualizar.getNumeroFactura());
		}
		if (registroActualizar.getSisHabilitado() != null) {
			registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
		}

		return registroAntiguo;
	}
}
