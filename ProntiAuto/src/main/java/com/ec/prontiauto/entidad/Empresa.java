package com.ec.prontiauto.entidad;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "empresa")
public class Empresa extends AbstractEntities {

	@Column(name = "\"nombreComercial\"", length = 255, nullable = false)
	@CsvBindByName(column = "nombre_comercial")
	private String nombreComercial;

	@Column(name = "\"razonSocial\"", length = 255, nullable = false)
	@CsvBindByName(column = "razon_social")
	private String razonSocial;

	@Column(name = "\"rucEmpresa\"", length = 255, nullable = false, unique = true)
	@CsvBindByName(column = "ruc_empresa")
	private String rucEmpresa;

	@Column(name = "\"direccionEmpresa\"", length = 255, nullable = false)
	@CsvBindByName(column = "direccion_empresa")
	private String direccionEmpresa;

	@Column(name = "\"telefonoEmpresa\"", length = 10, nullable = false)
	@CsvBindByName(column = "telefono_empresa")
	private String telefonoEmpresa;

	@Column(name = "\"documentoRepresentanteLegal\"", length = 10, nullable = false)
	@CsvBindByName(column = "documento_representante_legal")
	private String documentoRepresentanteLegal;

	@Column(name = "\"nombreRepresentanteLegal\"", length = 255, nullable = false)
	@CsvBindByName(column = "nombre_representante_legal")
	private String nombreRepresentanteLegal;

	@Column(name = "\"nombreContador\"", length = 255, nullable = false)
	@CsvBindByName(column = "nombre_contador")
	private String nombreContador;

	@Column(name = "\"rucContador\"", length = 255, nullable = false)
	@CsvBindByName(column = "ruc_contador")
	private String rucContador;

	@Column(name = "\"telefonoContador\"", length = 10, nullable = false)
	@CsvBindByName(column = "telefono_contador")
	private String telefonoContador;

	@Column(name = "\"correoEmpresa\"", length = 255, nullable = false)
	@CsvBindByName(column = "correo_empresa")
	private String correoEmpresa;

	@Column(name = "\"correoContador\"", length = 255, nullable = false)
	@CsvBindByName(column = "correo_contador")
	private String correoContador;

	@Column(name = "\"correoRepresentanteLegal\"", length = 255, nullable = false)
	@CsvBindByName(column = "correo_representante_legal")
	private String correoRepresentanteLegal;

	@Column(name = "\"tipoEmpresa\"", length = 255, nullable = false)
	@CsvBindByName(column = "tipo_empresa")
	private String tipoEmpresa;

	@Column(name = "\"claseContribuyente\"", length = 255, nullable = false)
	@CsvBindByName(column = "clase_contribuyente")
	private String claseContribuyente;

	@Column(name = "\"obligadoLlevarContabilidad\"", length = 1, nullable = false)
	@CsvBindByName(column = "obligado_llevar_contabilidad")
	private String obligadoLlevarContabilidad;

	@Column(name = "\"agenteRetencion\"", length = 1, nullable = false)
	@CsvBindByName(column = "agente_retencion")
	private String agenteRetencion;

	@JsonIgnore
	@OneToMany(mappedBy = "idEmpresa")
	private List<CuentaBancariaEmpresa> cuentaBancariaEmpresaCollection;

	@JsonIgnore
	@OneToMany(mappedBy = "idEmpresa")
	private List<Cliente> clienteCollection;

	@JsonIgnore
	@OneToMany(mappedBy = "idEmpresa")
	private List<Proveedor> proveedorCollection;

	@Column(name = "\"regimenGeneral\"", nullable = false, columnDefinition = "boolean default false")
	@CsvBindByName(column = "regimen_general")
	private Boolean regimenGeneral;

	@Column(name = "\"rimpeEmprendedor\"", nullable = false, columnDefinition = "boolean default false")
	@CsvBindByName(column = "rimpe_emprendedor")
	private Boolean rimpeEmprendedor;

