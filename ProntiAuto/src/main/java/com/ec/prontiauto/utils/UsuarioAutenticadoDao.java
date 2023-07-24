package com.ec.prontiauto.utils;

import java.util.List;

import com.ec.prontiauto.security.JwtUser;

public class UsuarioAutenticadoDao {
	private JwtUser usuario;
	private List<?> permisos;
	private String jwt;

	public UsuarioAutenticadoDao(JwtUser usuario, String jwt, List<?> permisos) {
		super();
		this.usuario = usuario;
		this.jwt = jwt;
		this.permisos = permisos;
	}

	public JwtUser getUsuario() {
		return usuario;
	}

	public void setUsuario(JwtUser usuario) {
		this.usuario = usuario;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	public List<?> getPermisos() {
		return permisos;
	}

	public void setPermisos(List<?> permisos) {
		this.permisos = permisos;
	}

}
