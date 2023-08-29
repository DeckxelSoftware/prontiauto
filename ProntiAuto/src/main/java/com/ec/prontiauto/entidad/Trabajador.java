package com.ec.prontiauto.entidad;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

@Entity
@Table(name = "trabajador")
public class Trabajador extends AbstractEntities {

	@Column(name = "\"estadoCivil\"", length = 255, nullable = false)
	@CsvBindByName(column = "estado_civil")
	private String estadoCivil;

	@Column(name = "genero", length = 1, nullable = false)
	@CsvBindByName(column = "genero")
	private String genero;

	@Column(name = "\"grupoSanguineo\"", length = 255, nullable = false)
	@CsvBindByName(column = "grupo_sanguineo")
	private String grupoSanguineo;

	@Column(name = "\"nivelEstudios\"", length = 255, nullable = false)
	@CsvBindByName(column = "nivel_estudios")
	private String nivelEstudios;

	@Column(name = "profesion", length = 255, nullable = false)
	@CsvBindByName(column = "profesion")
	private String profesion;

	@Column(name = "\"direccionDomiciliaria\"", length = 255, nullable = false)
	@CsvBindByName(column = "direccion_domiciliaria")
	private String direccionDomiciliaria;

	@Column(name = "\"estadoFamiliar\"", length = 2, nullable = false)
	@CsvBindByName(column = "estado_familiar")
	private String estadoFamiliar;

	@Column(name = "\"numeroAfiliacion\"", length = 255)
	@CsvBindByName(column = "numero_afiliacion")
	private String numeroAfiliacion;

	@Column(name = "discapacidad", length = 1, nullable = false)
	@CsvBindByName(column = "discapacidad")
	private String discapacidad;

	@Column(name = "\"tipoDiscapacidad\"", length = 255)
	@CsvBindByName(column = "tipo_discapacidad")
	private String tipoDiscapacidad;

	@Column(name = "sueldo", nullable = false)
	@CsvBindByName(column = "sueldo")
	private Float sueldo;

	@Column(name = "bonificacion")
	@CsvBindByName(column = "bonificacion")
	private Float bonificacion;

	@Column(name = "\"movilizacionEspecial\"")
	@CsvBindByName(column = "movilizacion_especial")
	private Float movilizacionEspecial;

	@Column(name = "\"componenteSalarialUnifi\"")
	@CsvBindByName(column = "componente_salarial_unifi")
	private Float componenteSalarialUnifi;

	@Column(name = "\"retencionesJudiciales\"")
	@CsvBindByName(column = "retenciones_judiciales")
	private Float retencionesJudiciales;

	@Column(name = "\"polizaPersonal\"")
	@CsvBindByName(column = "poliza_personal")
	private Float polizaPersonal;

	@Column(name = "\"aporteIess\"", length = 2)
	@CsvBindByName(column = "aporte_iess")
	private String aporteIess;

	@Column(name = "\"decimosAnio\"", length = 2)
	@CsvBindByName(column = "decimos_anio")
	private String decimosAnio;

	@Column(name = "\"impuestoRenta\"", length = 2)
	@CsvBindByName(column = "impuesto_renta")
	private String impuestoRenta;

	@Column(name = "\"fondoReservaIess\"", length = 2)
	@CsvBindByName(column = "fondo_reserva_iess")
	private String fondoReservaIess;

	@Column(name = "utilidades", length = 2)
	@CsvBindByName(column = "utilidades")
	private String utilidades;

	@Column(name = "\"pagoFondosReservaMes\"", length = 2)
	@CsvBindByName(column = "pago_fondos_reserva_mes")
	private String pagoFondosReservaMes;

	@Column(name = "\"impuestoRentaPatron\"", length = 2)
	@CsvBindByName(column = "impuesto_renta_patron")
	private String impuestoRentaPatron;

	@Column(name = "vacaciones", length = 2)
	@CsvBindByName(column = "vacaciones")
	private String vacaciones;

	@Column(name = "beneficios", length = 2)
	@CsvBindByName(column = "beneficios")
	private String beneficios;

	@Column(name = "\"pagoDecTercerCuartoMes\"", length = 2)
	@CsvBindByName(column = "pago_dec_tercer_cuarto_mes")
	private String pagoDecTercerCuartoMes;

