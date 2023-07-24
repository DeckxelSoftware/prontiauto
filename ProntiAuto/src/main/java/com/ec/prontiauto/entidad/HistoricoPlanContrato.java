package com.ec.prontiauto.entidad;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "historico_plan_contrato")
public class HistoricoPlanContrato extends AbstractEntities {

	@Column(name = "\"totalInscripcionPlan\"")
	@CsvBindByName(column = "total_inscripcion_plan")
	private BigDecimal totalInscripcionPlan;

	@Column(name = "\"valorDsctoInscripcion\"")
	@CsvBindByName(column = "valor_dscto_inscripcion")
	private BigDecimal valorDsctoInscripcion;

	@Column(name = "\"totalCobroInscripcion\"")
	@CsvBindByName(column = "total_cobro_inscripcion")
	private BigDecimal totalCobroInscripcion;

	@Column(name = "\"capitalTotal\"")
	@CsvBindByName(column = "capital_total")
	private BigDecimal capitalTotal;

	@Column(name = "\"capitalPorRefinanciamiento\"")
	@CsvBindByName(column = "capital_por_refinanciamiento")
	private BigDecimal capitalPorRefinanciamiento;

	@Column(name = "\"abonosCapitalActual\"")
	@CsvBindByName(column = "abonos_capital_actual")
	private BigDecimal abonosCapitalActual;

	@Column(name = "\"saldoCapital\"")
	@CsvBindByName(column = "saldo_capital")
	private BigDecimal saldoCapital;

	@Column(name = "\"valorTasaAdministrativa\"")
	@CsvBindByName(column = "valor_tasa_administrativa")
	private BigDecimal valorTasaAdministrativa;

	@Column(name = "\"totalTasaAdministrativaCobrada\"")
	@CsvBindByName(column = "total_tasa_administrativa_cobrada")
	private BigDecimal totalTasaAdministrativaCobrada;

	@Column(name = "\"totalCuotasCobradas\"")
	@CsvBindByName(column = "total_cuotas_cobradas")
	private Integer totalCuotasCobradas;

	@Column(name = "\"totalCuotasMoraActual\"")
	@CsvBindByName(column = "total_cuotas_mora_actual")
	private Integer totalCuotasMoraActual;

	@Column(name = "\"totalCuotasMora\"")
	@CsvBindByName(column = "total_cuotas_mora")
	private Integer totalCuotasMora;

	@Column(name = "\"totalMontoCobrado\"")
	@CsvBindByName(column = "total_monto_cobrado")
	private BigDecimal totalMontoCobrado;

	@Column(name = "\"valorDsctoPrimeraCuota\"")
	@CsvBindByName(column = "valor_dscto_primera_cuota")
	private BigDecimal valorDsctoPrimeraCuota;

	@Column(name = "\"totalCobroPrimeraCuota\"")
	@CsvBindByName(column = "total_cobro_primera_cuota")
	private BigDecimal totalCobroPrimeraCuota;

	@Column(name = "\"valorRecargo\"")
	@CsvBindByName(column = "valor_recargo")
	private BigDecimal valorRecargo;

	@Column(name = "\"numCuotasAbajoHaciaArriba\"")
	@CsvBindByName(column = "num_cuotas_abajo_hacia_arriba")
	private Integer numCuotasAbajoHaciaArriba;

	@Column(name = "\"valorADevolver\"")
	@CsvBindByName(column = "valor_a_devolver")
	private BigDecimal valorADevolver;

	@Column(name = "\"valorPrimerCapitalDevolver\"")
	@CsvBindByName(column = "valor_primer_capital_devolver")
	private BigDecimal valorPrimerCapitalDevolver;

	@Column(name = "\"inscripcionADevolver\"")
	@CsvBindByName(column = "inscripcion_a_devolver")
	private BigDecimal inscripcionADevolver;

	@Column(name = "\"tasaAdministrativaADevolver\"")
	@CsvBindByName(column = "tasa_administrativa_a_devolver")
	private BigDecimal tasaAdministrativaADevolver;

	@Column(name = "\"capitalALiquidar\"")
	@CsvBindByName(column = "capital_a_liquidar")
	private BigDecimal capitalALiquidar;

	@Column(name = "\"tasaAdministrativaALiquidar\"")
	@CsvBindByName(column = "tasa_administrativa_a_liquidar")
	private BigDecimal tasaAdministrativaALiquidar;

