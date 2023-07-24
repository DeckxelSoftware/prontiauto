package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.CuentaBancariaEmpresaRequestDao;
import com.ec.prontiauto.dao.CuentaBancariaEmpresaResponseDao;
import com.ec.prontiauto.entidad.Banco;
import com.ec.prontiauto.entidad.CuentaBancariaEmpresa;
import com.ec.prontiauto.entidad.Empresa;
import com.ec.prontiauto.entidad.InformacionFinanciera;

public class CuentaBancariaEmpresaMapper {
    public static Function<CuentaBancariaEmpresaRequestDao, CuentaBancariaEmpresa> setDaoRequestToEntity;
    public static Function<CuentaBancariaEmpresa, CuentaBancariaEmpresaResponseDao> setEntityToDaoResponse;
    public static Function<CuentaBancariaEmpresa, CuentaBancariaEmpresaResponseDao> setEntityToDaoReference;
    public static Function<CuentaBancariaEmpresa, CuentaBancariaEmpresaResponseDao> setEntityToDaoReferencePapas;
    public static Function<List<CuentaBancariaEmpresa>, List<CuentaBancariaEmpresaResponseDao>> setEntityListToDaoResponseList;
    public static Function<List<CuentaBancariaEmpresa>, List<CuentaBancariaEmpresaResponseDao>> setEntityListToDaoReferenceList;

    static {
        setDaoRequestToEntity = (daoRequest -> {
            CuentaBancariaEmpresa entity = new CuentaBancariaEmpresa();
            entity.setId(daoRequest.getId());
            entity.setNumeroCuenta(daoRequest.getNumeroCuenta());
            entity.setTipoCuenta(daoRequest.getTipoCuenta());
            entity.setSisHabilitado(daoRequest.getSisHabilitado());
            Empresa empresa = new Empresa();
            empresa.setId(daoRequest.getIdEmpresa());
            entity.setIdEmpresa(empresa);
            Banco banco = new Banco();
            banco.setId(daoRequest.getIdBanco());
            entity.setIdBanco(banco);
            InformacionFinanciera informacionFinanciera = new InformacionFinanciera();
            informacionFinanciera.setId(daoRequest.getIdInformacionFinanciera());
            entity.setIdInformacionFinanciera(informacionFinanciera);
            return entity;
        });
        setEntityToDaoResponse = (entity -> {
            CuentaBancariaEmpresaResponseDao dao = new CuentaBancariaEmpresaResponseDao();
            dao.setId(entity.getId());
            dao.setNumeroCuenta(entity.getNumeroCuenta());
            dao.setTipoCuenta(entity.getTipoCuenta());
            dao.setSisHabilitado(entity.getSisHabilitado());
            dao.setSisActualizado(entity.getSisActualizado());
            dao.setSisCreado(entity.getSisCreado());
            dao.setSisArchivo(entity.getSisArchivo());
            dao.setSisImagen(entity.getSisImagen());
            if (entity.getIdBanco() != null) {
                dao.setIdBanco(BancoMapper.setEntityToDaoReference.apply(entity.getIdBanco()));
            }
            if (entity.getIdEmpresa() != null && entity.getIdEmpresa().getId() != null) {
                dao.setIdEmpresa(EmpresaMapper.setEntityToDaoReference.apply(entity.getIdEmpresa()));
            }
            return dao;
        });

        setEntityToDaoReference = (entity -> {
            CuentaBancariaEmpresaResponseDao dao = new CuentaBancariaEmpresaResponseDao();
            dao.setId(entity.getId());
            dao.setNumeroCuenta(entity.getNumeroCuenta());
            dao.setTipoCuenta(entity.getTipoCuenta());
            dao.setSisHabilitado(entity.getSisHabilitado());
            dao.setSisActualizado(entity.getSisActualizado());
            dao.setSisCreado(entity.getSisCreado());
            dao.setSisArchivo(entity.getSisArchivo());
            dao.setSisImagen(entity.getSisImagen());
            return dao;
        });

        setEntityToDaoReferencePapas = (entity -> {
            CuentaBancariaEmpresaResponseDao dao = new CuentaBancariaEmpresaResponseDao();
            dao.setId(entity.getId());
            dao.setNumeroCuenta(entity.getNumeroCuenta());
            dao.setTipoCuenta(entity.getTipoCuenta());
            dao.setSisHabilitado(entity.getSisHabilitado());
            dao.setSisActualizado(entity.getSisActualizado());
            dao.setSisCreado(entity.getSisCreado());
            dao.setSisArchivo(entity.getSisArchivo());
            dao.setSisImagen(entity.getSisImagen());
            if (entity.getIdBanco() != null) {
                dao.setIdBanco(BancoMapper.setEntityToDaoReference.apply(entity.getIdBanco()));
            }
            if (entity.getIdEmpresa() != null && entity.getIdEmpresa().getId() != null) {
                dao.setIdEmpresa(EmpresaMapper.setEntityToDaoReference.apply(entity.getIdEmpresa()));
            }
            return dao;
        });

        setEntityListToDaoResponseList = (entityList -> {
            return (List<CuentaBancariaEmpresaResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(CuentaBancariaEmpresaMapper.setEntityToDaoResponse).collect(Collectors.toList());
        });
        setEntityListToDaoReferenceList = (entityList -> {
            return (List<CuentaBancariaEmpresaResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(CuentaBancariaEmpresaMapper.setEntityToDaoReferencePapas).collect(Collectors.toList());
        });
    }
}
