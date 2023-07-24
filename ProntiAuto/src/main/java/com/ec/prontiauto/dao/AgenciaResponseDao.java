package com.ec.prontiauto.dao;

import java.util.List;

import com.ec.prontiauto.abstracts.AbstractResponseDao;

public class AgenciaResponseDao extends AbstractResponseDao {

	private String nombre;
	private String direccion;
	private String ciudad;
	private List<SupervisorResponseDao> supervisorCollection;
	private List<VendedorResponseDao> vendedorCollection;
	private RegionResponseDao idRegion;
	private List<TrabajadorResponseDao> trabajadorCollection;

	public AgenciaResponseDao() {
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

	public List<SupervisorResponseDao> getSupervisorCollection() {
		return supervisorCollection;
	}

	public void setSupervisorCollection(List<SupervisorResponseDao> supervisorCollection) {
		this.supervisorCollection = supervisorCollection;
	}

	public List<VendedorResponseDao> getVendedorCollection() {
		return vendedorCollection;
	}

	public void setVendedorCollection(List<VendedorResponseDao> vendedorCollection) {
		this.vendedorCollection = vendedorCollection;
	}

	public RegionResponseDao getIdRegion() {
		return idRegion;
	}

	public void setIdRegion(RegionResponseDao idRegion) {
		this.idRegion = idRegion;
	}

	public List<TrabajadorResponseDao> getTrabajadorCollection() {
		return trabajadorCollection;
	}

	public void setTrabajadorCollection(List<TrabajadorResponseDao> trabajadorCollection) {
		this.trabajadorCollection = trabajadorCollection;
	}

}
