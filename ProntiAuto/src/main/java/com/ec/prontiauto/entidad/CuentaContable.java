package com.ec.prontiauto.entidad;

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

@Entity
@Table(name = "cuenta_contable")
public class CuentaContable extends AbstractEntities {

	@Column(name = "nombre", length = 255, nullable = false/*, unique = true*/)
	@CsvBindByName(column = "nombre")
	private String nombre;

	@Column(name = "identificador", nullable = false, unique = true)
	@CsvBindByName(column = "identificador")
	private Integer identificador;

	@Column(name = "\"codigoCuentaContable\"", nullable = false, unique = true)
	@CsvBindByName(column = "codigo_cuenta_contable")
	private String codigoCuentaContable;

	@Column(name = "nivel", nullable = false)
	@CsvBindByName(column = "nivel")
	private Integer nivel;

	@Column(name = "\"idNivel1\"")
	@CsvBindByName(column = "id_nivel_1")
	private Integer idNivel1;

	@Column(name = "\"idNivel2\"")
	@CsvBindByName(column = "id_nivel_2")
	private Integer idNivel2;

	@Column(name = "\"idNivel3\"")
	@CsvBindByName(column = "id_nivel_3")
	private Integer idNivel3;

	@Column(name = "\"idNivel4\"")
	@CsvBindByName(column = "id_nivel_4")
	private Integer idNivel4;

	@Column(name = "\"idNivel5\"")
	@CsvBindByName(column = "id_nivel_5")
	private Integer idNivel5;

	@Column(name = "\"tipoCuenta\"", length = 2, nullable = false)
	@CsvBindByName(column = "tipo_cuenta")
	private String tipoCuenta;

	@Column(name = "\"movimiento\"", length = 2, nullable = false)
	@CsvBindByName(column = "movimiento")
	private String movimiento;

	@Column(name = "\"anteriorDebito\"")
	@CsvBindByName(column = "anterior_debito")
	private Float anteriorDebito;

	@Column(name = "\"anteriorCredito\"")
	@CsvBindByName(column = "anterior_credito")
	private Float anteriorCredito;

	@Column(name = "\"anteriorSaldo\"")
	@CsvBindByName(column = "anterior_saldo")
	private Float anteriorSaldo;

	@Column(name = "\"eneroDebito\"")
	@CsvBindByName(column = "enero_debito")
	private Float eneroDebito;

	@Column(name = "\"eneroCredito\"")
	@CsvBindByName(column = "enero_credito")
	private Float eneroCredito;

	@Column(name = "\"eneroSaldo\"")
	@CsvBindByName(column = "enero_saldo")
	private Float eneroSaldo;

	@Column(name = "\"febreroDebito\"")
	@CsvBindByName(column = "febrero_debito")
	private Float febreroDebito;

	@Column(name = "\"febreroCredito\"")
	@CsvBindByName(column = "febrero_credito")
	private Float febreroCredito;

	@Column(name = "\"febreroSaldo\"")
	@CsvBindByName(column = "febrero_saldo")
	private Float febreroSaldo;

	@Column(name = "\"marzoDebito\"")
	@CsvBindByName(column = "marzo_debito")
	private Float marzoDebito;

	@Column(name = "\"marzoCredito\"")
	@CsvBindByName(column = "marzo_credito")
	private Float marzoCredito;

	@Column(name = "\"marzoSaldo\"")
	@CsvBindByName(column = "marzo_saldo")
	private Float marzoSaldo;

	@Column(name = "\"abrilDebito\"")
	@CsvBindByName(column = "abril_debito")
	private Float abrilDebito;

	@Column(name = "\"abrilCredito\"")
	@CsvBindByName(column = "abril_credito")
	private Float abrilCredito;

	@Column(name = "\"abrilSaldo\"")
	@CsvBindByName(column = "abril_saldo")
	private Float abrilSaldo;

	@Column(name = "\"mayoDebito\"")
	@CsvBindByName(column = "mayo_debito")
	private Float mayoDebito;

