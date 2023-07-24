package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.GrupoRequestDao;
import com.ec.prontiauto.dao.GrupoResponseDao;
import com.ec.prontiauto.entidad.Grupo;

public class GrupoMapper {
    public static Function<GrupoRequestDao, Grupo> setDaoRequestToEntity;
    public static Function<Grupo, GrupoResponseDao> setEntityToDaoResponse;
    public static Function<Grupo, GrupoResponseDao> setEntityToDaoReference;
    public static Function<List<Grupo>, List<GrupoResponseDao>> setEntityListToDaoResponseList;

    static {
        setDaoRequestToEntity = (daoRequest -> {
            Grupo entity = new Grupo();
            entity.setId(daoRequest.getId());
            entity.setNombreGrupo(daoRequest.getNombreGrupo());
            entity.setSumatoriaMontoMeta(daoRequest.getSumatoriaMontoMeta());
            entity.setTotalContratosUsados(daoRequest.getTotalContratosUsados());
            entity.setTotalContratosPermitidos(daoRequest.getTotalContratosPermitidos());
            entity.setSisHabilitado(daoRequest.getSisHabilitado());
            return entity;
        });
        setEntityToDaoResponse = (entity -> {
            GrupoResponseDao dao = new GrupoResponseDao();
            dao.setId(entity.getId());
            dao.setNombreGrupo(entity.getNombreGrupo());
            dao.setSumatoriaMontoMeta(entity.getSumatoriaMontoMeta());
            dao.setTotalContratosUsados(entity.getTotalContratosUsados());
            dao.setTotalContratosPermitidos(entity.getTotalContratosPermitidos());
            dao.setSisHabilitado(entity.getSisHabilitado());
            dao.setSisActualizado(entity.getSisActualizado());
            dao.setSisCreado(entity.getSisCreado());
            dao.setSisArchivo(entity.getSisArchivo());
            dao.setSisImagen(entity.getSisImagen());
            if (entity.getClienteEnGrupoCollection() != null) {
                dao.setClienteEnGrupoCollection(ClienteEnGrupoMapper.setEntityListToDaoReferenceList
                        .apply(entity.getClienteEnGrupoCollection()));
            }
            return dao;
        });

        setEntityToDaoReference = (entity -> {
            GrupoResponseDao dao = new GrupoResponseDao();
            dao.setId(entity.getId());
            dao.setNombreGrupo(entity.getNombreGrupo());
            dao.setSumatoriaMontoMeta(entity.getSumatoriaMontoMeta());
            dao.setTotalContratosUsados(entity.getTotalContratosUsados());
            dao.setTotalContratosPermitidos(entity.getTotalContratosPermitidos());
            dao.setSisHabilitado(entity.getSisHabilitado());
            dao.setSisActualizado(entity.getSisActualizado());
            dao.setSisCreado(entity.getSisCreado());
            dao.setSisArchivo(entity.getSisArchivo());
            dao.setSisImagen(entity.getSisImagen());
            return dao;
        });

        setEntityListToDaoResponseList = (entityList -> {
            return (List<GrupoResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(GrupoMapper.setEntityToDaoResponse).collect(Collectors.toList());
        });
    }
}
