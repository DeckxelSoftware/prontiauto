package com.ec.prontiauto.validations;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.ec.prontiauto.entidad.*;
import com.ec.prontiauto.helper.ListMapper;
import com.ec.prontiauto.repositoryImpl.PagosDosRepositoryImpl;

public class PagosDosVal {


    private EntityManager em;
    PagosDosRepositoryImpl repository;

    public PagosDosVal(EntityManager em,
                              PagosDosRepositoryImpl repository) {
        this.em = em;
        this.repository = repository;
    }

    private Trabajador getTrabajador(Integer idTrabajador) {
        Trabajador trabajador = null;
        try {
            if (idTrabajador == null) {
                throw new ApiException("El idTrabajador es requerido");
            }

            String queryText = "SELECT t FROM Trabajador t WHERE t.id = :idTrabajador";
            Query query = em.createQuery(queryText).setParameter("idTrabajador", idTrabajador);
            trabajador = (Trabajador) query.getSingleResult();
        } catch (Exception e) {
            System.out.printf("\n%s\n%s", "-----------", e.getMessage());
            throw new ApiException("No se encontro un trabajador con el id: " + idTrabajador);
        }

        return trabajador;
    }

    private PeriodoLaboral getPeriodoLaboral(Integer idPeriodo) {
        PeriodoLaboral periodoLaboral = null;
        try {
            if (idPeriodo == null) {
                throw new ApiException("El idPeriodoLaboral es requerido");
            }

            String queryText = "SELECT p FROM PeriodoLaboral p WHERE p.id = :idPeriodo";
            Query query = em.createQuery(queryText).setParameter("idPeriodo", idPeriodo);
            periodoLaboral = (PeriodoLaboral) query.getSingleResult();
        } catch (Exception e) {
            System.out.printf("\n%s\n%s", "-----------", e.getMessage());
            throw new ApiException("No se encontro un periodo laboral con el id: " + idPeriodo);
        }

        return periodoLaboral;
    }

    private List<PeriodoLaboral> getPeriodoLaboralCollectionByAnio(Integer anioPeriodoAct, Integer idTrabajador) {
        List<PeriodoLaboral> periodoLaboralCollection = null;
        try {
            String queryText = "SELECT pl FROM PeriodoLaboral pl " +
                    "INNER JOIN RolPago rp ON rp.idPeriodoLaboral=pl.id " +
                    "INNER JOIN PagosDos p2 ON p2.idPeriodoLaboral=rp.idPeriodoLaboral " +
                    "where p2.idTrabajador=" + idTrabajador + " " +
                    "and pl.anio in(:anioAct, :anioAnt) ";

            Query query = em.createQuery(queryText);
            query.setParameter("anioAct", anioPeriodoAct);
            query.setParameter("anioAnt", anioPeriodoAct - 1);

            periodoLaboralCollection = (List<PeriodoLaboral>) query.getResultList();

        } catch (Exception e) {
            System.out.printf("\n%s\n%s\n", "-----------", e.getMessage());
            throw new ApiException(e.getMessage());
        }
        return periodoLaboralCollection;
    }

    private PeriodoLaboral getPeriodoLaboralDic(Integer anioPeriodoAct){
        PeriodoLaboral periodoLaboral = null;
        try {

            String queryText = "SELECT pl from PeriodoLaboral pl " +
                    "INNER JOIN RolPago rp ON rp.idPeriodoLaboral=pl.id " +
                    "where pl.mes='DIC' and pl.anio =:anioPeriodoAct";

            Query query = em.createQuery(queryText).setParameter("anioPeriodoAct", anioPeriodoAct);
            periodoLaboral = (PeriodoLaboral) query.getSingleResult();
        }catch (Exception e){
            System.out.printf("\n%s\n%s", "-----------", e.getMessage());
            throw new ApiException("No existe un periodo laboral y rol pago para el mes DIC del año "+ anioPeriodoAct);
        }
        return periodoLaboral;
    }
    private void setProvDecimoTerceroRolPago(RolPago rolPago, Float valorMes){
        /*
         *  en el rol pago en el campo prov_decimo_tercero se almacena
         * el valor del mes correspondiente/12
         * */

        rolPago.setProvDecimoTercero(valorMes/12);
        em.merge(rolPago);
    }

