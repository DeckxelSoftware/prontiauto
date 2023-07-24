package com.ec.prontiauto.entidad;

import java.sql.Date;
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
import com.opencsv.bean.CsvDate;

@Entity
@Table(name = "historial_laboral")
public class HistorialLaboral extends AbstractEntities {

	@Column(name = "\"tipoContrato\"", nullable = false, length = 255)
	@CsvBindByName(column = "tipo_contrato")
	private String tipoContrato;

	@Column(name = "sueldo", nullable = false)
	@CsvBindByName(column = "sueldo")
	private Float sueldo;

	@Column(name = "\"fechaIngreso\"")
	@CsvDate(value = "yyyy-MM-dd")
	@CsvBindByName(column = "fecha_ingreso")
	private Date fechaIngreso;

	@Column(name = "\"fechaFin\"")
	@CsvDate(value = "yyyy-MM-dd")
	@CsvBindByName(column = "fecha_fin")
	private Date fechaFin;

	@Column(name = "\"duracion\"", length = 255)
	@CsvBindByName(column = "duracion")
	private String duracion;

	@Column(name = "\"fueAscendido\"", nullable = false, length = 1)
	@CsvBindByName(column = "fue_ascendido")
	private String fueAscendido;

	@Column(name = "\"codigoSectorial\"", length = 255)
	@CsvBindByName(column = "codigo_sectorial")
	private String codigoSectorial;

	@ManyToOne
	@JoinColumn(name = "\"idTrabajador\"", referencedColumnName = "id", nullable = false)
	private Trabajador idTrabajador;

	@Transient
	@CsvBindByName(column = "id_trabajador")
	private Integer idTrabajador1;

	@ManyToOne
	@JoinColumn(name = "\"idAgencia\"", referencedColumnName = "id", nullable = false)
	private Agencia idAgencia;

	@Transient
	@CsvBindByName(column = "id_agencia")
	private Integer idAgencia1;

	@ManyToOne
	@JoinColumn(name = "\"idCargo\"", referencedColumnName = "id", nullable = false)
	private Cargo idCargo;

	@Transient
	@CsvBindByName(column = "id_cargo")
	private Integer idCargo1;

	@ManyToOne
	@JoinColumn(name = "\"idDivisionAdministrativa\"", referencedColumnName = "id", nullable = false)
	private DivisionAdministrativa idDivisionAdministrativa;

	@Transient
	@CsvBindByName(column = "id_division_administrativa")
	private Integer idDivisionAdministrativa1;

	// Relacion Recursiva
	@JsonIgnore
	@OneToMany(mappedBy = "idHistorialLaboral")
	private List<HistorialLaboral> historialLaboralCollection;

	@ManyToOne
	@JoinColumn(name = "\"idHistorialLaboral\"", referencedColumnName = "id", nullable = true)
	private HistorialLaboral idHistorialLaboral;

	@JsonIgnore
	@OneToMany(mappedBy = "idHistorialLaboral")
	private List<RolPago> rolPagoCollection;

	public HistorialLaboral() {
	}

	public String getTipoContrato() {
		return tipoContrato;
	}

	public void setTipoContrato(String tipoContrato) {
		this.tipoContrato = tipoContrato;
	}

	public Float getSueldo() {
		return sueldo;
	}

	public void setSueldo(Float sueldo) {
		this.sueldo = sueldo;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getDuracion() {
		return duracion;
	}

	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}

	public String getFueAscendido() {
		return fueAscendido;
	}

	public void setFueAscendido(String fueAscendido) {
		this.fueAscendido = fueAscendido;
	}

	public String getCodigoSectorial() {
		return codigoSectorial;
	}

	public void setCodigoSectorial(String codigoSectorial) {
		this.codigoSectorial = codigoSectorial;
	}

	public Trabajador getIdTrabajador() {
		return idTrabajador;
	}

	public void setIdTrabajador(Trabajador idTrabajador) {
		this.idTrabajador = idTrabajador;
	}

	public Integer getIdTrabajador1() {
		return idTrabajador1;
	}

