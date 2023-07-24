package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.AbonoPrestamoRequestDao;
import com.ec.prontiauto.dao.AbonoPrestamoResponseDao;
import com.ec.prontiauto.entidad.AbonoPrestamo;
import com.ec.prontiauto.entidad.Prestamo;

public class AbonoPrestamoMapper {

    public static Function<AbonoPrestamoRequestDao, AbonoPrestamo> setDaoRequestToEntity;
    public static Function<AbonoPrestamo, AbonoPrestamoResponseDao> setEntityToDaoResponse;
    public static Function<AbonoPrestamo, AbonoPrestamoResponseDao> setEntityToDaoReference;
    public static Function<List<AbonoPrestamo>, List<AbonoPrestamoResponseDao>> setEntityListToDaoResponseList;
    public static Function<List<AbonoPrestamo>, List<AbonoPrestamoResponseDao>> setEntityListToDaoReferenceList;

    static {
        setDaoRequestToEntity = (daoRequest -> {
            AbonoPrestamo entity = new AbonoPrestamo();
            entity.setFechaPago(daoRequest.getFechaPago());
            entity.setNumeroPago(daoRequest.getNumeroPago());
            entity.setModalidadDescuento(daoRequest.getModalidadDescuento());
            entity.setMes(daoRequest.getMes());
            entity.setAnio(daoRequest.getAnio());
            entity.setValorCuota(daoRequest.getValorCuota());
            entity.setValorCapital(daoRequest.getValorCapital());
            entity.setValorTasaInteres(daoRequest.getValorTasaInteres());
            entity.setEstaPagado(daoRequest.getEstaPagado());
            Prestamo prestamo = new Prestamo();
            prestamo.setId(daoRequest.getIdPrestamo());
            entity.setIdPrestamo(prestamo);
            entity.setSisHabilitado(daoRequest.getSisHabilitado());
            return entity;
        });

        setEntityToDaoResponse = (entity -> {
            AbonoPrestamoResponseDao dao = new AbonoPrestamoResponseDao();
            dao.setId(entity.getId());
            dao.setFechaPago(entity.getFechaPago());
            dao.setNumeroPago(entity.getNumeroPago());
            dao.setModalidadDescuento(entity.getModalidadDescuento());
            dao.setMes(entity.getMes());
            dao.setAnio(entity.getAnio());
            dao.setValorCuota(entity.getValorCuota());
            dao.setValorCapital(entity.getValorCapital());
            dao.setValorTasaInteres(entity.getValorTasaInteres());
            dao.setEstaPagado(entity.getEstaPagado());
            dao.setIdPrestamo(PrestamoMapper.setEntityToDaoReference.apply(entity.getIdPrestamo()));
            dao.setSisHabilitado(entity.getSisHabilitado());
            dao.setSisActualizado(entity.getSisActualizado());
            dao.setSisCreado(entity.getSisCreado());
            return dao;
        });
        setEntityToDaoReference = (entity -> {
            AbonoPrestamoResponseDao dao = new AbonoPrestamoResponseDao();
            dao.setId(entity.getId());
            dao.setFechaPago(entity.getFechaPago());
            dao.setNumeroPago(entity.getNumeroPago());
            dao.setModalidadDescuento(entity.getModalidadDescuento());
            dao.setMes(entity.getMes());
            dao.setAnio(entity.getAnio());
            dao.setValorCuota(entity.getValorCuota());
            dao.setValorCapital(entity.getValorCapital());
            dao.setValorTasaInteres(entity.getValorTasaInteres());
            dao.setEstaPagado(entity.getEstaPagado());
            dao.setSisHabilitado(entity.getSisHabilitado());
            dao.setSisActualizado(entity.getSisActualizado());
            dao.setSisCreado(entity.getSisCreado());
            dao.setSisImagen(entity.getSisImagen());
            return dao;
        });
        setEntityListToDaoResponseList = (entityList -> {
            return (List<AbonoPrestamoResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(AbonoPrestamoMapper.setEntityToDaoResponse).collect(Collectors.toList());
        });
        setEntityListToDaoReferenceList = (entityList -> {
            return (List<AbonoPrestamoResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(AbonoPrestamoMapper.setEntityToDaoReference).collect(Collectors.toList());
        });

    }
}