	@Column(name = "\"cuentaContableNombre\"", length = 255)
	@CsvBindByName(column = "cuenta_contable_nombre")
	private String cuentaContableNombre;

	@Column(name = "\"acumuladoAportePersonalIess\"")
	@CsvBindByName(column = "acumulado_aporte_personal_iess")
	private Float acumuladoAportePersonalIess;

	@Column(name = "\"acumuladoDecimoTercero\"")
	@CsvBindByName(column = "acumulado_decimo_tercero")
	private Float acumuladoDecimoTercero;

	@Column(name = "\"acumuladoFondosReserva\"")
	@CsvBindByName(column = "acumulado_fondos_reserva")
	private Float acumuladoFondosReserva;

	@Column(name = "\"acumuladoVacaciones\"")
	@CsvBindByName(column = "acumulado_vacaciones")
	private Float acumuladoVacaciones;

	@Column(name = "\"acumuladoImpuestoRenta\"")
	@CsvBindByName(column = "acumulado_impuesto_renta")
	private Float acumuladoImpuestoRenta;

	@Column(name = "\"utilidadCargas\"")
	@CsvBindByName(column = "utilidad_cargas")
	private Float utilidadCargas;

	@Column(name = "\"tiempoParcial\"", length = 2)
	@CsvBindByName(column = "tiempo_parcial")
	private String tiempoParcial;

	@Column(name = "\"factorParcial\"")
	@CsvBindByName(column = "factor_parcial")
	private Float factorParcial;

	@Column(name = "pasante", length = 2)
	@CsvBindByName(column = "pasante")
	private String pasante;

	@Column(name = "reingreso", length = 2)
	@CsvBindByName(column = "reingreso")
	private String reingreso;

	@Column(name = "\"fechaReingreso\"")
	@CsvDate(value = "yyyy-MM-dd")
	@CsvBindByName(column = "fecha_reingreso")
	private Date fechaReingreso;

	@Column(name = "\"fechaIngreso\"", nullable = false)
	@CsvDate(value = "yyyy-MM-dd")
	@CsvBindByName(column = "fecha_ingreso")
	private Date fechaIngreso;

	@Column(name = "\"provDecimoTercero\"")
	@CsvBindByName(column = "prov_decimo_tercero")
	private Float provDecimoTercero;

	@Column(name = "\"decimoCuarto\"")
	@CsvBindByName(column = "decimo_cuarto")
	private Float decimoCuarto;

	@Column(name = "\"provDecimoCuarto\"")
	@CsvBindByName(column = "prov_decimo_cuarto")
	private Float provDecimoCuarto;

	@Column(name = "\"provFondosReserva\"")
	@CsvBindByName(column = "prov_fondos_reserva")
	private Float provFondosReserva;

	@Column(name = "\"provVacaciones\"")
	@CsvBindByName(column = "prov_vacaciones")
	private Float provVacaciones;

	@Column(name = "\"provAportePatronal\"")
	@CsvBindByName(column = "prov_aporte_patronal")
	private Float provAportePatronal;

	@OneToOne
	@JoinColumn(name = "\"idUsuario\"", referencedColumnName = "id", nullable = false)
	private Usuario idUsuario;

	@Transient
	@CsvBindByName(column = "id_usuario")
	private Integer idUsuario1;

	@JsonIgnore
	@OneToOne(mappedBy = "idTrabajador")
	private Supervisor idSupervisor;

	@JsonIgnore
	@OneToOne(mappedBy = "idTrabajador")
	private Vendedor idVendedor;

	@JsonIgnore
	@OneToMany(mappedBy = "idTrabajador")
	private List<CargaFamiliar> cargaFamiliarCollection;

	@JsonIgnore
	@OneToMany(mappedBy = "idTrabajador")
	private List<HistorialLaboral> historialLaboralCollection;

	@JsonIgnore
	@OneToMany(mappedBy = "idTrabajador")
	private List<Memorandum> memorandumCollection;

	@JsonIgnore
	@OneToMany(mappedBy = "idTrabajador")
	private List<InformacionFinanciera> informacionFinancieraCollection;

	@JsonIgnore
	@OneToMany(mappedBy = "idTrabajador")
	private List<Prestamo> prestamoCollection;

