package com.ec.prontiauto.dao;

import java.math.BigDecimal;

import com.ec.prontiauto.abstracts.AbstractContratatoDao;

public class CesionDerechosContratoDao extends AbstractContratatoDao {

	private Integer IdCliente;
	private BigDecimal valorADevolver;

	public CesionDerechosContratoDao() {

	}

	public Integer getIdCliente() {
		return IdCliente;
	}

	public void setIdCliente(Integer idCliente) {
		IdCliente = idCliente;
	}

	public BigDecimal getValorADevolver() {
		return valorADevolver;
	}

	public void setValorADevolver(BigDecimal valorADevolver) {
		this.valorADevolver = valorADevolver;
	}

}
