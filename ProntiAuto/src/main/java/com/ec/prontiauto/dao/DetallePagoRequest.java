package com.ec.prontiauto.dao;

import java.math.BigDecimal;

public class DetallePagoRequest {
    private BigDecimal valor;
    private BigDecimal abonoCapital;
    private BigDecimal cuotaAdministrativa;
    private BigDecimal oferta;
    private BigDecimal cargoAdjudicacion;
    private Integer numeroCuota;
    private String tipo;
    private Integer idItemCobroPago;

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getAbonoCapital() {
        return abonoCapital;
    }

    public void setAbonoCapital(BigDecimal abonoCapital) {
        this.abonoCapital = abonoCapital;
    }

    public BigDecimal getCuotaAdministrativa() {
        return cuotaAdministrativa;
    }

    public void setCuotaAdministrativa(BigDecimal cuotaAdministrativa) {
        this.cuotaAdministrativa = cuotaAdministrativa;
    }

    public BigDecimal getOferta() {
        return oferta;
    }

    public void setOferta(BigDecimal oferta) {
        this.oferta = oferta;
    }

    public BigDecimal getCargoAdjudicacion() {
        return cargoAdjudicacion;
    }

    public void setCargoAdjudicacion(BigDecimal cargoAdjudicacion) {
        this.cargoAdjudicacion = cargoAdjudicacion;
    }

    public Integer getNumeroCuota() {
        return numeroCuota;
    }

    public void setNumeroCuota(Integer numeroCuota) {
        this.numeroCuota = numeroCuota;
    }

    public Integer getIdItemCobroPago() {
        return idItemCobroPago;
    }

    public void setIdItemCobroPago(Integer idItemCobroPago) {
        this.idItemCobroPago = idItemCobroPago;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
