package com.ec.prontiauto.dao;

import com.ec.prontiauto.abstracts.AbstractRequestDao;

public class DivisionAdministrativaRequestDao extends AbstractRequestDao {
	private String nombreDivision;

    public DivisionAdministrativaRequestDao() {
    }

	public String getNombreDivision() {
		return nombreDivision;
	}

	public void setNombreDivision(String nombreDivision) {
		this.nombreDivision = nombreDivision;
	}

}
