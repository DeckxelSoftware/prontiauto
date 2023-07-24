package com.ec.prontiauto.dao;

import java.math.BigDecimal;
import java.sql.Date;

import com.ec.prontiauto.abstracts.AbstractRequestDao;
import com.ec.prontiauto.entidad.Cobro;

public class RefinanciamientoRequestDao extends AbstractRequestDao {
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
	private Integer idHistoricoPlanContrato;
	private CobroRequestDao cobro;

	public RefinanciamientoRequestDao() {
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

	public Integer getIdHistoricoPlanContrato() {
		return idHistoricoPlanContrato;
	}

	public void setIdHistoricoPlanContrato(Integer idHistoricoPlanContrato) {
		this.idHistoricoPlanContrato = idHistoricoPlanContrato;
	}

    public CobroRequestDao getCobro() {
        return cobro;
    }

    public void setCobro(CobroRequestDao cobro) {
        this.cobro = cobro;
    }

}
