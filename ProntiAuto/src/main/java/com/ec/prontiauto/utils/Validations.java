package com.ec.prontiauto.utils;

import java.io.IOException;

import com.ec.prontiauto.dao.ArchivoRequestDao;
import com.ec.prontiauto.exception.ApiRequestException;

public class Validations {

    public Validations() {
    }

    public void validateFormDataFile(ArchivoRequestDao dao) throws IOException {
        String typeFile = dao.getUploadFile().getContentType();

        if (dao.getUploadFile().isEmpty() || dao.getUploadFile() == null) {
            throw new ApiRequestException("Debe seleccionar un archivo");
        }
        if (dao.getUploadFile().getSize() > (1024 * 1024 * 15)) {
            throw new ApiRequestException("El peso del archivo es mayor a 50MB");
        }
        if (!typeFile.equals("application/pdf") && !typeFile.equals("image/jpeg") && !typeFile.equals("image/png")) {
            throw new ApiRequestException(
                    "El archivo no tiene un formato valido, verifique que el archivo sea un PDF o una imagen");
        }
        if (dao.getIdTabla() == null || dao.getIdTabla().isEmpty()
                || dao.getIdTabla().equals("")) {
            throw new ApiRequestException("El id de la tabla es requerido");
        }
        if (dao.getNombreTabla() == null || dao.getNombreTabla().isEmpty()
                || dao.getNombreTabla().equals("")) {
            throw new ApiRequestException("El nombre de la tabla es requerido");
        }
    }
}
