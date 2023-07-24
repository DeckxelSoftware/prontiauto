package com.ec.prontiauto.validations;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.ec.prontiauto.entidad.PeriodoContable;

public class PeriodoContableValidation {
    private EntityManager em;

    public PeriodoContableValidation(EntityManager em) {
        this.em = em;
    }

    public void actualizarPeriodoContableActivo() {

        Query query = this.em.createQuery("SELECT p FROM PeriodoContable p WHERE p.esPeriodoActual='A'");

        PeriodoContable periodoContableActual = (PeriodoContable) query.getSingleResult();
        periodoContableActual.setEsPeriodoActual("I");
        this.em.merge(periodoContableActual);
    }
}
