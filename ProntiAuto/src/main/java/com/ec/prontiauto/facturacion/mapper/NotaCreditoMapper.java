package com.ec.prontiauto.facturacion.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.facturacion.dao.NotaCreditoRequestDao;
import com.ec.prontiauto.facturacion.dao.NotaCreditoResponseDao;
import com.ec.prontiauto.facturacion.entidad.NotaCredito;

public class NotaCreditoMapper {

    public static Function<NotaCreditoRequestDao, NotaCredito> setDaoRequestToEntity;
    public static Function<NotaCredito, NotaCreditoResponseDao> setEntityToDaoResponse;
    public static Function<List<NotaCredito>, List<NotaCreditoResponseDao>> setEntityListToDaoResponseList;

    static {
        setDaoRequestToEntity = (daoRequest -> {
            NotaCredito entity = new NotaCredito();
            entity.setId(daoRequest.getId());
            entity.setItRuc(daoRequest.getItRuc());
            entity.setItRazonSocial(daoRequest.getItRazonSocial());
            entity.setItNombreComercial(daoRequest.getItNombreComercial());
            entity.setItCodDoc(daoRequest.getItCodDoc());
            entity.setItEstab(daoRequest.getItEstab());
            entity.setItPtoEmision(daoRequest.getItPtoEmision());
            entity.setItNumeroDocumento(daoRequest.getItNumeroDocumento());
            entity.setInIdentificacionComprador(daoRequest.getInIdentificacionComprador());
            entity.setInRazonSocialComprador(daoRequest.getInRazonSocialComprador());
            entity.setInDirEstablecimiento(daoRequest.getInDirEstablecimiento());
            entity.setInFechaEmision(daoRequest.getInFechaEmision());
            entity.setInTotalSinImpuestos(daoRequest.getInTotalSinImpuestos());
            entity.setInValorModificado(daoRequest.getInValorModificado());
            entity.setInMotivo(daoRequest.getInMotivo());
            entity.setJsonFactura(daoRequest.getJsonFactura());
            entity.setSisHabilitado(daoRequest.getSisHabilitado());
            entity.setInCodDocModificado(daoRequest.getInCodDocModificado());
            entity.setInNumDocModificado(daoRequest.getInNumDocModificado());
            return entity;
        });

        setEntityToDaoResponse = (entity -> {
            NotaCreditoResponseDao dao = new NotaCreditoResponseDao();
            dao.setId(entity.getId());
            dao.setItRuc(entity.getItRuc());
            dao.setItRazonSocial(entity.getItRazonSocial());
            dao.setItNombreComercial(entity.getItNombreComercial());
            dao.setItCodDoc(entity.getItCodDoc());
            dao.setItEstab(entity.getItEstab());
            dao.setItPtoEmision(entity.getItPtoEmision());
            dao.setItNumeroDocumento(entity.getItNumeroDocumento());
            dao.setInIdentificacionComprador(entity.getInIdentificacionComprador());
            dao.setInRazonSocialComprador(entity.getInRazonSocialComprador());
            dao.setInDirEstablecimiento(entity.getInDirEstablecimiento());
            dao.setInFechaEmision(entity.getInFechaEmision());
            dao.setInTotalSinImpuestos(entity.getInTotalSinImpuestos());
            dao.setInValorModificado(entity.getInValorModificado());
            dao.setInMotivo(entity.getInMotivo());
            dao.setJsonFactura(entity.getJsonFactura());
            dao.setSisHabilitado(entity.getSisHabilitado());
            dao.setInCodDocModificado(entity.getInCodDocModificado());
            dao.setInNumDocModificado(entity.getInNumDocModificado());
            return dao;
        });

        setEntityListToDaoResponseList = (entityList -> {
            return (List<NotaCreditoResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(NotaCreditoMapper.setEntityToDaoResponse).collect(Collectors.toList());
        });
    }

}
