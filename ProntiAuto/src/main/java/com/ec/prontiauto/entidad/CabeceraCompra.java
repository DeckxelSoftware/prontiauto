package com.ec.prontiauto.entidad;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.opencsv.bean.CsvBindByName;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "cabecera_compra")
public class CabeceraCompra extends AbstractEntities {
    @Column(name = "nombre_proveedor")
    @CsvBindByName(column = "nombreProveedor")
    private String nombreProveedor;

    @Column(name = "ruc_proveedor")
    @CsvBindByName(column = "rucProveedor")
    private String rucProveedor;

    @Column(name = "autorizacion")
    @CsvBindByName(column = "autorizacion")
    private String autorizacion;

    @Column(name = "numero_documento")
    @CsvBindByName(column = "numeroDocumento")
    private String numeroDocumento;

    @Column(name = "serie")
    @CsvBindByName(column = "serie")
    private String serie;

    @Column(name = "fecha_recepcion")
    @CsvBindByName(column = "fechaRecepcion")
    private Date fechaRecepcion;

    @Column(name = "fecha_emision")
    @CsvBindByName(column = "fechaEmision")
    private Date fechaEmision;

    @Column(name = "fecha_vencimiento")
    @CsvBindByName(column = "fechaVencimiento")
    private Date fechaVencimiento;

    @Column(name = "version")
    @CsvBindByName(column = "version")
    private Double version;

    @Column(name = "terminos_pago")
    @CsvBindByName(column = "terminosPago")
    private String terminosPago;

    @Column(name = "tipo_documento")
    @CsvBindByName(column = "tipoDocumento")
    private String tipoDocumento;

    @Column(name = "subtotal")
    @CsvBindByName(column = "subtotal")
    private Double subtotal;

    @Column(name = "impuesto")
    @CsvBindByName(column = "impuesto")
    private Double impuesto;

    @Column(name = "total_factura")
    @CsvBindByName(column = "totalFactura")
    private Double totalFactura;

    @Column(name = "retenciones")
    @CsvBindByName(column = "retenciones")
    private Double retenciones;

    @Column(name = "valor_Pagar")
    @CsvBindByName(column = "valor_Pagar")
    private Double valorAPagar;

    @Column(name = "observaciones")
    @CsvBindByName(column = "observaciones")
    private String observaciones;

    @OneToMany(mappedBy = "cabecera", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<DetalleCompra> detalles;

    @OneToOne
    @JoinColumn(name = "id_recurso" , referencedColumnName = "id")
    private Recurso recurso;

    @OneToOne
    @JoinColumn(name = "id_agencia", referencedColumnName = "id")
    private Agencia agencia;


    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getRucProveedor() {
        return rucProveedor;
    }

    public void setRucProveedor(String rucProveedor) {
        this.rucProveedor = rucProveedor;
    }

    public String getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(String autorizacion) {
        this.autorizacion = autorizacion;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public Date getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(Date fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Double getVersion() {
        return version;
    }

    public void setVersion(Double version) {
        this.version = version;
    }

    public String getTerminosPago() {
        return terminosPago;
    }

    public void setTerminosPago(String terminosPago) {
        this.terminosPago = terminosPago;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(Double impuesto) {
        this.impuesto = impuesto;
    }

    public Double getTotalFactura() {
        return totalFactura;
    }

    public void setTotalFactura(Double totalFactura) {
        this.totalFactura = totalFactura;
    }

    public Double getRetenciones() {
        return retenciones;
    }

    public void setRetenciones(Double retenciones) {
        this.retenciones = retenciones;
    }

    public Double getValorAPagar() {
        return valorAPagar;
    }

    public void setValorAPagar(Double valorAPagar) {
        this.valorAPagar = valorAPagar;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Set<DetalleCompra> getDetalles() {
        return detalles;
    }

    public void setDetalles(Set<DetalleCompra> detalles) {
        this.detalles = detalles;
    }

    public Recurso getRecurso() {
        return recurso;
    }

    public void setRecurso(Recurso recurso) {
        this.recurso = recurso;
    }

    public Agencia getAgencia() {
        return agencia;
    }

    public void setAgencia(Agencia agencia) {
        this.agencia = agencia;
    }
}
