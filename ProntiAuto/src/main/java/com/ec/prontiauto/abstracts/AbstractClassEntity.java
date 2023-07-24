package com.ec.prontiauto.abstracts;

public class AbstractClassEntity<T> {
    final Class<T> typeClass;

    public AbstractClassEntity(Class<T> typeClass) {
        this.typeClass = typeClass;
    }

    public Class<T> getTypeClass() {
        return typeClass;
    }
}
