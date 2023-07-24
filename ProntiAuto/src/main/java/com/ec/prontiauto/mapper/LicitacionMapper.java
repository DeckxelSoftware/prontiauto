package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.LicitacionRequestDao;
import com.ec.prontiauto.dao.LicitacionResponseDao;
import com.ec.prontiauto.entidad.Contrato;
import com.ec.prontiauto.entidad.Licitacion;

public class LicitacionMapper {
	public static Function<LicitacionRequestDao, Licitacion> setDaoRequestToEntity;
	public static Function<Licitacion, LicitacionResponseDao> setEntityToDaoResponse;
	public static Function<List<Licitacion>, List<LicitacionResponseDao>> setEntityListToDaoResponseList;
	public static Function<List<Licitacion>, List<LicitacionResponseDao>> setEntityListToDaoReferenceList;
	public static Function<Licitacion, LicitacionResponseDao> setEntityToDaoReference;

	static {
		setDaoRequestToEntity = (daoRequest -> {
			Licitacion entity = new Licitacion();
			entity.setId(daoRequest.getId());
			entity.setValorOferta(daoRequest.getValorOferta());
			entity.setPorcentajeOferta(daoRequest.getPorcentajeOferta());
			entity.setFechaOferta(daoRequest.getFechaOferta());
			entity.setObservacion(daoRequest.getObservacion());
			entity.setEstado(daoRequest.getEstado());
			entity.setPlanSeleccionado(daoRequest.getPlanSeleccionado());
			entity.setPrecioPlan(daoRequest.getPrecioPlan());
			entity.setTotalMontoCobrado(daoRequest.getTotalMontoCobrado());
			entity.setaprobadoPorGerencia(daoRequest.getaprobadoPorGerencia());
			entity.setSisHabilitado(daoRequest.getSisHabilitado());
			Contrato contrato = new Contrato();
			contrato.setId(daoRequest.getIdContrato());
			entity.setIdContrato(contrato);

			return entity;
		});
		
		setEntityToDaoResponse = (entity -> {
			LicitacionResponseDao dao = new LicitacionResponseDao();
			dao.setId(entity.getId());
			dao.setValorOferta(entity.getValorOferta());
			dao.setPorcentajeOferta(entity.getPorcentajeOferta());
			dao.setFechaOferta(entity.getFechaOferta());
			dao.setObservacion(entity.getObservacion());
			dao.setEstado(entity.getEstado());
			dao.setPlanSeleccionado(entity.getPlanSeleccionado());
			dao.setPrecioPlan(entity.getPrecioPlan());
			dao.setTotalMontoCobrado(entity.getTotalMontoCobrado());
			dao.setaprobadoPorGerencia(entity.getaprobadoPorGerencia());
			dao.setSisHabilitado(entity.getSisHabilitado());
			dao.setSisActualizado(entity.getSisActualizado());
			dao.setSisCreado(entity.getSisCreado());
			if (entity.getIdContrato() != null && entity.getIdContrato().getId() != null) {
				dao.setIdContrato(ContratoMapper.setEntityToDaoReference.apply(entity.getIdContrato()));
			}
			return dao;
		});

		setEntityToDaoReference = (entity -> {
			LicitacionResponseDao dao = new LicitacionResponseDao();
			dao.setId(entity.getId());
			dao.setValorOferta(entity.getValorOferta());
			dao.setPorcentajeOferta(entity.getPorcentajeOferta());
			dao.setFechaOferta(entity.getFechaOferta());
			dao.setObservacion(entity.getObservacion());
			dao.setEstado(entity.getEstado());
			dao.setPlanSeleccionado(entity.getPlanSeleccionado());
			dao.setPrecioPlan(entity.getPrecioPlan());
			dao.setTotalMontoCobrado(entity.getTotalMontoCobrado());
			dao.setaprobadoPorGerencia(entity.getaprobadoPorGerencia());
			dao.setSisHabilitado(entity.getSisHabilitado());
			dao.setSisActualizado(entity.getSisActualizado());
			dao.setSisCreado(entity.getSisCreado());
			if (entity.getIdContrato() != null && entity.getIdContrato().getId() != null) {
				dao.setIdContrato(ContratoMapper.setEntityToDaoReference.apply(entity.getIdContrato()));
			}
			return dao;
		});

		setEntityListToDaoResponseList = (entityList -> {
			return (List<LicitacionResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(LicitacionMapper.setEntityToDaoResponse).collect(Collectors.toList());
		});
		setEntityListToDaoReferenceList = (entityList -> {
			return (List<LicitacionResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(LicitacionMapper.setEntityToDaoReference).collect(Collectors.toList());
		});
	}
}
