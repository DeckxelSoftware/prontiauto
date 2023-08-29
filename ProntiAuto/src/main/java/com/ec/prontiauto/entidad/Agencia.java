package com.ec.prontiauto.entidad;

import java.util.List;

import javax.persistence.*;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "agencia")
public class Agencia extends AbstractEntities {

	@Column(name = "nombre", nullable = false, length = 255)
	@CsvBindByName(column = "nombre")
	private String nombre;

	@Column(name = "direccion", nullable = false, length = 255)
	@CsvBindByName(column = "direccion")
	private String direccion;

	@Column(name = "ciudad", nullable = false, length = 255)
	@CsvBindByName(column = "ciudad")
	private String ciudad;

	@JsonIgnore
	@OneToMany(mappedBy = "idAgencia")
	private List<Supervisor> supervisorCollection;

	@JsonIgnore
	@OneToMany(mappedBy = "idAgencia")
	private List<Vendedor> vendedorCollection;

	@JsonIgnore
	@OneToMany(mappedBy = "idAgencia")
	private List<HistorialLaboral> HistorialLaboralCollection;

	@ManyToOne
	@JoinColumn(name = "\"idRegion\"", referencedColumnName = "id")
	private Region idRegion;

	@Transient
	@CsvBindByName(column = "id_region")
	private Integer idRegion1;

	@OneToMany(mappedBy = "idAgencia")
	private List<Trabajador> trabajadorCollection;

	@OneToOne(mappedBy = "agencia")
	private CabeceraCompra cabecera;

	public Agencia() {
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public List<Supervisor> getSupervisorCollection() {
		return supervisorCollection;
	}

	public void setSupervisorCollection(List<Supervisor> supervisorCollection) {
		this.supervisorCollection = supervisorCollection;
	}

	public List<Vendedor> getVendedorCollection() {
		return vendedorCollection;
	}

	public void setVendedorCollection(List<Vendedor> vendedorCollection) {
		this.vendedorCollection = vendedorCollection;
	}

	public List<HistorialLaboral> getHistorialLaboralCollection() {
		return HistorialLaboralCollection;
	}

	public void setHistorialLaboralCollection(List<HistorialLaboral> historialLaboralCollection) {
		HistorialLaboralCollection = historialLaboralCollection;
	}

	public Agencia setValoresDiferentes(Agencia registroAntiguo,
			Agencia registroActualizar) {
		if (registroActualizar.getNombre() != null) {
			registroAntiguo.setNombre(registroActualizar.getNombre());
		}
		if (registroActualizar.getDireccion() != null) {
			registroAntiguo.setDireccion(registroActualizar.getDireccion());
		}
		if (registroActualizar.getCiudad() != null) {
			registroAntiguo.setCiudad(registroActualizar.getCiudad());
		}

		if (registroActualizar.getIdRegion() != null) {
			registroAntiguo.setIdRegion(registroActualizar.getIdRegion());
		}

		if (registroActualizar.getSisHabilitado() != null) {
			registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
		}
		return registroAntiguo;
	}

	public Region getIdRegion() {
		return idRegion;
	}

	public void setIdRegion(Region idRegion) {
		this.idRegion = idRegion;
	}

	public Integer getIdRegion1() {
		return idRegion1;
	}

	public void setIdRegion1(Integer idRegion1) {
		this.idRegion1 = idRegion1;
	}

	public List<Trabajador> getTrabajadorCollection() {
		return trabajadorCollection;
	}

	public void setTrabajadorCollection(List<Trabajador> trabajadorCollection) {
		this.trabajadorCollection = trabajadorCollection;
	}

	public CabeceraCompra getCabecera() {
		return cabecera;
	}

	public void setCabecera(CabeceraCompra cabecera) {
		this.cabecera = cabecera;
	}
}
