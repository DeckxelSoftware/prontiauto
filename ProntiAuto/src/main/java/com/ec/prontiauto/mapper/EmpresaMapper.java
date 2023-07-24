package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.EmpresaRequestDao;
import com.ec.prontiauto.dao.EmpresaResponseDao;
import com.ec.prontiauto.entidad.Empresa;

public class EmpresaMapper {
    public static Function<EmpresaRequestDao, Empresa> setDaoRequestToEntity;
    public static Function<Empresa, EmpresaResponseDao> setEntityToDaoResponse;
    public static Function<Empresa, EmpresaResponseDao> setEntityToDaoReference;
    public static Function<List<Empresa>, List<EmpresaResponseDao>> setEntityListToDaoResponseList;
    public static Function<List<Empresa>, List<EmpresaResponseDao>> setEntityListToDaoReferenceList;

    static {
        setDaoRequestToEntity = (daoRequest -> {
            Empresa entity = new Empresa();
            entity.setId(daoRequest.getId());
            entity.setNombreComercial(daoRequest.getNombreComercial());
            entity.setRazonSocial(daoRequest.getRazonSocial());
            entity.setRucEmpresa(daoRequest.getRucEmpresa());
            entity.setDireccionEmpresa(daoRequest.getDireccionEmpresa());
            entity.setTelefonoEmpresa(daoRequest.getTelefonoEmpresa());
            entity.setDocumentoRepresentanteLegal(daoRequest.getDocumentoRepresentanteLegal());
            entity.setNombreRepresentanteLegal(daoRequest.getNombreRepresentanteLegal());
            entity.setNombreContador(daoRequest.getNombreContador());
            entity.setRucContador(daoRequest.getRucContador());
            entity.setTelefonoContador(daoRequest.getTelefonoContador());
            entity.setCorreoEmpresa(daoRequest.getCorreoEmpresa());
            entity.setCorreoContador(daoRequest.getCorreoContador());
            entity.setCorreoRepresentanteLegal(daoRequest.getCorreoRepresentanteLegal());
            entity.setTipoEmpresa(daoRequest.getTipoEmpresa());
            entity.setSisHabilitado(daoRequest.getSisHabilitado());
            entity.setClaseContribuyente(daoRequest.getClaseContribuyente());
            entity.setObligadoLlevarContabilidad(daoRequest.getObligadoLlevarContabilidad());
            entity.setAgenteRetencion(daoRequest.getAgenteRetencion());
            return entity;
        });
        setEntityToDaoResponse = (entity -> {
            EmpresaResponseDao dao = new EmpresaResponseDao();
            dao.setId(entity.getId());
            dao.setNombreComercial(entity.getNombreComercial());
            dao.setRazonSocial(entity.getRazonSocial());
            dao.setRucEmpresa(entity.getRucEmpresa());
            dao.setDireccionEmpresa(entity.getDireccionEmpresa());
            dao.setTelefonoEmpresa(entity.getTelefonoEmpresa());
            dao.setDocumentoRepresentanteLegal(entity.getDocumentoRepresentanteLegal());
            dao.setNombreRepresentanteLegal(entity.getNombreRepresentanteLegal());
            dao.setNombreContador(entity.getNombreContador());
            dao.setRucContador(entity.getRucContador());
            dao.setTelefonoContador(entity.getTelefonoContador());
            dao.setCorreoEmpresa(entity.getCorreoEmpresa());
            dao.setCorreoContador(entity.getCorreoContador());
            dao.setCorreoRepresentanteLegal(entity.getCorreoRepresentanteLegal());
            dao.setTipoEmpresa(entity.getTipoEmpresa());
            dao.setSisHabilitado(entity.getSisHabilitado());
            dao.setSisActualizado(entity.getSisActualizado());
            dao.setSisCreado(entity.getSisCreado());
            dao.setSisArchivo(entity.getSisArchivo());
            dao.setSisImagen(entity.getSisImagen());
            dao.setClaseContribuyente(entity.getClaseContribuyente());
            dao.setObligadoLlevarContabilidad(entity.getObligadoLlevarContabilidad());
            dao.setAgenteRetencion(entity.getAgenteRetencion());

            if (entity.getCuentaBancariaEmpresaCollection() != null) {
                dao.setCuentaBancariaEmpresaCollection(CuentaBancariaEmpresaMapper.setEntityListToDaoResponseList
                        .apply(entity.getCuentaBancariaEmpresaCollection()));
            }
            return dao;
        });
        setEntityToDaoReference = (entity -> {
            EmpresaResponseDao dao = new EmpresaResponseDao();
            dao.setId(entity.getId());
            dao.setNombreComercial(entity.getNombreComercial());
            dao.setRazonSocial(entity.getRazonSocial());
            dao.setRucEmpresa(entity.getRucEmpresa());
            dao.setDireccionEmpresa(entity.getDireccionEmpresa());
            dao.setTelefonoEmpresa(entity.getTelefonoEmpresa());
            dao.setDocumentoRepresentanteLegal(entity.getDocumentoRepresentanteLegal());
            dao.setNombreRepresentanteLegal(entity.getNombreRepresentanteLegal());
            dao.setNombreContador(entity.getNombreContador());
            dao.setRucContador(entity.getRucContador());
            dao.setTelefonoContador(entity.getTelefonoContador());
            dao.setCorreoEmpresa(entity.getCorreoEmpresa());
            dao.setCorreoContador(entity.getCorreoContador());
            dao.setCorreoRepresentanteLegal(entity.getCorreoRepresentanteLegal());
            dao.setTipoEmpresa(entity.getTipoEmpresa());
            dao.setSisHabilitado(entity.getSisHabilitado());
            dao.setSisActualizado(entity.getSisActualizado());
            dao.setSisCreado(entity.getSisCreado());
            dao.setSisArchivo(entity.getSisArchivo());
            dao.setSisImagen(entity.getSisImagen());
            dao.setClaseContribuyente(entity.getClaseContribuyente());
            dao.setObligadoLlevarContabilidad(entity.getObligadoLlevarContabilidad());
            dao.setAgenteRetencion(entity.getAgenteRetencion());

            return dao;
        });
        setEntityListToDaoResponseList = (entityList -> {
            return (List<EmpresaResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(EmpresaMapper.setEntityToDaoResponse).collect(Collectors.toList());
        });
        setEntityListToDaoReferenceList = (entityList -> {
            return (List<EmpresaResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(EmpresaMapper.setEntityToDaoReference).collect(Collectors.toList());
        });
    }
}