	@Column(name = "\"cargosAdjudicacion\"")
	@CsvBindByName(column = "cargos_adjudicacion")
	private BigDecimal cargosAdjudicacion;

	@Column(name = "\"inscripcionEstaPagada\"", length = 1)
	@CsvBindByName(column = "inscripcion_esta_pagada")
	private String inscripcionEstaPagada;

	@Column(name = "\"valorPagadoInscripcion\"")
	@CsvBindByName(column = "valor_pagado_inscripcion")
	private BigDecimal valorPagadoInscripcion;
	
	@Column(name = "\"totalDispositivoCobrado\"")
	@CsvBindByName(column = "total_dispositivo_cobrado")
	private BigDecimal totalDispositivoCobrado;

	@ManyToOne
	@JoinColumn(name = "\"idContrato\"", referencedColumnName = "id", nullable = false)
	private Contrato idContrato;

	@ManyToOne
	@JoinColumn(name = "\"idPlan\"", referencedColumnName = "id", nullable = false)
	private Plan idPlan;

	@JsonIgnore
	@OneToMany(mappedBy = "idHistoricoPlanContrato")
	private List<Cuota> cuotaCollection;

	@JsonIgnore
	@OneToMany(mappedBy = "idHistoricoPlanContrato")
	private List<Refinanciamiento> refinanciamientoCollection;

	@Transient
	@CsvBindByName(column = "id_contrato")
	private Integer idContrato1;

	@Transient
	@CsvBindByName(column = "id_plan")
	private Integer idPlan1;

	public HistoricoPlanContrato() {

	}

	public BigDecimal getCapitalALiquidar() {
		return capitalALiquidar;
	}

	public void setCapitalALiquidar(BigDecimal capitalALiquidar) {
		this.capitalALiquidar = capitalALiquidar;
	}

	public BigDecimal getTasaAdministrativaALiquidar() {
		return tasaAdministrativaALiquidar;
	}

	public void setTasaAdministrativaALiquidar(BigDecimal tasaAdministrativaALiquidar) {
		this.tasaAdministrativaALiquidar = tasaAdministrativaALiquidar;
	}

	public BigDecimal getCargosAdjudicacion() {
		return cargosAdjudicacion;
	}

	public void setCargosAdjudicacion(BigDecimal cargosAdjudicacion) {
		this.cargosAdjudicacion = cargosAdjudicacion;
	}

	public Integer getNumCuotasAbajoHaciaArriba() {
		return numCuotasAbajoHaciaArriba;
	}

	public void setNumCuotasAbajoHaciaArriba(Integer numCuotasAbajoHaciaArriba) {
		this.numCuotasAbajoHaciaArriba = numCuotasAbajoHaciaArriba;
	}

	public BigDecimal getValorADevolver() {
		return valorADevolver;
	}

	public void setValorADevolver(BigDecimal valorADevolver) {
		this.valorADevolver = valorADevolver;
	}

	public BigDecimal getTotalInscripcionPlan() {
		return totalInscripcionPlan;
	}

	public void setTotalInscripcionPlan(BigDecimal totalInscripcionPlan) {
		this.totalInscripcionPlan = totalInscripcionPlan;
	}

	public BigDecimal getValorDsctoInscripcion() {
		return valorDsctoInscripcion;
	}

	public void setValorDsctoInscripcion(BigDecimal valorDsctoInscripcion) {
		this.valorDsctoInscripcion = valorDsctoInscripcion;
	}

	public BigDecimal getTotalCobroInscripcion() {
		return totalCobroInscripcion;
	}

	public void setTotalCobroInscripcion(BigDecimal totalCobroInscripcion) {
		this.totalCobroInscripcion = totalCobroInscripcion;
	}

	public BigDecimal getTotalMontoCobrado() {
		return totalMontoCobrado;
	}

	public void setTotalMontoCobrado(BigDecimal totalMontoCobrado) {
		this.totalMontoCobrado = totalMontoCobrado;
	}

	public BigDecimal getValorDsctoPrimeraCuota() {
		return valorDsctoPrimeraCuota;
	}

	public void setValorDsctoPrimeraCuota(BigDecimal valorDsctoPrimeraCuota) {
		this.valorDsctoPrimeraCuota = valorDsctoPrimeraCuota;
	}

