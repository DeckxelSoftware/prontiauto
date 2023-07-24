package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.PagosDosRequestDao;
import com.ec.prontiauto.dao.PagosDosResponseDao;
import com.ec.prontiauto.entidad.*;

public class PagosDosMapper {
    public static Function<PagosDosRequestDao, PagosDos> setDaoRequestToEntity;
    public static Function<PagosDos, PagosDosResponseDao> setEntityToDaoResponse;
    public static Function<List<PagosDos>, List<PagosDosResponseDao>> setEntityListToDaoResponseList;
    public static Function<List<PagosDos>, List<PagosDosResponseDao>> setEntityListToDaoReferenceList;
    public static Function<PagosDos, PagosDosResponseDao> setEntityToDaoReference;

    static {
        setDaoRequestToEntity = (daoRequest -> {
            PagosDos entity = new PagosDos();
            entity.setId(daoRequest.getId());
            entity.setSisHabilitado(daoRequest.getSisHabilitado());

            entity.setDiasLaboradosAlAnio(daoRequest.getDiasLaboradosAlAnio());
            entity.setValorReal(daoRequest.getValorReal());
            entity.setValorNominal(daoRequest.getValorNominal());
            entity.setOtrosIngresos(daoRequest.getOtrosIngresos());
            entity.setTotalIngresos(daoRequest.getTotalIngresos());
            entity.setOtrosDescuentos(daoRequest.getOtrosDescuentos());
            entity.setMultas(daoRequest.getMultas());
            entity.setTotalEgresos(daoRequest.getTotalEgresos());
            entity.setValorAPagar(daoRequest.getValorAPagar());
            entity.setFechaInicio(daoRequest.getFechaInicio());
            entity.setFechaFin(daoRequest.getFechaFin());
            entity.setAnioPago(daoRequest.getAnioPago());
            entity.setValorMes1(daoRequest.getValorMes1());
            entity.setValorMes2(daoRequest.getValorMes2());
            entity.setValorMes3(daoRequest.getValorMes3());
            entity.setValorMes4(daoRequest.getValorMes4());
            entity.setValorMes5(daoRequest.getValorMes5());
            entity.setValorMes6(daoRequest.getValorMes6());
            entity.setValorMes7(daoRequest.getValorMes7());
            entity.setValorMes8(daoRequest.getValorMes8());
            entity.setValorMes9(daoRequest.getValorMes9());
            entity.setValorMes10(daoRequest.getValorMes10());
            entity.setValorMes11(daoRequest.getValorMes11());
            entity.setValorMes12(daoRequest.getValorMes12());
            entity.setPrestamosEmpresa(daoRequest.getPrestamosEmpresa());
            entity.setAnticipos(daoRequest.getAnticipos());
            entity.setFechaActual(daoRequest.getFechaActual());

            if(daoRequest.getIdPagosUno() != null) {
                PagosUno pagosUno = new PagosUno();
                pagosUno.setId(daoRequest.getIdPagosUno());
                entity.setIdPagosUno(pagosUno);
            }

            if(daoRequest.getIdTrabajador() != null) {
                Trabajador trabajador = new Trabajador();
                trabajador.setId(daoRequest.getIdTrabajador());
                entity.setIdTrabajador(trabajador);
            }

            if(daoRequest.getIdPeriodoLaboral() != null){
                PeriodoLaboral periodoLaboral = new PeriodoLaboral();
                periodoLaboral.setId(daoRequest.getIdPeriodoLaboral());
                entity.setIdPeriodoLaboral(periodoLaboral);
            }

            return entity;
        });

        setEntityToDaoResponse = (entity -> {
            PagosDosResponseDao dao = new PagosDosResponseDao();
            dao.setId(entity.getId());
            dao.setSisHabilitado(entity.getSisHabilitado());

            dao.setDiasLaboradosAlAnio(entity.getDiasLaboradosAlAnio());
            dao.setValorReal(entity.getValorReal());
            dao.setValorNominal(entity.getValorNominal());
            dao.setOtrosIngresos(entity.getOtrosIngresos());
            dao.setTotalIngresos(entity.getTotalIngresos());
            dao.setOtrosDescuentos(entity.getOtrosDescuentos());
            dao.setMultas(entity.getMultas());
            dao.setTotalEgresos(entity.getTotalEgresos());
            dao.setValorAPagar(entity.getValorAPagar());
            dao.setFechaInicio(entity.getFechaInicio());
            dao.setFechaFin(entity.getFechaFin());
            dao.setAnioPago(entity.getAnioPago());
            dao.setValorMes1(entity.getValorMes1());
            dao.setValorMes2(entity.getValorMes2());
            dao.setValorMes3(entity.getValorMes3());
            dao.setValorMes4(entity.getValorMes4());
            dao.setValorMes5(entity.getValorMes5());
            dao.setValorMes6(entity.getValorMes6());
            dao.setValorMes7(entity.getValorMes7());
            dao.setValorMes8(entity.getValorMes8());
            dao.setValorMes9(entity.getValorMes9());
            dao.setValorMes10(entity.getValorMes10());
            dao.setValorMes11(entity.getValorMes11());
            dao.setValorMes12(entity.getValorMes12());
            dao.setPrestamosEmpresa(entity.getPrestamosEmpresa());
            dao.setAnticipos(entity.getAnticipos());
            dao.setFechaActual(entity.getFechaActual());


            if( entity.getIdPagosUno() != null && entity.getIdPagosUno().getId() != null ){
                entity.getIdPagosUno().setOnlyChildrenData(true);
                dao.setIdPagosUno(
                        PagosUnoMapper.setEntityToDaoReference.apply(entity.getIdPagosUno())
                );
            }

            if(entity.getIdPeriodoLaboral() != null && entity.getIdPeriodoLaboral().getId() != null){
                dao.setIdPeriodoLaboral(
                        PeriodoLaboralMapper.setEntityToDaoReference.apply(entity.getIdPeriodoLaboral())
                );
            }

            if(entity.getIdTrabajador() != null && entity.getIdTrabajador().getId() != null){
                entity.getIdTrabajador().setOnlyChildrenData(true);
                dao.setIdTrabajador(
                        TrabajadorMapper.setEntityToDaoReference.apply(entity.getIdTrabajador())
                );
            }



            return dao;

        });

        setEntityToDaoReference = (entity -> {
            PagosDosResponseDao dao = new PagosDosResponseDao();
            dao.setId(entity.getId());
            dao.setSisHabilitado(entity.getSisHabilitado());

            dao.setDiasLaboradosAlAnio(entity.getDiasLaboradosAlAnio());
            dao.setValorReal(entity.getValorReal());
            dao.setValorNominal(entity.getValorNominal());
            dao.setOtrosIngresos(entity.getOtrosIngresos());
            dao.setTotalIngresos(entity.getTotalIngresos());
            dao.setOtrosDescuentos(entity.getOtrosDescuentos());
            dao.setMultas(entity.getMultas());
            dao.setTotalEgresos(entity.getTotalEgresos());
            dao.setValorAPagar(entity.getValorAPagar());
            dao.setFechaInicio(entity.getFechaInicio());
            dao.setFechaFin(entity.getFechaFin());
            dao.setAnioPago(entity.getAnioPago());
            dao.setValorMes1(entity.getValorMes1());
            dao.setValorMes2(entity.getValorMes2());
            dao.setValorMes3(entity.getValorMes3());
            dao.setValorMes4(entity.getValorMes4());
            dao.setValorMes5(entity.getValorMes5());
            dao.setValorMes6(entity.getValorMes6());
            dao.setValorMes7(entity.getValorMes7());
            dao.setValorMes8(entity.getValorMes8());
            dao.setValorMes9(entity.getValorMes9());
            dao.setValorMes10(entity.getValorMes10());
            dao.setValorMes11(entity.getValorMes11());
            dao.setValorMes12(entity.getValorMes12());
            dao.setPrestamosEmpresa(entity.getPrestamosEmpresa());
            dao.setAnticipos(entity.getAnticipos());
            dao.setFechaActual(entity.getFechaActual());



            return dao;
        });

        setEntityListToDaoResponseList = (entityList -> {
            return (List<PagosDosResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(PagosDosMapper.setEntityToDaoResponse).collect(Collectors.toList());
        });

        setEntityListToDaoReferenceList = (entityList -> {
            return (List<PagosDosResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(PagosDosMapper.setEntityToDaoReference).collect(Collectors.toList());
        });
    }

}