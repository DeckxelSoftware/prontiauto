package com.ec.prontiauto.dao;

import com.ec.prontiauto.abstracts.AbstractRequestDao;

public class ChequeRequestDao extends AbstractRequestDao {

	private Integer numeroCheque;
	private String estadoCheque;
	private Integer idChequera;

	public ChequeRequestDao() {

	}

	public Integer getNumeroCheque() {
		return numeroCheque;
	}

	public void setNumeroCheque(Integer numeroCheque) {
		this.numeroCheque = numeroCheque;
	}

	public String getEstadoCheque() {
		return estadoCheque;
	}

	public void setEstadoCheque(String estadoCheque) {
		this.estadoCheque = estadoCheque;
	}

	public Integer getIdChequera() {
		return idChequera;
	}

	public void setIdChequera(Integer idChequera) {
		this.idChequera = idChequera;
	}

	
}
