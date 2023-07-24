package com.ec.prontiauto.entidad;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "banco")
public class Banco extends AbstractEntities {

	@Column(name = "nombre", length = 50, nullable = false)
	@CsvBindByName(column = "nombre")
	private String nombre;

	@OneToMany(mappedBy = "idBanco")
	private List<CuentaBancariaEmpresa> cuentaBancariaEmpresaCollection;

	public Banco() {
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<CuentaBancariaEmpresa> getCuentaBancariaEmpresaCollection() {
		return cuentaBancariaEmpresaCollection;
	}

	public void setCuentaBancariaEmpresaCollection(List<CuentaBancariaEmpresa> cuentaBancariaEmpresaCollection) {
		this.cuentaBancariaEmpresaCollection = cuentaBancariaEmpresaCollection;
	}

	public Banco setValoresDiferentes(Banco registroAntiguo, Banco registroActualizar) {
		if (registroActualizar.getNombre() != null) {
			registroAntiguo.setNombre(registroActualizar.getNombre());
		}
		if (registroActualizar.getSisHabilitado() != null) {
			registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
		}
		return registroAntiguo;
	}

}
