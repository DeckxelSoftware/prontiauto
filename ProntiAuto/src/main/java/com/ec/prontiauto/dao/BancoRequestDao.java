package com.ec.prontiauto.dao;

import com.ec.prontiauto.abstracts.AbstractRequestDao;

public class BancoRequestDao extends AbstractRequestDao {

	private String nombre;

	public BancoRequestDao() {
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
