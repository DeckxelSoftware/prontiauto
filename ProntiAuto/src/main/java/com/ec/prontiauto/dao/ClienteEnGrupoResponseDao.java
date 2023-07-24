package com.ec.prontiauto.dao;

import java.util.List;

import com.ec.prontiauto.abstracts.AbstractResponseDao;

public class ClienteEnGrupoResponseDao extends AbstractResponseDao {

	private ClienteResponseDao idCliente;
	private GrupoResponseDao idGrupo;
	private List<ContratoResponseDao> contratoCollection;

	public ClienteEnGrupoResponseDao() {
	}

	public ClienteResponseDao getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(ClienteResponseDao idCliente) {
		this.idCliente = idCliente;
	}

	public GrupoResponseDao getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(GrupoResponseDao idGrupo) {
		this.idGrupo = idGrupo;
	}

	public List<ContratoResponseDao> getContratoCollection() {
		return contratoCollection;
	}

	public void setContratoCollection(List<ContratoResponseDao> contratoCollection) {
		this.contratoCollection = contratoCollection;
	}

}
