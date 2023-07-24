package com.ec.prontiauto.dao;

import java.util.List;

import com.ec.prontiauto.abstracts.AbstractResponseDao;

public class DivisionAdministrativaResponseDao extends AbstractResponseDao {
	private String nombreDivision;
    private List<HistorialLaboralResponseDao> historialLaboralCollection;

    public DivisionAdministrativaResponseDao() {
    }

	public String getNombreDivision() {
		return nombreDivision;
	}

	public void setNombreDivision(String nombreDivision) {
		this.nombreDivision = nombreDivision;
	}

	public List<HistorialLaboralResponseDao> getHistorialLaboralCollection() {
		return historialLaboralCollection;
	}

	public void setHistorialLaboralCollection(List<HistorialLaboralResponseDao> historialLaboralCollection) {
		this.historialLaboralCollection = historialLaboralCollection;
	}

}