	@Column(name = "\"mayoCredito\"")
	@CsvBindByName(column = "mayo_credito")
	private Float mayoCredito;

	@Column(name = "\"mayoSaldo\"")
	@CsvBindByName(column = "mayo_saldo")
	private Float mayoSaldo;

	@Column(name = "\"junioDebito\"")
	@CsvBindByName(column = "junio_debito")
	private Float junioDebito;

	@Column(name = "\"junioCredito\"")
	@CsvBindByName(column = "junio_credito")
	private Float junioCredito;

	@Column(name = "\"junioSaldo\"")
	@CsvBindByName(column = "junio_saldo")
	private Float junioSaldo;

	@Column(name = "\"julioDebito\"")
	@CsvBindByName(column = "julio_debito")
	private Float julioDebito;

	@Column(name = "\"julioCredito\"")
	@CsvBindByName(column = "julio_credito")
	private Float julioCredito;

	@Column(name = "\"julioSaldo\"")
	@CsvBindByName(column = "julio_saldo")
	private Float julioSaldo;

	@Column(name = "\"agostoDebito\"")
	@CsvBindByName(column = "agosto_debito")
	private Float agostoDebito;

	@Column(name = "\"agostoCredito\"")
	@CsvBindByName(column = "agosto_credito")
	private Float agostoCredito;

	@Column(name = "\"agostoSaldo\"")
	@CsvBindByName(column = "agosto_saldo")
	private Float agostoSaldo;

	@Column(name = "\"septiembreDebito\"")
	@CsvBindByName(column = "septiembre_debito")
	private Float septiembreDebito;

	@Column(name = "\"septiembreCredito\"")
	@CsvBindByName(column = "septiembre_credito")
	private Float septiembreCredito;

	@Column(name = "\"septiembreSaldo\"")
	@CsvBindByName(column = "septiembre_saldo")
	private Float septiembreSaldo;

	@Column(name = "\"octubreDebito\"")
	@CsvBindByName(column = "octubre_debito")
	private Float octubreDebito;

	@Column(name = "\"octubreCredito\"")
	@CsvBindByName(column = "octubre_credito")
	private Float octubreCredito;

	@Column(name = "\"octubreSaldo\"")
	@CsvBindByName(column = "octubre_saldo")
	private Float octubreSaldo;

	@Column(name = "\"noviembreDebito\"")
	@CsvBindByName(column = "noviembre_debito")
	private Float noviembreDebito;

	@Column(name = "\"noviembreCredito\"")
	@CsvBindByName(column = "noviembre_credito")
	private Float noviembreCredito;

	@Column(name = "\"noviembreSaldo\"")
	@CsvBindByName(column = "noviembre_saldo")
	private Float noviembreSaldo;

	@Column(name = "\"diciembreDebito\"")
	@CsvBindByName(column = "diciembre_debito")
	private Float diciembreDebito;

	@Column(name = "\"diciembreCredito\"")
	@CsvBindByName(column = "diciembre_credito")
	private Float diciembreCredito;

	@Column(name = "\"diciembreSaldo\"")
	@CsvBindByName(column = "diciembre_saldo")
	private Float diciembreSaldo;

	@Column(name = "\"actualDebito\"")
	@CsvBindByName(column = "actual_debito")
	private Float actualDebito;

	@Column(name = "\"actualCredito\"")
	@CsvBindByName(column = "actual_credito")
	private Float actualCredito;

	@Column(name = "\"actualSaldo\"")
	@CsvBindByName(column = "actual_saldo")
	private Float actualSaldo;

	@ManyToOne
	@JoinColumn(name = "\"idPeriodoContable\"", referencedColumnName = "id", nullable = false)
	private PeriodoContable idPeriodoContable;

	@Transient
	@CsvBindByName(column = "id_periodo_contable")
	private Integer idPeriodoContable1;

	// Relacion Recursiva
	@JsonIgnore
	@OneToMany(mappedBy = "idCuentaContable")
	private List<CuentaContable> cuentaContableCollection;

