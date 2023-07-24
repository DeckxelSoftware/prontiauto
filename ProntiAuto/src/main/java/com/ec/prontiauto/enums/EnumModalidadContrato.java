package com.ec.prontiauto.enums;

public enum EnumModalidadContrato {
    S("S", "S"), N("N", "N"), NP("NP", "NP");

    private String clave;
    private String valor;

    private EnumModalidadContrato(String clave, String valor) {
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
