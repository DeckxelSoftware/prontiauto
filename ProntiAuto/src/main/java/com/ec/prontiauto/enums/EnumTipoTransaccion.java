package com.ec.prontiauto.enums;

public enum EnumTipoTransaccion {
    I("I", "I"), E("E", "E"),  D("D", "D"), T("T", "T");

    private String clave;
    private String valor;

    private EnumTipoTransaccion(String clave, String valor) {
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
