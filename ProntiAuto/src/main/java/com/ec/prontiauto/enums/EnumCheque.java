package com.ec.prontiauto.enums;

public enum EnumCheque {
    L("L", "L"), U("U", "U"), C("C", "C"), A("A", "A");

    private String clave;
    private String valor;

    private EnumCheque(String clave, String valor) {
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
