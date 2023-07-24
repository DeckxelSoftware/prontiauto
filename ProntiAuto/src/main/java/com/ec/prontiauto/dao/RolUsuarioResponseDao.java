package com.ec.prontiauto.dao;

import com.ec.prontiauto.abstracts.AbstractResponseDao;

public class RolUsuarioResponseDao extends AbstractResponseDao {

	RolResponseDao idRol;
	UsuarioResponseDao idUsuario;

	public RolUsuarioResponseDao() {
	}

	public RolResponseDao getIdRol() {
		return idRol;
	}

	public void setIdRol(RolResponseDao idRol) {
		this.idRol = idRol;
	}

	public UsuarioResponseDao getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(UsuarioResponseDao idUsuario) {
		this.idUsuario = idUsuario;
	}

}
