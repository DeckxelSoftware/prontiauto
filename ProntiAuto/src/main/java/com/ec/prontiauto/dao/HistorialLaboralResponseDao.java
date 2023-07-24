package com.ec.prontiauto.dao;

import java.sql.Date;
import java.util.List;

import com.ec.prontiauto.abstracts.AbstractResponseDao;

public class HistorialLaboralResponseDao extends AbstractResponseDao {

	private String tipoContrato;
	private Float sueldo;
	private Date fechaIngreso;
	private Date fechaFin;
	private String duracion;
	private String fueAscendido;
	private String codigoSectorial;

	private TrabajadorResponseDao idTrabajador;
	private AgenciaResponseDao idAgencia;
	private CargoResponseDao idCargo;
	private DivisionAdministrativaResponseDao idDivisionAdministrativa;
	// Recursivo Hijo
	private HistorialLaboralResponseDao idHistorialLaboral;
	// Recursivo padre
	private List<HistorialLaboralResponseDao> HistorialLaboralCollection;

	private List<RolPagoResponseDao> rolPagoCollection;

	public HistorialLaboralResponseDao() {
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

	public TrabajadorResponseDao getIdTrabajador() {
		return idTrabajador;
	}

	public void setIdTrabajador(TrabajadorResponseDao idTrabajador) {
		this.idTrabajador = idTrabajador;
	}

	public AgenciaResponseDao getIdAgencia() {
		return idAgencia;
	}

	public void setIdAgencia(AgenciaResponseDao idAgencia) {
		this.idAgencia = idAgencia;
	}

	public CargoResponseDao getIdCargo() {
		return idCargo;
	}

	public void setIdCargo(CargoResponseDao idCargo) {
		this.idCargo = idCargo;
	}

	public DivisionAdministrativaResponseDao getIdDivisionAdministrativa() {
		return idDivisionAdministrativa;
	}

	public void setIdDivisionAdministrativa(DivisionAdministrativaResponseDao idDivisionAdministrativa) {
		this.idDivisionAdministrativa = idDivisionAdministrativa;
	}

	public HistorialLaboralResponseDao getIdHistorialLaboral() {
		return idHistorialLaboral;
	}

	public void setIdHistorialLaboral(HistorialLaboralResponseDao idHistorialLaboral) {
		this.idHistorialLaboral = idHistorialLaboral;
	}

	public List<HistorialLaboralResponseDao> getHistorialLaboralCollection() {
		return HistorialLaboralCollection;
	}

	public void setHistorialLaboralCollection(List<HistorialLaboralResponseDao> historialLaboralCollection) {
		HistorialLaboralCollection = historialLaboralCollection;
	}

	public List<RolPagoResponseDao> getRolPagoCollection() {
		return rolPagoCollection;
	}

	public void setRolPagoCollection(List<RolPagoResponseDao> rolPagoCollection) {
		this.rolPagoCollection = rolPagoCollection;
	}

}