	public void setIdTrabajador1(Integer idTrabajador1) {
		this.idTrabajador1 = idTrabajador1;
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

	public Cargo getIdCargo() {
		return idCargo;
	}

	public void setIdCargo(Cargo idCargo) {
		this.idCargo = idCargo;
	}

	public Integer getIdCargo1() {
		return idCargo1;
	}

	public void setIdCargo1(Integer idCargo1) {
		this.idCargo1 = idCargo1;
	}

	public List<HistorialLaboral> getHistorialLaboralCollection() {
		return historialLaboralCollection;
	}

	public void setHistorialLaboralCollection(List<HistorialLaboral> historialLaboralCollection) {
		this.historialLaboralCollection = historialLaboralCollection;
	}

	public HistorialLaboral getIdHistorialLaboral() {
		return idHistorialLaboral;
	}

	public void setIdHistorialLaboral(HistorialLaboral idHistorialLaboral) {
		this.idHistorialLaboral = idHistorialLaboral;
	}

	public DivisionAdministrativa getIdDivisionAdministrativa() {
		return idDivisionAdministrativa;
	}

	public void setIdDivisionAdministrativa(DivisionAdministrativa idDivisionAdministrativa) {
		this.idDivisionAdministrativa = idDivisionAdministrativa;
	}

	public Integer getIdDivisionAdministrativa1() {
		return idDivisionAdministrativa1;
	}

	public void setIdDivisionAdministrativa1(Integer idDivisionAdministrativa1) {
		this.idDivisionAdministrativa1 = idDivisionAdministrativa1;
	}

	public List<RolPago> getRolPagoCollection() {
		return rolPagoCollection;
	}

	public void setRolPagoCollection(List<RolPago> rolPagoCollection) {
		this.rolPagoCollection = rolPagoCollection;
	}

	public HistorialLaboral setValoresDiferentes(HistorialLaboral registroAntiguo,
			HistorialLaboral registroActualizar) {

		if (registroActualizar.getTipoContrato() != null) {
			registroAntiguo.setTipoContrato(registroActualizar.getTipoContrato());
		}
		if (registroActualizar.getSueldo() != null) {
			registroAntiguo.setSueldo(registroActualizar.getSueldo());
		}
		if (registroActualizar.getFechaIngreso() != null) {
			registroAntiguo.setFechaIngreso(registroActualizar.getFechaIngreso());
		}
		if (registroActualizar.getFechaFin() != null) {
			registroAntiguo.setFechaFin(registroActualizar.getFechaFin());
		}
		if (registroActualizar.getDuracion() != null) {
			registroAntiguo.setDuracion(registroActualizar.getDuracion());
		}
		if (registroActualizar.getFueAscendido() != null) {
			registroAntiguo.setFueAscendido(registroActualizar.getFueAscendido());
		}
		if (registroActualizar.getSisHabilitado() != null) {
			registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
		}
		if (registroActualizar.getIdTrabajador() != null
				&& registroActualizar.getIdTrabajador().getId() != null) {
			registroAntiguo.setIdTrabajador(registroActualizar.getIdTrabajador());
		}
		if (registroActualizar.getIdAgencia() != null
				&& registroActualizar.getIdAgencia().getId() != null) {
			registroAntiguo.setIdAgencia(registroActualizar.getIdAgencia());
		}
		if (registroActualizar.getIdCargo() != null
				&& registroActualizar.getIdCargo().getId() != null) {
			registroAntiguo.setIdCargo(registroActualizar.getIdCargo());
		}
		if (registroActualizar.getIdDivisionAdministrativa() != null
				&& registroActualizar.getIdDivisionAdministrativa().getId() != null) {
			registroAntiguo.setIdDivisionAdministrativa(registroActualizar.getIdDivisionAdministrativa());
		}
		if (registroActualizar.getIdHistorialLaboral() != null
				&& registroActualizar.getIdHistorialLaboral().getId() != null) {
			registroAntiguo.setIdHistorialLaboral(registroActualizar.getIdHistorialLaboral());
		}
		return registroAntiguo;
	}

}
