package com.ec.prontiauto.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "autor_libro")
public class AutorLibro extends AbstractEntities {

	@Column(name = "nombres", nullable = false, length = 255)
	@CsvBindByName(column = "nombres")
	private String nombres;

	@Column(name = "apellidos", nullable = false, length = 255)
	@CsvBindByName(column = "apellidos")
	private String apellidos;

	@Column(name = "biografia", length = 2255)
	@CsvBindByName(column = "biografia")
	private String biografia;

	@ManyToOne
	@JoinColumn(name = "\"idLibroBiblioteca\"", referencedColumnName = "id", nullable = false)
	private LibroBiblioteca idLibroBiblioteca;

	@Transient
	@CsvBindByName(column = "id_libro_biblioteca")
	private Integer idLibroBiblioteca1;

	public AutorLibro() {
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

	public LibroBiblioteca getIdLibroBiblioteca() {
		return idLibroBiblioteca;
	}

	public void setIdLibroBiblioteca(LibroBiblioteca idLibroBiblioteca) {
		this.idLibroBiblioteca = idLibroBiblioteca;
	}

	public Integer getIdLibroBiblioteca1() {
		return idLibroBiblioteca1;
	}

	public void setIdLibroBiblioteca1(Integer idLibroBiblioteca1) {
		this.idLibroBiblioteca1 = idLibroBiblioteca1;
	}

	public AutorLibro setValoresDiferentes(AutorLibro registroAntiguo, AutorLibro registroActualizar) {
		if (registroActualizar.getNombres() != null) {
			registroAntiguo.setNombres(registroActualizar.getNombres());
		}
		if (registroActualizar.getApellidos() != null) {
			registroAntiguo.setApellidos(registroActualizar.getApellidos());
		}

		if (registroActualizar.getBiografia() != null) {
			registroAntiguo.setBiografia(registroActualizar.getBiografia());
		}

		if (registroActualizar.getSisHabilitado() != null) {
			registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
		}
		if (registroActualizar.getIdLibroBiblioteca().getId() != null) {
			registroAntiguo.setIdLibroBiblioteca(registroActualizar.getIdLibroBiblioteca());
		}
		return registroAntiguo;
	}

}
