package com.ec.prontiauto.validations;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.ec.prontiauto.entidad.CuentaContable;
import com.ec.prontiauto.entidad.PeriodoContable;
import com.ec.prontiauto.exception.ApiRequestException;

public class CuentaContableValidation {

    private EntityManager em;
    private CuentaContable entity;
    private PeriodoContable periodoContable;

    public CuentaContableValidation(EntityManager em, CuentaContable entity, Boolean isUpdate) {
        this.em = em;
        this.entity = entity;

        if (isUpdate) {
            initStateUpdate();
        } else {
            initState();
        }
    }

    private void initState() {
        this.getPeriodoContableActual();
        validarIdentificador();
        calculoNivelesIdentificador();

        entity.setIdPeriodoContable(periodoContable);
    }

    private void initStateUpdate() {
        if (entity.getNivel() != null && entity.getIdentificador() != null) {
            calculoNivelesIdentificador();
        }
    }

    private void getPeriodoContableActual() {
        try {
            Query query = this.em.createQuery("SELECT p FROM PeriodoContable p WHERE p.esPeriodoActual='A'");
            this.periodoContable = (PeriodoContable) query.getSingleResult();
        } catch (Exception e) {
            throw new ApiRequestException("No se encontró un periodo contable activo");
        }
    }

    private void validarIdentificador() {
        Integer identificador = entity.getIdentificador();

        if (identificador == null) {
            throw new ApiRequestException("El identificador no puede ser nulo");
        }

        Query query = this.em.createQuery("select t from CuentaContable t where identificador =:id");
        query.setParameter("id", identificador);

        if (query.getResultList().size() > 0) {
            throw new ApiRequestException("El identificador ya existe");
        }
    }

    private void calculoNivelesIdentificador() {
        /*
         * Cuando el usuario ingrese 1 en el campo de nivel entonces solo se le
         * permitirá ingresar un identificador de 1 carácter. Cuando sea 2 el
         * identificador será de 2 caracteres. Con nivel 3 el identificador de 3
         * caracteres.Con nivel 4 el identificador de 5 caracteres. y con nivel 5 el
         * identificador de 7 caracteres.
         */

        Integer nivel = entity.getNivel();
        Integer identificador = entity.getIdentificador();

        switch (nivel) {
            case 1:
                if (identificador.toString().length() != 1) {
                    throw new ApiRequestException(
                            "El identificador debe tener un solo caracter para una cuenta de nivel " + nivel);
                }
                break;
            case 2:
                if (identificador.toString().length() != 2) {
                    throw new ApiRequestException(
                            "El identificador debe tener dos caracteres para una cuenta de nivel " + nivel);
                }
                break;
            case 3:
                if (identificador.toString().length() != 3) {
                    throw new ApiRequestException(
                            "El identificador debe tener tres caracteres para una cuenta de nivel " + nivel);
                }
                break;
            case 4:
                if (identificador.toString().length() != 5) {
                    throw new ApiRequestException(
                            "El identificador debe tener cinco caracteres para una cuenta de nivel " + nivel);
                }
                break;
            case 5:
                if (identificador.toString().length() != 7) {
                    throw new ApiRequestException(
                            "El identificador debe tener siete caracteres para una cuenta de nivel " + nivel);
                }
                break;
            default:
                throw new ApiRequestException("El nivel debe ser entre 1 y 5");
        }

    }

    public CuentaContable getEntity() {
        return this.entity;
    }

}
