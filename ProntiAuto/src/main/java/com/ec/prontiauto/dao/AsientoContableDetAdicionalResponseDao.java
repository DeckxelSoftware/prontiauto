package com.ec.prontiauto.dao;

import com.ec.prontiauto.abstracts.AbstractResponseDao;

public class AsientoContableDetAdicionalResponseDao extends AbstractResponseDao {

	private String llave;
	private String valor;
	private AsientoContableCabeceraResponseDao idAsientoContableCabecera;
	
	public AsientoContableDetAdicionalResponseDao() {

	}

	public String getLlave() {
		return llave;
	}

	public void setLlave(String llave) {
		this.llave = llave;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public AsientoContableCabeceraResponseDao getIdAsientoContableCabecera() {
		return idAsientoContableCabecera;
	}

	public void setIdAsientoContableCabecera(AsientoContableCabeceraResponseDao idAsientoContableCabecera) {
		this.idAsientoContableCabecera = idAsientoContableCabecera;
	}

	
}
