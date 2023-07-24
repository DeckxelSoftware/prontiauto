package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.AreaRequestDao;
import com.ec.prontiauto.dao.AreaResponseDao;
import com.ec.prontiauto.entidad.Area;

public class AreaMapper {
    public static Function<AreaRequestDao, Area> setDaoRequestToEntity;
    public static Function<Area, AreaResponseDao> setEntityToDaoResponse;
    public static Function<Area, AreaResponseDao> setEntityToDaoReference;
    public static Function<List<Area>, List<AreaResponseDao>> setEntityListToDaoResponseList;

    static {
        setDaoRequestToEntity = (daoRequest -> {
        	Area entity = new Area();
            entity.setId(daoRequest.getId());
            entity.setNombre(daoRequest.getNombre());
            entity.setSisHabilitado(daoRequest.getSisHabilitado());
            return entity;
        });

        setEntityToDaoResponse = (entity -> {
        	AreaResponseDao dao = new AreaResponseDao();
            dao.setId(entity.getId());
            dao.setNombre(entity.getNombre());
            dao.setSisHabilitado(entity.getSisHabilitado());
            if (entity.getCargoCollection() != null) {
                dao.setCargoCollection(
                        CargoMapper.setEntityListToDaoResponseList.apply(entity.getCargoCollection()));
            }
            dao.setSisActualizado(entity.getSisActualizado());
            dao.setSisCreado(entity.getSisCreado());
            dao.setSisImagen(entity.getSisImagen());
            return dao;
        });
        setEntityToDaoReference = (entity -> {
        	AreaResponseDao dao = new AreaResponseDao();
            dao.setId(entity.getId());
            dao.setNombre(entity.getNombre());
            dao.setSisHabilitado(entity.getSisHabilitado());
            dao.setSisActualizado(entity.getSisActualizado());
            dao.setSisCreado(entity.getSisCreado());
            dao.setSisImagen(entity.getSisImagen());
            return dao;
        });

        setEntityListToDaoResponseList = (entityList -> {
            return (List<AreaResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(AreaMapper.setEntityToDaoResponse).collect(Collectors.toList());
        });
    }
}
