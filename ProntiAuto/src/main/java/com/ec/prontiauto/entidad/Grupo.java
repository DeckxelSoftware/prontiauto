package com.ec.prontiauto.entidad;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "grupo")
public class Grupo extends AbstractEntities {

	@Column(name = "\"nombreGrupo\"", nullable = false)
	@CsvBindByName(column = "nombre_grupo")
	private String nombreGrupo;

	@Column(name = "\"sumatoriaMontoMeta\"", nullable = false)
	@CsvBindByName(column = "sumatoria_monto_meta")
	private Double sumatoriaMontoMeta;

	@Column(name = "\"totalContratosUsados\"", nullable = false)
	@CsvBindByName(column = "total_contratos_usados")
	private Integer totalContratosUsados;

	@Column(name = "\"totalContratosPermitidos\"", nullable = false)
	@CsvBindByName(column = "total_contratos_permitidos")
	private Integer totalContratosPermitidos;

	@JsonIgnore
	@OneToMany(mappedBy = "idGrupo")
	private List<ClienteEnGrupo> clienteEnGrupoCollection;

	public Grupo() {
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

	public List<ClienteEnGrupo> getClienteEnGrupoCollection() {
		return clienteEnGrupoCollection;
	}

	public void setClienteEnGrupoCollection(List<ClienteEnGrupo> clienteEnGrupoCollection) {
		this.clienteEnGrupoCollection = clienteEnGrupoCollection;
	}

	public Grupo setValoresDiferentes(Grupo registroAntiguo, Grupo registroActualizar) {
		if (registroActualizar.getNombreGrupo() != null) {
			registroAntiguo.setNombreGrupo(registroActualizar.getNombreGrupo());
		}
		if (registroActualizar.getSumatoriaMontoMeta() != null) {
			registroAntiguo.setSumatoriaMontoMeta(registroActualizar.getSumatoriaMontoMeta());
		}
		if (registroActualizar.getTotalContratosUsados() != null) {
			registroAntiguo.setTotalContratosUsados(registroActualizar.getTotalContratosUsados());
		}
		if (registroActualizar.getTotalContratosPermitidos() != null) {
			registroAntiguo.setTotalContratosPermitidos(registroActualizar.getTotalContratosPermitidos());
		}
		if (registroActualizar.getSisHabilitado() != null) {
			registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
		}
		return registroAntiguo;
	}

}
