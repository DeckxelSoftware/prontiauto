package com.ec.prontiauto.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ec.prontiauto.abstracts.AbstractEntities;

@Entity
@Table(name = "archivo")
public class Archivo extends AbstractEntities {

    @Column(name = "tipoArchivo", nullable = false, length = 1)
    private String tipoArchivo;

    @Column(name = "\"nombreTabla\"", nullable = false, length = 255)
    private String nombreTabla;

    @Column(name = "idTabla", nullable = false, length = 255)
    private String idTabla;

    @Column(name = "\"tipoDocumento\"", nullable = false, length = 1)
    private String tipoDocumento;

    @Column(name = "buffer", nullable = false)
    private byte[] buffer;

    @Column(name = "\"nombreOriginal\"", nullable = false, length = 255)
    private String nombreOriginal;

    @Column(name = "\"typeFile\"", nullable = false)
    private String typeFile;

    public Archivo() {
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

    public byte[] getBuffer() {
        return buffer;
    }

    public void setBuffer(byte[] buffer) {
        this.buffer = buffer;
    }

    public String getNombreOriginal() {
        return nombreOriginal;
    }

    public void setNombreOriginal(String nombreOriginal) {
        this.nombreOriginal = nombreOriginal;
    }

    public String getTypeFile() {
        return typeFile;
    }

    public void setTypeFile(String typeFile) {
        this.typeFile = typeFile;
    }

    public Archivo setValoresDiferentes(Archivo registroAntiguo, Archivo registroActualizar) {
        if (registroActualizar.getBuffer() != null) {
            registroAntiguo.setBuffer(registroActualizar.getBuffer());
        }
        if (registroActualizar.getNombreOriginal() != null) {
            registroAntiguo.setNombreOriginal(registroActualizar.getNombreOriginal());
        }
        if (registroActualizar.getTypeFile() != null) {
            registroAntiguo.setTypeFile(registroActualizar.getTypeFile());
        }
        if (registroActualizar.getTipoArchivo() != null) {
            registroAntiguo.setTipoArchivo(registroActualizar.getTipoArchivo());
        }
        if (registroActualizar.getNombreTabla() != null) {
            registroAntiguo.setNombreTabla(registroActualizar.getNombreTabla());
        }
        if (registroActualizar.getIdTabla() != null) {
            registroAntiguo.setIdTabla(registroActualizar.getIdTabla());
        }
        if (registroActualizar.getTipoDocumento() != null) {
            registroAntiguo.setTipoDocumento(registroActualizar.getTipoDocumento());
        }
        if (registroActualizar.getSisHabilitado() != null) {
            registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
        }
        return registroAntiguo;
    }
}