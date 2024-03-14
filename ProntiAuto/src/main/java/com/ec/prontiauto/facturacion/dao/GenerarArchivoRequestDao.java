package com.ec.prontiauto.facturacion.dao;



public class GenerarArchivoRequestDao {

    private String establecimientoEmpresa;
    private String puntoEmisionEmpresa;
    private int facNumero;
    private String rucEmpresa;
    private String rutaLogo;
    private String rutaArchivo;
    private String identificacionComprador;

    public String getEstablecimientoEmpresa() {
        return establecimientoEmpresa;
    }

    public void setEstablecimientoEmpresa(String establecimientoEmpresa) {
        this.establecimientoEmpresa = establecimientoEmpresa;
    }

    public String getPuntoEmisionEmpresa() {
        return puntoEmisionEmpresa;
    }

    public void setPuntoEmisionEmpresa(String puntoEmisionEmpresa) {
        this.puntoEmisionEmpresa = puntoEmisionEmpresa;
    }

    public int getFacNumero() {
        return facNumero;
    }

    public void setFacNumero(int facNumero) {
        this.facNumero = facNumero;
    }

    public String getRucEmpresa() {
        return rucEmpresa;
    }

    public void setRucEmpresa(String rucEmpresa) {
        this.rucEmpresa = rucEmpresa;
    }

    public String getRutaLogo() {
        return rutaLogo;
    }

    public void setRutaLogo(String rutaLogo) {
        this.rutaLogo = rutaLogo;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public String getIdentificacionComprador() {
        return identificacionComprador;
    }

    public void setIdentificacionComprador(String identificacionComprador) {
        this.identificacionComprador = identificacionComprador;
    }

    public GenerarArchivoRequestDao() {}

    public GenerarArchivoRequestDao(String establecimientoEmpresa, String puntoEmisionEmpresa, int facNumero, String rucEmpresa, String rutaLogo, String rutaArchivo, String identificacionComprador) {
        this.establecimientoEmpresa = establecimientoEmpresa;
        this.puntoEmisionEmpresa = puntoEmisionEmpresa;
        this.facNumero = facNumero;
        this.rucEmpresa = rucEmpresa;
        this.rutaLogo = rutaLogo;
        this.rutaArchivo = rutaArchivo;
        this.identificacionComprador = identificacionComprador;
    }
}