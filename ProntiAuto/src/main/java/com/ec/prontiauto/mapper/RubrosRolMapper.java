package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.RubrosRolRequestDao;
import com.ec.prontiauto.dao.RubrosRolResponseDao;
import com.ec.prontiauto.entidad.RubrosRol;

public class RubrosRolMapper {
	public static Function<RubrosRolRequestDao, RubrosRol> setDaoRequestToEntity;
	public static Function<RubrosRol, RubrosRolResponseDao> setEntityToDaoResponse;
	public static Function<List<RubrosRol>, List<RubrosRolResponseDao>> setEntityListToDaoResponseList;
	public static Function<RubrosRol, RubrosRolResponseDao> setEntityToDaoReference;
	public static Function<List<RubrosRol>, List<RubrosRolResponseDao>> setEntityListToDaoReferenceList;

	static {
		setDaoRequestToEntity = (daoRequest -> {
			RubrosRol entity = new RubrosRol();
			entity.setId(daoRequest.getId());
			entity.setCodigoAuxiliar(daoRequest.getCodigoAuxiliar());
			entity.setNombre(daoRequest.getNombre());
			entity.setNombreAuxiliarUno(daoRequest.getNombreAuxiliarUno());
			entity.setNombreAuxiliarDos(daoRequest.getNombreAuxiliarDos());
			entity.setUnidad(daoRequest.getUnidad());
			entity.setCalculaIess(daoRequest.getCalculaIess());
			entity.setCalculaRenta(daoRequest.getCalculaRenta());
			entity.setCalculaDecTercero(daoRequest.getCalculaDecTercero());
			entity.setCalculaDecCuarto(daoRequest.getCalculaDecCuarto());
			entity.setCalculaFReserva(daoRequest.getCalculaFReserva());
			entity.setCalculaVacaciones(daoRequest.getCalculaVacaciones());
			entity.setSeSuma(daoRequest.getSeSuma());
			entity.setSisHabilitado(daoRequest.getSisHabilitado());
			return entity;
		});
		setEntityToDaoResponse = (entity -> {
			RubrosRolResponseDao dao = new RubrosRolResponseDao();
			dao.setId(entity.getId());
			dao.setCodigoAuxiliar(entity.getCodigoAuxiliar());
			dao.setNombre(entity.getNombre());
			dao.setNombreAuxiliarUno(entity.getNombreAuxiliarUno());
			dao.setNombreAuxiliarDos(entity.getNombreAuxiliarDos());
			dao.setUnidad(entity.getUnidad());
			dao.setCalculaIess(entity.getCalculaIess());
			dao.setCalculaRenta(entity.getCalculaRenta());
			dao.setCalculaDecTercero(entity.getCalculaDecTercero());
			dao.setCalculaDecCuarto(entity.getCalculaDecCuarto());
			dao.setCalculaFReserva(entity.getCalculaFReserva());
			dao.setCalculaVacaciones(entity.getCalculaVacaciones());
			dao.setSeSuma(entity.getSeSuma());
			dao.setSisHabilitado(entity.getSisHabilitado());
			dao.setSisActualizado(entity.getSisActualizado());
			dao.setSisCreado(entity.getSisCreado());
			return dao;
		});

		setEntityToDaoReference = (entity -> {
			RubrosRolResponseDao dao = new RubrosRolResponseDao();
			dao.setId(entity.getId());
			dao.setCodigoAuxiliar(entity.getCodigoAuxiliar());
			dao.setNombre(entity.getNombre());
			dao.setNombreAuxiliarUno(entity.getNombreAuxiliarUno());
			dao.setNombreAuxiliarDos(entity.getNombreAuxiliarDos());
			dao.setUnidad(entity.getUnidad());
			dao.setCalculaIess(entity.getCalculaIess());
			dao.setCalculaRenta(entity.getCalculaRenta());
			dao.setCalculaDecTercero(entity.getCalculaDecTercero());
			dao.setCalculaDecCuarto(entity.getCalculaDecCuarto());
			dao.setCalculaFReserva(entity.getCalculaFReserva());
			dao.setCalculaVacaciones(entity.getCalculaVacaciones());
			dao.setSeSuma(entity.getSeSuma());
			dao.setSisHabilitado(entity.getSisHabilitado());
			dao.setSisActualizado(entity.getSisActualizado());
			dao.setSisCreado(entity.getSisCreado());
			return dao;
		});
		setEntityListToDaoResponseList = (entityList -> {
			return (List<RubrosRolResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(RubrosRolMapper.setEntityToDaoResponse).collect(Collectors.toList());
		});
		setEntityListToDaoReferenceList = (entityList -> {
			return (List<RubrosRolResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(RubrosRolMapper.setEntityToDaoReference).collect(Collectors.toList());
		});
	}
}
