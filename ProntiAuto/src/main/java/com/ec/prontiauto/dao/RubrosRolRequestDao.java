package com.ec.prontiauto.dao;

import com.ec.prontiauto.abstracts.AbstractRequestDao;

public class RubrosRolRequestDao extends AbstractRequestDao {

	private String codigoAuxiliar;
	private String nombre;
	private String nombreAuxiliarUno;
	private String nombreAuxiliarDos;
	private String unidad;
	private String calculaIess;
	private String calculaRenta;
	private String calculaDecTercero;
	private String calculaDecCuarto;
	private String calculaFReserva;
	private String calculaVacaciones;
	private String seSuma;

	public RubrosRolRequestDao() {

	}

	public String getCodigoAuxiliar() {
		return codigoAuxiliar;
	}

	public void setCodigoAuxiliar(String codigoAuxiliar) {
		this.codigoAuxiliar = codigoAuxiliar;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombreAuxiliarUno() {
		return nombreAuxiliarUno;
	}

	public void setNombreAuxiliarUno(String nombreAuxiliarUno) {
		this.nombreAuxiliarUno = nombreAuxiliarUno;
	}

	public String getNombreAuxiliarDos() {
		return nombreAuxiliarDos;
	}

	public void setNombreAuxiliarDos(String nombreAuxiliarDos) {
		this.nombreAuxiliarDos = nombreAuxiliarDos;
	}

	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	public String getCalculaIess() {
		return calculaIess;
	}

	public void setCalculaIess(String calculaIess) {
		this.calculaIess = calculaIess;
	}

	public String getCalculaRenta() {
		return calculaRenta;
	}

	public void setCalculaRenta(String calculaRenta) {
		this.calculaRenta = calculaRenta;
	}

	public String getCalculaDecTercero() {
		return calculaDecTercero;
	}

	public void setCalculaDecTercero(String calculaDecTercero) {
		this.calculaDecTercero = calculaDecTercero;
	}

	public String getCalculaDecCuarto() {
		return calculaDecCuarto;
	}

	public void setCalculaDecCuarto(String calculaDecCuarto) {
		this.calculaDecCuarto = calculaDecCuarto;
	}

	public String getCalculaFReserva() {
		return calculaFReserva;
	}

	public void setCalculaFReserva(String calculaFReserva) {
		this.calculaFReserva = calculaFReserva;
	}

	public String getCalculaVacaciones() {
		return calculaVacaciones;
	}

	public void setCalculaVacaciones(String calculaVacaciones) {
		this.calculaVacaciones = calculaVacaciones;
	}

	public String getSeSuma() {
		return seSuma;
	}

	public void setSeSuma(String seSuma) {
		this.seSuma = seSuma;
	}

}
