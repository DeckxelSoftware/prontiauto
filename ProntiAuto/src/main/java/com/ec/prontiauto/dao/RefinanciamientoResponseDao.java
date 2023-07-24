package com.ec.prontiauto.dao;

import java.math.BigDecimal;
import java.sql.Date;

import com.ec.prontiauto.abstracts.AbstractResponseDao;

public class RefinanciamientoResponseDao extends AbstractResponseDao {

	private Integer totalCuotas;
	private Integer totalCuotasPagadas;
	private Integer totalCuotasMora;
	private Integer totalCuotasPagadasRefinanciamiento;
	private Integer totalCuotasFaltantesRefinanciamiento;
	private Integer cuotasRestantesSinMora;
	private BigDecimal valorCuota;
	private BigDecimal valorPendientePago;
	private BigDecimal valorAgregarseCuota;
	private Date fechaRefinanciamiento;
	private HistoricoPlanContratoResponseDao idHistoricoPlanContrato;

	public RefinanciamientoResponseDao() {
	}

	public Integer getTotalCuotas() {
		return totalCuotas;
	}

	public void setTotalCuotas(Integer totalCuotas) {
		this.totalCuotas = totalCuotas;
	}

	public Integer getTotalCuotasPagadas() {
		return totalCuotasPagadas;
	}

	public void setTotalCuotasPagadas(Integer totalCuotasPagadas) {
		this.totalCuotasPagadas = totalCuotasPagadas;
	}

	public Integer getTotalCuotasMora() {
		return totalCuotasMora;
	}

	public void setTotalCuotasMora(Integer totalCuotasMora) {
		this.totalCuotasMora = totalCuotasMora;
	}

	public Integer getTotalCuotasPagadasRefinanciamiento() {
		return totalCuotasPagadasRefinanciamiento;
	}

	public void setTotalCuotasPagadasRefinanciamiento(Integer totalCuotasPagadasRefinanciamiento) {
		this.totalCuotasPagadasRefinanciamiento = totalCuotasPagadasRefinanciamiento;
	}

	public Integer getTotalCuotasFaltantesRefinanciamiento() {
		return totalCuotasFaltantesRefinanciamiento;
	}

	public void setTotalCuotasFaltantesRefinanciamiento(Integer totalCuotasFaltantesRefinanciamiento) {
		this.totalCuotasFaltantesRefinanciamiento = totalCuotasFaltantesRefinanciamiento;
	}

	public Integer getCuotasRestantesSinMora() {
		return cuotasRestantesSinMora;
	}

	public void setCuotasRestantesSinMora(Integer cuotasRestantesSinMora) {
		this.cuotasRestantesSinMora = cuotasRestantesSinMora;
	}

	public BigDecimal getValorCuota() {
		return valorCuota;
	}

	public void setValorCuota(BigDecimal valorCuota) {
		this.valorCuota = valorCuota;
	}

	public BigDecimal getValorPendientePago() {
		return valorPendientePago;
	}

	public void setValorPendientePago(BigDecimal valorPendientePago) {
		this.valorPendientePago = valorPendientePago;
	}

	public BigDecimal getValorAgregarseCuota() {
		return valorAgregarseCuota;
	}

	public void setValorAgregarseCuota(BigDecimal valorAgregarseCuota) {
		this.valorAgregarseCuota = valorAgregarseCuota;
	}

	public Date getFechaRefinanciamiento() {
		return fechaRefinanciamiento;
	}

	public void setFechaRefinanciamiento(Date fechaRefinanciamiento) {
		this.fechaRefinanciamiento = fechaRefinanciamiento;
	}

	public HistoricoPlanContratoResponseDao getIdHistoricoPlanContrato() {
		return idHistoricoPlanContrato;
	}

	public void setIdHistoricoPlanContrato(HistoricoPlanContratoResponseDao idHistoricoPlanContrato) {
		this.idHistoricoPlanContrato = idHistoricoPlanContrato;
	}

}
