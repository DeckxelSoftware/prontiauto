package com.ec.prontiauto.dao;

import java.util.List;

import com.ec.prontiauto.abstracts.AbstractResponseDao;

public class GrupoResponseDao extends AbstractResponseDao {

	private String nombreGrupo;
	private Double sumatoriaMontoMeta;
	private Integer totalContratosUsados;
	private Integer totalContratosPermitidos;
	private List<ClienteEnGrupoResponseDao> clienteEnGrupoCollection;

	public GrupoResponseDao() {
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

	public List<ClienteEnGrupoResponseDao> getClienteEnGrupoCollection() {
		return clienteEnGrupoCollection;
	}

	public void setClienteEnGrupoCollection(List<ClienteEnGrupoResponseDao> clienteEnGrupoCollection) {
		this.clienteEnGrupoCollection = clienteEnGrupoCollection;
	}
}
