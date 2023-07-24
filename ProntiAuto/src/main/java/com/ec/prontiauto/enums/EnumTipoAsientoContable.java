package com.ec.prontiauto.enums;

public enum EnumTipoAsientoContable {
    E("E", "E"),  B("B", "B"), C("C", "C"), M("M", "M");

    private String clave;
    private String valor;

    private EnumTipoAsientoContable(String clave, String valor) {
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
