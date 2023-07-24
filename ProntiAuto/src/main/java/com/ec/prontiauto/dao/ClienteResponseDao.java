package com.ec.prontiauto.dao;

import java.util.List;

import com.ec.prontiauto.abstracts.AbstractResponseDao;

public class ClienteResponseDao extends AbstractResponseDao {

	private String tipoCliente;
	private UsuarioResponseDao idUsuario;
	private EmpresaResponseDao idEmpresa;
	private List<ClienteEnGrupoResponseDao> collectionClienteEnGrupo;

	public ClienteResponseDao() {
	}

	public String getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public UsuarioResponseDao getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(UsuarioResponseDao idUsuario) {
		this.idUsuario = idUsuario;
	}

	public EmpresaResponseDao getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(EmpresaResponseDao idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public List<ClienteEnGrupoResponseDao> getCollectionClienteEnGrupo() {
		return collectionClienteEnGrupo;
	}

	public void setCollectionClienteEnGrupo(List<ClienteEnGrupoResponseDao> collectionClienteEnGrupo) {
		this.collectionClienteEnGrupo = collectionClienteEnGrupo;
	}

}
