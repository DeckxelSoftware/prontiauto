package com.ec.prontiauto.helper;


public class ListMapper<T> {
    private String clave;
    private T value;

    public ListMapper(String clave, T anio) {
        this.clave = clave;
        this.value = anio;
    }


    public String getClave() {
        return this.clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public T getValue() {
        return this.value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
