package com.ec.prontiauto.entidad;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "rubros_rol")
public class RubrosRol extends AbstractEntities {

	@Column(name = "\"codigoAuxiliar\"", nullable = false, length = 255)
	@CsvBindByName(column = "codigo_auxiliar")
	private String codigoAuxiliar;

	@Column(name = "\"nombre\"", nullable = false, length = 255)
	@CsvBindByName(column = "nombre")
	private String nombre;

	@Column(name = "\"nombreAuxiliarUno\"", nullable = false, length = 255)
	@CsvBindByName(column = "nombre_auxiliar_uno")
	private String nombreAuxiliarUno;

	@Column(name = "\"nombreAuxiliarDos\"", nullable = false, length = 255)
	@CsvBindByName(column = "nombre_auxiliar_dos")
	private String nombreAuxiliarDos;

	@Column(name = "\"unidad\"", nullable = false, length = 255)
	@CsvBindByName(column = "unidad")
	private String unidad;

	@Column(name = "\"calculaIess\"", nullable = false, length = 2)
	@CsvBindByName(column = "calcula_iess")
	private String calculaIess;

	@Column(name = "\"calculaRenta\"", nullable = false, length = 2)
	@CsvBindByName(column = "calcula_renta")
	private String calculaRenta;

	@Column(name = "\"calculaDecTercero\"", nullable = false, length = 2)
	@CsvBindByName(column = "calcula_dec_tercero")
	private String calculaDecTercero;

	@Column(name = "\"calculaDecCuarto\"", nullable = false, length = 2)
	@CsvBindByName(column = "calcula_dec_cuarto")
	private String calculaDecCuarto;

	@Column(name = "\"calculaFReserva\"", nullable = false, length = 2)
	@CsvBindByName(column = "calcula_f_reserva")
	private String calculaFReserva;

	@Column(name = "\"calculaVacaciones\"", nullable = false, length = 2)
	@CsvBindByName(column = "calcula_vacaciones")
	private String calculaVacaciones;

	@Column(name = "\"seSuma\"", nullable = false, length = 2)
	@CsvBindByName(column = "se_suma")
	private String seSuma;

	@OneToMany(mappedBy = "idRubrosRol")
	private List<DetalleNovedadRolPago> detalleNovedadRolPagoCollection;

	public RubrosRol() {
	}

	public String getCodigoAuxiliar() {
		return codigoAuxiliar;
	}

	public void setCodigoAuxiliar(String codigoAuxiliar) {
		this.codigoAuxiliar = codigoAuxiliar;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombreAuxiliarUno() {
		return nombreAuxiliarUno;
	}

	public void setNombreAuxiliarUno(String nombreAuxiliarUno) {
		this.nombreAuxiliarUno = nombreAuxiliarUno;
	}

	public String getNombreAuxiliarDos() {
		return nombreAuxiliarDos;
	}

	public void setNombreAuxiliarDos(String nombreAuxiliarDos) {
		this.nombreAuxiliarDos = nombreAuxiliarDos;
	}

	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	public String getCalculaIess() {
		return calculaIess;
	}

	public void setCalculaIess(String calculaIess) {
		this.calculaIess = calculaIess;
	}

	public String getCalculaRenta() {
		return calculaRenta;
	}

	public void setCalculaRenta(String calculaRenta) {
		this.calculaRenta = calculaRenta;
	}

	public String getCalculaDecTercero() {
		return calculaDecTercero;
	}

	public void setCalculaDecTercero(String calculaDecTercero) {
		this.calculaDecTercero = calculaDecTercero;
	}

	public String getCalculaDecCuarto() {
		return calculaDecCuarto;
	}

	public void setCalculaDecCuarto(String calculaDecCuarto) {
		this.calculaDecCuarto = calculaDecCuarto;
	}

	public String getCalculaFReserva() {
		return calculaFReserva;
	}

	public void setCalculaFReserva(String calculaFReserva) {
		this.calculaFReserva = calculaFReserva;
	}

	public String getCalculaVacaciones() {
		return calculaVacaciones;
	}

	public void setCalculaVacaciones(String calculaVacaciones) {
		this.calculaVacaciones = calculaVacaciones;
	}

	public String getSeSuma() {
		return seSuma;
	}

	public void setSeSuma(String seSuma) {
		this.seSuma = seSuma;
	}

	public RubrosRol setValoresDiferentes(RubrosRol registroAntiguo, RubrosRol registroActualizar) {

		if (registroActualizar.getCodigoAuxiliar() != null) {
			registroAntiguo.setCodigoAuxiliar(registroActualizar.getCodigoAuxiliar());
		}
		if (registroActualizar.getNombre() != null) {
			registroAntiguo.setNombre(registroActualizar.getNombre());
		}
		if (registroActualizar.getNombreAuxiliarUno() != null) {
			registroAntiguo.setNombreAuxiliarUno(registroActualizar.getNombreAuxiliarUno());
		}
		if (registroActualizar.getNombreAuxiliarDos() != null) {
			registroAntiguo.setNombreAuxiliarDos(registroActualizar.getNombreAuxiliarDos());
		}
		if (registroActualizar.getUnidad() != null) {
			registroAntiguo.setUnidad(registroActualizar.getUnidad());
		}
		if (registroActualizar.getCalculaIess() != null) {
			registroAntiguo.setCalculaIess(registroActualizar.getCalculaIess());
		}
		if (registroActualizar.getCalculaRenta() != null) {
			registroAntiguo.setCalculaRenta(registroActualizar.getCalculaRenta());
		}
		if (registroActualizar.getCalculaDecTercero() != null) {
			registroAntiguo.setCalculaDecTercero(registroActualizar.getCalculaDecTercero());
		}
		if (registroActualizar.getCalculaDecCuarto() != null) {
			registroAntiguo.setCalculaDecCuarto(registroActualizar.getCalculaDecCuarto());
		}
		if (registroActualizar.getCalculaFReserva() != null) {
			registroAntiguo.setCalculaFReserva(registroActualizar.getCalculaFReserva());
		}
		if (registroActualizar.getCalculaVacaciones() != null) {
			registroAntiguo.setCalculaVacaciones(registroActualizar.getCalculaVacaciones());
		}
		if (registroActualizar.getSeSuma() != null) {
			registroAntiguo.setSeSuma(registroActualizar.getSeSuma());
		}
		if (registroActualizar.getSisHabilitado() != null) {
			registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
		}
		return registroAntiguo;
	}

	public List<DetalleNovedadRolPago> getDetalleNovedadRolPagoCollection() {
		return detalleNovedadRolPagoCollection;
	}

	public void setDetalleNovedadRolPagoCollection(List<DetalleNovedadRolPago> detalleNovedadRolPagoCollection) {
		this.detalleNovedadRolPagoCollection = detalleNovedadRolPagoCollection;
	}

}
