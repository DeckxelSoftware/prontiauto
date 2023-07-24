package com.ec.prontiauto.dao;

import com.ec.prontiauto.abstracts.AbstractRequestDao;

public class EmpresaRequestDao extends AbstractRequestDao {

	private String nombreComercial;
	private String razonSocial;
	private String rucEmpresa;
	private String direccionEmpresa;
	private String telefonoEmpresa;
	private String documentoRepresentanteLegal;
	private String nombreRepresentanteLegal;
	private String nombreContador;
	private String rucContador;
	private String telefonoContador;
	private String correoEmpresa;
	private String correoContador;
	private String correoRepresentanteLegal;
	private String tipoEmpresa;
	private String claseContribuyente;
	private String obligadoLlevarContabilidad;
	private String agenteRetencion;

	public EmpresaRequestDao() {
	}

	public String getNombreComercial() {
		return nombreComercial;
	}

	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getClaseContribuyente() {
		return claseContribuyente;
	}

	public void setClaseContribuyente(String claseContribuyente) {
		this.claseContribuyente = claseContribuyente;
	}

	public String getObligadoLlevarContabilidad() {
		return obligadoLlevarContabilidad;
	}

	public void setObligadoLlevarContabilidad(String obligadoLlevarContabilidad) {
		this.obligadoLlevarContabilidad = obligadoLlevarContabilidad;
	}

	public String getAgenteRetencion() {
		return agenteRetencion;
	}

	public void setAgenteRetencion(String agenteRetencion) {
		this.agenteRetencion = agenteRetencion;
	}

	public String getRucEmpresa() {
		return rucEmpresa;
	}

	public void setRucEmpresa(String rucEmpresa) {
		this.rucEmpresa = rucEmpresa;
	}

	public String getDireccionEmpresa() {
		return direccionEmpresa;
	}

	public void setDireccionEmpresa(String direccionEmpresa) {
		this.direccionEmpresa = direccionEmpresa;
	}

	public String getTelefonoEmpresa() {
		return telefonoEmpresa;
	}

	public void setTelefonoEmpresa(String telefonoEmpresa) {
		this.telefonoEmpresa = telefonoEmpresa;
	}

	public String getDocumentoRepresentanteLegal() {
		return documentoRepresentanteLegal;
	}

	public void setDocumentoRepresentanteLegal(String documentoRepresentanteLegal) {
		this.documentoRepresentanteLegal = documentoRepresentanteLegal;
	}

	public String getNombreRepresentanteLegal() {
		return nombreRepresentanteLegal;
	}

	public void setNombreRepresentanteLegal(String nombreRepresentanteLegal) {
		this.nombreRepresentanteLegal = nombreRepresentanteLegal;
	}

	public String getNombreContador() {
		return nombreContador;
	}

	public void setNombreContador(String nombreContador) {
		this.nombreContador = nombreContador;
	}

	public String getRucContador() {
		return rucContador;
	}

	public void setRucContador(String rucContador) {
		this.rucContador = rucContador;
	}

	public String getTelefonoContador() {
		return telefonoContador;
	}

	public void setTelefonoContador(String telefonoContador) {
		this.telefonoContador = telefonoContador;
	}

	public String getCorreoEmpresa() {
		return correoEmpresa;
	}

	public void setCorreoEmpresa(String correoEmpresa) {
		this.correoEmpresa = correoEmpresa;
	}

	public String getCorreoContador() {
		return correoContador;
	}

	public void setCorreoContador(String correoContador) {
		this.correoContador = correoContador;
	}

	public String getCorreoRepresentanteLegal() {
		return correoRepresentanteLegal;
	}

	public void setCorreoRepresentanteLegal(String correoRepresentanteLegal) {
		this.correoRepresentanteLegal = correoRepresentanteLegal;
	}

	public String getTipoEmpresa() {
		return tipoEmpresa;
	}

	public void setTipoEmpresa(String tipoEmpresa) {
		this.tipoEmpresa = tipoEmpresa;
	}

}
