package com.ec.prontiauto.entidad;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "plan")
public class Plan extends AbstractEntities {

	@Column(name = "\"cuotaMes12\"")
	@CsvBindByName(column = "cuota_mes12")
	private BigDecimal cuotaMes12;

	@Column(name = "\"cuotaMes24\"")
	@CsvBindByName(column = "cuota_mes24")
	private BigDecimal cuotaMes24;

	@Column(name = "\"cuotaMes36\"")
	@CsvBindByName(column = "cuota_mes36")
	private BigDecimal cuotaMes36;

	@Column(name = "\"cuotaMes48\"")
	@CsvBindByName(column = "cuota_mes48")
	private BigDecimal cuotaMes48;

	@Column(name = "\"cuotaMes60\"")
	@CsvBindByName(column = "cuota_mes60")
	private BigDecimal cuotaMes60;

	@Column(name = "\"cuotaMes72\"")
	@CsvBindByName(column = "cuota_mes72")
	private BigDecimal cuotaMes72;

	@Column(name = "\"cuotaMes84\"")
	@CsvBindByName(column = "cuota_mes84")
	private BigDecimal cuotaMes84;

	@Column(name = "modelo", nullable = false, length = 255)
	@CsvBindByName(column = "modelo")
	private String modelo;

	@Column(name = "precio", nullable = false)
	@CsvBindByName(column = "precio")
	private BigDecimal precio;

	@Column(name = "inscripcion", nullable = false)
	@CsvBindByName(column = "inscripcion")
	private BigDecimal inscripcion;

	@JsonIgnore
	@OneToMany(mappedBy = "idPlan")
	private List<HistoricoPlanContrato> historicoPlanContratoCollection;

	public Plan() {

	}

	public BigDecimal getCuotaMes12() {
		return cuotaMes12;
	}

	public void setCuotaMes12(BigDecimal cuotaMes12) {
		this.cuotaMes12 = cuotaMes12;
	}

	public BigDecimal getCuotaMes24() {
		return cuotaMes24;
	}

	public void setCuotaMes24(BigDecimal cuotaMes24) {
		this.cuotaMes24 = cuotaMes24;
	}

	public BigDecimal getCuotaMes36() {
		return cuotaMes36;
	}

	public void setCuotaMes36(BigDecimal cuotaMes36) {
		this.cuotaMes36 = cuotaMes36;
	}

	public BigDecimal getCuotaMes48() {
		return cuotaMes48;
	}

	public void setCuotaMes48(BigDecimal cuotaMes48) {
		this.cuotaMes48 = cuotaMes48;
	}

	public BigDecimal getCuotaMes60() {
		return cuotaMes60;
	}

	public void setCuotaMes60(BigDecimal cuotaMes60) {
		this.cuotaMes60 = cuotaMes60;
	}

	public BigDecimal getCuotaMes72() {
		return cuotaMes72;
	}

	public void setCuotaMes72(BigDecimal cuotaMes72) {
		this.cuotaMes72 = cuotaMes72;
	}

	public BigDecimal getCuotaMes84() {
		return cuotaMes84;
	}

	public void setCuotaMes84(BigDecimal cuotaMes84) {
		this.cuotaMes84 = cuotaMes84;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public BigDecimal getInscripcion() {
		return inscripcion;
	}

	public void setInscripcion(BigDecimal inscripcion) {
		this.inscripcion = inscripcion;
	}

	public List<HistoricoPlanContrato> getHistoricoPlanContratoCollection() {
		return historicoPlanContratoCollection;
	}

	public void setHistoricoPlanContratoCollection(List<HistoricoPlanContrato> historicoPlanContratoCollection) {
		this.historicoPlanContratoCollection = historicoPlanContratoCollection;
	}

	public Plan setValoresDiferentes(Plan registroAntiguo, Plan registroActualizar) {

		if (registroActualizar.getCuotaMes12() != null) {
			registroAntiguo.setCuotaMes12(registroActualizar.getCuotaMes12());
		}
		if (registroActualizar.getCuotaMes24() != null) {
			registroAntiguo.setCuotaMes24(registroActualizar.getCuotaMes24());
		}
		if (registroActualizar.getCuotaMes36() != null) {
			registroAntiguo.setCuotaMes36(registroActualizar.getCuotaMes36());
		}
		if (registroActualizar.getCuotaMes48() != null) {
			registroAntiguo.setCuotaMes48(registroActualizar.getCuotaMes48());
		}
		if (registroActualizar.getCuotaMes60() != null) {
			registroAntiguo.setCuotaMes60(registroActualizar.getCuotaMes60());
		}
		if (registroActualizar.getCuotaMes72() != null) {
			registroAntiguo.setCuotaMes72(registroActualizar.getCuotaMes72());
		}
		if (registroActualizar.getCuotaMes84() != null) {
			registroAntiguo.setCuotaMes84(registroActualizar.getCuotaMes84());
		}
		if (registroActualizar.getModelo() != null) {
			registroAntiguo.setModelo(registroActualizar.getModelo());
		}
		if (registroActualizar.getPrecio() != null) {
			registroAntiguo.setPrecio(registroActualizar.getPrecio());
		}
		if (registroActualizar.getInscripcion() != null) {
			registroAntiguo.setInscripcion(registroActualizar.getInscripcion());
		}
		if (registroActualizar.getSisHabilitado() != null) {
			registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
		}
		return registroAntiguo;
	}

}
