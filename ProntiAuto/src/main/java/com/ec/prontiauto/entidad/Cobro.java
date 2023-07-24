package com.ec.prontiauto.entidad;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "cobro")
public class Cobro extends AbstractEntities {

    @Column(name = "\"valorACobrar\"", nullable = false)
    @CsvBindByName(column = "valor_a_cobrar")
    private BigDecimal valorACobrar;

    @Column(name = "\"detalleCobros\"")
    private String detalleCobros;

    @ManyToOne
    @JoinColumn(name = "\"idContrato\"", referencedColumnName = "id", nullable = false)
    private Contrato idContrato;

    @Transient
    @CsvBindByName(column = "id_contrato")
    private Integer idContrato1;

    // @OneToOne(mappedBy = "idFactura")
    // private Factura idFactura;

    // @Transient
    // @CsvBindByName(column = "id_factura")
    // private Integer idFactura1;

    @OneToMany(mappedBy = "idCobro")
    private List<Pago> pagoCollection;

    public Cobro() {
    }

    public BigDecimal getValorACobrar() {
        return valorACobrar;
    }

    public void setValorACobrar(BigDecimal valorACobrar) {
        this.valorACobrar = valorACobrar;
    }

    public Contrato getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(Contrato idContrato) {
        this.idContrato = idContrato;
    }

    public Integer getIdContrato1() {
        return idContrato1;
    }

    public void setIdContrato1(Integer idContrato1) {
        this.idContrato1 = idContrato1;
    }

    public List<Pago> getPagoCollection() {
        return pagoCollection;
    }

    public void setPagoCollection(List<Pago> pagoCollection) {
        this.pagoCollection = pagoCollection;
    }

    public String getDetalleCobros() {
        return detalleCobros;
    }

    public void setDetalleCobros(String detalleCobros) {
        this.detalleCobros = detalleCobros;
    }

}
