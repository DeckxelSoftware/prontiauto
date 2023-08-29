package com.ec.prontiauto.mapper;

import com.ec.prontiauto.dao.RecursoRequestDao;
import com.ec.prontiauto.dao.RecursoResponseDao;
import com.ec.prontiauto.entidad.Recurso;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RecursoMapper {
    public static Function<RecursoRequestDao, Recurso> setDaoRequestToEntity;
    public static Function<Recurso, RecursoResponseDao> setEntityToDaoResponse;
    public static Function<List<Recurso>, List<RecursoResponseDao>> setEntityListToDaoResponseList;

    static {
        setDaoRequestToEntity = ( daoRequest -> {
           Recurso recurso = new Recurso();
           recurso.setNombre(daoRequest.getNombre());
           recurso.setId(daoRequest.getId());
           recurso.setSisHabilitado(Objects.isNull(daoRequest.getSisHabilitado()) ? "A" : daoRequest.getSisHabilitado());
           return recurso;
        });

        setEntityToDaoResponse = (entity -> {
            RecursoResponseDao responseDao = new RecursoResponseDao();
            responseDao.setId(entity.getId());
            responseDao.setSisHabilitado(entity.getSisHabilitado());
            responseDao.setNombre(entity.getNombre());
            return responseDao;
        });

        setEntityListToDaoResponseList = (entityList ->
           entityList.stream().map(RecursoResponseDao::new).collect(Collectors.toList())
        );
    }
}
