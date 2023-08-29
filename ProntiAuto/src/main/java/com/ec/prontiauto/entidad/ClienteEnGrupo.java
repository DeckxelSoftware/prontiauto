package com.ec.prontiauto.entidad;

import java.util.List;

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
@Table(name = "cliente_en_grupo", uniqueConstraints = @UniqueConstraint(columnNames = { "\"idCliente\"",
		"\"idGrupo\"" }))
public class ClienteEnGrupo extends AbstractEntities {

	@ManyToOne
	@JoinColumn(name = "\"idCliente\"", referencedColumnName = "id", nullable = false)
	@CsvBindByName(column = "id_cliente")
	private Cliente idCliente;

	@ManyToOne
	@JoinColumn(name = "\"idGrupo\"", referencedColumnName = "id", nullable = false)
	@CsvBindByName(column = "id_grupo")
	private Grupo idGrupo;

	@Transient
	@CsvBindByName(column = "id_cliente")
	private Integer idCliente1;

	@Transient
	@CsvBindByName(column = "id_grupo")
	private Integer idGrupo1;

	@JsonIgnore
	@OneToMany(mappedBy = "idClienteEnGrupo")
	private List<Contrato> contratoCollection;

	public ClienteEnGrupo() {
	}

	public Cliente getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Cliente idCliente) {
		this.idCliente = idCliente;
	}

	public Grupo getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Grupo idGrupo) {
		this.idGrupo = idGrupo;
	}

	public Integer getIdCliente1() {
		return idCliente1;
	}

	public void setIdCliente1(Integer idCliente1) {
		this.idCliente1 = idCliente1;
	}

	public Integer getIdGrupo1() {
		return idGrupo1;
	}

	public void setIdGrupo1(Integer idGrupo1) {
		this.idGrupo1 = idGrupo1;
	}

	public List<Contrato> getContratoCollection() {
		return contratoCollection;
	}

	public void setContratoCollection(List<Contrato> contratoCollection) {
		this.contratoCollection = contratoCollection;
	}

	public ClienteEnGrupo setValoresDiferentes(ClienteEnGrupo registroAntiguo, ClienteEnGrupo registroActualizar) {
		if (registroActualizar.getIdCliente().getId() != null) {
			registroAntiguo.setIdCliente(registroActualizar.getIdCliente());
		}
		if (registroActualizar.getIdGrupo().getId() != null) {
			registroAntiguo.setIdGrupo(registroActualizar.getIdGrupo());
		}
		if (registroActualizar.getSisHabilitado() != null) {
			registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
		}
		return registroAntiguo;
	}

}
