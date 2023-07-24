package com.ec.prontiauto.dao;

import java.math.BigDecimal;

import com.ec.prontiauto.abstracts.AbstractResponseDao;

public class DetalleNovedadRolPagoResponseDao extends AbstractResponseDao {
    private String codigoNovedad;
    private String tipoNovedad;
    private BigDecimal valor;
    private String concepto;
    private RubrosRolResponseDao idRubrosRol;
    private TrabajadorResponseDao idTrabajador;
    private PeriodoLaboralResponseDao idPeriodoLaboral;

    public DetalleNovedadRolPagoResponseDao() {
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

    public RubrosRolResponseDao getIdRubrosRol() {
        return idRubrosRol;
    }

    public void setIdRubrosRol(RubrosRolResponseDao idRubrosRol) {
        this.idRubrosRol = idRubrosRol;
    }

    public TrabajadorResponseDao getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(TrabajadorResponseDao idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public PeriodoLaboralResponseDao getIdPeriodoLaboral() {
        return idPeriodoLaboral;
    }

    public void setIdPeriodoLaboral(PeriodoLaboralResponseDao idPeriodoLaboral) {
        this.idPeriodoLaboral = idPeriodoLaboral;
    }

}
