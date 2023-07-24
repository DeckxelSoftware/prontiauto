package com.ec.prontiauto.dao;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import com.ec.prontiauto.abstracts.AbstractResponseDao;
import com.ec.prontiauto.entidad.Vendedor;

public class ContratoResponseDao extends AbstractResponseDao {

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
	private BigDecimal cuotaActual;
	private String tipoDocumentoIdentidad;
	private String documentoIdentidad;
	private String planSeleccionado;
	private BigDecimal precioPlanSeleccionado;
	private String nombresCliente;
	private String apellidosCliente;
	private String nombreGrupo;
	private BigDecimal dsctoRecargo;
	private ClienteEnGrupoResponseDao idClienteEnGrupo;
	private Vendedor idVendedor;
	private List<HistoricoPlanContratoResponseDao> historicoPlanContratoCollection;
	private List<LicitacionResponseDao> licitacionCollection;

	public ContratoResponseDao() {

	}

	public BigDecimal getCuotaActual() {
		return cuotaActual;
	}

	public void setCuotaActual(BigDecimal cuotaActual) {
		this.cuotaActual = cuotaActual;
	}

	public String getTipoDocumentoIdentidad() {
		return tipoDocumentoIdentidad;
	}

	public void setTipoDocumentoIdentidad(String tipoDocumentoIdentidad) {
		this.tipoDocumentoIdentidad = tipoDocumentoIdentidad;
	}

	public String getDocumentoIdentidad() {
		return documentoIdentidad;
	}

	public void setDocumentoIdentidad(String documentoIdentidad) {
		this.documentoIdentidad = documentoIdentidad;
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

	public BigDecimal getDsctoRecargo() {
		return dsctoRecargo;
	}

	public void setDsctoRecargo(BigDecimal dsctoRecargo) {
		this.dsctoRecargo = dsctoRecargo;
	}

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

	public List<HistoricoPlanContratoResponseDao> getHistoricoPlanContratoCollection() {
		return historicoPlanContratoCollection;
	}

	public void setHistoricoPlanContratoCollection(
			List<HistoricoPlanContratoResponseDao> historicoPlanContratoCollection) {
		this.historicoPlanContratoCollection = historicoPlanContratoCollection;
	}

	public ClienteEnGrupoResponseDao getIdClienteEnGrupo() {
		return idClienteEnGrupo;
	}

	public void setIdClienteEnGrupo(ClienteEnGrupoResponseDao idClienteEnGrupo) {
		this.idClienteEnGrupo = idClienteEnGrupo;
	}

	public Vendedor getIdVendedor() {
		return idVendedor;
	}

	public void setIdVendedor(Vendedor idVendedor) {
		this.idVendedor = idVendedor;
	}

	public List<LicitacionResponseDao> getLicitacionCollection() {
		return licitacionCollection;
	}

	public void setLicitacionCollection(List<LicitacionResponseDao> licitacionCollection) {
		this.licitacionCollection = licitacionCollection;
	}

}
