package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.AsientoContableCabeceraRequestDao;
import com.ec.prontiauto.dao.AsientoContableCabeceraResponseDao;
import com.ec.prontiauto.entidad.AsientoContableCabecera;
import com.ec.prontiauto.entidad.Cheque;
import com.ec.prontiauto.entidad.CuentaContable;


public class AsientoContableCabeceraMapper {

	public static Function<AsientoContableCabeceraRequestDao, AsientoContableCabecera> setDaoRequestToEntity;
	public static Function<AsientoContableCabecera, AsientoContableCabeceraResponseDao> setEntityToDaoResponse;
	public static Function<AsientoContableCabecera, AsientoContableCabeceraResponseDao> setEntityToDaoReference;
	public static Function<List<AsientoContableCabecera>, List<AsientoContableCabeceraResponseDao>> setEntityListToDaoResponseList;
	public static Function<List<AsientoContableCabecera>, List<AsientoContableCabeceraResponseDao>> setEntityListToDaoReferenceList;

	static {
		setDaoRequestToEntity = (daoRequest -> {
			AsientoContableCabecera entity = new AsientoContableCabecera();
			entity.setId(daoRequest.getId());
			entity.setFecha(daoRequest.getFecha());
			entity.setTipoTransaccion(daoRequest.getTipoTransaccion());
			entity.setTipoAsientoContable(daoRequest.getTipoAsientoContable());
			entity.setCodigoReferencialAsientoContable(daoRequest.getCodigoReferencialAsientoContable());
			entity.setMesPeriodo(daoRequest.getMesPeriodo());
			entity.setAnio(daoRequest.getAnio());
			entity.setTotalDebito(daoRequest.getTotalDebito());
			entity.setTotalCredito(daoRequest.getTotalCredito());
			entity.setTotalSaldoActualFecha(daoRequest.getTotalSaldoActualFecha());
			entity.setAsientoCerrado(daoRequest.getAsientoCerrado());
			entity.setSerie(daoRequest.getSerie());
			entity.setDescripcion(daoRequest.getDescripcion());
			entity.setSisHabilitado(daoRequest.getSisHabilitado());
			entity.setBeneficiario(daoRequest.getBeneficiario());

//			SubgrupoContable subgrupoContable = new SubgrupoContable();
//			subgrupoContable.setId(daoRequest.getIdSugrupoContable());
//			entity.setIdSubgrupoContable(subgrupoContable);

			CuentaContable cuentaContable = new CuentaContable();
			cuentaContable.setId((int) daoRequest.getIdCuentaContable());
			entity.setIdCuentaContable(cuentaContable);

			if (daoRequest.getIdCheque() != null) {
				Cheque cheque = new Cheque();
				cheque.setId(daoRequest.getIdCheque());
				entity.setIdCheque(cheque);
			}

			return entity;
		});

		setEntityToDaoResponse = ((entity) -> {
			AsientoContableCabeceraResponseDao dao = new AsientoContableCabeceraResponseDao();
			dao.setId(entity.getId());
			dao.setFecha(entity.getFecha());
			dao.setTipoTransaccion(entity.getTipoTransaccion());
			dao.setTipoAsientoContable(entity.getTipoAsientoContable());
			dao.setCodigoReferencialAsientoContable(entity.getCodigoReferencialAsientoContable());
			dao.setMesPeriodo(entity.getMesPeriodo());
			dao.setAnio(entity.getAnio());
			dao.setTotalDebito(entity.getTotalDebito());
			dao.setTotalCredito(entity.getTotalCredito());
			dao.setTotalSaldoActualFecha(entity.getTotalSaldoActualFecha());
			dao.setAsientoCerrado(entity.getAsientoCerrado());
			dao.setSerie(entity.getSerie());
			dao.setDescripcion(entity.getDescripcion());
			dao.setSisHabilitado(entity.getSisHabilitado());
			dao.setSisActualizado(entity.getSisActualizado());
			dao.setSisCreado(entity.getSisCreado());
			dao.setBeneficiario(entity.getBeneficiario());

//			if (entity.getIdSubgrupoContable() != null && entity.getIdSubgrupoContable().getId() != null) {
//				dao.setIdSubgrupoContable(
//						SubgrupoContableMapper.setEntityToDaoReference.apply(entity.getIdSubgrupoContable()));
//			}

			if (entity.getIdCheque() != null && entity.getIdCheque().getId() != null) {
				dao.setIdCheque(ChequeMapper.setEntityToDaoReference.apply(entity.getIdCheque()));
			}

			if (entity.getIdCuentaContable() != null && entity.getIdCuentaContable().getId() != null) {
				entity.getIdCuentaContable().setOnlyChildrenData(true);
				dao.setIdCuentaContable(
						(CuentaContableMapper.setEntityToDaoReference.apply(entity.getIdCuentaContable())));
			}

			if(entity.getTransaccionAsientoContableCollection() != null) {
				entity.getTransaccionAsientoContableCollection().forEach(transaccionAsientoContable -> {
					transaccionAsientoContable.setOnlyChildrenData(true);
				});
				dao.setTransaccionAsientoContableCollection(
						TransaccionAsientoContableMapper.setEntityListToDaoResponseList.apply(
								entity.getTransaccionAsientoContableCollection()
						)
				);
			}

			return dao;
		});

		setEntityToDaoReference = (entity -> {
			AsientoContableCabeceraResponseDao dao = new AsientoContableCabeceraResponseDao();
			dao.setId(entity.getId());
			dao.setFecha(entity.getFecha());
			dao.setTipoTransaccion(entity.getTipoTransaccion());
			dao.setTipoAsientoContable(entity.getTipoAsientoContable());
			dao.setCodigoReferencialAsientoContable(entity.getCodigoReferencialAsientoContable());
			dao.setMesPeriodo(entity.getMesPeriodo());
			dao.setAnio(entity.getAnio());
			dao.setTotalDebito(entity.getTotalDebito());
			dao.setTotalCredito(entity.getTotalCredito());
			dao.setTotalSaldoActualFecha(entity.getTotalSaldoActualFecha());
			dao.setAsientoCerrado(entity.getAsientoCerrado());
			dao.setSerie(entity.getSerie());
			dao.setDescripcion(entity.getDescripcion());
			dao.setSisHabilitado(entity.getSisHabilitado());
			dao.setSisActualizado(entity.getSisActualizado());
			dao.setSisCreado(entity.getSisCreado());
			dao.setSisArchivo(entity.getSisArchivo());
			dao.setSisImagen(entity.getSisImagen());
			dao.setBeneficiario(entity.getBeneficiario());

			if (entity.getTransaccionAsientoContableCollection() != null) {
				dao.setTransaccionAsientoContableCollection(
						TransaccionAsientoContableMapper.setEntityListToDaoReferenceList
								.apply(entity.getTransaccionAsientoContableCollection()));
			}
			if (entity.getAsientoContableDetAdicionalCollection() != null) {
				dao.setAsientoContableDetAdicionalCollection(
						AsientoContableDetAdicionalMapper.setEntityListToDaoReferenceList
								.apply(entity.getAsientoContableDetAdicionalCollection()));
			}

			return dao;
		});

		setEntityListToDaoResponseList = (entityList -> {
			return (List<AsientoContableCabeceraResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(AsientoContableCabeceraMapper.setEntityToDaoResponse).collect(Collectors.toList());
		});
		setEntityListToDaoReferenceList = (entityList -> {
			return (List<AsientoContableCabeceraResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(AsientoContableCabeceraMapper.setEntityToDaoReference).collect(Collectors.toList());
		});
	}
}
