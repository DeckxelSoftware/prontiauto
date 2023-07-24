package com.ec.prontiauto.dao;

import java.math.BigDecimal;
import java.sql.Date;

import com.ec.prontiauto.abstracts.AbstractRequestDao;

public class PrestamoRequestDao extends AbstractRequestDao {

	private String tipoPrestamo;
	private Date fechaPrestamo;
	private String comprobanteEgreso;
	private BigDecimal valor;
	private Integer cuotas;
	private BigDecimal tasaInteres;
	private String concepto;
	private String estado;
	private String modalidadDescuento;
	private String totalPagado;
	private String totalSaldo;
	private Integer idTrabajador;
	private String nombreApellidoResponsable;
	private String estadoSolicitud;

	public PrestamoRequestDao() {

	}

	public String getNombreApellidoResponsable() {
		return nombreApellidoResponsable;
	}

	public void setNombreApellidoResponsable(String nombreApellidoResponsable) {
		this.nombreApellidoResponsable = nombreApellidoResponsable;
	}

	public String getEstadoSolicitud() {
		return estadoSolicitud;
	}

	public void setEstadoSolicitud(String estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}

	public String getTipoPrestamo() {
		return tipoPrestamo;
	}

	public void setTipoPrestamo(String tipoPrestamo) {
		this.tipoPrestamo = tipoPrestamo;
	}

	public Date getFechaPrestamo() {
		return fechaPrestamo;
	}

	public void setFechaPrestamo(Date fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}

	public String getComprobanteEgreso() {
		return comprobanteEgreso;
	}

	public void setComprobanteEgreso(String comprobanteEgreso) {
		this.comprobanteEgreso = comprobanteEgreso;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Integer getCuotas() {
		return cuotas;
	}

	public void setCuotas(Integer cuotas) {
		this.cuotas = cuotas;
	}

	public BigDecimal getTasaInteres() {
		return tasaInteres;
	}

	public void setTasaInteres(BigDecimal tasaInteres) {
		this.tasaInteres = tasaInteres;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getModalidadDescuento() {
		return modalidadDescuento;
	}

	public void setModalidadDescuento(String modalidadDescuento) {
		this.modalidadDescuento = modalidadDescuento;
	}

	public String getTotalPagado() {
		return totalPagado;
	}

	public void setTotalPagado(String totalPagado) {
		this.totalPagado = totalPagado;
	}

	public String getTotalSaldo() {
		return totalSaldo;
	}

	public void setTotalSaldo(String totalSaldo) {
		this.totalSaldo = totalSaldo;
	}

	public Integer getIdTrabajador() {
		return idTrabajador;
	}

	public void setIdTrabajador(Integer idTrabajador) {
		this.idTrabajador = idTrabajador;
	}

}
