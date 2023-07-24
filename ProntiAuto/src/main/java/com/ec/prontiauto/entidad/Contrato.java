package com.ec.prontiauto.entidad;

import java.math.BigDecimal;
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
@Table(name = "contrato")
public class Contrato extends AbstractEntities {

	@Column(name = "\"numeroDeContrato\"", nullable = false)
	@CsvBindByName(column = "numero_de_contrato")
	private Double numeroDeContrato;

	@Column(name = "\"fechaInicio\"", nullable = false)
	@CsvDate(value = "yyyy-MM-dd")
	@CsvBindByName(column = "fecha_inicio")
	private Date fechaInicio;

	@Column(name = "\"fechaFin\"")
	@CsvDate(value = "yyyy-MM-dd")
	@CsvBindByName(column = "fecha_fin")
	private Date fechaFin;

	@Column(name = "\"dsctoInscripcion\"", nullable = false)
	@CsvBindByName(column = "dscto_inscripcion")
	private BigDecimal dsctoInscripcion;

	@Column(name = "\"dsctoPrimeraCuota\"", nullable = false)
	@CsvBindByName(column = "dscto_primera_cuota")
	private BigDecimal dsctoPrimeraCuota;

	@Column(name = "observacion", length = 255)
	@CsvBindByName(column = "observacion")
	private String observacion;

	@Column(name = "\"fechaIniciaCobro\"", nullable = false)
	@CsvDate(value = "yyyy-MM-dd")
	@CsvBindByName(column = "fecha_inicia_cobro")
	private Date fechaIniciaCobro;

	@Column(name = "estado", length = 255)
	@CsvBindByName(column = "estado")
	private String estado;

	@Column(name = "\"plazoMesSeleccionado\"", nullable = false)
	@CsvBindByName(column = "plazo_mes_seleccionado")
	private Integer plazoMesSeleccionado;

	@Column(name = "version")
	@CsvBindByName(column = "version")
	private Integer version;

	@Column(name = "\"cuotaActual\"")
	@CsvBindByName(column = "cuota_actual")
	private BigDecimal cuotaActual;

	@Column(name = "\"tipoDocumentoIdentidad\" ", length = 255)
	@CsvBindByName(column = "tipo_documento_identidad ")
	private String tipoDocumentoIdentidad;

	@Column(name = "\"documentoIdentidad\"", length = 255)
	@CsvBindByName(column = "documento_identidad")
	private String documentoIdentidad;

	@Column(name = "\"planSeleccionado\"", length = 255)
	@CsvBindByName(column = "plan_seleccionado")
	private String planSeleccionado;

	@Column(name = "\"precioPlanSeleccionado\"")
	@CsvBindByName(column = "precio_plan_seleccionado")
	private BigDecimal precioPlanSeleccionado;

	@Column(name = "\"nombresCliente\"", length = 255)
	@CsvBindByName(column = "nombres_cliente")
	private String nombresCliente;

	@Column(name = "\"apellidosCliente\"", length = 255)
	@CsvBindByName(column = "apellidos_cliente")
	private String apellidosCliente;

	@Column(name = "\"nombreGrupo\"", length = 255)
	@CsvBindByName(column = "nombre_grupo ")
	private String nombreGrupo;

	@Column(name = "\"dsctoRecargo\"")
	@CsvBindByName(column = "dscto_recargo")
	private BigDecimal dsctoRecargo;

	@Column(name = "\"valorFondo\"")
	@CsvBindByName(column = "valor_fondo")
	private BigDecimal valorFondo;

	@Column(name = "\"cuotaACobrar\"")
	@CsvBindByName(column = "cuota_a_cobrar")
	private Integer cuotaACobrar;

	@Column(name = "\"idContratos\"")
	private String idContratos;

	@Column(name = "\"medioCaptacion\"")
	@CsvBindByName(column = "medio_captacion")
	private String medioCaptacion;

	@ManyToOne
	@JoinColumn(name = "\"idClienteEnGrupo\"", referencedColumnName = "id", nullable = true)
	private ClienteEnGrupo idClienteEnGrupo;

	@Transient
	@CsvBindByName(column = "id_cliente_en_grupo")
	private Integer idClienteEnGrupo1;

	@Transient
	@CsvBindByName(column = "id_vendedor")
	private Integer idVendedor1;

	@ManyToOne
	@JoinColumn(name = "\"idVendedor\"", referencedColumnName = "id", nullable = false)
	private Vendedor idVendedor;

	@JsonIgnore
	@OneToMany(mappedBy = "idContrato")
	private List<HistoricoPlanContrato> historicoPlanContratoCollection;

	@JsonIgnore
	@OneToMany(mappedBy = "idContrato")
	private List<Licitacion> licitacionCollection;

	@OneToOne(mappedBy = "idContrato")
	private OrdenCompra idOrdenCompra;

	@OneToMany(mappedBy = "idContrato")
	private List<Cobro> cobroCollection;

	public Contrato() {
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

	public ClienteEnGrupo getIdClienteEnGrupo() {
		return idClienteEnGrupo;
	}

	public void setIdClienteEnGrupo(ClienteEnGrupo idClienteEnGrupo) {
		this.idClienteEnGrupo = idClienteEnGrupo;
	}

	public List<HistoricoPlanContrato> getHistoricoPlanContratoCollection() {
		return historicoPlanContratoCollection;
	}

	public void setHistoricoPlanContratoCollection(List<HistoricoPlanContrato> historicoPlanContratoCollection) {
		this.historicoPlanContratoCollection = historicoPlanContratoCollection;
	}

	public Integer getIdClienteEnGrupo1() {
		return idClienteEnGrupo1;
	}

	public void setIdClienteEnGrupo1(Integer idClienteEnGrupo1) {
		this.idClienteEnGrupo1 = idClienteEnGrupo1;
	}

	public Vendedor getIdVendedor() {
		return idVendedor;
	}

	public void setIdVendedor(Vendedor idVendedor) {
		this.idVendedor = idVendedor;
	}

	public Integer getIdVendedor1() {
		return idVendedor1;
	}

	public void setIdVendedor1(Integer idVendedor1) {
		this.idVendedor1 = idVendedor1;
	}

	public List<Licitacion> getLicitacionCollection() {
		return licitacionCollection;
	}

	public void setLicitacionCollection(List<Licitacion> licitacionCollection) {
		this.licitacionCollection = licitacionCollection;
	}

	public OrdenCompra getIdOrdenCompra() {
		return idOrdenCompra;
	}

	public void setIdOrdenCompra(OrdenCompra idOrdenCompra) {
		this.idOrdenCompra = idOrdenCompra;
	}

	public BigDecimal getValorFondo() {
		return valorFondo;
	}

	public void setValorFondo(BigDecimal valorFondo) {
		this.valorFondo = valorFondo;
	}

	public Integer getCuotaACobrar() {
		return cuotaACobrar;
	}

	public void setCuotaACobrar(Integer cuotaACobrar) {
		this.cuotaACobrar = cuotaACobrar;
	}

	public List<Cobro> getCobroCollection() {
		return cobroCollection;
	}

	public void setCobroCollection(List<Cobro> cobroCollection) {
		this.cobroCollection = cobroCollection;
	}

}
