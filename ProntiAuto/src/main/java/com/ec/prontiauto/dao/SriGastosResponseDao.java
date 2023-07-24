package com.ec.prontiauto.dao;

import com.ec.prontiauto.abstracts.AbstractResponseDao;

public class SriGastosResponseDao extends AbstractResponseDao {
    private Integer anio;
    private Float gastoVivienda;
    private Float gastoEducacion;
    private Float gastoSalud;
    private Float gastoVestido;
    private Float gastoAlimento;
    private Float gastoTurismo;
    private Float gastoDiscapacidad;
    private Float gastoTerceraEdad;
    private Float totalGastos;

    private TrabajadorResponseDao idTrabajador;


    public SriGastosResponseDao(){}


    public Integer getAnio() {
        return anio;
    }


    public void setAnio(Integer anio) {
        this.anio = anio;
    }


    public Float getGastoVivienda() {
        return gastoVivienda;
    }


    public void setGastoVivienda(Float gastoVivienda) {
        this.gastoVivienda = gastoVivienda;
    }


    public Float getGastoEducacion() {
        return gastoEducacion;
    }


    public void setGastoEducacion(Float gastoEducacion) {
        this.gastoEducacion = gastoEducacion;
    }


    public Float getGastoSalud() {
        return gastoSalud;
    }


    public void setGastoSalud(Float gastoSalud) {
        this.gastoSalud = gastoSalud;
    }


    public Float getGastoVestido() {
        return gastoVestido;
    }


    public void setGastoVestido(Float gastoVestido) {
        this.gastoVestido = gastoVestido;
    }


    public Float getGastoAlimento() {
        return gastoAlimento;
    }


    public void setGastoAlimento(Float gastoAlimento) {
        this.gastoAlimento = gastoAlimento;
    }


    public Float getGastoTurismo() {
        return gastoTurismo;
    }


    public void setGastoTurismo(Float gastoTurismo) {
        this.gastoTurismo = gastoTurismo;
    }


    public Float getGastoDiscapacidad() {
        return gastoDiscapacidad;
    }


    public void setGastoDiscapacidad(Float gastoDiscapacidad) {
        this.gastoDiscapacidad = gastoDiscapacidad;
    }


    public Float getGastoTerceraEdad() {
        return gastoTerceraEdad;
    }


    public void setGastoTerceraEdad(Float gastoTerceraEdad) {
        this.gastoTerceraEdad = gastoTerceraEdad;
    }


    public Float getTotalGastos() {
        return totalGastos;
    }


    public void setTotalGastos(Float totalGastos) {
        this.totalGastos = totalGastos;
    }


    public TrabajadorResponseDao getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(TrabajadorResponseDao idTrabajador) {
        this.idTrabajador = idTrabajador;
    }
}
