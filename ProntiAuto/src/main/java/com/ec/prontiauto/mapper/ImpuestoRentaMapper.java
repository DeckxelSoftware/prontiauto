package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.ImpuestoRentaRequestDao;
import com.ec.prontiauto.dao.ImpuestoRentaResponseDao;
import com.ec.prontiauto.entidad.ImpuestoRenta;

public class ImpuestoRentaMapper {
    public static Function<ImpuestoRentaRequestDao, ImpuestoRenta> setDaoRequestToEntity;
    public static Function<ImpuestoRenta, ImpuestoRentaResponseDao> setEntityToDaoResponse;
    public static Function<List<ImpuestoRenta>, List<ImpuestoRentaResponseDao>> setEntityListToDaoResponseList;

    static {
        setDaoRequestToEntity = (daoRequest -> {
            ImpuestoRenta entity = new ImpuestoRenta();
            entity.setNombre(daoRequest.getNombre());
            entity.setAnio(daoRequest.getAnio());
            entity.setFraccionBasica1(daoRequest.getFraccionBasica1());
            entity.setImpuestoFraccionBasica1(daoRequest.getImpuestoFraccionBasica1());
            entity.setImpuestoFraccionExcedente1(daoRequest.getImpuestoFraccionExcedente1());
            entity.setFraccionBasica2(daoRequest.getFraccionBasica2());
            entity.setImpuestoFraccionBasica2(daoRequest.getImpuestoFraccionBasica2());
            entity.setImpuestoFraccionExcedente2(daoRequest.getImpuestoFraccionExcedente2());
            entity.setFraccionBasica3(daoRequest.getFraccionBasica3());
            entity.setImpuestoFraccionBasica3(daoRequest.getImpuestoFraccionBasica3());
            entity.setImpuestoFraccionExcedente3(daoRequest.getImpuestoFraccionExcedente3());
            entity.setFraccionBasica4(daoRequest.getFraccionBasica4());
            entity.setImpuestoFraccionBasica4(daoRequest.getImpuestoFraccionBasica4());
            entity.setImpuestoFraccionExcedente4(daoRequest.getImpuestoFraccionExcedente4());
            entity.setFraccionBasica5(daoRequest.getFraccionBasica5());
            entity.setImpuestoFraccionBasica5(daoRequest.getImpuestoFraccionBasica5());
            entity.setImpuestoFraccionExcedente5(daoRequest.getImpuestoFraccionExcedente5());
            entity.setFraccionBasica6(daoRequest.getFraccionBasica6());
            entity.setImpuestoFraccionBasica6(daoRequest.getImpuestoFraccionBasica6());
            entity.setImpuestoFraccionExcedente6(daoRequest.getImpuestoFraccionExcedente6());
            entity.setFraccionBasica7(daoRequest.getFraccionBasica7());
            entity.setImpuestoFraccionBasica7(daoRequest.getImpuestoFraccionBasica7());
            entity.setImpuestoFraccionExcedente7(daoRequest.getImpuestoFraccionExcedente7());
            entity.setFraccionBasica8(daoRequest.getFraccionBasica8());
            entity.setImpuestoFraccionBasica8(daoRequest.getImpuestoFraccionBasica8());
            entity.setImpuestoFraccionExcedente8(daoRequest.getImpuestoFraccionExcedente8());
            entity.setFraccionBasica9(daoRequest.getFraccionBasica9());
            entity.setImpuestoFraccionBasica9(daoRequest.getImpuestoFraccionBasica9());
            entity.setImpuestoFraccionExcedente9(daoRequest.getImpuestoFraccionExcedente9());
            entity.setFraccionBasica10(daoRequest.getFraccionBasica10());
            entity.setImpuestoFraccionBasica10(daoRequest.getImpuestoFraccionBasica10());
            entity.setImpuestoFraccionExcedente10(daoRequest.getImpuestoFraccionExcedente10());
            entity.setSisHabilitado(daoRequest.getSisHabilitado());
            return entity;
        });
        setEntityToDaoResponse = (entity -> {
            ImpuestoRentaResponseDao dao = new ImpuestoRentaResponseDao();
            dao.setId(entity.getId());
            dao.setNombre(entity.getNombre());
            dao.setAnio(entity.getAnio());
            dao.setFraccionBasica1(entity.getFraccionBasica1());
            dao.setImpuestoFraccionBasica1(entity.getImpuestoFraccionBasica1());
            dao.setImpuestoFraccionExcedente1(entity.getImpuestoFraccionExcedente1());
            dao.setFraccionBasica2(entity.getFraccionBasica2());
            dao.setImpuestoFraccionBasica2(entity.getImpuestoFraccionBasica2());
            dao.setImpuestoFraccionExcedente2(entity.getImpuestoFraccionExcedente2());
            dao.setFraccionBasica3(entity.getFraccionBasica3());
            dao.setImpuestoFraccionBasica3(entity.getImpuestoFraccionBasica3());
            dao.setImpuestoFraccionExcedente3(entity.getImpuestoFraccionExcedente3());
            dao.setFraccionBasica4(entity.getFraccionBasica4());
            dao.setImpuestoFraccionBasica4(entity.getImpuestoFraccionBasica4());
            dao.setImpuestoFraccionExcedente4(entity.getImpuestoFraccionExcedente4());
            dao.setFraccionBasica5(entity.getFraccionBasica5());
            dao.setImpuestoFraccionBasica5(entity.getImpuestoFraccionBasica5());
            dao.setImpuestoFraccionExcedente5(entity.getImpuestoFraccionExcedente5());
            dao.setFraccionBasica6(entity.getFraccionBasica6());
            dao.setImpuestoFraccionBasica6(entity.getImpuestoFraccionBasica6());
            dao.setImpuestoFraccionExcedente6(entity.getImpuestoFraccionExcedente6());
            dao.setFraccionBasica7(entity.getFraccionBasica7());
            dao.setImpuestoFraccionBasica7(entity.getImpuestoFraccionBasica7());
            dao.setImpuestoFraccionExcedente7(entity.getImpuestoFraccionExcedente7());
            dao.setFraccionBasica8(entity.getFraccionBasica8());
            dao.setImpuestoFraccionBasica8(entity.getImpuestoFraccionBasica8());
            dao.setImpuestoFraccionExcedente8(entity.getImpuestoFraccionExcedente8());
            dao.setFraccionBasica9(entity.getFraccionBasica9());
            dao.setImpuestoFraccionBasica9(entity.getImpuestoFraccionBasica9());
            dao.setImpuestoFraccionExcedente9(entity.getImpuestoFraccionExcedente9());
            dao.setFraccionBasica10(entity.getFraccionBasica10());
            dao.setImpuestoFraccionBasica10(entity.getImpuestoFraccionBasica10());
            dao.setImpuestoFraccionExcedente10(entity.getImpuestoFraccionExcedente10());
            dao.setSisHabilitado(entity.getSisHabilitado());
            dao.setSisActualizado(entity.getSisActualizado());
            dao.setSisCreado(entity.getSisCreado());
            return dao;
        });
        setEntityListToDaoResponseList = (entityList -> {
            return (List<ImpuestoRentaResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(ImpuestoRentaMapper.setEntityToDaoResponse).collect(Collectors.toList());
        });
    }
}
