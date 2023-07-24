package com.ec.prontiauto.enums;

public enum EnumAgenteRetencion {
    S("S", "S"), N("N", "N");

    private String clave;
    private String valor;

    private EnumAgenteRetencion(String clave, String valor) {
        this.clave = clave;
        this.valor = valor;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

}
