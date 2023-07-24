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
@Table(name = "chequera")
public class Chequera extends AbstractEntities {

	@Column(name = "\"fechaEmision\"", nullable = false)
	@CsvDate(value = "yyyy-MM-dd")
	@CsvBindByName(column = "fecha_emision")
	private Date fechaEmision;

	@Column(name = "\"serieDesde\"", nullable = true)
	@CsvBindByName(column = "serie_desde")
	private Integer serieDesde;

	@Column(name = "\"serieHasta\"", nullable = true)
	@CsvBindByName(column = "serie_hasta")
	private Integer serieHasta;

	@ManyToOne
	@JoinColumn(name = "\"idCuentaBancariaEmpresa\"", referencedColumnName = "id", nullable = true)
	private CuentaBancariaEmpresa idCuentaBancariaEmpresa;

	@Transient
	@CsvBindByName(column = "id_Cuenta_bancaria_empresa")
	private Integer idCuentaBancariaEmpresa1;

	@JsonIgnore
	@OneToMany(mappedBy = "idChequera")
	private List<Cheque> chequeCollection;

	public Chequera() {
	}

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public Integer getSerieDesde() {
		return serieDesde;
	}

	public void setSerieDesde(Integer serieDesde) {
		this.serieDesde = serieDesde;
	}

	public Integer getSerieHasta() {
		return serieHasta;
	}

	public void setSerieHasta(Integer serieHasta) {
		this.serieHasta = serieHasta;
	}

	public CuentaBancariaEmpresa getIdCuentaBancariaEmpresa() {
		return idCuentaBancariaEmpresa;
	}

	public void setIdCuentaBancariaEmpresa(CuentaBancariaEmpresa idCuentaBancariaEmpresa) {
		this.idCuentaBancariaEmpresa = idCuentaBancariaEmpresa;
	}

	public Integer getIdCuentaBancariaEmpresa1() {
		return idCuentaBancariaEmpresa1;
	}

	public void setIdCuentaBancariaEmpresa1(Integer idCuentaBancariaEmpresa1) {
		this.idCuentaBancariaEmpresa1 = idCuentaBancariaEmpresa1;
	}

	public List<Cheque> getChequeCollection() {
		return chequeCollection;
	}

	public void setChequeCollection(List<Cheque> chequeCollection) {
		this.chequeCollection = chequeCollection;
	}

	public Chequera setValoresDiferentes(Chequera registroAntiguo, Chequera registroActualizar) {

		if (registroActualizar.getFechaEmision() != null) {
			registroAntiguo.setFechaEmision(registroActualizar.getFechaEmision());
		}
		if (registroActualizar.getSerieDesde() != null) {
			registroAntiguo.setSerieDesde(registroActualizar.getSerieDesde());
		}
		if (registroActualizar.getSerieHasta() != null) {
			registroAntiguo.setSerieHasta(registroActualizar.getSerieHasta());
		}
		if (registroActualizar.getIdCuentaBancariaEmpresa() != null
				&& registroActualizar.getIdCuentaBancariaEmpresa().getId() != null) {
			registroAntiguo.setIdCuentaBancariaEmpresa(registroActualizar.getIdCuentaBancariaEmpresa());
		}
		if (registroActualizar.getSisHabilitado() != null) {
			registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
		}
		return registroAntiguo;
	}

}
