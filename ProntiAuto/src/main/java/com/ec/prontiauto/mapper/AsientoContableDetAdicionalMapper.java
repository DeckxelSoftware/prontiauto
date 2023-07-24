package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.AsientoContableDetAdicionalRequestDao;
import com.ec.prontiauto.dao.AsientoContableDetAdicionalResponseDao;
import com.ec.prontiauto.entidad.AsientoContableCabecera;
import com.ec.prontiauto.entidad.AsientoContableDetAdicional;

public class AsientoContableDetAdicionalMapper {
	public static Function<AsientoContableDetAdicionalRequestDao, AsientoContableDetAdicional> setDaoRequestToEntity;
	public static Function<AsientoContableDetAdicional, AsientoContableDetAdicionalResponseDao> setEntityToDaoResponse;
	public static Function<List<AsientoContableDetAdicional>, List<AsientoContableDetAdicionalResponseDao>> setEntityListToDaoResponseList;
	public static Function<AsientoContableDetAdicional, AsientoContableDetAdicionalResponseDao> setEntityToDaoReference;
	public static Function<List<AsientoContableDetAdicional>, List<AsientoContableDetAdicionalResponseDao>> setEntityListToDaoReferenceList;

	static {
		setDaoRequestToEntity = (daoRequest -> {
			AsientoContableDetAdicional entity = new AsientoContableDetAdicional();
			entity.setId(daoRequest.getId());
			entity.setLlave(daoRequest.getLlave());
			entity.setValor(daoRequest.getValor());
			entity.setSisHabilitado(daoRequest.getSisHabilitado());
			if(daoRequest.getIdAsientoContableCabecera() != null) {
				AsientoContableCabecera asientoContableCabecera = new AsientoContableCabecera();
				asientoContableCabecera.setId(daoRequest.getIdAsientoContableCabecera());
				entity.setIdAsientoContableCabecera(asientoContableCabecera);	
			}
			
			return entity;
		});
		setEntityToDaoResponse = (entity -> {
			AsientoContableDetAdicionalResponseDao dao = new AsientoContableDetAdicionalResponseDao();
			dao.setId(entity.getId());
			dao.setLlave(entity.getLlave());
			dao.setValor(entity.getValor());
			dao.setSisHabilitado(entity.getSisHabilitado());
			dao.setSisActualizado(entity.getSisActualizado());
			dao.setSisCreado(entity.getSisCreado());
			if (entity.getIdAsientoContableCabecera() != null
					&& entity.getIdAsientoContableCabecera().getId() != null) {
				dao.setIdAsientoContableCabecera(AsientoContableCabeceraMapper.setEntityToDaoReference
						.apply(entity.getIdAsientoContableCabecera()));
			}
			return dao;
		});

		setEntityToDaoReference = (entity -> {
			AsientoContableDetAdicionalResponseDao dao = new AsientoContableDetAdicionalResponseDao();
			dao.setId(entity.getId());
			dao.setLlave(entity.getLlave());
			dao.setValor(entity.getValor());
			dao.setSisHabilitado(entity.getSisHabilitado());
			dao.setSisActualizado(entity.getSisActualizado());
			dao.setSisCreado(entity.getSisCreado());
			
			return dao;
		});
		setEntityListToDaoResponseList = (entityList -> {
			return (List<AsientoContableDetAdicionalResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(AsientoContableDetAdicionalMapper.setEntityToDaoResponse).collect(Collectors.toList());
		});
		setEntityListToDaoReferenceList = (entityList -> {
			return (List<AsientoContableDetAdicionalResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(AsientoContableDetAdicionalMapper.setEntityToDaoReference).collect(Collectors.toList());
		});
	}
}
