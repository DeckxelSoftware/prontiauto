package com.ec.prontiauto.validations;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.EntityManager;

import com.ec.prontiauto.entidad.Trabajador;
import com.ec.prontiauto.exception.ApiRequestException;

public class TrabajadorValidation {

    Trabajador entity;
    EntityManager em;

    public TrabajadorValidation(EntityManager em, Trabajador entity, Boolean isUpdate) {
        this.entity = entity;
        this.em = em;

        if (isUpdate) {
            initUpdate();
        } else {
            initPost();
        }
    }

    private void initUpdate() {

    }

    private void initPost() {
        valFechaInicio();
    }


    private void valFechaInicio() {
        /*
         * Se debe validar que la fecha de inicio del trabajador no sea menor a la
         * fecha actual
         */
        Date fechaIngreso = entity.getFechaIngreso();
        LocalDate fechaInicio = LocalDate.parse(fechaIngreso.toString());
        LocalDate now = LocalDate.now();

        if (fechaInicio != null) {
            if (fechaInicio.isBefore(now)) {
                throw new ApiRequestException("La fecha de ingreso no puede ser menor a la fecha actual");
            }
        }
    }

    public Trabajador getEntity() {
        return entity;
    }
}
