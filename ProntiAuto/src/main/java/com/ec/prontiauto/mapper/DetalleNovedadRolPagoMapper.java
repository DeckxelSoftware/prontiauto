package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.DetalleNovedadRolPagoRequestDao;
import com.ec.prontiauto.dao.DetalleNovedadRolPagoResponseDao;
import com.ec.prontiauto.entidad.DetalleNovedadRolPago;
import com.ec.prontiauto.entidad.PeriodoLaboral;
import com.ec.prontiauto.entidad.RubrosRol;
import com.ec.prontiauto.entidad.Trabajador;

public class DetalleNovedadRolPagoMapper {
    public static Function<DetalleNovedadRolPagoRequestDao, DetalleNovedadRolPago> setDaoRequestToEntity;
    public static Function<DetalleNovedadRolPago, DetalleNovedadRolPagoResponseDao> setEntityToDaoResponse;
    public static Function<DetalleNovedadRolPago, DetalleNovedadRolPagoResponseDao> setEntityToDaoReference;
    public static Function<List<DetalleNovedadRolPago>, List<DetalleNovedadRolPagoResponseDao>> setEntityListToDaoResponseList;
    public static Function<List<DetalleNovedadRolPago>, List<DetalleNovedadRolPagoResponseDao>> setEntityListToDaoReferenceList;

    static {
        setDaoRequestToEntity = (daoRequest -> {
            DetalleNovedadRolPago entity = new DetalleNovedadRolPago();
            entity.setCodigoNovedad(daoRequest.getCodigoNovedad());
            entity.setTipoNovedad(daoRequest.getTipoNovedad());
            entity.setValor(daoRequest.getValor());
            entity.setConcepto(daoRequest.getConcepto());
            RubrosRol rubrosRol = new RubrosRol();
            rubrosRol.setId(daoRequest.getIdRubrosRol());
            entity.setIdRubrosRol(rubrosRol);
            Trabajador trabajador = new Trabajador();
            trabajador.setId(daoRequest.getIdTrabajador());
            entity.setIdTrabajador(trabajador);
            PeriodoLaboral periodoLaboral = new PeriodoLaboral();
            periodoLaboral.setId(daoRequest.getIdPeriodoLaboral());
            entity.setIdPeriodoLaboral(periodoLaboral);
            entity.setSisHabilitado(daoRequest.getSisHabilitado());
            return entity;
        });

        setEntityToDaoResponse = (entity -> {
            DetalleNovedadRolPagoResponseDao daoResponse = new DetalleNovedadRolPagoResponseDao();
            daoResponse.setId(entity.getId());
            daoResponse.setCodigoNovedad(entity.getCodigoNovedad());
            daoResponse.setTipoNovedad(entity.getTipoNovedad());
            daoResponse.setValor(entity.getValor());
            daoResponse.setConcepto(entity.getConcepto());
            daoResponse.setSisHabilitado(entity.getSisHabilitado());

            if (entity.getOnlyChildrenData() == null) {
                if (entity.getIdRubrosRol() != null) {
                    daoResponse.setIdRubrosRol(RubrosRolMapper.setEntityToDaoResponse.apply(entity.getIdRubrosRol()));
                }
                if (entity.getIdTrabajador() != null) {
                    daoResponse
                            .setIdTrabajador(TrabajadorMapper.setEntityToDaoResponse.apply(entity.getIdTrabajador()));
                }
                if (entity.getIdPeriodoLaboral() != null) {
                    daoResponse.setIdPeriodoLaboral(
                            PeriodoLaboralMapper.setEntityToDaoResponse.apply(entity.getIdPeriodoLaboral()));
                }
            }
            return daoResponse;
        });

        setEntityToDaoReference = (entity -> {
            DetalleNovedadRolPagoResponseDao daoResponse = new DetalleNovedadRolPagoResponseDao();
            daoResponse.setId(entity.getId());
            daoResponse.setCodigoNovedad(entity.getCodigoNovedad());
            daoResponse.setTipoNovedad(entity.getTipoNovedad());
            daoResponse.setValor(entity.getValor());
            daoResponse.setConcepto(entity.getConcepto());
            if (entity.getIdRubrosRol() != null) {
                daoResponse.setIdRubrosRol(RubrosRolMapper.setEntityToDaoReference.apply(entity.getIdRubrosRol()));
            }
            if (entity.getIdTrabajador() != null) {
                daoResponse.setIdTrabajador(TrabajadorMapper.setEntityToDaoReference.apply(entity.getIdTrabajador()));
            }
            if (entity.getIdPeriodoLaboral() != null) {
                daoResponse.setIdPeriodoLaboral(
                        PeriodoLaboralMapper.setEntityToDaoReference.apply(entity.getIdPeriodoLaboral()));
            }
            daoResponse.setSisHabilitado(entity.getSisHabilitado());
            return daoResponse;
        });

        setEntityListToDaoResponseList = (entityList -> {
            return (List<DetalleNovedadRolPagoResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(DetalleNovedadRolPagoMapper.setEntityToDaoResponse).collect(Collectors.toList());
        });

        setEntityListToDaoReferenceList = (entityList -> {
            return (List<DetalleNovedadRolPagoResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(DetalleNovedadRolPagoMapper.setEntityToDaoReference).collect(Collectors.toList());
        });
    }
}
