package com.ec.prontiauto.dao;

import java.util.List;

import com.ec.prontiauto.abstracts.AbstractResponseDao;
import com.ec.prontiauto.entidad.Contrato;

public class VendedorResponseDao extends AbstractResponseDao {

	private AgenciaResponseDao idAgencia;
	private TrabajadorResponseDao idTrabajador;
	private ProveedorResponseDao idProveedor;
	private List<Contrato> contratoCollection;

	public TrabajadorResponseDao getIdTrabajador() {
		return idTrabajador;
	}

	public void setIdTrabajador(TrabajadorResponseDao idTrabajador) {
		this.idTrabajador = idTrabajador;
	}

	public VendedorResponseDao() {

	}

	public AgenciaResponseDao getIdAgencia() {
		return idAgencia;
	}

	public void setIdAgencia(AgenciaResponseDao idAgencia) {
		this.idAgencia = idAgencia;
	}

	public List<Contrato> getContratoCollection() {
		return contratoCollection;
	}

	public void setContratoCollection(List<Contrato> contratoCollection) {
		this.contratoCollection = contratoCollection;
	}

	public ProveedorResponseDao getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(ProveedorResponseDao idProveedor) {
		this.idProveedor = idProveedor;
	}
}
