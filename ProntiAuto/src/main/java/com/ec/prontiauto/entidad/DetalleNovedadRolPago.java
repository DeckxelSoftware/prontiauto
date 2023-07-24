package com.ec.prontiauto.entidad;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostPersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "detalle_novedad_rol_pago")
public class DetalleNovedadRolPago extends AbstractEntities {

    @Column(name = "\"codigo_novedad\"", nullable = false, length = 255)
    @CsvBindByName(column = "codigo_novedad")
    private String codigoNovedad;

    @Column(name = "\"tipo_novedad\"", nullable = false, length = 255)
    @CsvBindByName(column = "tipo_novedad")
    private String tipoNovedad;

    @Column(name = "valor", nullable = false)
    @CsvBindByName(column = "valor")
    private BigDecimal valor;

    @Column(name = "\"concepto\"", length = 255)
    @CsvBindByName(column = "concepto")
    private String concepto;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "clave", unique = true)
    private String clave;

    @ManyToOne
    @JoinColumn(name = "\"idRubrosRol\"", referencedColumnName = "id", nullable = false)
    private RubrosRol idRubrosRol;

    @Transient
    @CsvBindByName(column = "id_rubros_rol")
    private Integer idRubrosRol1;

    @ManyToOne
    @JoinColumn(name = "\"idTrabajador\"", referencedColumnName = "id", nullable = false)
    private Trabajador idTrabajador;

    @Transient
    @CsvBindByName(column = "id_trabajador")
    private Integer idTrabajador1;

    @ManyToOne
    @JoinColumn(name = "\"idPeriodoLaboral\"", referencedColumnName = "id", nullable = false)
    private PeriodoLaboral idPeriodoLaboral;

    @Transient
    @CsvBindByName(column = "id_periodo_laboral")
    private Integer idPeriodoLaboral1;

    public DetalleNovedadRolPago() {
    }

    @PostPersist
    private void prePersistClave() {
        numero = this.getId();
        clave = codigoNovedad + numero;
    }

    public RubrosRol getIdRubrosRol() {
        return idRubrosRol;
    }

    public void setIdRubrosRol(RubrosRol idRubrosRol) {
        this.idRubrosRol = idRubrosRol;
    }

    public Integer getIdRubrosRol1() {
        return idRubrosRol1;
    }

    public void setIdRubrosRol1(Integer idRubrosRol1) {
        this.idRubrosRol1 = idRubrosRol1;
    }

    public Trabajador getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(Trabajador idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public Integer getIdTrabajador1() {
        return idTrabajador1;
    }

    public void setIdTrabajador1(Integer idTrabajador1) {
        this.idTrabajador1 = idTrabajador1;
    }

    public PeriodoLaboral getIdPeriodoLaboral() {
        return idPeriodoLaboral;
    }

    public void setIdPeriodoLaboral(PeriodoLaboral idPeriodoLaboral) {
        this.idPeriodoLaboral = idPeriodoLaboral;
    }

    public Integer getIdPeriodoLaboral1() {
        return idPeriodoLaboral1;
    }

    public void setIdPeriodoLaboral1(Integer idPeriodoLaboral1) {
        this.idPeriodoLaboral1 = idPeriodoLaboral1;
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

    public DetalleNovedadRolPago setValoresDiferentes(DetalleNovedadRolPago antiguo, DetalleNovedadRolPago nuevo) {
        if (nuevo.getCodigoNovedad() != null) {
            antiguo.setCodigoNovedad(nuevo.getCodigoNovedad());
        }
        if (nuevo.getTipoNovedad() != null) {
            antiguo.setTipoNovedad(nuevo.getTipoNovedad());
        }
        if (nuevo.getValor() != null) {
            antiguo.setValor(nuevo.getValor());
        }
        if (nuevo.getConcepto() != null) {
            antiguo.setConcepto(nuevo.getConcepto());
        }
        if (nuevo.getIdRubrosRol() != null && nuevo.getIdRubrosRol().getId() != null) {
            antiguo.setIdRubrosRol(nuevo.getIdRubrosRol());
        }
        if (nuevo.getIdTrabajador() != null && nuevo.getIdTrabajador().getId() != null) {
            antiguo.setIdTrabajador(nuevo.getIdTrabajador());
        }
        if (nuevo.getIdPeriodoLaboral() != null && nuevo.getIdPeriodoLaboral().getId() != null) {
            antiguo.setIdPeriodoLaboral(nuevo.getIdPeriodoLaboral());
        }
        if (nuevo.getSisHabilitado() != null) {
            antiguo.setSisHabilitado(nuevo.getSisHabilitado());
        }

        return antiguo;
    }
}
