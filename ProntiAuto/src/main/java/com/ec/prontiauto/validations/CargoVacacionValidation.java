package com.ec.prontiauto.validations;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.ec.prontiauto.entidad.ConfiguracionGeneral;
import com.ec.prontiauto.entidad.Trabajador;
import com.ec.prontiauto.exception.ApiRequestException;

public class CargoVacacionValidation {

    private Integer idTrabajador;
    private Integer countRegistrosCargoVacacion;
    private EntityManager em;

    private Trabajador trabajador;
    private ConfiguracionGeneral configuracionGeneral;

    public CargoVacacionValidation() {
    }

    public CargoVacacionValidation(EntityManager em, Integer idTrabajador) {
        this.em = em;
        this.idTrabajador = idTrabajador;
        this.initState();
    }

    private void initState() {
        this.getCount();
        this.trabajadorByCargoVacacion();
        this.configuracionGeneral();
    }

    private void getCount() {
        Query query = this.em.createNativeQuery("select count(*) from cargo_vacacion");
        int count = ((Number) query.getSingleResult()).intValue();
        this.countRegistrosCargoVacacion = count;
    }

    private void trabajadorByCargoVacacion() {
        try {
            Query query = this.em.createQuery("select t from Trabajador t where id =:id");
            query.setParameter("id", this.idTrabajador);
            this.trabajador = (Trabajador) query.getSingleResult();
        } catch (Exception e) {
            throw new ApiRequestException("No se encontro el trabajador");
        }

    }

    private void configuracionGeneral() {
        try {
            Query query = this.em.createQuery("select c from ConfiguracionGeneral c");
            this.configuracionGeneral = (ConfiguracionGeneral) query.getSingleResult();
        } catch (Exception e) {
            throw new ApiRequestException("No se encontro la configuracion general");
        }
    }

    public Date valFechaDesde() {
        /*
         * la 'fecha desde' del primer registro de cargo vacación será igual a la fecha
         * de ingreso del trabajador
         * 
         * a partir del segundo registro la 'fecha desde' va a ser igual a la fecha que
         * ingreso + 365 días
         */

        if (countRegistrosCargoVacacion >= 1) {
            LocalDate customDate = LocalDate.parse(this.trabajador.getFechaIngreso().toString());
            return Date.valueOf(customDate.plusDays(365));
        } else {
            return this.trabajador.getFechaIngreso();
        }

    }

    public Date valFechaHasta(Date fechaDesde) {
        /*
         * la fecha hasta será igual a: fecha_hasta = fecha_desde + 364 días
         */
        LocalDate customDate = LocalDate.parse(fechaDesde.toString());
        return Date.valueOf(customDate.plusDays(364));
    }

    public Integer valDiasVacaciones() {
        /*
         * los días de vacaciones van a ser igual a el valor que viene de la tabla
         * configuración general del campo num_dias_vacaciones_al_anio
         */
        return this.configuracionGeneral.getNumDiasVacacionesAlAnio();
    }

    public Integer valDiasTomados(Integer diasTomados) {
        /*
         * los días tomados van a ser igual a los días que ha tomado el trabajador,
         * en el primer registro este valor va a ser igual a 0
         * 
         * dias_tomados (de la tabla cargo_vacacion) = dias_tomados (de la tabla
         * registro_vacacion)
         */

        if (diasTomados == null) {
            throw new ApiRequestException("Debes especificar los dias tomados");
        }

        if (countRegistrosCargoVacacion >= 1) {
            return diasTomados;
        } else {
            return 0;
        }
    }

    public Integer valDiasSaldo(Integer diasTeoricos, Integer diasTomados) {
        /*
         * Los días saldo van a ser igual a los días teóricos menos los días tomados
         */
        if (diasTeoricos == null || diasTomados == null) {
            throw new ApiRequestException("Debes especificar los dias teoricos y los dias tomados");
        }

        if (diasTeoricos < diasTomados) {
            throw new ApiRequestException("Los días teoricos no pueden ser menores a los dias tomados");
        }

        return diasTeoricos - diasTomados;
    }

