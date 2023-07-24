package com.ec.prontiauto.security.model;

public enum UsuarioAutorizacion {
	ROLE_USER("USUARIO", true), ROLE_ADMIN("ADMIN", true);

	private String rol;
	private Boolean habilitado;

	private UsuarioAutorizacion(String rol, Boolean habilitado) {
		this.rol = rol;
		this.habilitado = habilitado;
	}

	public String getRol() {
		return rol;
	}

	public Boolean getHabilitado() {
		return habilitado;
	}

}