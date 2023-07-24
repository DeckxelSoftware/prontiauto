package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.PermisoRequestDao;
import com.ec.prontiauto.dao.PermisoResponseDao;
import com.ec.prontiauto.entidad.Permiso;

public class PermisoMapper {
    public static Function<PermisoRequestDao, Permiso> setDaoRequestToEntity;
    public static Function<Permiso, PermisoResponseDao> setEntityToDaoResponse;
    // Solamente cuando es un PAPA
    // public static Function<Permiso, PermisoResponseDao> setEntityToDaoReference;
    public static Function<Permiso, PermisoResponseDao> setEntityToDaoReference;
    public static Function<List<Permiso>, List<PermisoResponseDao>> setEntityListToDaoResponseList;

    static {
        setDaoRequestToEntity = (daoRequest -> {
            Permiso entity = new Permiso();
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
            PermisoResponseDao dao = new PermisoResponseDao();
            dao.setId(entity.getId());
            dao.setNombre(entity.getNombre());
            // Otras propiedades
            // dao.setNombres(entity.getNombres());
            /*
             * //Si hay colecciones, verificamos si es null y luego apply
             * if (entity.getRelacionHijosCollection() != null) {
             * dao.setRelacionHijosCollection(
             * RelacionHijosMapper.setEntityListToDaoResponseList.apply(entity.
             * getRelacionHijosCollection())
             * );
             * * hijos
             * dao.setIdRelacionPapa(RelacionPapaMapper.setEntityToDaoResponse.apply(entity.
             * getIdRelacionPapa()));
             * }
             */

            if (entity.getRolPermisoCollection() != null) {
                dao.setRolPermisoCollection(
                        RolPermisoMapper.setEntityListToDaoResponseList.apply(entity.getRolPermisoCollection()));
            }

            dao.setSisHabilitado(entity.getSisHabilitado());
            dao.setSisActualizado(entity.getSisActualizado());
            dao.setSisCreado(entity.getSisCreado());
            return dao;
        });
        // Solo cuando es PAPA
        /*
         * setEntityToDaoReference = (entity -> {
         * PermisoResponseDao dao = new PermisoResponseDao();
         * dao.setId(entity.getId());
         * // Otras propiedades
         * //dao.setNombres(entity.getNombres());
         * dao.setSisHabilitado(entity.getSisHabilitado());
         * dao.setSisActualizado(entity.getSisActualizado());
         * dao.setSisCreado(entity.getSisCreado());
         * dao.setSisArchivo(entity.getSisArchivo());
         * dao.setSisImagen(entity.getSisImagen());
         * // Para relaciones asi:
         * //
         * return dao;
         * });
         */

        setEntityToDaoReference = (entity -> {
            PermisoResponseDao dao = new PermisoResponseDao();
            dao.setId(entity.getId());
            dao.setNombre(entity.getNombre());
            dao.setSisHabilitado(entity.getSisHabilitado());
            dao.setSisActualizado(entity.getSisActualizado());
            dao.setSisArchivo(entity.getSisArchivo());
            dao.setSisImagen(entity.getSisImagen());
            return dao;
        });

        setEntityListToDaoResponseList = (entityList -> {
            return (List<PermisoResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(PermisoMapper.setEntityToDaoResponse).collect(Collectors.toList());
        });
    }
}
