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
@Table(name = "asiento_contable_cabecera")
public class AsientoContableCabecera extends AbstractEntities {

	@Column(name = "fecha")
	@CsvDate(value = "yyyy-MM-dd")
	@CsvBindByName(column = "fecha")
	private Date fecha;

	@Column(name = "anio", nullable = false)
	@CsvBindByName(column = "anio")
	private Integer anio;

	@Column(name = "\"mesPeriodo\"", nullable = false, length = 3/*, unique = true*/)
	@CsvBindByName(column = "mes_periodo")
	private String mesPeriodo;

	@Column(name = "\"tipoTransaccion\"", nullable = false, length = 2)
	@CsvBindByName(column = "tipo_transaccion")
	private String tipoTransaccion;

	@Column(name = "\"tipoAsientoContable\"", nullable = false, length = 2)
	@CsvBindByName(column = "tipo_asiento_contable")
	private String tipoAsientoContable;

	@Column(name = "\"codigoReferencialAsientoContable\"", length = 255)
	@CsvBindByName(column = "codigo_referencial_asiento_contable")
	private String codigoReferencialAsientoContable;

	@Column(name = "\"beneficiario\"", length = 255, nullable = false)
	@CsvBindByName(column = "beneficiario")
	private String beneficiario;

	@Column(name = "\"totalDebito\"", columnDefinition = "Decimal(10,2)")
	@CsvBindByName(column = "total_debito")
	private Float totalDebito;

	@Column(name = "\"totalCredito\"", columnDefinition = "Decimal(10,2)")
	@CsvBindByName(column = "total_credito")
	private Float totalCredito;

	@Column(name = "\"totalSaldoActualFecha\"", columnDefinition = "Decimal(10,2)")
	@CsvBindByName(column = "total_saldo_actual_fecha")
	private Float totalSaldoActualFecha;

	@Column(name = "\"asientoCerrado\"", nullable = false, length = 2)
	@CsvBindByName(column = "asiento_cerrado")
	private String asientoCerrado;

	@Column(name = "\"serie\"", length = 255)
	@CsvBindByName(column = "serie")
	private String serie;

	@Column(name = "\"descripcion\"", nullable = false, length = 255)
	@CsvBindByName(column = "descripcion")
	private String descripcion;

	// Hijos

	@JsonIgnore
	@OneToMany(mappedBy = "idAsientoContableCabecera")
	private List<TransaccionAsientoContable> transaccionAsientoContableCollection;

	@JsonIgnore
	@OneToMany(mappedBy = "idAsientoContableCabecera")
	private List<AsientoContableDetAdicional> asientoContableDetAdicionalCollection;

	// Padres
	@ManyToOne
	@JoinColumn(name = "\"idCheque\"", referencedColumnName = "id")
	private Cheque idCheque;

	@Transient
	@CsvBindByName(column = "id_Cheque")
	private Integer idCheque1;


//	@Transient
//	@CsvBindByName(column = "id_subgrupo_contable")
//	private Integer idSubgrupoContable1;

	@OneToOne
	@JoinColumn(name = "\"idCuentaContable\"", referencedColumnName = "id", nullable = false)
	private CuentaContable idCuentaContable;

	@Transient
	@CsvBindByName(column = "id_cuenta_contable")
	private Integer idCuentaContable1;

