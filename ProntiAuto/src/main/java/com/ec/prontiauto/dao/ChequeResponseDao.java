package com.ec.prontiauto.dao;

import java.util.List;

import com.ec.prontiauto.abstracts.AbstractResponseDao;

public class ChequeResponseDao extends AbstractResponseDao {

	private Integer numeroCheque;
	private String estadoCheque;
	private ChequeraResponseDao idChequera;
	private List<AsientoContableCabeceraResponseDao> AsientoContableCabeceraCollection;

	public ChequeResponseDao() {

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

	public ChequeraResponseDao getIdChequera() {
		return idChequera;
	}

	public void setIdChequera(ChequeraResponseDao idChequera) {
		this.idChequera = idChequera;
	}

	public List<AsientoContableCabeceraResponseDao> getAsientoContableCabeceraCollection() {
		return AsientoContableCabeceraCollection;
	}

	public void setAsientoContableCabeceraCollection(
			List<AsientoContableCabeceraResponseDao> asientoContableCabeceraCollection) {
		AsientoContableCabeceraCollection = asientoContableCabeceraCollection;
	}

	
	
}
