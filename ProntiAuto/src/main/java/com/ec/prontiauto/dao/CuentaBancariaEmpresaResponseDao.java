package com.ec.prontiauto.dao;

import java.util.List;

import com.ec.prontiauto.abstracts.AbstractResponseDao;

public class CuentaBancariaEmpresaResponseDao extends AbstractResponseDao {

	private String numeroCuenta;
	private String tipoCuenta;
	private BancoResponseDao idBanco;
	private EmpresaResponseDao idEmpresa;
	private InformacionFinancieraResponseDao idInformacionFinanciera;
	private List<ChequeraResponseDao> chequeraCollection;

	public CuentaBancariaEmpresaResponseDao() {
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

	public BancoResponseDao getIdBanco() {
		return idBanco;
	}

	public void setIdBanco(BancoResponseDao idBanco) {
		this.idBanco = idBanco;
	}

	public EmpresaResponseDao getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(EmpresaResponseDao idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public InformacionFinancieraResponseDao getIdInformacionFinanciera() {
		return idInformacionFinanciera;
	}

	public void setIdInformacionFinanciera(InformacionFinancieraResponseDao idInformacionFinanciera) {
		this.idInformacionFinanciera = idInformacionFinanciera;
	}

	public List<ChequeraResponseDao> getChequeraCollection() {
		return chequeraCollection;
	}

	public void setChequeraCollection(List<ChequeraResponseDao> chequeraCollection) {
		this.chequeraCollection = chequeraCollection;
	}

	
}
