package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.ConfiguracionGeneralRequestDao;
import com.ec.prontiauto.dao.ConfiguracionGeneralResponseDao;
import com.ec.prontiauto.entidad.ConfiguracionGeneral;

public class ConfiguracionGeneralMapper {
    public static Function<ConfiguracionGeneralRequestDao, ConfiguracionGeneral> setDaoRequestToEntity;
    public static Function<ConfiguracionGeneral, ConfiguracionGeneralResponseDao> setEntityToDaoResponse;
    public static Function<List<ConfiguracionGeneral>, List<ConfiguracionGeneralResponseDao>> setEntityListToDaoResponseList;

    static {
        setDaoRequestToEntity = (daoRequest -> {
            ConfiguracionGeneral entity = new ConfiguracionGeneral();
            entity.setId(daoRequest.getId());
            entity.setIvaPorcentaje(daoRequest.getIvaPorcentaje());
            entity.setTasaCambioContrato(daoRequest.getTasaCambioContrato());
            entity.setMinCuotaMoraRefinanciamiento(daoRequest.getMinCuotaMoraRefinanciamiento());
            entity.setMaxContratosEnGrupo(daoRequest.getMaxContratosEnGrupo());
            entity.setCuotaAdministrativa(daoRequest.getCuotaAdministrativa());
            entity.setNumDiasVacacionesAlAnio(daoRequest.getNumDiasVacacionesAlAnio());
            entity.setTasaCargoAdjudicacion(daoRequest.getTasaCargoAdjudicacion());
            entity.setAportePatronalIess(daoRequest.getAportePatronalIess());
            entity.setAportePersonalIess(daoRequest.getAportePersonalIess());
            entity.setSueldoBasico(daoRequest.getSueldoBasico());
            entity.setSisHabilitado(daoRequest.getSisHabilitado());
            entity.setRastreo(daoRequest.getRastreo());
            entity.setDispositivo(daoRequest.getDispositivo());
            return entity;
        });

        setEntityToDaoResponse = (entity -> {
            ConfiguracionGeneralResponseDao dao = new ConfiguracionGeneralResponseDao();
            dao.setId(entity.getId());
            dao.setIvaPorcentaje(entity.getIvaPorcentaje());
            dao.setTasaCambioContrato(entity.getTasaCambioContrato());
            dao.setMinCuotaMoraRefinanciamiento(entity.getMinCuotaMoraRefinanciamiento());
            dao.setMaxContratosEnGrupo(entity.getMaxContratosEnGrupo());
            dao.setCuotaAdministrativa(entity.getCuotaAdministrativa());
            dao.setNumDiasVacacionesAlAnio(entity.getNumDiasVacacionesAlAnio());
            dao.setTasaCargoAdjudicacion(entity.getTasaCargoAdjudicacion());
            dao.setAportePatronalIess(entity.getAportePatronalIess());
            dao.setAportePersonalIess(entity.getAportePersonalIess());
            dao.setSueldoBasico(entity.getSueldoBasico());
            dao.setRastreo(entity.getRastreo());
            dao.setDispositivo(entity.getDispositivo());
            dao.setSisHabilitado(entity.getSisHabilitado());
            dao.setSisActualizado(entity.getSisActualizado());
            dao.setSisCreado(entity.getSisCreado());
            dao.setSisArchivo(entity.getSisArchivo());
            dao.setSisImagen(entity.getSisImagen());
            return dao;
        });

        setEntityListToDaoResponseList = (entityList -> {
            return (List<ConfiguracionGeneralResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(ConfiguracionGeneralMapper.setEntityToDaoResponse).collect(Collectors.toList());
        });
    }
}
