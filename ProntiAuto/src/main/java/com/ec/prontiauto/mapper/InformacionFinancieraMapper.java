package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.InformacionFinancieraRequestDao;
import com.ec.prontiauto.dao.InformacionFinancieraResponseDao;
import com.ec.prontiauto.entidad.InformacionFinanciera;
import com.ec.prontiauto.entidad.Trabajador;

public class InformacionFinancieraMapper {
	public static Function<InformacionFinancieraRequestDao, InformacionFinanciera> setDaoRequestToEntity;
	public static Function<InformacionFinanciera, InformacionFinancieraResponseDao> setEntityToDaoResponse;
	public static Function<List<InformacionFinanciera>, List<InformacionFinancieraResponseDao>> setEntityListToDaoResponseList;
	public static Function<InformacionFinanciera, InformacionFinancieraResponseDao> setEntityToDaoReference;
	public static Function<List<InformacionFinanciera>, List<InformacionFinancieraResponseDao>> setEntityListToDaoReferenceList;

	static {
		setDaoRequestToEntity = (daoRequest -> {
			InformacionFinanciera entity = new InformacionFinanciera();
			entity.setId(daoRequest.getId());
			entity.setFormaPago(daoRequest.getFormaPago());
			entity.setSisHabilitado(daoRequest.getSisHabilitado());
			Trabajador trabajador = new Trabajador();
			trabajador.setId(daoRequest.getIdTrabajador());
			entity.setIdTrabajador(trabajador);

			return entity;
		});
		setEntityToDaoResponse = (entity -> {
			InformacionFinancieraResponseDao dao = new InformacionFinancieraResponseDao();
			dao.setId(entity.getId());
			dao.setFormaPago(entity.getFormaPago());
			dao.setSisHabilitado(entity.getSisHabilitado());
			dao.setSisActualizado(entity.getSisActualizado());
			dao.setSisCreado(entity.getSisCreado());

			if (entity.getOnlyChildrenData() == null) {
				if (entity.getIdTrabajador() != null && entity.getIdTrabajador().getId() != null) {
					dao.setIdTrabajador(TrabajadorMapper.setEntityToDaoReference.apply(entity.getIdTrabajador()));
				}
				if (entity.getCuentaBancariaEmpresaCollection() != null) {
					dao.setCuentaBancariaEmpresaCollection(
							CuentaBancariaEmpresaMapper.setEntityListToDaoReferenceList
									.apply(entity.getCuentaBancariaEmpresaCollection()));
				}
			}

			return dao;
		});

		setEntityToDaoReference = (entity -> {
			InformacionFinancieraResponseDao dao = new InformacionFinancieraResponseDao();
			dao.setId(entity.getId());
			dao.setFormaPago(entity.getFormaPago());
			dao.setSisHabilitado(entity.getSisHabilitado());
			dao.setSisActualizado(entity.getSisActualizado());
			dao.setSisCreado(entity.getSisCreado());
			if (entity.getIdTrabajador() != null && entity.getIdTrabajador().getId() != null) {
				dao.setIdTrabajador(TrabajadorMapper.setEntityToDaoReference.apply(entity.getIdTrabajador()));
			}
			if (entity.getCuentaBancariaEmpresaCollection() != null) {
				dao.setCuentaBancariaEmpresaCollection(
						CuentaBancariaEmpresaMapper.setEntityListToDaoResponseList
								.apply(entity.getCuentaBancariaEmpresaCollection()));
			}
			return dao;
		});
		setEntityListToDaoResponseList = (entityList -> {
			return (List<InformacionFinancieraResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(InformacionFinancieraMapper.setEntityToDaoResponse).collect(Collectors.toList());
		});
		setEntityListToDaoReferenceList = (entityList -> {
			return (List<InformacionFinancieraResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(InformacionFinancieraMapper.setEntityToDaoReference).collect(Collectors.toList());
		});
	}
}
