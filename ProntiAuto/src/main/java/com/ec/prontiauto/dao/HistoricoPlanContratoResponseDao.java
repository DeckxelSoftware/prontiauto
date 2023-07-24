package com.ec.prontiauto.dao;

import java.math.BigDecimal;
import java.util.List;

import com.ec.prontiauto.abstracts.AbstractResponseDao;

public class HistoricoPlanContratoResponseDao extends AbstractResponseDao {
	private BigDecimal totalInscripcionPlan;
	private BigDecimal valorDsctoInscripcion;
	private BigDecimal totalCobroInscripcion;
	private BigDecimal capitalTotal;
	private BigDecimal capitalPorRefinanciamiento;
	private BigDecimal abonosCapitalActual;
	private BigDecimal saldoCapital;
	private BigDecimal valorTasaAdministrativa;
	private BigDecimal totalTasaAdministrativaCobrada;
	private Integer totalCuotasCobradas;
	private Integer totalCuotasMoraActual;
	private Integer totalCuotasMora;
	private BigDecimal totalMontoCobrado;
	private BigDecimal valorDsctoPrimeraCuota;
	private BigDecimal totalCobroPrimeraCuota;
	private BigDecimal valorRecargo;
	private Integer numCuotasAbajoHaciaArriba;
	private BigDecimal valorADevolver;
	private BigDecimal valorPrimerCapitalDevolver;
	private BigDecimal inscripcionADevolver;
	private BigDecimal tasaAdministrativaADevolver;
	private BigDecimal capitalALiquidar;
	private BigDecimal tasaAdministrativaALiquidar;
	private BigDecimal cargosAdjudicacion;
	private String inscripcionEstaPagada;
	private BigDecimal valorPagadoInscripcion;
	private ContratoResponseDao idContrato;
	private PlanResponseDao idPlan;
	private List<CuotaResponseDao> coutaCollection;
	private List<RefinanciamientoResponseDao> refinanciamientoCollection;

	public HistoricoPlanContratoResponseDao() {

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

	public List<CuotaResponseDao> getCoutaCollection() {
		return coutaCollection;
	}

	public void setCoutaCollection(List<CuotaResponseDao> coutaCollection) {
		this.coutaCollection = coutaCollection;
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

	public ContratoResponseDao getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(ContratoResponseDao idContrato) {
		this.idContrato = idContrato;
	}

	public PlanResponseDao getIdPlan() {
		return idPlan;
	}

	public void setIdPlan(PlanResponseDao idPlan) {
		this.idPlan = idPlan;
	}

	public List<CuotaResponseDao> getCuotaCollection() {
		return coutaCollection;
	}

	public void setCuotaCollection(List<CuotaResponseDao> coutaCollection) {
		this.coutaCollection = coutaCollection;
	}

	public List<RefinanciamientoResponseDao> getRefinanciamientoCollection() {
		return refinanciamientoCollection;
	}

	public void setRefinanciamientoCollection(List<RefinanciamientoResponseDao> refinanciamientoCollection) {
		this.refinanciamientoCollection = refinanciamientoCollection;
	}

}
