package com.ec.prontiauto.entidad;

import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "vendedor")
public class Vendedor extends AbstractEntities {

	@ManyToOne
	@JoinColumn(name = "\"idAgencia\"", referencedColumnName = "id", nullable = false)
	private Agencia idAgencia;

	@OneToOne
	@JoinColumn(name = "\"idTrabajador\"", referencedColumnName = "id")
	private Trabajador idTrabajador;

	@OneToOne
	@JoinColumn(name = "\"idProveedor\"", referencedColumnName = "id")
	private Proveedor idProveedor;

	@Transient
	@CsvBindByName(column = "id_agencia")
	private Integer idAgencia1;

	@Transient
	@CsvBindByName(column = "id_trabajador")
	private Integer idTrabajador1;

	@JsonIgnore
	@OneToMany(mappedBy = "idVendedor")
	private List<Contrato> contratoCollection;

	public Vendedor() {

	}

	public Agencia getIdAgencia() {
		return idAgencia;
	}

	public void setIdAgencia(Agencia idAgencia) {
		this.idAgencia = idAgencia;
	}

	public Integer getIdAgencia1() {
		return idAgencia1;
	}

	public void setIdAgencia1(Integer idAgencia1) {
		this.idAgencia1 = idAgencia1;
	}

	public Integer getIdTrabajador1() {
		return idTrabajador1;
	}

	public void setIdTrabajador1(Integer idTrabajador1) {
		this.idTrabajador1 = idTrabajador1;
	}

	public Trabajador getIdTrabajador() {
		return idTrabajador;
	}

	public void setIdTrabajador(Trabajador idTrabajador) {
		this.idTrabajador = idTrabajador;
	}

	public List<Contrato> getContratoCollection() {
		return contratoCollection;
	}

	public void setContratoCollection(List<Contrato> contratoCollection) {
		this.contratoCollection = contratoCollection;
	}

	public Vendedor setValoresDiferentes(Vendedor registroAntiguo,
			Vendedor registroActualizar) {

		if (registroActualizar.getSisHabilitado() != null) {
			registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
		}

		if (registroActualizar.getIdTrabajador().getId() != null) {
			registroAntiguo.setIdTrabajador(registroActualizar.getIdTrabajador());
		}
		if (registroActualizar.getIdAgencia().getId() != null) {
			registroAntiguo.setIdAgencia(registroActualizar.getIdAgencia());
		}
		if (Objects.nonNull(registroActualizar.getIdProveedor().getId())) {
			registroAntiguo.setIdProveedor(registroActualizar.getIdProveedor());
		}


		return registroAntiguo;
	}

	public Proveedor getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(Proveedor idProveedor) {
		this.idProveedor = idProveedor;
	}
}
