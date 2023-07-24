package com.ec.prontiauto.dao;

import com.ec.prontiauto.abstracts.AbstractRequestDao;

public class GrupoRequestDao extends AbstractRequestDao {
	private String nombreGrupo;
	private Double sumatoriaMontoMeta;
	private Integer totalContratosUsados;
	private Integer totalContratosPermitidos;

	public GrupoRequestDao() {
	}



	public String getNombreGrupo() {
        return nombreGrupo;
    }



    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }



    public Double getSumatoriaMontoMeta() {
		return sumatoriaMontoMeta;
	}

	public void setSumatoriaMontoMeta(Double sumatoriaMontoMeta) {
		this.sumatoriaMontoMeta = sumatoriaMontoMeta;
	}

	public Integer getTotalContratosUsados() {
		return totalContratosUsados;
	}

	public void setTotalContratosUsados(Integer totalContratosUsados) {
		this.totalContratosUsados = totalContratosUsados;
	}

	public Integer getTotalContratosPermitidos() {
		return totalContratosPermitidos;
	}

	public void setTotalContratosPermitidos(Integer totalContratosPermitidos) {
		this.totalContratosPermitidos = totalContratosPermitidos;
	}
}
