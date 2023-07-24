package com.ec.prontiauto.entidad;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "cuenta_bancaria_empresa")
public class CuentaBancariaEmpresa extends AbstractEntities {

	@Column(name = "\"numeroCuenta\"", length = 255, nullable = false)
	@CsvBindByName(column = "numero_cuenta")
	private String numeroCuenta;

	@Column(name = "\"tipoCuenta\"", length = 255, nullable = false)
	@CsvBindByName(column = "tipo_cuenta")
	private String tipoCuenta;

	@ManyToOne
	@JoinColumn(name = "\"idEmpresa\"", referencedColumnName = "id", nullable = false)
	private Empresa idEmpresa;

	@Transient
	@CsvBindByName(column = "id_empresa")
	private Integer idEmpresa1;

	@ManyToOne
	@JoinColumn(name = "\"idBanco\"", referencedColumnName = "id", nullable = false)
	private Banco idBanco;
	
	@Transient
	@CsvBindByName(column = "id_banco")
	private Integer idBanco1;

	@ManyToOne
	@JoinColumn(name = "\"idInformacionFinanciera\"", referencedColumnName = "id", nullable = false, unique = false)
	private InformacionFinanciera idInformacionFinanciera;

	@Transient
	@CsvBindByName(column = "id_informacion_financiera")
	private Integer idInformacionFinanciera1;
	
	@JsonIgnore
	@OneToMany(mappedBy = "idCuentaBancariaEmpresa")
	private List<Chequera> chequeraCollection;

	@OneToMany(mappedBy = "idCuentaBancariaEmpresa")
	private List<Pago> pagoCollection;

	public CuentaBancariaEmpresa() {
	}

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public String getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	public Integer getIdBanco1() {
		return idBanco1;
	}

	public void setIdBanco1(Integer idBanco1) {
		this.idBanco1 = idBanco1;
	}

	public Empresa getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Empresa idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public Banco getIdBanco() {
		return idBanco;
	}

	public void setIdBanco(Banco idBanco) {
		this.idBanco = idBanco;
	}

	public Integer getIdEmpresa1() {
		return idEmpresa1;
	}

	public void setIdEmpresa1(Integer idEmpresa1) {
		this.idEmpresa1 = idEmpresa1;
	}

	public InformacionFinanciera getIdInformacionFinanciera() {
		return idInformacionFinanciera;
	}

	public void setIdInformacionFinanciera(InformacionFinanciera idInformacionFinanciera) {
		this.idInformacionFinanciera = idInformacionFinanciera;
	}

	public Integer getIdInformacionFinanciera1() {
		return idInformacionFinanciera1;
	}

	public void setIdInformacionFinanciera1(Integer idInformacionFinanciera1) {
		this.idInformacionFinanciera1 = idInformacionFinanciera1;
	}

	public List<Chequera> getChequeraCollection() {
		return chequeraCollection;
	}

	public void setChequeraCollection(List<Chequera> chequeraCollection) {
		this.chequeraCollection = chequeraCollection;
	}

	public CuentaBancariaEmpresa setValoresDiferentes(CuentaBancariaEmpresa registroAntiguo,
			CuentaBancariaEmpresa registroActualizar) {
		if (registroActualizar.getNumeroCuenta() != null) {
			registroAntiguo.setNumeroCuenta(registroActualizar.getNumeroCuenta());
		}
		if (registroActualizar.getTipoCuenta() != null) {
			registroAntiguo.setTipoCuenta(registroActualizar.getTipoCuenta());
		}
		if (registroActualizar.getSisHabilitado() != null) {
			registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
		}
		if (registroActualizar.getIdInformacionFinanciera() != null && registroActualizar.getIdInformacionFinanciera().getId() != null) {
			registroAntiguo.setIdInformacionFinanciera(registroActualizar.getIdInformacionFinanciera());
		}
		return registroAntiguo;
	}

}
