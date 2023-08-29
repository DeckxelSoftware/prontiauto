package com.ec.prontiauto.entidad;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.ec.prontiauto.dao.DetalleCompraFacturaDao;
import com.opencsv.bean.CsvBindByName;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "detalle_compra")
public class DetalleCompra extends AbstractEntities {

    @Column(name = "descripcion")
    @CsvBindByName(column = "descripcion")
    private String descripcion;

    @Column(name = "cantidad_factura")
    @CsvBindByName(column = "cantidadFactura")
    private Integer cantidadFactura;

    @Column(name = "precio_factura")
    @CsvBindByName(column = "precioFactura")
    private Double precioFactura;

    @Column(name = "descuento")
    @CsvBindByName(column = "descuento")
    private Double descuento;

    @Column(name = "importe")
    @CsvBindByName(column = "importe")
    private Double importe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cabecera_compra" , nullable = false, referencedColumnName = "id")
    private CabeceraCompra cabecera;


    @OneToMany(mappedBy = "detalle" , cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<DetalleImpuesto> impuestos;


    public DetalleCompra(DetalleCompraFacturaDao detalleCompraFacturaDao){
        this.descripcion = detalleCompraFacturaDao.getDescripcion();
        this.cantidadFactura = detalleCompraFacturaDao.getCantidadFactura();
        this.precioFactura = detalleCompraFacturaDao.getPrecioFactura();
        this.descuento = detalleCompraFacturaDao.getDescuento();
        this.importe = detalleCompraFacturaDao.getImporte();
        this.setSisHabilitado("A");
        this.setSisCreado(new Date());
        //this.impuestos = detalleCompraFacturaDao.getDetalleImpuestoCollection().stream().map(DetalleImpuesto::new).collect(Collectors.toSet());
    }

    public DetalleCompra() {

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

    public CabeceraCompra getCabecera() {
        return cabecera;
    }

    public void setCabecera(CabeceraCompra cabecera) {
        this.cabecera = cabecera;
    }

    public Set<DetalleImpuesto> getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(Set<DetalleImpuesto> impuestos) {
        this.impuestos = impuestos;
    }
}
