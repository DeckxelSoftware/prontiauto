package com.ec.prontiauto.dao;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.ec.prontiauto.abstracts.AbstractRequestDao;

public class ArchivoRequestDao extends AbstractRequestDao {

    private MultipartFile uploadFile;
    private String tipoArchivo;
    private String nombreTabla;
    private String idTabla;
    private String tipoDocumento;
    private String buffer;
    private String nombreOriginal;
    private Date sisActualizado;
    private Date sisCreado;

    public ArchivoRequestDao() {
    }

    public String getTipoArchivo() {
        return tipoArchivo;
    }

    public void setTipoArchivo(String tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }

    public String getNombreTabla() {
        return nombreTabla;
    }

    public void setNombreTabla(String nombreTabla) {
        this.nombreTabla = nombreTabla;
    }

    public String getIdTabla() {
        return idTabla;
    }

    public void setIdTabla(String idTabla) {
        this.idTabla = idTabla;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getBuffer() {
        return buffer;
    }

    public void setBuffer(String buffer) {
        this.buffer = buffer;
    }

    public String getNombreOriginal() {
        return nombreOriginal;
    }

    public void setNombreOriginal(String nombreOriginal) {
        this.nombreOriginal = nombreOriginal;
    }

    public MultipartFile getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(MultipartFile uploadFile) {
        this.uploadFile = uploadFile;
    }

    public Date getSisActualizado() {
        return sisActualizado;
    }

    public void setSisActualizado(Date sisActualizado) {
        this.sisActualizado = sisActualizado;
    }

    public Date getSisCreado() {
        return sisCreado;
    }

    public void setSisCreado(Date sisCreado) {
        this.sisCreado = sisCreado;
    }

}
