package com.ec.prontiauto.entidad;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.opencsv.bean.CsvBindByName;

import javax.persistence.*;

@Entity
@Table(name = "recurso")
public class Recurso extends AbstractEntities {

    @Column(name = "nombre", nullable = false)
    @CsvBindByName(column = "nombre")
    private String nombre;

    @OneToOne(mappedBy = "recurso")
    private CabeceraCompra cabecera;


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public CabeceraCompra getCabecera() {
        return cabecera;
    }

    public void setCabecera(CabeceraCompra cabecera) {
        this.cabecera = cabecera;
    }
}
