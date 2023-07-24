package com.ec.prontiauto.entidad;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

@Entity
@Table(name = "prestamo")
public class Prestamo extends AbstractEntities {

	@Column(name = "\"tipoPrestamo\"", length = 255, nullable = false)
	@CsvBindByName(column = "tipo_prestamo")
	private String tipoPrestamo;

	@Column(name = "\"fechaPrestamo\"", nullable = false)
	@CsvDate(value = "yyyy-MM-dd")
	@CsvBindByName(column = "fecha_prestamo")
	private Date fechaPrestamo;

	@Column(name = "\"comprobanteEgreso\"", length = 255)
	@CsvBindByName(column = "comprobante_egreso ")
	private String comprobanteEgreso;

	@Column(name = "valor", nullable = false)
	@CsvBindByName(column = "valor")
	private BigDecimal valor;

	@Column(name = "\"cuotas\"", nullable = false)
	@CsvBindByName(column = "cuotas")
	private Integer cuotas;

	@Column(name = "\"tasaInteres\"", nullable = false)
	@CsvBindByName(column = "tasa_interes")
	private BigDecimal tasaInteres;

	@Column(name = "concepto ", length = 255)
	@CsvBindByName(column = "concepto ")
	private String concepto;

	@Column(name = "estado ", length = 255, nullable = false)
	@CsvBindByName(column = "estado ")
	private String estado;

	@Column(name = "\"modalidadDescuento\"", length = 255, nullable = false)
	@CsvBindByName(column = "modalidad_descuento ")
	private String modalidadDescuento;

	@Column(name = "\"totalPagado\"", length = 255)
	@CsvBindByName(column = "total_pagado ")
	private String totalPagado;

	@Column(name = "\"totalSaldo\"", length = 255, nullable = false)
	@CsvBindByName(column = "total_saldo ")
	private String totalSaldo;

	@Column(name = "\"nombreApellidoResponsable\"", length = 255, nullable = false)
	@CsvBindByName(column = "nombre_apellido_responsable ")
	private String nombreApellidoResponsable;

	@Column(name = "\"estadoSolicitud\"", length = 255, nullable = false)
	@CsvBindByName(column = "estado_solicitud ")
	private String estadoSolicitud;

	@ManyToOne
	@JoinColumn(name = "\"idTrabajador\"", referencedColumnName = "id", nullable = false)
	private Trabajador idTrabajador;

	@Transient
	@CsvBindByName(column = "id_trabajador")
	private Integer idTrabajador1;

	@OneToMany(mappedBy = "idPrestamo")
	List<AbonoPrestamo> abonoPrestamoCollection;

	public Prestamo() {
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

	public List<AbonoPrestamo> getAbonoPrestamoCollection() {
		return abonoPrestamoCollection;
	}

	public void setAbonoPrestamoCollection(List<AbonoPrestamo> abonoPrestamoCollection) {
		this.abonoPrestamoCollection = abonoPrestamoCollection;
	}

	public Prestamo setValoresDiferentes(Prestamo registroAntiguo, Prestamo registroActualizar) {

		if (registroActualizar.getTipoPrestamo() != null) {
			registroAntiguo.setTipoPrestamo(registroActualizar.getTipoPrestamo());
		}
		if (registroActualizar.getFechaPrestamo() != null) {
			registroAntiguo.setFechaPrestamo(registroActualizar.getFechaPrestamo());
		}
		if (registroActualizar.getComprobanteEgreso() != null) {
			registroAntiguo.setComprobanteEgreso(registroActualizar.getComprobanteEgreso());
		}
		if (registroActualizar.getValor() != null) {
			registroAntiguo.setValor(registroActualizar.getValor());
		}
		if (registroActualizar.getCuotas() != null) {
			registroAntiguo.setCuotas(registroActualizar.getCuotas());
		}
		if (registroActualizar.getTasaInteres() != null) {
			registroAntiguo.setTasaInteres(registroActualizar.getTasaInteres());
		}
		if (registroActualizar.getConcepto() != null) {
			registroAntiguo.setConcepto(registroActualizar.getConcepto());
		}
		if (registroActualizar.getEstado() != null) {
			registroAntiguo.setEstado(registroActualizar.getEstado());
		}
		if (registroActualizar.getModalidadDescuento() != null) {
			registroAntiguo.setModalidadDescuento(registroActualizar.getModalidadDescuento());
		}
		if (registroActualizar.getTotalPagado() != null) {
			registroAntiguo.setTotalPagado(registroActualizar.getTotalPagado());
		}
		if (registroActualizar.getTotalSaldo() != null) {
			registroAntiguo.setTotalSaldo(registroActualizar.getTotalSaldo());
		}
		if (registroActualizar.getSisHabilitado() != null) {
			registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
		}
		if (registroActualizar.getIdTrabajador() != null && registroActualizar.getIdTrabajador().getId() != null) {
			registroAntiguo.setIdTrabajador(registroActualizar.getIdTrabajador());
		}
		if (registroActualizar.getNombreApellidoResponsable() != null) {
			registroAntiguo.setNombreApellidoResponsable(registroActualizar.getNombreApellidoResponsable());
		}
		if (registroActualizar.getEstadoSolicitud() != null) {
			registroAntiguo.setEstadoSolicitud(registroActualizar.getEstadoSolicitud());
		}
		return registroAntiguo;
	}

}
