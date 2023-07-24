package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.CargoVacacionRequestDao;
import com.ec.prontiauto.dao.CargoVacacionResponseDao;
import com.ec.prontiauto.entidad.CargoVacacion;
import com.ec.prontiauto.entidad.Trabajador;

public class CargoVacacionMapper {
	public static Function<CargoVacacionRequestDao, CargoVacacion> setDaoRequestToEntity;
	public static Function<CargoVacacion, CargoVacacionResponseDao> setEntityToDaoResponse;
	public static Function<List<CargoVacacion>, List<CargoVacacionResponseDao>> setEntityListToDaoResponseList;
	public static Function<List<CargoVacacion>, List<CargoVacacionResponseDao>> setEntityListToDaoReferenceList;
	public static Function<CargoVacacion, CargoVacacionResponseDao> setEntityToDaoReference;

	static {
		setDaoRequestToEntity = (daoRequest -> {
			CargoVacacion entity = new CargoVacacion();
			entity.setId(daoRequest.getId());
			entity.setFechaDesde(daoRequest.getFechaDesde());
			entity.setFechaHasta(daoRequest.getFechaHasta());
			entity.setDiasVacaciones(daoRequest.getDiasVacaciones());
			entity.setDiasAntiguedad(daoRequest.getDiasAntiguedad());
			entity.setDiasTeoricos(daoRequest.getDiasTeoricos());
			entity.setDiasTomados(daoRequest.getDiasTomados());
			entity.setDiasSaldo(daoRequest.getDiasSaldo());
			entity.setValorVacacion(daoRequest.getValorVacacion());
			entity.setValorDias(daoRequest.getValorDias());
			entity.setValorAntiguedad(daoRequest.getValorAntiguedad());
			entity.setValorTeorico(daoRequest.getValorTeorico());
			entity.setValorTomado(daoRequest.getValorTomado());
			entity.setValorSaldo(daoRequest.getValorSaldo());
			entity.setTotalIngresosAnio(daoRequest.getTotalIngresosAnio());
			entity.setNumAnioAcumulado(daoRequest.getNumAnioAcumulado());
			entity.setSisHabilitado(daoRequest.getSisHabilitado());

			Trabajador trabajador = new Trabajador();
			trabajador.setId(daoRequest.getIdTrabajador());
			entity.setIdTrabajador(trabajador);

			return entity;
		});

		setEntityToDaoResponse = (entity -> {
			CargoVacacionResponseDao dao = new CargoVacacionResponseDao();
			dao.setId(entity.getId());
			dao.setFechaDesde(entity.getFechaDesde());
			dao.setFechaHasta(entity.getFechaHasta());
			dao.setDiasVacaciones(entity.getDiasVacaciones());
			dao.setDiasAntiguedad(entity.getDiasAntiguedad());
			dao.setDiasTeoricos(entity.getDiasTeoricos());
			dao.setDiasTomados(entity.getDiasTomados());
			dao.setDiasSaldo(entity.getDiasSaldo());
			dao.setValorVacacion(entity.getValorVacacion());
			dao.setValorDias(entity.getValorDias());
			dao.setValorAntiguedad(entity.getValorAntiguedad());
			dao.setValorTeorico(entity.getValorTeorico());
			dao.setValorTomado(entity.getValorTomado());
			dao.setValorSaldo(entity.getValorSaldo());
			dao.setTotalIngresosAnio(entity.getTotalIngresosAnio());
			dao.setNumAnioAcumulado(entity.getNumAnioAcumulado());
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
			CargoVacacionResponseDao dao = new CargoVacacionResponseDao();
			dao.setId(entity.getId());
			dao.setFechaDesde(entity.getFechaDesde());
			dao.setFechaHasta(entity.getFechaHasta());
			dao.setDiasVacaciones(entity.getDiasVacaciones());
			dao.setDiasAntiguedad(entity.getDiasAntiguedad());
			dao.setDiasTeoricos(entity.getDiasTeoricos());
			dao.setDiasTomados(entity.getDiasTomados());
			dao.setDiasSaldo(entity.getDiasSaldo());
			dao.setValorVacacion(entity.getValorVacacion());
			dao.setValorDias(entity.getValorDias());
			dao.setValorAntiguedad(entity.getValorAntiguedad());
			dao.setValorTeorico(entity.getValorTeorico());
			dao.setValorTomado(entity.getValorTomado());
			dao.setValorSaldo(entity.getValorSaldo());
			dao.setTotalIngresosAnio(entity.getTotalIngresosAnio());
			dao.setNumAnioAcumulado(entity.getNumAnioAcumulado());
			dao.setSisHabilitado(entity.getSisHabilitado());
			dao.setSisActualizado(entity.getSisActualizado());
			dao.setSisCreado(entity.getSisCreado());
			return dao;
		});

		setEntityListToDaoResponseList = (entityList -> {
			return (List<CargoVacacionResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(CargoVacacionMapper.setEntityToDaoResponse).collect(Collectors.toList());
		});
		setEntityListToDaoReferenceList = (entityList -> {
			return (List<CargoVacacionResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(CargoVacacionMapper.setEntityToDaoReference).collect(Collectors.toList());
		});
	}

}
