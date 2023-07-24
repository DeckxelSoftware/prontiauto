package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.ChequeRequestDao;
import com.ec.prontiauto.dao.ChequeResponseDao;
import com.ec.prontiauto.entidad.Cheque;
import com.ec.prontiauto.entidad.Chequera;

public class ChequeMapper {
	public static Function<ChequeRequestDao, Cheque> setDaoRequestToEntity;
	public static Function<Cheque, ChequeResponseDao> setEntityToDaoResponse;
	public static Function<List<Cheque>, List<ChequeResponseDao>> setEntityListToDaoResponseList;
	public static Function<Cheque, ChequeResponseDao> setEntityToDaoReference;
	public static Function<List<Cheque>, List<ChequeResponseDao>> setEntityListToDaoReferenceList;

	static {
		setDaoRequestToEntity = (daoRequest -> {
			Cheque entity = new Cheque();
			entity.setId(daoRequest.getId());
			entity.setNumeroCheque(daoRequest.getNumeroCheque());
			entity.setEstadoCheque(daoRequest.getEstadoCheque());
			entity.setSisHabilitado(daoRequest.getSisHabilitado());
			Chequera chequera = new Chequera();
			chequera.setId(daoRequest.getIdChequera());
			entity.setIdChequera(chequera);

			return entity;
		});
		setEntityToDaoResponse = (entity -> {
			ChequeResponseDao dao = new ChequeResponseDao();
			dao.setId(entity.getId());
			dao.setNumeroCheque(entity.getNumeroCheque());
			dao.setEstadoCheque(entity.getEstadoCheque());
			dao.setSisHabilitado(entity.getSisHabilitado());
			dao.setSisActualizado(entity.getSisActualizado());
			dao.setSisCreado(entity.getSisCreado());

			if (entity.getIdChequera() != null && entity.getIdChequera().getId() != null) {
				dao.setIdChequera(ChequeraMapper.setEntityToDaoReference.apply(entity.getIdChequera()));
			}
			if (entity.getAsientoContableCabeceraCollection() != null) {
				dao.setAsientoContableCabeceraCollection(
						AsientoContableCabeceraMapper.setEntityListToDaoResponseList
								.apply(entity.getAsientoContableCabeceraCollection()));
			}
			return dao;
		});

		setEntityToDaoReference = (entity -> {
			ChequeResponseDao dao = new ChequeResponseDao();
			dao.setId(entity.getId());
			dao.setNumeroCheque(entity.getNumeroCheque());
			dao.setEstadoCheque(entity.getEstadoCheque());
			dao.setSisHabilitado(entity.getSisHabilitado());
			dao.setSisActualizado(entity.getSisActualizado());
			dao.setSisCreado(entity.getSisCreado());
			if (entity.getAsientoContableCabeceraCollection() != null) {
				dao.setAsientoContableCabeceraCollection(
						AsientoContableCabeceraMapper.setEntityListToDaoReferenceList
								.apply(entity.getAsientoContableCabeceraCollection()));
			}
			return dao;
		});
		setEntityListToDaoResponseList = (entityList -> {
			return (List<ChequeResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(ChequeMapper.setEntityToDaoResponse).collect(Collectors.toList());
		});
		setEntityListToDaoReferenceList = (entityList -> {
			return (List<ChequeResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(ChequeMapper.setEntityToDaoReference).collect(Collectors.toList());
		});
	}
}
