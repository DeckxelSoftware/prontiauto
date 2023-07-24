package com.ec.prontiauto.dao;



import java.sql.Date;

import com.ec.prontiauto.abstracts.AbstractRequestDao;

public class HistorialLaboralRequestDao extends AbstractRequestDao {

	private String tipoContrato;
	private Float sueldo;
	private Date fechaIngreso ;
	private Date fechaFin ;
	private String duracion;
	private String fueAscendido;
	private String codigoSectorial;
	
	private Integer idTrabajador;
	private Integer idAgencia;
	private Integer idCargo;
	private Integer idDivisionAdministrativa;
	//recursivo
	private Integer idHistorialLaboral;

	public HistorialLaboralRequestDao() {
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

	public Integer getIdTrabajador() {
		return idTrabajador;
	}

	public void setIdTrabajador(Integer idTrabajador) {
		this.idTrabajador = idTrabajador;
	}

	public Integer getIdAgencia() {
		return idAgencia;
	}

	public void setIdAgencia(Integer idAgencia) {
		this.idAgencia = idAgencia;
	}

	public Integer getIdCargo() {
		return idCargo;
	}

	public void setIdCargo(Integer idCargo) {
		this.idCargo = idCargo;
	}

	public Integer getIdDivisionAdministrativa() {
		return idDivisionAdministrativa;
	}

	public void setIdDivisionAdministrativa(Integer idDivisionAdministrativa) {
		this.idDivisionAdministrativa = idDivisionAdministrativa;
	}

	public Integer getIdHistorialLaboral() {
		return idHistorialLaboral;
	}

	public void setIdHistorialLaboral(Integer idHistorialLaboral) {
		this.idHistorialLaboral = idHistorialLaboral;
	}
	
	
}
