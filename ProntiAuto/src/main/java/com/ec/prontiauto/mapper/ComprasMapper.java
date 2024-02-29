package com.ec.prontiauto.mapper;

import com.ec.prontiauto.dao.CabeceraCompraRequestDao;
import com.ec.prontiauto.entidad.Agencia;
import com.ec.prontiauto.entidad.CabeceraCompra;
import com.ec.prontiauto.entidad.Recurso;

import java.util.function.Function;

public class ComprasMapper {
    public static Function<CabeceraCompraRequestDao, CabeceraCompra> setDaoRequestToCabeceraCompraEntity;
    public static Function<CabeceraCompra, CabeceraCompraResponseDao> setEntityToDaoResponse;

    static {
        setDaoRequestToCabeceraCompraEntity = (requestDao -> {
            CabeceraCompra cabeceraCompra = new CabeceraCompra();
            cabeceraCompra.setNombreProveedor(requestDao.getNombreProveedor());
            cabeceraCompra.setRucProveedor(requestDao.getRucProveedor());
            cabeceraCompra.setAutorizacion(requestDao.getAutorizacion());
            cabeceraCompra.setNumeroDocumento(requestDao.getNumeroDocumento());
            cabeceraCompra.setSerie(requestDao.getSerie());
            cabeceraCompra.setFechaRecepcion(requestDao.getFechaRecepcion());
            cabeceraCompra.setFechaEmision(requestDao.getFechaEmision());
            cabeceraCompra.setFechaVencimiento(requestDao.getFechaVencimiento());
            cabeceraCompra.setVersion(requestDao.getVersion());
            cabeceraCompra.setTerminosPago(requestDao.getTerminosPago());
            cabeceraCompra.setTipoDocumento(requestDao.getTipoDocumento());
            cabeceraCompra.setSubtotal(requestDao.getSubtotal());
            cabeceraCompra.setImpuesto(requestDao.getImpuesto());
            cabeceraCompra.setTotalFactura(requestDao.getTotalFactura());
            cabeceraCompra.setRetenciones(requestDao.getRetenciones());
            cabeceraCompra.setValorAPagar(requestDao.getValorAPagar());
            cabeceraCompra.setObservaciones(requestDao.getObservaciones());
            cabeceraCompra.setSisHabilitado("A");

            Agencia agencia = new Agencia();
            agencia.setId(requestDao.getIdAgencia());
            cabeceraCompra.setAgencia(agencia);

            Recurso recurso = new Recurso();
            recurso.setId(requestDao.getIdRecurso());
            cabeceraCompra.setRecurso(recurso);
            cabeceraCompra.setVersion(1d);
            return cabeceraCompra;
        });

        setEntityToDaoResponse = (entity -> {
            CabeceraCompraResponseDao responseDao = new CabeceraCompraResponseDao();
            responseDao.setId(entity.getId());
            responseDao.setRespuesta("OK");
            return responseDao;
        });
    }
}
