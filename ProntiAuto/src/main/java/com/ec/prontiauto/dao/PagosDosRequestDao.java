package com.ec.prontiauto.dao;

import com.ec.prontiauto.abstracts.AbstractRequestDao;

import java.sql.Date;

public class PagosDosRequestDao extends AbstractRequestDao {
    private Integer diasLaboradosAlAnio;
    private Float valorReal;
    private Float valorNominal;
    private Float otrosIngresos;
    private Float totalIngresos;
    private Float otrosDescuentos;
    private Float multas;
    private Float totalEgresos;
    private Float valorAPagar;
    private Date fechaInicio;
    private Date fechaFin;
    private Integer anioPago;

    private Float valorMes1;
    private Float valorMes2;
    private Float valorMes3;
    private Float valorMes4;
    private Float valorMes5;
    private Float valorMes6;
    private Float valorMes7;
    private Float valorMes8;
    private Float valorMes9;
    private Float valorMes10;
    private Float valorMes11;
    private Float valorMes12;

    private Float prestamosEmpresa;
    private Float anticipos;
    private Date fechaActual;

    private Integer idTrabajador;
    private Integer idPagosUno;

    private Integer idPeriodoLaboral;

    public PagosDosRequestDao() {}

    public Integer getDiasLaboradosAlAnio() {
        return diasLaboradosAlAnio;
    }

    public void setDiasLaboradosAlAnio(Integer diasLaboradosAlAnio) {
        this.diasLaboradosAlAnio = diasLaboradosAlAnio;
    }

    public Float getValorReal() {
        return valorReal;
    }

    public void setValorReal(Float valorReal) {
        this.valorReal = valorReal;
    }

    public Float getValorNominal() {
        return valorNominal;
    }

    public void setValorNominal(Float valorNominal) {
        this.valorNominal = valorNominal;
    }

    public Float getOtrosIngresos() {
        return otrosIngresos;
    }

    public void setOtrosIngresos(Float otrosIngresos) {
        this.otrosIngresos = otrosIngresos;
    }

    public Float getTotalIngresos() {
        return totalIngresos;
    }

    public void setTotalIngresos(Float totalIngresos) {
        this.totalIngresos = totalIngresos;
    }

    public Float getOtrosDescuentos() {
        return otrosDescuentos;
    }

    public void setOtrosDescuentos(Float otrosDescuentos) {
        this.otrosDescuentos = otrosDescuentos;
    }

    public Float getMultas() {
        return multas;
    }

    public void setMultas(Float multas) {
        this.multas = multas;
    }

    public Float getTotalEgresos() {
        return totalEgresos;
    }

    public void setTotalEgresos(Float totalEgresos) {
        this.totalEgresos = totalEgresos;
    }

    public Float getValorAPagar() {
        return valorAPagar;
    }

    public void setValorAPagar(Float valorAPagar) {
        this.valorAPagar = valorAPagar;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getAnioPago() {
        return anioPago;
    }

    public void setAnioPago(Integer anioPago) {
        this.anioPago = anioPago;
    }

    public Float getValorMes1() {
        return valorMes1;
    }

    public void setValorMes1(Float valorMes1) {
        this.valorMes1 = valorMes1;
    }

    public Float getValorMes2() {
        return valorMes2;
    }

    public void setValorMes2(Float valorMes2) {
        this.valorMes2 = valorMes2;
    }

    public Float getValorMes3() {
        return valorMes3;
    }

    public void setValorMes3(Float valorMes3) {
        this.valorMes3 = valorMes3;
    }

    public Float getValorMes4() {
        return valorMes4;
    }

    public void setValorMes4(Float valorMes4) {
        this.valorMes4 = valorMes4;
    }

    public Float getValorMes5() {
        return valorMes5;
    }

    public void setValorMes5(Float valorMes5) {
        this.valorMes5 = valorMes5;
    }

    public Float getValorMes6() {
        return valorMes6;
    }

    public void setValorMes6(Float valorMes6) {
        this.valorMes6 = valorMes6;
    }

    public Float getValorMes7() {
        return valorMes7;
    }

    public void setValorMes7(Float valorMes7) {
        this.valorMes7 = valorMes7;
    }

    public Float getValorMes8() {
        return valorMes8;
    }

    public void setValorMes8(Float valorMes8) {
        this.valorMes8 = valorMes8;
    }

    public Float getValorMes9() {
        return valorMes9;
    }

    public void setValorMes9(Float valorMes9) {
        this.valorMes9 = valorMes9;
    }

    public Float getValorMes10() {
        return valorMes10;
    }

    public void setValorMes10(Float valorMes10) {
        this.valorMes10 = valorMes10;
    }

    public Float getValorMes11() {
        return valorMes11;
    }

    public void setValorMes11(Float valorMes11) {
        this.valorMes11 = valorMes11;
    }

    public Float getValorMes12() {
        return valorMes12;
    }

    public void setValorMes12(Float valorMes12) {
        this.valorMes12 = valorMes12;
    }

    public Float getPrestamosEmpresa() {
        return prestamosEmpresa;
    }

    public void setPrestamosEmpresa(Float prestamosEmpresa) {
        this.prestamosEmpresa = prestamosEmpresa;
    }

    public Float getAnticipos() {
        return anticipos;
    }

    public void setAnticipos(Float anticipos) {
        this.anticipos = anticipos;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public Integer getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(Integer idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public Integer getIdPagosUno() {
        return idPagosUno;
    }

    public void setIdPagosUno(Integer idPagosUno) {
        this.idPagosUno = idPagosUno;
    }

    public Integer getIdPeriodoLaboral() {
        return idPeriodoLaboral;
    }

    public void setIdPeriodoLaboral(Integer idPeriodoLaboral) {
        this.idPeriodoLaboral = idPeriodoLaboral;
    }
}
