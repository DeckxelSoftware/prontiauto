package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.LibroBibliotecaRequestDao;
import com.ec.prontiauto.dao.LibroBibliotecaResponseDao;
import com.ec.prontiauto.entidad.LibroBiblioteca;

public class LibroBibliotecaMapper {
    public static Function<LibroBibliotecaRequestDao, LibroBiblioteca> setDaoRequestToEntity;
    public static Function<LibroBiblioteca, LibroBibliotecaResponseDao> setEntityToDaoResponse;
    public static Function<LibroBiblioteca, LibroBibliotecaResponseDao> setEntityToDaoReference;
    public static Function<List<LibroBiblioteca>, List<LibroBibliotecaResponseDao>> setEntityListToDaoResponseList;

    static {
        setDaoRequestToEntity = (daoRequest -> {
            LibroBiblioteca entity = new LibroBiblioteca();
            entity.setId(daoRequest.getId());
            entity.setDescripcion(daoRequest.getDescripcion());
            entity.setGeneroLibro(daoRequest.getGeneroLibro());
            entity.setIsbn(daoRequest.getIsbn());
            entity.setNombre(daoRequest.getNombre());
            entity.setSisHabilitado(daoRequest.getSisHabilitado());
            return entity;
        });

        setEntityToDaoResponse = (entity -> {
            LibroBibliotecaResponseDao dao = new LibroBibliotecaResponseDao();
            dao.setId(entity.getId());
            dao.setDescripcion(entity.getDescripcion());
            dao.setGeneroLibro(entity.getGeneroLibro());
            dao.setIsbn(entity.getIsbn());
            dao.setNombre(entity.getNombre());
            dao.setSisHabilitado(entity.getSisHabilitado());
            if (entity.getAutorLibroCollection() != null) {
                dao.setAutorLibroCollection(
                        AutorLibroMapper.setEntityListToDaoResponseList.apply(entity.getAutorLibroCollection()));
            }
            dao.setSisActualizado(entity.getSisActualizado());
            dao.setSisCreado(entity.getSisCreado());
            dao.setSisImagen(entity.getSisImagen());
            return dao;
        });
        setEntityToDaoReference = (entity -> {
            LibroBibliotecaResponseDao dao = new LibroBibliotecaResponseDao();
            dao.setId(entity.getId());
            dao.setDescripcion(entity.getDescripcion());
            dao.setGeneroLibro(entity.getGeneroLibro());
            dao.setIsbn(entity.getIsbn());
            dao.setNombre(entity.getNombre());
            dao.setSisHabilitado(entity.getSisHabilitado());
            dao.setSisActualizado(entity.getSisActualizado());
            dao.setSisCreado(entity.getSisCreado());
            dao.setSisImagen(entity.getSisImagen());
            return dao;
        });

        setEntityListToDaoResponseList = (entityList -> {
            return (List<LibroBibliotecaResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(LibroBibliotecaMapper.setEntityToDaoResponse).collect(Collectors.toList());
        });
    }
}
