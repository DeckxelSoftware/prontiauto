package com.ec.prontiauto.dao;

import java.math.BigDecimal;

import com.ec.prontiauto.abstracts.AbstractRequestDao;

public class ConfiguracionGeneralRequestDao extends AbstractRequestDao {

	private BigDecimal ivaPorcentaje;
	private BigDecimal tasaCambioContrato;
	private Integer minCuotaMoraRefinanciamiento;
	private Integer maxContratosEnGrupo;
	private BigDecimal cuotaAdministrativa;
	private Integer numDiasVacacionesAlAnio;
	private BigDecimal tasaCargoAdjudicacion;
	private BigDecimal aportePatronalIess;
	private BigDecimal aportePersonalIess;
	private BigDecimal sueldoBasico;
	private BigDecimal rastreo;
	private BigDecimal dispositivo;

	public ConfiguracionGeneralRequestDao() {
	}

	public BigDecimal getRastreo() {
		return rastreo;
	}

	public void setRastreo(BigDecimal rastreo) {
		this.rastreo = rastreo;
	}

	public BigDecimal getDispositivo() {
		return dispositivo;
	}

	public void setDispositivo(BigDecimal dispositivo) {
		this.dispositivo = dispositivo;
	}

	public BigDecimal getTasaCargoAdjudicacion() {
		return tasaCargoAdjudicacion;
	}

	public void setTasaCargoAdjudicacion(BigDecimal tasaCargoAdjudicacion) {
		this.tasaCargoAdjudicacion = tasaCargoAdjudicacion;
	}

	public BigDecimal getAportePatronalIess() {
		return aportePatronalIess;
	}

	public void setAportePatronalIess(BigDecimal aportePatronalIess) {
		this.aportePatronalIess = aportePatronalIess;
	}

	public BigDecimal getAportePersonalIess() {
		return aportePersonalIess;
	}

	public void setAportePersonalIess(BigDecimal aportePersonalIess) {
		this.aportePersonalIess = aportePersonalIess;
	}

	public BigDecimal getSueldoBasico() {
		return sueldoBasico;
	}

	public void setSueldoBasico(BigDecimal sueldoBasico) {
		this.sueldoBasico = sueldoBasico;
	}

	public BigDecimal getIvaPorcentaje() {
		return ivaPorcentaje;
	}

	public void setIvaPorcentaje(BigDecimal ivaPorcentaje) {
		this.ivaPorcentaje = ivaPorcentaje;
	}

	public BigDecimal getTasaCambioContrato() {
		return tasaCambioContrato;
	}

	public void setTasaCambioContrato(BigDecimal tasaCambioContrato) {
		this.tasaCambioContrato = tasaCambioContrato;
	}

	public Integer getMinCuotaMoraRefinanciamiento() {
		return minCuotaMoraRefinanciamiento;
	}

	public void setMinCuotaMoraRefinanciamiento(Integer minCuotaMoraRefinanciamiento) {
		this.minCuotaMoraRefinanciamiento = minCuotaMoraRefinanciamiento;
	}

	public Integer getMaxContratosEnGrupo() {
		return maxContratosEnGrupo;
	}

	public void setMaxContratosEnGrupo(Integer maxContratosEnGrupo) {
		this.maxContratosEnGrupo = maxContratosEnGrupo;
	}

	public BigDecimal getCuotaAdministrativa() {
		return cuotaAdministrativa;
	}

	public void setCuotaAdministrativa(BigDecimal cuotaAdministrativa) {
		this.cuotaAdministrativa = cuotaAdministrativa;
	}

	public Integer getNumDiasVacacionesAlAnio() {
		return numDiasVacacionesAlAnio;
	}

	public void setNumDiasVacacionesAlAnio(Integer numDiasVacacionesAlAnio) {
		this.numDiasVacacionesAlAnio = numDiasVacacionesAlAnio;
	}

}
