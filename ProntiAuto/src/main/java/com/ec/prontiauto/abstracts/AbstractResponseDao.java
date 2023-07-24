package com.ec.prontiauto.abstracts;

import java.util.Date;

import com.ec.prontiauto.dao.ArchivoRequestDao;

public class AbstractResponseDao extends AbstractRequestDao {
    private Date sisActualizado;
    private Date sisCreado;
    private ArchivoRequestDao sisArchivo;
    private ArchivoRequestDao sisImagen;

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

    public ArchivoRequestDao getSisArchivo() {
        return sisArchivo;
    }

    public void setSisArchivo(ArchivoRequestDao sisArchivo) {
        this.sisArchivo = sisArchivo;
    }

    public ArchivoRequestDao getSisImagen() {
        return sisImagen;
    }

    public void setSisImagen(ArchivoRequestDao sisImagen) {
        this.sisImagen = sisImagen;
    }

}
