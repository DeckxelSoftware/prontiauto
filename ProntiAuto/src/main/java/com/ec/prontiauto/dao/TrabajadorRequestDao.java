package com.ec.prontiauto.dao;

import java.sql.Date;

import com.ec.prontiauto.abstracts.AbstractRequestDao;

public class TrabajadorRequestDao extends AbstractRequestDao {
	private String estadoCivil;
	private String genero;
	private String grupoSanguineo;
	private String nivelEstudios;
	private String profesion;
	private String direccionDomiciliaria;
	private String estadoFamiliar;
	private String numeroAfiliacion;
	private String discapacidad;
	private String tipoDiscapacidad;
	Integer idUsuario;
	private Float sueldo;
	private Float bonificacion;
	private Float movilizacionEspecial;
	private Float componenteSalarialUnifi;
	private Float retencionesJudiciales;
	private Float polizaPersonal;
	private String aporteIess;
	private String decimosAnio;
	private String impuestoRenta;
	private String fondoReservaIess;
	private String utilidades;
	private String pagoFondosReservaMes;
	private String impuestoRentaPatron;
	private String vacaciones;
	private String beneficios;
	private String pagoDecTercerCuartoMes;
	private String cuentaContableNombre;
	private Float acumuladoAportePersonalIess;
	private Float acumuladoDecimoTercero;
	private Float acumuladoFondosReserva;
	private Float acumuladoVacaciones;
	private Float acumuladoImpuestoRenta;
	private Float utilidadCargas;
	private String tiempoParcial;
	private Float factorParcial;
	private String pasante;
	private String reingreso;
	private Date fechaReingreso;
	private Date fechaIngreso;
	private Float provDecimoTercero;
	private Float decimoCuarto;
	private Float provDecimoCuarto;
	private Float provFondosReserva;
	private Float provVacaciones;
	private Float provAportePatronal;
	private Integer idAgencia;

	public TrabajadorRequestDao() {
	}

	public Float getSueldo() {
		return sueldo;
	}

	public void setSueldo(Float sueldo) {
		this.sueldo = sueldo;
	}

	public Float getBonificacion() {
		return bonificacion;
	}

	public void setBonificacion(Float bonificacion) {
		this.bonificacion = bonificacion;
	}

	public Float getMovilizacionEspecial() {
		return movilizacionEspecial;
	}

	public void setMovilizacionEspecial(Float movilizacionEspecial) {
		this.movilizacionEspecial = movilizacionEspecial;
	}

	public Float getComponenteSalarialUnifi() {
		return componenteSalarialUnifi;
	}

	public void setComponenteSalarialUnifi(Float componenteSalarialUnifi) {
		this.componenteSalarialUnifi = componenteSalarialUnifi;
	}

	public Float getRetencionesJudiciales() {
		return retencionesJudiciales;
	}

	public void setRetencionesJudiciales(Float retencionesJudiciales) {
		this.retencionesJudiciales = retencionesJudiciales;
	}

	public Float getPolizaPersonal() {
		return polizaPersonal;
	}

	public void setPolizaPersonal(Float polizaPersonal) {
		this.polizaPersonal = polizaPersonal;
	}

	public String getAporteIess() {
		return aporteIess;
	}

	public void setAporteIess(String aporteIess) {
		this.aporteIess = aporteIess;
	}

	public String getDecimosAnio() {
		return decimosAnio;
	}

	public void setDecimosAnio(String decimosAnio) {
		this.decimosAnio = decimosAnio;
	}

	public String getImpuestoRenta() {
		return impuestoRenta;
	}

	public void setImpuestoRenta(String impuestoRenta) {
		this.impuestoRenta = impuestoRenta;
	}

	public String getFondoReservaIess() {
		return fondoReservaIess;
	}

	public void setFondoReservaIess(String fondoReservaIess) {
		this.fondoReservaIess = fondoReservaIess;
	}

	public String getUtilidades() {
		return utilidades;
	}

	public void setUtilidades(String utilidades) {
		this.utilidades = utilidades;
	}

	public String getPagoFondosReservaMes() {
		return pagoFondosReservaMes;
	}

	public void setPagoFondosReservaMes(String pagoFondosReservaMes) {
		this.pagoFondosReservaMes = pagoFondosReservaMes;
	}

	public String getImpuestoRentaPatron() {
		return impuestoRentaPatron;
	}

	public void setImpuestoRentaPatron(String impuestoRentaPatron) {
		this.impuestoRentaPatron = impuestoRentaPatron;
	}

	public String getVacaciones() {
		return vacaciones;
	}

	public void setVacaciones(String vacaciones) {
		this.vacaciones = vacaciones;
	}

	public String getBeneficios() {
		return beneficios;
	}

	public void setBeneficios(String beneficios) {
		this.beneficios = beneficios;
	}

	public String getPagoDecTercerCuartoMes() {
		return pagoDecTercerCuartoMes;
	}

	public void setPagoDecTercerCuartoMes(String pagoDecTercerCuartoMes) {
		this.pagoDecTercerCuartoMes = pagoDecTercerCuartoMes;
	}

	public String getCuentaContableNombre() {
		return cuentaContableNombre;
	}

	public void setCuentaContableNombre(String cuentaContableNombre) {
		this.cuentaContableNombre = cuentaContableNombre;
	}

