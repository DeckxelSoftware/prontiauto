package com.ec.prontiauto.dao;

import com.ec.prontiauto.abstracts.AbstractRequestDao;

public class RolUsuarioRequestDao extends AbstractRequestDao {

	Integer idRol;
	Integer idUsuario;

	public RolUsuarioRequestDao() {
	}

	public Integer getIdRol() {
		return idRol;
	}

	public void setIdRol(Integer idRol) {
		this.idRol = idRol;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

}