	@Column(name = "\"rimpePopular\"", nullable = false, columnDefinition = "boolean default false")
	@CsvBindByName(column = "rimpe_popular")
	private Boolean rimpePopular;

	public Empresa() {
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

	public List<CuentaBancariaEmpresa> getCuentaBancariaEmpresaCollection() {
		return cuentaBancariaEmpresaCollection;
	}

	public void setCuentaBancariaEmpresaCollection(List<CuentaBancariaEmpresa> cuentaBancariaEmpresaCollection) {
		this.cuentaBancariaEmpresaCollection = cuentaBancariaEmpresaCollection;
	}

	public List<Cliente> getClienteCollection() {
		return clienteCollection;
	}

	public void setClienteCollection(List<Cliente> clienteCollection) {
		this.clienteCollection = clienteCollection;
	}

	public List<Proveedor> getProveedorCollection() {
		return this.proveedorCollection;
	}

	public void setProveedorCollection(List<Proveedor> proveedorCollection) {
		this.proveedorCollection = proveedorCollection;
	}

	public Empresa setValoresDiferentes(Empresa registroAntiguo, Empresa registroActualizar) {
		if (registroActualizar.getNombreComercial() != null) {
			registroAntiguo.setNombreComercial(registroActualizar.getNombreComercial());
		}
		if (registroActualizar.getRazonSocial() != null) {
			registroAntiguo.setRazonSocial(registroActualizar.getRazonSocial());
		}
		if (registroActualizar.getDireccionEmpresa() != null) {
			registroAntiguo.setDireccionEmpresa(registroActualizar.getDireccionEmpresa());
		}
		if (registroActualizar.getTelefonoEmpresa() != null) {
			registroAntiguo.setTelefonoEmpresa(registroActualizar.getTelefonoEmpresa());
		}
		if (registroActualizar.getDocumentoRepresentanteLegal() != null) {
			registroAntiguo.setDocumentoRepresentanteLegal(registroActualizar.getDocumentoRepresentanteLegal());
		}
		if (registroActualizar.getNombreRepresentanteLegal() != null) {
			registroAntiguo.setNombreRepresentanteLegal(registroActualizar.getNombreRepresentanteLegal());
		}
		if (registroActualizar.getNombreContador() != null) {
			registroAntiguo.setNombreContador(registroActualizar.getNombreContador());
		}
		if (registroActualizar.getRucContador() != null) {
			registroAntiguo.setRucContador(registroActualizar.getRucContador());
		}
		if (registroActualizar.getTelefonoContador() != null) {
			registroAntiguo.setTelefonoContador(registroActualizar.getTelefonoContador());
		}
		if (registroActualizar.getCorreoEmpresa() != null) {
			registroAntiguo.setCorreoEmpresa(registroActualizar.getCorreoEmpresa());
		}
		if (registroActualizar.getCorreoContador() != null) {
			registroAntiguo.setCorreoContador(registroActualizar.getCorreoContador());
		}
		if (registroActualizar.getCorreoRepresentanteLegal() != null) {
			registroAntiguo.setCorreoRepresentanteLegal(registroActualizar.getCorreoRepresentanteLegal());
		}
		if (registroActualizar.getTipoEmpresa() != null) {
			registroAntiguo.setTipoEmpresa(registroActualizar.getTipoEmpresa());
		}
		if (registroActualizar.getSisHabilitado() != null) {
			registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
		}
		if (registroActualizar.getClaseContribuyente() != null) {
			registroAntiguo.setClaseContribuyente(registroActualizar.getClaseContribuyente());
		}
		if (registroActualizar.getObligadoLlevarContabilidad() != null) {
			registroAntiguo.setObligadoLlevarContabilidad(registroActualizar.getObligadoLlevarContabilidad());
		}
		if (registroActualizar.getAgenteRetencion() != null) {
			registroAntiguo.setAgenteRetencion(registroActualizar.getAgenteRetencion());
		}
		return registroAntiguo;
	}

}
