package com.ec.prontiauto.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class PagosRequestDao {

    private String tipoDocumento;
    private String observaciones;
    private String numeroDocumento;
    
    private BigDecimal valor;
    private Date fechaDeposito;
    private List<DetallePagoRequest> detallePago;
    private Integer idCuentaBancariaEmpresa;
    private String bancoCuentaBancaria;

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Date getFechaDeposito() {
        return fechaDeposito;
    }

    public void setFechaDeposito(Date fechaDeposito) {
        this.fechaDeposito = fechaDeposito;
    }

    public Integer getIdCuentaBancariaEmpresa() {
        return idCuentaBancariaEmpresa;
    }

    public void setIdCuentaBancariaEmpresa(Integer idCuentaBancariaEmpresa) {
        this.idCuentaBancariaEmpresa = idCuentaBancariaEmpresa;
    }

    public List<DetallePagoRequest> getDetallePago() {
        return detallePago;
    }

    public void setDetallePago(List<DetallePagoRequest> detallePago) {
        this.detallePago = detallePago;
    }

    public String getBancoCuentaBancaria() {
        return bancoCuentaBancaria;
    }

    public void setBancoCuentaBancaria(String bancoCuentaBancaria) {
        this.bancoCuentaBancaria = bancoCuentaBancaria;
    }

}
