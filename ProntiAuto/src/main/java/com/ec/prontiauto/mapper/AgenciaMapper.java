package com.ec.prontiauto.mapper;

import com.ec.prontiauto.dao.AgenciaRequestDao;
import com.ec.prontiauto.dao.AgenciaResponseDao;
import com.ec.prontiauto.entidad.Agencia;
import com.ec.prontiauto.entidad.Region;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AgenciaMapper {
    public static Function<AgenciaRequestDao, Agencia> setDaoRequestToEntity;
    public static Function<Agencia, AgenciaResponseDao> setEntityToDaoResponse;
    public static Function<List<Agencia>, List<AgenciaResponseDao>> setEntityListToDaoResponseList;
    public static Function<Agencia, AgenciaResponseDao> setEntityToDaoReference;

    static {
        setDaoRequestToEntity = (daoRequest -> {
            Agencia entity = new Agencia();
            entity.setId(daoRequest.getId());
            entity.setNombre(daoRequest.getNombre());
            entity.setDireccion(daoRequest.getDireccion());
            Region region = new Region();
            region.setId(daoRequest.getIdRegion());
            entity.setIdRegion(region);
            entity.setCiudad(daoRequest.getCiudad());
            entity.setSisHabilitado(daoRequest.getSisHabilitado());
            return entity;
        });
        setEntityToDaoResponse = (entity -> {
            AgenciaResponseDao dao = new AgenciaResponseDao();
            dao.setId(entity.getId());
            dao.setNombre(entity.getNombre());
            dao.setDireccion(entity.getDireccion());
            dao.setCiudad(entity.getCiudad());
            if (entity.getVendedorCollection() != null) {
                dao.setVendedorCollection(VendedorMapper.setEntityListToDaoResponseList
                        .apply(entity.getVendedorCollection()));
            }
            if (entity.getSupervisorCollection() != null) {
                dao.setSupervisorCollection(SupervisorMapper.setEntityListToDaoResponseList
                        .apply(entity.getSupervisorCollection()));
            }
            if (entity.getIdRegion() != null) {
                dao.setIdRegion(RegionMapper.setEntityToDaoReference.apply(entity.getIdRegion()));
            }
            dao.setSisHabilitado(entity.getSisHabilitado());
            dao.setSisActualizado(entity.getSisActualizado());
            dao.setSisCreado(entity.getSisCreado());
            dao.setSisArchivo(entity.getSisArchivo());
            dao.setSisImagen(entity.getSisImagen());
            return dao;
        });

        setEntityToDaoReference = (entity -> {
            AgenciaResponseDao dao = new AgenciaResponseDao();
            dao.setId(entity.getId());
            dao.setNombre(entity.getNombre());
            dao.setDireccion(entity.getDireccion());
            dao.setCiudad(entity.getCiudad());
            dao.setSisHabilitado(entity.getSisHabilitado());
            dao.setSisActualizado(entity.getSisActualizado());
            dao.setSisCreado(entity.getSisCreado());
            dao.setSisArchivo(entity.getSisArchivo());
            dao.setSisImagen(entity.getSisImagen());
            return dao;
        });

        setEntityListToDaoResponseList = (entityList) -> entityList.stream().map(AgenciaMapper.setEntityToDaoResponse).collect(Collectors.toList());
    }
}
