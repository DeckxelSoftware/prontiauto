package com.ec.prontiauto.mapper;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.ArchivoRequestDao;
import com.ec.prontiauto.entidad.Archivo;
import com.ec.prontiauto.utils.Validations;

public class ArchivoMapper {
    public static Function<ArchivoRequestDao, Archivo> setDaoRequestToEntity;
    public static Function<Archivo, ArchivoRequestDao> setEntityToDaoResponse;
    public static Function<List<Archivo>, List<ArchivoRequestDao>> setEntityListToDaoResponseList;

    static {
        setDaoRequestToEntity = (requestDao -> {
            Archivo entity = new Archivo();
            entity.setId(requestDao.getId());
            try {
                new Validations().validateFormDataFile(requestDao);
                if (requestDao.getUploadFile() != null) {
                    entity.setBuffer(requestDao.getUploadFile().getBytes());
                    entity.setNombreOriginal(requestDao.getUploadFile().getOriginalFilename());
                    entity.setTypeFile(requestDao.getUploadFile().getContentType());
                }
                entity.setTipoArchivo(requestDao.getTipoArchivo());
                entity.setNombreTabla(requestDao.getNombreTabla());
                entity.setIdTabla(requestDao.getIdTabla());
                entity.setTipoDocumento(requestDao.getTipoDocumento());
                entity.setSisHabilitado(requestDao.getSisHabilitado());
            } catch (IOException e) {
                throw new Error(e.getMessage());
            }

            return entity;
        });
        setEntityToDaoResponse = (entity -> {
            ArchivoRequestDao dao = new ArchivoRequestDao();
            dao.setId(entity.getId());
            dao.setTipoArchivo(entity.getTipoArchivo());
            dao.setNombreTabla(entity.getNombreTabla());
            dao.setIdTabla(entity.getIdTabla());
            dao.setTipoDocumento(entity.getTipoDocumento());
            dao.setBuffer("data:" + entity.getTypeFile() + ";base64,"
                    + Base64.getEncoder().encodeToString(entity.getBuffer()));
            dao.setNombreOriginal(entity.getNombreOriginal());
            dao.setSisHabilitado(entity.getSisHabilitado());
            return dao;
        });
        setEntityListToDaoResponseList = (entityList -> {
            return (List<ArchivoRequestDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(ArchivoMapper.setEntityToDaoResponse).collect(Collectors.toList());
        });
    }
}
