package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.TransaccionAsientoContableRequestDao;
import com.ec.prontiauto.dao.TransaccionAsientoContableResponseDao;
import com.ec.prontiauto.entidad.AsientoContableCabecera;
import com.ec.prontiauto.entidad.CuentaContable;
import com.ec.prontiauto.entidad.TransaccionAsientoContable;

public class TransaccionAsientoContableMapper {
	public static Function<TransaccionAsientoContableRequestDao, TransaccionAsientoContable> setDaoRequestToEntity;
	public static Function<TransaccionAsientoContable, TransaccionAsientoContableResponseDao> setEntityToDaoResponse;
	public static Function<List<TransaccionAsientoContable>, List<TransaccionAsientoContableResponseDao>> setEntityListToDaoResponseList;
	public static Function<TransaccionAsientoContable, TransaccionAsientoContableResponseDao> setEntityToDaoReference;
	public static Function<List<TransaccionAsientoContable>, List<TransaccionAsientoContableResponseDao>> setEntityListToDaoReferenceList;

	static {
		setDaoRequestToEntity = (daoRequest -> {
			TransaccionAsientoContable entity = new TransaccionAsientoContable();
			entity.setId(daoRequest.getId());
			entity.setDetalle(daoRequest.getDetalle());
			entity.setValorDebito(daoRequest.getValorDebito());
			entity.setValorCredito(daoRequest.getValorCredito());
			entity.setSisHabilitado(daoRequest.getSisHabilitado());
			entity.setNumeroFactura(daoRequest.getNumeroFactura());

			if(daoRequest.getIdAsientoContableCabecera() != null){
				entity.setIdAsientoContableCabecera((AsientoContableCabecera) daoRequest.getIdAsientoContableCabecera());
			}else {
				AsientoContableCabecera asientoContableCabecera = new AsientoContableCabecera();
				entity.setIdAsientoContableCabecera(asientoContableCabecera);
			}

			if(daoRequest.getIdCuentaContable() != null){
				CuentaContable cuentaContable = new CuentaContable();
				cuentaContable.setId(daoRequest.getIdCuentaContable());
				entity.setIdCuentaContable(cuentaContable);
			}

			return entity;
		});
		setEntityToDaoResponse = (entity -> {
			TransaccionAsientoContableResponseDao dao = new TransaccionAsientoContableResponseDao();
			dao.setId(entity.getId());
			dao.setDetalle(entity.getDetalle());
			dao.setValorDebito(entity.getValorDebito());
			dao.setValorCredito(entity.getValorCredito());
			dao.setNumeroFactura(entity.getNumeroFactura());
			dao.setSisHabilitado(entity.getSisHabilitado());
			dao.setSisActualizado(entity.getSisActualizado());
			dao.setSisCreado(entity.getSisCreado());

			if (entity.getIdAsientoContableCabecera() != null
					&& entity.getIdAsientoContableCabecera().getId() != null) {
				dao.setIdAsientoContableCabecera(AsientoContableCabeceraMapper.setEntityToDaoReference
						.apply(entity.getIdAsientoContableCabecera()));
			}

			if (entity.getIdCuentaContable() != null
					&& entity.getIdCuentaContable().getId() != null) {
				dao.setIdCuentaContable(CuentaContableMapper.setEntityToDaoReference
						.apply(entity.getIdCuentaContable()));
			}

			return dao;
		});

		setEntityToDaoReference = (entity -> {
			TransaccionAsientoContableResponseDao dao = new TransaccionAsientoContableResponseDao();
			dao.setId(entity.getId());
			dao.setDetalle(entity.getDetalle());
			dao.setValorDebito(entity.getValorDebito());
			dao.setValorCredito(entity.getValorCredito());
			dao.setNumeroFactura(entity.getNumeroFactura());
			dao.setSisHabilitado(entity.getSisHabilitado());
			dao.setSisActualizado(entity.getSisActualizado());
			dao.setSisCreado(entity.getSisCreado());

			return dao;
		});
		setEntityListToDaoResponseList = (entityList -> {
			return (List<TransaccionAsientoContableResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(TransaccionAsientoContableMapper.setEntityToDaoResponse).collect(Collectors.toList());
		});
		setEntityListToDaoReferenceList = (entityList -> {
			return (List<TransaccionAsientoContableResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(TransaccionAsientoContableMapper.setEntityToDaoReference).collect(Collectors.toList());
		});
	}
}
