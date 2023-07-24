package com.ec.prontiauto.entidad;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "orden_de_compra", uniqueConstraints = { @UniqueConstraint(columnNames = { "\"idContrato\"",
        "\"idArticulo\"", "\"idProveedor\"" }),
        @UniqueConstraint(columnNames = { "\"idContrato\"", "\"idArticulo\"" }) })
public class OrdenCompra extends AbstractEntities {

    @Column(name = "\"fechaInicio\"", nullable = false)
    @CsvBindByName(column = "fecha_inicio")
    private Date fechaInicio;

    @Column(name = "\"numeroOrdenContrato\"", nullable = false)
    @CsvBindByName(column = "numero_orden_contrato")
    private String numeroOrdenContrato;

    @Column(name = "\"fechaCartaOferta\"", nullable = false)
    @CsvBindByName(column = "fecha_carta_oferta")
    private Date fechaCartaOferta;

    @Column(name = "\"fechaRegistroOferta\"", nullable = false)
    @CsvBindByName(column = "fecha_registro_oferta")
    private Date fechaRegistroOferta;

    @Column(name = "\"nombreCliente\"", nullable = false)
    @CsvBindByName(column = "nombre_cliente")
    private String nombreCliente;

    @Column(name = "correo")
    @CsvBindByName(column = "correo")
    private String correo;

    @Column(name = "telefono", nullable = false)
    @CsvBindByName(column = "telefono")
    private String telefono;

    @Column(name = "\"tipoDocumentoIdentidad\"", nullable = false)
    @CsvBindByName(column = "tipo_documento_identidad")
    private String tipoDocumentoIdentidad;

    @Column(name = "\"documentoIdentidad\"", nullable = false)
    @CsvBindByName(column = "documento_identidad")
    private String documentoIdentidad;

    @Column(name = "marca", nullable = false)
    @CsvBindByName(column = "marca")
    private String marca;

    @Column(name = "modelo", nullable = false)
    @CsvBindByName(column = "modelo")
    private String modelo;

    @Column(name = "motor", nullable = false)
    @CsvBindByName(column = "motor")
    private String motor;

    @Column(name = "chasis", nullable = false)
    @CsvBindByName(column = "chasis")
    private String chasis;

    @Column(name = "placa", nullable = false)
    @CsvBindByName(column = "placa")
    private String placa;

    @Column(name = "color", nullable = false)
    @CsvBindByName(column = "color")
    private String color;

    @Column(name = "anio", nullable = false)
    @CsvBindByName(column = "anio")
    private Integer anio;

    @Column(name = "\"valorSinIva\"", nullable = false, precision = 10, scale = 2)
    @CsvBindByName(column = "valor_sin_iva")
    private Float valorSinIva;

    @Column(name = "\"beneficiarioCheque\"", nullable = false)
    @CsvBindByName(column = "beneficiario_cheque")
    private String beneficiarioCheque;

    @Column(name = "\"valorTotal\"", nullable = false, precision = 10, scale = 2)
    @CsvBindByName(column = "valor_total")
    private Float valorTotal;

    @Column(name = "observacion", nullable = false)
    @CsvBindByName(column = "observacion")
    private String observacion;

    @Column(name = "\"tipoVehiculo\"", nullable = false)
    @CsvBindByName(column = "tipo_vehiculo")
    private String tipoVehiculo;

    @OneToOne
    @JoinColumn(name = "\"idContrato\"", referencedColumnName = "id", nullable = false)
    private Contrato idContrato;

    @OneToOne
    @JoinColumn(name = "\"idArticulo\"", referencedColumnName = "id", nullable = false)
    private Articulo idArticulo;

    @ManyToOne
    @JoinColumn(name = "\"idProveedor\"", referencedColumnName = "id", nullable = false)
    private Proveedor idProveedor;

    @Transient
    @CsvBindByName(column = "id_articulo")
    private Integer idArticulo1;

    @Transient
    @CsvBindByName(column = "id_contrato")
    private Integer idContrato1;

    @Transient
    @CsvBindByName(column = "id_proveedor")
    private Integer idProveedor1;

    public Proveedor getIdProveedor() {
        return this.idProveedor;
    }

    public void setIdProveedor(Proveedor proveedor) {
        this.idProveedor = proveedor;
    }

    public Articulo getIdArticulo() {
        return this.idArticulo;
    }

    public void setIdArticulo(Articulo articulo) {
        this.idArticulo = articulo;
    }

    public Contrato getIdContrato() {
        return this.idContrato;
    }

