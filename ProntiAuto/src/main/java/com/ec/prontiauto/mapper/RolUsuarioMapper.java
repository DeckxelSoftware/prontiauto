package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.RolUsuarioRequestDao;
import com.ec.prontiauto.dao.RolUsuarioResponseDao;
import com.ec.prontiauto.entidad.Rol;
import com.ec.prontiauto.entidad.RolUsuario;
import com.ec.prontiauto.entidad.Usuario;

public class RolUsuarioMapper {
    public static Function<RolUsuarioRequestDao, RolUsuario> setDaoRequestToEntity;
    public static Function<RolUsuario, RolUsuarioResponseDao> setEntityToDaoResponse;
    public static Function<List<RolUsuario>, List<RolUsuarioResponseDao>> setEntityListToDaoResponseList;

    static {
        setDaoRequestToEntity = (daoRequest -> {
            RolUsuario entity = new RolUsuario();
            entity.setId(daoRequest.getId());
            Rol rol = new Rol();
            rol.setId(daoRequest.getIdRol());
            entity.setIdRol(rol);
            Usuario usuario = new Usuario();
            usuario.setId(daoRequest.getIdUsuario());
            entity.setIdUsuario(usuario);
            entity.setSisHabilitado(daoRequest.getSisHabilitado());
            return entity;
        });
        setEntityToDaoResponse = (entity -> {
            RolUsuarioResponseDao dao = new RolUsuarioResponseDao();
            dao.setId(entity.getId());
            dao.setIdRol(RolMapper.setEntityToDaoReference.apply(entity.getIdRol()));
            dao.setIdUsuario(UsuarioMapper.setEntityToDaoReference.apply(entity.getIdUsuario()));
            dao.setSisHabilitado(entity.getSisHabilitado());
            dao.setSisActualizado(entity.getSisActualizado());
            dao.setSisCreado(entity.getSisCreado());
            return dao;
        });
        setEntityListToDaoResponseList = (entityList -> {
            return (List<RolUsuarioResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(RolUsuarioMapper.setEntityToDaoResponse).collect(Collectors.toList());
        });
    }
}
