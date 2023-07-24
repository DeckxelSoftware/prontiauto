package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.ArticuloRequestDao;
import com.ec.prontiauto.dao.ArticuloResponseDao;
import com.ec.prontiauto.entidad.Articulo;

public class ArticuloMapper {
    public static Function<ArticuloRequestDao, Articulo> setDaoRequestToEntity;
    public static Function<Articulo, ArticuloResponseDao> setEntityToDaoResponse;
    public static Function<Articulo, ArticuloResponseDao> setEntityToDaoReference;
    public static Function<List<Articulo>, List<ArticuloResponseDao>> setEntityListToDaoResponseList;

    static {
        setDaoRequestToEntity = (daoRequest -> {
            Articulo entity = new Articulo();
            entity.setId(daoRequest.getId());
            entity.setPlaca(daoRequest.getPlaca());
            entity.setChasis(daoRequest.getChasis());
            entity.setMarca(daoRequest.getMarca());
            entity.setModelo(daoRequest.getModelo());
            entity.setAnio(daoRequest.getAnio());
            entity.setColor(daoRequest.getColor());
            entity.setObservacion(daoRequest.getObservacion());
            entity.setEstado(daoRequest.getEstado());
            entity.setUbicacionFisica(daoRequest.getUbicacionFisica());
            entity.setSisHabilitado(daoRequest.getSisHabilitado());
            entity.setFechaAdjudicacion(daoRequest.getFechaAdjudicacion());
            return entity;
        });

        setEntityToDaoResponse = (entity -> {
            ArticuloResponseDao dao = new ArticuloResponseDao();
            dao.setId(entity.getId());
            dao.setPlaca(entity.getPlaca());
            dao.setChasis(entity.getChasis());
            dao.setMarca(entity.getMarca());
            dao.setModelo(entity.getModelo());
            dao.setAnio(entity.getAnio());
            dao.setColor(entity.getColor());
            dao.setObservacion(entity.getObservacion());
            dao.setEstado(entity.getEstado());
            dao.setUbicacionFisica(entity.getUbicacionFisica());
            dao.setSisHabilitado(entity.getSisHabilitado());
            dao.setSisActualizado(entity.getSisActualizado());
            dao.setSisCreado(entity.getSisCreado());
            dao.setFechaAdjudicacion(entity.getFechaAdjudicacion());

            return dao;
        });
        setEntityToDaoReference = (entity -> {
            ArticuloResponseDao dao = new ArticuloResponseDao();
            dao.setId(entity.getId());
            dao.setPlaca(entity.getPlaca());
            dao.setChasis(entity.getChasis());
            dao.setMarca(entity.getMarca());
            dao.setModelo(entity.getModelo());
            dao.setAnio(entity.getAnio());
            dao.setColor(entity.getColor());
            dao.setObservacion(entity.getObservacion());
            dao.setEstado(entity.getEstado());
            dao.setUbicacionFisica(entity.getUbicacionFisica());
            dao.setSisHabilitado(entity.getSisHabilitado());
            dao.setSisActualizado(entity.getSisActualizado());
            dao.setSisCreado(entity.getSisCreado());
            dao.setFechaAdjudicacion(entity.getFechaAdjudicacion());
            return dao;
        });

        setEntityListToDaoResponseList = (entityList -> {
            return (List<ArticuloResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(ArticuloMapper.setEntityToDaoResponse).collect(Collectors.toList());
        });
    }
}
