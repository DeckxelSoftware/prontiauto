package com.ec.prontiauto.dao;

import com.ec.prontiauto.abstracts.AbstractRequestDao;

public class GrupoRequestDao extends AbstractRequestDao {
	private String nombreGrupo;
	private Integer totalContratosPermitidos;

	public GrupoRequestDao() {
	}



	public String getNombreGrupo() {
        return nombreGrupo;
    }



    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }


	public Integer getTotalContratosPermitidos() {
		return totalContratosPermitidos;
	}

	public void setTotalContratosPermitidos(Integer totalContratosPermitidos) {
		this.totalContratosPermitidos = totalContratosPermitidos;
	}
}
