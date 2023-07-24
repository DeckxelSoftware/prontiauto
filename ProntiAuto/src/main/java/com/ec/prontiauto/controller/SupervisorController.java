package com.ec.prontiauto.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ec.prontiauto.abstracts.AbstractController;
import com.ec.prontiauto.abstracts.GenericMethods;
import com.ec.prontiauto.dao.SupervisorRequestDao;
import com.ec.prontiauto.dao.SupervisorResponseDao;
import com.ec.prontiauto.entidad.Agencia;
import com.ec.prontiauto.entidad.Supervisor;
import com.ec.prontiauto.entidad.Trabajador;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.mapper.SupervisorMapper;
import com.ec.prontiauto.repositoryImpl.SupervisorRepositoryImpl;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/supervisor")
@Api(tags = "Supervisor", description = "Gestion de supervisor")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class SupervisorController extends
		AbstractController<Supervisor, SupervisorRequestDao, SupervisorResponseDao, Integer> {

	private GenericMethods genericMethods = new GenericMethods();

	@Autowired
	private SupervisorRepositoryImpl supervisorRepository;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public SupervisorResponseDao devolverRespuestaDao(Supervisor entity) {
		return SupervisorMapper.setEntityToDaoResponse.apply(entity);
	}

	@Override
	@Transactional
	public Supervisor devolverRespuestaUpdate(Supervisor entity, Integer id) {
		Supervisor antiguo = (Supervisor) genericMethods.findById("Supervisor",
				entityManager, id);
		if (antiguo != null) {
			antiguo = antiguo.setValoresDiferentes(antiguo, entity);
		}
		return antiguo;
	}

	@Override
	public Supervisor setDaoRequestToEntity(SupervisorRequestDao dao) {
		return SupervisorMapper.setDaoRequestToEntity.apply(dao);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> findBySearchAndUpdate(String busqueda,
			String sisHabilitado,
			Integer idAgencia,
			Integer idTrabajador,
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
			Trabajador trabajador = new Trabajador();
			trabajador.setId(idTrabajador);
			params.put("idTrabajador", idTrabajador == null ? "" : trabajador);

			Agencia agencia = new Agencia();
			agencia.setId(idAgencia);
			params.put("idAgencia", idAgencia == null ? "" : agencia);

			String sortFieldDefault = "id";
			sortField = sortField == null ? sortFieldDefault : sortField;
			sortAscending = sortAscending == null ? true : sortAscending;

			List<Object> list = this.findWithSkipAndTake(
					this.supervisorRepository,
					params,
					skip,
					take,
					sortField,
					sortAscending);

			return new ResponseEntity<>(list, httpHeaders, HttpStatus.OK);
		} catch (Exception e) {
			httpHeaders.add("STATUS", "400");
			return new ResponseEntity<ExceptionResponse>(new ExceptionResponse("400", e.getMessage()), httpHeaders,
					HttpStatus.BAD_REQUEST);
		}
	}

}
