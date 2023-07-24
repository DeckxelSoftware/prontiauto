package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ec.prontiauto.dao.UsuarioRequestDao;
import com.ec.prontiauto.dao.UsuarioResponseDao;
import com.ec.prontiauto.entidad.Usuario;

public class UsuarioMapper {
    public static Function<UsuarioRequestDao, Usuario> setDaoRequestToEntity;
    public static Function<Usuario, UsuarioResponseDao> setEntityToDaoResponse;
    public static Function<Usuario, UsuarioResponseDao> setEntityToDaoReference;
    public static Function<List<Usuario>, List<UsuarioResponseDao>> setEntityListToDaoResponseList;
    public static Function<List<Usuario>, List<UsuarioResponseDao>> setEntityListToDaoReferenceList;

    static {
        setDaoRequestToEntity = (daoRequest -> {
            Usuario entity = new Usuario();
            entity.setId(daoRequest.getId());
            entity.setUsername(daoRequest.getUsername() != null ? daoRequest.getUsername() : "");
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            entity.setPassword(daoRequest.getPassword() != null ? passwordEncoder.encode(daoRequest.getPassword())
                    : daoRequest.getPassword()!=null?daoRequest.getPassword():"");
            entity.setNombres(daoRequest.getNombres());
            entity.setApellidos(daoRequest.getApellidos());
            entity.setFechaNacimiento(daoRequest.getFechaNacimiento());
            entity.setCorreo(daoRequest.getCorreo() != null ? daoRequest.getCorreo() : "");
            entity.setSisHabilitado(daoRequest.getSisHabilitado());
            entity.setTipoMedioContacto1(daoRequest.getTipoMedioContacto1()!=null?daoRequest.getTipoMedioContacto1():"");
            entity.setMedioContacto1(daoRequest.getMedioContacto1()!=null?daoRequest.getMedioContacto1():"");
            entity.setTipoDocumentoIdentidad(daoRequest.getTipoDocumentoIdentidad());
            entity.setDocumentoIdentidad(daoRequest.getDocumentoIdentidad());
            entity.setPais(daoRequest.getPais());
            entity.setProvincia(daoRequest.getProvincia());
            entity.setCiudad(daoRequest.getCiudad());
            return entity;
        });

        setEntityToDaoResponse = (entity -> {
            UsuarioResponseDao dao = new UsuarioResponseDao();
            dao.setId(entity.getId());
            dao.setUsername(entity.getUsername()!=null?entity.getUsername():"");
            dao.setNombres(entity.getNombres());
            dao.setApellidos(entity.getApellidos());
            dao.setFechaNacimiento(entity.getFechaNacimiento());
            dao.setCorreo(entity.getCorreo()!=null?entity.getCorreo():"");
            dao.setSisHabilitado(entity.getSisHabilitado());
            dao.setSisActualizado(entity.getSisActualizado());
            dao.setSisCreado(entity.getSisCreado());
            dao.setTipoMedioContacto1(entity.getTipoMedioContacto1()!=null?entity.getTipoMedioContacto1():"");
            dao.setMedioContacto1(entity.getMedioContacto1()!=null?entity.getMedioContacto1():"");
            dao.setTipoDocumentoIdentidad(entity.getTipoDocumentoIdentidad());
            dao.setDocumentoIdentidad(entity.getDocumentoIdentidad());
            dao.setPais(entity.getPais());
            dao.setProvincia(entity.getProvincia());
            dao.setCiudad(entity.getCiudad());
            if (entity.getClienteCollection() != null) {
                dao.setClienteCollection(
                        ClienteMapper.setEntityListToDaoResponseList.apply(entity.getClienteCollection()));
            }
            if (entity.getRolUsuarioCollection() != null) {
                dao.setRolUsuarioCollection(
                        RolUsuarioMapper.setEntityListToDaoResponseList.apply(entity.getRolUsuarioCollection()));
            }
            if (entity.getIdTrabajador() != null) {
                dao.setIdTrabajador(TrabajadorMapper.setEntityToDaoResponse.apply(entity.getIdTrabajador()));
            }
            // if (entity.getProveedorCollection() != null) {
            // dao.setProveedorCollection(
            // ProveedorMapper.setEntityListToDaoResponseList.apply(entity.getProveedorCollection()));
            // }
            return dao;
        });

        setEntityToDaoReference = (entity -> {
            UsuarioResponseDao dao = new UsuarioResponseDao();
            dao.setId(entity.getId());
            dao.setUsername(entity.getUsername()!=null?entity.getUsername():"");
            dao.setNombres(entity.getNombres());
            dao.setApellidos(entity.getApellidos());
            dao.setFechaNacimiento(entity.getFechaNacimiento());
            dao.setCorreo(entity.getCorreo()!=null?entity.getCorreo():"");
            dao.setSisHabilitado(entity.getSisHabilitado());
            dao.setSisActualizado(entity.getSisActualizado());
            dao.setSisCreado(entity.getSisCreado());
            dao.setTipoMedioContacto1(entity.getTipoMedioContacto1()!=null?entity.getTipoMedioContacto1():"");
            dao.setMedioContacto1(entity.getMedioContacto1()!=null?entity.getMedioContacto1():"");
            dao.setTipoDocumentoIdentidad(entity.getTipoDocumentoIdentidad());
            dao.setDocumentoIdentidad(entity.getDocumentoIdentidad());
            dao.setPais(entity.getPais());
            dao.setProvincia(entity.getProvincia());
            dao.setCiudad(entity.getCiudad());
            return dao;
        });

        setEntityListToDaoResponseList = (entityList -> {
            return (List<UsuarioResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(UsuarioMapper.setEntityToDaoResponse).collect(Collectors.toList());
        });

        setEntityListToDaoReferenceList = (entityList -> {
            return (List<UsuarioResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(UsuarioMapper.setEntityToDaoReference).collect(Collectors.toList());
        });

    }

}
