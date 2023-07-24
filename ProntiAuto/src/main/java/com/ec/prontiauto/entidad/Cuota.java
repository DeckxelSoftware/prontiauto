package com.ec.prontiauto.entidad;

import java.math.BigDecimal;
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
@Table(name = "cuota")
public class Cuota extends AbstractEntities {

	@Column(name = "\"fechaCobro\"", nullable = false)
	@CsvDate(value = "yyyy-MM-dd")
	@CsvBindByName(column = "fecha_cobro")
	private Date fechaCobro;

	@Column(name = "\"fechaMora\"")
	@CsvDate(value = "yyyy-MM-dd")
	@CsvBindByName(column = "fecha_mora")
	private Date fechaMora;

	@Column(name = "\"numeroCuota\"", nullable = false)
	@CsvBindByName(column = "numero_cuota")
	private Integer numeroCuota;

	@Column(name = "\"valorCuota\"", nullable = false)
	@CsvBindByName(column = "valor_cuota")
	private BigDecimal valorCuota;

	@Column(name = "\"valorPagadoCuota\"", nullable = false)
	@CsvBindByName(column = "valor_pagado_cuota")
	private BigDecimal valorPagadoCuota;

	@Column(name = "\"valorTasaAdministrativa\"", nullable = false)
	@CsvBindByName(column = "valor_tasa_administrativa")
	private BigDecimal valorTasaAdministrativa;

	@Column(name = "\"valorImpuesto\"", nullable = false)
	@CsvBindByName(column = "valor_impuesto")
	private BigDecimal valorImpuesto;

	@Column(name = "\"abonoCapital\"", nullable = false)
	@CsvBindByName(column = "abono_capital")
	private BigDecimal abonoCapital;

	@Column(name = "\"estaPagado\"", nullable = false)
	@CsvBindByName(column = "esta_pagado")
	private String estaPagado;

	@Column(name = "\"estaMora\"", nullable = false)
	@CsvBindByName(column = "esta_mora")
	private String estaMora;

	@Column(name = "dispositivo")
	@CsvBindByName(column = "dispositivo")
	private BigDecimal dispositivo;

	@Column(name = "rastreo")
	@CsvBindByName(column = "rastreo")
	private BigDecimal rastreo;

	@Column(name = "\"dispositivoEstaPagado\"")
	@CsvBindByName(column = "dispositivo_esta_pagado")
	private String dispositivoEstaPagado;

	@Column(name = "\"rastreoEstaPagado\"")
	@CsvBindByName(column = "rastreo_esta_pagado")
	private String rastreoEstaPagado;

	@Column(name = "\"valorPagadoRastreo\"")
	@CsvBindByName(column = "valor_pagado_rastreo")
	private BigDecimal valorPagadoRastreo;

	@Column(name = "\"valorPagadoDispositivo\"")
	@CsvBindByName(column = "valor_pagado_dispositivo")
	private BigDecimal valorPagadoDispositivo;

	@ManyToOne
	@JoinColumn(name = "\"idHistoricoPlanContrato\"", referencedColumnName = "id", nullable = false)
	private HistoricoPlanContrato idHistoricoPlanContrato;

	@Transient
	@CsvBindByName(column = "id_historico_plan_contrato")
	private Integer idHistoricoPlanContrato1;

	public Cuota() {
	}

	public BigDecimal getValorPagadoRastreo() {
		return valorPagadoRastreo;
	}

	public void setValorPagadoRastreo(BigDecimal valorPagadoRastreo) {
		this.valorPagadoRastreo = valorPagadoRastreo;
	}

	public BigDecimal getValorPagadoDispositivo() {
		return valorPagadoDispositivo;
	}

	public void setValorPagadoDispositivo(BigDecimal valorPagadoDispositivo) {
		this.valorPagadoDispositivo = valorPagadoDispositivo;
	}

	public BigDecimal getDispositivo() {
		return dispositivo;
	}

	public void setDispositivo(BigDecimal dispositivo) {
		this.dispositivo = dispositivo;
	}

	public BigDecimal getRastreo() {
		return rastreo;
	}

	public void setRastreo(BigDecimal rastreo) {
		this.rastreo = rastreo;
	}

	public String getDispositivoEstaPagado() {
		return dispositivoEstaPagado;
	}

	public void setDispositivoEstaPagado(String dispositivoEstaPagado) {
		this.dispositivoEstaPagado = dispositivoEstaPagado;
	}

	public String getRastreoEstaPagado() {
		return rastreoEstaPagado;
	}

	public void setRastreoEstaPagado(String rastreoEstaPagado) {
		this.rastreoEstaPagado = rastreoEstaPagado;
	}

