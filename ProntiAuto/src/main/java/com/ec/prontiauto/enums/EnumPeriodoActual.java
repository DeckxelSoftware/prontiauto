package com.ec.prontiauto.enums;

public enum EnumPeriodoActual {
     A("A", "A"), I("I", "I");

    private String clave;
    private String valor;

    private EnumPeriodoActual(String clave, String valor) {
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
