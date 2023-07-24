package com.ec.prontiauto.enums;

public enum EnumCodigoAuxiliarRubrosRol {
	AE("AE", "AE"), AI("AI", "AI"), EF("EF", "EF"), EL("EL", "EL"), EO("EO", "EO"),
	EP("EP", "EP"), IF("IF", "IF"), IL("IL", "IL"), IO("IO", "IO"), PV("PV", "PV"),
	TA("TA", "TA"), TI("TI", "TI");

    private String clave;
    private String valor;

    private EnumCodigoAuxiliarRubrosRol(String clave, String valor) {
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
