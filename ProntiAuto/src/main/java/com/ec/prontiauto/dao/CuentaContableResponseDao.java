package com.ec.prontiauto.dao;

import java.util.List;

import com.ec.prontiauto.abstracts.AbstractResponseDao;
import com.ec.prontiauto.entidad.CuentaContable;

public class CuentaContableResponseDao extends AbstractResponseDao {

	private String nombre;
	private Integer identificador;
	private Integer nivel;
	private Integer idNivel1;
	private Integer idNivel2;
	private Integer idNivel3;
	private Integer idNivel4;
	private Integer idNivel5;
	private String tipoCuenta;
	private String movimiento;
	private Float anteriorDebito;
	private Float anteriorCredito;
	private Float anteriorSaldo;
	private Float eneroDebito;
	private Float eneroCredito;
	private Float eneroSaldo;
	private Float febreroDebito;
	private Float febreroCredito;
	private Float febreroSaldo;
	private Float marzoDebito;
	private Float marzoCredito;
	private Float marzoSaldo;
	private Float abrilDebito;
	private Float abrilCredito;
	private Float abrilSaldo;
	private Float mayoDebito;
	private Float mayoCredito;
	private Float mayoSaldo;
	private Float junioDebito;
	private Float junioCredito;
	private Float junioSaldo;
	private Float julioDebito;
	private Float julioCredito;
	private Float julioSaldo;
	private Float agostoDebito;
	private Float agostoCredito;
	private Float agostoSaldo;
	private Float septiembreDebito;
	private Float septiembreCredito;
	private Float septiembreSaldo;
	private Float octubreDebito;
	private Float octubreCredito;
	private Float octubreSaldo;
	private Float noviembreDebito;
	private Float noviembreCredito;
	private Float noviembreSaldo;
	private Float diciembreDebito;
	private Float diciembreCredito;
	private Float diciembreSaldo;
	private Float actualDebito;
	private Float actualCredito;
	private Float actualSaldo;
	
	private PeriodoContableResponseDao idPeriodoContable;
	private List<TransaccionAsientoContableResponseDao> transaccionAsientoContableCollection;
//	Recursivo Hijo
	private CuentaContableResponseDao idCuentaContable;
//	Recursivo padre
	private List<CuentaContableResponseDao> CuentaContableCollection;

	private List<ItemCobroPagoResponseDao> ItemCobroPagoCollection;

	public CuentaContableResponseDao() {
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

	public PeriodoContableResponseDao getIdPeriodoContable() {
		return idPeriodoContable;
	}

	public void setIdPeriodoContable(PeriodoContableResponseDao idPeriodoContable) {
		this.idPeriodoContable = idPeriodoContable;
	}

	public List<TransaccionAsientoContableResponseDao> getTransaccionAsientoContableCollection() {
		return transaccionAsientoContableCollection;
	}

	public void setTransaccionAsientoContableCollection(
			List<TransaccionAsientoContableResponseDao> transaccionAsientoContableCollection) {
		this.transaccionAsientoContableCollection = transaccionAsientoContableCollection;
	}

	public CuentaContableResponseDao getIdCuentaContable() {
		return idCuentaContable;
	}

	public void setIdCuentaContable(CuentaContableResponseDao idCuentaContable) {
		this.idCuentaContable = idCuentaContable;
	}

	public List<CuentaContableResponseDao> getCuentaContableCollection() {
		return CuentaContableCollection;
	}

	public void setCuentaContableCollection(List<CuentaContableResponseDao> cuentaContableCollection) {
		CuentaContableCollection = cuentaContableCollection;
	}


	public List<ItemCobroPagoResponseDao> getItemCobroPagoCollection() {
		return this.ItemCobroPagoCollection;
	}

	public void setItemCobroPagoCollection(List<ItemCobroPagoResponseDao> ItemCobroPagoCollection) {
		this.ItemCobroPagoCollection = ItemCobroPagoCollection;
	}

	public CuentaContableResponseDao(CuentaContable cuentaContable){
		this.nombre = cuentaContable.getNombre();
		this.identificador = cuentaContable.getIdentificador();
		this.nivel = cuentaContable.getNivel();
		this.idNivel1 = cuentaContable.getIdNivel1();
		this.idNivel2 = cuentaContable.getIdNivel2();
		this.idNivel3 = cuentaContable.getIdNivel3();
		this.idNivel4 = cuentaContable.getIdNivel4();
		this.idNivel5 = cuentaContable.getIdNivel5();
		this.tipoCuenta = cuentaContable.getTipoCuenta();
		this.movimiento = cuentaContable.getMovimiento();
		this.anteriorDebito = cuentaContable.getAnteriorDebito();
		this.anteriorCredito = cuentaContable.getAnteriorCredito();
		this.anteriorSaldo = cuentaContable.getAnteriorSaldo();
		this.eneroDebito = cuentaContable.getEneroDebito();
		this.eneroCredito = cuentaContable.getEneroCredito();
		this.eneroSaldo = cuentaContable.getEneroSaldo();
		this.febreroDebito = cuentaContable.getFebreroDebito();
		this.febreroCredito = cuentaContable.getFebreroCredito();
		this.febreroSaldo = cuentaContable.getFebreroSaldo();
		this.marzoDebito = cuentaContable.getMarzoDebito();
		this.marzoCredito  = cuentaContable.getMarzoCredito();
		this.marzoSaldo = cuentaContable.getMarzoSaldo();
		this.abrilDebito = cuentaContable.getAbrilDebito();
		this.abrilCredito = cuentaContable.getAbrilCredito();
		this.abrilSaldo = cuentaContable.getAbrilSaldo();
		this.mayoDebito = cuentaContable.getMayoDebito();
		this.mayoCredito = cuentaContable.getMayoCredito();
		this.mayoSaldo = cuentaContable.getMayoSaldo();
		this.junioDebito = cuentaContable.getJunioDebito();
		this.junioCredito = cuentaContable.getJunioCredito();
		this.junioSaldo = cuentaContable.getJunioSaldo();
		this.julioDebito = cuentaContable.getJulioDebito();
		this.julioCredito = cuentaContable.getJulioCredito();
		this.julioSaldo = cuentaContable.getJulioSaldo();
		this.agostoDebito = cuentaContable.getAgostoDebito();
		this.agostoCredito = cuentaContable.getAgostoCredito();
		this.agostoSaldo = cuentaContable.getAgostoSaldo();
		this.septiembreDebito = cuentaContable.getSeptiembreDebito();
		this.septiembreCredito = cuentaContable.getSeptiembreCredito();
		this.septiembreSaldo = cuentaContable.getSeptiembreSaldo();
		this.octubreDebito = cuentaContable.getOctubreDebito();
		this.octubreCredito = cuentaContable.getOctubreCredito();
		this.octubreSaldo = cuentaContable.getOctubreSaldo();
		this.noviembreDebito = cuentaContable.getNoviembreDebito();
		this.noviembreCredito = cuentaContable.getNoviembreCredito();
		this.noviembreSaldo = cuentaContable.getNoviembreSaldo();
		this.diciembreDebito = cuentaContable.getDiciembreDebito();
		this.diciembreCredito = cuentaContable.getDiciembreCredito();
		this.diciembreSaldo = cuentaContable.getDiciembreSaldo();
		this.actualDebito = cuentaContable.getActualDebito();
		this.actualCredito = cuentaContable.getActualCredito();
		this.actualSaldo = cuentaContable.getActualSaldo();
	}
	
}
