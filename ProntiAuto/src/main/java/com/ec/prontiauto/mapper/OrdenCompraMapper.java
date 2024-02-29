package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.OrdenCompraRequestDao;
import com.ec.prontiauto.dao.OrdenCompraResponseDao;
import com.ec.prontiauto.entidad.Articulo;
import com.ec.prontiauto.entidad.Contrato;
import com.ec.prontiauto.entidad.OrdenCompra;
import com.ec.prontiauto.entidad.Proveedor;

public class OrdenCompraMapper {
    public static Function<OrdenCompraRequestDao, OrdenCompra> setDaoRequestToEntity;
    public static Function<OrdenCompra, OrdenCompraResponseDao> setEntityToDaoResponse;
    public static Function<OrdenCompra, OrdenCompraResponseDao> setEntityToDaoReference;
    public static Function<List<OrdenCompra>, List<OrdenCompraResponseDao>> setEntityListToDaoResponseList;
    public static Function<List<OrdenCompra>, List<OrdenCompraResponseDao>> setEntityListToDaoReferenceList;

    static {
        setDaoRequestToEntity = (daoReq -> {
            OrdenCompra entity = new OrdenCompra();
            entity.setId(daoReq.getId());
            entity.setSisHabilitado(daoReq.getSisHabilitado() == null || daoReq.getSisHabilitado().isEmpty() ? "A" : daoReq.getSisHabilitado());

            entity.setFechaInicio(daoReq.getFechaInicio());
            entity.setNumeroOrdenContrato(daoReq.getNumeroOrdenContrato());
            entity.setFechaCartaOferta(daoReq.getFechaCartaOferta());
            entity.setFechaRegistroOferta(daoReq.getFechaRegistroOferta());
            entity.setNombreCliente(daoReq.getNombreCliente());
            entity.setCorreo(daoReq.getCorreo());
            entity.setTelefono(daoReq.getTelefono());
            entity.setTipoDocumentoIdentidad(daoReq.getTipoDocumentoIdentidad());
            entity.setDocumentoIdentidad(daoReq.getDocumentoIdentidad());
            entity.setMarca(daoReq.getMarca());
            entity.setModelo(daoReq.getModelo());
            entity.setMotor(daoReq.getMotor());
            entity.setChasis(daoReq.getChasis());
            entity.setPlaca(daoReq.getPlaca());
            entity.setColor(daoReq.getColor());
            entity.setAnio(daoReq.getAnio());
            entity.setValorSinIva(daoReq.getValorSinIva());
            entity.setBeneficiarioCheque(daoReq.getBeneficiarioCheque());
            entity.setValorTotal(daoReq.getValorTotal());
            entity.setObservacion(daoReq.getObservacion());
            entity.setTipoVehiculo(daoReq.getTipoVehiculo());

            if(daoReq.getIdProveedor() != null){
                Proveedor proveedor = new Proveedor();
                proveedor.setId((int) daoReq.getIdProveedor());
                entity.setIdProveedor(proveedor);
            }

            if( daoReq.getIdContrato() != null ){
                Contrato contrato = new Contrato();
                contrato.setId(daoReq.getIdContrato());
                entity.setIdContrato(contrato);
            }

            if(daoReq.getArticulo() != null){
                Articulo articulo = ArticuloMapper.setDaoRequestToEntity.apply(daoReq.getArticulo());
                entity.setArticulo(articulo);
            }

            return entity;
        });
        setEntityToDaoResponse = (entity -> {
            OrdenCompraResponseDao daoRes = new OrdenCompraResponseDao();

            daoRes.setId(entity.getId());
            daoRes.setSisHabilitado(entity.getSisHabilitado());

            daoRes.setFechaInicio(entity.getFechaInicio());
            daoRes.setNumeroOrdenContrato(entity.getNumeroOrdenContrato());
            daoRes.setFechaCartaOferta(entity.getFechaCartaOferta());
            daoRes.setFechaRegistroOferta(entity.getFechaRegistroOferta());
            daoRes.setNombreCliente(entity.getNombreCliente());
            daoRes.setCorreo(entity.getCorreo());
            daoRes.setTelefono(entity.getTelefono());
            daoRes.setTipoDocumentoIdentidad(entity.getTipoDocumentoIdentidad());
            daoRes.setDocumentoIdentidad(entity.getDocumentoIdentidad());
            daoRes.setMarca(entity.getMarca());
            daoRes.setModelo(entity.getModelo());
            daoRes.setMotor(entity.getMotor());
            daoRes.setChasis(entity.getChasis());
            daoRes.setPlaca(entity.getPlaca());
            daoRes.setColor(entity.getColor());
            daoRes.setAnio(entity.getAnio());
            daoRes.setValorSinIva(entity.getValorSinIva());
            daoRes.setBeneficiarioCheque(entity.getBeneficiarioCheque());
            daoRes.setValorTotal(entity.getValorTotal());
            daoRes.setObservacion(entity.getObservacion());
            daoRes.setTipoVehiculo(entity.getTipoVehiculo());

            if (entity.getIdContrato() != null && entity.getIdContrato().getId() != null) {
                daoRes.setIdContrato(ContratoMapper.setEntityToDaoReference.apply(entity.getIdContrato()));
            }

            if (entity.getArticulo() != null && entity.getArticulo().getId() != null) {
                daoRes.setIdArticulo(ArticuloMapper.setEntityToDaoReference.apply(entity.getArticulo()));
            }

            if (entity.getIdProveedor() != null && entity.getIdProveedor().getId() != null) {
                daoRes.setIdProveedor(ProveedorMapper.setEntityToDaoReference.apply(entity.getIdProveedor()));
            }

            return daoRes;
        });

        setEntityToDaoReference = (entity -> {
            OrdenCompraResponseDao daoRes = new OrdenCompraResponseDao();

            daoRes.setId(entity.getId());
            daoRes.setSisHabilitado(entity.getSisHabilitado());

            daoRes.setFechaInicio(entity.getFechaInicio());
            daoRes.setNumeroOrdenContrato(entity.getNumeroOrdenContrato());
            daoRes.setFechaCartaOferta(entity.getFechaCartaOferta());
            daoRes.setFechaRegistroOferta(entity.getFechaRegistroOferta());
            daoRes.setNombreCliente(entity.getNombreCliente());
            daoRes.setCorreo(entity.getCorreo());
            daoRes.setTelefono(entity.getTelefono());
            daoRes.setTipoDocumentoIdentidad(entity.getTipoDocumentoIdentidad());
            daoRes.setDocumentoIdentidad(entity.getDocumentoIdentidad());
            daoRes.setMarca(entity.getMarca());
            daoRes.setModelo(entity.getModelo());
            daoRes.setMotor(entity.getMotor());
            daoRes.setChasis(entity.getChasis());
            daoRes.setPlaca(entity.getPlaca());
            daoRes.setColor(entity.getColor());
            daoRes.setAnio(entity.getAnio());
            daoRes.setValorSinIva(entity.getValorSinIva());
            daoRes.setBeneficiarioCheque(entity.getBeneficiarioCheque());
            daoRes.setValorTotal(entity.getValorTotal());
            daoRes.setObservacion(entity.getObservacion());
            daoRes.setTipoVehiculo(entity.getTipoVehiculo());

            return daoRes;
        });

        setEntityListToDaoResponseList = (entityList -> {
            return (List<OrdenCompraResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(OrdenCompraMapper.setEntityToDaoResponse).collect(Collectors.toList());
        });

        setEntityListToDaoReferenceList = (entityList -> {
            return (List<OrdenCompraResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(OrdenCompraMapper.setEntityToDaoReference).collect(Collectors.toList());
        });
    }
}
