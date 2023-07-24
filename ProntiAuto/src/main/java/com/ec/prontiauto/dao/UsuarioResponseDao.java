package com.ec.prontiauto.dao;

import java.sql.Date;
import java.util.List;

import com.ec.prontiauto.abstracts.AbstractResponseDao;

public class UsuarioResponseDao extends AbstractResponseDao {
	private String username;
	private String nombres;
	private String apellidos;
	private Date fechaNacimiento;
	private String correo;
	private Date ultimoReseteo;
	private String tipoMedioContacto1;
	private String medioContacto1;
	private String tipoDocumentoIdentidad;
	private String documentoIdentidad;
	private String pais;
	private String provincia;
	private String ciudad;
	private List<RolUsuarioResponseDao> rolUsuarioCollection;
	private TrabajadorResponseDao idTrabajador;
	private List<ClienteResponseDao> clienteCollection;
	// private List<ProveedorResponseDao> proveedorCollection;

	public UsuarioResponseDao() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public List<RolUsuarioResponseDao> getRolUsuarioCollection() {
		return rolUsuarioCollection;
	}

	public void setRolUsuarioCollection(List<RolUsuarioResponseDao> rolUsuarioCollection) {
		this.rolUsuarioCollection = rolUsuarioCollection;
	}

	public TrabajadorResponseDao getIdTrabajador() {
		return idTrabajador;
	}

	public void setIdTrabajador(TrabajadorResponseDao idTrabajador) {
		this.idTrabajador = idTrabajador;
	}

	public List<ClienteResponseDao> getClienteCollection() {
		return clienteCollection;
	}

	public void setClienteCollection(List<ClienteResponseDao> clienteCollection) {
		this.clienteCollection = clienteCollection;
	}

	// public List<ProveedorResponseDao> getProveedorCollection() {
	// 	return proveedorCollection;
	// }

	// public void setProveedorCollection(List<ProveedorResponseDao> proveedorCollection) {
	// 	this.proveedorCollection = proveedorCollection;
	// }

}
