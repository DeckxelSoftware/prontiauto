package com.ec.prontiauto.facturacion.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.facturacion.dao.FacturaRequestDao;
import com.ec.prontiauto.facturacion.dao.FacturaResponseDao;
import com.ec.prontiauto.facturacion.entidad.Factura;

public class FacturaMapper {
    public static Function<FacturaRequestDao, Factura> setDaoRequestToEntity;
    public static Function<Factura, FacturaResponseDao> setEntityToDaoResponse;
    public static Function<List<Factura>, List<FacturaResponseDao>> setEntityListToDaoResponseList;

    static {
        setDaoRequestToEntity = (daoRequest -> {
            Factura entity = new Factura();
            entity.setId(daoRequest.getId());
            entity.setItRuc(daoRequest.getItRuc());
            entity.setItRazonSocial(daoRequest.getItRazonSocial());
            entity.setItNombreComercial(daoRequest.getItNombreComercial());
            entity.setItCodDoc(daoRequest.getItCodDoc());
            entity.setItEstab(daoRequest.getItEstab());
            entity.setItPtoEmision(daoRequest.getItPtoEmision());
            entity.setItNumeroDocumento(daoRequest.getItNumeroDocumento());
            entity.setIfIdentificacionComprador(daoRequest.getIfIdentificacionComprador());
            entity.setIfRazonSocialComprador(daoRequest.getIfRazonSocialComprador());
            entity.setIfDirEstablecimiento(daoRequest.getIfDirEstablecimiento());
            entity.setIfFechaEmision(daoRequest.getIfFechaEmision());
            entity.setIfImporteTotal(daoRequest.getIfImporteTotal());
            entity.setJsonFactura(daoRequest.getJsonFactura());
            entity.setSisHabilitado(daoRequest.getSisHabilitado());
            return entity;
        });

        setEntityToDaoResponse = (entity -> {
            FacturaResponseDao dao = new FacturaResponseDao();
            dao.setId(entity.getId());
            dao.setItRuc(entity.getItRuc());
            dao.setItRazonSocial(entity.getItRazonSocial());
            dao.setItNombreComercial(entity.getItNombreComercial());
            dao.setItCodDoc(entity.getItCodDoc());
            dao.setItEstab(entity.getItEstab());
            dao.setItPtoEmision(entity.getItPtoEmision());
            dao.setItNumeroDocumento(entity.getItNumeroDocumento());
            dao.setIfIdentificacionComprador(entity.getIfIdentificacionComprador());
            dao.setIfRazonSocialComprador(entity.getIfRazonSocialComprador());
            dao.setIfDirEstablecimiento(entity.getIfDirEstablecimiento());
            dao.setIfFechaEmision(entity.getIfFechaEmision());
            dao.setIfImporteTotal(entity.getIfImporteTotal());
            dao.setJsonFactura(entity.getJsonFactura());
            dao.setSisHabilitado(entity.getSisHabilitado());
            return dao;
        });

        setEntityListToDaoResponseList = (entityList -> {
            return (List<FacturaResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(FacturaMapper.setEntityToDaoResponse).collect(Collectors.toList());
        });
    }
}
