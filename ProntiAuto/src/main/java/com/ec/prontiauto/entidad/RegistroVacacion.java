package com.ec.prontiauto.entidad;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

@Entity
@Table(name = "registro_vacacion")
public class RegistroVacacion extends AbstractEntities {

	@Column(name = "\"fechaDesde\"", nullable = false)
	@CsvDate(value = "yyyy-MM-dd")
	@CsvBindByName(column = "fecha_desde")
	private Date fechaDesde;

	@Column(name = "\"fechaHasta\"", nullable = false)
	@CsvDate(value = "yyyy-MM-dd")
	@CsvBindByName(column = "fecha_hasta")
	private Date fechaHasta;

	@Column(name = "\"diasTomados\"", nullable = false)
	@CsvBindByName(column = "dias_tomados")
	private Integer diasTomados;

	@Column(name = "\"valorTomado\"", nullable = false, columnDefinition = "Decimal(10,2)")
	@CsvBindByName(column = "valor_tomado")
	private Float valorTomado;

	@Column(name = "\"estaPagado\"", length = 2, nullable = false)
	@CsvBindByName(column = "esta_pagado")
	private String estaPagado;

	@Column(name = "\"fechaPago\"")
	@CsvDate(value = "yyyy-MM-dd")
	@CsvBindByName(column = "fecha_pago")
	private Date fechaPago;

	@Column(name = "\"comprobantePago\"", length = 255)
	@CsvBindByName(column = "comprobante_pago")
	private String comprobantePago;

	@Column(name = "\"valorPagado\"", columnDefinition = "Decimal(10,2)")
	@CsvBindByName(column = "valor_pagado")
	private Float valorPagado;

	@Column(name = "\"nombreApellidoResponsable\"", length = 255, nullable = false)
	@CsvBindByName(column = "nombre_apellido_responsable")
	private String nombreApellidoResponsable;

	@ManyToOne
	@JoinColumn(name = "\"idCargoVacacion\"", referencedColumnName = "id", nullable = false)
	private CargoVacacion idCargoVacacion;

	@Transient
	@CsvBindByName(column = "id_cargo_vacacion")
	private Integer idCargoVacacion1;

	public RegistroVacacion() {
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

	public CargoVacacion getIdCargoVacacion() {
		return idCargoVacacion;
	}

	public void setIdCargoVacacion(CargoVacacion idCargoVacacion) {
		this.idCargoVacacion = idCargoVacacion;
	}

	public Integer getIdCargoVacacion1() {
		return idCargoVacacion1;
	}

	public void setIdCargoVacacion1(Integer idCargoVacacion1) {
		this.idCargoVacacion1 = idCargoVacacion1;
	}

	public RegistroVacacion setValoresDiferentes(RegistroVacacion registroAntiguo,
			RegistroVacacion registroActualizar) {
		if (registroActualizar.getFechaDesde() != null) {
			registroAntiguo.setFechaDesde(registroActualizar.getFechaDesde());
		}
		if (registroActualizar.getFechaHasta() != null) {
			registroAntiguo.setFechaHasta(registroActualizar.getFechaHasta());
		}
		if (registroActualizar.getDiasTomados() != null) {
			registroAntiguo.setDiasTomados(registroActualizar.getDiasTomados());
		}
		if (registroActualizar.getValorTomado() != null) {
			registroAntiguo.setValorTomado(registroActualizar.getValorTomado());
		}
		if (registroActualizar.getEstaPagado() != null) {
			registroAntiguo.setEstaPagado(registroActualizar.getEstaPagado());
		}
		if (registroActualizar.getFechaPago() != null) {
			registroAntiguo.setFechaPago(registroActualizar.getFechaPago());
		}
		if (registroActualizar.getComprobantePago() != null) {
			registroAntiguo.setComprobantePago(registroActualizar.getComprobantePago());
		}
		if (registroActualizar.getValorPagado() != null) {
			registroAntiguo.setValorPagado(registroActualizar.getValorPagado());
		}
		if (registroActualizar.getNombreApellidoResponsable() != null) {
			registroAntiguo.setNombreApellidoResponsable(registroActualizar.getNombreApellidoResponsable());
		}
		if (registroActualizar.getSisHabilitado() != null) {
			registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
		}
		if (registroActualizar.getIdCargoVacacion() != null
				&& registroActualizar.getIdCargoVacacion().getId() != null) {
			registroAntiguo.setIdCargoVacacion(registroActualizar.getIdCargoVacacion());
		}

		return registroAntiguo;
	}

}