	@JsonIgnore
	@OneToMany(mappedBy = "idTrabajador")
	private List<CargoVacacion> cargoVacacionCollection;

	@JsonIgnore
	@OneToMany(mappedBy = "idTrabajador")
	private List<SriGastos> sriGastosCollection;

	@OneToMany(mappedBy = "idTrabajador")
	private List<DetalleNovedadRolPago> detalleNovedadRolPagoCollection;

	@OneToMany(mappedBy = "idTrabajador")
	private List<PagosDos> pagosDosCollection;

	@ManyToOne
	@JoinColumn(name = "\"idAgencia\"", referencedColumnName = "id", nullable = false)
	private Agencia idAgencia;

	@Transient
	@CsvBindByName(column = "id_agencia")
	private Integer idAgencia1;


	@JsonIgnore
	@OneToMany(mappedBy = "trabajador")
	private List<Finiquito> finiquitosCollection;

	public Trabajador() {
	}

	public Agencia getIdAgencia() {
		return idAgencia;
	}

	public void setIdAgencia(Agencia idAgencia) {
		this.idAgencia = idAgencia;
	}

	public Integer getIdAgencia1() {
		return idAgencia1;
	}

	public void setIdAgencia1(Integer idAgencia1) {
		this.idAgencia1 = idAgencia1;
	}

	public List<DetalleNovedadRolPago> getDetalleNovedadRolPagoCollection() {
		return detalleNovedadRolPagoCollection;
	}

