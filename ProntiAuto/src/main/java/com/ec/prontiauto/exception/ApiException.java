package com.ec.prontiauto.exception;

public class ApiException {
    private final String mensaje;
    private final String codigo;

    public ApiException(String mensaje, String codigo) {
        this.mensaje = mensaje;
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getCodigo() {
        return codigo;
    }

}
