package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.HistorialLaboralRequestDao;
import com.ec.prontiauto.dao.HistorialLaboralResponseDao;
import com.ec.prontiauto.entidad.Agencia;
import com.ec.prontiauto.entidad.Cargo;
import com.ec.prontiauto.entidad.DivisionAdministrativa;
import com.ec.prontiauto.entidad.HistorialLaboral;
import com.ec.prontiauto.entidad.Trabajador;

public class HistorialLaboralMapper {
	public static Function<HistorialLaboralRequestDao, HistorialLaboral> setDaoRequestToEntity;
	public static Function<HistorialLaboral, HistorialLaboralResponseDao> setEntityToDaoResponse;
	public static Function<List<HistorialLaboral>, List<HistorialLaboralResponseDao>> setEntityListToDaoResponseList;
	public static Function<List<HistorialLaboral>, List<HistorialLaboralResponseDao>> setEntityListToDaoReferenceList;
	public static Function<HistorialLaboral, HistorialLaboralResponseDao> setEntityToDaoReference;

	static {
		setDaoRequestToEntity = (daoRequest -> {
			HistorialLaboral entity = new HistorialLaboral();
			entity.setId(daoRequest.getId());
			entity.setTipoContrato(daoRequest.getTipoContrato());
			entity.setSueldo(daoRequest.getSueldo());
			entity.setFechaIngreso(daoRequest.getFechaIngreso());
			entity.setFechaFin(daoRequest.getFechaFin());
			entity.setDuracion(daoRequest.getDuracion());
			entity.setFueAscendido(daoRequest.getFueAscendido());
			entity.setCodigoSectorial(daoRequest.getCodigoSectorial());
			entity.setSisHabilitado(daoRequest.getSisHabilitado());
			Trabajador trabajador = new Trabajador();
			trabajador.setId(daoRequest.getIdTrabajador());
			entity.setIdTrabajador(trabajador);

			Agencia agencia = new Agencia();
			agencia.setId(daoRequest.getIdAgencia());
			entity.setIdAgencia(agencia);

			Cargo cargo = new Cargo();
			cargo.setId(daoRequest.getIdCargo());
			entity.setIdCargo(cargo);

			if (daoRequest.getIdDivisionAdministrativa() != null) {
				DivisionAdministrativa divisionAdministrativa = new DivisionAdministrativa();
				divisionAdministrativa.setId(daoRequest.getIdDivisionAdministrativa());
				entity.setIdDivisionAdministrativa(divisionAdministrativa);
			}

			return entity;
		});

		setEntityToDaoResponse = (entity -> {
			HistorialLaboralResponseDao dao = new HistorialLaboralResponseDao();
			dao.setId(entity.getId());
			dao.setTipoContrato(entity.getTipoContrato());
			dao.setSueldo(entity.getSueldo());
			dao.setFechaIngreso(entity.getFechaIngreso());
			dao.setFechaFin(entity.getFechaFin());
			dao.setDuracion(entity.getDuracion());
			dao.setFueAscendido(entity.getFueAscendido());
			dao.setCodigoSectorial(entity.getCodigoSectorial());
			dao.setSisHabilitado(entity.getSisHabilitado());
			dao.setSisActualizado(entity.getSisActualizado());
			dao.setSisCreado(entity.getSisCreado());

			if (entity.getOnlyChildrenData() == null) {
				if (entity.getIdTrabajador() != null && entity.getIdTrabajador().getId() != null) {
					dao.setIdTrabajador(TrabajadorMapper.setEntityToDaoReference.apply(entity.getIdTrabajador()));
				}
				if (entity.getIdAgencia() != null && entity.getIdAgencia().getId() != null) {
					dao.setIdAgencia(AgenciaMapper.setEntityToDaoReference.apply(entity.getIdAgencia()));
				}
				if (entity.getIdCargo() != null && entity.getIdCargo().getId() != null) {
					dao.setIdCargo(CargoMapper.setEntityToDaoReference.apply(entity.getIdCargo()));
				}
				if (entity.getIdDivisionAdministrativa() != null
						&& entity.getIdDivisionAdministrativa().getId() != null) {
					dao.setIdDivisionAdministrativa(DivisionAdministrativaMapper.setEntityToDaoReference
							.apply(entity.getIdDivisionAdministrativa()));
				}
				// Recursivo Hijo
				if (entity.getIdHistorialLaboral() != null && entity.getIdHistorialLaboral().getId() != null) {
					dao.setIdHistorialLaboral(
							HistorialLaboralMapper.setEntityToDaoReference.apply(entity.getIdHistorialLaboral()));
				}
				// Recursivo padre
				if (entity.getHistorialLaboralCollection() != null) {
					dao.setHistorialLaboralCollection(
							HistorialLaboralMapper.setEntityListToDaoReferenceList
									.apply(entity.getHistorialLaboralCollection()));
				}
				if (entity.getRolPagoCollection() != null) {
					dao.setRolPagoCollection(
							RolPagoMapper.setEntityListToDaoResponseList.apply(entity.getRolPagoCollection()));
				}
			}

			return dao;
		});

		setEntityToDaoReference = (entity -> {
			HistorialLaboralResponseDao dao = new HistorialLaboralResponseDao();
			dao.setId(entity.getId());
			dao.setTipoContrato(entity.getTipoContrato());
			dao.setSueldo(entity.getSueldo());
			dao.setFechaIngreso(entity.getFechaIngreso());
			dao.setFechaFin(entity.getFechaFin());
			dao.setDuracion(entity.getDuracion());
			dao.setFueAscendido(entity.getFueAscendido());
			dao.setCodigoSectorial(entity.getCodigoSectorial());
			dao.setSisHabilitado(entity.getSisHabilitado());
			dao.setSisActualizado(entity.getSisActualizado());
			dao.setSisCreado(entity.getSisCreado());

			if (entity.getIdTrabajador() != null && entity.getIdTrabajador().getId() != null) {
				dao.setIdTrabajador(TrabajadorMapper.setEntityToDaoReference.apply(entity.getIdTrabajador()));
			}

			if (entity.getIdAgencia() != null && entity.getIdAgencia().getId() != null) {
				dao.setIdAgencia(AgenciaMapper.setEntityToDaoReference.apply(entity.getIdAgencia()));
			}

			if (entity.getIdCargo() != null && entity.getIdCargo().getId() != null) {
				dao.setIdCargo(CargoMapper.setEntityToDaoReference.apply(entity.getIdCargo()));
			}

			return dao;
		});

		setEntityListToDaoResponseList = (entityList -> {
			return (List<HistorialLaboralResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(HistorialLaboralMapper.setEntityToDaoResponse).collect(Collectors.toList());
		});
		setEntityListToDaoReferenceList = (entityList -> {
			return (List<HistorialLaboralResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(HistorialLaboralMapper.setEntityToDaoReference).collect(Collectors.toList());
		});
	}
}