	public AsientoContableCabecera() {
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public String getMesPeriodo() {
		return mesPeriodo;
	}

	public void setMesPeriodo(String mesPeriodo) {
		this.mesPeriodo = mesPeriodo;
	}

	public String getTipoTransaccion() {
		return tipoTransaccion;
	}

	public void setTipoTransaccion(String tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}

	public String getTipoAsientoContable() {
		return tipoAsientoContable;
	}

	public void setTipoAsientoContable(String tipoAsientoContable) {
		this.tipoAsientoContable = tipoAsientoContable;
	}

	public String getCodigoReferencialAsientoContable() {
		return codigoReferencialAsientoContable;
	}

	public void setCodigoReferencialAsientoContable(String codigoReferencialAsientoContable) {
		this.codigoReferencialAsientoContable = codigoReferencialAsientoContable;
	}

	public Float getTotalDebito() {
		return totalDebito;
	}

	public void setTotalDebito(Float totalDebito) {
		this.totalDebito = totalDebito;
	}

	public Float getTotalCredito() {
		return totalCredito;
	}

	public void setTotalCredito(Float totalCredito) {
		this.totalCredito = totalCredito;
	}

	public Float getTotalSaldoActualFecha() {
		return totalSaldoActualFecha;
	}

	public void setTotalSaldoActualFecha(Float totalSaldoActualFecha) {
		this.totalSaldoActualFecha = totalSaldoActualFecha;
	}

	public String getAsientoCerrado() {
		return asientoCerrado;
	}

	public void setAsientoCerrado(String asientoCerrado) {
		this.asientoCerrado = asientoCerrado;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<AsientoContableDetAdicional> getAsientoContableDetAdicionalCollection() {
		return asientoContableDetAdicionalCollection;
	}

	public void setAsientoContableDetAdicionalCollection(
			List<AsientoContableDetAdicional> asientoContableDetAdicionalCollection) {
		this.asientoContableDetAdicionalCollection = asientoContableDetAdicionalCollection;
	}

	public Cheque getIdCheque() {
		return idCheque;
	}

	public void setIdCheque(Cheque idCheque) {
		this.idCheque = idCheque;
	}

	public Integer getIdCheque1() {
		return idCheque1;
	}

	public void setIdCheque1(Integer idCheque1) {
		this.idCheque1 = idCheque1;
	}


//
//	public Integer getIdSubgrupoContable1() {
//		return idSubgrupoContable1;
//	}
//
//	public void setIdSubgrupoContable1(Integer idSubgrupoContable1) {
//		this.idSubgrupoContable1 = idSubgrupoContable1;
//	}

	public String getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(String beneficiario) {
		this.beneficiario = beneficiario;
	}

	public CuentaContable getIdCuentaContable() {
		return idCuentaContable;
	}

	public void setIdCuentaContable(CuentaContable idCuentaContable) {
		this.idCuentaContable = idCuentaContable;
	}

	public Integer getIdCuentaContable1() {
		return idCuentaContable1;
	}

	public void setIdCuentaContable1(Integer idCuentaContable1) {
		this.idCuentaContable1 = idCuentaContable1;
	}

	public List<TransaccionAsientoContable> getTransaccionAsientoContableCollection() {
		return transaccionAsientoContableCollection;
	}

	public void setTransaccionAsientoContableCollection(List<TransaccionAsientoContable> transaccionAsientoContableCollection) {
		this.transaccionAsientoContableCollection = transaccionAsientoContableCollection;
	}

	public AsientoContableCabecera setValoresDiferentes(AsientoContableCabecera registroAntiguo,
														AsientoContableCabecera registroActualizar) {

		if (registroActualizar.getFecha() != null) {
			registroAntiguo.setFecha(registroActualizar.getFecha());
		}
		if (registroActualizar.getTipoTransaccion() != null) {
			registroAntiguo.setTipoTransaccion(registroActualizar.getTipoTransaccion());
		}
		if (registroActualizar.getTipoAsientoContable() != null) {
			registroAntiguo.setTipoAsientoContable(registroActualizar.getTipoAsientoContable());
		}
		if (registroActualizar.getCodigoReferencialAsientoContable() != null) {
			registroAntiguo
					.setCodigoReferencialAsientoContable(registroActualizar.getCodigoReferencialAsientoContable());
		}
		if (registroActualizar.getMesPeriodo() != null) {
			registroAntiguo.setMesPeriodo(registroActualizar.getMesPeriodo());
		}
		if (registroActualizar.getAnio() != null) {
			registroAntiguo.setAnio(registroActualizar.getAnio());
		}
		if (registroActualizar.getTotalDebito() != null) {
			registroAntiguo.setTotalDebito(registroActualizar.getTotalDebito());
		}
		if (registroActualizar.getTotalCredito() != null) {
			registroAntiguo.setTotalCredito(registroActualizar.getTotalCredito());
		}
		if (registroActualizar.getTotalSaldoActualFecha() != null) {
			registroAntiguo.setTotalSaldoActualFecha(registroActualizar.getTotalSaldoActualFecha());
		}
		if (registroActualizar.getAsientoCerrado() != null) {
			registroAntiguo.setAsientoCerrado(registroActualizar.getAsientoCerrado());
		}
		if (registroActualizar.getSerie() != null) {
			registroAntiguo.setSerie(registroActualizar.getSerie());
		}
		if (registroActualizar.getDescripcion() != null) {
			registroAntiguo.setDescripcion(registroActualizar.getDescripcion());
		}
		
		if (registroActualizar.getIdCheque() != null && registroActualizar.getIdCheque().getId() != null) {
			registroAntiguo.setIdCheque(registroActualizar.getIdCheque());
		}
		if (registroActualizar.getSisHabilitado() != null) {
			registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
		}
		return registroAntiguo;
	}

}
