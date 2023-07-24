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
@Table(name = "cargo_vacacion")
public class CargoVacacion extends AbstractEntities {

	@Column(name = "\"fechaDesde\"", nullable = false)
	@CsvDate(value = "yyyy-MM-dd")
	@CsvBindByName(column = "fecha_desde")
	private Date fechaDesde;

	@Column(name = "\"fechaHasta\"", nullable = false)
	@CsvDate(value = "yyyy-MM-dd")
	@CsvBindByName(column = "fecha_hasta")
	private Date fechaHasta;

	@Column(name = "\"diasVacaciones\"", nullable = false)
	@CsvBindByName(column = "dias_vacaciones")
	private Integer diasVacaciones;

	@Column(name = "\"diasAntiguedad\"", nullable = false)
	@CsvBindByName(column = "dias_antiguedad")
	private Integer diasAntiguedad;

	@Column(name = "\"diasTeoricos\"", nullable = false)
	@CsvBindByName(column = "dias_teoricos")
	private Integer diasTeoricos;

	@Column(name = "\"diasTomados\"", nullable = false)
	@CsvBindByName(column = "dias_tomados")
	private Integer diasTomados;

	@Column(name = "\"diasSaldo\"", nullable = false)
	@CsvBindByName(column = "dias_saldo")
	private Integer diasSaldo;

	@Column(name = "\"valorVacacion\"", nullable = false, columnDefinition = "Decimal(10,2)")
	@CsvBindByName(column = "valor_vacacion")
	private Float valorVacacion;

	@Column(name = "\"valorDias\"", nullable = false, columnDefinition = "Decimal(10,2)")
	@CsvBindByName(column = "valor_dias")
	private Float valorDias;

	@Column(name = "\"valorAntiguedad\"", nullable = false, columnDefinition = "Decimal(10,2)")
	@CsvBindByName(column = "valor_antiguedad")
	private Float valorAntiguedad;

	@Column(name = "\"valorTeorico\"", nullable = false, columnDefinition = "Decimal(10,2)")
	@CsvBindByName(column = "valor_teorico")
	private Float valorTeorico;

	@Column(name = "\"valorTomado\"", nullable = false, columnDefinition = "Decimal(10,2)")
	@CsvBindByName(column = "valor_tomado")
	private Float valorTomado;

	@Column(name = "\"valorSaldo\"", nullable = false, columnDefinition = "Decimal(10,2)")
	@CsvBindByName(column = "valor_saldo")
	private Float valorSaldo;

	@Column(name = "\"totalIngresosAnio\"", nullable = false, columnDefinition = "Decimal(10,2)")
	@CsvBindByName(column = "total_ingresos_anio")
	private Float totalIngresosAnio;

	@Column(name = "\"numAnioAcumulado\"", nullable = false)
	@CsvBindByName(column = "num_anio_acumulado")
	private Integer numAnioAcumulado;

	@ManyToOne
	@JoinColumn(name = "\"idTrabajador\"", referencedColumnName = "id", nullable = false)
	private Trabajador idTrabajador;

	@Transient
	@CsvBindByName(column = "id_trabajador")
	private Integer idTrabajador1;

