package com.ec.prontiauto.dao;

public class DetalleImpuestoFacturaDao {
    private String lineaImpuesto;
    private Double baseImponible;
    private Double valorImpuesto;
    private Double porcentaje;

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

    public Double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }
}
