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
@Table(name = "informacion_financiera")
public class InformacionFinanciera extends AbstractEntities {

	
	@Column(name = "\"formaPago\"", nullable = false, length = 255)
	@CsvBindByName(column = "forma_pago")
	private String formaPago;

	@ManyToOne
	@JoinColumn(name = "\"idTrabajador\"", referencedColumnName = "id", nullable = false, unique = false)
	private Trabajador idTrabajador;

	@Transient
	@CsvBindByName(column = "id_trabajador")
	private Integer idTrabajador1;
	
	@JsonIgnore
	@OneToMany(mappedBy = "idInformacionFinanciera")
	private List<CuentaBancariaEmpresa> cuentaBancariaEmpresaCollection;

	
	public InformacionFinanciera() {
	}

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	public Trabajador getIdTrabajador() {
		return idTrabajador;
	}

	public void setIdTrabajador(Trabajador idTrabajador) {
		this.idTrabajador = idTrabajador;
	}

	public Integer getIdTrabajador1() {
		return idTrabajador1;
	}

	public void setIdTrabajador1(Integer idTrabajador1) {
		this.idTrabajador1 = idTrabajador1;
	}

	public List<CuentaBancariaEmpresa> getCuentaBancariaEmpresaCollection() {
		return cuentaBancariaEmpresaCollection;
	}

	public void setCuentaBancariaEmpresaCollection(List<CuentaBancariaEmpresa> cuentaBancariaEmpresaCollection) {
		this.cuentaBancariaEmpresaCollection = cuentaBancariaEmpresaCollection;
	}

	public InformacionFinanciera setValoresDiferentes(InformacionFinanciera registroAntiguo, InformacionFinanciera registroActualizar) {
		
		if (registroActualizar.getFormaPago() != null) {
			registroAntiguo.setFormaPago(registroActualizar.getFormaPago());
		}
		if (registroActualizar.getSisHabilitado() != null) {
			registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
		}
		if (registroActualizar.getIdTrabajador() != null && registroActualizar.getIdTrabajador().getId() != null) {
			registroAntiguo.setIdTrabajador(registroActualizar.getIdTrabajador());
		}
		return registroAntiguo;
	}

}
