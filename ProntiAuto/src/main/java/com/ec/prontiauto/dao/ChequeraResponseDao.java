package com.ec.prontiauto.dao;

import java.sql.Date;
import java.util.List;

import com.ec.prontiauto.abstracts.AbstractResponseDao;

public class ChequeraResponseDao extends AbstractResponseDao {

	private Date fechaEmision;
	private Integer serieDesde;
	private Integer serieHasta;
	private CuentaBancariaEmpresaResponseDao idCuentaBancariaEmpresa;
	private List<ChequeResponseDao> ChequeCollection;

	public ChequeraResponseDao() {

	}

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public Integer getSerieDesde() {
		return serieDesde;
	}

	public void setSerieDesde(Integer serieDesde) {
		this.serieDesde = serieDesde;
	}

	public Integer getSerieHasta() {
		return serieHasta;
	}

	public void setSerieHasta(Integer serieHasta) {
		this.serieHasta = serieHasta;
	}

	public CuentaBancariaEmpresaResponseDao getIdCuentaBancariaEmpresa() {
		return idCuentaBancariaEmpresa;
	}

	public void setIdCuentaBancariaEmpresa(CuentaBancariaEmpresaResponseDao idCuentaBancariaEmpresa) {
		this.idCuentaBancariaEmpresa = idCuentaBancariaEmpresa;
	}

	public List<ChequeResponseDao> getChequeCollection() {
		return ChequeCollection;
	}

	public void setChequeCollection(List<ChequeResponseDao> chequeCollection) {
		ChequeCollection = chequeCollection;
	}

}
