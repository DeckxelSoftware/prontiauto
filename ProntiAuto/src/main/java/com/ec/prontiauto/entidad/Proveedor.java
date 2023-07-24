package com.ec.prontiauto.entidad;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "proveedor", uniqueConstraints = @UniqueConstraint(columnNames = { "\"idUsuario\"", "\"idEmpresa\"" }))
public class Proveedor extends AbstractEntities {

	@Column(name = "\"tipoProveedor\"", length = 2, nullable = false)
	@CsvBindByName(column = "tipo_proveedor")
	private String tipoProveedor;

	@Column(name = "\"nombrePersonaReferencia\"", nullable = false)
	@CsvBindByName(column = "nombre_persona_referencia")
	private String nombrePersonaReferencia;

	@Column(name = "\"contactoReferencia\"", nullable = false)
	@CsvBindByName(column = "contacto_referencia")
	private String contactoReferencia;

	@Column(name = "\"tipoCuentaContable\"", nullable = false)
	@CsvBindByName(column = "tipo_cuenta_contable")
	private String tipoCuentaContable;

	@Column(name = "\"claseContribuyente\"")
	@CsvBindByName(column = "clase_contribuyente")
	private String claseContribuyente;

	@Column(name = "\"obligadoLlevarContabilidad\"")
	@CsvBindByName(column = "obligado_llevar_contabilidad")
	private String obligadoLlevarContabilidad;

	@Column(name = "\"agenteRetencion\"")
	@CsvBindByName(column = "agente_retencion")
	private String agenteRetencion;

	@ManyToOne
	@JoinColumn(name = "\"idUsuario\"", referencedColumnName = "id", nullable = true)
	private Usuario idUsuario;

	@ManyToOne
	@JoinColumn(name = "\"idEmpresa\"", referencedColumnName = "id", nullable = true)
	private Empresa idEmpresa;

	@JsonIgnore
	@OneToMany(mappedBy = "idProveedor")
	private List<OrdenCompra> ordenCompraCollection;

	@Transient
	@CsvBindByName(column = "id_usuario")
	private Integer idUsuario1;

	@Transient
	@CsvBindByName(column = "id_empresa")
	private Integer idEmpresa1;

	public String getTipoProveedor() {
		return tipoProveedor;
	}

	public void setTipoProveedor(String tipoProveedor) {
		this.tipoProveedor = tipoProveedor;
	}

	public String getNombrePersonaReferencia() {
		return nombrePersonaReferencia;
	}

	public void setNombrePersonaReferencia(String nombrePersonaReferencia) {
		this.nombrePersonaReferencia = nombrePersonaReferencia;
	}

	public String getContactoReferencia() {
		return contactoReferencia;
	}

	public void setContactoReferencia(String contactoReferencia) {
		this.contactoReferencia = contactoReferencia;
	}

	public String getTipoCuentaContable() {
		return tipoCuentaContable;
	}

	public void setTipoCuentaContable(String tipoCuentaContable) {
		this.tipoCuentaContable = tipoCuentaContable;
	}

	public String getClaseContribuyente() {
		return claseContribuyente;
	}

	public void setClaseContribuyente(String claseContribuyente) {
		this.claseContribuyente = claseContribuyente;
	}

	public String getAgenteRetencion() {
		return agenteRetencion;
	}

	public void setAgenteRetencion(String agenteRetencion) {
		this.agenteRetencion = agenteRetencion;
	}

	public String getObligadoLlevarContabilidad() {
		return obligadoLlevarContabilidad;
	}

	public void setObligadoLlevarContabilidad(String obligadoLlevarContabilidad) {
		this.obligadoLlevarContabilidad = obligadoLlevarContabilidad;
	}

	public Usuario getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Usuario idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Empresa getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Empresa idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public List<OrdenCompra> getOrdenCompraCollection() {
		return ordenCompraCollection;
	}

	public void setOrdenCompraCollection(List<OrdenCompra> ordenCompraCollection) {
		this.ordenCompraCollection = ordenCompraCollection;
	}

	public Integer getIdUsuario1() {
		return idUsuario1;
	}

	public void setIdUsuario1(Integer idUsuario1) {
		this.idUsuario1 = idUsuario1;
	}

	public Integer getIdEmpresa1() {
		return idEmpresa1;
	}

	public void setIdEmpresa1(Integer idEmpresa1) {
		this.idEmpresa1 = idEmpresa1;
	}

	public Proveedor setValoresDiferentes(Proveedor registroAntiguo, Proveedor registroActualizar) {
		if (registroActualizar.getTipoProveedor() != null) {
			registroAntiguo.setTipoProveedor(registroActualizar.getTipoProveedor());
		}

		if (registroActualizar.getNombrePersonaReferencia() != null) {
			registroAntiguo.setNombrePersonaReferencia(registroActualizar.getNombrePersonaReferencia());
		}

		if (registroActualizar.getContactoReferencia() != null) {
			registroAntiguo.setContactoReferencia(registroActualizar.getContactoReferencia());
		}

		if (registroActualizar.getTipoCuentaContable() != null) {
			registroAntiguo.setTipoCuentaContable(registroActualizar.getTipoCuentaContable());
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
