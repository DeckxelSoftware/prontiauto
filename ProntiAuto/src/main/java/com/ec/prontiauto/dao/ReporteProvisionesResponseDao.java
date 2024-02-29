package com.ec.prontiauto.dao;

import java.sql.Date;

public class ReporteProvisionesResponseDao {

    private String nombres;
    private String apellidos;
    private Date fechaIngreso;
    private Float provDecimoCuarto;
    private Float provDecimoTercero;
    private String fondoReservaIess;
    private String pagoFondosReservaMes;
    private Float pagoFondoReservaMes;
    private Float provFondosReserva;
    private Float provVacaciones;
    private Float provAportePatronal;

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getFondoReservaIess() {
        return fondoReservaIess;
    }

    public void setFondoReservaIess(String fondoReservaIess) {
        this.fondoReservaIess = fondoReservaIess;
    }

    public String getPagoFondosReservaMes() {
        return pagoFondosReservaMes;
    }

    public void setPagoFondosReservaMes(String pagoFondosReservaMes) {
        this.pagoFondosReservaMes = pagoFondosReservaMes;
    }


    public Float getProvDecimoCuarto() {
        return provDecimoCuarto;
    }

    public void setProvDecimoCuarto(Float provDecimoCuarto) {
        this.provDecimoCuarto = provDecimoCuarto;
    }

    public Float getProvDecimoTercero() {
        return provDecimoTercero;
    }

    public void setProvDecimoTercero(Float provDecimoTercero) {
        this.provDecimoTercero = provDecimoTercero;
    }

    public Float getPagoFondoReservaMes() {
        return pagoFondoReservaMes;
    }

    public void setPagoFondoReservaMes(Float pagoFondoReservaMes) {
        this.pagoFondoReservaMes = pagoFondoReservaMes;
    }

    public Float getProvFondosReserva() {
        return provFondosReserva;
    }

    public void setProvFondosReserva(Float provFondosReserva) {
        this.provFondosReserva = provFondosReserva;
    }

    public Float getProvVacaciones() {
        return provVacaciones;
    }

    public void setProvVacaciones(Float provVacaciones) {
        this.provVacaciones = provVacaciones;
    }

    public Float getProvAportePatronal() {
        return provAportePatronal;
    }

    public void setProvAportePatronal(Float provAportePatronal) {
        this.provAportePatronal = provAportePatronal;
    }

    public ReporteProvisionesResponseDao(){}
    public ReporteProvisionesResponseDao(Object[] response){
        this.nombres = (String) response[0];
        this.apellidos = (String) response[1];
        this.fechaIngreso = (Date) response[2];
        this.provDecimoCuarto = (Float) response[3];
        this.provDecimoTercero = (Float) response[4];
        this.fondoReservaIess = (String) response[5];
        this.pagoFondosReservaMes = (String) response[6];
        this.pagoFondoReservaMes = (Float) response[7];
        this.provFondosReserva = (Float) response[8];
        this.provVacaciones = (Float) response[9];
        this.provAportePatronal = (Float) response[10];
    }
}
