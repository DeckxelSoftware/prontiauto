package com.ec.prontiauto.controller;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ec.prontiauto.abstracts.AbstractController;
import com.ec.prontiauto.abstracts.GenericMethods;
import com.ec.prontiauto.dao.PrestamoDao;
import com.ec.prontiauto.dao.PrestamoRequestDao;
import com.ec.prontiauto.dao.PrestamoResponseDao;
import com.ec.prontiauto.entidad.Prestamo;
import com.ec.prontiauto.entidad.Trabajador;
import com.ec.prontiauto.entidad.Usuario;
import com.ec.prontiauto.exception.ApiRequestException;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.mapper.PrestamoMapper;
import com.ec.prontiauto.repositoryImpl.PrestamoRepositoryImpl;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/prestamo")
// @CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST
// })
@Api(tags = "Prestamo", description = "Gestion de Prestamo")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class PrestamoController extends
		AbstractController<Prestamo, PrestamoRequestDao, PrestamoResponseDao, Integer> {

	private GenericMethods genericMethods = new GenericMethods();

	@Autowired
	private PrestamoRepositoryImpl prestamoRepositoryImpl;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public PrestamoResponseDao devolverRespuestaDao(Prestamo entity) {
		return PrestamoMapper.setEntityToDaoResponse.apply(entity);
	}

	@Override
	@Transactional
	public Prestamo devolverRespuestaUpdate(Prestamo entity, Integer id) {
		Prestamo antiguo = (Prestamo) genericMethods.findById("Prestamo",
				entityManager, id);
		if (antiguo != null && antiguo.getEstado().equals("PNT")) {
			antiguo = antiguo.setValoresDiferentes(antiguo, entity);
		} else {
			throw new ApiRequestException("El estado del prestamo no esta Pendiente");
		}
		return antiguo;
	}

	@Override
	public Prestamo setDaoRequestToEntity(PrestamoRequestDao dao) {
		return PrestamoMapper.setDaoRequestToEntity.apply(dao);
	}

	@Override
	public Prestamo validationRequestEntity(Prestamo entity) {

		entity.setEstadoSolicitud("REG");
		return entity;

	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> findBySearchAndUpdate(String busqueda,
			String sisHabilitado,
			Integer idTrabajador,
			Integer idUsuario,
			String tipoPrestamo,
			Date fechaPrestamo,
			String estadoSolicitud,
			String modalidadDescuento,
			Integer id,
			Integer skip,
			Integer take,
			String sortField,
			Boolean sortAscending) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("busqueda", busqueda == null ? "" : busqueda);
			params.put("sisHabilitado", sisHabilitado == null ? "" : sisHabilitado);
			params.put("fechaPrestamo", fechaPrestamo == null ? "" : fechaPrestamo);
			params.put("estadoSolicitud", estadoSolicitud == null ? "" : estadoSolicitud);
			params.put("modalidadDescuento", modalidadDescuento == null ? "" : modalidadDescuento);
			params.put("id", id == null ? "" : id);
			Trabajador trabajador = new Trabajador();
			trabajador.setId(idTrabajador);
			params.put("idTrabajador", idTrabajador == null ? "" : trabajador);
			Usuario usuario = new Usuario();
			usuario.setId(idUsuario);
			params.put("idUsuario", idUsuario == null ? "" : usuario);
			String sortFieldDefault = "id";
			sortField = sortField == null ? sortFieldDefault : sortField;
			sortAscending = sortAscending == null ? true : sortAscending;

			List<Object> list = this.findWithSkipAndTake(
					this.prestamoRepositoryImpl,
					params,
					skip,
					take,
					sortField,
					sortAscending);

			return new ResponseEntity<>(list, httpHeaders, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("-------------------\n" + e.getMessage());
			throw new ApiRequestException(e.getMessage());
		}
	}

	@RequestMapping(path = "/cuotas", method = RequestMethod.POST)
	public ResponseEntity<?> createWithCuotas(@RequestBody PrestamoDao dao) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		try {
			httpHeaders.add("STATUS", "200");
			String response = this.prestamoRepositoryImpl.abstractProcedure(dao, "crear_prestamo");
			return new ResponseEntity<ExceptionResponse>(
					new ExceptionResponse("200", response),
					httpHeaders,
					HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("-------------------\n" + e.getMessage());
			throw new ApiRequestException(e.getMessage());
		}
	}
}