    public void setIdContrato(Contrato contrato) {
        this.idContrato = contrato;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getNumeroOrdenContrato() {
        return numeroOrdenContrato;
    }

    public void setNumeroOrdenContrato(String numeroOrdenContrato) {
        this.numeroOrdenContrato = numeroOrdenContrato;
    }

    public Date getFechaCartaOferta() {
        return fechaCartaOferta;
    }

    public void setFechaCartaOferta(Date fechaCartaOferta) {
        this.fechaCartaOferta = fechaCartaOferta;
    }

    public Date getFechaRegistroOferta() {
        return fechaRegistroOferta;
    }

    public void setFechaRegistroOferta(Date fechaRegistroOferta) {
        this.fechaRegistroOferta = fechaRegistroOferta;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTipoDocumentoIdentidad() {
        return tipoDocumentoIdentidad;
    }

    public void setTipoDocumentoIdentidad(String tipoDocumentoIdentidad) {
        this.tipoDocumentoIdentidad = tipoDocumentoIdentidad;
    }

    public String getDocumentoIdentidad() {
        return documentoIdentidad;
    }

    public void setDocumentoIdentidad(String documentoIdentidad) {
        this.documentoIdentidad = documentoIdentidad;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public String getChasis() {
        return chasis;
    }

    public void setChasis(String chasis) {
        this.chasis = chasis;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Float getValorSinIva() {
        return valorSinIva;
    }

    public void setValorSinIva(Float valorSinIva) {
        this.valorSinIva = valorSinIva;
    }

    public String getBeneficiarioCheque() {
        return beneficiarioCheque;
    }

    public void setBeneficiarioCheque(String beneficiarioCheque) {
        this.beneficiarioCheque = beneficiarioCheque;
    }

    public Float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public Integer getIdArticulo1() {
        return idArticulo1;
    }

    public void setIdArticulo1(Integer idArticulo1) {
        this.idArticulo1 = idArticulo1;
    }

    public Integer getIdContrato1() {
        return idContrato1;
    }

    public void setIdContrato1(Integer idContrato1) {
        this.idContrato1 = idContrato1;
    }

    public Integer getIdProveedor1() {
        return idProveedor1;
    }

    public void setIdProveedor1(Integer idProveedor1) {
        this.idProveedor1 = idProveedor1;
    }

    public OrdenCompra setValoresDiferentes(OrdenCompra registroAntiguo, OrdenCompra registroAct) {
        if (registroAct.getFechaInicio() != null) {
            registroAntiguo.setFechaInicio(registroAct.getFechaInicio());
        }

        if (registroAct.getNumeroOrdenContrato() != null) {
            registroAntiguo.setNumeroOrdenContrato(registroAct.getNumeroOrdenContrato());
        }

        if (registroAct.getFechaCartaOferta() != null) {
            registroAntiguo.setFechaCartaOferta(registroAct.getFechaCartaOferta());
        }

        if (registroAct.getNombreCliente() != null) {
            registroAntiguo.setNombreCliente(registroAct.getNombreCliente());
        }

        if (registroAct.getCorreo() != null) {
            registroAntiguo.setCorreo(registroAct.getCorreo());
        }

        if (registroAct.getTelefono() != null) {
            registroAntiguo.setTelefono(registroAct.getTelefono());
        }

        if (registroAct.getTipoDocumentoIdentidad() != null) {
            registroAntiguo.setTipoDocumentoIdentidad(registroAct.getTipoDocumentoIdentidad());
        }

        if (registroAct.getDocumentoIdentidad() != null) {
            registroAntiguo.setDocumentoIdentidad(registroAct.getDocumentoIdentidad());
        }

        if (registroAct.getMarca() != null) {
            registroAntiguo.setMarca(registroAct.getMarca());
        }

        if (registroAct.getModelo() != null) {
            registroAntiguo.setModelo(registroAct.getModelo());
        }

        if (registroAct.getMotor() != null) {
            registroAntiguo.setMotor(registroAct.getMotor());
        }

        if (registroAct.getChasis() != null) {
            registroAntiguo.setChasis(registroAct.getChasis());
        }

        if (registroAct.getPlaca() != null) {
            registroAntiguo.setPlaca(registroAct.getPlaca());
        }

        if (registroAct.getColor() != null) {
            registroAntiguo.setColor(registroAct.getColor());
        }

        if (registroAct.getAnio() != null) {
            registroAntiguo.setAnio(registroAct.getAnio());
        }

        if (registroAct.getValorSinIva() != null) {
            registroAntiguo.setValorSinIva(registroAct.getValorSinIva());
        }

        if (registroAct.getBeneficiarioCheque() != null) {
            registroAntiguo.setBeneficiarioCheque(registroAct.getBeneficiarioCheque());
        }

        if (registroAct.getValorTotal() != null) {
            registroAntiguo.setValorTotal(registroAct.getValorTotal());
        }

        if (registroAct.getObservacion() != null) {
            registroAntiguo.setObservacion(registroAct.getObservacion());
        }

        if (registroAct.getTipoVehiculo() != null) {
            registroAntiguo.setTipoVehiculo(registroAct.getTipoVehiculo());
        }

        if (registroAct.getSisHabilitado() != null) {
            registroAntiguo.setSisHabilitado(registroAct.getSisHabilitado());
        }

        return registroAntiguo;
    }

}
