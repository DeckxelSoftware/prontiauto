package com.ec.prontiauto.entidad;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ec.prontiauto.abstracts.AbstractEntities;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

@Entity
@Table(name = "refinanciamiento")
public class Refinanciamiento extends AbstractEntities {

	@Column(name = "\"totalCuotas\"", nullable = false)
	@CsvBindByName(column = "total_cuotas")
	private Integer totalCuotas;

	@Column(name = "\"totalCuotasPagadas\"", nullable = false)
	@CsvBindByName(column = "total_cuotas_pagadas")
	private Integer totalCuotasPagadas;

	@Column(name = "\"totalCuotasMora\"", nullable = false)
	@CsvBindByName(column = "total_cuotas_mora")
	private Integer totalCuotasMora;

	@Column(name = "\"totalCuotasPagadasRefinanciamiento\"", nullable = false)
	@CsvBindByName(column = "total_cuotas_pagadas_refinanciamiento")
	private Integer totalCuotasPagadasRefinanciamiento;

	@Column(name = "\"totalCuotasFaltantesRefinanciamiento\"", nullable = false)
	@CsvBindByName(column = "total_cuotas_faltantes_refinanciamiento")
	private Integer totalCuotasFaltantesRefinanciamiento;

	@Column(name = "\"cuotasRestantesSinMora\"", nullable = false)
	@CsvBindByName(column = "cuotas_restantes_sin_mora")
	private Integer cuotasRestantesSinMora;

	@Column(name = "\"valorCuota\"", nullable = false)
	@CsvBindByName(column = "valor_cuota")
	private BigDecimal valorCuota;

	@Column(name = "\"valorPendientePago\"", nullable = false)
	@CsvBindByName(column = "valor_pendiente_pago")
	private BigDecimal valorPendientePago;

	@Column(name = "\"valorAgregarseCuota\"", nullable = false)
	@CsvBindByName(column = "valor_agregarse_cuota")
	private BigDecimal valorAgregarseCuota;

	@Column(name = "\"fechaRefinanciamiento\"", nullable = false)
	@CsvDate(value = "yyyy-MM-dd")
	@CsvBindByName(column = "fecha_refinanciamiento")

	private Date fechaRefinanciamiento;

	@ManyToOne
	@JoinColumn(name = "\"idHistoricoPlanContrato\"", referencedColumnName = "id", nullable = false)
	private HistoricoPlanContrato idHistoricoPlanContrato;

	@Transient
	@CsvBindByName(column = "id_historico_plan_contrato")
	private Integer idHistoricoPlanContrato1;
	
	   @Transient
	    @CsvBindByName(column = "cobro")
	    private Cobro cobro;
	    

	public Refinanciamiento() {
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

	public HistoricoPlanContrato getIdHistoricoPlanContrato() {
		return idHistoricoPlanContrato;
	}

	public void setIdHistoricoPlanContrato(HistoricoPlanContrato idHistoricoPlanContrato) {
		this.idHistoricoPlanContrato = idHistoricoPlanContrato;
	}

	public Integer getIdHistoricoPlanContrato1() {
		return idHistoricoPlanContrato1;
	}

	public void setIdHistoricoPlanContrato1(Integer idHistoricoPlanContrato1) {
		this.idHistoricoPlanContrato1 = idHistoricoPlanContrato1;
	}

	public Refinanciamiento setValoresDiferentes(Refinanciamiento registroAntiguo,
			Refinanciamiento registroActualizar) {
		if (registroActualizar.getTotalCuotas() != null) {
			registroAntiguo.setTotalCuotas(registroActualizar.getTotalCuotas());
		}
		if (registroActualizar.getTotalCuotasPagadas() != null) {
			registroAntiguo.setTotalCuotasPagadas(registroActualizar.getTotalCuotasPagadas());
		}

		if (registroActualizar.getTotalCuotasMora() != null) {
			registroAntiguo.setTotalCuotasMora(registroActualizar.getTotalCuotasMora());
		}

		if (registroActualizar.getTotalCuotasPagadasRefinanciamiento() != null) {
			registroAntiguo
					.setTotalCuotasPagadasRefinanciamiento(registroActualizar.getTotalCuotasPagadasRefinanciamiento());
		}
		if (registroActualizar.getTotalCuotasFaltantesRefinanciamiento() != null) {
			registroAntiguo.setTotalCuotasFaltantesRefinanciamiento(
					registroActualizar.getTotalCuotasFaltantesRefinanciamiento());
		}
		if (registroActualizar.getCuotasRestantesSinMora() != null) {
			registroAntiguo.setCuotasRestantesSinMora(registroActualizar.getCuotasRestantesSinMora());
		}

		if (registroActualizar.getValorCuota() != null) {
			registroAntiguo.setValorCuota(registroActualizar.getValorCuota());
		}

		if (registroActualizar.getValorPendientePago() != null) {
			registroAntiguo.setValorPendientePago(registroActualizar.getValorPendientePago());
		}
		if (registroActualizar.getValorAgregarseCuota() != null) {
			registroAntiguo.setValorAgregarseCuota(registroActualizar.getValorAgregarseCuota());
		}

		if (registroActualizar.getFechaRefinanciamiento() != null) {
			registroAntiguo.setFechaRefinanciamiento(registroActualizar.getFechaRefinanciamiento());
		}
		if (registroActualizar.getSisHabilitado() != null) {
			registroAntiguo.setSisHabilitado(registroActualizar.getSisHabilitado());
		}
		if (registroActualizar.getIdHistoricoPlanContrato() != null
				&& registroActualizar.getIdHistoricoPlanContrato().getId() != null) {
			registroAntiguo.setIdHistoricoPlanContrato(registroActualizar.getIdHistoricoPlanContrato());
		}
		return registroAntiguo;
	}

    public Cobro getCobro() {
        return cobro;
    }

    public void setCobro(Cobro cobro) {
        this.cobro = cobro;
    }

}
