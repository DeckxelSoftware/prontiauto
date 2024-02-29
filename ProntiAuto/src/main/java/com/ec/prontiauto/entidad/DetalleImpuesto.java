package com.ec.prontiauto.entidad;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.ec.prontiauto.dao.DetalleImpuestoFacturaDao;
import com.opencsv.bean.CsvBindByName;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "detalle_impuesto")
public class DetalleImpuesto extends AbstractEntities {

    @Column(name = "linea_impuesto")
    @CsvBindByName(column = "lineaImpuesto")
    private String lineaImpuesto;

    @Column(name = "base_imponible")
    @CsvBindByName(column = "baseImponible")
    private Double baseImponible;

    @Column(name = "valor_impuesto")
    @CsvBindByName(column = "valorImpuesto")
    private Double valorImpuesto;

    @Column(name = "porcentaje")
    @CsvBindByName(column = "porcentaje")
    private Double porcentaje;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_detalle_compra", referencedColumnName = "id")
    private DetalleCompra detalle;

    public DetalleImpuesto(DetalleImpuestoFacturaDao detalleImpuestoFacturaDao){
        this.lineaImpuesto = detalleImpuestoFacturaDao.getLineaImpuesto();
        this.baseImponible = detalleImpuestoFacturaDao.getBaseImponible();
        this.valorImpuesto = detalleImpuestoFacturaDao.getValorImpuesto();
        this.porcentaje = detalleImpuestoFacturaDao.getPorcentaje();
        this.setSisHabilitado("A");
        this.setSisCreado(new Date());
    }

    public DetalleImpuesto() {

    }


    public String getLineaImpuesto() {
        return lineaImpuesto;
    }

    public void setLineaImpuesto(String lineaImpuesto) {
        this.lineaImpuesto = lineaImpuesto;
    }

    public Double getBaseImponible() {
        return baseImponible;
    }

    public void setBaseImponible(Double baseImponible) {
        this.baseImponible = baseImponible;
    }

    public Double getValorImpuesto() {
        return valorImpuesto;
    }

    public void setValorImpuesto(Double valorImpuesto) {
        this.valorImpuesto = valorImpuesto;
    }

    public DetalleCompra getDetalle() {
        return detalle;
    }

    public void setDetalle(DetalleCompra detalle) {
        this.detalle = detalle;
    }

    public Double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }
}
