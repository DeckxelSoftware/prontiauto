package com.ec.prontiauto.dao;

import com.ec.prontiauto.abstracts.AbstractRequestDao;

public class ClienteRequestDao extends AbstractRequestDao {

	private String tipoCliente;
	private Object idUsuario;
	private Object idEmpresa;

	public ClienteRequestDao() {
	}

	public String getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public Object getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Object idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Object getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Object idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

}
