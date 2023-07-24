package com.ec.prontiauto.validations;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.ec.prontiauto.entidad.ConfiguracionGeneral;
import com.ec.prontiauto.entidad.HistorialLaboral;
import com.ec.prontiauto.entidad.Trabajador;
import com.ec.prontiauto.exception.ApiRequestException;

public class HistorialLabloralValidation {
    EntityManager em;
    HistorialLaboral entity;
    Trabajador trabajador;
    ConfiguracionGeneral configuracionGeneral;

    public HistorialLabloralValidation(EntityManager em, HistorialLaboral entity, Boolean isUpdate) {
        this.em = em;
        this.entity = entity;

        getTrabajador();
        getConfiguracionGeneral();

        if( isUpdate ){
            initUpdate();
        }else {
            initCreate();
        }
    }

    private void initUpdate(){
        valSueldo();
    }
    private void initCreate() {
        valSueldo();
        valDuracion();
    }

    private void getTrabajador() {
        try {

            if (entity.getIdTrabajador() == null) {
                throw new ApiRequestException("El idTrabajador es requerido");
            }

            Query query = em.createQuery("select t from Trabajador t where t.id=:id");
            query.setParameter("id", entity.getIdTrabajador().getId());
            trabajador = (Trabajador) query.getSingleResult();
        } catch (Exception e) {
            throw new ApiRequestException("No se encontro el trabajador");
        }
    }

    private void getConfiguracionGeneral() {
        try {
            Query query = this.em.createQuery("select c from ConfiguracionGeneral c");
            this.configuracionGeneral = (ConfiguracionGeneral) query.getSingleResult();
        } catch (Exception e) {
            throw new ApiRequestException("No se encontro la configuracion general");
        }
    }

    private void valSueldo() {
        /*
         * Requerimiento # 361
         *
         * Cuando se actualice el sueldo del historial laboral del trabajador
         * Entonces se actualiza el sueldo del trabajador con el nuevo sueldo del
         * historial laboral.
         *
         * Y se debe validar que el sueldo no sea inferior al sueldo básico execto en
         * pasantias y en tiempo parcial.
         *
         *    * actualización: Error #361
         *    * La validacion solo se debe realizar para el tipo de contrato indefinido
         *
         * Cuando el usuario seleccione tiempo parcial
         * Entonces el sueldo será igual a sueldo básico * factor parcial
         * 
         * Cuando seleccione pasante
         * Entonces el sueldo va a ser igual a sueldo basico * ⅓
         *
         */

        Float sueldo = entity.getSueldo();
        Float sueldoBasico = configuracionGeneral.getSueldoBasico().floatValue();
        Boolean isPasante = trabajador.getPasante() != null && trabajador.getPasante().equals("S");
        Boolean isTiempoParcial = trabajador.getTiempoParcial() != null && trabajador.getTiempoParcial().equals("S");
        Float factorParcial = trabajador.getFactorParcial();

        if (sueldo != null) {
            if( entity.getTipoContrato().toLowerCase().equals("indefinido") ){
                if (sueldo < sueldoBasico && !(isPasante || isTiempoParcial)) {
                    throw new ApiRequestException("El sueldo no puede ser inferior al sueldo básico");
                }
            }

            if (isTiempoParcial) {
                if (factorParcial == null) {
                    throw new ApiRequestException(
                            "No se ha definido el factor parcial para el trabajador seleccionado");
                }
                sueldo = sueldoBasico * factorParcial;
            }

            if (isPasante) {
                sueldo = sueldoBasico * 1 / 3;
            }

            trabajador.setSueldo(sueldo);
            entity.setSueldo(sueldo);

            em.merge(trabajador);
        }
    }

    private void valDuracion() {
        /*
         * La duración del trabajador va a ser igual a la fecha de ingreso - fecha
         * actual
         */

        Date fechaIngreso = trabajador.getFechaIngreso();

        if (fechaIngreso == null) {
            throw new ApiRequestException("La fecha de ingreso del trabajador no ha sido definida");
        }

        LocalDate fechaInicio = LocalDate.parse(fechaIngreso.toString());
        LocalDate now = LocalDate.now();
        Long noOfDaysBetween = ChronoUnit.DAYS.between(fechaInicio, now);

        String duracion = noOfDaysBetween + " días";

        entity.setDuracion(duracion);

    }

    public HistorialLaboral getEntity() {
        entity.setIdTrabajador(trabajador);
        return this.entity;
    }
}
