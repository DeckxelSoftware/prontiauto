package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.PeriodoContableRequestDao;
import com.ec.prontiauto.dao.PeriodoContableResponseDao;
import com.ec.prontiauto.entidad.PeriodoContable;

public class PeriodoContableMapper {
	public static Function<PeriodoContableRequestDao, PeriodoContable> setDaoRequestToEntity;
	public static Function<PeriodoContable, PeriodoContableResponseDao> setEntityToDaoResponse;
	public static Function<List<PeriodoContable>, List<PeriodoContableResponseDao>> setEntityListToDaoResponseList;
	public static Function<PeriodoContable, PeriodoContableResponseDao> setEntityToDaoReference;

	static {
		setDaoRequestToEntity = (daoRequest -> {
			PeriodoContable entity = new PeriodoContable();
			entity.setId(daoRequest.getId());
			entity.setFechaInicio(daoRequest.getFechaInicio());
			entity.setFechaFin(daoRequest.getFechaFin());
			entity.setAnio(daoRequest.getAnio());
			entity.setEsPeriodoActual(daoRequest.getEsPeriodoActual());
			entity.setSisHabilitado(daoRequest.getSisHabilitado());
			return entity;
		});
		setEntityToDaoResponse = (entity -> {
			PeriodoContableResponseDao dao = new PeriodoContableResponseDao();
			dao.setId(entity.getId());
			dao.setFechaInicio(entity.getFechaInicio());
			dao.setFechaFin(entity.getFechaFin());
			dao.setAnio(entity.getAnio());
			dao.setEsPeriodoActual(entity.getEsPeriodoActual());
			if (entity.getCuentaContableCollection() != null) {
                dao.setCuentcontableCollection(
                        CuentaContableMapper.setEntityListToDaoResponseList.apply(entity.getCuentaContableCollection()));
            }
			dao.setSisHabilitado(entity.getSisHabilitado());
			dao.setSisActualizado(entity.getSisActualizado());
			dao.setSisCreado(entity.getSisCreado());
			return dao;
		});

		setEntityToDaoReference = (entity -> {
			PeriodoContableResponseDao dao = new PeriodoContableResponseDao();
			dao.setId(entity.getId());
			dao.setFechaInicio(entity.getFechaInicio());
			dao.setFechaFin(entity.getFechaFin());
			dao.setAnio(entity.getAnio());
			dao.setEsPeriodoActual(entity.getEsPeriodoActual());
			dao.setSisHabilitado(entity.getSisHabilitado());
			dao.setSisActualizado(entity.getSisActualizado());
			dao.setSisCreado(entity.getSisCreado());
			return dao;
		});
		setEntityListToDaoResponseList = (entityList -> {
			return (List<PeriodoContableResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(PeriodoContableMapper.setEntityToDaoResponse).collect(Collectors.toList());
		});
	}
}
