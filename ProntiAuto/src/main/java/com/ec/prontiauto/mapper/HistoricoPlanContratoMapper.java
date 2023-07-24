package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.HistoricoPlanContratoRequestDao;
import com.ec.prontiauto.dao.HistoricoPlanContratoResponseDao;
import com.ec.prontiauto.entidad.Contrato;
import com.ec.prontiauto.entidad.HistoricoPlanContrato;
import com.ec.prontiauto.entidad.Plan;

public class HistoricoPlanContratoMapper {

	public static Function<HistoricoPlanContratoRequestDao, HistoricoPlanContrato> setDaoRequestToEntity;
	public static Function<HistoricoPlanContrato, HistoricoPlanContratoResponseDao> setEntityToDaoResponse;
	public static Function<HistoricoPlanContrato, HistoricoPlanContratoResponseDao> setEntityToDaoReference;
	public static Function<List<HistoricoPlanContrato>, List<HistoricoPlanContratoResponseDao>> setEntityListToDaoResponseList;

	static {
		setDaoRequestToEntity = (daoRequest -> {
			HistoricoPlanContrato entity = new HistoricoPlanContrato();
			entity.setId(daoRequest.getId());
			entity.setTotalInscripcionPlan(daoRequest.getTotalInscripcionPlan());
			entity.setValorDsctoInscripcion(daoRequest.getValorDsctoInscripcion());
			entity.setTotalCobroInscripcion(daoRequest.getTotalCobroInscripcion());
			entity.setCapitalTotal(daoRequest.getCapitalTotal());
			entity.setCapitalPorRefinanciamiento(daoRequest.getCapitalPorRefinanciamiento());
			entity.setAbonosCapitalActual(daoRequest.getAbonosCapitalActual());
			entity.setSaldoCapital(daoRequest.getSaldoCapital());
			entity.setValorTasaAdministrativa(daoRequest.getValorTasaAdministrativa());
			entity.setTotalTasaAdministrativaCobrada(daoRequest.getTotalTasaAdministrativaCobrada());
			entity.setTotalCuotasCobradas(daoRequest.getTotalCuotasCobradas());
			entity.setTotalCuotasMoraActual(daoRequest.getTotalCuotasMoraActual());
			entity.setTotalCuotasMora(daoRequest.getTotalCuotasMora());
			entity.setSisHabilitado(daoRequest.getSisHabilitado());
			entity.setValorADevolver(daoRequest.getValorADevolver());
			entity.setValorPrimerCapitalDevolver(daoRequest.getValorPrimerCapitalDevolver());
			entity.setInscripcionADevolver(daoRequest.getInscripcionADevolver());
			entity.setTasaAdministrativaADevolver(daoRequest.getTasaAdministrativaADevolver());
			entity.setCapitalALiquidar(daoRequest.getCapitalALiquidar());
			entity.setTasaAdministrativaALiquidar(daoRequest.getTasaAdministrativaALiquidar());
			entity.setCargosAdjudicacion(daoRequest.getCargosAdjudicacion());
			entity.setInscripcionEstaPagada(daoRequest.getInscripcionEstaPagada());
			entity.setValorPagadoInscripcion(daoRequest.getValorPagadoInscripcion());
			Contrato contrato = new Contrato();
			contrato.setId(daoRequest.getIdContrato());
			entity.setIdContrato(contrato);
			Plan plan = new Plan();
			plan.setId(daoRequest.getIdPlan());
			entity.setIdPlan(plan);
			return entity;
		});

		setEntityToDaoResponse = (entity -> {
			HistoricoPlanContratoResponseDao dao = new HistoricoPlanContratoResponseDao();
			dao.setId(entity.getId());
			dao.setTotalInscripcionPlan(entity.getTotalInscripcionPlan());
			dao.setValorDsctoInscripcion(entity.getValorDsctoInscripcion());
			dao.setTotalCobroInscripcion(entity.getTotalCobroInscripcion());
			dao.setCapitalTotal(entity.getCapitalTotal());
			dao.setCapitalPorRefinanciamiento(entity.getCapitalPorRefinanciamiento());
			dao.setAbonosCapitalActual(entity.getAbonosCapitalActual());
			dao.setSaldoCapital(entity.getSaldoCapital());
			dao.setValorTasaAdministrativa(entity.getValorTasaAdministrativa());
			dao.setTotalTasaAdministrativaCobrada(entity.getTotalTasaAdministrativaCobrada());
			dao.setTotalCuotasCobradas(entity.getTotalCuotasCobradas());
			dao.setTotalCuotasMoraActual(entity.getTotalCuotasMoraActual());
			dao.setTotalCuotasMora(entity.getTotalCuotasMora());
			dao.setTotalMontoCobrado(entity.getTotalMontoCobrado());
			dao.setValorDsctoPrimeraCuota(entity.getValorDsctoPrimeraCuota());
			dao.setTotalCobroPrimeraCuota(entity.getTotalCobroPrimeraCuota());
			dao.setValorRecargo(entity.getValorRecargo());
			dao.setNumCuotasAbajoHaciaArriba(entity.getNumCuotasAbajoHaciaArriba());
			dao.setValorADevolver(entity.getValorADevolver());
			dao.setSisHabilitado(entity.getSisHabilitado());
			dao.setSisActualizado(entity.getSisActualizado());
			dao.setSisCreado(entity.getSisCreado());
			dao.setSisArchivo(entity.getSisArchivo());
			dao.setSisImagen(entity.getSisImagen());
			dao.setValorADevolver(entity.getValorADevolver());
			dao.setValorPrimerCapitalDevolver(entity.getValorPrimerCapitalDevolver());
			dao.setInscripcionADevolver(entity.getInscripcionADevolver());
			dao.setTasaAdministrativaADevolver(entity.getTasaAdministrativaADevolver());
			dao.setCapitalALiquidar(entity.getCapitalALiquidar());
			dao.setTasaAdministrativaALiquidar(entity.getTasaAdministrativaALiquidar());
			dao.setCargosAdjudicacion(entity.getCargosAdjudicacion());
			dao.setInscripcionEstaPagada(entity.getInscripcionEstaPagada());
			dao.setValorPagadoInscripcion(entity.getValorPagadoInscripcion());
			dao.setIdContrato(ContratoMapper.setEntityToDaoReference.apply(entity.getIdContrato()));
			dao.setIdPlan(PlanMapper.setEntityToDaoReference.apply(entity.getIdPlan()));
			if (entity.getRefinanciamientoCollection() != null) {
				dao.setRefinanciamientoCollection(RefinanciamientoMapper.setEntityListToDaoResponseList
						.apply(entity.getRefinanciamientoCollection()));
			}
			if (entity.getCuotaCollection() != null) {
				dao.setCuotaCollection(CuotaMapper.setEntityListToDaoResponseList.apply(entity.getCuotaCollection()));
			}
			return dao;
		});

		setEntityToDaoReference = (entity -> {
			HistoricoPlanContratoResponseDao dao = new HistoricoPlanContratoResponseDao();
			dao.setId(entity.getId());
			dao.setTotalInscripcionPlan(entity.getTotalInscripcionPlan());
			dao.setValorDsctoInscripcion(entity.getValorDsctoInscripcion());
			dao.setTotalCobroInscripcion(entity.getTotalCobroInscripcion());
			dao.setCapitalTotal(entity.getCapitalTotal());
			dao.setCapitalPorRefinanciamiento(entity.getCapitalPorRefinanciamiento());
			dao.setAbonosCapitalActual(entity.getAbonosCapitalActual());
			dao.setSaldoCapital(entity.getSaldoCapital());
			dao.setValorTasaAdministrativa(entity.getValorTasaAdministrativa());
			dao.setTotalTasaAdministrativaCobrada(entity.getTotalTasaAdministrativaCobrada());
			dao.setTotalCuotasCobradas(entity.getTotalCuotasCobradas());
			dao.setTotalCuotasMoraActual(entity.getTotalCuotasMoraActual());
			dao.setTotalCuotasMora(entity.getTotalCuotasMora());
			dao.setSisHabilitado(entity.getSisHabilitado());
			dao.setSisActualizado(entity.getSisActualizado());
			dao.setSisCreado(entity.getSisCreado());
			dao.setSisArchivo(entity.getSisArchivo());
			dao.setSisImagen(entity.getSisImagen());
			dao.setTotalMontoCobrado(entity.getTotalMontoCobrado());
			dao.setValorDsctoPrimeraCuota(entity.getValorDsctoPrimeraCuota());
			dao.setTotalCobroPrimeraCuota(entity.getTotalCobroPrimeraCuota());
			dao.setValorRecargo(entity.getValorRecargo());
			dao.setNumCuotasAbajoHaciaArriba(entity.getNumCuotasAbajoHaciaArriba());
			dao.setValorADevolver(entity.getValorADevolver());
			dao.setValorPrimerCapitalDevolver(entity.getValorPrimerCapitalDevolver());
			dao.setInscripcionADevolver(entity.getInscripcionADevolver());
			dao.setTasaAdministrativaADevolver(entity.getTasaAdministrativaADevolver());
			dao.setCapitalALiquidar(entity.getCapitalALiquidar());
			dao.setTasaAdministrativaALiquidar(entity.getTasaAdministrativaALiquidar());
			dao.setCargosAdjudicacion(entity.getCargosAdjudicacion());
			dao.setInscripcionEstaPagada(entity.getInscripcionEstaPagada());
			dao.setValorPagadoInscripcion(entity.getValorPagadoInscripcion());
			dao.setIdContrato(ContratoMapper.setEntityToDaoReference.apply(entity.getIdContrato()));
			dao.setIdPlan(PlanMapper.setEntityToDaoReference.apply(entity.getIdPlan()));
			return dao;
		});

		setEntityListToDaoResponseList = (entityList -> {
			return (List<HistoricoPlanContratoResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(HistoricoPlanContratoMapper.setEntityToDaoResponse).collect(Collectors.toList());
		});
	}
}
