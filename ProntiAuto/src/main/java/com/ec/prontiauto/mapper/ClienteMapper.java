package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.ClienteRequestDao;
import com.ec.prontiauto.dao.ClienteResponseDao;
import com.ec.prontiauto.entidad.Cliente;
import com.ec.prontiauto.entidad.Empresa;
import com.ec.prontiauto.entidad.Usuario;

public class ClienteMapper {
    public static Function<ClienteRequestDao, Cliente> setDaoRequestToEntity;
    public static Function<Cliente, ClienteResponseDao> setEntityToDaoResponse;
    public static Function<Cliente, ClienteResponseDao> setEntityToDaoReference;
    public static Function<List<Cliente>, List<ClienteResponseDao>> setEntityListToDaoResponseList;
    public static Function<List<Cliente>, List<ClienteResponseDao>> setEntityListToDaoReferenceList;

    static {
        setDaoRequestToEntity = (daoRequest -> {
            Cliente entity = new Cliente();
            entity.setId(daoRequest.getId());
            entity.setTipoCliente(daoRequest.getTipoCliente());
            entity.setSisHabilitado(daoRequest.getSisHabilitado());
            if (daoRequest.getIdUsuario() != null) {
                Usuario usuario = new Usuario();
                usuario.setId((int) daoRequest.getIdUsuario());
                entity.setIdUsuario(usuario);
            }
            if (daoRequest.getIdEmpresa() != null) {
                Empresa empresa = new Empresa();
                empresa.setId((int) daoRequest.getIdEmpresa());
                entity.setIdEmpresa(empresa);
            }
            return entity;
        });

        setEntityToDaoResponse = (entity -> {
            ClienteResponseDao dao = new ClienteResponseDao();
            dao.setId(entity.getId());
            dao.setTipoCliente(entity.getTipoCliente());
            dao.setSisHabilitado(entity.getSisHabilitado());
            dao.setSisActualizado(entity.getSisActualizado());
            dao.setSisCreado(entity.getSisCreado());
            dao.setSisArchivo(entity.getSisArchivo());
            dao.setSisImagen(entity.getSisImagen());
            if (entity.getIdEmpresa() != null) {
                dao.setIdEmpresa(EmpresaMapper.setEntityToDaoReference.apply(entity.getIdEmpresa()));
            }
            dao.setIdUsuario(
                    UsuarioMapper.setEntityToDaoReference.apply(entity.getIdUsuario()));
            if (entity.getCollectionClienteEnGrupo() != null) {
                dao.setCollectionClienteEnGrupo(
                        ClienteEnGrupoMapper.setEntityListToDaoResponseList.apply(
                                entity.getCollectionClienteEnGrupo()));
            }
            return dao;
        });

        setEntityToDaoReference = (entity -> {
            ClienteResponseDao dao = new ClienteResponseDao();
            dao.setId(entity.getId());
            dao.setTipoCliente(entity.getTipoCliente());
            dao.setSisHabilitado(entity.getSisHabilitado());
            dao.setSisActualizado(entity.getSisActualizado());
            dao.setSisCreado(entity.getSisCreado());
            dao.setSisArchivo(entity.getSisArchivo());
            dao.setSisImagen(entity.getSisImagen());
            if (entity.getIdUsuario() != null) {
                dao.setIdUsuario(UsuarioMapper.setEntityToDaoReference.apply(entity.getIdUsuario()));
            }
            if (entity.getIdEmpresa() != null) {
                dao.setIdEmpresa(EmpresaMapper.setEntityToDaoReference.apply(entity.getIdEmpresa()));
            }
            return dao;
        });

        setEntityListToDaoResponseList = (entityList -> {
            return (List<ClienteResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(ClienteMapper.setEntityToDaoResponse).collect(Collectors.toList());
        });

        setEntityListToDaoReferenceList = (entityList -> {
            return (List<ClienteResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(ClienteMapper.setEntityToDaoReference).collect(Collectors.toList());
        });
    }
}
