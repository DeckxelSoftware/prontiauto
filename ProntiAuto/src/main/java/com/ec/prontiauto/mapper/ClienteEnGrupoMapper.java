package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.ClienteEnGrupoRequestDao;
import com.ec.prontiauto.dao.ClienteEnGrupoResponseDao;
import com.ec.prontiauto.entidad.Cliente;
import com.ec.prontiauto.entidad.ClienteEnGrupo;
import com.ec.prontiauto.entidad.Grupo;

public class ClienteEnGrupoMapper {
    public static Function<ClienteEnGrupoRequestDao, ClienteEnGrupo> setDaoRequestToEntity;
    public static Function<ClienteEnGrupo, ClienteEnGrupoResponseDao> setEntityToDaoResponse;
    public static Function<ClienteEnGrupo, ClienteEnGrupoResponseDao> setEntityToDaoReference;
    public static Function<List<ClienteEnGrupo>, List<ClienteEnGrupoResponseDao>> setEntityListToDaoResponseList;
    public static Function<List<ClienteEnGrupo>, List<ClienteEnGrupoResponseDao>> setEntityListToDaoReferenceList;

    static {
        setDaoRequestToEntity = (daoRequest -> {
            ClienteEnGrupo entity = new ClienteEnGrupo();
            entity.setId(daoRequest.getId());
            entity.setSisHabilitado(daoRequest.getSisHabilitado());
            Cliente cliente = new Cliente();
            cliente.setId(daoRequest.getIdCliente());
            entity.setIdCliente(cliente);
            Grupo grupo = new Grupo();
            grupo.setId(daoRequest.getIdGrupo());
            entity.setIdGrupo(grupo);
            return entity;
        });
        setEntityToDaoResponse = (entity -> {
            ClienteEnGrupoResponseDao dao = new ClienteEnGrupoResponseDao();
            dao.setId(entity.getId());
            dao.setSisHabilitado(entity.getSisHabilitado());
            dao.setSisActualizado(entity.getSisActualizado());
            dao.setSisCreado(entity.getSisCreado());
            dao.setSisArchivo(entity.getSisArchivo());
            dao.setSisImagen(entity.getSisImagen());
            if (entity.getIdCliente().getId() != null) {
                dao.setIdCliente(ClienteMapper.setEntityToDaoReference.apply(entity.getIdCliente()));
            }
            if (entity.getIdGrupo().getId() != null) {
                dao.setIdGrupo(GrupoMapper.setEntityToDaoReference.apply(entity.getIdGrupo()));
            }
            if (entity.getContratoCollection() != null) {
                dao.setContratoCollection(
                        ContratoMapper.setEntityListToDaoResponseList.apply(entity.getContratoCollection()));
            }
            return dao;
        });

        setEntityToDaoReference = (entity -> {
            ClienteEnGrupoResponseDao dao = new ClienteEnGrupoResponseDao();
            dao.setId(entity.getId());
            dao.setSisHabilitado(entity.getSisHabilitado());
            dao.setSisActualizado(entity.getSisActualizado());
            dao.setSisCreado(entity.getSisCreado());
            dao.setSisArchivo(entity.getSisArchivo());
            dao.setSisImagen(entity.getSisImagen());
            if (entity.getIdCliente() != null) {
                dao.setIdCliente(ClienteMapper.setEntityToDaoReference.apply(entity.getIdCliente()));
            }
            if (entity.getIdGrupo() != null) {
                dao.setIdGrupo(GrupoMapper.setEntityToDaoReference.apply(entity.getIdGrupo()));
            }
            return dao;
        });

        setEntityListToDaoResponseList = (entityList -> {
            return (List<ClienteEnGrupoResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(ClienteEnGrupoMapper.setEntityToDaoResponse).collect(Collectors.toList());
        });

        setEntityListToDaoReferenceList = (entityList -> {
            return (List<ClienteEnGrupoResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(ClienteEnGrupoMapper.setEntityToDaoReference).collect(Collectors.toList());
        });
    }
}
