package com.ec.prontiauto.dao;



import java.sql.Date;

import com.ec.prontiauto.abstracts.AbstractRequestDao;

public class ChequeraRequestDao extends AbstractRequestDao {

	private Date fechaEmision;
	private Integer serieDesde;
	private Integer serieHasta;
	private Integer idCuentaBancariaEmpresa;

	public ChequeraRequestDao() {

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

	public Integer getIdCuentaBancariaEmpresa() {
		return idCuentaBancariaEmpresa;
	}

	public void setIdCuentaBancariaEmpresa(Integer idCuentaBancariaEmpresa) {
		this.idCuentaBancariaEmpresa = idCuentaBancariaEmpresa;
	}

	
}
