package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.AutorLibroRequestDao;
import com.ec.prontiauto.dao.AutorLibroResponseDao;
import com.ec.prontiauto.entidad.AutorLibro;
import com.ec.prontiauto.entidad.LibroBiblioteca;

public class AutorLibroMapper {
    public static Function<AutorLibroRequestDao, AutorLibro> setDaoRequestToEntity;
    public static Function<AutorLibro, AutorLibroResponseDao> setEntityToDaoResponse;
    public static Function<List<AutorLibro>, List<AutorLibroResponseDao>> setEntityListToDaoResponseList;

    static {
        setDaoRequestToEntity = (daoRequest -> {
            AutorLibro entity = new AutorLibro();
            entity.setId(daoRequest.getId());
            entity.setApellidos(daoRequest.getApellidos());
            entity.setBiografia(daoRequest.getBiografia());
            entity.setNombres(daoRequest.getNombres());
            entity.setSisHabilitado(daoRequest.getSisHabilitado());
            LibroBiblioteca libroBiblioteca = new LibroBiblioteca();
            libroBiblioteca.setId(daoRequest.getIdLibroBiblioteca());
            entity.setIdLibroBiblioteca(libroBiblioteca);
            return entity;
        });
        setEntityToDaoResponse = (entity -> {
            AutorLibroResponseDao dao = new AutorLibroResponseDao();
            dao.setId(entity.getId());
            dao.setApellidos(entity.getApellidos());
            dao.setBiografia(entity.getBiografia());
            dao.setNombres(entity.getNombres());
            dao.setSisHabilitado(entity.getSisHabilitado());
            dao.setSisActualizado(entity.getSisActualizado());
            dao.setSisCreado(entity.getSisCreado());
            dao.setSisArchivo(entity.getSisArchivo());
            dao.setIdLibroBiblioteca(
                    LibroBibliotecaMapper.setEntityToDaoReference.apply(entity.getIdLibroBiblioteca()));
            return dao;
        });
        setEntityListToDaoResponseList = (entityList -> {
            return (List<AutorLibroResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(AutorLibroMapper.setEntityToDaoResponse).collect(Collectors.toList());
        });
    }
}