	@ManyToOne
	@JoinColumn(name = "\"idCuentaContable\"", referencedColumnName = "id", nullable = true)
	private CuentaContable idCuentaContable;

	@JsonIgnore
	@OneToMany(mappedBy = "idCuentaContable")
	private List<TransaccionAsientoContable> transaccionAsientoContableCollection;

	@JsonIgnore
	@OneToMany(mappedBy = "idCuentaContable")
	private List<ItemCobroPago> itemCobroPagoCollection;

	@OneToOne(mappedBy = "idCuentaContable")
	private AsientoContableCabecera idAsientoContableCabecera;

	public CuentaContable() {
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getIdentificador() {
		return identificador;
	}

	public void setIdentificador(Integer identificador) {
		this.identificador = identificador;
	}

	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	public Integer getIdNivel1() {
		return idNivel1;
	}

	public void setIdNivel1(Integer idNivel1) {
		this.idNivel1 = idNivel1;
	}

	public Integer getIdNivel2() {
		return idNivel2;
	}

	public void setIdNivel2(Integer idNivel2) {
		this.idNivel2 = idNivel2;
	}

	public Integer getIdNivel3() {
		return idNivel3;
	}

	public void setIdNivel3(Integer idNivel3) {
		this.idNivel3 = idNivel3;
	}

	public Integer getIdNivel4() {
		return idNivel4;
	}

	public void setIdNivel4(Integer idNivel4) {
		this.idNivel4 = idNivel4;
	}

	public Integer getIdNivel5() {
		return idNivel5;
	}

	public void setIdNivel5(Integer idNivel5) {
		this.idNivel5 = idNivel5;
	}

	public String getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	public String getMovimiento() {
		return movimiento;
	}

	public void setMovimiento(String movimiento) {
		this.movimiento = movimiento;
	}

	public Float getAnteriorDebito() {
		return anteriorDebito;
	}

	public void setAnteriorDebito(Float anteriorDebito) {
		this.anteriorDebito = anteriorDebito;
	}

	public Float getAnteriorCredito() {
		return anteriorCredito;
	}

	public void setAnteriorCredito(Float anteriorCredito) {
		this.anteriorCredito = anteriorCredito;
	}

	public Float getAnteriorSaldo() {
		return anteriorSaldo;
	}

	public void setAnteriorSaldo(Float anteriorSaldo) {
		this.anteriorSaldo = anteriorSaldo;
	}

	public Float getEneroDebito() {
		return eneroDebito;
	}

	public void setEneroDebito(Float eneroDebito) {
		this.eneroDebito = eneroDebito;
	}

	public Float getEneroCredito() {
		return eneroCredito;
	}

	public void setEneroCredito(Float eneroCredito) {
		this.eneroCredito = eneroCredito;
	}

	public Float getEneroSaldo() {
		return eneroSaldo;
	}

	public void setEneroSaldo(Float eneroSaldo) {
		this.eneroSaldo = eneroSaldo;
	}

	public Float getFebreroDebito() {
		return febreroDebito;
	}

	public void setFebreroDebito(Float febreroDebito) {
		this.febreroDebito = febreroDebito;
	}

	public Float getFebreroCredito() {
		return febreroCredito;
	}

	public void setFebreroCredito(Float febreroCredito) {
		this.febreroCredito = febreroCredito;
	}

	public Float getFebreroSaldo() {
		return febreroSaldo;
	}

	public void setFebreroSaldo(Float febreroSaldo) {
		this.febreroSaldo = febreroSaldo;
	}

	public Float getMarzoDebito() {
		return marzoDebito;
	}

	public void setMarzoDebito(Float marzoDebito) {
		this.marzoDebito = marzoDebito;
	}

	public Float getMarzoCredito() {
		return marzoCredito;
	}

	public void setMarzoCredito(Float marzoCredito) {
		this.marzoCredito = marzoCredito;
	}

	public Float getMarzoSaldo() {
		return marzoSaldo;
	}

	public void setMarzoSaldo(Float marzoSaldo) {
		this.marzoSaldo = marzoSaldo;
	}

	public Float getAbrilDebito() {
		return abrilDebito;
	}

	public void setAbrilDebito(Float abrilDebito) {
		this.abrilDebito = abrilDebito;
	}

	public Float getAbrilCredito() {
		return abrilCredito;
	}

	public void setAbrilCredito(Float abrilCredito) {
		this.abrilCredito = abrilCredito;
	}

	public Float getAbrilSaldo() {
		return abrilSaldo;
	}

	public void setAbrilSaldo(Float abrilSaldo) {
		this.abrilSaldo = abrilSaldo;
	}

	public Float getMayoDebito() {
		return mayoDebito;
	}

	public void setMayoDebito(Float mayoDebito) {
		this.mayoDebito = mayoDebito;
	}

	public Float getMayoCredito() {
		return mayoCredito;
	}

	public void setMayoCredito(Float mayoCredito) {
		this.mayoCredito = mayoCredito;
	}

	public Float getMayoSaldo() {
		return mayoSaldo;
	}

	public void setMayoSaldo(Float mayoSaldo) {
		this.mayoSaldo = mayoSaldo;
	}

	public Float getJunioDebito() {
		return junioDebito;
	}

	public void setJunioDebito(Float junioDebito) {
		this.junioDebito = junioDebito;
	}

	public Float getJunioCredito() {
		return junioCredito;
	}

	public void setJunioCredito(Float junioCredito) {
		this.junioCredito = junioCredito;
	}

	public Float getJunioSaldo() {
		return junioSaldo;
	}

	public void setJunioSaldo(Float junioSaldo) {
		this.junioSaldo = junioSaldo;
	}

	public Float getJulioDebito() {
		return julioDebito;
	}

	public void setJulioDebito(Float julioDebito) {
		this.julioDebito = julioDebito;
	}

	public Float getJulioCredito() {
		return julioCredito;
	}

	public void setJulioCredito(Float julioCredito) {
		this.julioCredito = julioCredito;
	}

	public Float getJulioSaldo() {
		return julioSaldo;
	}

	public void setJulioSaldo(Float julioSaldo) {
		this.julioSaldo = julioSaldo;
	}

	public Float getAgostoDebito() {
		return agostoDebito;
	}

	public void setAgostoDebito(Float agostoDebito) {
		this.agostoDebito = agostoDebito;
	}

	public Float getAgostoCredito() {
		return agostoCredito;
	}

	public void setAgostoCredito(Float agostoCredito) {
		this.agostoCredito = agostoCredito;
	}

	public Float getAgostoSaldo() {
		return agostoSaldo;
	}

	public void setAgostoSaldo(Float agostoSaldo) {
		this.agostoSaldo = agostoSaldo;
	}

	public Float getSeptiembreDebito() {
		return septiembreDebito;
	}

	public void setSeptiembreDebito(Float septiembreDebito) {
		this.septiembreDebito = septiembreDebito;
	}

	public Float getSeptiembreCredito() {
		return septiembreCredito;
	}

	public void setSeptiembreCredito(Float septiembreCredito) {
		this.septiembreCredito = septiembreCredito;
	}

	public Float getSeptiembreSaldo() {
		return septiembreSaldo;
	}

	public void setSeptiembreSaldo(Float septiembreSaldo) {
		this.septiembreSaldo = septiembreSaldo;
	}

	public Float getOctubreDebito() {
		return octubreDebito;
	}

	public void setOctubreDebito(Float octubreDebito) {
		this.octubreDebito = octubreDebito;
	}

	public Float getOctubreCredito() {
		return octubreCredito;
	}

	public void setOctubreCredito(Float octubreCredito) {
		this.octubreCredito = octubreCredito;
	}

	public Float getOctubreSaldo() {
		return octubreSaldo;
	}

	public void setOctubreSaldo(Float octubreSaldo) {
		this.octubreSaldo = octubreSaldo;
	}

	public Float getNoviembreDebito() {
		return noviembreDebito;
	}

	public void setNoviembreDebito(Float noviembreDebito) {
		this.noviembreDebito = noviembreDebito;
	}

	public Float getNoviembreCredito() {
		return noviembreCredito;
	}

	public void setNoviembreCredito(Float noviembreCredito) {
		this.noviembreCredito = noviembreCredito;
	}

	public Float getNoviembreSaldo() {
		return noviembreSaldo;
	}

	public void setNoviembreSaldo(Float noviembreSaldo) {
		this.noviembreSaldo = noviembreSaldo;
	}

	public Float getDiciembreDebito() {
		return diciembreDebito;
	}

	public void setDiciembreDebito(Float diciembreDebito) {
		this.diciembreDebito = diciembreDebito;
	}

	public Float getDiciembreCredito() {
		return diciembreCredito;
	}

	public void setDiciembreCredito(Float diciembreCredito) {
		this.diciembreCredito = diciembreCredito;
	}

	public Float getDiciembreSaldo() {
		return diciembreSaldo;
	}

	public void setDiciembreSaldo(Float diciembreSaldo) {
		this.diciembreSaldo = diciembreSaldo;
	}

	public Float getActualDebito() {
		return actualDebito;
	}

	public void setActualDebito(Float actualDebito) {
		this.actualDebito = actualDebito;
	}

	public Float getActualCredito() {
		return actualCredito;
	}

	public void setActualCredito(Float actualCredito) {
		this.actualCredito = actualCredito;
	}

	public Float getActualSaldo() {
		return actualSaldo;
	}

	public void setActualSaldo(Float actualSaldo) {
		this.actualSaldo = actualSaldo;
	}

	public PeriodoContable getIdPeriodoContable() {
		return idPeriodoContable;
	}

	public void setIdPeriodoContable(PeriodoContable idPeriodoContable) {
		this.idPeriodoContable = idPeriodoContable;
	}

	public Integer getIdPeriodoContable1() {
		return idPeriodoContable1;
	}

	public void setIdPeriodoContable1(Integer idPeriodoContable1) {
		this.idPeriodoContable1 = idPeriodoContable1;
	}

	public List<CuentaContable> getCuentaContableCollection() {
		return cuentaContableCollection;
	}

	public void setCuentaContableCollection(List<CuentaContable> cuentaContableCollection) {
		this.cuentaContableCollection = cuentaContableCollection;
	}

	public CuentaContable getIdCuentaContable() {
		return idCuentaContable;
	}

	public void setIdCuentaContable(CuentaContable idCuentaContable) {
		this.idCuentaContable = idCuentaContable;
	}

	public List<ItemCobroPago> getItemCobroPagoCollection() {
		return this.itemCobroPagoCollection;
	}

	public void setItemCobroPagoCollection(List<ItemCobroPago> itemCobroPagoCollection) {
		this.itemCobroPagoCollection = itemCobroPagoCollection;
	}

	public List<TransaccionAsientoContable> getTransaccionAsientoContableCollection() {
		return transaccionAsientoContableCollection;
	}

	public void setTransaccionAsientoContableCollection(List<TransaccionAsientoContable> transaccionAsientoContableCollection) {
		this.transaccionAsientoContableCollection = transaccionAsientoContableCollection;
	}

	public CuentaContable setValoresDiferentes(CuentaContable registroAntiguo, CuentaContable registroActualizar) {

		if (registroActualizar.getNombre() != null) {
			registroAntiguo.setNombre(registroActualizar.getNombre());
		}
		if (registroActualizar.getIdentificador() != null) {
			registroAntiguo.setIdentificador(registroActualizar.getIdentificador());
		}
		if (registroActualizar.getNivel() != null) {
			registroAntiguo.setNivel(registroActualizar.getNivel());
		}
		if (registroActualizar.getIdNivel1() != null) {
			registroAntiguo.setIdNivel1(registroActualizar.getIdNivel1());
		}
		if (registroActualizar.getIdNivel2() != null) {
			registroAntiguo.setIdNivel2(registroActualizar.getIdNivel2());
		}
		if (registroActualizar.getIdNivel3() != null) {
			registroAntiguo.setIdNivel3(registroActualizar.getIdNivel3());
		}
		if (registroActualizar.getIdNivel4() != null) {
			registroAntiguo.setIdNivel4(registroActualizar.getIdNivel4());
		}
		if (registroActualizar.getIdNivel5() != null) {
			registroAntiguo.setIdNivel5(registroActualizar.getIdNivel5());
		}
		if (registroActualizar.getTipoCuenta() != null) {
			registroAntiguo.setTipoCuenta(registroActualizar.getTipoCuenta());
		}
		if (registroActualizar.getMovimiento() != null) {
			registroAntiguo.setMovimiento(registroActualizar.getMovimiento());
		}
		if (registroActualizar.getAnteriorDebito() != null) {
			registroAntiguo.setAnteriorDebito(registroActualizar.getAnteriorDebito());
		}
		if (registroActualizar.getAnteriorCredito() != null) {
			registroAntiguo.setAnteriorCredito(registroActualizar.getAnteriorCredito());
		}
		if (registroActualizar.getAnteriorSaldo() != null) {
			registroAntiguo.setAnteriorSaldo(registroActualizar.getAnteriorSaldo());
		}
		if (registroActualizar.getEneroDebito() != null) {
			registroAntiguo.setEneroDebito(registroActualizar.getEneroDebito());
		}
		if (registroActualizar.getEneroCredito() != null) {
			registroAntiguo.setEneroCredito(registroActualizar.getEneroCredito());
		}
		if (registroActualizar.getEneroSaldo() != null) {
			registroAntiguo.setEneroSaldo(registroActualizar.getEneroSaldo());
		}
		if (registroActualizar.getFebreroDebito() != null) {
			registroAntiguo.setFebreroDebito(registroActualizar.getFebreroDebito());
		}
		if (registroActualizar.getFebreroCredito() != null) {
			registroAntiguo.setFebreroCredito(registroActualizar.getFebreroCredito());
		}
		if (registroActualizar.getFebreroSaldo() != null) {
			registroAntiguo.setFebreroSaldo(registroActualizar.getFebreroSaldo());
		}
		if (registroActualizar.getMarzoDebito() != null) {
			registroAntiguo.setMarzoDebito(registroActualizar.getMarzoDebito());
		}
		if (registroActualizar.getMarzoCredito() != null) {
			registroAntiguo.setMarzoCredito(registroActualizar.getMarzoCredito());
		}
		if (registroActualizar.getMarzoSaldo() != null) {
			registroAntiguo.setMarzoSaldo(registroActualizar.getMarzoSaldo());
		}
		if (registroActualizar.getAbrilDebito() != null) {
			registroAntiguo.setAbrilDebito(registroActualizar.getAbrilDebito());
		}
		if (registroActualizar.getAbrilCredito() != null) {
			registroAntiguo.setAbrilCredito(registroActualizar.getAbrilCredito());
		}
		if (registroActualizar.getAbrilSaldo() != null) {
			registroAntiguo.setAbrilSaldo(registroActualizar.getAbrilSaldo());
		}
		if (registroActualizar.getMayoDebito() != null) {
			registroAntiguo.setMayoDebito(registroActualizar.getMayoDebito());
		}
		if (registroActualizar.getMayoCredito() != null) {
			registroAntiguo.setMayoCredito(registroActualizar.getMayoCredito());
		}
		if (registroActualizar.getMayoSaldo() != null) {
			registroAntiguo.setMayoSaldo(registroActualizar.getMayoSaldo());
		}
		if (registroActualizar.getJunioDebito() != null) {
			registroAntiguo.setJunioDebito(registroActualizar.getJunioDebito());
		}
		if (registroActualizar.getJunioCredito() != null) {
			registroAntiguo.setJunioCredito(registroActualizar.getJunioCredito());
		}
		if (registroActualizar.getJunioSaldo() != null) {
			registroAntiguo.setJunioSaldo(registroActualizar.getJunioSaldo());
		}
		if (registroActualizar.getJulioDebito() != null) {
			registroAntiguo.setJulioDebito(registroActualizar.getJulioDebito());
		}
		if (registroActualizar.getJulioCredito() != null) {
			registroAntiguo.setJulioCredito(registroActualizar.getJulioCredito());
		}
		if (registroActualizar.getJulioSaldo() != null) {
			registroAntiguo.setJulioSaldo(registroActualizar.getJulioSaldo());
		}
		if (registroActualizar.getAgostoDebito() != null) {
			registroAntiguo.setAgostoDebito(registroActualizar.getAgostoDebito());
		}
		if (registroActualizar.getAgostoCredito() != null) {
			registroAntiguo.setAgostoCredito(registroActualizar.getAgostoCredito());
		}
		if (registroActualizar.getAgostoSaldo() != null) {
			registroAntiguo.setAgostoSaldo(registroActualizar.getAgostoSaldo());
		}
		if (registroActualizar.getSeptiembreDebito() != null) {
			registroAntiguo.setSeptiembreDebito(registroActualizar.getSeptiembreDebito());
		}
		if (registroActualizar.getSeptiembreCredito() != null) {
			registroAntiguo.setSeptiembreCredito(registroActualizar.getSeptiembreCredito());
		}
		if (registroActualizar.getSeptiembreSaldo() != null) {
			registroAntiguo.setSeptiembreSaldo(registroActualizar.getSeptiembreSaldo());
		}
		if (registroActualizar.getOctubreDebito() != null) {
			registroAntiguo.setOctubreDebito(registroActualizar.getOctubreDebito());
		}
		if (registroActualizar.getOctubreCredito() != null) {
			registroAntiguo.setOctubreCredito(registroActualizar.getOctubreCredito());
		}
		if (registroActualizar.getOctubreSaldo() != null) {
			registroAntiguo.setOctubreSaldo(registroActualizar.getOctubreSaldo());
		}
		if (registroActualizar.getNoviembreDebito() != null) {
			registroAntiguo.setNoviembreDebito(registroActualizar.getNoviembreDebito());
		}
		if (registroActualizar.getNoviembreCredito() != null) {
			registroAntiguo.setNoviembreCredito(registroActualizar.getNoviembreCredito());
		}
		if (registroActualizar.getNoviembreSaldo() != null) {
			registroAntiguo.setNoviembreSaldo(registroActualizar.getNoviembreSaldo());
		}
		if (registroActualizar.getDiciembreDebito() != null) {
			registroAntiguo.setDiciembreDebito(registroActualizar.getDiciembreDebito());
		}
		if (registroActualizar.getDiciembreCredito() != null) {
			registroAntiguo.setDiciembreCredito(registroActualizar.getDiciembreCredito());
		}
		if (registroActualizar.getDiciembreSaldo() != null) {
			registroAntiguo.setDiciembreSaldo(registroActualizar.getDiciembreSaldo());
		}
		if (registroActualizar.getActualDebito() != null) {
			registroAntiguo.setActualDebito(registroActualizar.getActualDebito());
		}
		if (registroActualizar.getActualCredito() != null) {
			registroAntiguo.setActualCredito(registroActualizar.getActualCredito());
		}
		if (registroActualizar.getActualSaldo() != null) {
			registroAntiguo.setActualSaldo(registroActualizar.getActualSaldo());
		}

		if (registroActualizar.getSisHabilitado() != null) {
			registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
		}
		return registroAntiguo;
	}

	public AsientoContableCabecera getIdAsientoContableCabecera() {
		return idAsientoContableCabecera;
	}

	public void setIdAsientoContableCabecera(AsientoContableCabecera idAsientoContableCabecera) {
		this.idAsientoContableCabecera = idAsientoContableCabecera;
	}

}
