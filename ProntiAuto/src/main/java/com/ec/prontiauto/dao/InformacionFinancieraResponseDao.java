package com.ec.prontiauto.dao;

import java.util.List;

import com.ec.prontiauto.abstracts.AbstractResponseDao;

public class InformacionFinancieraResponseDao extends AbstractResponseDao {

	private String formaPago;
	private TrabajadorResponseDao idTrabajador;
	private List<CuentaBancariaEmpresaResponseDao> CuentaBancariaEmpresaCollection;

	public InformacionFinancieraResponseDao() {

	}

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	public TrabajadorResponseDao getIdTrabajador() {
		return idTrabajador;
	}

	public void setIdTrabajador(TrabajadorResponseDao idTrabajador) {
		this.idTrabajador = idTrabajador;
	}

	public List<CuentaBancariaEmpresaResponseDao> getCuentaBancariaEmpresaCollection() {
		return CuentaBancariaEmpresaCollection;
	}

	public void setCuentaBancariaEmpresaCollection(List<CuentaBancariaEmpresaResponseDao> cuentaBancariaEmpresaCollection) {
		CuentaBancariaEmpresaCollection = cuentaBancariaEmpresaCollection;
	}

}
