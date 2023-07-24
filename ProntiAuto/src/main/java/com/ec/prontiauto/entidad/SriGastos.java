package com.ec.prontiauto.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "sri_gastos")
public class SriGastos extends AbstractEntities {

    @Column(name = "anio")
    @CsvBindByName(column = "anio")
    private Integer anio;

    @Column(name = "\"gastoVivienda\"", precision = 10, scale = 2)
    @CsvBindByName(column = "gasto_vivienda")
    private Float gastoVivienda;

    @Column(name = "\"gastoEducacion\"", precision = 10, scale = 2)
    @CsvBindByName(column = "gasto_educacion")
    private Float gastoEducacion;

    @Column(name = "\"gastoSalud\"", precision = 10, scale = 2)
    @CsvBindByName(column = "gasto_salud")
    private Float gastoSalud;

    @Column(name = "\"gastoVestido\"", precision = 10, scale = 2)
    @CsvBindByName(column = "gasto_vestido")
    private Float gastoVestido;

    @Column(name = "\"gastoAlimento\"", precision = 10, scale = 2)
    @CsvBindByName(column = "gasto_alimento")
    private Float gastoAlimento;

    @Column(name = "\"gastoTurismo\"", precision = 10, scale = 2)
    @CsvBindByName(column = "gasto_turismo")
    private Float gastoTurismo;

    @Column(name = "\"gastoDiscapacidad\"", precision = 10, scale = 2)
    @CsvBindByName(column = "gasto_discapacidad")
    private Float gastoDiscapacidad;

    @Column(name = "\"gastoTerceraEdad\"", precision = 10, scale = 2)
    @CsvBindByName(column = "gasto_tercera_edad")
    private Float gastoTerceraEdad;

    @Column(name = "\"totalGastos\"", precision = 10, scale = 2)
    @CsvBindByName(column = "total_gastos")
    private Float totalGastos;

    @ManyToOne
    @JoinColumn(name = "\"idTrabajador\"", referencedColumnName = "id", nullable = false)
    private Trabajador idTrabajador;

    @Transient
    @CsvBindByName(column = "id_trabajador")
    private Integer idTrabajador1;

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

    public Trabajador getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(Trabajador idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public Integer getIdTrabajador1() {
        return idTrabajador1;
    }

    public void setIdTrabajador1(Integer idTrabajador1) {
        this.idTrabajador1 = idTrabajador1;
    }

    public SriGastos setValoresDiferentes(SriGastos registroAntiguo, SriGastos registroActualizar) {
        if (registroActualizar.getAnio() != null) {
            registroAntiguo.setAnio(registroActualizar.getAnio());
        }

        if (registroActualizar.getGastoVivienda() != null) {
            registroAntiguo.setGastoVivienda(registroActualizar.getGastoVivienda());
        }

        if (registroActualizar.getGastoEducacion() != null) {
            registroAntiguo.setGastoEducacion(registroActualizar.getGastoEducacion());
        }

        if (registroActualizar.getGastoSalud() != null) {
            registroAntiguo.setGastoSalud(registroActualizar.getGastoSalud());
        }

        if (registroActualizar.getGastoVestido() != null) {
            registroAntiguo.setGastoVestido(registroActualizar.getGastoVestido());
        }

        if (registroActualizar.getGastoAlimento() != null) {
            registroAntiguo.setGastoAlimento(registroActualizar.getGastoAlimento());
        }

        if (registroActualizar.getGastoTurismo() != null) {
            registroAntiguo.setGastoTurismo(registroActualizar.getGastoTurismo());
        }

        if (registroActualizar.getGastoDiscapacidad() != null) {
            registroAntiguo.setGastoDiscapacidad(registroActualizar.getGastoDiscapacidad());
        }

        if (registroActualizar.getGastoTerceraEdad() != null) {
            registroAntiguo.setGastoTerceraEdad(registroActualizar.getGastoTerceraEdad());
        }

        if (registroActualizar.getTotalGastos() != null) {
            registroAntiguo.setTotalGastos(registroActualizar.getTotalGastos());
        }

        if (registroActualizar.getIdTrabajador() != null && registroActualizar.getIdTrabajador().getId() != null) {
            registroAntiguo.setIdTrabajador(registroActualizar.getIdTrabajador());
        }

        if (registroActualizar.getSisHabilitado() != null) {
            registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
        }

        return registroAntiguo;

    }

}
