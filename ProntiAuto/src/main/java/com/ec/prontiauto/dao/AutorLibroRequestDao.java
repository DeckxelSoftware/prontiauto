package com.ec.prontiauto.dao;

import com.ec.prontiauto.abstracts.AbstractRequestDao;

public class AutorLibroRequestDao extends AbstractRequestDao {

	private String nombres;
	private String apellidos;
	private String biografia;
	private Integer idLibroBiblioteca;

	public AutorLibroRequestDao() {
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getBiografia() {
		return biografia;
	}

	public void setBiografia(String biografia) {
		this.biografia = biografia;
	}

	public Integer getIdLibroBiblioteca() {
		return idLibroBiblioteca;
	}

	public void setIdLibroBiblioteca(Integer idLibroBiblioteca) {
		this.idLibroBiblioteca = idLibroBiblioteca;
	}
}
