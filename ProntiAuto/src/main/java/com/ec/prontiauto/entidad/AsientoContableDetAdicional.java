package com.ec.prontiauto.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "asiento_contable_det_adicional")
public class AsientoContableDetAdicional extends AbstractEntities {

	
	@Column(name = "llave", nullable = false, length = 255, unique = true)
	@CsvBindByName(column = "llave")
	private String llave;

	@Column(name = "valor", nullable = false, length = 255)
	@CsvBindByName(column = "valor")
	private String valor;
	
	@ManyToOne
	@JoinColumn(name = "\"idAsientoContableCabecera\"", referencedColumnName = "id")
	private AsientoContableCabecera idAsientoContableCabecera;

	@Transient
	@CsvBindByName(column = "id_asiento_contable_cabecera")
	private Integer idAsientoContableCabecera1;
	
	
	
	public AsientoContableDetAdicional() {
	}
	
	public String getLlave() {
		return llave;
	}

	public void setLlave(String llave) {
		this.llave = llave;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public AsientoContableCabecera getIdAsientoContableCabecera() {
		return idAsientoContableCabecera;
	}

	public void setIdAsientoContableCabecera(AsientoContableCabecera idAsientoContableCabecera) {
		this.idAsientoContableCabecera = idAsientoContableCabecera;
	}

	public Integer getIdAsientoContableCabecera1() {
		return idAsientoContableCabecera1;
	}

	public void setIdAsientoContableCabecera1(Integer idAsientoContableCabecera1) {
		this.idAsientoContableCabecera1 = idAsientoContableCabecera1;
	}

	public AsientoContableDetAdicional setValoresDiferentes(AsientoContableDetAdicional registroAntiguo, AsientoContableDetAdicional registroActualizar) {
		
		if (registroActualizar.getLlave() != null) {
			registroAntiguo.setLlave(registroActualizar.getLlave());
		}
		if (registroActualizar.getValor() != null) {
			registroAntiguo.setValor(registroActualizar.getValor());
		}
		if (registroActualizar.getSisHabilitado() != null) {
			registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
		}
		if (registroActualizar.getIdAsientoContableCabecera() != null
				&& registroActualizar.getIdAsientoContableCabecera().getId() != null) {
			registroAntiguo.setIdAsientoContableCabecera(registroActualizar.getIdAsientoContableCabecera());
		}
		return registroAntiguo;
	}

}
