package com.ec.prontiauto.dao;

import com.ec.prontiauto.abstracts.AbstractRequestDao;

public class AsientoContableDetAdicionalRequestDao extends AbstractRequestDao {

	private String llave;
	private String valor;
	private Integer idAsientoContableCabecera;

	public AsientoContableDetAdicionalRequestDao() {

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

	public Integer getIdAsientoContableCabecera() {
		return idAsientoContableCabecera;
	}

	public void setIdAsientoContableCabecera(Integer idAsientoContableCabecera) {
		this.idAsientoContableCabecera = idAsientoContableCabecera;
	}

}
