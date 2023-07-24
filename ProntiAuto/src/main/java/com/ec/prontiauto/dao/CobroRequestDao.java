package com.ec.prontiauto.dao;

import java.math.BigDecimal;
import java.util.List;

public class CobroRequestDao {
    private BigDecimal valorACobrar;
    private Integer idContrato;
    BigDecimal capitalALiquidar;
    BigDecimal tasaAdministrativaALiquidar;
    private List<PagosRequestDao> pagos;

    private List<DetalleCrobrosRequestDao> detalleCobros;

    public BigDecimal getValorACobrar() {
        return valorACobrar;
    }

    public void setValorACobrar(BigDecimal valorACobrar) {
        this.valorACobrar = valorACobrar;
    }

    public Integer getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(Integer idContrato) {
        this.idContrato = idContrato;
    }

    public List<PagosRequestDao> getPagos() {
        return pagos;
    }

    public void setPagos(List<PagosRequestDao> pagos) {
        this.pagos = pagos;
    }

    public List<DetalleCrobrosRequestDao> getDetalleCobros() {
        return detalleCobros;
    }

    public void setDetalleCobros(List<DetalleCrobrosRequestDao> detalleCobros) {
        this.detalleCobros = detalleCobros;
    }

    public BigDecimal getCapitalALiquidar() {
        return capitalALiquidar;
    }

    public void setCapitalALiquidar(BigDecimal capitalALiquidar) {
        this.capitalALiquidar = capitalALiquidar;
    }

    public BigDecimal getTasaAdministrativaALiquidar() {
        return tasaAdministrativaALiquidar;
    }

    public void setTasaAdministrativaALiquidar(BigDecimal tasaAdministrativaALiquidar) {
        this.tasaAdministrativaALiquidar = tasaAdministrativaALiquidar;
    }

}
