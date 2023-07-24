package com.ec.prontiauto.dao;

import com.ec.prontiauto.abstracts.AbstractResponseDao;

public class SupervisorResponseDao extends AbstractResponseDao {

	private AgenciaResponseDao idAgencia;
	private TrabajadorResponseDao idTrabajador;

	public SupervisorResponseDao() {
	}

	public AgenciaResponseDao getIdAgencia() {
		return idAgencia;
	}

	public void setIdAgencia(AgenciaResponseDao idAgencia) {
		this.idAgencia = idAgencia;
	}

	public TrabajadorResponseDao getIdTrabajador() {
		return idTrabajador;
	}

	public void setIdTrabajador(TrabajadorResponseDao idTrabajador) {
		this.idTrabajador = idTrabajador;
	}

}
