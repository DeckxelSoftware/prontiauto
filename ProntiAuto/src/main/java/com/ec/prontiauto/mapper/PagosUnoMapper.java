package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.PagosUnoRequestDao;
import com.ec.prontiauto.dao.PagosUnoResponseDao;
import com.ec.prontiauto.entidad.PagosUno;

public class PagosUnoMapper {
    public static Function<PagosUnoRequestDao, PagosUno> setDaoRequestToEntity;
    public static Function<PagosUno, PagosUnoResponseDao> setEntityToDaoResponse;
    public static Function<List<PagosUno>, List<PagosUnoResponseDao>> setEntityListToDaoResponseList;
    public static Function<List<PagosUno>, List<PagosUnoResponseDao>> setEntityListToDaoReferenceList;
    public static Function<PagosUno, PagosUnoResponseDao> setEntityToDaoReference;

    static {
        setDaoRequestToEntity = (daoRequest -> {
            PagosUno entity = new PagosUno();
            entity.setId(daoRequest.getId());
            entity.setSisHabilitado(daoRequest.getSisHabilitado());

            entity.setFechaUltimoPago(daoRequest.getFechaUltimoPago());
            entity.setFechaInicio(daoRequest.getFechaInicio());
            entity.setFechaFin(daoRequest.getFechaFin());
            entity.setNombre(daoRequest.getNombre());
            entity.setPeriodo(daoRequest.getPeriodo());

            return entity;
        });

        setEntityToDaoResponse = (entity -> {
            PagosUnoResponseDao dao = new PagosUnoResponseDao();

            dao.setId(entity.getId());
            dao.setSisHabilitado(entity.getSisHabilitado());

            dao.setFechaUltimoPago(entity.getFechaUltimoPago());
            dao.setFechaInicio(entity.getFechaInicio());
            dao.setFechaFin(entity.getFechaFin());
            dao.setNombre(entity.getNombre());
            dao.setPeriodo(entity.getPeriodo());

            if (entity.getPagosDosCollection() != null) {
                entity.getPagosDosCollection().forEach(pagosDos -> {
                    pagosDos.setOnlyChildrenData(true);
                });
                dao.setPagosDosCollection(
                        PagosDosMapper.setEntityListToDaoResponseList.apply(entity.getPagosDosCollection()));
            }

            return dao;

        });

        setEntityToDaoReference = (entity -> {
            PagosUnoResponseDao dao = new PagosUnoResponseDao();
            dao.setId(entity.getId());
            dao.setSisHabilitado(entity.getSisHabilitado());

            dao.setFechaUltimoPago(entity.getFechaUltimoPago());
            dao.setFechaInicio(entity.getFechaInicio());
            dao.setFechaFin(entity.getFechaFin());
            dao.setNombre(entity.getNombre());
            dao.setPeriodo(entity.getPeriodo());

            return dao;
        });

        setEntityListToDaoResponseList = (entityList -> {
            return (List<PagosUnoResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(PagosUnoMapper.setEntityToDaoResponse).collect(Collectors.toList());
        });

        setEntityListToDaoReferenceList = (entityList -> {
            return (List<PagosUnoResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(PagosUnoMapper.setEntityToDaoReference).collect(Collectors.toList());
        });
    }

}