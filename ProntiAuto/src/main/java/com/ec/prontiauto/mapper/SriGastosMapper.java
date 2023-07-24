package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.SriGastosRequestDao;
import com.ec.prontiauto.dao.SriGastosResponseDao;
import com.ec.prontiauto.entidad.SriGastos;
import com.ec.prontiauto.entidad.Trabajador;

public class SriGastosMapper {
	public static Function<SriGastosRequestDao, SriGastos> setDaoRequestToEntity;
	public static Function<SriGastos, SriGastosResponseDao> setEntityToDaoResponse;
	public static Function<List<SriGastos>, List<SriGastosResponseDao>> setEntityListToDaoResponseList;
	public static Function<List<SriGastos>, List<SriGastosResponseDao>> setEntityListToDaoReferenceList;
	public static Function<SriGastos, SriGastosResponseDao> setEntityToDaoReference;

	static {
		setDaoRequestToEntity = (daoRequest -> {
			SriGastos entity = new SriGastos();
			entity.setId(daoRequest.getId());
			entity.setSisHabilitado(daoRequest.getSisHabilitado());

			entity.setAnio(daoRequest.getAnio());
			entity.setGastoVivienda(daoRequest.getGastoVivienda());
			entity.setGastoEducacion(daoRequest.getGastoEducacion());
			entity.setGastoSalud(daoRequest.getGastoSalud());
			entity.setGastoVestido(daoRequest.getGastoVestido());
			entity.setGastoAlimento(daoRequest.getGastoAlimento());
			entity.setGastoTurismo(daoRequest.getGastoTurismo());
			entity.setGastoDiscapacidad(daoRequest.getGastoDiscapacidad());
			entity.setGastoTerceraEdad(daoRequest.getGastoTerceraEdad());
			entity.setTotalGastos(daoRequest.getTotalGastos());

			Trabajador trabajador = new Trabajador();
			trabajador.setId(daoRequest.getIdTrabajador());
			entity.setIdTrabajador(trabajador);

			return entity;
		});

		setEntityToDaoResponse = (entity -> {
			SriGastosResponseDao dao = new SriGastosResponseDao();
			dao.setId(entity.getId());
			dao.setSisHabilitado(entity.getSisHabilitado());

			dao.setAnio(entity.getAnio());
			dao.setGastoVivienda(entity.getGastoVivienda());
			dao.setGastoEducacion(entity.getGastoEducacion());
			dao.setGastoSalud(entity.getGastoSalud());
			dao.setGastoVestido(entity.getGastoVestido());
			dao.setGastoAlimento(entity.getGastoAlimento());
			dao.setGastoTurismo(entity.getGastoTurismo());
			dao.setGastoDiscapacidad(entity.getGastoDiscapacidad());
			dao.setGastoTerceraEdad(entity.getGastoTerceraEdad());
			dao.setTotalGastos(entity.getTotalGastos());

			if (entity.getOnlyChildrenData() == null) {
				if (entity.getIdTrabajador() != null && entity.getIdTrabajador().getId() != null) {
					dao.setIdTrabajador(TrabajadorMapper.setEntityToDaoReference.apply(entity.getIdTrabajador()));
				}
			}

			return dao;

		});

		setEntityToDaoReference = (entity -> {
			SriGastosResponseDao dao = new SriGastosResponseDao();
			dao.setId(entity.getId());
			dao.setSisHabilitado(entity.getSisHabilitado());

			dao.setAnio(entity.getAnio());
			dao.setGastoVivienda(entity.getGastoVivienda());
			dao.setGastoEducacion(entity.getGastoEducacion());
			dao.setGastoSalud(entity.getGastoSalud());
			dao.setGastoVestido(entity.getGastoVestido());
			dao.setGastoAlimento(entity.getGastoAlimento());
			dao.setGastoTurismo(entity.getGastoTurismo());
			dao.setGastoDiscapacidad(entity.getGastoDiscapacidad());
			dao.setGastoTerceraEdad(entity.getGastoTerceraEdad());
			dao.setTotalGastos(entity.getTotalGastos());

			return dao;
		});

		setEntityListToDaoResponseList = (entityList -> {
			return (List<SriGastosResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(SriGastosMapper.setEntityToDaoResponse).collect(Collectors.toList());
		});

		setEntityListToDaoReferenceList = (entityList -> {
			return (List<SriGastosResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(SriGastosMapper.setEntityToDaoReference).collect(Collectors.toList());
		});
	}

}