	public BigDecimal getTotalCobroPrimeraCuota() {
		return totalCobroPrimeraCuota;
	}

	public void setTotalCobroPrimeraCuota(BigDecimal totalCobroPrimeraCuota) {
		this.totalCobroPrimeraCuota = totalCobroPrimeraCuota;
	}

	public BigDecimal getValorRecargo() {
		return valorRecargo;
	}

	public void setValorRecargo(BigDecimal valorRecargo) {
		this.valorRecargo = valorRecargo;
	}

	public BigDecimal getCapitalTotal() {
		return capitalTotal;
	}

	public void setCapitalTotal(BigDecimal capitalTotal) {
		this.capitalTotal = capitalTotal;
	}

	public BigDecimal getCapitalPorRefinanciamiento() {
		return capitalPorRefinanciamiento;
	}

	public void setCapitalPorRefinanciamiento(BigDecimal capitalPorRefinanciamiento) {
		this.capitalPorRefinanciamiento = capitalPorRefinanciamiento;
	}

	public BigDecimal getAbonosCapitalActual() {
		return abonosCapitalActual;
	}

	public void setAbonosCapitalActual(BigDecimal abonosCapitalActual) {
		this.abonosCapitalActual = abonosCapitalActual;
	}

	public BigDecimal getSaldoCapital() {
		return saldoCapital;
	}

	public void setSaldoCapital(BigDecimal saldoCapital) {
		this.saldoCapital = saldoCapital;
	}

	public BigDecimal getValorTasaAdministrativa() {
		return valorTasaAdministrativa;
	}

	public void setValorTasaAdministrativa(BigDecimal valorTasaAdministrativa) {
		this.valorTasaAdministrativa = valorTasaAdministrativa;
	}

	public BigDecimal getTotalTasaAdministrativaCobrada() {
		return totalTasaAdministrativaCobrada;
	}

	public void setTotalTasaAdministrativaCobrada(BigDecimal totalTasaAdministrativaCobrada) {
		this.totalTasaAdministrativaCobrada = totalTasaAdministrativaCobrada;
	}

	public Integer getTotalCuotasCobradas() {
		return totalCuotasCobradas;
	}

	public void setTotalCuotasCobradas(Integer totalCuotasCobradas) {
		this.totalCuotasCobradas = totalCuotasCobradas;
	}

	public Integer getTotalCuotasMoraActual() {
		return totalCuotasMoraActual;
	}

	public void setTotalCuotasMoraActual(Integer totalCuotasMoraActual) {
		this.totalCuotasMoraActual = totalCuotasMoraActual;
	}

	public Integer getTotalCuotasMora() {
		return totalCuotasMora;
	}

	public void setTotalCuotasMora(Integer totalCuotasMora) {
		this.totalCuotasMora = totalCuotasMora;
	}

