package com.ec.prontiauto.dao;

import java.sql.Date;

import com.ec.prontiauto.abstracts.AbstractResponseDao;

public class RegistroVacacionResponseDao extends AbstractResponseDao {

	private Date fechaDesde;
	private Date fechaHasta;
	private Integer diasTomados;
	private String numeroSolicitud;
	private Float valorTomado;
	private String estaPagado;
	private Date fechaPago;
	private String comprobantePago;
	private Float valorPagado;
	private String nombreApellidoResponsable;

	private CargoVacacionResponseDao idCargoVacacion;

	public RegistroVacacionResponseDao() {
	}

	public Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public Integer getDiasTomados() {
		return diasTomados;
	}

	public void setDiasTomados(Integer diasTomados) {
		this.diasTomados = diasTomados;
	}

	public String getNumeroSolicitud() {
		return numeroSolicitud;
	}

	public void setNumeroSolicitud(String numeroSolicitud) {
		this.numeroSolicitud = numeroSolicitud;
	}

	public Float getValorTomado() {
		return valorTomado;
	}

	public void setValorTomado(Float valorTomado) {
		this.valorTomado = valorTomado;
	}

	public String getEstaPagado() {
		return estaPagado;
	}

	public void setEstaPagado(String estaPagado) {
		this.estaPagado = estaPagado;
	}

	public Date getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	public String getComprobantePago() {
		return comprobantePago;
	}

	public void setComprobantePago(String comprobantePago) {
		this.comprobantePago = comprobantePago;
	}

	public Float getValorPagado() {
		return valorPagado;
	}

	public void setValorPagado(Float valorPagado) {
		this.valorPagado = valorPagado;
	}

	public String getNombreApellidoResponsable() {
		return nombreApellidoResponsable;
	}

	public void setNombreApellidoResponsable(String nombreApellidoResponsable) {
		this.nombreApellidoResponsable = nombreApellidoResponsable;
	}

	public CargoVacacionResponseDao getIdCargoVacacion() {
		return idCargoVacacion;
	}

	public void setIdCargoVacacion(CargoVacacionResponseDao idCargoVacacion) {
		this.idCargoVacacion = idCargoVacacion;
	}
	
}
