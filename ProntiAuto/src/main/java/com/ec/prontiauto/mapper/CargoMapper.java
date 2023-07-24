package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.CargoRequestDao;
import com.ec.prontiauto.dao.CargoResponseDao;
import com.ec.prontiauto.entidad.Area;
import com.ec.prontiauto.entidad.Cargo;

public class CargoMapper {
	public static Function<CargoRequestDao, Cargo> setDaoRequestToEntity;
	public static Function<Cargo, CargoResponseDao> setEntityToDaoResponse;
	public static Function<List<Cargo>, List<CargoResponseDao>> setEntityListToDaoResponseList;
	public static Function<List<Cargo>, List<CargoResponseDao>> setEntityListToDaoReferenceList;
	public static Function<Cargo, CargoResponseDao> setEntityToDaoReference;

	static {
		setDaoRequestToEntity = (daoRequest -> {
			Cargo entity = new Cargo();
			entity.setId(daoRequest.getId());
			entity.setNombre(daoRequest.getNombre());
			entity.setSueldo(daoRequest.getSueldo());
			entity.setSisHabilitado(daoRequest.getSisHabilitado());
			Area area = new Area();
			area.setId(daoRequest.getIdArea());
            entity.setIdArea(area);
			return entity;
		});
		setEntityToDaoResponse = (entity -> {
			CargoResponseDao dao = new CargoResponseDao();
			dao.setId(entity.getId());
			dao.setNombre(entity.getNombre());
			dao.setSueldo(entity.getSueldo());
			if (entity.getHistorialLaboralCollection() != null) {
				dao.setHistorialLaboralCollection(
						HistorialLaboralMapper.setEntityListToDaoReferenceList.apply(entity.getHistorialLaboralCollection()));
			}
			dao.setSisHabilitado(entity.getSisHabilitado());
			dao.setSisActualizado(entity.getSisActualizado());
			dao.setSisCreado(entity.getSisCreado());
			dao.setIdArea(AreaMapper.setEntityToDaoReference.apply(entity.getIdArea()));
			return dao;
		});

		setEntityToDaoReference = (entity -> {
			CargoResponseDao dao = new CargoResponseDao();
			dao.setId(entity.getId());
			dao.setNombre(entity.getNombre());
			dao.setSueldo(entity.getSueldo());
			dao.setSisHabilitado(entity.getSisHabilitado());
			dao.setSisActualizado(entity.getSisActualizado());
			dao.setSisCreado(entity.getSisCreado());
			return dao;
		});
		setEntityListToDaoResponseList = (entityList -> {
			return (List<CargoResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(CargoMapper.setEntityToDaoResponse).collect(Collectors.toList());
		});
		setEntityListToDaoReferenceList = (entityList -> {
			return (List<CargoResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(CargoMapper.setEntityToDaoReference).collect(Collectors.toList());
		});
	}
}
