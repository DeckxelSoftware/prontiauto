package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.VendedorRequestDao;
import com.ec.prontiauto.dao.VendedorResponseDao;
import com.ec.prontiauto.entidad.Agencia;
import com.ec.prontiauto.entidad.Trabajador;
import com.ec.prontiauto.entidad.Vendedor;

public class VendedorMapper {
	public static Function<VendedorRequestDao, Vendedor> setDaoRequestToEntity;
	public static Function<Vendedor, VendedorResponseDao> setEntityToDaoResponse;
	public static Function<Vendedor, VendedorResponseDao> setEntityToDaoReference;
	public static Function<List<Vendedor>, List<VendedorResponseDao>> setEntityListToDaoResponseList;

	static {
		setDaoRequestToEntity = (daoRequest -> {
			Vendedor entity = new Vendedor();
			entity.setId(daoRequest.getId());
			entity.setSisHabilitado(daoRequest.getSisHabilitado());
			Agencia agencia = new Agencia();
			agencia.setId(daoRequest.getIdAgencia());
			entity.setIdAgencia(agencia);
			Trabajador trabajador = new Trabajador();
			trabajador.setId(daoRequest.getIdTrabajador());
			entity.setIdTrabajador(trabajador);
			return entity;
		});

		setEntityToDaoResponse = (entity -> {
			VendedorResponseDao dao = new VendedorResponseDao();
			dao.setId(entity.getId());
			dao.setSisHabilitado(entity.getSisHabilitado());
			dao.setSisActualizado(entity.getSisActualizado());
			dao.setSisCreado(entity.getSisCreado());
			dao.setSisArchivo(entity.getSisArchivo());
			dao.setSisImagen(entity.getSisImagen());

			if (entity.getOnlyChildrenData() == null) {
				dao.setIdTrabajador(
						TrabajadorMapper.setEntityToDaoReferenceUsario.apply(entity.getIdTrabajador()));

				dao.setIdAgencia(AgenciaMapper.setEntityToDaoReference.apply(entity.getIdAgencia()));
			}
			return dao;
		});
		setEntityToDaoReference = (entity -> {
			VendedorResponseDao dao = new VendedorResponseDao();
			dao.setId(entity.getId());
			dao.setSisHabilitado(entity.getSisHabilitado());
			dao.setSisActualizado(entity.getSisActualizado());
			dao.setSisCreado(entity.getSisCreado());
			dao.setSisArchivo(entity.getSisArchivo());
			dao.setSisImagen(entity.getSisImagen());

			// dao.setIdAgencia(AgenciaMapper.setEntityToDaoReference.apply(entity.getIdAgencia()));
			return dao;
		});

		setEntityListToDaoResponseList = (entityList -> {
			return (List<VendedorResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(VendedorMapper.setEntityToDaoResponse).collect(Collectors.toList());
		});

	}

}
