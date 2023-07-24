package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.BancoRequestDao;
import com.ec.prontiauto.dao.BancoResponseDao;
import com.ec.prontiauto.entidad.Banco;

public class BancoMapper {
    public static Function<BancoRequestDao, Banco> setDaoRequestToEntity;
    public static Function<Banco, BancoResponseDao> setEntityToDaoResponse;
    public static Function<Banco, BancoResponseDao> setEntityToDaoReference;
    public static Function<List<Banco>, List<BancoResponseDao>> setEntityListToDaoResponseList;
    public static Function<List<Banco>, List<BancoResponseDao>> setEntityListToDaoReferenceList;

    static {
        setDaoRequestToEntity = (daoRequest -> {
            Banco entity = new Banco();
            entity.setId(daoRequest.getId());
            entity.setNombre(daoRequest.getNombre());
            entity.setSisHabilitado(daoRequest.getSisHabilitado());
            return entity;
        });
        setEntityToDaoResponse = (entity -> {
            BancoResponseDao dao = new BancoResponseDao();
            dao.setId(entity.getId());
            dao.setNombre(entity.getNombre());
            dao.setSisHabilitado(entity.getSisHabilitado());
            dao.setSisActualizado(entity.getSisActualizado());
            dao.setSisCreado(entity.getSisCreado());
            dao.setSisArchivo(entity.getSisArchivo());
            dao.setSisImagen(entity.getSisImagen());
            if (entity.getCuentaBancariaEmpresaCollection() != null) {
                dao.setCuentaBancariaEmpresaCollection(CuentaBancariaEmpresaMapper.setEntityListToDaoResponseList
                        .apply(entity.getCuentaBancariaEmpresaCollection()));
            }
            return dao;
        });

        setEntityToDaoReference = (entity -> {
            BancoResponseDao dao = new BancoResponseDao();
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
            return (List<BancoResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(BancoMapper.setEntityToDaoResponse).collect(Collectors.toList());
        });

        setEntityListToDaoReferenceList = (entityList -> {
            return (List<BancoResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(BancoMapper.setEntityToDaoReference).collect(Collectors.toList());
        });
    }
}
