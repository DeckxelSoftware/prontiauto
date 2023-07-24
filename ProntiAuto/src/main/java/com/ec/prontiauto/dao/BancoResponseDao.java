package com.ec.prontiauto.dao;

import java.util.List;

import com.ec.prontiauto.abstracts.AbstractResponseDao;

public class BancoResponseDao extends AbstractResponseDao {

	private String nombre;
	private List<CuentaBancariaEmpresaResponseDao> cuentaBancariaEmpresaCollection;
	private List<AsientoContableCabeceraResponseDao> asientoContableCabeceraCollection;

	public BancoResponseDao() {
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<CuentaBancariaEmpresaResponseDao> getCuentaBancariaEmpresaCollection() {
		return cuentaBancariaEmpresaCollection;
	}

	public void setCuentaBancariaEmpresaCollection(
			List<CuentaBancariaEmpresaResponseDao> cuentaBancariaEmpresaCollection) {
		this.cuentaBancariaEmpresaCollection = cuentaBancariaEmpresaCollection;
	}

	public List<AsientoContableCabeceraResponseDao> getAsientoContableCabeceraCollection() {
		return asientoContableCabeceraCollection;
	}

	public void setAsientoContableCabeceraCollection(
			List<AsientoContableCabeceraResponseDao> asientoContableCabeceraCollection) {
		this.asientoContableCabeceraCollection = asientoContableCabeceraCollection;
	}
	

}
