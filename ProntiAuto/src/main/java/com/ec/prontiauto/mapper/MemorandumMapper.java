package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.MemorandumRequestDao;
import com.ec.prontiauto.dao.MemorandumResponseDao;
import com.ec.prontiauto.entidad.Memorandum;
import com.ec.prontiauto.entidad.Trabajador;

public class MemorandumMapper {
	public static Function<MemorandumRequestDao, Memorandum> setDaoRequestToEntity;
	public static Function<Memorandum, MemorandumResponseDao> setEntityToDaoResponse;
	public static Function<List<Memorandum>, List<MemorandumResponseDao>> setEntityListToDaoResponseList;
	public static Function<Memorandum, MemorandumResponseDao> setEntityToDaoReference;
	public static Function<List<Memorandum>, List<MemorandumResponseDao>> setEntityListToDaoReferenceList;

	static {
		setDaoRequestToEntity = (daoRequest -> {
			Memorandum entity = new Memorandum();
			entity.setId(daoRequest.getId());
			entity.setFecha(daoRequest.getFecha());
			entity.setMotivo(daoRequest.getMotivo());
			entity.setObservaciones(daoRequest.getObservaciones());
			entity.setSisHabilitado(daoRequest.getSisHabilitado());
			Trabajador trabajador = new Trabajador();
			trabajador.setId(daoRequest.getIdTrabajador());
			entity.setIdTrabajador(trabajador);
			return entity;
		});
		setEntityToDaoResponse = (entity -> {
			MemorandumResponseDao dao = new MemorandumResponseDao();
			dao.setId(entity.getId());
			dao.setFecha(entity.getFecha());
			dao.setMotivo(entity.getMotivo());
			dao.setObservaciones(entity.getObservaciones());
			dao.setSisHabilitado(entity.getSisHabilitado());
			dao.setSisActualizado(entity.getSisActualizado());
			dao.setSisCreado(entity.getSisCreado());

			if (entity.getOnlyChildrenData() == null) {
				if (entity.getIdTrabajador() != null && entity.getIdTrabajador().getId() != null) {
					dao.setIdTrabajador(TrabajadorMapper.setEntityToDaoReference.apply(entity.getIdTrabajador()));
				}
			}

			return dao;
		});

		setEntityToDaoReference = (entity -> {
			MemorandumResponseDao dao = new MemorandumResponseDao();
			dao.setId(entity.getId());
			dao.setFecha(entity.getFecha());
			dao.setMotivo(entity.getMotivo());
			dao.setObservaciones(entity.getObservaciones());
			dao.setSisHabilitado(entity.getSisHabilitado());
			dao.setSisActualizado(entity.getSisActualizado());
			dao.setSisCreado(entity.getSisCreado());
			return dao;
		});
		setEntityListToDaoResponseList = (entityList -> {
			return (List<MemorandumResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(MemorandumMapper.setEntityToDaoResponse).collect(Collectors.toList());
		});
		setEntityListToDaoReferenceList = (entityList -> {
			return (List<MemorandumResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(MemorandumMapper.setEntityToDaoReference).collect(Collectors.toList());
		});
	}
}
