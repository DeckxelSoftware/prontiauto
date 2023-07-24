package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.DivisionAdministrativaRequestDao;
import com.ec.prontiauto.dao.DivisionAdministrativaResponseDao;
import com.ec.prontiauto.entidad.DivisionAdministrativa;

public class DivisionAdministrativaMapper {
    public static Function<DivisionAdministrativaRequestDao, DivisionAdministrativa> setDaoRequestToEntity;
    public static Function<DivisionAdministrativa, DivisionAdministrativaResponseDao> setEntityToDaoResponse;
    public static Function<DivisionAdministrativa, DivisionAdministrativaResponseDao> setEntityToDaoReference;
    public static Function<List<DivisionAdministrativa>, List<DivisionAdministrativaResponseDao>> setEntityListToDaoResponseList;
    public static Function<List<DivisionAdministrativa>, List<DivisionAdministrativaResponseDao>> setEntityListToDaoReferenceList;

    static {
        setDaoRequestToEntity = (daoRequest -> {
        	DivisionAdministrativa entity = new DivisionAdministrativa();
            entity.setId(daoRequest.getId());
            entity.setNombreDivision(daoRequest.getNombreDivision());
            entity.setSisHabilitado(daoRequest.getSisHabilitado());
            return entity;
        });

        setEntityToDaoResponse = (entity -> {
        	DivisionAdministrativaResponseDao dao = new DivisionAdministrativaResponseDao();
            dao.setId(entity.getId());
            dao.setNombreDivision(entity.getNombreDivision());
            dao.setSisHabilitado(entity.getSisHabilitado());
            if (entity.getHistorialLaboralCollection() != null) {
                dao.setHistorialLaboralCollection(
                        HistorialLaboralMapper.setEntityListToDaoResponseList.apply(entity.getHistorialLaboralCollection()));
            }
            dao.setSisActualizado(entity.getSisActualizado());
            dao.setSisCreado(entity.getSisCreado());
            return dao;
        });
        setEntityToDaoReference = (entity -> {
        	DivisionAdministrativaResponseDao dao = new DivisionAdministrativaResponseDao();
            dao.setId(entity.getId());
            dao.setNombreDivision(entity.getNombreDivision());
            dao.setSisHabilitado(entity.getSisHabilitado());
            dao.setSisActualizado(entity.getSisActualizado());
            dao.setSisCreado(entity.getSisCreado());
            if (entity.getHistorialLaboralCollection() != null) {
                dao.setHistorialLaboralCollection(
                        HistorialLaboralMapper.setEntityListToDaoReferenceList.apply(entity.getHistorialLaboralCollection()));
            }
            return dao;
        });

        setEntityListToDaoResponseList = (entityList -> {
            return (List<DivisionAdministrativaResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(DivisionAdministrativaMapper.setEntityToDaoResponse).collect(Collectors.toList());
        });
        setEntityListToDaoReferenceList = (entityList -> {
            return (List<DivisionAdministrativaResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(DivisionAdministrativaMapper.setEntityToDaoReference).collect(Collectors.toList());
        });
    }
}
