package com.ec.prontiauto.validations;

import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.ec.prontiauto.entidad.*;
import com.ec.prontiauto.exception.ApiRequestException;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class PagosDosValidation {
    private EntityManager em;
    private PagosDos entity;
    private Trabajador trabajador;

    private PeriodoLaboral periodoLaboral;
    private List<PeriodoLaboral> periodoLaboralCollectionDicNov;

    public PagosDosValidation(EntityManager em, PagosDos entity){
        this.em = em;
        this.entity = entity;

        validationEntities();

        this.trabajador = this.getTrabajador();
        this.periodoLaboral = this.getPeriodoLaboral();
        this.periodoLaboralCollectionDicNov = getPeriodoLaboralDicNov(periodoLaboral.getAnio(),
                trabajador.getId());

    }


    private void validationEntities(){
        if (entity.getIdTrabajador() != null && entity.getIdTrabajador().getId() == null){
            throw new ApiException("El idTrabajador es requerido");
        }

        if (entity.getIdPeriodoLaboral() != null && entity.getIdPeriodoLaboral().getId() == null){
            throw new ApiException("El idPeriodoLaboral es requerido");
        }

        if (entity.getIdPagosUno() != null && entity.getIdPagosUno().getId() == null){
            throw new ApiException("El idPagosUno es requerido");
        }
    }

    private Trabajador getTrabajador(){
        Trabajador trabajador = null;
        Integer idTrabajador = this.entity.getIdTrabajador().getId();

        try {
            trabajador = (Trabajador) em.createQuery("SELECT t FROM Trabajador t WHERE t.id = :id")
                    .setParameter("id", idTrabajador)
                    .getSingleResult();
        } catch (Exception e) {
            System.out.printf("\n%s\n%s", "-----------", e.getMessage());
            throw new ApiRequestException("No se encontró un trabajador con el id: ");
        }

        return trabajador;
    }

    private PeriodoLaboral getPeriodoLaboral() {
        PeriodoLaboral periodoLaboral = null;
        Integer idPeriodoLaboral = this.entity.getIdPeriodoLaboral().getId();


        try {
            periodoLaboral = (PeriodoLaboral) em.createQuery("SELECT p FROM PeriodoLaboral p WHERE p.id = :idPeriodo")
                    .setParameter("idPeriodo", idPeriodoLaboral).getSingleResult();
        } catch (Exception e) {
            System.out.printf("\n%s\n%s", "-----------", e.getMessage());
            throw new ApiException("No se encontró un periodo laboral con el id: " + idPeriodoLaboral);
        }

        return periodoLaboral;
    }

    private List<PeriodoLaboral> getPeriodoLaboralDicNov(Integer anioPeriodoAct, Integer idTrabajador){
        List<PeriodoLaboral> periodoLaboralCollection = null;
        if(anioPeriodoAct == null){
            throw new ApiException("El anioPeriodoAct es requerido");
        }

        if(idTrabajador == null){
            throw new ApiException("El idTrabajador es requerido");
        }

        try {
            String query = "SELECT pl FROM PeriodoLaboral pl " +
                    "INNER JOIN RolPago rp ON rp.idPeriodoLaboral=pl.id " +
                    "INNER JOIN PagosDos p2 ON p2.idPeriodoLaboral=rp.idPeriodoLaboral " +
                    "where p2.idTrabajador=" + idTrabajador + " " +
                    "and pl.anio in(:anioAct, :anioAnt) ";

            periodoLaboralCollection = (List<PeriodoLaboral>) em.createQuery(query)
                    .setParameter("anioAct", anioPeriodoAct)
                    .setParameter("anioAnt", anioPeriodoAct - 1)
                    .getResultList();
        }catch (Exception e) {
            System.out.printf("\n%s\n%s", "-----------", e.getMessage());
        }

        return periodoLaboralCollection;
    }

    private PeriodoLaboral getPeriodoLaboralDicAnioAct(Integer anioPeriodoAct){
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


    public PagosDos calculoDecimoTerceroAnual(){
        /*
        * Requerimiento #313/314
        * Cuando el trabajador tiene si en décimos año
        * */

        Boolean hashDecimosAnio = trabajador.getDecimosAnio() != null && trabajador.getDecimosAnio().equals("S");

        if(!hashDecimosAnio) {
            throw new ApiException("El trabajador no tiene decimos anuales");
        };

        if(!hashDecimosAnio) {
            return entity;
        };

        if(entity.getFechaInicio() == null || entity.getFechaFin() == null){
            throw new ApiException("La fecha de inicio y fecha fin son requeridas");
        }

        LocalDate fechaInicio = LocalDate.parse(entity.getFechaInicio().toString());
        LocalDate fechaFin = LocalDate.parse(entity.getFechaFin().toString());
        Integer diasLaboradosAnio = 0;

        LocalDate fechaIngreso = LocalDate.parse(trabajador.getFechaIngreso().toString());
        LocalDate now = LocalDate.now();
        Boolean hashOneYearAtLeast = (now.getYear() - fechaIngreso.getYear()) >= 1;


        if(hashOneYearAtLeast) {
            diasLaboradosAnio = fechaIngreso.getDayOfYear() - fechaFin.getDayOfYear();
        }else {
            diasLaboradosAnio = fechaInicio.getDayOfYear() - fechaFin.getDayOfYear();
        }

        entity.setDiasLaboradosAlAnio(diasLaboradosAnio);

        /*
         * el valor mes 1 va a ser igual al total de ingresos del mes de diciembre del
         * año anterior.
         * el valor mes 2 va a ser igual al total de ingresos del mes de enero del año actual.
         * ...
         * el valor mes 12 va a ser igual al total de ingresos del mes de noviembre del año actual.
         *
         */
        Integer AnioPeriodoDao = periodoLaboral.getAnio();
        List<PeriodoLaboral> periodoLaboralCollection = periodoLaboralCollectionDicNov;

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
                entity.setValorMes1(totalIngresos);
                totalIngresosPeriodoAct += totalIngresos;
                contMestPeriodo++;

                rolPago = periodoLaboralCollection.get(i).getRolPagoCollection().get(0);
                setProvDecimoTerceroRolPago(rolPago, totalIngresos);
            }

            if(Mes.equals("ENE") && (Objects.equals(AnioPeriodo, AnioPeriodoDao))){
                entity.setValorMes2(totalIngresos);
                totalIngresosPeriodoAct += totalIngresos;
                contMestPeriodo++;

                rolPago = periodoLaboralCollection.get(i).getRolPagoCollection().get(0);
                setProvDecimoTerceroRolPago(rolPago, totalIngresos);
            }

            if(Mes.equals("FEB") && (Objects.equals(AnioPeriodo, AnioPeriodoDao))){
                entity.setValorMes3(totalIngresos);
                totalIngresosPeriodoAct += totalIngresos;
                contMestPeriodo++;

                rolPago = periodoLaboralCollection.get(i).getRolPagoCollection().get(0);
                setProvDecimoTerceroRolPago(rolPago, totalIngresos);
            }

            if(Mes.equals("MAR") && (Objects.equals(AnioPeriodo, AnioPeriodoDao))){
                entity.setValorMes4(totalIngresos);
                totalIngresosPeriodoAct += totalIngresos;
                contMestPeriodo++;

                rolPago = periodoLaboralCollection.get(i).getRolPagoCollection().get(0);
                setProvDecimoTerceroRolPago(rolPago, totalIngresos);
            }

            if(Mes.equals("ABR") && (Objects.equals(AnioPeriodo, AnioPeriodoDao))){
                entity.setValorMes5(totalIngresos);
                totalIngresosPeriodoAct += totalIngresos;
                contMestPeriodo++;

                rolPago = periodoLaboralCollection.get(i).getRolPagoCollection().get(0);
                setProvDecimoTerceroRolPago(rolPago, totalIngresos);
            }

            if(Mes.equals("MAY") && (Objects.equals(AnioPeriodo, AnioPeriodoDao))){
                entity.setValorMes6(totalIngresos);
                totalIngresosPeriodoAct += totalIngresos;
                contMestPeriodo++;

                rolPago = periodoLaboralCollection.get(i).getRolPagoCollection().get(0);
                setProvDecimoTerceroRolPago(rolPago, totalIngresos);
            }

            if(Mes.equals("JUN") && (Objects.equals(AnioPeriodo, AnioPeriodoDao))){
                entity.setValorMes7(totalIngresos);
                totalIngresosPeriodoAct += totalIngresos;
                contMestPeriodo++;

                rolPago = periodoLaboralCollection.get(i).getRolPagoCollection().get(0);
                setProvDecimoTerceroRolPago(rolPago, totalIngresos);
            }

            if(Mes.equals("JUL") && (Objects.equals(AnioPeriodo, AnioPeriodoDao))){
                entity.setValorMes8(totalIngresos);
                totalIngresosPeriodoAct += totalIngresos;
                contMestPeriodo++;

                rolPago = periodoLaboralCollection.get(i).getRolPagoCollection().get(0);
                setProvDecimoTerceroRolPago(rolPago, totalIngresos);
            }

            if(Mes.equals("AGO") && (Objects.equals(AnioPeriodo, AnioPeriodoDao))){
                entity.setValorMes9(totalIngresos);
                totalIngresosPeriodoAct += totalIngresos;
                contMestPeriodo++;

                rolPago = periodoLaboralCollection.get(i).getRolPagoCollection().get(0);
                setProvDecimoTerceroRolPago(rolPago, totalIngresos);
            }

            if(Mes.equals("SEP") && (Objects.equals(AnioPeriodo, AnioPeriodoDao))){
                entity.setValorMes10(totalIngresos);
                totalIngresosPeriodoAct += totalIngresos;
                contMestPeriodo++;

                rolPago = periodoLaboralCollection.get(i).getRolPagoCollection().get(0);
                setProvDecimoTerceroRolPago(rolPago, totalIngresos);
            }

            if(Mes.equals("OCT") && (Objects.equals(AnioPeriodo, AnioPeriodoDao))){
                entity.setValorMes11(totalIngresos);
                totalIngresosPeriodoAct += totalIngresos;
                contMestPeriodo++;
                setProvDecimoTerceroRolPago(rolPago, totalIngresos);
            }

            if(Mes.equals("NOV") && (Objects.equals(AnioPeriodo, AnioPeriodoDao))){
                entity.setValorMes12(totalIngresos);
                totalIngresosPeriodoAct += totalIngresos;
                contMestPeriodo++;
                setProvDecimoTerceroRolPago(rolPago, totalIngresos);
            }

        }

        entity.setTotalIngresos(totalIngresosPeriodoAct);

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
            entity.setPrestamosEmpresa(valorCuota);

            /*
             *   el valor total egresos va a ser igual a el valor del préstamo empresa.
             *   total_egresos = prestamos_empresa
             */

            entity.setTotalEgresos(entity.getPrestamosEmpresa());

            /*
             * el valor a pagar va a ser igual al total ingresos/12 - total egresos
             * valor_a_pagar = total_ingresos/12 - total_egresos
             */

            Float valorAPagar = 0.0f;
            if(entity.getTotalIngresos() != null && entity.getTotalEgresos() != null){
                valorAPagar = entity.getTotalIngresos()/12 - entity.getTotalEgresos();
            }

            entity.setValorAPagar(valorAPagar);
        } else {
            /*
             *  si el empleado no tiene préstamos a descontar con el décimo tercero.
             *  el valor prestamos empresa será igual a 0
             */

            entity.setPrestamosEmpresa(0.0f);

            /*
             * el valor total egresos va a ser igual a el valor del préstamo empresa
             * */

            entity.setTotalEgresos(entity.getPrestamosEmpresa());

            /*
             * el valor a pagar va a ser igual al total ingresos/12 - total egresos
             * valor_a_pagar = total_ingresos/12 - total_egresos
             */

            entity.setValorAPagar((entity.getTotalIngresos()/12) - entity.getTotalEgresos());
        }

        /*
         *   Cuando se completen los 12 valores de los meses
         *    Entonces al rol del mes de diciembre en el campo acumulado_decimo_tercero pasa el valor a pagar de la tabla pagos2.
         * */


        if( contMestPeriodo == 12 ){
            PeriodoLaboral periodoLaboralDIC = getPeriodoLaboralDicAnioAct(AnioPeriodoDao);
            RolPago rolPagoDIC = periodoLaboralDIC.getRolPagoCollection().get(0);
            rolPagoDIC.setAcumuladoDecimoTercero(entity.getValorAPagar());
            em.merge(rolPagoDIC);
        }

        return entity;
    }

    public PagosDos calculoDecimoTerceroMensual(){
        /*
        * Requerimiento # 313/314
        *
        * Cuando el trabajador tiene si en décimo mensual
         *   Y si el usuario da clic en calcular rol
         *   Entonces se verifica si el trabajador ya tiene un año en la empresa
         *   duracion = fecha_ingreso (de la tabla historial_laboral - fecha_actual)
         *
         *   Y si la duración es menor a un año
         *   Entonces los días laborados al año serán igual a:
         *   dias_laborados_al_anio  = fecha_ingreso - fecha_fin
         */

        Boolean hashDecimosMensual = trabajador.getPagoDecTercerCuartoMes() != null && trabajador.getPagoDecTercerCuartoMes().equals("S");

        if(!hashDecimosMensual) {
            throw new ApiException("El trabajador no tiene decimos mensuales");
        }

        if(entity.getFechaInicio() == null || entity.getFechaFin() == null){
            throw new ApiException("La fecha de inicio y fecha fin son requeridas");
        }

        LocalDate fechaInicio = LocalDate.parse(entity.getFechaInicio().toString());
        LocalDate fechaFin = LocalDate.parse(entity.getFechaFin().toString());
        Integer diasLaboradosAnio = 0;

        LocalDate fechaIngreso = LocalDate.parse(trabajador.getFechaIngreso().toString());
        LocalDate now = LocalDate.now();
        Boolean hashOneYearAtLeast = (now.getYear() - fechaIngreso.getYear()) >= 1;


        if(hashOneYearAtLeast) {
            diasLaboradosAnio = fechaIngreso.getDayOfYear() - fechaFin.getDayOfYear();
        }else {
            diasLaboradosAnio = fechaInicio.getDayOfYear() - fechaFin.getDayOfYear();
        }

        entity.setDiasLaboradosAlAnio(diasLaboradosAnio);

        Date fechaActual = Date.valueOf(now);

        entity.setFechaActual(fechaActual);
        /*
        *  El total ingresos va a ser igual a la suma de los ingresos del mes actual
        * */

        Integer currentMonth = now.getMonthValue();
        Float totalIngresos = 0.0f;

        List<PeriodoLaboral> periodoLaboralCollection = periodoLaboralCollectionDicNov;
        String Month = null;
        RolPago rolPago = null;
        Float valorCurrentMonth = 0.0f;

        switch (currentMonth){
            case 1:
                Month = "ENE";
                valorCurrentMonth = entity.getValorMes1();
                break;
            case 2:
                Month = "FEB";
                valorCurrentMonth = entity.getValorMes2();
                break;
            case 3:
                Month = "MAR";
                valorCurrentMonth = entity.getValorMes3();
                break;
            case 4:
                Month = "ABR";
                valorCurrentMonth = entity.getValorMes4();
                break;
            case 5:
                Month = "MAY";
                valorCurrentMonth = entity.getValorMes5();
                break;
            case 6:
                Month = "JUN";
                valorCurrentMonth = entity.getValorMes6();
                break;
            case 7:
                Month = "JUL";
                valorCurrentMonth = entity.getValorMes7();
                break;
            case 8:
                Month = "AGO";
                valorCurrentMonth = entity.getValorMes8();
                break;
            case 9:
                Month = "SEP";
                valorCurrentMonth = entity.getValorMes9();
                break;
            case 10:
                Month = "OCT";
                valorCurrentMonth = entity.getValorMes10();
                break;
            case 11:
                Month = "NOV";
                valorCurrentMonth = entity.getValorMes11();
                break;
            case 12:
                Month = "DIC";
                valorCurrentMonth = entity.getValorMes12();
                break;
        }

        for (int i = 0; i < periodoLaboralCollection.size(); i++) {

            if(Month != null && periodoLaboralCollection.get(i).getMes().equals(Month)){
                totalIngresos = periodoLaboralCollection.get(i).getRolPagoCollection().get(0).getTotalIngresos();
                entity.setTotalIngresos(totalIngresos);
                if(valorCurrentMonth == null || valorCurrentMonth == 0){
                    valorCurrentMonth = totalIngresos;
                }
                rolPago = periodoLaboralCollection.get(i).getRolPagoCollection().get(0);
                break;
            }
        }

        /*
        * Y el valor a pagar va a ser igual al total ingresos/12
        *    valor_a_pagar = total_ingresos/12
        */
        Float valorAPagar = 0.0f;
        if(entity.getTotalIngresos() != null){
            valorAPagar = entity.getTotalIngresos()/12;
            entity.setValorAPagar(valorAPagar);
        }

        /*
        * Y ese valor a pagar pasa a la tabla rol pago en el campo pago_decimo_tercero_cuarto_mes
        * Y en el rol pago en el campo prov_decimo_tercero se almacena el valor del mes correspondiente/12
        * */
        if(rolPago != null){
            rolPago.setPagoDecimoTerceroCuartoMes(valorAPagar);
            rolPago.setProvDecimoTercero(valorCurrentMonth/12);
        }

        return entity;
    }

}