	public void setDetalleNovedadRolPagoCollection(List<DetalleNovedadRolPago> detalleNovedadRolPagoCollection) {
		this.detalleNovedadRolPagoCollection = detalleNovedadRolPagoCollection;
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

	public Usuario getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Usuario idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getIdUsuario1() {
		return idUsuario1;
	}

	public void setIdUsuario1(Integer idUsuario1) {
		this.idUsuario1 = idUsuario1;
	}

	public Supervisor getIdSupervisor() {
		return idSupervisor;
	}

	public void setIdSupervisor(Supervisor idSupervisor) {
		this.idSupervisor = idSupervisor;
	}

	public Vendedor getIdVendedor() {
		return idVendedor;
	}

	public void setIdVendedor(Vendedor idVendedor) {
		this.idVendedor = idVendedor;
	}

	public List<CargaFamiliar> getCargaFamiliarCollection() {
		return cargaFamiliarCollection;
	}

	public void setCargaFamiliarCollection(List<CargaFamiliar> cargaFamiliarCollection) {
		this.cargaFamiliarCollection = cargaFamiliarCollection;
	}

	public List<HistorialLaboral> getHistorialLaboralCollection() {
		return historialLaboralCollection;
	}

	public void setHistorialLaboralCollection(List<HistorialLaboral> historialLaboralCollection) {
		this.historialLaboralCollection = historialLaboralCollection;
	}

	public List<Memorandum> getMemorandumCollection() {
		return memorandumCollection;
	}

	public void setMemorandumCollection(List<Memorandum> memorandumCollection) {
		this.memorandumCollection = memorandumCollection;
	}

	public List<InformacionFinanciera> getInformacionFinancieraCollection() {
		return informacionFinancieraCollection;
	}

	public void setInformacionFinancieraCollection(List<InformacionFinanciera> informacionFinancieraCollection) {
		this.informacionFinancieraCollection = informacionFinancieraCollection;
	}

	public List<Prestamo> getPrestamoCollection() {
		return prestamoCollection;
	}

	public void setPrestamoCollection(List<Prestamo> prestamoCollection) {
		this.prestamoCollection = prestamoCollection;
	}

	public List<CargoVacacion> getCargoVacacionCollection() {
		return cargoVacacionCollection;
	}

	public void setCargoVacacionCollection(List<CargoVacacion> cargoVacacionCollection) {
		this.cargoVacacionCollection = cargoVacacionCollection;
	}

	public List<SriGastos> getSriGastosCollection() {
		return this.sriGastosCollection;
	}

	public void setSriGastosCollection(List<SriGastos> sriGastosCollection) {
		this.sriGastosCollection = sriGastosCollection;
	}

	public List<PagosDos> getPagosDosCollection() {
		return pagosDosCollection;
	}

	public void setPagosDosCollection(List<PagosDos> pagosDosCollection) {
		this.pagosDosCollection = pagosDosCollection;
	}

	public List<Finiquito> getFiniquitosCollection() {
		return finiquitosCollection;
	}

	public void setFiniquitosCollection(List<Finiquito> finiquitosCollection) {
		this.finiquitosCollection = finiquitosCollection;
	}

	public Trabajador setValoresDiferentes(Trabajador registroAntiguo, Trabajador registroActualizar) {
		if (registroActualizar.getSisHabilitado() != null) {
			registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
		}
		if (registroActualizar.getEstadoCivil() != null) {
			registroAntiguo.setEstadoCivil(registroActualizar.getEstadoCivil());
		}
		if (registroActualizar.getGenero() != null) {
			registroAntiguo.setGenero(registroActualizar.getGenero());
		}
		if (registroActualizar.getGrupoSanguineo() != null) {
			registroAntiguo.setGrupoSanguineo(registroActualizar.getGrupoSanguineo());
		}
		if (registroActualizar.getNivelEstudios() != null) {
			registroAntiguo.setNivelEstudios(registroActualizar.getNivelEstudios());
		}
		if (registroActualizar.getProfesion() != null) {
			registroAntiguo.setProfesion(registroActualizar.getProfesion());
		}
		if (registroActualizar.getDireccionDomiciliaria() != null) {
			registroAntiguo.setDireccionDomiciliaria(registroActualizar.getDireccionDomiciliaria());
		}
		if (registroActualizar.getEstadoFamiliar() != null) {
			registroAntiguo.setEstadoFamiliar(registroActualizar.getEstadoFamiliar());
		}
		if (registroActualizar.getNumeroAfiliacion() != null) {
			registroAntiguo.setNumeroAfiliacion(registroActualizar.getNumeroAfiliacion());
		}
		if (registroActualizar.getDiscapacidad() != null) {
			registroAntiguo.setDiscapacidad(registroActualizar.getDiscapacidad());
		}
		if (registroActualizar.getTipoDiscapacidad() != null) {
			registroAntiguo.setTipoDiscapacidad(registroActualizar.getTipoDiscapacidad());
		}
		if (registroActualizar.getIdUsuario().getId() != null) {
			registroAntiguo.setIdUsuario(registroActualizar.getIdUsuario());
		}
		if (registroActualizar.getSueldo() != null) {
			registroAntiguo.setSueldo(registroActualizar.getSueldo());
		}
		if (registroActualizar.getBonificacion() != null) {
			registroAntiguo.setBonificacion(registroActualizar.getBonificacion());
		}
		if (registroActualizar.getMovilizacionEspecial() != null) {
			registroAntiguo.setMovilizacionEspecial(registroActualizar.getMovilizacionEspecial());
		}

		if (registroActualizar.getComponenteSalarialUnifi() != null) {
			registroAntiguo.setComponenteSalarialUnifi(registroActualizar.getComponenteSalarialUnifi());
		}

		if (registroActualizar.getRetencionesJudiciales() != null) {
			registroAntiguo.setRetencionesJudiciales(registroActualizar.getRetencionesJudiciales());
		}

		if (registroActualizar.getPolizaPersonal() != null) {
			registroAntiguo.setPolizaPersonal(registroActualizar.getPolizaPersonal());
		}

		if (registroActualizar.getAporteIess() != null) {
			registroAntiguo.setAporteIess(registroActualizar.getAporteIess());
		}

		if (registroActualizar.getDecimosAnio() != null) {
			registroAntiguo.setDecimosAnio(registroActualizar.getDecimosAnio());
		}

		if (registroActualizar.getImpuestoRenta() != null) {
			registroAntiguo.setImpuestoRenta(registroActualizar.getImpuestoRenta());
		}

		if (registroActualizar.getFondoReservaIess() != null) {
			registroAntiguo.setFondoReservaIess(registroActualizar.getFondoReservaIess());
		}

		if (registroActualizar.getUtilidades() != null) {
			registroAntiguo.setUtilidades(registroActualizar.getUtilidades());
		}
		if (registroActualizar.getPagoFondosReservaMes() != null) {
			registroAntiguo.setPagoFondosReservaMes(registroActualizar.getPagoFondosReservaMes());
		}

		if (registroActualizar.getImpuestoRentaPatron() != null) {
			registroAntiguo.setImpuestoRentaPatron(registroActualizar.getImpuestoRentaPatron());
		}

		if (registroActualizar.getVacaciones() != null) {
			registroAntiguo.setVacaciones(registroActualizar.getVacaciones());
		}

		if (registroActualizar.getBeneficios() != null) {
			registroAntiguo.setBeneficios(registroActualizar.getBeneficios());
		}

		if (registroActualizar.getPagoDecTercerCuartoMes() != null) {
			registroAntiguo.setPagoDecTercerCuartoMes(registroActualizar.getPagoDecTercerCuartoMes());
		}

		if (registroActualizar.getCuentaContableNombre() != null) {
			registroAntiguo.setCuentaContableNombre(registroActualizar.getCuentaContableNombre());
		}

		if (registroActualizar.getAcumuladoAportePersonalIess() != null) {
			registroAntiguo.setAcumuladoAportePersonalIess(registroActualizar.getAcumuladoAportePersonalIess());
		}

		if (registroActualizar.getAcumuladoDecimoTercero() != null) {
			registroAntiguo.setAcumuladoDecimoTercero(registroActualizar.getAcumuladoDecimoTercero());
		}

		if (registroActualizar.getAcumuladoFondosReserva() != null) {
			registroAntiguo.setAcumuladoFondosReserva(registroActualizar.getAcumuladoFondosReserva());
		}

		if (registroActualizar.getAcumuladoVacaciones() != null) {
			registroAntiguo.setAcumuladoVacaciones(registroActualizar.getAcumuladoVacaciones());
		}

		if (registroActualizar.getAcumuladoImpuestoRenta() != null) {
			registroAntiguo.setAcumuladoImpuestoRenta(registroActualizar.getAcumuladoImpuestoRenta());
		}

		if (registroActualizar.getUtilidadCargas() != null) {
			registroAntiguo.setUtilidadCargas(registroActualizar.getUtilidadCargas());
		}

		if (registroActualizar.getTiempoParcial() != null) {
			registroAntiguo.setTiempoParcial(registroActualizar.getTiempoParcial());
		}

		if (registroActualizar.getFactorParcial() != null) {
			registroAntiguo.setFactorParcial(registroActualizar.getFactorParcial());
		}

		if (registroActualizar.getPasante() != null) {
			registroAntiguo.setPasante(registroActualizar.getPasante());
		}

		if (registroActualizar.getReingreso() != null) {
			registroAntiguo.setReingreso(registroActualizar.getReingreso());
		}

		if (registroActualizar.getFechaReingreso() != null) {
			registroAntiguo.setFechaReingreso(registroActualizar.getFechaReingreso());
		}

		if (registroActualizar.getFechaIngreso() != null) {
			registroAntiguo.setFechaIngreso(registroActualizar.getFechaIngreso());
		}

		if (registroActualizar.getProvDecimoTercero() != null) {
			registroAntiguo.setProvDecimoTercero(registroActualizar.getProvDecimoTercero());
		}

		if (registroActualizar.getDecimoCuarto() != null) {
			registroAntiguo.setDecimoCuarto(registroActualizar.getDecimoCuarto());
		}

		if (registroActualizar.getProvDecimoCuarto() != null) {
			registroAntiguo.setProvDecimoCuarto(registroActualizar.getProvDecimoCuarto());
		}

		if (registroActualizar.getProvFondosReserva() != null) {
			registroAntiguo.setProvFondosReserva(registroActualizar.getProvFondosReserva());
		}

		if (registroActualizar.getProvVacaciones() != null) {
			registroAntiguo.setProvVacaciones(registroActualizar.getProvVacaciones());
		}

		if (registroActualizar.getProvAportePatronal() != null) {
			registroAntiguo.setProvAportePatronal(registroActualizar.getProvAportePatronal());
		}

		if(registroActualizar.getIdAgencia().getId() != null){
			registroAntiguo.setIdAgencia(registroActualizar.getIdAgencia());
		}
		
		return registroAntiguo;
	}
}
