package com.ec.prontiauto.validations;

import java.sql.Date;

import com.ec.prontiauto.entidad.CargaFamiliar;
import com.ec.prontiauto.exception.ApiRequestException;

import java.time.LocalDate;

public class CargaFamiliarValidation {
    private CargaFamiliar entity;

    public CargaFamiliarValidation(CargaFamiliar entity) {
        this.entity = entity;

        init();
    }

    private void init() {
        valEdad();
        valDiscapacidad();
        valRelaciones();
    }

    private void valRelaciones() {
        if (entity.getIdTrabajador().getId() == null) {
            throw new ApiRequestException("Debes especificar el id del trabajador");
        }
    }

    private void valEdad() {
        /*
         * Cuando el usuario ingrese la fecha de nacimiento
         * Entonces el sistema calculará todos los años la edad.
         */
        Date fecha_nacimiento = entity.getFechaNacimiento();
        Integer edad = 0;

        if (fecha_nacimiento != null) {
            LocalDate customDate = LocalDate.parse(fecha_nacimiento.toString());
            LocalDate now = LocalDate.now();

            if (customDate.isAfter(now)) {
                throw new ApiRequestException("La fecha de nacimiento debe ser menor a la fecha actual");
            }

            edad = now.getYear() - customDate.getYear();
        }

        entity.setEdad(edad);
    }

    private void valDiscapacidad() {
        /*
         * Cuando el usuario Seleccione un tipo de discapacidad
         * Entonces se habilita el campo para seleccionar si aplica o no utilidades
         */

        Boolean tieneDiscapacidad = entity.getTipoDiscapacidad() != null;

        if (tieneDiscapacidad && entity.getAplicaUtilidad() == null) {
            throw new ApiRequestException(
                    "Si el usuario tiene algun tipo de discapacidad se debe registrar si aplica o no utilidades");
        }
    }

    public CargaFamiliar getEntity() {
        return this.entity;
    }
}
