package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.CuentaContableRequestDao;
import com.ec.prontiauto.dao.CuentaContableResponseDao;
import com.ec.prontiauto.entidad.CuentaContable;
import com.ec.prontiauto.entidad.PeriodoContable;

public class CuentaContableMapper {
	public static Function<CuentaContableRequestDao, CuentaContable> setDaoRequestToEntity;
	public static Function<CuentaContable, CuentaContableResponseDao> setEntityToDaoResponse;
	public static Function<List<CuentaContable>, List<CuentaContableResponseDao>> setEntityListToDaoResponseList;
	public static Function<List<CuentaContable>, List<CuentaContableResponseDao>> setEntityListToDaoReferenceList;
	public static Function<CuentaContable, CuentaContableResponseDao> setEntityToDaoReference;

	static {
		setDaoRequestToEntity = (daoRequest -> {
			CuentaContable entity = new CuentaContable();
			entity.setId(daoRequest.getId());
			entity.setNombre(daoRequest.getNombre());
			entity.setIdentificador(daoRequest.getIdentificador());
			entity.setNivel(daoRequest.getNivel());
			entity.setIdNivel1(daoRequest.getIdNivel1());
			entity.setIdNivel2(daoRequest.getIdNivel2());
			entity.setIdNivel3(daoRequest.getIdNivel3());
			entity.setIdNivel4(daoRequest.getIdNivel4());
			entity.setIdNivel5(daoRequest.getIdNivel5());
			entity.setTipoCuenta(daoRequest.getTipoCuenta());
			entity.setMovimiento(daoRequest.getMovimiento());
			entity.setAnteriorDebito(daoRequest.getAnteriorDebito());
			entity.setAnteriorCredito(daoRequest.getAnteriorCredito());
			entity.setAnteriorSaldo(daoRequest.getAnteriorSaldo());
			entity.setEneroDebito(daoRequest.getEneroDebito());
			entity.setEneroCredito(daoRequest.getEneroCredito());
			entity.setEneroSaldo(daoRequest.getEneroSaldo());
			entity.setFebreroDebito(daoRequest.getFebreroDebito());
			entity.setFebreroCredito(daoRequest.getFebreroCredito());
			entity.setFebreroSaldo(daoRequest.getFebreroSaldo());
			entity.setMarzoDebito(daoRequest.getMarzoDebito());
			entity.setMarzoCredito(daoRequest.getMarzoCredito());
			entity.setMarzoSaldo(daoRequest.getMarzoSaldo());
			entity.setAbrilDebito(daoRequest.getAbrilDebito());
			entity.setAbrilCredito(daoRequest.getAbrilCredito());
			entity.setAbrilSaldo(daoRequest.getAbrilSaldo());
			entity.setMayoDebito(daoRequest.getMayoDebito());
			entity.setMayoCredito(daoRequest.getMayoCredito());
			entity.setMayoSaldo(daoRequest.getMayoSaldo());
			entity.setJunioDebito(daoRequest.getJunioDebito());
			entity.setJunioCredito(daoRequest.getJunioCredito());
			entity.setJunioSaldo(daoRequest.getJunioSaldo());
			entity.setJulioDebito(daoRequest.getJulioDebito());
			entity.setJulioCredito(daoRequest.getJulioCredito());
			entity.setJulioSaldo(daoRequest.getJulioSaldo());
			entity.setAgostoDebito(daoRequest.getAgostoDebito());
			entity.setAgostoCredito(daoRequest.getAgostoCredito());
			entity.setAgostoSaldo(daoRequest.getAgostoSaldo());
			entity.setSeptiembreDebito(daoRequest.getSeptiembreDebito());
			entity.setSeptiembreCredito(daoRequest.getSeptiembreCredito());
			entity.setSeptiembreSaldo(daoRequest.getSeptiembreSaldo());
			entity.setOctubreDebito(daoRequest.getOctubreDebito());
			entity.setOctubreCredito(daoRequest.getOctubreCredito());
			entity.setOctubreSaldo(daoRequest.getOctubreSaldo());
			entity.setNoviembreDebito(daoRequest.getNoviembreDebito());
			entity.setNoviembreCredito(daoRequest.getNoviembreCredito());
			entity.setNoviembreSaldo(daoRequest.getNoviembreSaldo());
			entity.setDiciembreDebito(daoRequest.getDiciembreDebito());
			entity.setDiciembreCredito(daoRequest.getDiciembreCredito());
			entity.setDiciembreSaldo(daoRequest.getDiciembreSaldo());
			entity.setActualDebito(daoRequest.getActualDebito());
			entity.setActualCredito(daoRequest.getActualCredito());
			entity.setActualSaldo(daoRequest.getActualSaldo());
			entity.setSisHabilitado(daoRequest.getSisHabilitado());

			if(daoRequest.getIdPeriodoContable() != null){
				PeriodoContable periodoContableo = new PeriodoContable();
				periodoContableo.setId(daoRequest.getIdPeriodoContable());
				entity.setIdPeriodoContable(periodoContableo);
			}
			return entity;
		});
		setEntityToDaoResponse = (entity -> {
			CuentaContableResponseDao dao = new CuentaContableResponseDao();
			dao.setId(entity.getId());
			dao.setNombre(entity.getNombre());
			dao.setIdentificador(entity.getIdentificador());
			dao.setNivel(entity.getNivel());
			dao.setIdNivel1(entity.getIdNivel1());
			dao.setIdNivel2(entity.getIdNivel2());
			dao.setIdNivel3(entity.getIdNivel3());
			dao.setIdNivel4(entity.getIdNivel4());
			dao.setIdNivel5(entity.getIdNivel5());
			dao.setTipoCuenta(entity.getTipoCuenta());
			dao.setMovimiento(entity.getMovimiento());
			dao.setAnteriorDebito(entity.getAnteriorDebito());
			dao.setAnteriorCredito(entity.getAnteriorCredito());
			dao.setAnteriorSaldo(entity.getAnteriorSaldo());
			dao.setEneroDebito(entity.getEneroDebito());
			dao.setEneroCredito(entity.getEneroCredito());
			dao.setEneroSaldo(entity.getEneroSaldo());
			dao.setFebreroDebito(entity.getFebreroDebito());
			dao.setFebreroCredito(entity.getFebreroCredito());
			dao.setFebreroSaldo(entity.getFebreroSaldo());
			dao.setMarzoDebito(entity.getMarzoDebito());
			dao.setMarzoCredito(entity.getMarzoCredito());
			dao.setMarzoSaldo(entity.getMarzoSaldo());
			dao.setAbrilDebito(entity.getAbrilDebito());
			dao.setAbrilCredito(entity.getAbrilCredito());
			dao.setAbrilSaldo(entity.getAbrilSaldo());
			dao.setMayoDebito(entity.getMayoDebito());
			dao.setMayoCredito(entity.getMayoCredito());
			dao.setMayoSaldo(entity.getMayoSaldo());
			dao.setJunioDebito(entity.getJunioDebito());
			dao.setJunioCredito(entity.getJunioCredito());
			dao.setJunioSaldo(entity.getJunioSaldo());
			dao.setJulioDebito(entity.getJulioDebito());
			dao.setJulioCredito(entity.getJulioCredito());
			dao.setJulioSaldo(entity.getJulioSaldo());
			dao.setAgostoDebito(entity.getAgostoDebito());
			dao.setAgostoCredito(entity.getAgostoCredito());
			dao.setAgostoSaldo(entity.getAgostoSaldo());
			dao.setSeptiembreDebito(entity.getSeptiembreDebito());
			dao.setSeptiembreCredito(entity.getSeptiembreCredito());
			dao.setSeptiembreSaldo(entity.getSeptiembreSaldo());
			dao.setOctubreDebito(entity.getOctubreDebito());
			dao.setOctubreCredito(entity.getOctubreCredito());
			dao.setOctubreSaldo(entity.getOctubreSaldo());
			dao.setNoviembreDebito(entity.getNoviembreDebito());
			dao.setNoviembreCredito(entity.getNoviembreCredito());
			dao.setNoviembreSaldo(entity.getNoviembreSaldo());
			dao.setDiciembreDebito(entity.getDiciembreDebito());
			dao.setDiciembreCredito(entity.getDiciembreCredito());
			dao.setDiciembreSaldo(entity.getDiciembreSaldo());
			dao.setActualDebito(entity.getActualDebito());
			dao.setActualCredito(entity.getActualCredito());
			dao.setActualSaldo(entity.getActualSaldo());

			if (entity.getOnlyChildrenData() == null) {
				if (entity.getIdPeriodoContable() != null && entity.getIdPeriodoContable().getId() != null) {
					dao.setIdPeriodoContable(
							PeriodoContableMapper.setEntityToDaoReference.apply(entity.getIdPeriodoContable()));
				}
				// Recursivo Hijo
				if (entity.getIdCuentaContable() != null && entity.getIdCuentaContable().getId() != null) {
					dao.setIdCuentaContable(
							CuentaContableMapper.setEntityToDaoReference.apply(entity.getIdCuentaContable()));
				}
				// Recursivo padre
				if (entity.getCuentaContableCollection() != null) {
					dao.setCuentaContableCollection(
							CuentaContableMapper.setEntityListToDaoReferenceList
									.apply(entity.getCuentaContableCollection()));
				}

				if (entity.getItemCobroPagoCollection() != null) {
					dao.setItemCobroPagoCollection(
							ItemCobroPagoMapper.setEntityListToDaoResponseList
									.apply(entity.getItemCobroPagoCollection()));
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
			}

			dao.setSisHabilitado(entity.getSisHabilitado());
			dao.setSisActualizado(entity.getSisActualizado());
			dao.setSisCreado(entity.getSisCreado());
			return dao;
		});

		setEntityToDaoReference = (entity -> {
			CuentaContableResponseDao dao = new CuentaContableResponseDao();
			dao.setId(entity.getId());
			dao.setNombre(entity.getNombre());
			dao.setIdentificador(entity.getIdentificador());
			dao.setNivel(entity.getNivel());
			dao.setIdNivel1(entity.getIdNivel1());
			dao.setIdNivel2(entity.getIdNivel2());
			dao.setIdNivel3(entity.getIdNivel3());
			dao.setIdNivel4(entity.getIdNivel4());
			dao.setIdNivel5(entity.getIdNivel5());
			dao.setTipoCuenta(entity.getTipoCuenta());
			dao.setMovimiento(entity.getMovimiento());
			dao.setAnteriorDebito(entity.getAnteriorDebito());
			dao.setAnteriorCredito(entity.getAnteriorCredito());
			dao.setAnteriorSaldo(entity.getAnteriorSaldo());
			dao.setEneroDebito(entity.getEneroDebito());
			dao.setEneroCredito(entity.getEneroCredito());
			dao.setEneroSaldo(entity.getEneroSaldo());
			dao.setFebreroDebito(entity.getFebreroDebito());
			dao.setFebreroCredito(entity.getFebreroCredito());
			dao.setFebreroSaldo(entity.getFebreroSaldo());
			dao.setMarzoDebito(entity.getMarzoDebito());
			dao.setMarzoCredito(entity.getMarzoCredito());
			dao.setMarzoSaldo(entity.getMarzoSaldo());
			dao.setAbrilDebito(entity.getAbrilDebito());
			dao.setAbrilCredito(entity.getAbrilCredito());
			dao.setAbrilSaldo(entity.getAbrilSaldo());
			dao.setMayoDebito(entity.getMayoDebito());
			dao.setMayoCredito(entity.getMayoCredito());
			dao.setMayoSaldo(entity.getMayoSaldo());
			dao.setJunioDebito(entity.getJunioDebito());
			dao.setJunioCredito(entity.getJunioCredito());
			dao.setJunioSaldo(entity.getJunioSaldo());
			dao.setJulioDebito(entity.getJulioDebito());
			dao.setJulioCredito(entity.getJulioCredito());
			dao.setJulioSaldo(entity.getJulioSaldo());
			dao.setAgostoDebito(entity.getAgostoDebito());
			dao.setAgostoCredito(entity.getAgostoCredito());
			dao.setAgostoSaldo(entity.getAgostoSaldo());
			dao.setSeptiembreDebito(entity.getSeptiembreDebito());
			dao.setSeptiembreCredito(entity.getSeptiembreCredito());
			dao.setSeptiembreSaldo(entity.getSeptiembreSaldo());
			dao.setOctubreDebito(entity.getOctubreDebito());
			dao.setOctubreCredito(entity.getOctubreCredito());
			dao.setOctubreSaldo(entity.getOctubreSaldo());
			dao.setNoviembreDebito(entity.getNoviembreDebito());
			dao.setNoviembreCredito(entity.getNoviembreCredito());
			dao.setNoviembreSaldo(entity.getNoviembreSaldo());
			dao.setDiciembreDebito(entity.getDiciembreDebito());
			dao.setDiciembreCredito(entity.getDiciembreCredito());
			dao.setDiciembreSaldo(entity.getDiciembreSaldo());
			dao.setActualDebito(entity.getActualDebito());
			dao.setActualCredito(entity.getActualCredito());
			dao.setActualSaldo(entity.getActualSaldo());
			dao.setSisHabilitado(entity.getSisHabilitado());
			dao.setSisActualizado(entity.getSisActualizado());
			dao.setSisCreado(entity.getSisCreado());

			if (entity.getOnlyChildrenData() == null) {
				if (entity.getIdPeriodoContable() != null && entity.getIdPeriodoContable().getId() != null) {
					dao.setIdPeriodoContable(
							PeriodoContableMapper.setEntityToDaoReference.apply(entity.getIdPeriodoContable()));
				}
			}

			return dao;
		});
		setEntityListToDaoResponseList = (entityList -> {
			return (List<CuentaContableResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(CuentaContableMapper.setEntityToDaoResponse).collect(Collectors.toList());
		});
		setEntityListToDaoReferenceList = (entityList -> {
			return (List<CuentaContableResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(CuentaContableMapper.setEntityToDaoReference).collect(Collectors.toList());
		});
	}
}
