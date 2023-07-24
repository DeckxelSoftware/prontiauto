package com.ec.prontiauto.dao;

import com.ec.prontiauto.abstracts.AbstractContratatoDao;

public class DesistirContratoDao extends AbstractContratatoDao {

	private Integer idPlanAnterior;
	private Integer idVendedor;
	private boolean porcentajeTasa;

	public DesistirContratoDao() {

	}

	public Integer getIdPlanAnterior() {
		return idPlanAnterior;
	}

	public void setIdPlanAnterior(Integer idPlanAnterior) {
		this.idPlanAnterior = idPlanAnterior;
	}

	public Integer getIdVendedor() {
		return idVendedor;
	}

	public void setIdVendedor(Integer idVendedor) {
		this.idVendedor = idVendedor;
	}

	public boolean isPorcentajeTasa() {
		return porcentajeTasa;
	}

	public void setPorcentajeTasa(boolean porcentajeTasa) {
		this.porcentajeTasa = porcentajeTasa;
	}

}
