package com.ec.prontiauto.entidad;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "cheque")
public class Cheque extends AbstractEntities {

	
	@Column(name = "\"numeroCheque\"", nullable = true)
	@CsvBindByName(column = "numero_cheque")
	private Integer numeroCheque;

	@Column(name = "\"estadoCheque\"", nullable = true, length = 2)
	@CsvBindByName(column = "estado_cheque")
	private String estadoCheque;

	@ManyToOne
	@JoinColumn(name = "\"idChequera\"", referencedColumnName = "id", nullable = true)
	private Chequera idChequera;
	
	@Transient
	@CsvBindByName(column = "id_Chequera")
	private Integer idChequera1;
	
	
	@JsonIgnore
	@OneToMany(mappedBy = "idCheque")
	private List<AsientoContableCabecera> asientoContableCabeceraCollection;

	
	public Cheque() {
	}

	public Integer getNumeroCheque() {
		return numeroCheque;
	}

	public void setNumeroCheque(Integer numeroCheque) {
		this.numeroCheque = numeroCheque;
	}

	public String getEstadoCheque() {
		return estadoCheque;
	}

	public void setEstadoCheque(String estadoCheque) {
		this.estadoCheque = estadoCheque;
	}

	public Chequera getIdChequera() {
		return idChequera;
	}

	public void setIdChequera(Chequera idChequera) {
		this.idChequera = idChequera;
	}

	public Integer getIdChequera1() {
		return idChequera1;
	}

	public void setIdChequera1(Integer idChequera1) {
		this.idChequera1 = idChequera1;
	}

	public List<AsientoContableCabecera> getAsientoContableCabeceraCollection() {
		return asientoContableCabeceraCollection;
	}

	public void setAsientoContableCabeceraCollection(List<AsientoContableCabecera> asientoContableCabeceraCollection) {
		this.asientoContableCabeceraCollection = asientoContableCabeceraCollection;
	}

	public Cheque setValoresDiferentes(Cheque registroAntiguo, Cheque registroActualizar) {
		
		if (registroActualizar.getNumeroCheque() != null) {
			registroAntiguo.setNumeroCheque(registroActualizar.getNumeroCheque());
		}
		if (registroActualizar.getEstadoCheque() != null) {
			registroAntiguo.setEstadoCheque(registroActualizar.getEstadoCheque());
		}
		if (registroActualizar.getIdChequera() != null && registroActualizar.getIdChequera().getId() != null) {
			registroAntiguo.setIdChequera(registroActualizar.getIdChequera());
		}
		if (registroActualizar.getSisHabilitado() != null) {
			registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
		}
		return registroAntiguo;
	}

}
