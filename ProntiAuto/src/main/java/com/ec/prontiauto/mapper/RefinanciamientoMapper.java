package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.RefinanciamientoRequestDao;
import com.ec.prontiauto.dao.RefinanciamientoResponseDao;
import com.ec.prontiauto.entidad.HistoricoPlanContrato;
import com.ec.prontiauto.entidad.Refinanciamiento;

public class RefinanciamientoMapper {
    public static Function<RefinanciamientoRequestDao, Refinanciamiento> setDaoRequestToEntity;
    public static Function<Refinanciamiento, RefinanciamientoResponseDao> setEntityToDaoResponse;
    public static Function<List<Refinanciamiento>, List<RefinanciamientoResponseDao>> setEntityListToDaoResponseList;

    static {
        setDaoRequestToEntity = (daoRequest -> {
            Refinanciamiento entity = new Refinanciamiento();
            entity.setId(daoRequest.getId());
            entity.setTotalCuotas(daoRequest.getTotalCuotas());
            entity.setTotalCuotasPagadas(daoRequest.getTotalCuotasPagadas());
            entity.setTotalCuotasMora(daoRequest.getTotalCuotasMora());
            entity.setTotalCuotasPagadasRefinanciamiento(daoRequest.getTotalCuotasPagadasRefinanciamiento());
            entity.setTotalCuotasFaltantesRefinanciamiento(daoRequest.getTotalCuotasFaltantesRefinanciamiento());
            entity.setCuotasRestantesSinMora(daoRequest.getCuotasRestantesSinMora());
            entity.setValorCuota(daoRequest.getValorCuota());
            entity.setValorPendientePago(daoRequest.getValorPendientePago());
            entity.setValorAgregarseCuota(daoRequest.getValorAgregarseCuota());
            entity.setFechaRefinanciamiento(daoRequest.getFechaRefinanciamiento());
            entity.setSisHabilitado(daoRequest.getSisHabilitado());
            HistoricoPlanContrato historicoplancontrato = new HistoricoPlanContrato();
            historicoplancontrato.setId(daoRequest.getIdHistoricoPlanContrato());
            entity.setIdHistoricoPlanContrato(historicoplancontrato);
            return entity;
        });
        setEntityToDaoResponse = (entity -> {
            RefinanciamientoResponseDao dao = new RefinanciamientoResponseDao();
            dao.setId(entity.getId());
            dao.setTotalCuotas(entity.getTotalCuotas());
            dao.setTotalCuotasPagadas(entity.getTotalCuotasPagadas());
            dao.setTotalCuotasMora(entity.getTotalCuotasMora());
            dao.setTotalCuotasPagadasRefinanciamiento(entity.getTotalCuotasPagadasRefinanciamiento());
            dao.setTotalCuotasFaltantesRefinanciamiento(entity.getTotalCuotasFaltantesRefinanciamiento());
            dao.setCuotasRestantesSinMora(entity.getCuotasRestantesSinMora());
            dao.setValorCuota(entity.getValorCuota());
            dao.setValorPendientePago(entity.getValorPendientePago());
            dao.setValorAgregarseCuota(entity.getValorAgregarseCuota());
            dao.setFechaRefinanciamiento(entity.getFechaRefinanciamiento());
            dao.setSisHabilitado(entity.getSisHabilitado());
            if (entity.getIdHistoricoPlanContrato() != null) {
                dao.setIdHistoricoPlanContrato(
                        HistoricoPlanContratoMapper.setEntityToDaoReference.apply(entity.getIdHistoricoPlanContrato()));
            }
            return dao;
        });
        setEntityListToDaoResponseList = (entityList -> {
            return (List<RefinanciamientoResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(RefinanciamientoMapper.setEntityToDaoResponse).collect(Collectors.toList());
        });
    }
}
