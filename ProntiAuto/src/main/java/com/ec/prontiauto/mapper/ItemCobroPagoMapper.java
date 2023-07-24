package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.ItemCobroPagoRequestDao;
import com.ec.prontiauto.dao.ItemCobroPagoResponseDao;
import com.ec.prontiauto.entidad.CuentaContable;
import com.ec.prontiauto.entidad.ItemCobroPago;

public class ItemCobroPagoMapper {
	public static Function<ItemCobroPagoRequestDao, ItemCobroPago> setDaoRequestToEntity;
	public static Function<ItemCobroPago, ItemCobroPagoResponseDao> setEntityToDaoResponse;
	public static Function<List<ItemCobroPago>, List<ItemCobroPagoResponseDao>> setEntityListToDaoResponseList;
	public static Function<List<ItemCobroPago>, List<ItemCobroPagoResponseDao>> setEntityListToDaoReferenceList;
	public static Function<ItemCobroPago, ItemCobroPagoResponseDao> setEntityToDaoReference;

	static {
		setDaoRequestToEntity = (daoRequest -> {
			ItemCobroPago entity = new ItemCobroPago();
			entity.setId(daoRequest.getId());
			entity.setSisHabilitado(daoRequest.getSisHabilitado());

            entity.setNombreItem(daoRequest.getNombreItem());
            entity.setNombreCuenta(daoRequest.getNombreCuenta());

            CuentaContable cuenta = new CuentaContable();
            cuenta.setId(daoRequest.getIdCuentaContable());
            entity.setIdCuentaContable(cuenta);

			

			return entity;
		});
		
		setEntityToDaoResponse = (entity -> {
            ItemCobroPagoResponseDao dao = new ItemCobroPagoResponseDao();
			dao.setId(entity.getId());
			dao.setSisHabilitado(entity.getSisHabilitado());

            dao.setNombreItem(entity.getNombreItem());
            dao.setNombreCuenta(entity.getNombreCuenta());

            if (entity.getIdCuentaContable() != null && entity.getIdCuentaContable().getId() != null) {
				dao.setIdCuentaContable(CuentaContableMapper.setEntityToDaoReference.apply(entity.getIdCuentaContable()));
			}
			return dao;

		});

		setEntityToDaoReference = (entity -> {
            ItemCobroPagoResponseDao dao = new ItemCobroPagoResponseDao();
			dao.setId(entity.getId());
			dao.setSisHabilitado(entity.getSisHabilitado());

            dao.setNombreItem(entity.getNombreItem());
            dao.setNombreCuenta(entity.getNombreCuenta());
			return dao;
		});

		setEntityListToDaoResponseList = (entityList -> {
			return (List<ItemCobroPagoResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(ItemCobroPagoMapper.setEntityToDaoResponse).collect(Collectors.toList());
		});

		setEntityListToDaoReferenceList = (entityList -> {
			return (List<ItemCobroPagoResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(ItemCobroPagoMapper.setEntityToDaoReference).collect(Collectors.toList());
		});
	}

}