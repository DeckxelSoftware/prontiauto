package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.PeriodoLaboralRequestDao;
import com.ec.prontiauto.dao.PeriodoLaboralResponseDao;
import com.ec.prontiauto.entidad.PeriodoLaboral;

public class PeriodoLaboralMapper {
	public static Function<PeriodoLaboralRequestDao, PeriodoLaboral> setDaoRequestToEntity;
	public static Function<PeriodoLaboral, PeriodoLaboralResponseDao> setEntityToDaoResponse;
	public static Function<List<PeriodoLaboral>, List<PeriodoLaboralResponseDao>> setEntityListToDaoResponseList;
	public static Function<PeriodoLaboral, PeriodoLaboralResponseDao> setEntityToDaoReference;
	public static Function<List<PeriodoLaboral>, List<PeriodoLaboralResponseDao>> setEntityListToDaoReferenceList;

	static {
		setDaoRequestToEntity = (daoRequest -> {
			PeriodoLaboral entity = new PeriodoLaboral();
			entity.setId(daoRequest.getId());
			entity.setAnio(daoRequest.getAnio());
			entity.setMes(daoRequest.getMes());
			entity.setFechaInicio(daoRequest.getFechaInicio());
			entity.setFechaFin(daoRequest.getFechaFin());
			entity.setActivo(daoRequest.getActivo());
			entity.setSisHabilitado(daoRequest.getSisHabilitado());
			return entity;
		});
		setEntityToDaoResponse = (entity -> {
			PeriodoLaboralResponseDao dao = new PeriodoLaboralResponseDao();
			dao.setId(entity.getId());
			dao.setAnio(entity.getAnio());
			dao.setMes(entity.getMes());
			dao.setFechaInicio(entity.getFechaInicio());
			dao.setFechaFin(entity.getFechaFin());
			dao.setActivo(entity.getActivo());
			dao.setSisHabilitado(entity.getSisHabilitado());
			dao.setSisActualizado(entity.getSisActualizado());
			dao.setSisCreado(entity.getSisCreado());

			return dao;
		});

		setEntityToDaoReference = (entity -> {
			PeriodoLaboralResponseDao dao = new PeriodoLaboralResponseDao();
			dao.setId(entity.getId());
			dao.setAnio(entity.getAnio());
			dao.setMes(entity.getMes());
			dao.setFechaInicio(entity.getFechaInicio());
			dao.setFechaFin(entity.getFechaFin());
			dao.setActivo(entity.getActivo());
			dao.setSisHabilitado(entity.getSisHabilitado());
			dao.setSisActualizado(entity.getSisActualizado());
			dao.setSisCreado(entity.getSisCreado());
			return dao;
		});
		setEntityListToDaoResponseList = (entityList -> {
			return (List<PeriodoLaboralResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(PeriodoLaboralMapper.setEntityToDaoResponse).collect(Collectors.toList());
		});
		setEntityListToDaoReferenceList = (entityList -> {
			return (List<PeriodoLaboralResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(PeriodoLaboralMapper.setEntityToDaoReference).collect(Collectors.toList());
		});
	}
}
