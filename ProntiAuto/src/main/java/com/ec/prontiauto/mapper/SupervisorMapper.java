package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.SupervisorRequestDao;
import com.ec.prontiauto.dao.SupervisorResponseDao;
import com.ec.prontiauto.entidad.Agencia;
import com.ec.prontiauto.entidad.Supervisor;
import com.ec.prontiauto.entidad.Trabajador;

public class SupervisorMapper {
	public static Function<SupervisorRequestDao, Supervisor> setDaoRequestToEntity;
	public static Function<Supervisor, SupervisorResponseDao> setEntityToDaoResponse;
	public static Function<Supervisor, SupervisorResponseDao> setEntityToDaoReference;
	public static Function<List<Supervisor>, List<SupervisorResponseDao>> setEntityListToDaoResponseList;

	static {
		setDaoRequestToEntity = (daoRequest -> {
			Supervisor entity = new Supervisor();
			entity.setId(daoRequest.getId());
			entity.setSisHabilitado(daoRequest.getSisHabilitado());
			Agencia agencia = new Agencia();
			agencia.setId(daoRequest.getIdAgencia());
			entity.setIdAgencia(agencia);
			Trabajador trabajador = new Trabajador();
			trabajador.setId(daoRequest.getIdTrabajador());
			entity.setIdTrabajador(trabajador);
			return entity;
		});

		setEntityToDaoResponse = (entity -> {
			SupervisorResponseDao dao = new SupervisorResponseDao();
			dao.setId(entity.getId());
			dao.setSisHabilitado(entity.getSisHabilitado());
			dao.setSisActualizado(entity.getSisActualizado());
			dao.setSisCreado(entity.getSisCreado());
			dao.setSisArchivo(entity.getSisArchivo());
			dao.setSisImagen(entity.getSisImagen());

			if (entity.getOnlyChildrenData() == null) {
				dao.setIdTrabajador(
						TrabajadorMapper.setEntityToDaoReferenceUsario.apply(entity.getIdTrabajador()));
				dao.setIdAgencia(AgenciaMapper.setEntityToDaoReference.apply(entity.getIdAgencia()));
			}
			return dao;
		});
		setEntityToDaoReference = (entity -> {
			SupervisorResponseDao dao = new SupervisorResponseDao();
			dao.setId(entity.getId());
			dao.setSisHabilitado(entity.getSisHabilitado());
			dao.setSisActualizado(entity.getSisActualizado());
			dao.setSisCreado(entity.getSisCreado());
			dao.setSisArchivo(entity.getSisArchivo());
			dao.setSisImagen(entity.getSisImagen());

			// dao.setIdAgencia(AgenciaMapper.setEntityToDaoReference.apply(entity.getIdAgencia()));
			return dao;
		});

		setEntityListToDaoResponseList = (entityList -> {
			return (List<SupervisorResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(SupervisorMapper.setEntityToDaoResponse).collect(Collectors.toList());
		});
	}
}
