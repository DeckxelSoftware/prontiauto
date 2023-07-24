package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.PrestamoRequestDao;
import com.ec.prontiauto.dao.PrestamoResponseDao;
import com.ec.prontiauto.entidad.Prestamo;
import com.ec.prontiauto.entidad.Trabajador;

public class PrestamoMapper {
	public static Function<PrestamoRequestDao, Prestamo> setDaoRequestToEntity;
	public static Function<Prestamo, PrestamoResponseDao> setEntityToDaoResponse;
	public static Function<List<Prestamo>, List<PrestamoResponseDao>> setEntityListToDaoResponseList;
	public static Function<Prestamo, PrestamoResponseDao> setEntityToDaoReference;
	public static Function<List<Prestamo>, List<PrestamoResponseDao>> setEntityListToDaoReferenceList;

	static {
		setDaoRequestToEntity = (daoRequest -> {
			Prestamo entity = new Prestamo();
			entity.setId(daoRequest.getId());
			entity.setTipoPrestamo(daoRequest.getTipoPrestamo());
			entity.setFechaPrestamo(daoRequest.getFechaPrestamo());
			entity.setComprobanteEgreso(daoRequest.getComprobanteEgreso());
			entity.setValor(daoRequest.getValor());
			entity.setCuotas(daoRequest.getCuotas());
			entity.setTasaInteres(daoRequest.getTasaInteres());
			entity.setConcepto(daoRequest.getConcepto());
			entity.setEstado(daoRequest.getEstado());
			entity.setModalidadDescuento(daoRequest.getModalidadDescuento());
			entity.setTotalPagado(daoRequest.getTotalPagado());
			entity.setTotalSaldo(daoRequest.getTotalSaldo());
			entity.setSisHabilitado(daoRequest.getSisHabilitado());
			if (daoRequest.getIdTrabajador() != null) {
				Trabajador trabajador = new Trabajador();
				trabajador.setId(daoRequest.getIdTrabajador());
				entity.setIdTrabajador(trabajador);
			}
			entity.setNombreApellidoResponsable(daoRequest.getNombreApellidoResponsable());
			entity.setEstadoSolicitud(daoRequest.getEstadoSolicitud());
			return entity;
		});
		setEntityToDaoResponse = (entity -> {
			PrestamoResponseDao dao = new PrestamoResponseDao();
			dao.setId(entity.getId());
			dao.setTipoPrestamo(entity.getTipoPrestamo());
			dao.setFechaPrestamo(entity.getFechaPrestamo());
			dao.setComprobanteEgreso(entity.getComprobanteEgreso());
			dao.setValor(entity.getValor());
			dao.setCuotas(entity.getCuotas());
			dao.setTasaInteres(entity.getTasaInteres());
			dao.setConcepto(entity.getConcepto());
			dao.setEstado(entity.getEstado());
			dao.setModalidadDescuento(entity.getModalidadDescuento());
			dao.setTotalPagado(entity.getTotalPagado());
			dao.setTotalSaldo(entity.getTotalSaldo());
			dao.setSisHabilitado(entity.getSisHabilitado());
			dao.setSisActualizado(entity.getSisActualizado());
			dao.setSisCreado(entity.getSisCreado());
			dao.setEstadoSolicitud(entity.getEstadoSolicitud());

			if (entity.getOnlyChildrenData() == null) {

				if (entity.getIdTrabajador() != null && entity.getIdTrabajador().getId() != null) {
					dao.setIdTrabajador(TrabajadorMapper.setEntityToDaoReference.apply(entity.getIdTrabajador()));
				}
				if (entity.getAbonoPrestamoCollection() != null) {
					dao.setAbonoPrestamoCollection(
							AbonoPrestamoMapper.setEntityListToDaoReferenceList
									.apply(entity.getAbonoPrestamoCollection()));
				}

			}

			return dao;
		});

		setEntityToDaoReference = (entity -> {
			PrestamoResponseDao dao = new PrestamoResponseDao();
			dao.setId(entity.getId());
			dao.setTipoPrestamo(entity.getTipoPrestamo());
			dao.setFechaPrestamo(entity.getFechaPrestamo());
			dao.setComprobanteEgreso(entity.getComprobanteEgreso());
			dao.setValor(entity.getValor());
			dao.setCuotas(entity.getCuotas());
			dao.setTasaInteres(entity.getTasaInteres());
			dao.setConcepto(entity.getConcepto());
			dao.setEstado(entity.getEstado());
			dao.setModalidadDescuento(entity.getModalidadDescuento());
			dao.setTotalPagado(entity.getTotalPagado());
			dao.setTotalSaldo(entity.getTotalSaldo());
			dao.setSisHabilitado(entity.getSisHabilitado());
			dao.setSisActualizado(entity.getSisActualizado());
			dao.setSisCreado(entity.getSisCreado());
			dao.setNombreApellidoResponsable(entity.getNombreApellidoResponsable());
			dao.setEstadoSolicitud(entity.getEstadoSolicitud());
			if (entity.getIdTrabajador() != null && entity.getIdTrabajador().getId() != null) {
				dao.setIdTrabajador(TrabajadorMapper.setEntityToDaoReference.apply(entity.getIdTrabajador()));
			}
			return dao;
		});
		setEntityListToDaoResponseList = (entityList -> {
			return (List<PrestamoResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(PrestamoMapper.setEntityToDaoResponse).collect(Collectors.toList());
		});
		setEntityListToDaoReferenceList = (entityList -> {
			return (List<PrestamoResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(PrestamoMapper.setEntityToDaoReference).collect(Collectors.toList());
		});
	}
}
