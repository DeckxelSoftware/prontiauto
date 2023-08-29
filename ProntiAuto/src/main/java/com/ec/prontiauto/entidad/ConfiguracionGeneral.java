package com.ec.prontiauto.entidad;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "configuracion_general")
public class ConfiguracionGeneral extends AbstractEntities {

	@Column(name = "\"ivaPorcentaje\"", nullable = false)
	@CsvBindByName(column = "iva_porcentaje")
	private BigDecimal ivaPorcentaje;

	@Column(name = "\"tasaCambioContrato\"", nullable = false)
	@CsvBindByName(column = "tasa_cambio_contrato")
	private BigDecimal tasaCambioContrato;

	@Column(name = "\"minCuotaMoraRefinanciamiento\"", nullable = false)
	@CsvBindByName(column = "min_cuota_mora_refinanciamiento")
	private Integer minCuotaMoraRefinanciamiento;

	@Column(name = "\"maxContratosEnGrupo\"", nullable = false)
	@CsvBindByName(column = "max_contratos_en_grupo")
	private Integer maxContratosEnGrupo;

	@Column(name = "\"cuotaAdministrativa\"", nullable = false)
	@CsvBindByName(column = "cuota_administrativa")
	private BigDecimal cuotaAdministrativa;

	@Column(name = "\"numDiasVacacionesAlAnio\"", nullable = false)
	@CsvBindByName(column = "num_dias_vacaciones_al_anio")
	private Integer numDiasVacacionesAlAnio;

	@Column(name = "\"tasaCargoAdjudicacion\"", nullable = false)
	@CsvBindByName(column = "tasa_cargo_adjudicacion")
	private BigDecimal tasaCargoAdjudicacion;

	@Column(name = "\"aportePatronalIess\"", nullable = false)
	@CsvBindByName(column = "aporte_patronal_iess")
	private BigDecimal aportePatronalIess;

	@Column(name = "\"aportePersonalIess\"", nullable = false)
	@CsvBindByName(column = "aporte_personal_iess")
	private BigDecimal aportePersonalIess;

	@Column(name = "\"sueldoBasico\"", nullable = false)
	@CsvBindByName(column = "sueldo_basico")
	private BigDecimal sueldoBasico;

	@Column(name = "\"rastreo\"", nullable = false)
	@CsvBindByName(column = "rastreo")
	private BigDecimal rastreo;

	@Column(name = "\"dispositivo\"", nullable = false)
	@CsvBindByName(column = "dispositivo")
	private BigDecimal dispositivo;

	@Column(name = "cod_establecimiento")
	@CsvBindByName(column = "codigoEstablecimiento")
	private String codigoEstablecimiento;

	@Column(name = "punto_emision")
	@CsvBindByName(column = "puntoEmision")
	private String puntoEmision;


	@Column(name = "patronal_parcial")
	@CsvBindByName(column = "patronalParcial")
	private BigDecimal patronalParcial;


	@Column(name = "patronal_pasante")
	@CsvBindByName(column = "patronalPasante")
	private BigDecimal patronalPasante;

	public ConfiguracionGeneral() {
	}

	public BigDecimal getTasaCargoAdjudicacion() {
		return tasaCargoAdjudicacion;
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

	public ConfiguracionGeneral setValoresDiferentes(ConfiguracionGeneral registroAntiguo,
			ConfiguracionGeneral registroActualizar) {
		if (registroActualizar.getSisHabilitado() != null) {
			registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
		}
		if (registroActualizar.getIvaPorcentaje() != null) {
			registroAntiguo.setIvaPorcentaje(registroActualizar.getIvaPorcentaje());
		}
		if (registroActualizar.getTasaCambioContrato() != null) {
			registroAntiguo.setTasaCambioContrato(registroActualizar.getTasaCambioContrato());
		}
		if (registroActualizar.getMinCuotaMoraRefinanciamiento() != null) {
			registroAntiguo.setMinCuotaMoraRefinanciamiento(registroActualizar.getMinCuotaMoraRefinanciamiento());
		}
		if (registroActualizar.getMaxContratosEnGrupo() != null) {
			registroAntiguo.setMaxContratosEnGrupo(registroActualizar.getMaxContratosEnGrupo());
		}
		if (registroActualizar.getCuotaAdministrativa() != null) {
			registroAntiguo.setCuotaAdministrativa(registroActualizar.getCuotaAdministrativa());
		}
		if (registroActualizar.getNumDiasVacacionesAlAnio() != null) {
			registroAntiguo.setNumDiasVacacionesAlAnio(registroActualizar.getNumDiasVacacionesAlAnio());
		}
		if (registroActualizar.getTasaCargoAdjudicacion() != null) {
			registroAntiguo.setTasaCargoAdjudicacion(registroActualizar.getTasaCargoAdjudicacion());
		}
		if (registroActualizar.getAportePatronalIess() != null) {
			registroAntiguo.setAportePatronalIess(registroActualizar.getAportePatronalIess());
		}
		if (registroActualizar.getAportePersonalIess() != null) {
			registroAntiguo.setAportePersonalIess(registroActualizar.getAportePersonalIess());
		}
		if (registroActualizar.getSueldoBasico() != null) {
			registroAntiguo.setSueldoBasico(registroActualizar.getSueldoBasico());
		}
		if (registroActualizar.getRastreo() != null) {
			registroAntiguo.setRastreo(registroActualizar.getRastreo());
		}
		if (registroActualizar.getDispositivo() != null) {
			registroAntiguo.setDispositivo(registroActualizar.getDispositivo());
		}

		if (Objects.nonNull(registroActualizar.getCodigoEstablecimiento())) {
			registroAntiguo.setCodigoEstablecimiento(registroActualizar.getCodigoEstablecimiento());
		}

		if (Objects.nonNull(registroActualizar.getPuntoEmision())) {
			registroAntiguo.setPuntoEmision(registroActualizar.getPuntoEmision());
		}

		if (Objects.nonNull(registroActualizar.getPatronalParcial())) {
			registroAntiguo.setPatronalParcial(registroActualizar.getPatronalParcial());
		}

		if (Objects.nonNull(registroActualizar.getPatronalPasante())) {
			registroAntiguo.setPatronalPasante(registroActualizar.getPatronalPasante());
		}

		return registroAntiguo;
	}

	public Integer getNumDiasVacacionesAlAnio() {
		return numDiasVacacionesAlAnio;
	}

	public void setNumDiasVacacionesAlAnio(Integer numDiasVacacionesAlAnio) {
		this.numDiasVacacionesAlAnio = numDiasVacacionesAlAnio;
	}

	public String getCodigoEstablecimiento() {
		return codigoEstablecimiento;
	}

	public void setCodigoEstablecimiento(String codigoEstablecimiento) {
		this.codigoEstablecimiento = codigoEstablecimiento;
	}

	public String getPuntoEmision() {
		return puntoEmision;
	}

	public void setPuntoEmision(String puntoEmision) {
		this.puntoEmision = puntoEmision;
	}

	public BigDecimal getPatronalParcial() {
		return patronalParcial;
	}

	public void setPatronalParcial(BigDecimal patronalParcial) {
		this.patronalParcial = patronalParcial;
	}

	public BigDecimal getPatronalPasante() {
		return patronalPasante;
	}

	public void setPatronalPasante(BigDecimal patronalPasante) {
		this.patronalPasante = patronalPasante;
	}
}
