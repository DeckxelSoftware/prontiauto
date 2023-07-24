package com.ec.prontiauto.dao;

import java.math.BigDecimal;
import java.sql.Date;

import com.ec.prontiauto.abstracts.AbstractRequestDao;

public class ContratoRequestDao extends AbstractRequestDao {

	private Double numeroDeContrato;
	private Date fechaInicio;
	private Date fechaFin;
	private BigDecimal dsctoInscripcion;
	private BigDecimal dsctoPrimeraCuota;
	private String observacion;
	private Date fechaIniciaCobro;
	private String estado;
	private Integer plazoMesSeleccionado;
	private Integer version;
	private Integer idClienteEnGrupo;
	private Integer idVendedor;
	private BigDecimal CuotaActual;
	private String tipoDocumentoIdentidad;
	private String planSeleccionado;
	private BigDecimal precioPlanSeleccionado;
	private String nombresCliente;
	private String apellidosCliente;
	private String nombreGrupo;

	public Double getNumeroDeContrato() {
		return numeroDeContrato;
	}

	public void setNumeroDeContrato(Double numeroDeContrato) {
		this.numeroDeContrato = numeroDeContrato;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public BigDecimal getDsctoInscripcion() {
		return dsctoInscripcion;
	}

	public void setDsctoInscripcion(BigDecimal dsctoInscripcion) {
		this.dsctoInscripcion = dsctoInscripcion;
	}

	public BigDecimal getDsctoPrimeraCuota() {
		return dsctoPrimeraCuota;
	}

	public void setDsctoPrimeraCuota(BigDecimal dsctoPrimeraCuota) {
		this.dsctoPrimeraCuota = dsctoPrimeraCuota;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Date getFechaIniciaCobro() {
		return fechaIniciaCobro;
	}

	public void setFechaIniciaCobro(Date fechaIniciaCobro) {
		this.fechaIniciaCobro = fechaIniciaCobro;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getPlazoMesSeleccionado() {
		return plazoMesSeleccionado;
	}

	public void setPlazoMesSeleccionado(Integer plazoMesSeleccionado) {
		this.plazoMesSeleccionado = plazoMesSeleccionado;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getIdClienteEnGrupo() {
		return idClienteEnGrupo;
	}

	public void setIdClienteEnGrupo(Integer idClienteEnGrupo) {
		this.idClienteEnGrupo = idClienteEnGrupo;
	}

	public Integer getIdVendedor() {
		return idVendedor;
	}

	public void setIdVendedor(Integer idVendedor) {
		this.idVendedor = idVendedor;
	}

	public BigDecimal getCuotaActual() {
		return CuotaActual;
	}

	public void setCuotaActual(BigDecimal cuotaActual) {
		CuotaActual = cuotaActual;
	}

	public String getTipoDocumentoIdentidad() {
		return tipoDocumentoIdentidad;
	}

	public void setTipoDocumentoIdentidad(String tipoDocumentoIdentidad) {
		this.tipoDocumentoIdentidad = tipoDocumentoIdentidad;
	}

	public String getPlanSeleccionado() {
		return planSeleccionado;
	}

	public void setPlanSeleccionado(String planSeleccionado) {
		this.planSeleccionado = planSeleccionado;
	}

	public BigDecimal getPrecioPlanSeleccionado() {
		return precioPlanSeleccionado;
	}

	public void setPrecioPlanSeleccionado(BigDecimal precioPlanSeleccionado) {
		this.precioPlanSeleccionado = precioPlanSeleccionado;
	}

	public String getNombresCliente() {
		return nombresCliente;
	}

	public void setNombresCliente(String nombresCliente) {
		this.nombresCliente = nombresCliente;
	}

	public String getApellidosCliente() {
		return apellidosCliente;
	}

	public void setApellidosCliente(String apellidosCliente) {
		this.apellidosCliente = apellidosCliente;
	}

	public String getNombreGrupo() {
		return nombreGrupo;
	}

	public void setNombreGrupo(String nombreGrupo) {
		this.nombreGrupo = nombreGrupo;
	}

}
