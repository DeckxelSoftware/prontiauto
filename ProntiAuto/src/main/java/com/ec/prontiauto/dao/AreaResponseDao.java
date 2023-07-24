package com.ec.prontiauto.dao;

import java.util.List;

import com.ec.prontiauto.abstracts.AbstractResponseDao;

public class AreaResponseDao extends AbstractResponseDao {
    
    private String nombre;
    
    private List<CargoResponseDao> CargoCollection;

    public AreaResponseDao() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

	public List<CargoResponseDao> getCargoCollection() {
		return CargoCollection;
	}

	public void setCargoCollection(List<CargoResponseDao> cargoCollection) {
		CargoCollection = cargoCollection;
	}

}
