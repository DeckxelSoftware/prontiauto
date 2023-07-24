package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.ChequeraRequestDao;
import com.ec.prontiauto.dao.ChequeraResponseDao;
import com.ec.prontiauto.entidad.Chequera;
import com.ec.prontiauto.entidad.CuentaBancariaEmpresa;

public class ChequeraMapper {
	public static Function<ChequeraRequestDao, Chequera> setDaoRequestToEntity;
	public static Function<Chequera, ChequeraResponseDao> setEntityToDaoResponse;
	public static Function<List<Chequera>, List<ChequeraResponseDao>> setEntityListToDaoResponseList;
	public static Function<Chequera, ChequeraResponseDao> setEntityToDaoReference;
	public static Function<List<Chequera>, List<ChequeraResponseDao>> setEntityListToDaoReferenceList;

	static {
		setDaoRequestToEntity = (daoRequest -> {
			Chequera entity = new Chequera();
			entity.setId(daoRequest.getId());
			entity.setFechaEmision(daoRequest.getFechaEmision());
			entity.setSerieDesde(daoRequest.getSerieDesde());
			CuentaBancariaEmpresa cuentaBancariaEmpresa = new CuentaBancariaEmpresa();
			cuentaBancariaEmpresa.setId(daoRequest.getIdCuentaBancariaEmpresa());
			entity.setIdCuentaBancariaEmpresa(cuentaBancariaEmpresa);
			entity.setSerieHasta(daoRequest.getSerieHasta());
			entity.setSisHabilitado(daoRequest.getSisHabilitado());

			return entity;
		});
		setEntityToDaoResponse = (entity -> {
			ChequeraResponseDao dao = new ChequeraResponseDao();
			dao.setId(entity.getId());
			dao.setFechaEmision(entity.getFechaEmision());
			dao.setSerieDesde(entity.getSerieDesde());
			dao.setSerieHasta(entity.getSerieHasta());
			dao.setSisHabilitado(entity.getSisHabilitado());
			dao.setSisActualizado(entity.getSisActualizado());
			dao.setSisCreado(entity.getSisCreado());
			if (entity.getIdCuentaBancariaEmpresa() != null && entity.getIdCuentaBancariaEmpresa().getId() != null) {
				dao.setIdCuentaBancariaEmpresa(
						CuentaBancariaEmpresaMapper.setEntityToDaoReference.apply(entity.getIdCuentaBancariaEmpresa()));
			}
			return dao;
		});

		setEntityToDaoReference = (entity -> {
			ChequeraResponseDao dao = new ChequeraResponseDao();
			dao.setId(entity.getId());
			dao.setFechaEmision(entity.getFechaEmision());
			dao.setSerieDesde(entity.getSerieDesde());
			dao.setSerieHasta(entity.getSerieHasta());
			dao.setSisHabilitado(entity.getSisHabilitado());
			dao.setSisActualizado(entity.getSisActualizado());
			dao.setSisCreado(entity.getSisCreado());
			if (entity.getIdCuentaBancariaEmpresa() != null && entity.getIdCuentaBancariaEmpresa().getId() != null) {
				dao.setIdCuentaBancariaEmpresa(
						CuentaBancariaEmpresaMapper.setEntityToDaoReference.apply(entity.getIdCuentaBancariaEmpresa()));
			}
			return dao;
		});

		setEntityListToDaoResponseList = (entityList -> {
			return (List<ChequeraResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(ChequeraMapper.setEntityToDaoResponse).collect(Collectors.toList());
		});
		
		setEntityListToDaoReferenceList = (entityList -> {
			return (List<ChequeraResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(ChequeraMapper.setEntityToDaoReference).collect(Collectors.toList());
		});
	}
}
