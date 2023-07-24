package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.RegistroVacacionRequestDao;
import com.ec.prontiauto.dao.RegistroVacacionResponseDao;
import com.ec.prontiauto.entidad.CargoVacacion;
import com.ec.prontiauto.entidad.RegistroVacacion;

public class RegistroVacacionMapper {
	public static Function<RegistroVacacionRequestDao, RegistroVacacion> setDaoRequestToEntity;
	public static Function<RegistroVacacion, RegistroVacacionResponseDao> setEntityToDaoResponse;
	public static Function<List<RegistroVacacion>, List<RegistroVacacionResponseDao>> setEntityListToDaoResponseList;
	public static Function<List<RegistroVacacion>, List<RegistroVacacionResponseDao>> setEntityListToDaoReferenceList;
	public static Function<RegistroVacacion, RegistroVacacionResponseDao> setEntityToDaoReference;

	static {
		setDaoRequestToEntity = (daoRequest -> {
			RegistroVacacion entity = new RegistroVacacion();
			entity.setId(daoRequest.getId());
			entity.setFechaDesde(daoRequest.getFechaDesde());
			entity.setFechaHasta(daoRequest.getFechaHasta());
			entity.setDiasTomados(daoRequest.getDiasTomados());
			entity.setValorTomado(daoRequest.getValorTomado());
			entity.setEstaPagado(daoRequest.getEstaPagado());
			entity.setFechaPago(daoRequest.getFechaPago());
			entity.setComprobantePago(daoRequest.getComprobantePago());
			entity.setValorPagado(daoRequest.getValorPagado());
			entity.setNombreApellidoResponsable(daoRequest.getNombreApellidoResponsable());
			entity.setSisHabilitado(daoRequest.getSisHabilitado());
			CargoVacacion cargoVacacion = new CargoVacacion();
			cargoVacacion.setId(daoRequest.getIdCargoVacacion());
			entity.setIdCargoVacacion(cargoVacacion);

			return entity;
		});

		setEntityToDaoResponse = (entity -> {
			RegistroVacacionResponseDao dao = new RegistroVacacionResponseDao();
			dao.setId(entity.getId());
			dao.setFechaDesde(entity.getFechaDesde());
			dao.setFechaHasta(entity.getFechaHasta());
			dao.setDiasTomados(entity.getDiasTomados());
			dao.setValorTomado(entity.getValorTomado());
			dao.setEstaPagado(entity.getEstaPagado());
			dao.setFechaPago(entity.getFechaPago());
			dao.setComprobantePago(entity.getComprobantePago());
			dao.setValorPagado(entity.getValorPagado());
			dao.setNombreApellidoResponsable(entity.getNombreApellidoResponsable());
			dao.setSisHabilitado(entity.getSisHabilitado());
			dao.setSisActualizado(entity.getSisActualizado());
			dao.setSisCreado(entity.getSisCreado());
			if (entity.getIdCargoVacacion() != null && entity.getIdCargoVacacion().getId() != null) {
				dao.setIdCargoVacacion(CargoVacacionMapper.setEntityToDaoReference.apply(entity.getIdCargoVacacion()));
			}
			return dao;
		});

		setEntityToDaoReference = (entity -> {
			RegistroVacacionResponseDao dao = new RegistroVacacionResponseDao();
			dao.setId(entity.getId());
			dao.setFechaDesde(entity.getFechaDesde());
			dao.setFechaHasta(entity.getFechaHasta());
			dao.setDiasTomados(entity.getDiasTomados());
			dao.setValorTomado(entity.getValorTomado());
			dao.setEstaPagado(entity.getEstaPagado());
			dao.setFechaPago(entity.getFechaPago());
			dao.setComprobantePago(entity.getComprobantePago());
			dao.setValorPagado(entity.getValorPagado());
			dao.setNombreApellidoResponsable(entity.getNombreApellidoResponsable());
			dao.setSisHabilitado(entity.getSisHabilitado());
			dao.setSisActualizado(entity.getSisActualizado());
			dao.setSisCreado(entity.getSisCreado());
			return dao;
		});

		setEntityListToDaoResponseList = (entityList -> {
			return (List<RegistroVacacionResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(RegistroVacacionMapper.setEntityToDaoResponse).collect(Collectors.toList());
		});
		setEntityListToDaoReferenceList = (entityList -> {
			return (List<RegistroVacacionResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(RegistroVacacionMapper.setEntityToDaoReference).collect(Collectors.toList());
		});
	}
}
