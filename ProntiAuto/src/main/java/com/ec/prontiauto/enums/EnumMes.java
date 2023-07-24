package com.ec.prontiauto.enums;

public enum EnumMes {
	ENE("ENE", "ENE"), FEB("FEB", "FEB"),MAR("MAR", "MAR"), ABR("ABR", "ABR"),MAY("MAY", "MAY"), JUN("JUN", "JUN"),
	JUL("JUL", "JUL"), AGO("AGO", "AGO"),SEP("SEP", "SEP"), OCT("OCT", "OCT"),NOV("NOV", "NOV"), DIC("DIC", "DIC");

    private String clave;
    private String valor;

    private EnumMes(String clave, String valor) {
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