	public Float getAcumuladoAportePersonalIess() {
		return acumuladoAportePersonalIess;
	}

	public void setAcumuladoAportePersonalIess(Float acumuladoAportePersonalIess) {
		this.acumuladoAportePersonalIess = acumuladoAportePersonalIess;
	}

	public Float getAcumuladoDecimoTercero() {
		return acumuladoDecimoTercero;
	}

	public void setAcumuladoDecimoTercero(Float acumuladoDecimoTercero) {
		this.acumuladoDecimoTercero = acumuladoDecimoTercero;
	}

	public Float getAcumuladoFondosReserva() {
		return acumuladoFondosReserva;
	}

	public void setAcumuladoFondosReserva(Float acumuladoFondosReserva) {
		this.acumuladoFondosReserva = acumuladoFondosReserva;
	}

	public Float getAcumuladoVacaciones() {
		return acumuladoVacaciones;
	}

	public void setAcumuladoVacaciones(Float acumuladoVacaciones) {
		this.acumuladoVacaciones = acumuladoVacaciones;
	}

	public Float getAcumuladoImpuestoRenta() {
		return acumuladoImpuestoRenta;
	}

	public void setAcumuladoImpuestoRenta(Float acumuladoImpuestoRenta) {
		this.acumuladoImpuestoRenta = acumuladoImpuestoRenta;
	}

	public Float getUtilidadCargas() {
		return utilidadCargas;
	}

	public void setUtilidadCargas(Float utilidadCargas) {
		this.utilidadCargas = utilidadCargas;
	}

	public String getTiempoParcial() {
		return tiempoParcial;
	}

	public void setTiempoParcial(String tiempoParcial) {
		this.tiempoParcial = tiempoParcial;
	}

	public Float getFactorParcial() {
		return factorParcial;
	}

	public void setFactorParcial(Float factorParcial) {
		this.factorParcial = factorParcial;
	}

	public String getPasante() {
		return pasante;
	}

	public void setPasante(String pasante) {
		this.pasante = pasante;
	}

	public String getReingreso() {
		return reingreso;
	}

	public void setReingreso(String reingreso) {
		this.reingreso = reingreso;
	}

	public Date getFechaReingreso() {
		return fechaReingreso;
	}

	public void setFechaReingreso(Date fechaReingreso) {
		this.fechaReingreso = fechaReingreso;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public Float getProvDecimoTercero() {
		return provDecimoTercero;
	}

	public void setProvDecimoTercero(Float provDecimoTercero) {
		this.provDecimoTercero = provDecimoTercero;
	}

	public Float getDecimoCuarto() {
		return decimoCuarto;
	}

	public void setDecimoCuarto(Float decimoCuarto) {
		this.decimoCuarto = decimoCuarto;
	}

	public Float getProvDecimoCuarto() {
		return provDecimoCuarto;
	}

	public void setProvDecimoCuarto(Float provDecimoCuarto) {
		this.provDecimoCuarto = provDecimoCuarto;
	}

	public Float getProvFondosReserva() {
		return provFondosReserva;
	}

	public void setProvFondosReserva(Float provFondosReserva) {
		this.provFondosReserva = provFondosReserva;
	}

	public Float getProvVacaciones() {
		return provVacaciones;
	}

	public void setProvVacaciones(Float provVacaciones) {
		this.provVacaciones = provVacaciones;
	}

	public Float getProvAportePatronal() {
		return provAportePatronal;
	}

	public void setProvAportePatronal(Float provAportePatronal) {
		this.provAportePatronal = provAportePatronal;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getGrupoSanguineo() {
		return grupoSanguineo;
	}

	public void setGrupoSanguineo(String grupoSanguineo) {
		this.grupoSanguineo = grupoSanguineo;
	}

	public String getNivelEstudios() {
		return nivelEstudios;
	}

	public void setNivelEstudios(String nivelEstudios) {
		this.nivelEstudios = nivelEstudios;
	}

	public String getProfesion() {
		return profesion;
	}

	public void setProfesion(String profesion) {
		this.profesion = profesion;
	}

	public String getDireccionDomiciliaria() {
		return direccionDomiciliaria;
	}

	public void setDireccionDomiciliaria(String direccionDomiciliaria) {
		this.direccionDomiciliaria = direccionDomiciliaria;
	}

	public String getEstadoFamiliar() {
		return estadoFamiliar;
	}

	public void setEstadoFamiliar(String estadoFamiliar) {
		this.estadoFamiliar = estadoFamiliar;
	}

	public String getNumeroAfiliacion() {
		return numeroAfiliacion;
	}

	public void setNumeroAfiliacion(String numeroAfiliacion) {
		this.numeroAfiliacion = numeroAfiliacion;
	}

	public String getDiscapacidad() {
		return discapacidad;
	}

	public void setDiscapacidad(String discapacidad) {
		this.discapacidad = discapacidad;
	}

	public String getTipoDiscapacidad() {
		return tipoDiscapacidad;
	}

	public void setTipoDiscapacidad(String tipoDiscapacidad) {
		this.tipoDiscapacidad = tipoDiscapacidad;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getIdAgencia() {
		return idAgencia;
	}

	public void setIdAgencia(Integer idAgencia) {
		this.idAgencia = idAgencia;
	}

}
