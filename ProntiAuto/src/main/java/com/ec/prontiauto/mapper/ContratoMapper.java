package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.ContratoRequestDao;
import com.ec.prontiauto.dao.ContratoResponseDao;
import com.ec.prontiauto.entidad.ClienteEnGrupo;
import com.ec.prontiauto.entidad.Contrato;
import com.ec.prontiauto.entidad.Vendedor;

public class ContratoMapper {

	public static Function<ContratoRequestDao, Contrato> setDaoRequestToEntity;
	public static Function<Contrato, ContratoResponseDao> setEntityToDaoResponse;
	public static Function<List<Contrato>, List<ContratoResponseDao>> setEntityListToDaoResponseList;
	public static Function<List<Contrato>, List<ContratoResponseDao>> setEntityListToDaoReferenceList;
	public static Function<Contrato, ContratoResponseDao> setEntityToDaoReference;

	static {
		setDaoRequestToEntity = (daoRequest -> {
			Contrato entity = new Contrato();
			entity.setId(daoRequest.getId());
			entity.setNumeroDeContrato(daoRequest.getNumeroDeContrato());
			entity.setFechaInicio(daoRequest.getFechaInicio());
			entity.setFechaFin(daoRequest.getFechaFin());
			entity.setDsctoInscripcion(daoRequest.getDsctoInscripcion());
			entity.setDsctoPrimeraCuota(daoRequest.getDsctoPrimeraCuota());
			entity.setObservacion(daoRequest.getObservacion());
			entity.setFechaIniciaCobro(daoRequest.getFechaIniciaCobro());
			entity.setEstado(daoRequest.getEstado());
			entity.setPlazoMesSeleccionado(daoRequest.getPlazoMesSeleccionado());
			entity.setVersion(daoRequest.getVersion());
			Vendedor vendedor = new Vendedor();
			vendedor.setId(daoRequest.getIdVendedor());
			entity.setIdVendedor(vendedor);
			entity.setSisHabilitado(daoRequest.getSisHabilitado());
			ClienteEnGrupo clienteEnGrupo = new ClienteEnGrupo();
			clienteEnGrupo.setId(daoRequest.getIdClienteEnGrupo());
			entity.setIdClienteEnGrupo(clienteEnGrupo);
			entity.setCuotaActual(daoRequest.getCuotaActual());
			entity.setTipoDocumentoIdentidad(daoRequest.getTipoDocumentoIdentidad());
			entity.setPlanSeleccionado(daoRequest.getPlanSeleccionado());
			entity.setPrecioPlanSeleccionado(daoRequest.getPrecioPlanSeleccionado());
			entity.setNombresCliente(daoRequest.getNombresCliente());
			entity.setApellidosCliente(daoRequest.getApellidosCliente());
			entity.setNombreGrupo(daoRequest.getNombreGrupo());
			return entity;
		});

		setEntityToDaoReference = (entity -> {
			ContratoResponseDao dao = new ContratoResponseDao();
			dao.setId(entity.getId());
			dao.setNumeroDeContrato(entity.getNumeroDeContrato());
			dao.setFechaInicio(entity.getFechaInicio());
			dao.setFechaFin(entity.getFechaFin());
			dao.setDsctoInscripcion(entity.getDsctoInscripcion());
			dao.setDsctoPrimeraCuota(entity.getDsctoPrimeraCuota());
			dao.setObservacion(entity.getObservacion());
			dao.setFechaIniciaCobro(entity.getFechaIniciaCobro());
			dao.setEstado(entity.getEstado());
			dao.setPlazoMesSeleccionado(entity.getPlazoMesSeleccionado());
			dao.setVersion(entity.getVersion());
			dao.setCuotaActual(entity.getCuotaActual());
			dao.setTipoDocumentoIdentidad(entity.getTipoDocumentoIdentidad());
			dao.setDocumentoIdentidad(entity.getDocumentoIdentidad());
			dao.setPlanSeleccionado(entity.getPlanSeleccionado());
			dao.setPrecioPlanSeleccionado(entity.getPrecioPlanSeleccionado());
			dao.setNombresCliente(entity.getNombresCliente());
			dao.setApellidosCliente(entity.getApellidosCliente());
			dao.setNombreGrupo(entity.getNombreGrupo());
			dao.setDsctoRecargo(entity.getDsctoRecargo());
			dao.setSisHabilitado(entity.getSisHabilitado());
			dao.setSisActualizado(entity.getSisActualizado());
			dao.setSisCreado(entity.getSisCreado());
			dao.setSisArchivo(entity.getSisArchivo());
			dao.setSisImagen(entity.getSisImagen());
			return dao;
		});

		setEntityListToDaoResponseList = (entityList -> {
			return (List<ContratoResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(ContratoMapper.setEntityToDaoReference).collect(Collectors.toList());
		});
		setEntityListToDaoReferenceList = (entityList -> {
			return (List<ContratoResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(ContratoMapper.setEntityToDaoReference).collect(Collectors.toList());
		});
	}

}
