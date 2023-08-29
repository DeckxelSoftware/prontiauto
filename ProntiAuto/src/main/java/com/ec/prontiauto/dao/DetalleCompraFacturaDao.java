package com.ec.prontiauto.dao;

import com.ec.prontiauto.entidad.DetalleCompra;

import java.util.Set;

public class DetalleCompraFacturaDao {

    private String descripcion;
    private Integer cantidadFactura;
    private Double precioFactura;
    private Double descuento;
    private Double importe;
    private Set<DetalleImpuestoFacturaDao> detalleImpuestoCollection;

    public DetalleCompraFacturaDao(){}

    public DetalleCompraFacturaDao(DetalleCompra detalleCompra){
        this.descripcion = detalleCompra.getDescripcion();
        this.cantidadFactura = detalleCompra.getCantidadFactura();
        this.precioFactura = detalleCompra.getPrecioFactura();
        this.descuento = detalleCompra.getDescuento();
        this.importe = detalleCompra.getImporte();
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCantidadFactura() {
        return cantidadFactura;
    }

    public void setCantidadFactura(Integer cantidadFactura) {
        this.cantidadFactura = cantidadFactura;
    }

    public Double getPrecioFactura() {
        return precioFactura;
    }

    public void setPrecioFactura(Double precioFactura) {
        this.precioFactura = precioFactura;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Set<DetalleImpuestoFacturaDao> getDetalleImpuestoCollection() {
        return detalleImpuestoCollection;
    }

    public void setDetalleImpuestoCollection(Set<DetalleImpuestoFacturaDao> detalleImpuestoCollection) {
        this.detalleImpuestoCollection = detalleImpuestoCollection;
    }
}