	public Contrato getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Contrato idContrato) {
		this.idContrato = idContrato;
	}

	public Plan getIdPlan() {
		return idPlan;
	}

	public void setIdPlan(Plan idPlan) {
		this.idPlan = idPlan;
	}

	public Integer getIdContrato1() {
		return idContrato1;
	}

	public void setIdContrato1(Integer idContrato1) {
		this.idContrato1 = idContrato1;
	}

	public Integer getIdPlan1() {
		return idPlan1;
	}

	public void setIdPlan1(Integer idPlan1) {
		this.idPlan1 = idPlan1;
	}

	public List<Cuota> getCuotaCollection() {
		return cuotaCollection;
	}

	public BigDecimal getValorPrimerCapitalDevolver() {
		return valorPrimerCapitalDevolver;
	}

	public void setValorPrimerCapitalDevolver(BigDecimal valorPrimerCapitalDevolver) {
		this.valorPrimerCapitalDevolver = valorPrimerCapitalDevolver;
	}

	public BigDecimal getInscripcionADevolver() {
		return inscripcionADevolver;
	}

	public void setInscripcionADevolver(BigDecimal inscripcionADevolver) {
		this.inscripcionADevolver = inscripcionADevolver;
	}

	public BigDecimal getTasaAdministrativaADevolver() {
		return tasaAdministrativaADevolver;
	}

	public void setTasaAdministrativaADevolver(BigDecimal tasaAdministrativaADevolver) {
		this.tasaAdministrativaADevolver = tasaAdministrativaADevolver;
	}

	public void setCuotaCollection(List<Cuota> cuotaCollection) {
		this.cuotaCollection = cuotaCollection;
	}

	public List<Refinanciamiento> getRefinanciamientoCollection() {
		return refinanciamientoCollection;
	}

	public void setRefinanciamientoCollection(List<Refinanciamiento> refinanciamientoCollection) {
		this.refinanciamientoCollection = refinanciamientoCollection;
	}

	public String getInscripcionEstaPagada() {
		return inscripcionEstaPagada;
	}

	public void setInscripcionEstaPagada(String inscripcionEstaPagada) {
		this.inscripcionEstaPagada = inscripcionEstaPagada;
	}

	public BigDecimal getValorPagadoInscripcion() {
		return valorPagadoInscripcion;
	}

	public void setValorPagadoInscripcion(BigDecimal valorPagadoInscripcion) {
		this.valorPagadoInscripcion = valorPagadoInscripcion;
	}

	public HistoricoPlanContrato setValoresDiferentes(HistoricoPlanContrato registroAntiguo,
			HistoricoPlanContrato registroActualizar) {
		if (registroActualizar.getCapitalPorRefinanciamiento() != null) {
			registroAntiguo.setCapitalPorRefinanciamiento(registroActualizar.getCapitalPorRefinanciamiento());
		}
		if (registroActualizar.getAbonosCapitalActual() != null) {
			registroAntiguo.setAbonosCapitalActual(registroActualizar.getAbonosCapitalActual());
		}
		if (registroActualizar.getSaldoCapital() != null) {
			registroAntiguo.setSaldoCapital(registroActualizar.getSaldoCapital());
		}
		if (registroActualizar.getValorTasaAdministrativa() != null) {
			registroAntiguo.setValorTasaAdministrativa(registroActualizar.getValorTasaAdministrativa());
		}
		if (registroActualizar.getTotalTasaAdministrativaCobrada() != null) {
			registroAntiguo.setTotalTasaAdministrativaCobrada(registroActualizar.getTotalTasaAdministrativaCobrada());
		}
		if (registroActualizar.getTotalCuotasCobradas() != null) {
			registroAntiguo.setTotalCuotasCobradas(registroActualizar.getTotalCuotasCobradas());
		}
		if (registroActualizar.getTotalCuotasMoraActual() != null) {
			registroAntiguo.setTotalCuotasMoraActual(registroActualizar.getTotalCuotasMoraActual());
		}
		if (registroActualizar.getTotalCuotasMora() != null) {
			registroAntiguo.setTotalCuotasMora(registroActualizar.getTotalCuotasMora());
		}
		if (registroActualizar.getSisHabilitado() != null) {
			registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
		}
		if (registroActualizar.getValorADevolver() != null) {
			registroAntiguo.setValorADevolver(registroActualizar.getValorADevolver());
		}
		if (registroActualizar.getValorPrimerCapitalDevolver() != null) {
			registroAntiguo.setValorPrimerCapitalDevolver(registroActualizar.getValorPrimerCapitalDevolver());
		}
		if (registroActualizar.getInscripcionADevolver() != null) {
			registroAntiguo.setInscripcionADevolver(registroActualizar.getInscripcionADevolver());
		}
		if (registroActualizar.getTasaAdministrativaADevolver() != null) {
			registroAntiguo.setTasaAdministrativaADevolver(registroActualizar.getTasaAdministrativaADevolver());
		}
		if (registroActualizar.getCapitalALiquidar() != null) {
			registroAntiguo.setCapitalALiquidar(registroActualizar.getCapitalALiquidar());
		}
		if (registroActualizar.getTasaAdministrativaALiquidar() != null) {
			registroAntiguo.setTasaAdministrativaALiquidar(registroActualizar.getTasaAdministrativaALiquidar());
		}
		if (registroActualizar.getCargosAdjudicacion() != null) {
			registroAntiguo.setCargosAdjudicacion(registroActualizar.getCargosAdjudicacion());
		}
		if (registroActualizar.getInscripcionEstaPagada() != null) {
			registroAntiguo.setInscripcionEstaPagada(registroActualizar.getInscripcionEstaPagada());
		}
		if (registroActualizar.getValorPagadoInscripcion() != null) {
			registroAntiguo.setValorPagadoInscripcion(registroActualizar.getValorPagadoInscripcion());
		}
		return registroAntiguo;
	}

}
