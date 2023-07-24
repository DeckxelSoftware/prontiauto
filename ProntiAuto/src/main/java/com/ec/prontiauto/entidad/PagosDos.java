package com.ec.prontiauto.entidad;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "pagos2")
public class PagosDos extends AbstractEntities {

    @Column(name = "\"diasLaboradosAlAnio\"")
    @CsvBindByName(column = "dias_laborados_al_anio")
    private Integer diasLaboradosAlAnio;

    @Column(name = "\"valorReal\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "valor_real")
    private Float valorReal;

    @Column(name = "\"valorNominal\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "valor_nominal")
    private Float valorNominal;

    @Column(name = "\"otrosIngresos\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "otros_ingresos")
    private Float otrosIngresos;

    @Column(name = "\"totalIngresos\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "total_ingresos")
    private Float totalIngresos;

    @Column(name = "\"otrosDescuentos\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "otros_descuentos")
    private Float otrosDescuentos;

    @Column(name = "multas", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "multas")
    private Float multas;

    @Column(name = "\"totalEgresos\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "total_egresos")
    private Float totalEgresos;

    @Column(name = "\"valorAPagar\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "valor_a_pagar")
    private Float valorAPagar;

    @Column(name = "\"fechaInicio\"")
    @CsvBindByName(column = "fecha_inicio")
    private Date fechaInicio;

    @Column(name = "\"fechaFin\"")
    @CsvBindByName(column = "fecha_fin")
    private Date fechaFin;

    @Column(name = "\"anioPago\"")
    @CsvBindByName(column = "anio_pago")
    private Integer anioPago;

    @Column(name = "\"valorMes1\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "valor_mes1")
    private Float valorMes1;

    @Column(name = "\"valorMes2\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "valor_mes2")
    private Float valorMes2;

    @Column(name = "\"valorMes3\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "valor_mes3")
    private Float valorMes3;

    @Column(name = "\"valorMes4\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "valor_mes4")
    private Float valorMes4;

    @Column(name = "\"valorMes5\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "valor_mes5")
    private Float valorMes5;

    @Column(name = "\"valorMes6\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "valor_mes6")
    private Float valorMes6;

    @Column(name = "\"valorMes7\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "valor_mes7")
    private Float valorMes7;

    @Column(name = "\"valorMes8\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "valor_mes8")
    private Float valorMes8;

    @Column(name = "\"valorMes9\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "valor_mes9")
    private Float valorMes9;

    @Column(name = "\"valorMes10\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "valor_mes10")
    private Float valorMes10;

    @Column(name = "\"valorMes11\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "valor_mes11")
    private Float valorMes11;

    @Column(name = "\"valorMes12\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "valor_mes12")
    private Float valorMes12;

    @Column(name = "\"prestamosEmpresa\"", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "prestamos_empresa")
    private Float prestamosEmpresa;

    @Column(name = "anticipos", columnDefinition = "Decimal(10,2)")
    @CsvBindByName(column = "anticipos")
    private Float anticipos;

    @Column(name = "\"fechaActual\"")
    @CsvBindByName(column = "fecha_actual")
    private Date fechaActual;

    @ManyToOne
    @JoinColumn(name = "\"idPagosUno\"", referencedColumnName = "id", nullable = false)
    private PagosUno idPagosUno;

    @ManyToOne
    @JoinColumn(name = "\"idTrabajador\"", referencedColumnName = "id", nullable = false)
    private Trabajador idTrabajador;

    @ManyToOne
    @JoinColumn(name = "\"idPeriodoLaboral\"", referencedColumnName = "id", nullable = false)
    private PeriodoLaboral idPeriodoLaboral;

    @Transient
    @CsvBindByName(column = "id_pagos1")
    private Integer idPagosUno1;

    @Transient
    @CsvBindByName(column = "id_trabajador")
    private Integer IdTrabajador1;

    @Transient
    @CsvBindByName(column = "id_periodo_laboral")
    private Integer IdPeriodoLaboral1;

    public PagosDos() {
    }

    public PagosDos setValoresDiferentes(PagosDos regAntiguo, PagosDos regActualizar) {

        if (regActualizar.getDiasLaboradosAlAnio() != null) {
            regAntiguo.setDiasLaboradosAlAnio(regActualizar.getDiasLaboradosAlAnio());
        }
        if (regActualizar.getValorReal() != null) {
            regAntiguo.setValorReal(regActualizar.getValorReal());
        }
        if (regActualizar.getValorNominal() != null) {
            regAntiguo.setValorNominal(regActualizar.getValorNominal());
        }
        if (regActualizar.getOtrosIngresos() != null) {
            regAntiguo.setOtrosIngresos(regActualizar.getOtrosIngresos());
        }
        if (regActualizar.getTotalIngresos() != null) {
            regAntiguo.setTotalIngresos(regActualizar.getTotalIngresos());
        }
        if (regActualizar.getOtrosDescuentos() != null) {
            regAntiguo.setOtrosDescuentos(regActualizar.getOtrosDescuentos());
        }
        if (regActualizar.getMultas() != null) {
            regAntiguo.setMultas(regActualizar.getMultas());
        }
        if (regActualizar.getTotalEgresos() != null) {
            regAntiguo.setTotalEgresos(regActualizar.getTotalEgresos());
        }
        if (regActualizar.getValorAPagar() != null) {
            regAntiguo.setValorAPagar(regActualizar.getValorAPagar());
        }
        if (regActualizar.getFechaInicio() != null) {
            regAntiguo.setFechaInicio(regActualizar.getFechaInicio());
        }
        if (regActualizar.getFechaFin() != null) {
            regAntiguo.setFechaFin(regActualizar.getFechaFin());
        }
        if (regActualizar.getAnioPago() != null) {
            regAntiguo.setAnioPago(regActualizar.getAnioPago());
        }
        if (regActualizar.getValorMes1() != null) {
            regAntiguo.setValorMes1(regActualizar.getValorMes1());
        }
        if (regActualizar.getValorMes2() != null) {
            regAntiguo.setValorMes2(regActualizar.getValorMes2());
        }
        if (regActualizar.getValorMes3() != null) {
            regAntiguo.setValorMes3(regActualizar.getValorMes3());
        }
        if (regActualizar.getValorMes4() != null) {
            regAntiguo.setValorMes4(regActualizar.getValorMes4());
        }
        if (regActualizar.getValorMes5() != null) {
            regAntiguo.setValorMes5(regActualizar.getValorMes5());
        }
        if (regActualizar.getValorMes6() != null) {
            regAntiguo.setValorMes6(regActualizar.getValorMes6());
        }
        if (regActualizar.getValorMes7() != null) {
            regAntiguo.setValorMes7(regActualizar.getValorMes7());
        }
        if (regActualizar.getValorMes8() != null) {
            regAntiguo.setValorMes8(regActualizar.getValorMes8());
        }
        if (regActualizar.getValorMes9() != null) {
            regAntiguo.setValorMes9(regActualizar.getValorMes9());
        }
        if (regActualizar.getValorMes10() != null) {
            regAntiguo.setValorMes10(regActualizar.getValorMes10());
        }
        if (regActualizar.getValorMes11() != null) {
            regAntiguo.setValorMes11(regActualizar.getValorMes11());
        }
        if (regActualizar.getValorMes12() != null) {
            regAntiguo.setValorMes12(regActualizar.getValorMes12());
        }
        if (regActualizar.getPrestamosEmpresa() != null) {
            regAntiguo.setPrestamosEmpresa(regActualizar.getPrestamosEmpresa());
        }
        if (regActualizar.getAnticipos() != null) {
            regAntiguo.setAnticipos(regActualizar.getAnticipos());
        }
        if (regActualizar.getFechaActual() != null) {
            regAntiguo.setFechaActual(regActualizar.getFechaActual());
        }
        if (regActualizar.getSisHabilitado() != null) {
            regAntiguo.setSisHabilitado(regActualizar.getSisHabilitado());
        }

        return regAntiguo;
    }

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

    public PagosUno getIdPagosUno() {
        return idPagosUno;
    }

    public void setIdPagosUno(PagosUno idPagosUno) {
        this.idPagosUno = idPagosUno;
    }

    public Integer getIdPagosUno1() {
        return idPagosUno1;
    }

    public void setIdPagosUno1(Integer idPagosUno1) {
        this.idPagosUno1 = idPagosUno1;
    }

    public Trabajador getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(Trabajador idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public Integer getIdTrabajador1() {
        return IdTrabajador1;
    }

    public void setIdTrabajador1(Integer idTrabajador1) {
        IdTrabajador1 = idTrabajador1;
    }

    public PeriodoLaboral getIdPeriodoLaboral() {
        return idPeriodoLaboral;
    }

    public void setIdPeriodoLaboral(PeriodoLaboral idPeriodoLaboral) {
        this.idPeriodoLaboral = idPeriodoLaboral;
    }

    public Integer getIdPeriodoLaboral1() {
        return IdPeriodoLaboral1;
    }

    public void setIdPeriodoLaboral1(Integer idPeriodoLaboral1) {
        IdPeriodoLaboral1 = idPeriodoLaboral1;
    }
}
