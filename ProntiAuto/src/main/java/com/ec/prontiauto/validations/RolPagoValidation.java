package com.ec.prontiauto.validations;

import com.ec.prontiauto.entidad.*;
import com.ec.prontiauto.exception.ApiRequestException;
import com.ec.prontiauto.helper.DatesHelper;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class RolPagoValidation {

    EntityManager em;
    private HistorialLaboral historialLaboral;
    private ConfiguracionGeneral configuracionGeneral;
    private List<DetalleNovedadRolPago> detalleNovedadCollection;

    private DatesHelper datesHelper;
    RolPago entity;

    public RolPagoValidation(EntityManager em, RolPago entity, Boolean isUpdate) {
        this.em = em;
        this.entity = entity;
        this.datesHelper = new DatesHelper();

        valParentRelations();

        this.historialLaboral =  getHistorialLaboral();
        this.configuracionGeneral = configuracionGeneral();
        this.detalleNovedadCollection = getNovedadesTrabajador();

        if(isUpdate) {
            initUpdate();
        }else {
            initPost();
        }
    }

    private void initPost(){

        /* gestionHorasExtra();
        calculoAporteIess();
        aportePatronalIESS();
        pagoFondoReserva(); */
        gestionRol();
    }

    private void initUpdate(){
        gestionHorasExtra();
    }


    private void valParentRelations() {
        if (entity.getIdHistorialLaboral().getId() == null) {
            throw new ApiRequestException("El id del historialLaboral es requerido");
        }

        if (entity.getIdPeriodoLaboral().getId() == null) {
            throw new ApiRequestException("El id del periodoLaboral es requerido");
        }
    }

    private HistorialLaboral getHistorialLaboral() {
        HistorialLaboral historialLaboral = null;
        try {
            Query query = em.createQuery(
                    "SELECT DISTINCT h from HistorialLaboral h " +
                            "INNER JOIN Trabajador t on h.idTrabajador=t.id " +
                            " WHERE h.id=:id"
            );
            query.setParameter("id", this.entity.getIdHistorialLaboral().getId());

            historialLaboral = (HistorialLaboral) query.getSingleResult();

        } catch (Exception e) {
            throw new ApiRequestException("No se encontró un historial laboral con el id ingresado");
        }

        return historialLaboral;
    }

    private List<DetalleNovedadRolPago> getNovedadesTrabajador() {
        List<DetalleNovedadRolPago> detalleNovedad = null;
        try {
            Integer idTrabajador = this.historialLaboral.getIdTrabajador().getId();

            if (idTrabajador != null) {
                Query query = em.createQuery(
                        "SELECT h from DetalleNovedadRolPago h WHERE h.idTrabajador.id=:id");
                query.setParameter("id", idTrabajador);

                detalleNovedad = (List<DetalleNovedadRolPago>) query.getResultList();
            }
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage());
        }
        return detalleNovedad;
    }

    private ConfiguracionGeneral configuracionGeneral() {
        ConfiguracionGeneral cg = null;
        try {
            Query query = this.em.createQuery("select c from ConfiguracionGeneral c");
            cg = (ConfiguracionGeneral) query.getSingleResult();
        } catch (Exception e) {
            throw new ApiRequestException("No se encontró la configuración general");
        }
        return cg;
    }

    private List<RolPago> getRolPagoByMonth(List<String> months, Integer anio ) {
        List<RolPago> rolPagoCollection = null;

        try {
            String queryText = "SELECT e FROM RolPago e " +
                    "INNER JOIN PeriodoLaboral p ON e.idPeriodoLaboral=p.id " +
                    "INNER JOIN HistorialLaboral h ON e.idHistorialLaboral=h.id " +
                    "INNER JOIN Trabajador t ON h.idTrabajador=t.id " +
                    "WHERE p.mes in :listMonths " +
                    "AND p.anio=:anio ";

            Query query = em.createQuery(queryText);
            query.setParameter("listMonths", months);
            query.setParameter("anio", anio);
            rolPagoCollection = (List<RolPago>) query.getResultList();

        } catch (Exception e){
            System.out.printf("\n\n%s\n\n",e.getMessage());
        }
        return rolPagoCollection;
    }

    private void calculoAporteIess() {
        /*
         * Cuando el trabajador tiene si en aporte al IESS
         * Y si el usuario da CLICK EN CALCULAR ROL
         * Entonces el aporte iess será igual a:
         * aporte_iess =(( sueldo + total_horas_extra + bonificacion) *
         * aporte_personal_iess)/100 (de la tabla configuracion_general)
         */

        Trabajador trabajador = this.historialLaboral.getIdTrabajador();
        Float aporteIess = 0.0f;
        Float sueldo = trabajador.getSueldo();
        Float horasExtra = this.entity.getTotalHorasExtra() != null ? this.entity.getTotalHorasExtra() : 0.0f;
        Float bonificacion = trabajador.getBonificacion() != null ? trabajador.getBonificacion() : 0.0f;
        Float aportePersonalIess = this.configuracionGeneral.getAportePersonalIess().floatValue();


        if (trabajador.getAporteIess().equals("S") && sueldo != null && aportePersonalIess != null) {

            aporteIess = ((sueldo + horasExtra + bonificacion) * aportePersonalIess) / 100;

            entity.setBonificacion(bonificacion);
            this.entity.setAporteIess(aporteIess);
        }
    }

    private void gestionRol() {
        /*
        * la fecha desde será igual a el primero del mes actual
        * la fecha hasta será igual al último día del mes actual
        * al rol pasa el valor del sueldo del trabajador
         * */

        LocalDate fechaDesde = LocalDate.now().withDayOfMonth(1);
        LocalDate fechaHasta = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());

        entity.setSueldo(historialLaboral.getSueldo());

        /*
        * Novedades con clave AI5 pasa el valor de la novedad al campo bonificación de la tabla rol pago.
        *
        * Novedades con clave AI9 pasa el valor de la novedad al campo otros ingresos de la tabla rol pago.
        *
        * Novedades con clave AI6 pasa el valor de la novedad al campo movilización especial de la tabla rol pago
        *
        * Novedades con clave AI7 pasa el valor de la novedad al campo componente salarial unifi de la tabla rol pago.
        *
        * Novedades con clave AI4 pasa el valor de la novedad al campo comisión de la tabla rol pago.
        *
        * Novedades con clave AI8 pasa el valor de la novedad al campo incentivos ocasionales de la tabla rol pago.
        *
        * Novedades con clave AI10 pasa el valor de la novedad al campo retroactivo de la tabla rol pago.
        *
        * Novedades con clave AI11 pasa el valor de la novedad al campo ajustes ingreso de la tabla rol pago.
        *
        * Novedades con clave AE12 pasa el valor de la novedad al campo prestamo_quirografario de la tabla rol pago.
        *
        * Novedades con clave AE13 pasa el valor de la novedad al campo prestamo_hipotecario de la tabla rol pago.
        *
        * Novedades con clave AE14 pasa el valor de la novedad al campo multas de la tabla rol pago.
        *
        * Novedades con clave AE15 pasa el valor de la novedad al campo otros_descuentos de la tabla rol pago
        *
        * Novedades con clave AE16 pasa el valor de la novedad al campo celular_consumo de la tabla rol pago.
        *
        * Novedades con clave AE20 pasa el valor de la novedad al campo ley_solidaria de la tabla rol pago.
        *
        * Novedades con clave AE21 pasa el valor de la novedad al campo ajuste_egreso de la tabla rol pago.
        *
         */

        String claveNovedad = null;
        Float valorNovedad = null;
        String tipoNovedad = null;
        int i = 0;

        /*
         *  El total ingresos va a ser igual a la suma de todas las novedades de tipo AI mas
         * el sueldo y mas pago_decimo_tercero_cuarto_mes +
         * pago_decimo_cuarto_mes + pago_fondo_reserva_mes + utilidad
         *
         * Y el total egresos va a ser igual a la suma de todas las novedades de tipo AE, el aporte iess y el pago cuota vehículo
         * Y el valor a pagar es la diferencia del total ingresos - total egresos.
         **/
        Float totalIngresos = 0.0f;
        Float totalEgresos = 0.0f;

        while (i < detalleNovedadCollection.size()) {
            claveNovedad = detalleNovedadCollection.get(i).getCodigoNovedad().toLowerCase();
            valorNovedad = detalleNovedadCollection.get(i).getValor().floatValue();
            tipoNovedad = detalleNovedadCollection.get(i).getTipoNovedad().toLowerCase();

            if(claveNovedad.startsWith("ai")){
                totalIngresos += valorNovedad;
            }

            if(claveNovedad.startsWith("ae")){
                totalEgresos += valorNovedad;
            }

            if(claveNovedad.startsWith("ai5")) {
                entity.setBonificacion(valorNovedad);
            }
            if(claveNovedad.startsWith("ai9")) {
                entity.setOtrosIngresos(valorNovedad);
            }
            if(claveNovedad.startsWith("ai6")) {
                entity.setMovilizacionEspecial(valorNovedad);
            }
            if(claveNovedad.startsWith("ai7")) {
                entity.setComponenteSalarialUnif(valorNovedad);
            }
            if(claveNovedad.startsWith("ai4")) {
                entity.setComision(valorNovedad);
            }
            if(claveNovedad.startsWith("ai8")) {
                entity.setIncentivosOcacionales(valorNovedad);
            }
            if(claveNovedad.startsWith("ai10")) {
                entity.setRetroactivo(valorNovedad);
            }
            if(claveNovedad.startsWith("ai11")) {
                entity.setAjusteIngreso(valorNovedad);
            }
            if(claveNovedad.startsWith("ae12")) {
                entity.setPrestamoQuirografario(valorNovedad);
            }
            if(claveNovedad.startsWith("ae13")) {
                entity.setPrestamoHipotecario(valorNovedad);
            }
            if(claveNovedad.startsWith("ae14")) {
                entity.setMultas(valorNovedad);
            }
            if(claveNovedad.startsWith("ae15")) {
                entity.setOtrosDescuentos(valorNovedad);
            }
            if(claveNovedad.startsWith("ae16")) {
                entity.setCelularConsumo(valorNovedad);
            }
            if(claveNovedad.startsWith("ae20")) {
                entity.setLeySolidaria(valorNovedad);
            }
            if(claveNovedad.startsWith("ae21")) {
                entity.setAjusteEgreso(valorNovedad);
            }
            if(claveNovedad.startsWith("ae23") && tipoNovedad.toLowerCase().contains("enfermedad")) {

                /*
                * si el trabajador tiene registrados días de enfermedad en las novedades  de tipo AE23
                 * Entonces el valor que pasa al rol en el campo descuento_enfermedad será igual a:
                 * Promedio aportes = suma de los ingresos de los 3 últimos meses  = ( sueldo + total_horas_extra + bonificacion) / 3
                 * descuento_enfermedad_iess = (((valor (de la novedad AE23) -3 )* Promedio aportes/30)*0.75)
                 * costo_dia= (sueldo/30)*3*0.5
                 * descuento_enfermedad(de la tabla rol pago) = descuento_enfermedad_iess + costo_dia
                * */

                int currentYear = LocalDate.now().getYear();
                int currentMonth = LocalDate.now().getMonthValue();
                int startMonthValue = currentMonth - 3;
                List<String> months = new ArrayList<>();

                for (int j = startMonthValue; j < currentMonth; j++) {
                    months.add(datesHelper.getMonthByNumber(j));
                }

                List<RolPago> getRolPagoLast3Month = getRolPagoByMonth(months,currentYear);

                Float promedioAportes = 0.0f;
                Float sueldo = 0.0f;
                Float totalHorasExtra = 0.0f;
                Float bonificacion = 0.0f;
                HistorialLaboral historialLaboralAux = null;
                Trabajador trabajador = null;


                if(getRolPagoLast3Month != null && getRolPagoLast3Month.size() > 0){
                    for (int j = 0; j < getRolPagoLast3Month.size(); j++) {

                        historialLaboralAux = getRolPagoLast3Month.get(j).getIdHistorialLaboral();
                        trabajador = historialLaboralAux.getIdTrabajador();

                        sueldo = historialLaboralAux.getSueldo() != null ? historialLaboralAux.getSueldo() : 0.0f;
                        bonificacion = trabajador.getBonificacion() != null ? trabajador.getBonificacion() : 0.0f;
                        totalHorasExtra = getRolPagoLast3Month.get(j).getTotalHorasExtra() != null ? getRolPagoLast3Month.get(j).getTotalHorasExtra() : 0.0f;
                        promedioAportes += sueldo + totalHorasExtra + bonificacion;
                    }
                    promedioAportes = promedioAportes / 3;
                }

                sueldo = historialLaboral.getSueldo() != null ? historialLaboral.getSueldo() : 0.0f;
                double descuentoEnfermedadIess = 0.0f;
                float valorNovedadMap = valorNovedad != null ? valorNovedad : 0.0f;
                float costoDia= (float) ((sueldo/30)*3*0.5);



                descuentoEnfermedadIess = (((valorNovedadMap - 3 ) * promedioAportes/30)*0.75);
                entity.setDescuentoEnfermedad((float) (descuentoEnfermedadIess + costoDia));
            }

            if(claveNovedad.startsWith("ae18") && tipoNovedad.toLowerCase().contains("falta")) {
                /*
                 *   si el trabajador tiene registrados descuentos por falta en las novedades de tipo AE18
                 *   Entonces el valor que pasa al rol en el campo descuentos_por_faltas será igual a:
                 *   valor dia = sueldo/30 si el mes tiene 30 días
                 *   valor dia = sueldo/31 si el mes tiene 31 días
                 *   valor dia = sueldo/28 si el mes tiene 28 días
                 *   descuentos_por_faltas = valor dia * (valor (de la novedad AE18))
                 * */

                Float sueldo = historialLaboral.getSueldo() != null ? historialLaboral.getSueldo() : 0.0f;
                int daysInMonth = LocalDate.now().getMonth().length(LocalDate.now().isLeapYear());
                Float valorDia = (float) (sueldo/daysInMonth);
                float valorNovedadMap = valorNovedad != null ? valorNovedad : 0.0f;
                entity.setDescuentosPorFaltas(valorNovedadMap * valorDia);
            }

            if(claveNovedad.startsWith("ae19") && tipoNovedad.toLowerCase().contains("atrasos")) {
                /*si el trabajador tiene registrados descuentos por atrasos en las novedades de tipo AE19
                    el valor que pasa al rol en el campo descuentos_por_atrasos será igual a:
                    valor hora = sueldo/30/8
                    descuentos_por_atrasos = valor hora * (valor (de la novedad AE19))
                 */

                Float sueldo = historialLaboral.getSueldo() != null ? historialLaboral.getSueldo() : 0.0f;
                Float valorHora = sueldo/30/8;
                float valorNovedadMap = valorNovedad != null ? valorNovedad : 0.0f;

                entity.setDescuentosPorAtrasos(valorNovedadMap * valorHora);
            }

            if(claveNovedad.startsWith("ae24") && tipoNovedad.toLowerCase().contains("maternidad")) {
                /*
                *   Si el trabajador tiene registrados días de maternidad en las novedades de tipo AE24
                *
                *   Entonces se verifica si la duración del trabajador es mayor o igual a 360 días (1 año)
                *    Y si la duración es mayor o igual a un año
                *    Entonces el valor que pasa al rol en el campo descuento_maternidad será a:
                *    descuento_maternidad = sueldo*0.25
                *    Y si la duración no es mayor o igual a un año
                *    Entonces el valor que pasa al rol en el campo descuento_maternidad será a:
                *    descuento_maternidad = sueldo
                * */

                Float sueldo = historialLaboral.getSueldo() != null ? historialLaboral.getSueldo() : 0.0f;
                Trabajador trabajador = historialLaboral.getIdTrabajador();


                LocalDate fechaIngreso = LocalDate.parse(trabajador.getFechaIngreso().toString());
                LocalDate now = LocalDate.now();
                Long duracion = ChronoUnit.DAYS.between(fechaIngreso, now);

                if(duracion >= 360) {
                    entity.setDescuentoMaternidad(sueldo * 0.25f);
                }else {
                    entity.setDescuentoMaternidad(sueldo);
                }

            }

            if(claveNovedad.startsWith("ae25") && tipoNovedad.toLowerCase().contains("anticipos")) {
                /*
                * si el trabajador tiene registrados Anticipos en las novedades de tipo AE25
                * Entonces el valor que pasa al rol en el campo anticipos será igual al valor registrado en la novedad
                *
                * Y en el campo prov_vacaciones se almacena el valor de la suma de (sueldo+ bonificación+ total_horas_extra +componente_salarial_unif +retroactivo +  incentivos_ocacionales) /24
                *
                * Y en el campo prov_decimo_cuarto se almacena el valor del sueldo básico/12 (de la tabla configuración)
                *
                * Y en el campo prov_decimo_tercero se almacena el valor del total ingresos /12
                 * */

                float valorNovedadMap = valorNovedad != null ? valorNovedad : 0.0f;
                entity.setAnticipos(valorNovedadMap);

                Trabajador trabajador = historialLaboral.getIdTrabajador();
                Float sueldo = historialLaboral.getSueldo() != null ? historialLaboral.getSueldo() : 0.0f;
                Float bonificacion = trabajador.getBonificacion() != null ? trabajador.getBonificacion() : 0.0f;
                Float totalHorasExtra = entity.getTotalHorasExtra() != null ? entity.getTotalHorasExtra() : 0.0f;
                Float componenteSalarialUnif = entity.getComponenteSalarialUnif() != null ? entity.getComponenteSalarialUnif() : 0.0f;
                Float retroactivo = entity.getRetroactivo() != null ? entity.getRetroactivo() : 0.0f;
                Float incentivosOcacionales = entity.getIncentivosOcacionales() != null ? entity.getIncentivosOcacionales() : 0.0f;

                Float provVacaciones = (sueldo + bonificacion + totalHorasExtra + componenteSalarialUnif + retroactivo + incentivosOcacionales) / 24;
                entity.setProvVacaciones(provVacaciones);

                Float sueldoBasico = configuracionGeneral.getSueldoBasico() != null ? configuracionGeneral.getSueldoBasico().floatValue() : 0.0f;

                Float provDecimoCuarto = sueldoBasico / 12;

                entity.setProvDecimoCuarto(provDecimoCuarto);
                Float totalIngresosAux = entity.getTotalIngresos() != null ? entity.getTotalIngresos() : 0.0f;

                entity.setProvDecimoTercero(totalIngresosAux / 12);
            }

            i++;
        }

        /*
        *Cuando el trabajador tiene registrados préstamos de tipo vehiculo
        **/

        Trabajador trabajador = historialLaboral.getIdTrabajador();
        List<Prestamo> prestamos = trabajador.getPrestamoCollection();
        List<AbonoPrestamo> abonoPrestamoCollection = null;

        if(prestamos != null && prestamos.size()> 0) {
            i = 0;
            while(i < prestamos.size()) {
                Prestamo prestamo = prestamos.get(i);

                if(prestamo.getTipoPrestamo().toLowerCase().equals("vehiculo")){

                    /*
                    *   Entonces el valor de la cuota del contrato en estado pendiente pasa al campo pago_cuota_vehiculo de la tabla rol pago.
                     * */

                    abonoPrestamoCollection = prestamo.getAbonoPrestamoCollection();

                    for (int j = 0; j < abonoPrestamoCollection.size(); j++) {
                        AbonoPrestamo abonoPrestamo = abonoPrestamoCollection.get(j);
                        if(abonoPrestamo.getEstaPagado().toLowerCase().equals("pendiente")) {
                            entity.setPagoCuotaVehiculo(abonoPrestamo.getValorCuota().floatValue());
                            break;
                        }
                    }
                }

                /*
                *  si el trabajador tiene préstamos registrados en estado pendiente.
                * Entonces el valor de la cuota pasa al campo préstamo empresa de la tabla rol pagos.
                * */

                if( prestamo.getEstado().toLowerCase().equals("pendiente")) {
                    abonoPrestamoCollection = prestamo.getAbonoPrestamoCollection();
                    for (int j = 0; j < abonoPrestamoCollection.size(); j++) {
                        AbonoPrestamo abonoPrestamo = abonoPrestamoCollection.get(j);
                        if(abonoPrestamo.getEstaPagado().toLowerCase().equals("pendiente")) {
                            entity.setPrestamosEmpresa(abonoPrestamo.getValorCuota().floatValue());
                            break;
                        }
                    }
                }

                i++;
            }
        }

        /*calculo totalIngresos y totalEgresos*/

        Float sueldo = trabajador.getSueldo() != null ? trabajador.getSueldo() : 0.0f;
        Float pagoDecimoTerceroCuartoMes = entity.getPagoDecimoTerceroCuartoMes() != null ? entity.getPagoDecimoTerceroCuartoMes() : 0.0f;
        Float pagoFondoReservaMes = entity.getPagoFondoReservaMes() != null ? entity.getPagoFondoReservaMes() : 0.0f;
        Float utilidad = 0.0f;

        totalIngresos += sueldo + pagoDecimoTerceroCuartoMes + pagoFondoReservaMes + utilidad;
        entity.setTotalIngresos(totalIngresos);

        Float aporteIess = entity.getAporteIess() != null ? entity.getAporteIess() : 0.0f;
        Float pagoCuotaVehiculo = entity.getPagoCuotaVehiculo() != null ? entity.getPagoCuotaVehiculo() : 0.0f;
        totalEgresos += aporteIess + pagoCuotaVehiculo;
        entity.setTotalEgresos(totalEgresos);

        /*
        * Y el valor a pagar es la diferencia del total ingresos - total egresos
        * */
        Float valorAPagar = totalIngresos - totalEgresos;
        entity.setTotalAPagar(valorAPagar);

    }






    private void gestionHorasExtra() {
        /*
         * Cuando al empleado se le asigne novedades de horas extra al 50%
         * Se calcula el valor por hora de trabajo que será igual a
         * valor_hora = sueldo/8/30
         * valor_hora_extra = valor_hora + (valor_hora*factor)( si es 25% entonces
         * factor=0.25, si es 50% factor=0.5, si es 100% factor=2)
         * 
         * Y el valor de la hora extra al (25%,50%,100%) será igual a:
         * horas_[25,50,100] (de la tabla rol_pago) = valor_hora_extra * valor (de la
         * tabla
         * detalle_novedad_rol_pago)
         * 
         * Entonces CUANDO SE DE CLICK EN CALCULAR ROL
         * Entonces el total_horas_extra va a ser igual a
         * total_horas_extra = total_horas_extra + horas_[25,50,100]
         */

        List<DetalleNovedadRolPago> novedades = this.detalleNovedadCollection;
        Float valorHoraExtraNovedad = 0.0f;
        Float sueldo = historialLaboral.getIdTrabajador().getSueldo();
        Float valorHora = 0.0f;
        Float valorHoraExtra = 0.0f;

        entity.setSueldo(sueldo);

        if (novedades.size() > 0 && sueldo > 0) { // presenta novedades
            int cont = 0;
            String codigoNovedad = "";
            String tipoNovedad = "";
            valorHora = (sueldo / 8) / 30;

            while (cont < novedades.size()) {

                codigoNovedad = novedades.get(cont).getCodigoNovedad();
                tipoNovedad = novedades.get(cont).getTipoNovedad();

                if (codigoNovedad.toLowerCase().startsWith("ai")) { // codigo AI (Argumento de ingreso)
                    if (novedades.get(cont).getValor() != null) {
                        valorHoraExtraNovedad = novedades.get(cont).getValor().floatValue();
                    }

                    if (tipoNovedad.toLowerCase().contains("horas extra 100")) {
                        valorHoraExtra = (float) (valorHora + (valorHora * 2));
                        entity.setHoras100(valorHoraExtra * valorHoraExtraNovedad);
                        entity.setTotalHorasExtra(entity.getTotalHorasExtra() + entity.getHoras100());

                    } else if (tipoNovedad.toLowerCase().contains("horas extra 50")) {
                        valorHoraExtra = (float) (valorHora + (valorHora * 0.5));
                        entity.setHoras50(valorHoraExtra * valorHoraExtraNovedad);
                        entity.setTotalHorasExtra(entity.getTotalHorasExtra() + entity.getHoras50());

                    } else if (tipoNovedad.toLowerCase().contains("horas extra 25")) {
                        valorHoraExtra = (float) (valorHora + (valorHora * 0.25));
                        entity.setHoras25(valorHoraExtra * valorHoraExtraNovedad);
                        entity.setTotalHorasExtra(entity.getTotalHorasExtra() + entity.getHoras25());
                    }
                }
                cont++;
            }
        }

    }

    private void aportePatronalIESS() {
        /*
         * Cuando el trabajador tiene si en aporte al IESS
         * Entonces el aporte patronal iess será igual a:
         * prov_aporte_patronal =(( sueldo + total_horas_extra + bonificacion) *
         * aporte_patronal_iess)/100 (de la tabla configuracion_general)
         */

        Trabajador trabajador = this.historialLaboral.getIdTrabajador();
        Boolean tieneAporteIess = trabajador.getAporteIess() != null && trabajador.getAporteIess().equals("S");

        if (tieneAporteIess) {
            Float sueldo = trabajador.getSueldo() != null ? trabajador.getSueldo() : 0.0f;
            Float provAportePatronal = 0.0f;
            Float totalHorasExtra = entity.getTotalHorasExtra() != null ? entity.getTotalHorasExtra() : 0.0f;
            Float bonificacion = entity.getBonificacion() != null ? trabajador.getBonificacion() : 0.0f;
            Float aportePatronalIess = configuracionGeneral.getAportePatronalIess() != null
                    ? configuracionGeneral.getAportePatronalIess().floatValue()
                    : 0.0f;

            provAportePatronal = ((sueldo + totalHorasExtra + bonificacion) * aportePatronalIess) / 100;

            entity.setProvAportePatronal(provAportePatronal);
        }
    }

    private Integer duracionTrabajador() {
        LocalDate fechaIngreso = LocalDate.parse(historialLaboral.getFechaIngreso().toString());
        LocalDate now = LocalDate.now();

        Integer duracion = now.getYear() - fechaIngreso.getYear();

        return duracion;
    }

    private void pagoFondoReserva() {
        /*
         * Requerimiento #288
         * 
         * - Escenario: Fondo de reserva mensual
         * Cuando el trabajador tiene si en pago fondos de reserva mes
         * Entonces se verifica que la duración del trabajador en la empresa sea mayor o
         * igual a 1 año.
         * duración = fecha_ingreso (de la tabla historial_laboral - fecha_actual)
         * 
         * Y si la duración es mayor a 1 año
         * Entonces el total ingresos será igual al total de los ingresos del periodo
         * actual
         * total_ingresos = sueldo+ bonificación+ total_horas_extra
         * +componente_salarial_unif +retroactivo + incentivos_ocacionales
         * 
         * Cuando el usuario de clic en calcular rol
         * Entonces el valor de pago_fondo_reserva_mes será igual a:
         * pago_fondo_reserva_mes = total_ingresos * (8.33/100)
         * 
         */

        Trabajador trabajador = this.historialLaboral.getIdTrabajador();
        Float sueldo = trabajador.getSueldo();
        Float bonificacion = trabajador.getBonificacion() != null ? trabajador.getBonificacion() : 0.0f;
        Float totalHorasExtra = entity.getTotalHorasExtra() != null ? entity.getTotalHorasExtra() : 0.0f;
        Float componenteSalarialUnif = entity.getComponenteSalarialUnif() != null ? entity.getComponenteSalarialUnif() : 0.0f;
        Float retroactivo = entity.getRetroactivo() != null ? entity.getRetroactivo() : 0.0f;
        Float incentivosOcacionales = entity.getIncentivosOcacionales() != null ? entity.getIncentivosOcacionales() : 0.0f;

        Boolean tienePagoFondoReservaMes = trabajador.getPagoFondosReservaMes() != null
                && trabajador.getPagoFondosReservaMes().equals("S");

        if (tienePagoFondoReservaMes && sueldo != null && duracionTrabajador() >= 1) {
                entity.setTotalIngresos(sueldo + bonificacion + totalHorasExtra + componenteSalarialUnif
                        + retroactivo + incentivosOcacionales);

                entity.setPagoFondoReservaMes(entity.getTotalIngresos() * (8.33f / 100));
        }

        /*
         * - Escenario: fondos de reserva Iess
         * 
         * Cuando el trabajador tiene si en fondos de reserva iess
         * Y si el usuario da clic en calcular rol
         * Entonces se verifica que la duración del trabajador en la empresa sea mayor o
         * igual a 1 año.
         * 
         * Y si la duración es mayor a 1 año
         * Entonces el total ingresos será igual al total de los ingresos del periodo
         * actual
         * total_ingresos = sueldo+ bonificación+ total_horas_extra
         * +componente_salarial_unif +retroactivo + incentivos_ocacionales
         * Cuando el usuario de clic en calcular rol
         * Entonces el valor prov_fondos_reserva será igual a:
         * prov_fondos_reserva = total_ingresos * (8.33/100)
         * 
         */

        Boolean tieneFondoReservaIess = trabajador.getFondoReservaIess() != null
                && trabajador.getFondoReservaIess().equals("S");

        if (tieneFondoReservaIess && sueldo != null && duracionTrabajador() >= 1) {
            entity.setTotalIngresos(sueldo + bonificacion + totalHorasExtra + componenteSalarialUnif
                    + retroactivo + incentivosOcacionales);

            entity.setProvFondosReserva(entity.getTotalIngresos() * (8.33f / 100));
        }
    }

    public RolPago getRolPago() {
        this.entity.setIdHistorialLaboral(historialLaboral);
        return this.entity;
    }

}