	public CargoVacacion() {
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

	public Integer getDiasVacaciones() {
		return diasVacaciones;
	}

	public void setDiasVacaciones(Integer diasVacaciones) {
		this.diasVacaciones = diasVacaciones;
	}

	public Integer getDiasAntiguedad() {
		return diasAntiguedad;
	}

	public void setDiasAntiguedad(Integer diasAntiguedad) {
		this.diasAntiguedad = diasAntiguedad;
	}

	public Integer getDiasTeoricos() {
		return diasTeoricos;
	}

	public void setDiasTeoricos(Integer diasTeoricos) {
		this.diasTeoricos = diasTeoricos;
	}

	public Integer getDiasTomados() {
		return diasTomados;
	}

	public void setDiasTomados(Integer diasTomados) {
		this.diasTomados = diasTomados;
	}

	public Integer getDiasSaldo() {
		return diasSaldo;
	}

	public void setDiasSaldo(Integer diasSaldo) {
		this.diasSaldo = diasSaldo;
	}

	public Float getValorVacacion() {
		return valorVacacion;
	}

	public void setValorVacacion(Float valorVacacion) {
		this.valorVacacion = valorVacacion;
	}

	public Float getValorDias() {
		return valorDias;
	}

	public void setValorDias(Float valorDias) {
		this.valorDias = valorDias;
	}

	public Float getValorAntiguedad() {
		return valorAntiguedad;
	}

	public void setValorAntiguedad(Float valorAntiguedad) {
		this.valorAntiguedad = valorAntiguedad;
	}

	public Float getValorTeorico() {
		return valorTeorico;
	}

	public void setValorTeorico(Float valorTeorico) {
		this.valorTeorico = valorTeorico;
	}

	public Float getValorTomado() {
		return valorTomado;
	}

	public void setValorTomado(Float valorTomado) {
		this.valorTomado = valorTomado;
	}

	public Float getValorSaldo() {
		return valorSaldo;
	}

	public void setValorSaldo(Float valorSaldo) {
		this.valorSaldo = valorSaldo;
	}

	public Float getTotalIngresosAnio() {
		return totalIngresosAnio;
	}

	public void setTotalIngresosAnio(Float totalIngresosAnio) {
		this.totalIngresosAnio = totalIngresosAnio;
	}

	public Integer getNumAnioAcumulado() {
		return numAnioAcumulado;
	}

	public void setNumAnioAcumulado(Integer numAnioAcumulado) {
		this.numAnioAcumulado = numAnioAcumulado;
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

	public CargoVacacion setValoresDiferentes(CargoVacacion registroAntiguo, CargoVacacion registroActualizar) {
		if (registroActualizar.getFechaDesde() != null) {
			registroAntiguo.setFechaDesde(registroActualizar.getFechaDesde());
		}
		if (registroActualizar.getFechaHasta() != null) {
			registroAntiguo.setFechaHasta(registroActualizar.getFechaHasta());
		}
		if (registroActualizar.getDiasVacaciones() != null) {
			registroAntiguo.setDiasVacaciones(registroActualizar.getDiasVacaciones());
		}
		if (registroActualizar.getDiasAntiguedad() != null) {
			registroAntiguo.setDiasAntiguedad(registroActualizar.getDiasAntiguedad());
		}
		if (registroActualizar.getDiasTeoricos() != null) {
			registroAntiguo.setDiasTeoricos(registroActualizar.getDiasTeoricos());
		}
		if (registroActualizar.getDiasTomados() != null) {
			registroAntiguo.setDiasTomados(registroActualizar.getDiasTomados());
		}
		if (registroActualizar.getDiasSaldo() != null) {
			registroAntiguo.setDiasSaldo(registroActualizar.getDiasSaldo());
		}
		if (registroActualizar.getValorVacacion() != null) {
			registroAntiguo.setValorVacacion(registroActualizar.getValorVacacion());
		}
		if (registroActualizar.getValorDias() != null) {
			registroAntiguo.setValorDias(registroActualizar.getValorDias());
		}
		if (registroActualizar.getValorAntiguedad() != null) {
			registroAntiguo.setValorAntiguedad(registroActualizar.getValorAntiguedad());
		}
		if (registroActualizar.getValorTeorico() != null) {
			registroAntiguo.setValorTeorico(registroActualizar.getValorTeorico());
		}
		if (registroActualizar.getValorTomado() != null) {
			registroAntiguo.setValorTomado(registroActualizar.getValorTomado());
		}
		if (registroActualizar.getValorSaldo() != null) {
			registroAntiguo.setValorSaldo(registroActualizar.getValorSaldo());
		}
		if (registroActualizar.getTotalIngresosAnio() != null) {
			registroAntiguo.setTotalIngresosAnio(registroActualizar.getTotalIngresosAnio());
		}
		if (registroActualizar.getNumAnioAcumulado() != null) {
			registroAntiguo.setNumAnioAcumulado(registroActualizar.getNumAnioAcumulado());
		}
		if (registroActualizar.getSisHabilitado() != null) {
			registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
		}
		if (registroActualizar.getIdTrabajador() != null
				&& registroActualizar.getIdTrabajador().getId() != null) {
			registroAntiguo.setIdTrabajador(registroActualizar.getIdTrabajador());
		}

		return registroAntiguo;
	}

}
