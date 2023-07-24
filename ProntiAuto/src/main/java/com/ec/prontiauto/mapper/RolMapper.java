package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.RolRequestDao;
import com.ec.prontiauto.dao.RolResponseDao;
import com.ec.prontiauto.entidad.Rol;

public class RolMapper {
    public static Function<RolRequestDao, Rol> setDaoRequestToEntity;
    public static Function<Rol, RolResponseDao> setEntityToDaoResponse;
    // Solamente cuando es un PAPA
    public static Function<Rol, RolResponseDao> setEntityToDaoReference;
    public static Function<List<Rol>, List<RolResponseDao>> setEntityListToDaoResponseList;

    static {
        setDaoRequestToEntity = (daoRequest -> {
            Rol entity = new Rol();
            entity.setId(daoRequest.getId());
            // otras propiedades
            // entity.setNombres(daoRequest.getNombres());
            entity.setNombre(daoRequest.getNombre());
            entity.setSisHabilitado(daoRequest.getSisHabilitado());
            // Relaciones hacia el padre
            // RelacionPapa relacionPapa = new RelacionPapa();
            // RelacionPapa.setId(daoRequest.getIdRelacionPapa());
            // entity.setIdRelacionPapa(relacionPaparelacionPapa);
            return entity;
        });
        setEntityToDaoResponse = (entity -> {
            RolResponseDao dao = new RolResponseDao();
            dao.setId(entity.getId());
            // Otras propiedades
            // dao.setNombres(entity.getNombres());
            // Si soy PAPA, si hay colecciones, verificamos si es null y luego apply
            // if (entity.getRelacionHijosCollection() != null) {
            // dao.setRelacionHijosCollection(
            // RelacionHijosMapper.setEntityListToDaoResponseList.apply(entity.getRelacionHijosCollection())
            // );
            // }

            if (entity.getRolPermisoCollection() != null) {
                dao.setRolPermisoCollection(
                        RolPermisoMapper.setEntityListToDaoResponseList.apply(entity.getRolPermisoCollection()));
            }
            if (entity.getRolUsuarioCollection() != null) {
                dao.setRolUsuarioCollection(
                        RolUsuarioMapper.setEntityListToDaoResponseList.apply(entity.getRolUsuarioCollection()));
            }
            dao.setNombre(entity.getNombre());
            dao.setSisHabilitado(entity.getSisHabilitado());
            dao.setSisActualizado(entity.getSisActualizado());
            dao.setSisCreado(entity.getSisCreado());
            dao.setSisArchivo(entity.getSisArchivo());
            dao.setSisImagen(entity.getSisImagen());
            return dao;
        });
        // Solo cuando es HIJO

        // setEntityToDaoReference = (entity -> {
        // RolResponseDao dao = new RolResponseDao();
        // dao.setId(entity.getId());
        // // Otras propiedades
        // //dao.setNombres(entity.getNombres());
        // dao.setSisHabilitado(entity.getSisHabilitado());
        // dao.setSisActualizado(entity.getSisActualizado());
        // dao.setSisCreado(entity.getSisCreado());
        // dao.setSisArchivo(entity.getSisArchivo());
        // dao.setSisImagen(entity.getSisImagen());
        // return dao;
        // });
        setEntityToDaoReference = (entity -> {
            RolResponseDao dao = new RolResponseDao();
            dao.setId(entity.getId());
            dao.setNombre(entity.getNombre());
            dao.setSisHabilitado(entity.getSisHabilitado());
            dao.setSisActualizado(entity.getSisActualizado());
            dao.setSisCreado(entity.getSisCreado());
            dao.setSisArchivo(entity.getSisArchivo());
            dao.setSisImagen(entity.getSisImagen());
            return dao;
        });

        setEntityListToDaoResponseList = (entityList -> {
            return (List<RolResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(RolMapper.setEntityToDaoResponse).collect(Collectors.toList());
        });
    }
}
