package com.ec.prontiauto.dao;

import java.sql.Date;

import com.ec.prontiauto.abstracts.AbstractResponseDao;

public class OrdenCompraResponseDao extends AbstractResponseDao {

    private Date fechaInicio;
    private String numeroOrdenContrato;
    private Date fechaCartaOferta;
    private Date fechaRegistroOferta;
    private String nombreCliente;
    private String correo;
    private String telefono;
    private String tipoDocumentoIdentidad;
    private String documentoIdentidad;
    private String marca;
    private String modelo;
    private String motor;
    private String chasis;
    private String placa;
    private String color; 
    private Integer anio;
    private Float valorSinIva;
    private String beneficiarioCheque;
    private Float valorTotal;
    private String observacion;
    private String tipoVehiculo;

    private ProveedorResponseDao idProveedor;
    private ContratoResponseDao idContrato;
    private ArticuloResponseDao idArticulo;


    public OrdenCompraResponseDao() {}


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

    public ProveedorResponseDao getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(ProveedorResponseDao idProveedor) {
        this.idProveedor = idProveedor;
    }

    public ContratoResponseDao getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(ContratoResponseDao idContrato) {
        this.idContrato = idContrato;
    }

    public ArticuloResponseDao getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(ArticuloResponseDao idArticulo) {
        this.idArticulo = idArticulo;
    }

}