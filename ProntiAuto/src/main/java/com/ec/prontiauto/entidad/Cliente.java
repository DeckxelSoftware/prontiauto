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
import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "cliente", uniqueConstraints = @UniqueConstraint(columnNames = { "\"idUsuario\"", "\"idEmpresa\"" }))
public class Cliente extends AbstractEntities {

	@Column(name = "\"tipoCliente\"", length = 2, nullable = false)
	@CsvBindByName(column = "tipo_cliente")
	private String tipoCliente;

	@ManyToOne
	@JoinColumn(name = "\"idUsuario\"", referencedColumnName = "id")
	private Usuario idUsuario;

	@ManyToOne
	@JoinColumn(name = "\"idEmpresa\"", referencedColumnName = "id")
	private Empresa idEmpresa;

	@OneToMany(mappedBy = "idCliente")
	private List<ClienteEnGrupo> collectionClienteEnGrupo;

	@Transient
	@CsvBindByName(column = "id_usuario")
	private Integer idUsuario1;

	@Transient
	@CsvBindByName(column = "id_empresa")
	private Integer idEmpresa1;

	public Cliente() {
	}

	public String getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public Usuario getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Usuario idUsuario) {
		this.idUsuario = idUsuario;
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

	public Empresa getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Empresa idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public List<ClienteEnGrupo> getCollectionClienteEnGrupo() {
		return collectionClienteEnGrupo;
	}

	public void setCollectionClienteEnGrupo(List<ClienteEnGrupo> collectionClienteEnGrupo) {
		this.collectionClienteEnGrupo = collectionClienteEnGrupo;
	}

	public Cliente setValoresDiferentes(Cliente registroAntiguo, Cliente registroActualizar) {
		if (registroActualizar.getTipoCliente() != null) {
			registroAntiguo.setTipoCliente(registroActualizar.getTipoCliente());
		}
		if (registroActualizar.getSisHabilitado() != null) {
			registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
		}
		if (registroActualizar.getIdUsuario()!= null && registroActualizar.getIdUsuario().getId() != null ) {
			registroAntiguo.setIdUsuario(registroActualizar.getIdUsuario());
		}
		if (registroActualizar.getIdEmpresa() != null && registroActualizar.getIdEmpresa().getId() != null) {
			registroAntiguo.setIdEmpresa(registroActualizar.getIdEmpresa());
		} else {
			registroAntiguo.setIdEmpresa(null);
		}
		return registroAntiguo;
	}

}
