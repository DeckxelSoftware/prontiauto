package com.ec.prontiauto.entidad;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "detalle_pago")
public class DetallePago extends AbstractEntities {

    @Column(name = "valor", nullable = false)
    @CsvBindByName(column = "valor")
    private BigDecimal valor;

    @Column(name = "\"numeroCuota\"", nullable = false)
    @CsvBindByName(column = "numero_cuota")
    private Integer numeroCuota;

    @Column(name = "\"abonoCapital\"")
    @CsvBindByName(column = "abono_capital")
    private String abonoCapital;

    @Column(name = "\"cuotaAdministrativa\"")
    @CsvBindByName(column = "cuota_administrativa")
    private String cuotaAdministrativa;

    @Column(name = "oferta")
    @CsvBindByName(column = "oferta")
    private String oferta;

    @Column(name = "\"cargoAdjudicacion\"")
    @CsvBindByName(column = "cargo_adjudicacion")
    private String cargoAdjudicacion;

    @Column(name = "\"tipo\"")
    @CsvBindByName(column = "tipo")
    private String tipo;

    @OneToOne
    @JoinColumn(name = "\"idPago\"", referencedColumnName = "id", nullable = false)
    private Pago idPago;

    @Transient
    @CsvBindByName(column = "id_pago")
    private Integer idPago1;

    @ManyToOne
    @JoinColumn(name = "\"idItemCobroPago\"", referencedColumnName = "id", nullable = false)
    private ItemCobroPago idItemCobroPago;

    @Transient
    @CsvBindByName(column = "id_item_cobro_pago")
    private Integer idItemCobroPago1;

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Integer getNumeroCuota() {
        return numeroCuota;
    }

    public void setNumeroCuota(Integer numeroCuota) {
        this.numeroCuota = numeroCuota;
    }

    public String getAbonoCapital() {
        return abonoCapital;
    }

    public void setAbonoCapital(String abonoCapital) {
        this.abonoCapital = abonoCapital;
    }

    public String getCuotaAdministrativa() {
        return cuotaAdministrativa;
    }

    public void setCuotaAdministrativa(String cuotaAdministrativa) {
        this.cuotaAdministrativa = cuotaAdministrativa;
    }

    public String getOferta() {
        return oferta;
    }

    public void setOferta(String oferta) {
        this.oferta = oferta;
    }

    public String getCargoAdjudicacion() {
        return cargoAdjudicacion;
    }

    public void setCargoAdjudicacion(String cargoAdjudicacion) {
        this.cargoAdjudicacion = cargoAdjudicacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Pago getIdPago() {
        return idPago;
    }

    public void setIdPago(Pago idPago) {
        this.idPago = idPago;
    }

    public Integer getIdPago1() {
        return idPago1;
    }

    public void setIdPago1(Integer idPago1) {
        this.idPago1 = idPago1;
    }

    public ItemCobroPago getIdItemCobroPago() {
        return idItemCobroPago;
    }

    public void setIdItemCobroPago(ItemCobroPago idItemCobroPago) {
        this.idItemCobroPago = idItemCobroPago;
    }

    public Integer getIdItemCobroPago1() {
        return idItemCobroPago1;
    }

    public void setIdItemCobroPago1(Integer idItemCobroPago1) {
        this.idItemCobroPago1 = idItemCobroPago1;
    }

}