package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.PlanRequestDao;
import com.ec.prontiauto.dao.PlanResponseDao;
import com.ec.prontiauto.entidad.Plan;

public class PlanMapper {
	public static Function<PlanRequestDao, Plan> setDaoRequestToEntity;
	public static Function<Plan, PlanResponseDao> setEntityToDaoResponse;
	public static Function<List<Plan>, List<PlanResponseDao>> setEntityListToDaoResponseList;
	public static Function<List<Plan>, List<PlanResponseDao>> setEntityListToDaoReferenceList;
	public static Function<Plan, PlanResponseDao> setEntityToDaoReference;

	static {
		setDaoRequestToEntity = (daoRequest -> {
			Plan entity = new Plan();
			entity.setCuotaMes12(daoRequest.getCuotaMes12());
			entity.setCuotaMes24(daoRequest.getCuotaMes24());
			entity.setCuotaMes36(daoRequest.getCuotaMes36());
			entity.setCuotaMes48(daoRequest.getCuotaMes48());
			entity.setCuotaMes60(daoRequest.getCuotaMes60());
			entity.setCuotaMes72(daoRequest.getCuotaMes72());
			entity.setCuotaMes84(daoRequest.getCuotaMes84());
			entity.setModelo(daoRequest.getModelo());
			entity.setPrecio(daoRequest.getPrecio());
			entity.setInscripcion(daoRequest.getInscripcion());
			entity.setSisHabilitado(daoRequest.getSisHabilitado());
			return entity;
		});
		setEntityToDaoResponse = (entity -> {
			PlanResponseDao dao = new PlanResponseDao();
			dao.setId(entity.getId());
			dao.setCuotaMes12(entity.getCuotaMes12());
			dao.setCuotaMes24(entity.getCuotaMes24());
			dao.setCuotaMes36(entity.getCuotaMes36());
			dao.setCuotaMes48(entity.getCuotaMes48());
			dao.setCuotaMes60(entity.getCuotaMes60());
			dao.setCuotaMes72(entity.getCuotaMes72());
			dao.setCuotaMes84(entity.getCuotaMes84());
			dao.setModelo(entity.getModelo());
			dao.setPrecio(entity.getPrecio());
			dao.setInscripcion(entity.getInscripcion());
			if (entity.getHistoricoPlanContratoCollection() != null) {
				dao.setHistoricoPlanContratoCollection(HistoricoPlanContratoMapper.setEntityListToDaoResponseList
						.apply(entity.getHistoricoPlanContratoCollection()));
			}
			dao.setSisHabilitado(entity.getSisHabilitado());
			dao.setSisActualizado(entity.getSisActualizado());
			dao.setSisCreado(entity.getSisCreado());
			dao.setSisArchivo(entity.getSisArchivo());
			dao.setSisImagen(entity.getSisImagen());
			return dao;
		});

		setEntityToDaoReference = (entity -> {
			PlanResponseDao dao = new PlanResponseDao();
			dao.setId(entity.getId());
			dao.setCuotaMes12(entity.getCuotaMes12());
			dao.setCuotaMes24(entity.getCuotaMes24());
			dao.setCuotaMes36(entity.getCuotaMes36());
			dao.setCuotaMes48(entity.getCuotaMes48());
			dao.setCuotaMes60(entity.getCuotaMes60());
			dao.setCuotaMes72(entity.getCuotaMes72());
			dao.setCuotaMes84(entity.getCuotaMes84());
			dao.setModelo(entity.getModelo());
			dao.setPrecio(entity.getPrecio());
			dao.setInscripcion(entity.getInscripcion());
			dao.setSisHabilitado(entity.getSisHabilitado());
			dao.setSisActualizado(entity.getSisActualizado());
			dao.setSisCreado(entity.getSisCreado());
			dao.setSisArchivo(entity.getSisArchivo());
			dao.setSisImagen(entity.getSisImagen());
			return dao;
		});

		setEntityListToDaoResponseList = (entityList -> {
			return (List<PlanResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(PlanMapper.setEntityToDaoResponse).collect(Collectors.toList());
		});

		setEntityListToDaoReferenceList = (entityList -> {
			return (List<PlanResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(PlanMapper.setEntityToDaoReference).collect(Collectors.toList());
		});
	}
}