    public Float valValorDiasVacaciones(Float totalIngresosAnio) {
        /*
         * el valor vacación será iguala total de ingresos al año dividido para 24
         */
        if (totalIngresosAnio == null) {
            throw new ApiRequestException("Debes especificar el total de ingresos del año");
        }

        if (totalIngresosAnio <= 0) {
            throw new ApiRequestException("El total de ingresos del año no puede ser menor o igual a 0");
        }

        return totalIngresosAnio / 24;
    }

    public Float valValorDias(Float valorVacaciones, Integer diasVacaciones) {
        /*
         * el valor dias es igual a:
         * valor _dias = valor_vacacion / dias_vacaciones
         */
        if (valorVacaciones == null || diasVacaciones == null) {
            throw new ApiRequestException("Debes especificar el valor de vacaciones y los dias de vacaciones");
        }

        if (valorVacaciones <= 0) {
            throw new ApiRequestException("El valor de vacaciones no puede ser menor o igual a 0");
        }

        return valorVacaciones / diasVacaciones;
    }

    public Float valAntiguedad(Float valorDias, Integer diasAntiguedad) {
        /*
         * el valor antigüedad va a ser iguala a:
         * valor_antiguedad = valor_dias * dias_antiguedad
         */

        return valorDias * diasAntiguedad;
    }

    public Float valValorTeorico(Float valorAntiguedad, Float valorVacacion) {
        /*
         * el valor teórico será igual a:
         * valor_teorico = valor_antiguedad + valor_vacacion
         */
        return valorAntiguedad + valorVacacion;
    }

    public Float valValorTomado(Float valorDias, Integer diasTomados) {
        /*
         * el valor tomado será igual a:
         * valor_tomado = valor_dias * dias_tomados (de la tabla cargo_vacacion)
         */

        return valorDias * diasTomados;
    }

    public Float valValorSaldo(Float valorTeorico, Float valorTomado) {
        /*
         * el valor saldo será igual a:
         * valor_saldo = valor_teorico - valor_tomado
         */

        if (valorTeorico == null || valorTomado == null) {
            throw new ApiRequestException("Debes especificar el valor teorico y el valor tomado");
        }

        if (valorTeorico < valorTomado) {
            throw new ApiRequestException("El valor teorico no puede ser menor al valor tomado");
        }

        return valorTeorico - valorTomado;
    }

    public Integer valAniosAcumulados() {
        /*
         * el número de años acumulados será igual a:
         * fecha actual - fecha de ingreso
         */

        LocalDate currentDate = LocalDate.now();
        LocalDate customDate = LocalDate.parse(this.trabajador.getFechaIngreso().toString());
        LocalDate period = currentDate.minusYears(customDate.getYear());

        return period.getYear();
    }

    public Float valTotalIngresosAnio() {
        /*
         * el total ingresos al año va a ser igual al código auxiliar TI con nombre
         * total ingresos de la tabla rubros rol -> acumuladoVacaciones tabla Trabajador
         */

        return this.trabajador.getAcumuladoVacaciones();
    }

    public Integer valDiasAntiguedad(Integer aniosAcumulados) {
        /*
         * el número de años acumulados sea menor o igual a 5, entonces el número de
         * días de antigüedad será igual a 0.
         * 
         * el número de años acumulados sea mayor o igual a 6 entonces los días de
         * antigüedad van a ser igual a: dias_antiguedad = dias_antiguedad +1 hasta que
         * llegue a 15 días.
         */

        Integer aniosCorte = 6;

        if (aniosAcumulados >= aniosCorte) {
            Integer dias = 0;

            while (dias <= aniosAcumulados) {
                dias++;
            }

            dias -= aniosCorte;

            if (dias < 15) {
                return dias;
            } else {
                return 15;
            }
        } else {
            return 0;
        }
    }

    public Integer valDiasTeoricos(Integer diasVacaciones, Integer diasAntiguedad) {
        /*
         * Los días teóricos van a ser igual a la suma de los días de vacaciones y los
         * días de antigüedad
         */
        return diasVacaciones + diasAntiguedad;
    }

    public Trabajador getTrabajador() {
        return this.trabajador;
    }

}
