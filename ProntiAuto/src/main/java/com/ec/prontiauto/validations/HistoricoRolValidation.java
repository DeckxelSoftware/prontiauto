package com.ec.prontiauto.validations;

import com.ec.prontiauto.entidad.*;
import com.ec.prontiauto.exception.ApiRequestException;
import com.ec.prontiauto.utils.MailerClass;

import javax.persistence.EntityManager;
import java.rmi.RemoteException;
import java.util.List;

public class HistoricoRolValidation {

    private HistoricoRol entity;

    private PeriodoLaboral periodoLaboral;
    private EntityManager em;

    public HistoricoRolValidation (EntityManager em, HistoricoRol entity) {
        this.em = em;
        this.entity = entity;
        validateRelationEntities();
        this.periodoLaboral = getPeriodoLaboralByRol();

        init();
    }

    private void init(){

        /* escenario cerrar rol */
        // sendRolToEmail();
        deleteNovedades();
        resetRolPago();

    }

    private void validateRelationEntities(){
        if( entity.getIdRolPago() == null || entity.getIdRolPago().getId() == null ){
            throw new ApiRequestException("El idRolPago no puede ser nulo");
        }
    }

    private PeriodoLaboral getPeriodoLaboralByRol(){
        PeriodoLaboral periodoLaboral = null;
        try {
            String queryText = "SELECT e FROM PeriodoLaboral e " +
                    "INNER JOIN RolPago r on r.idPeriodoLaboral=e.id " +
                    "INNER JOIN PagosDos p on p.idPeriodoLaboral=e.id " +
                    "INNER JOIN Trabajador t on p.idTrabajador=t.id " +
                    "WHERE r.id = :idRolPago";

            List<PeriodoLaboral> collection = (List<PeriodoLaboral>) em.createQuery(queryText)
                    .setParameter("idRolPago",entity.getIdRolPago().getId())
                    .getResultList();
            if(collection.size() > 0){
                periodoLaboral = collection.get(0);
            }
        } catch (Exception e) {
            System.out.printf("\n%s\n",e.getMessage());
        }
        return periodoLaboral;
    }

    private List<DetalleNovedadRolPago> getNovedadesByTrabajador(Integer idTrabajador){
        List<DetalleNovedadRolPago> novedad = null;

        if(idTrabajador == null) return novedad;

        try {
            String queryText = "SELECT e FROM DetalleNovedadRolPago e " +
                    "INNER JOIN Trabajador t on e.idTrabajador=t.id " +
                    "WHERE t.id = :idTrabajador";

            novedad = (List<DetalleNovedadRolPago>) em.createQuery(queryText)
                    .setParameter("idTrabajador",idTrabajador)
                    .getResultList();
        } catch (Exception e) {
            System.out.printf("\n%s\n",e.getMessage());
        }
        return novedad;
    }

    private void sendRolToEmail(){
        /*
        * se envía el rol al correo del trabajador
        * */

        try {
            String[] files = {};
            String textHTML = "<body>hola</body>";

            MailerClass mailer = new MailerClass();
            Boolean isSend = mailer.sendMailSimple3("jbpaig@gmail.com",files,"asunto",textHTML);

            System.out.println("correo enviado");
        }catch (Exception e){
            System.out.println("\n\n" + e.getMessage() + "\n\n");
        }
    }

    private void resetRolPago(){
        /*  si en el rol se pagó la cuota del préstamo.
         *  Entonces a la tabla préstamo en el campo total pagado
         *  pasa el valor pagado que se encuentra en el rol en el campo prestamos_empresa
         *  total_pagado = total_pagado + prestamos_empresa
        */

        RolPago rolPago = periodoLaboral.getRolPagoCollection().get(0);

        if(rolPago != null){
            Boolean isPagoCuotaPrestamo = rolPago.getPrestamosEmpresa() != null || rolPago.getPrestamosEmpresa() <= 0;

            if(isPagoCuotaPrestamo) {

            }
        }
    }

    private void deleteNovedades(){
        /* Las novedades que tenga registrado el trabajador son eliminadas. */

        Trabajador trabajador = periodoLaboral.getPagosDosCollection().get(0).getIdTrabajador();
        List<DetalleNovedadRolPago> novedades = getNovedadesByTrabajador(trabajador.getId());

        if(novedades.size() > 0){
            int i = 0;
            while (i < novedades.size()){
                DetalleNovedadRolPago novedad = novedades.get(i);
                em.remove(novedad);
                i++;
            }
        }
    }

    public HistoricoRol getEntity(){
        return entity;
    }

}
