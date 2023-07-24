package com.ec.prontiauto.dao;

import com.ec.prontiauto.abstracts.AbstractResponseDao;

public class AutorLibroResponseDao extends AbstractResponseDao {

	private String nombres;
	private String apellidos;
	private String biografia;
	private LibroBibliotecaResponseDao idLibroBiblioteca;

	public AutorLibroResponseDao() {
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


	public LibroBibliotecaResponseDao getIdLibroBiblioteca() {
		return idLibroBiblioteca;
	}

	public void setIdLibroBiblioteca(LibroBibliotecaResponseDao idLibroBiblioteca) {
		this.idLibroBiblioteca = idLibroBiblioteca;
	}

}
