package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.CuotaRequestDao;
import com.ec.prontiauto.dao.CuotaResponseDao;
import com.ec.prontiauto.entidad.Cuota;
import com.ec.prontiauto.entidad.HistoricoPlanContrato;

public class CuotaMapper {
    public static Function<CuotaRequestDao, Cuota> setDaoRequestToEntity;
    public static Function<Cuota, CuotaResponseDao> setEntityToDaoResponse;
    public static Function<List<Cuota>, List<CuotaResponseDao>> setEntityListToDaoResponseList;

    static {
        setDaoRequestToEntity = (daoRequest -> {
            Cuota entity = new Cuota();
            entity.setId(daoRequest.getId());
            entity.setFechaCobro(daoRequest.getFechaCobro());
            entity.setFechaMora(daoRequest.getFechaMora());
            entity.setNumeroCuota(daoRequest.getNumeroCuota());
            entity.setValorCuota(daoRequest.getValorCuota());
            entity.setValorPagadoCuota(daoRequest.getValorPagadoCuota());
            entity.setValorTasaAdministrativa(daoRequest.getValorTasaAdministrativa());
            entity.setValorImpuesto(daoRequest.getValorImpuesto());
            entity.setAbonoCapital(daoRequest.getAbonoCapital());
            entity.setEstaPagado(daoRequest.getEstaPagado());
            entity.setEstaMora(daoRequest.getEstaMora());
            entity.setSisHabilitado(daoRequest.getSisHabilitado());
            HistoricoPlanContrato historicoPlanContrato = new HistoricoPlanContrato();
            historicoPlanContrato.setId(daoRequest.getIdHistoricoPlanContrato());
            entity.setIdHistoricoPlanContrato(historicoPlanContrato);
            return entity;
        });
        setEntityToDaoResponse = (entity -> {
            CuotaResponseDao dao = new CuotaResponseDao();
            dao.setId(entity.getId());
            dao.setFechaCobro(entity.getFechaCobro());
            dao.setFechaMora(entity.getFechaMora());
            dao.setNumeroCuota(entity.getNumeroCuota());
            dao.setValorCuota(entity.getValorCuota());
            dao.setValorPagadoCuota(entity.getValorPagadoCuota());
            dao.setValorTasaAdministrativa(entity.getValorTasaAdministrativa());
            dao.setValorImpuesto(entity.getValorImpuesto());
            dao.setAbonoCapital(entity.getAbonoCapital());
            dao.setEstaPagado(entity.getEstaPagado());
            dao.setEstaMora(entity.getEstaMora());
            dao.setSisHabilitado(entity.getSisHabilitado());
            dao.setSisActualizado(entity.getSisActualizado());
            dao.setSisCreado(entity.getSisCreado());
            if (entity.getIdHistoricoPlanContrato() != null) {
                dao.setIdHistoricoPlanContrato(
                        HistoricoPlanContratoMapper.setEntityToDaoReference.apply(entity.getIdHistoricoPlanContrato()));
            }
            return dao;
        });

        setEntityListToDaoResponseList = (entityList -> {
            return (List<CuotaResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(CuotaMapper.setEntityToDaoResponse).collect(Collectors.toList());
        });
    }
}
