package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.RolPermisoRequestDao;
import com.ec.prontiauto.dao.RolPermisoResponseDao;
import com.ec.prontiauto.entidad.Permiso;
import com.ec.prontiauto.entidad.Rol;
import com.ec.prontiauto.entidad.RolPermiso;

public class RolPermisoMapper {
    public static Function<RolPermisoRequestDao, RolPermiso> setDaoRequestToEntity;
    public static Function<RolPermiso, RolPermisoResponseDao> setEntityToDaoResponse;
    public static Function<List<RolPermiso>, List<RolPermisoResponseDao>> setEntityListToDaoResponseList;

    static {
        setDaoRequestToEntity = (daoRequest -> {
            RolPermiso entity = new RolPermiso();
            entity.setId(daoRequest.getId());
            Rol rol = new Rol();
            rol.setId(daoRequest.getIdRol());
            entity.setIdRol(rol);
            Permiso permiso = new Permiso();
            permiso.setId(daoRequest.getIdPermiso());
            entity.setIdPermiso(permiso);
            entity.setSisHabilitado(daoRequest.getSisHabilitado());
            return entity;
        });
        setEntityToDaoResponse = (entity -> {
            RolPermisoResponseDao dao = new RolPermisoResponseDao();
            dao.setId(entity.getId());
            dao.setIdRol(RolMapper.setEntityToDaoReference.apply(entity.getIdRol()));
            dao.setIdPermiso(PermisoMapper.setEntityToDaoReference.apply(entity.getIdPermiso()));
            dao.setSisHabilitado(entity.getSisHabilitado());
            dao.setSisActualizado(entity.getSisActualizado());
            dao.setSisCreado(entity.getSisCreado());
            return dao;
        });
        setEntityListToDaoResponseList = (entityList -> {
            return (List<RolPermisoResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(RolPermisoMapper.setEntityToDaoResponse).collect(Collectors.toList());
        });
    }
}
