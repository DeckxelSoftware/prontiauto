package com.ec.prontiauto.dao;

import com.ec.prontiauto.abstracts.AbstractRequestDao;

public class CuentaBancariaEmpresaRequestDao extends AbstractRequestDao {
	private String numeroCuenta;
	private String tipoCuenta;
	private Integer idBanco;
	private Integer idEmpresa;
	private Integer idInformacionFinanciera;

	public CuentaBancariaEmpresaRequestDao() {
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

	public Integer getIdBanco() {
		return idBanco;
	}

	public void setIdBanco(Integer idBanco) {
		this.idBanco = idBanco;
	}

	public Integer getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public Integer getIdInformacionFinanciera() {
		return idInformacionFinanciera;
	}

	public void setIdInformacionFinanciera(Integer idInformacionFinanciera) {
		this.idInformacionFinanciera = idInformacionFinanciera;
	}

}
