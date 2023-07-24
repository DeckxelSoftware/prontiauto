package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.CargaFamiliarRequestDao;
import com.ec.prontiauto.dao.CargaFamiliarResponseDao;
import com.ec.prontiauto.entidad.CargaFamiliar;
import com.ec.prontiauto.entidad.Trabajador;

public class CargaFamiliarMapper {
    public static Function<CargaFamiliarRequestDao, CargaFamiliar> setDaoRequestToEntity;
    public static Function<CargaFamiliar, CargaFamiliarResponseDao> setEntityToDaoResponse;
    public static Function<CargaFamiliar, CargaFamiliarResponseDao> setEntityToDaoReference;
    public static Function<List<CargaFamiliar>, List<CargaFamiliarResponseDao>> setEntityListToDaoResponseList;
    public static Function<List<CargaFamiliar>, List<CargaFamiliarResponseDao>> setEntityListToDaoReferenceList;

    static {
        setDaoRequestToEntity = (daoRequest -> {
            CargaFamiliar entity = new CargaFamiliar();
            entity.setId(daoRequest.getId());
            entity.setNombres(daoRequest.getNombres());
            entity.setApellidos(daoRequest.getApellidos());
            entity.setParentesco(daoRequest.getParentesco());
            entity.setTipoDocumento(daoRequest.getTipoDocumento());
            entity.setDocumentoIdentidad(daoRequest.getDocumentoIdentidad());
            entity.setGenero(daoRequest.getGenero());
            entity.setFechaNacimiento(daoRequest.getFechaNacimiento());
            entity.setDiscapacidad(daoRequest.getDiscapacidad());
            entity.setTipoDiscapacidad(daoRequest.getTipoDiscapacidad());
            entity.setEstudia(daoRequest.getEstudia());
            entity.setSisHabilitado(daoRequest.getSisHabilitado());
            entity.setAplicaUtilidad(daoRequest.getAplicaUtilidad());
            entity.setEdad(daoRequest.getEdad());

            Trabajador trabajador = new Trabajador();
            trabajador.setId(daoRequest.getIdTrabajador());
            entity.setIdTrabajador(trabajador);
            return entity;
        });
        setEntityToDaoResponse = (entity -> {
            CargaFamiliarResponseDao dao = new CargaFamiliarResponseDao();
            dao.setId(entity.getId());
            dao.setNombres(entity.getNombres());
            dao.setApellidos(entity.getApellidos());
            dao.setParentesco(entity.getParentesco());
            dao.setTipoDocumento(entity.getTipoDocumento());
            dao.setDocumentoIdentidad(entity.getDocumentoIdentidad());
            dao.setGenero(entity.getGenero());
            dao.setFechaNacimiento(entity.getFechaNacimiento());
            dao.setDiscapacidad(entity.getDiscapacidad());
            dao.setTipoDiscapacidad(entity.getTipoDiscapacidad());
            dao.setEstudia(entity.getEstudia());
            dao.setAplicaUtilidad(entity.getAplicaUtilidad());
            dao.setEdad(entity.getEdad());

            dao.setSisHabilitado(entity.getSisHabilitado());
            dao.setSisActualizado(entity.getSisActualizado());
            dao.setSisCreado(entity.getSisCreado());

            if (entity.getOnlyChildrenData() == null) {
                if (entity.getIdTrabajador() != null) {
                    dao.setIdTrabajador(
                            TrabajadorMapper.setEntityToDaoReference.apply(entity.getIdTrabajador()));
                }
            }

            return dao;
        });
        setEntityToDaoReference = (entity -> {
            CargaFamiliarResponseDao dao = new CargaFamiliarResponseDao();
            dao.setId(entity.getId());
            dao.setNombres(entity.getNombres());
            dao.setApellidos(entity.getApellidos());
            dao.setParentesco(entity.getParentesco());
            dao.setTipoDocumento(entity.getTipoDocumento());
            dao.setDocumentoIdentidad(entity.getDocumentoIdentidad());
            dao.setGenero(entity.getGenero());
            dao.setFechaNacimiento(entity.getFechaNacimiento());
            dao.setDiscapacidad(entity.getDiscapacidad());
            dao.setTipoDiscapacidad(entity.getTipoDiscapacidad());
            dao.setEstudia(entity.getEstudia());
            dao.setAplicaUtilidad(entity.getAplicaUtilidad());
            dao.setEdad(entity.getEdad());
            dao.setSisHabilitado(entity.getSisHabilitado());
            dao.setSisActualizado(entity.getSisActualizado());
            dao.setSisCreado(entity.getSisCreado());

            return dao;
        });

        setEntityListToDaoResponseList = (entityList -> {
            return (List<CargaFamiliarResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(CargaFamiliarMapper.setEntityToDaoResponse).collect(Collectors.toList());
        });
        setEntityListToDaoReferenceList = (entityList -> {
            return (List<CargaFamiliarResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(CargaFamiliarMapper.setEntityToDaoReference).collect(Collectors.toList());
        });
    }
}
