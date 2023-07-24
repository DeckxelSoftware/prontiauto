package com.ec.prontiauto.entidad;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.ec.prontiauto.security.model.Autorizacion;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

@Entity
@Table(name = "usuario")
public class Usuario extends AbstractEntities {

	@Column(name = "username", length = 255, unique = true)
	@CsvBindByName(column = "username")
	private String username;

	@JsonIgnore
	@Column(name = "password", length = 255)
	@CsvBindByName(column = "password")
	private String password;

	@Column(name = "nombres", length = 255, nullable = false)
	@CsvBindByName(column = "nombres")
	private String nombres;

	@Column(name = "apellidos", length = 255, nullable = false)
	@CsvBindByName(column = "apellidos")
	private String apellidos;

	@Column(name = "\"fechaNacimiento\"")
	@CsvDate(value = "yyyy-MM-dd")
	@CsvBindByName(column = "fecha_nacimiento")
	
	private Date fechaNacimiento;

	@Column(name = "correo", length = 255)
	@CsvBindByName(column = "correo")
	private String correo;

	@Column(name = "\"ultimoReseteo\"")
	@CsvBindByName(column = "ultimo_reseteo")
	
	private Date ultimoReseteo;

	@Column(name = "\"tipoMedioContacto1\"", length = 255)
	@CsvBindByName(column = "tipo_medio_contacto_1")
	private String tipoMedioContacto1;

	@Column(name = "\"medioContacto1\"", length = 255)
	@CsvBindByName(column = "medio_contacto_1")
	private String medioContacto1;

	@Column(name = "\"tipoDocumentoIdentidad\"", length = 255, nullable = false)
	@CsvBindByName(column = "tipo_documento_identidad")
	private String tipoDocumentoIdentidad;

	@Column(name = "\"documentoIdentidad\"", length = 255, nullable = false, unique = true)
	@CsvBindByName(column = "documento_identidad")
	private String documentoIdentidad;

	@Column(name = "pais", length = 255, nullable = false)
	@CsvBindByName(column = "pais")
	private String pais;

	@Column(name = "provincia", length = 255, nullable = false)
	@CsvBindByName(column = "provincia")
	private String provincia;

	@Column(name = "ciudad", length = 255, nullable = false)
	@CsvBindByName(column = "ciudad")
	private String ciudad;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuario_autorizacion", joinColumns = {
			@JoinColumn(name = "usuario_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "autorizacion_id", referencedColumnName = "id") })
	private List<Autorizacion> authorities;

	@JsonIgnore
	@OneToMany(mappedBy = "idUsuario")
	private List<RolUsuario> rolUsuarioCollection;

	@OneToOne(mappedBy = "idUsuario")
	private Trabajador idTrabajador;

	@JsonIgnore
	@OneToMany(mappedBy = "idUsuario")
	private List<Cliente> clienteCollection;

	@JsonIgnore
	@OneToMany(mappedBy = "idUsuario")
	private List<Proveedor> proveedorCollection;




	public Usuario() {
	}

	public List<Cliente> getClienteCollection() {
		return clienteCollection;
	}

	public void setClienteCollection(List<Cliente> clienteCollection) {
		this.clienteCollection = clienteCollection;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public Date getUltimoReseteo() {
		return ultimoReseteo;
	}

	public void setUltimoReseteo(Date ultimoReseteo) {
		this.ultimoReseteo = ultimoReseteo;
	}

	public List<Autorizacion> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<Autorizacion> authorities) {
		this.authorities = authorities;
	}

	public List<RolUsuario> getRolUsuarioCollection() {
		return rolUsuarioCollection;
	}

	public void setRolUsuarioCollection(List<RolUsuario> rolUsuarioCollection) {
		this.rolUsuarioCollection = rolUsuarioCollection;
	}

	public String getTipoMedioContacto1() {
		return tipoMedioContacto1;
	}

	public void setTipoMedioContacto1(String tipoMedioContacto1) {
		this.tipoMedioContacto1 = tipoMedioContacto1;
	}

	public String getMedioContacto1() {
		return medioContacto1;
	}

	public void setMedioContacto1(String medioContacto1) {
		this.medioContacto1 = medioContacto1;
	}

	public String getTipoDocumentoIdentidad() {
		return tipoDocumentoIdentidad;
	}

	public void setTipoDocumentoIdentidad(String tipoDocumentoIdentidad) {
		this.tipoDocumentoIdentidad = tipoDocumentoIdentidad;
	}

	public String getDocumentoIdentidad() {
		return documentoIdentidad;
	}

	public void setDocumentoIdentidad(String documentoIdentidad) {
		this.documentoIdentidad = documentoIdentidad;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public Trabajador getIdTrabajador() {
		return idTrabajador;
	}

	public void setIdTrabajador(Trabajador idTrabajador) {
		this.idTrabajador = idTrabajador;
	}

	public List<Proveedor> getProveedorCollection() {
		return this.proveedorCollection;
	}

	public void setProveedorCollection(List<Proveedor> proveedorCollection) {
		this.proveedorCollection = proveedorCollection;
	}


	public Usuario setValoresDiferentes(Usuario registroAntiguo, Usuario registroActualizar) {

		if (registroActualizar.getApellidos() != null) {
			registroAntiguo.setApellidos(registroActualizar.getApellidos());
		}
		if (registroActualizar.getFechaNacimiento() != null) {
			registroAntiguo.setFechaNacimiento(registroActualizar.getFechaNacimiento());
		}
		if (registroActualizar.getNombres() != null) {
			registroAntiguo.setNombres(registroActualizar.getNombres());
		}
		if (registroActualizar.getSisHabilitado() != null) {
			registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
		}
		if (registroActualizar.tipoMedioContacto1 != null) {
			registroAntiguo.setTipoMedioContacto1(registroActualizar.getTipoMedioContacto1());
		}
		if (registroActualizar.getMedioContacto1() != null) {
			registroAntiguo.setMedioContacto1(registroActualizar.getMedioContacto1());
		}
		if (registroActualizar.getTipoDocumentoIdentidad() != null) {
			registroAntiguo.setTipoDocumentoIdentidad(registroActualizar.getTipoDocumentoIdentidad());
		}
		if (registroActualizar.getPais() != null) {
			registroAntiguo.setPais(registroActualizar.getPais());
		}
		if (registroActualizar.getProvincia() != null) {
			registroAntiguo.setProvincia(registroActualizar.getProvincia());
		}
		if (registroActualizar.getCiudad() != null) {
			registroAntiguo.setCiudad(registroActualizar.getCiudad());
		}
		return registroAntiguo;
	}

}