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
import com.ec.prontiauto.dao.AgenciaRequestDao;
import com.ec.prontiauto.dao.AgenciaResponseDao;
import com.ec.prontiauto.entidad.Agencia;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.mapper.AgenciaMapper;
import com.ec.prontiauto.repositoryImpl.AgenciaRepositoryImpl;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/agencia")
// @CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST
// })
@Api(tags = "Agencia", description = "Gestion de Agencia")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class AgenciaController extends
		AbstractController<Agencia, AgenciaRequestDao, AgenciaResponseDao, Integer> {

	private GenericMethods genericMethods = new GenericMethods();

	@Autowired
	private AgenciaRepositoryImpl agenciaRepository;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public AgenciaResponseDao devolverRespuestaDao(Agencia entity) {
		return AgenciaMapper.setEntityToDaoResponse.apply(entity);
	}

	@Override
	@Transactional
	public Agencia devolverRespuestaUpdate(Agencia entity, Integer id) {
		Agencia antiguo = (Agencia) genericMethods.findById("Agencia",
				entityManager, id);
		if (antiguo != null) {
			antiguo = antiguo.setValoresDiferentes(antiguo, entity);
		}
		return antiguo;
	}

	@Override
	public Agencia setDaoRequestToEntity(AgenciaRequestDao dao) {
		return AgenciaMapper.setDaoRequestToEntity.apply(dao);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> findBySearchAndUpdate(String busqueda,
			String ciudad,
			Integer skip,
			Integer take,
			String sortField,
			Boolean sortAscending) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("busqueda", busqueda == null ? "" : busqueda);
			params.put("ciudad", ciudad == null ? "" : ciudad);
			String sortFieldDefault = "id";
			sortField = sortField == null ? sortFieldDefault : sortField;
			sortAscending = sortAscending == null ? true : sortAscending;

			List<Object> list = this.findWithSkipAndTake(
					this.agenciaRepository,
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
