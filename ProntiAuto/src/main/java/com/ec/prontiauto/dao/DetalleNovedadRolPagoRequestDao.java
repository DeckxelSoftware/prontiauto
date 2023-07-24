package com.ec.prontiauto.dao;

import java.math.BigDecimal;

import com.ec.prontiauto.abstracts.AbstractRequestDao;

public class DetalleNovedadRolPagoRequestDao extends AbstractRequestDao {
    private String codigoNovedad;
    private String tipoNovedad;
    private BigDecimal valor;
    private String concepto;
    private Integer idRubrosRol;
    private Integer idTrabajador;
    private Integer idPeriodoLaboral;

    public DetalleNovedadRolPagoRequestDao() {
    }

    public String getCodigoNovedad() {
        return codigoNovedad;
    }

    public void setCodigoNovedad(String codigoNovedad) {
        this.codigoNovedad = codigoNovedad;
    }

    public String getTipoNovedad() {
        return tipoNovedad;
    }

    public void setTipoNovedad(String tipoNovedad) {
        this.tipoNovedad = tipoNovedad;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Integer getIdRubrosRol() {
        return idRubrosRol;
    }

    public void setIdRubrosRol(Integer idRubrosRol) {
        this.idRubrosRol = idRubrosRol;
    }

    public Integer getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(Integer idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public Integer getIdPeriodoLaboral() {
        return idPeriodoLaboral;
    }

    public void setIdPeriodoLaboral(Integer idPeriodoLaboral) {
        this.idPeriodoLaboral = idPeriodoLaboral;
    }

}
