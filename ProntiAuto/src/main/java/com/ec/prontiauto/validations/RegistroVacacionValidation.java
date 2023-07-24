package com.ec.prontiauto.validations;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.ec.prontiauto.entidad.CargoVacacion;
import com.ec.prontiauto.entidad.RegistroVacacion;
import com.ec.prontiauto.exception.ApiRequestException;

public class RegistroVacacionValidation {

    EntityManager em;
    Integer idCargoVacacion;
    CargoVacacion cargoVacacion;
    private RegistroVacacion entity;

    public RegistroVacacionValidation(EntityManager em, Integer idCargoVacacion, RegistroVacacion entity,
            Boolean isOnlyPost) {
        this.em = em;
        this.idCargoVacacion = idCargoVacacion;
        this.entity = entity;

        this.getCargoVacacion();

        if (isOnlyPost) {
            this.initOnlyPost();
        } else {
            this.init();
        }

    }

    private void init() {

    }

    private void initOnlyPost() {
        valCreateRegistroVacacion();
        valDiasTomados();
        valFechaHasta();
        valValorTomado();
        valorPagado();
        valDataOnCreate();
    }

    private void getCargoVacacion() {
        try {
            Query query = this.em.createQuery("select cv from CargoVacacion cv where id =:id");
            query.setParameter("id", this.idCargoVacacion);
            this.cargoVacacion = (CargoVacacion) query.getSingleResult();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ApiRequestException("No se encontro el cargoVacacion asociado al id ingresado");
        }
    }

    public void valCreateRegistroVacacion() {
        /*
         * el número de dìas tomados sea igual al número de días teóricos
         * no se permitirá crear registros de vacaciones para el cargo de vacaciòn
         */

        if (this.cargoVacacion.getDiasTeoricos() == this.cargoVacacion.getDiasTomados()) {
            throw new ApiRequestException(
                    "No se permite crear registro de vacaciones cuando los dias tomados son iguales a los dias teoricos");
        }
    }

    private void valDiasTomados() {
        /*
         * dias_tomados (de la tabla cargo_vacacion) = dias_tomados (de la tabla
         * registro_vacacion)
         */

        Integer diasTomados = this.entity.getDiasTomados();

        if (diasTomados != null) {
            cargoVacacion.setDiasTomados(diasTomados);
            em.merge(cargoVacacion);
        }
    }

    private void valFechaHasta() {
        /*
         * Cuando el usuario coloque la fecha desde.
         * Entonces la fecha hasta será igual a la fecha desde más los días tomados.
         * fecha_hasta = fecha_desde + dias_tomados
         */

        Date fecha_desde = entity.getFechaDesde();
        Integer diasTomados = entity.getDiasTomados();

        if (fecha_desde != null && diasTomados != null) {
            LocalDate customDate = LocalDate.parse(fecha_desde.toString());
            entity.setFechaHasta(Date.valueOf(customDate.plusDays(diasTomados)));
        }
    }

    private void valValorTomado() {
        /*
         * Valor tomado será igual a valor días de la tabla cargo vacación por dias
         * tomados de la tabla registro vacación.
         */

        Float valorDias = this.cargoVacacion.getValorDias();
        Integer diasTomados = entity.getDiasTomados();

        if (valorDias != null && diasTomados != null) {
            entity.setValorTomado(diasTomados * valorDias);
        }
    }

    private void valorPagado() {
        /*
         * Cuando el usuario seleccione sí en esta pagado
         * Entonces se habilitan los campos para colocar fecha de pago y comprobante de
         * pago
         * Y el valor pagado será igual al valor tomado
         * 
         * Y si el usuario selecciona no en esta pagado
         * Entonces los campos para colocar fecha de pago y comprobante no se
         * habilitarán Y el valor pagado será igual a 0
         * 
         */

        Boolean estaPagado = entity.getEstaPagado() != null & entity.getEstaPagado().equals("S");

        if (estaPagado && (entity.getFechaPago() == null || entity.getComprobantePago() == null)) {
            throw new ApiRequestException("Debe ingresar la fecha de pago y el comprobante de pago");
        }

        Float valorPagado = 0.00f;

        if (estaPagado) {
            valorPagado = entity.getValorTomado();
        }

        entity.setValorPagado(valorPagado);
    }

    private void valDataOnCreate() {
        /*
         * Cuando el registro de vacación es creado.
         * Entonces en el registro cargo vacación se actualizan los valores de días
         * tomados que será igual a la suma de los días tomados en los registros
         * vacaciones.
         * dias_tomados (tabla cargo_vacacion) = dias_tomados (tabla registro_vacacion)
         * 
         * Y días saldos será igual a la diferencia entre días teóricos y días tomados
         * dias_saldo = dias_teoricos - dias_tomados (tabla cargo_vacacion)
         * 
         * Y valor tomado será igual a días tomados * valor días
         * valor_tomado = dias_tomados (tabla cargo_vacacion) *valor_dias
         * 
         * Y el valor saldo será igual a valor teórico - valor tomado
         * valor_saldo = valor_teorico - valor_tomado
         */

        Integer diasTomados = entity.getDiasTomados();
        if (diasTomados != null) {
            this.cargoVacacion.setDiasTomados(diasTomados);
        }

        Integer diasTeoricos = this.cargoVacacion.getDiasTeoricos();
        if (diasTeoricos != null && diasTomados != null) {
            this.cargoVacacion.setDiasSaldo(diasTeoricos - diasTomados);
        }

        Float valorDias = this.cargoVacacion.getValorDias();
        if (diasTomados != null && valorDias != null) {
            this.cargoVacacion.setValorTomado(diasTomados * valorDias);
        }

        Float valorTeorico = this.cargoVacacion.getValorTeorico();
        Float valorTomado = this.cargoVacacion.getValorTomado();
        if (valorTeorico != null && valorTomado != null) {
            this.cargoVacacion.setValorSaldo(valorTeorico - valorTomado);
        }

        em.merge(cargoVacacion);

    }

    public RegistroVacacion getEntity() {
        this.entity.setIdCargoVacacion(cargoVacacion);
        return this.entity;
    }
}