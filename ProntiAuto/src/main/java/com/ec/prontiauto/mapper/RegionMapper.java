package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.RegionRequestDao;
import com.ec.prontiauto.dao.RegionResponseDao;
import com.ec.prontiauto.entidad.Region;

public class RegionMapper {
    public static Function<RegionRequestDao, Region> setDaoRequestToEntity;
    public static Function<Region, RegionResponseDao> setEntityToDaoResponse;
    public static Function<Region, RegionResponseDao> setEntityToDaoReference;
    public static Function<List<Region>, List<RegionResponseDao>> setEntityListToDaoResponseList;
    public static Function<List<Region>, List<RegionResponseDao>> setEntityListToDaoReferenceList;
    static {
        setDaoRequestToEntity = (daoRequest -> {
            Region entity = new Region();
            entity.setId(daoRequest.getId());
            entity.setNombre(daoRequest.getNombre());
            entity.setProvincia(daoRequest.getProvincia());
            entity.setCiudad(daoRequest.getCiudad());
            entity.setSisHabilitado(daoRequest.getSisHabilitado());
            return entity;
        });
        setEntityToDaoResponse = (entity -> {
            RegionResponseDao dao = new RegionResponseDao();
            dao.setId(entity.getId());
            dao.setNombre(entity.getNombre());
            dao.setProvincia(entity.getProvincia());
            dao.setCiudad(entity.getCiudad());
            dao.setSisHabilitado(entity.getSisHabilitado());
            if (entity.getAgenciaCollection() != null) {
                dao.setAgenciaCollection(
                        AgenciaMapper.setEntityListToDaoResponseList.apply(entity.getAgenciaCollection()));
            }
            return dao;
        });
        setEntityToDaoReference = (entity -> {
            RegionResponseDao dao = new RegionResponseDao();
            dao.setId(entity.getId());
            dao.setNombre(entity.getNombre());
            dao.setProvincia(entity.getProvincia());
            dao.setCiudad(entity.getCiudad());
            dao.setSisHabilitado(entity.getSisHabilitado());
            return dao;
        });
        setEntityListToDaoResponseList = (entityList -> {
            return StreamSupport.stream(entityList.spliterator(), false)
                    .map(RegionMapper.setEntityToDaoResponse).collect(Collectors.toList());
        });
        setEntityListToDaoReferenceList = (entityList -> {
            return StreamSupport.stream(entityList.spliterator(), false)
                    .map(RegionMapper.setEntityToDaoReference).collect(Collectors.toList());
        });
    }
}