	public Date getFechaCobro() {
		return fechaCobro;
	}

	public void setFechaCobro(Date fechaCobro) {
		this.fechaCobro = fechaCobro;
	}

	public Date getFechaMora() {
		return fechaMora;
	}

	public void setFechaMora(Date fechaMora) {
		this.fechaMora = fechaMora;
	}

	public Integer getNumeroCuota() {
		return numeroCuota;
	}

	public void setNumeroCuota(Integer numeroCuota) {
		this.numeroCuota = numeroCuota;
	}

	public BigDecimal getValorCuota() {
		return valorCuota;
	}

	public void setValorCuota(BigDecimal valorCuota) {
		this.valorCuota = valorCuota;
	}

	public BigDecimal getValorPagadoCuota() {
		return valorPagadoCuota;
	}

	public void setValorPagadoCuota(BigDecimal valorPagadoCuota) {
		this.valorPagadoCuota = valorPagadoCuota;
	}

	public BigDecimal getValorTasaAdministrativa() {
		return valorTasaAdministrativa;
	}

	public void setValorTasaAdministrativa(BigDecimal valorTasaAdministrativa) {
		this.valorTasaAdministrativa = valorTasaAdministrativa;
	}

	public BigDecimal getValorImpuesto() {
		return valorImpuesto;
	}

	public void setValorImpuesto(BigDecimal valorImpuesto) {
		this.valorImpuesto = valorImpuesto;
	}

	public BigDecimal getAbonoCapital() {
		return abonoCapital;
	}

	public void setAbonoCapital(BigDecimal abonoCapital) {
		this.abonoCapital = abonoCapital;
	}

	public String getEstaPagado() {
		return estaPagado;
	}

	public void setEstaPagado(String estaPagado) {
		this.estaPagado = estaPagado;
	}

	public String getEstaMora() {
		return estaMora;
	}

	public void setEstaMora(String estaMora) {
		this.estaMora = estaMora;
	}

	public HistoricoPlanContrato getIdHistoricoPlanContrato() {
		return idHistoricoPlanContrato;
	}

	public void setIdHistoricoPlanContrato(HistoricoPlanContrato idHistoricoPlanContrato) {
		this.idHistoricoPlanContrato = idHistoricoPlanContrato;
	}

	public Integer getIdHistoricoPlanContrato1() {
		return idHistoricoPlanContrato1;
	}

	public void setIdHistoricoPlanContrato1(Integer idHistoricoPlanContrato1) {
		this.idHistoricoPlanContrato1 = idHistoricoPlanContrato1;
	}

	public Cuota setValoresDiferentes(Cuota registroAntiguo, Cuota registroActualizar) {
		if (registroActualizar.getFechaCobro() != null) {
			registroAntiguo.setFechaCobro(registroActualizar.getFechaCobro());
		}
		if (registroActualizar.getFechaMora() != null) {
			registroAntiguo.setFechaMora(registroActualizar.getFechaMora());
		}

		if (registroActualizar.getNumeroCuota() != null) {
			registroAntiguo.setNumeroCuota(registroActualizar.getNumeroCuota());
		}

		if (registroActualizar.getValorCuota() != null) {
			registroAntiguo.setValorCuota(registroActualizar.getValorCuota());
		}
		if (registroActualizar.getValorPagadoCuota() != null) {
			registroAntiguo.setValorPagadoCuota(registroActualizar.getValorPagadoCuota());
		}

		if (registroActualizar.getValorTasaAdministrativa() != null) {
			registroAntiguo.setValorTasaAdministrativa(registroActualizar.getValorTasaAdministrativa());
		}

		if (registroActualizar.getValorImpuesto() != null) {
			registroAntiguo.setValorImpuesto(registroActualizar.getValorImpuesto());
		}
		if (registroActualizar.getAbonoCapital() != null) {
			registroAntiguo.setAbonoCapital(registroActualizar.getAbonoCapital());
		}

		if (registroActualizar.getEstaPagado() != null) {
			registroAntiguo.setEstaPagado(registroActualizar.getEstaPagado());
		}

		if (registroActualizar.getEstaMora() != null) {
			registroAntiguo.setEstaMora(registroActualizar.getEstaMora());
		}

		if (registroActualizar.getSisHabilitado() != null) {
			registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
		}
		if (registroActualizar.getIdHistoricoPlanContrato() != null
				&& registroActualizar.getIdHistoricoPlanContrato().getId() != null) {
			registroAntiguo.setIdHistoricoPlanContrato(registroActualizar.getIdHistoricoPlanContrato());
		}
		return registroAntiguo;
	}

}
