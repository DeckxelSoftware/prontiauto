package com.ec.prontiauto.entidad;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

@Entity
@Table(name = "carga_familiar")
public class CargaFamiliar extends AbstractEntities {

	@Column(name = "nombres", nullable = false, length = 255)
	@CsvBindByName(column = "nombres")
	private String nombres;

	@Column(name = "apellidos", nullable = false, length = 255)
	@CsvBindByName(column = "apellidos")
	private String apellidos;

	@Column(name = "parentesco", nullable = false, length = 255)
	@CsvBindByName(column = "parentesco")
	private String parentesco;

	@Column(name = "\"tipoDocumento\"", nullable = false, length = 255)
	@CsvBindByName(column = "tipo_documento")
	private String tipoDocumento;

	@Column(name = "\"documentoIdentidad\"", nullable = false, length = 255)
	@CsvBindByName(column = "documento_identidad")
	private String documentoIdentidad;

	@Column(name = "genero ", nullable = false, length = 255)
	@CsvBindByName(column = "genero ")
	private String genero;

	@Column(name = "\"fechaNacimiento\"")
	@CsvDate(value = "yyyy-MM-dd")
	@CsvBindByName(column = "fecha_nacimiento")

	private Date fechaNacimiento;
	@Column(name = "discapacidad", nullable = false, length = 1)
	@CsvBindByName(column = "discapacidad")
	private String discapacidad;

	@Column(name = "\"tipoDiscapacidad\"", nullable = false, length = 255)
	@CsvBindByName(column = "tipo_discapacidad ")
	private String tipoDiscapacidad;

	@Column(name = "estudia", nullable = false, length = 255)
	@CsvBindByName(column = "estudia")
	private String estudia;

	private String aplicaUtilidad;
	private Integer edad;

	@ManyToOne
	@JoinColumn(name = "\"idTrabajador\"", referencedColumnName = "id")
	private Trabajador idTrabajador;

	@Transient
	@CsvBindByName(column = "id_trabajador")
	private Integer idTrabajador1;

	public CargaFamiliar() {
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

	public String getParentesco() {
		return parentesco;
	}

	public void setParentesco(String parentesco) {
		this.parentesco = parentesco;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getDocumentoIdentidad() {
		return documentoIdentidad;
	}

	public void setDocumentoIdentidad(String documentoIdentidad) {
		this.documentoIdentidad = documentoIdentidad;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getDiscapacidad() {
		return discapacidad;
	}

	public void setDiscapacidad(String discapacidad) {
		this.discapacidad = discapacidad;
	}

	public String getTipoDiscapacidad() {
		return tipoDiscapacidad;
	}

	public void setTipoDiscapacidad(String tipoDiscapacidad) {
		this.tipoDiscapacidad = tipoDiscapacidad;
	}

	public String getEstudia() {
		return estudia;
	}

	public void setEstudia(String estudia) {
		this.estudia = estudia;
	}

	public Trabajador getIdTrabajador() {
		return idTrabajador;
	}

	public void setIdTrabajador(Trabajador idTrabajador) {
		this.idTrabajador = idTrabajador;
	}

	public Integer getIdTrabajador1() {
		return idTrabajador1;
	}

	public void setIdTrabajador1(Integer idTrabajador1) {
		this.idTrabajador1 = idTrabajador1;
	}

	public String getAplicaUtilidad() {
		return aplicaUtilidad;
	}

	public void setAplicaUtilidad(String aplicaUtilidad) {
		this.aplicaUtilidad = aplicaUtilidad;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	public CargaFamiliar setValoresDiferentes(CargaFamiliar registroAntiguo, CargaFamiliar registroActualizar) {
		if (registroActualizar.getNombres() != null) {
			registroAntiguo.setNombres(registroActualizar.getNombres());
		}
		if (registroActualizar.getApellidos() != null) {
			registroAntiguo.setApellidos(registroActualizar.getApellidos());
		}
		if (registroActualizar.getParentesco() != null) {
			registroAntiguo.setParentesco(registroActualizar.getParentesco());
		}
		if (registroActualizar.getTipoDocumento() != null) {
			registroAntiguo.setTipoDocumento(registroActualizar.getTipoDocumento());
		}
		if (registroActualizar.getDocumentoIdentidad() != null) {
			registroAntiguo.setDocumentoIdentidad(registroActualizar.getDocumentoIdentidad());
		}
		if (registroActualizar.getGenero() != null) {
			registroAntiguo.setGenero(registroActualizar.getGenero());
		}
		if (registroActualizar.getFechaNacimiento() != null) {
			registroAntiguo.setFechaNacimiento(registroActualizar.getFechaNacimiento());
		}
		if (registroActualizar.getDiscapacidad() != null) {
			registroAntiguo.setDiscapacidad(registroActualizar.getDiscapacidad());
		}
		if (registroActualizar.getEstudia() != null) {
			registroAntiguo.setEstudia(registroActualizar.getEstudia());
		}
		if (registroActualizar.getSisHabilitado() != null) {
			registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
		}
		if (registroActualizar.getIdTrabajador() != null
				&& registroActualizar.getIdTrabajador().getId() != null) {
			registroAntiguo.setIdTrabajador(registroActualizar.getIdTrabajador());
		}

		if (registroActualizar.getAplicaUtilidad() != null) {
			registroAntiguo.setAplicaUtilidad(registroActualizar.getAplicaUtilidad());
		}

		if (registroActualizar.getEdad() != null) {
			registroAntiguo.setEdad(registroActualizar.getEdad());
		}
		return registroAntiguo;
	}

}