    public void decimoTerceroAnual(Integer idTrabajador, Integer idPeriodoLaboral, PagosUno entityPagosUno) {

        /*
         * Requerimiento: #313
         *
         * Cuando el trabajador tiene si en décimos año
         * Y si el usuario da clic en calcular
         * se envia nombre, fecha_ultimo_pago, fecha_inicio, fecha_fin, periodo
         * se verifica si el trabajador ya tiene un año en la empresa
         *
         * Y si la duración es menor a un año
         * Entonces los días laborados al año serán igual a:
         * dias_laborados_al_anio = fecha_ingreso - fecha_fin
         *
         * Y si la duración es mayor a un año
         * Entonces los días laborados al año serán igual a:
         * dias_laborados_al_anio = fecha_inicio - fecha_fin
         *
         */

        Trabajador trabajador = getTrabajador(idTrabajador);
        PeriodoLaboral periodoLaboral = getPeriodoLaboral(idPeriodoLaboral);
        PagosDos pagosDos = new PagosDos();

        Boolean hashDecimosAnio = trabajador.getDecimosAnio() != null && trabajador.getDecimosAnio().equals("S");

        if (hashDecimosAnio) {
            LocalDate fechaIngreso = LocalDate.parse(trabajador.getFechaIngreso().toString());
            LocalDate now = LocalDate.now();
            Boolean hashUnoYearAtLeast = (now.getYear() - fechaIngreso.getYear()) >= 1;

            LocalDate fechaInicio = LocalDate.parse(entityPagosUno.getFechaInicio().toString());
            LocalDate fechaFin = LocalDate.parse(entityPagosUno.getFechaFin().toString());
            Integer diasLaboradosAnio = 0;

            if (hashUnoYearAtLeast) {
                diasLaboradosAnio = fechaIngreso.getDayOfYear() - fechaFin.getDayOfYear();

            } else {
                diasLaboradosAnio = fechaInicio.getDayOfYear() - fechaFin.getDayOfYear();
            }

            pagosDos.setDiasLaboradosAlAnio(diasLaboradosAnio);

            /*
             * el valor mes 1 va a ser igual al total de ingresos del mes de diciembre del
             * año anterior.
             * el valor mes 2 va a ser igual al total de ingresos del mes de enero del año actual.
             * ...
             * el valor mes 12 va a ser igual al total de ingresos del mes de noviembre del año actual.
             *
             */
            Integer AnioPeriodoDao = periodoLaboral.getAnio();
            List<PeriodoLaboral> periodoLaboralCollection = getPeriodoLaboralCollectionByAnio(AnioPeriodoDao,
                    trabajador.getId());

            String Mes = null;
            Integer AnioPeriodo = null;
            Float totalIngresos = null;
            Float totalIngresosPeriodoAct = 0f;
            Integer contMestPeriodo = 0;
            RolPago rolPago = null;



            for (int i = 0; i < periodoLaboralCollection.size(); i++) {
                Mes = periodoLaboralCollection.get(i).getMes();
                AnioPeriodo = periodoLaboralCollection.get(i).getAnio();
                totalIngresos = periodoLaboralCollection.get(i).getRolPagoCollection().get(0).getTotalIngresos();


                if(Mes.equals("DIC") &&( AnioPeriodo == AnioPeriodoDao - 1)){
                    pagosDos.setValorMes1(totalIngresos);
                    totalIngresosPeriodoAct += totalIngresos;
                    contMestPeriodo++;

                    rolPago = periodoLaboralCollection.get(i).getRolPagoCollection().get(0);
                    setProvDecimoTerceroRolPago(rolPago, totalIngresos);
                }

                if(Mes.equals("ENE") && (Objects.equals(AnioPeriodo, AnioPeriodoDao))){
                    pagosDos.setValorMes2(totalIngresos);
                    totalIngresosPeriodoAct += totalIngresos;
                    contMestPeriodo++;

                    rolPago = periodoLaboralCollection.get(i).getRolPagoCollection().get(0);
                    setProvDecimoTerceroRolPago(rolPago, totalIngresos);
                }

                if(Mes.equals("FEB") && (Objects.equals(AnioPeriodo, AnioPeriodoDao))){
                    pagosDos.setValorMes3(totalIngresos);
                    totalIngresosPeriodoAct += totalIngresos;
                    contMestPeriodo++;

                    rolPago = periodoLaboralCollection.get(i).getRolPagoCollection().get(0);
                    setProvDecimoTerceroRolPago(rolPago, totalIngresos);
                }

                if(Mes.equals("MAR") && (Objects.equals(AnioPeriodo, AnioPeriodoDao))){
                    pagosDos.setValorMes4(totalIngresos);
                    totalIngresosPeriodoAct += totalIngresos;
                    contMestPeriodo++;

                    rolPago = periodoLaboralCollection.get(i).getRolPagoCollection().get(0);
                    setProvDecimoTerceroRolPago(rolPago, totalIngresos);
                }

                if(Mes.equals("ABR") && (Objects.equals(AnioPeriodo, AnioPeriodoDao))){
                    pagosDos.setValorMes5(totalIngresos);
                    totalIngresosPeriodoAct += totalIngresos;
                    contMestPeriodo++;

                    rolPago = periodoLaboralCollection.get(i).getRolPagoCollection().get(0);
                    setProvDecimoTerceroRolPago(rolPago, totalIngresos);
                }

                if(Mes.equals("MAY") && (Objects.equals(AnioPeriodo, AnioPeriodoDao))){
                    pagosDos.setValorMes6(totalIngresos);
                    totalIngresosPeriodoAct += totalIngresos;
                    contMestPeriodo++;

                    rolPago = periodoLaboralCollection.get(i).getRolPagoCollection().get(0);
                    setProvDecimoTerceroRolPago(rolPago, totalIngresos);
                }

                if(Mes.equals("JUN") && (Objects.equals(AnioPeriodo, AnioPeriodoDao))){
                    pagosDos.setValorMes7(totalIngresos);
                    totalIngresosPeriodoAct += totalIngresos;
                    contMestPeriodo++;

                    rolPago = periodoLaboralCollection.get(i).getRolPagoCollection().get(0);
                    setProvDecimoTerceroRolPago(rolPago, totalIngresos);
                }

                if(Mes.equals("JUL") && (Objects.equals(AnioPeriodo, AnioPeriodoDao))){
                    pagosDos.setValorMes8(totalIngresos);
                    totalIngresosPeriodoAct += totalIngresos;
                    contMestPeriodo++;

                    rolPago = periodoLaboralCollection.get(i).getRolPagoCollection().get(0);
                    setProvDecimoTerceroRolPago(rolPago, totalIngresos);
                }

                if(Mes.equals("AGO") && (Objects.equals(AnioPeriodo, AnioPeriodoDao))){
                    pagosDos.setValorMes9(totalIngresos);
                    totalIngresosPeriodoAct += totalIngresos;
                    contMestPeriodo++;

                    rolPago = periodoLaboralCollection.get(i).getRolPagoCollection().get(0);
                    setProvDecimoTerceroRolPago(rolPago, totalIngresos);
                }

                if(Mes.equals("SEP") && (Objects.equals(AnioPeriodo, AnioPeriodoDao))){
                    pagosDos.setValorMes10(totalIngresos);
                    totalIngresosPeriodoAct += totalIngresos;
                    contMestPeriodo++;

                    rolPago = periodoLaboralCollection.get(i).getRolPagoCollection().get(0);
                    setProvDecimoTerceroRolPago(rolPago, totalIngresos);
                }

                if(Mes.equals("OCT") && (Objects.equals(AnioPeriodo, AnioPeriodoDao))){
                    pagosDos.setValorMes11(totalIngresos);
                    totalIngresosPeriodoAct += totalIngresos;
                    contMestPeriodo++;
                    setProvDecimoTerceroRolPago(rolPago, totalIngresos);
                }

                if(Mes.equals("NOV") && (Objects.equals(AnioPeriodo, AnioPeriodoDao))){
                    pagosDos.setValorMes12(totalIngresos);
                    totalIngresosPeriodoAct += totalIngresos;
                    contMestPeriodo++;
                    setProvDecimoTerceroRolPago(rolPago, totalIngresos);
                }
            }

            pagosDos.setTotalIngresos(totalIngresosPeriodoAct);


            /*
             *    si el empleado tiene préstamos de la empresa a descontar con el décimo tercero
             *    Entonces el valor prestamos empresa será igual al valor de la cuota del préstamo
             *    prestamos_empresa = valor_cuota de la tabla abono_prestamo
             */

            List<Prestamo> prestamos = trabajador.getPrestamoCollection();
            String modalidadPrestamo = null;
            String statePrestamo = null;
            Boolean hashPrestamosWithDecimoTercero = false;
            Float valorCuota = 0.0f;

            for (int i = 0; i < prestamos.size(); i++) {
                modalidadPrestamo = prestamos.get(i).getModalidadDescuento();
                statePrestamo = prestamos.get(i).getEstado();
                hashPrestamosWithDecimoTercero = statePrestamo.equals("PNT") && modalidadPrestamo.equals("DCT");

                if(hashPrestamosWithDecimoTercero){
                    valorCuota = prestamos.get(i).getAbonoPrestamoCollection().get(0).getValorCuota().floatValue();
                    break;
                }
            }

            if(hashPrestamosWithDecimoTercero){
                pagosDos.setPrestamosEmpresa(valorCuota);

                /*
                 *   el valor total egresos va a ser igual a el valor del préstamo empresa.
                 *   total_egresos = prestamos_empresa
                 */

                pagosDos.setTotalEgresos(pagosDos.getPrestamosEmpresa());

                /*
                 * el valor a pagar va a ser igual al total ingresos/12 - total egresos
                 * valor_a_pagar = total_ingresos/12 - total_egresos
                 */

                Float valorAPagar = 0.0f;
                if(pagosDos.getTotalIngresos() != null && pagosDos.getTotalEgresos() != null){
                    valorAPagar = pagosDos.getTotalIngresos()/12 - pagosDos.getTotalEgresos();
                }

                pagosDos.setValorAPagar(valorAPagar);
            } else {
                /*
                 *  si el empleado no tiene préstamos a descontar con el décimo tercero.
                 *  el valor prestamos empresa será igual a 0
                 */

                pagosDos.setPrestamosEmpresa(0.0f);

                /*
                 * el valor total egresos va a ser igual a el valor del préstamo empresa
                 * */

                pagosDos.setTotalEgresos(pagosDos.getPrestamosEmpresa());

                /*
                 * el valor a pagar va a ser igual al total ingresos/12 - total egresos
                 * valor_a_pagar = total_ingresos/12 - total_egresos
                 */

                pagosDos.setValorAPagar((pagosDos.getTotalIngresos()/12) - pagosDos.getTotalEgresos());
            }

            /*
             *   Cuando se completen los 12 valores de los meses
             *    Entonces al rol del mes de diciembre en el campo acumulado_decimo_tercero pasa el valor a pagar de la tabla pagos2.
             * */


            if( contMestPeriodo == 12 ){
                PeriodoLaboral periodoLaboralDIC = getPeriodoLaboralDic(AnioPeriodoDao);
                RolPago rolPagoDIC = periodoLaboralDIC.getRolPagoCollection().get(0);
                rolPagoDIC.setAcumuladoDecimoTercero(pagosDos.getValorAPagar());
                em.merge(rolPagoDIC);
            }

            pagosDos.setIdPagosUno(entityPagosUno);
            pagosDos.setIdTrabajador(trabajador);
            pagosDos.setIdPeriodoLaboral(periodoLaboral);
            pagosDos.setSisHabilitado("A");

            em.persist(pagosDos);
        }

    }

    public void decimoTerceroMensual(Integer idTrabajador) {

        /*
         * Requerimiento: #313
         *
         * Cuando el trabajador tiene si en décimo mensual
         *
         * Entonces se verifica si el trabajador ya tiene un año en la empresa
         *
         */

        Trabajador trabajador = getTrabajador(idTrabajador);
        PagosDos pagosDos = new PagosDos();

    }
}
