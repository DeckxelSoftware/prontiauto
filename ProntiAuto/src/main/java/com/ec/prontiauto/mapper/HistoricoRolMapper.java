package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.HistoricoRolRequestDao;
import com.ec.prontiauto.dao.HistoricoRolResponseDao;
import com.ec.prontiauto.entidad.HistorialLaboral;
import com.ec.prontiauto.entidad.PeriodoLaboral;
import com.ec.prontiauto.entidad.HistoricoRol;
import com.ec.prontiauto.entidad.RolPago;

public class HistoricoRolMapper {
    public static Function<HistoricoRolRequestDao, HistoricoRol> setDaoRequestToEntity;
    public static Function<HistoricoRol, HistoricoRolResponseDao> setEntityToDaoResponse;
    public static Function<List<HistoricoRol>, List<HistoricoRolResponseDao>> setEntityListToDaoResponseList;
    public static Function<List<HistoricoRol>, List<HistoricoRolResponseDao>> setEntityListToDaoReferenceList;
    public static Function<HistoricoRol, HistoricoRolResponseDao> setEntityToDaoReference;

    static {
        setDaoRequestToEntity = (daoRequest -> {
            HistoricoRol entity = new HistoricoRol();
            entity.setId(daoRequest.getId());
            entity.setSisHabilitado(daoRequest.getSisHabilitado());

            entity.setHoras25(daoRequest.getHoras25());
            entity.setHoras50(daoRequest.getHoras50());
            entity.setHoras100(daoRequest.getHoras100());
            entity.setRetencionesJudiciales(daoRequest.getRetencionesJudiciales());
            entity.setPolizaPersonal(daoRequest.getPolizaPersonal());
            entity.setAporteIess(daoRequest.getAporteIess());
            entity.setImpuestoRenta(daoRequest.getImpuestoRenta());
            entity.setDescuentosPorAtrasos(daoRequest.getDescuentosPorAtrasos());
            entity.setAnticipos(daoRequest.getAnticipos());
            entity.setMultas(daoRequest.getMultas());
            entity.setOtrosDescuentos(daoRequest.getOtrosDescuentos());
            entity.setCelularConsumo(daoRequest.getCelularConsumo());
            entity.setPermisoHoras(daoRequest.getPermisoHoras());
            entity.setPermisoDias(daoRequest.getPermisoDias());
            entity.setDescuentosPorFaltas(daoRequest.getDescuentosPorFaltas());
            entity.setLeySolidaria(daoRequest.getLeySolidaria());
            entity.setPrestamosEmpresa(daoRequest.getPrestamosEmpresa());
            entity.setPeluqueria(daoRequest.getPeluqueria());
            entity.setOptica(daoRequest.getOptica());
            entity.setPagoCuotaVehiculo(daoRequest.getPagoCuotaVehiculo());
            entity.setSueldo(daoRequest.getSueldo());
            entity.setBonificacion(daoRequest.getBonificacion());
            entity.setMovilizacionEspecial(daoRequest.getMovilizacionEspecial());
            entity.setComponenteSalarialUnif(daoRequest.getComponenteSalarialUnif());
            entity.setDiasFalta(daoRequest.getDiasFalta());
            entity.setDiasMaternidad(daoRequest.getDiasMaternidad());
            entity.setDiasEnfermedad(daoRequest.getDiasEnfermedad());
            entity.setDiasVacacion(daoRequest.getDiasVacacion());
            entity.setHorasAtraso(daoRequest.getHorasAtraso());
            entity.setDescuentoMaternidad(daoRequest.getDescuentoMaternidad());
            entity.setDescuentoEnfermedad(daoRequest.getDescuentoEnfermedad());
            entity.setDescuentoVacacion(daoRequest.getDescuentoVacacion());
            entity.setProvDecimoTercero(daoRequest.getProvDecimoTercero());
            entity.setProvDecimoCuarto(daoRequest.getProvDecimoCuarto());
            entity.setProvFondosReserva(daoRequest.getProvFondosReserva());
            entity.setProvVacaciones(daoRequest.getProvVacaciones());
            entity.setProvAportePatronal(daoRequest.getProvAportePatronal());
            entity.setVacacion1(daoRequest.getVacacion1());
            entity.setVacacion2(daoRequest.getVacacion2());
            entity.setPagoVacacion(daoRequest.getPagoVacacion());
            entity.setPagoFondoReservaMes(daoRequest.getPagoFondoReservaMes());
            entity.setPagoDecimoTerceroCuartoMes(daoRequest.getPagoDecimoTerceroCuartoMes());
            entity.setAcumuladoAportePersonalIess(daoRequest.getAcumuladoAportePersonalIess());
            entity.setAcumuladoDecimoTercero(daoRequest.getAcumuladoDecimoTercero());
            entity.setAcumuladoFondosReserva(daoRequest.getAcumuladoFondosReserva());
            entity.setAcumuladoVacaciones(daoRequest.getAcumuladoVacaciones());
            entity.setAcumuladoImpuestoRenta(daoRequest.getAcumuladoImpuestoRenta());
            entity.setTotalIngresos(daoRequest.getTotalIngresos());
            entity.setTotalEgresos(daoRequest.getTotalEgresos());
            entity.setTotalAPagar(daoRequest.getTotalAPagar());
            entity.setOtrosIngresos(daoRequest.getOtrosIngresos());
            entity.setPrestamosIess(daoRequest.getPrestamosIess());
            entity.setTotalHorasExtra(daoRequest.getTotalHorasExtra());
            entity.setFechaDesde(daoRequest.getFechaDesde());
            entity.setFechaHasta(daoRequest.getFechaHasta());

            entity.setRolCerrado(daoRequest.getRolCerrado());
            entity.setComision(daoRequest.getComision());
            entity.setAjustesIngreso(daoRequest.getAjustesIngreso());
            entity.setAjustesEgreso(daoRequest.getAjustesEgreso());
            entity.setIncentivosOcacionales(daoRequest.getIncentivosOcacionales());
            entity.setRetroactivo(daoRequest.getRetroactivo());
            entity.setPrestamoQuirografario(daoRequest.getPrestamoQuirografario());
            entity.setPrestamoHipotecario(daoRequest.getPrestamoHipotecario());
            entity.setPagoDecimoCuartoMes(daoRequest.getPagoDecimoCuartoMes());

            RolPago rolPago = new RolPago();
            rolPago.setId(daoRequest.getIdRolPago());
            entity.setIdRolPago(rolPago);


            return entity;
        });

        setEntityToDaoResponse = (entity -> {
            HistoricoRolResponseDao dao = new HistoricoRolResponseDao();
            dao.setId(entity.getId());
            dao.setSisHabilitado(entity.getSisHabilitado());

            dao.setHoras25(entity.getHoras25());
            dao.setHoras50(entity.getHoras50());
            dao.setHoras100(entity.getHoras100());
            dao.setRetencionesJudiciales(entity.getRetencionesJudiciales());
            dao.setPolizaPersonal(entity.getPolizaPersonal());
            dao.setAporteIess(entity.getAporteIess());
            dao.setImpuestoRenta(entity.getImpuestoRenta());
            dao.setDescuentosPorAtrasos(entity.getDescuentosPorAtrasos());
            dao.setAnticipos(entity.getAnticipos());
            dao.setMultas(entity.getMultas());
            dao.setOtrosDescuentos(entity.getOtrosDescuentos());
            dao.setCelularConsumo(entity.getCelularConsumo());
            dao.setPermisoHoras(entity.getPermisoHoras());
            dao.setPermisoDias(entity.getPermisoDias());
            dao.setDescuentosPorFaltas(entity.getDescuentosPorFaltas());
            dao.setLeySolidaria(entity.getLeySolidaria());
            dao.setPrestamosEmpresa(entity.getPrestamosEmpresa());
            dao.setPeluqueria(entity.getPeluqueria());
            dao.setOptica(entity.getOptica());
            dao.setPagoCuotaVehiculo(entity.getPagoCuotaVehiculo());
            dao.setSueldo(entity.getSueldo());
            dao.setBonificacion(entity.getBonificacion());
            dao.setMovilizacionEspecial(entity.getMovilizacionEspecial());
            dao.setComponenteSalarialUnif(entity.getComponenteSalarialUnif());
            dao.setDiasFalta(entity.getDiasFalta());
            dao.setDiasMaternidad(entity.getDiasMaternidad());
            dao.setDiasEnfermedad(entity.getDiasEnfermedad());
            dao.setDiasVacacion(entity.getDiasVacacion());
            dao.setHorasAtraso(entity.getHorasAtraso());
            dao.setDescuentoMaternidad(entity.getDescuentoMaternidad());
            dao.setDescuentoEnfermedad(entity.getDescuentoEnfermedad());
            dao.setDescuentoVacacion(entity.getDescuentoVacacion());
            dao.setProvDecimoTercero(entity.getProvDecimoTercero());
            dao.setProvDecimoCuarto(entity.getProvDecimoCuarto());
            dao.setProvFondosReserva(entity.getProvFondosReserva());
            dao.setProvVacaciones(entity.getProvVacaciones());
            dao.setProvAportePatronal(entity.getProvAportePatronal());
            dao.setVacacion1(entity.getVacacion1());
            dao.setVacacion2(entity.getVacacion2());
            dao.setPagoVacacion(entity.getPagoVacacion());
            dao.setPagoFondoReservaMes(entity.getPagoFondoReservaMes());
            dao.setPagoDecimoTerceroCuartoMes(entity.getPagoDecimoTerceroCuartoMes());
            dao.setAcumuladoAportePersonalIess(entity.getAcumuladoAportePersonalIess());
            dao.setAcumuladoDecimoTercero(entity.getAcumuladoDecimoTercero());
            dao.setAcumuladoFondosReserva(entity.getAcumuladoFondosReserva());
            dao.setAcumuladoVacaciones(entity.getAcumuladoVacaciones());
            dao.setAcumuladoImpuestoRenta(entity.getAcumuladoImpuestoRenta());
            dao.setTotalIngresos(entity.getTotalIngresos());
            dao.setTotalEgresos(entity.getTotalEgresos());
            dao.setTotalAPagar(entity.getTotalAPagar());
            dao.setOtrosIngresos(entity.getOtrosIngresos());
            dao.setPrestamosIess(entity.getPrestamosIess());
            dao.setTotalHorasExtra(entity.getTotalHorasExtra());
            dao.setFechaDesde(entity.getFechaDesde());
            dao.setFechaHasta(entity.getFechaHasta());

            dao.setRolCerrado(entity.getRolCerrado());
            dao.setComision(entity.getComision());
            dao.setAjustesIngreso(entity.getAjustesIngreso());
            dao.setAjustesEgreso(entity.getAjustesEgreso());
            dao.setIncentivosOcacionales(entity.getIncentivosOcacionales());
            dao.setRetroactivo(entity.getRetroactivo());
            dao.setPrestamoQuirografario(entity.getPrestamoQuirografario());
            dao.setPrestamoHipotecario(entity.getPrestamoHipotecario());
            dao.setPagoDecimoCuartoMes(entity.getPagoDecimoCuartoMes());

            if (entity.getIdRolPago() != null &&
                    entity.getIdRolPago().getId() != null) {
                dao.setIdRolPago(
                        RolPagoMapper.setEntityToDaoReference.apply(entity.getIdRolPago()));
            }


            return dao;

        });

        setEntityToDaoReference = (entity -> {
            HistoricoRolResponseDao dao = new HistoricoRolResponseDao();
            dao.setId(entity.getId());
            dao.setSisHabilitado(entity.getSisHabilitado());

            dao.setHoras25(entity.getHoras25());
            dao.setHoras50(entity.getHoras50());
            dao.setHoras100(entity.getHoras100());
            dao.setRetencionesJudiciales(entity.getRetencionesJudiciales());
            dao.setPolizaPersonal(entity.getPolizaPersonal());
            dao.setAporteIess(entity.getAporteIess());
            dao.setImpuestoRenta(entity.getImpuestoRenta());
            dao.setDescuentosPorAtrasos(entity.getDescuentosPorAtrasos());
            dao.setAnticipos(entity.getAnticipos());
            dao.setMultas(entity.getMultas());
            dao.setOtrosDescuentos(entity.getOtrosDescuentos());
            dao.setCelularConsumo(entity.getCelularConsumo());
            dao.setPermisoHoras(entity.getPermisoHoras());
            dao.setPermisoDias(entity.getPermisoDias());
            dao.setDescuentosPorFaltas(entity.getDescuentosPorFaltas());
            dao.setLeySolidaria(entity.getLeySolidaria());
            dao.setPrestamosEmpresa(entity.getPrestamosEmpresa());
            dao.setPeluqueria(entity.getPeluqueria());
            dao.setOptica(entity.getOptica());
            dao.setPagoCuotaVehiculo(entity.getPagoCuotaVehiculo());
            dao.setSueldo(entity.getSueldo());
            dao.setBonificacion(entity.getBonificacion());
            dao.setMovilizacionEspecial(entity.getMovilizacionEspecial());
            dao.setComponenteSalarialUnif(entity.getComponenteSalarialUnif());
            dao.setDiasFalta(entity.getDiasFalta());
            dao.setDiasMaternidad(entity.getDiasMaternidad());
            dao.setDiasEnfermedad(entity.getDiasEnfermedad());
            dao.setDiasVacacion(entity.getDiasVacacion());
            dao.setHorasAtraso(entity.getHorasAtraso());
            dao.setDescuentoMaternidad(entity.getDescuentoMaternidad());
            dao.setDescuentoEnfermedad(entity.getDescuentoEnfermedad());
            dao.setDescuentoVacacion(entity.getDescuentoVacacion());
            dao.setProvDecimoTercero(entity.getProvDecimoTercero());
            dao.setProvDecimoCuarto(entity.getProvDecimoCuarto());
            dao.setProvFondosReserva(entity.getProvFondosReserva());
            dao.setProvVacaciones(entity.getProvVacaciones());
            dao.setProvAportePatronal(entity.getProvAportePatronal());
            dao.setVacacion1(entity.getVacacion1());
            dao.setVacacion2(entity.getVacacion2());
            dao.setPagoVacacion(entity.getPagoVacacion());
            dao.setPagoFondoReservaMes(entity.getPagoFondoReservaMes());
            dao.setPagoDecimoTerceroCuartoMes(entity.getPagoDecimoTerceroCuartoMes());
            dao.setAcumuladoAportePersonalIess(entity.getAcumuladoAportePersonalIess());
            dao.setAcumuladoDecimoTercero(entity.getAcumuladoDecimoTercero());
            dao.setAcumuladoFondosReserva(entity.getAcumuladoFondosReserva());
            dao.setAcumuladoVacaciones(entity.getAcumuladoVacaciones());
            dao.setAcumuladoImpuestoRenta(entity.getAcumuladoImpuestoRenta());
            dao.setTotalIngresos(entity.getTotalIngresos());
            dao.setTotalEgresos(entity.getTotalEgresos());
            dao.setTotalAPagar(entity.getTotalAPagar());
            dao.setOtrosIngresos(entity.getOtrosIngresos());
            dao.setPrestamosIess(entity.getPrestamosIess());
            dao.setTotalHorasExtra(entity.getTotalHorasExtra());
            dao.setFechaDesde(entity.getFechaDesde());
            dao.setFechaHasta(entity.getFechaHasta());
            dao.setPagoDecimoCuartoMes(entity.getPagoDecimoCuartoMes());

            return dao;
        });

        setEntityListToDaoResponseList = (entityList -> {
            return (List<HistoricoRolResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(HistoricoRolMapper.setEntityToDaoResponse).collect(Collectors.toList());
        });

        setEntityListToDaoReferenceList = (entityList -> {
            return (List<HistoricoRolResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(HistoricoRolMapper.setEntityToDaoReference).collect(Collectors.toList());
        });
    }

}