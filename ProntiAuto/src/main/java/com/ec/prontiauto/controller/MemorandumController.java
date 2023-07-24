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
import com.ec.prontiauto.dao.MemorandumRequestDao;
import com.ec.prontiauto.dao.MemorandumResponseDao;
import com.ec.prontiauto.entidad.Memorandum;
import com.ec.prontiauto.entidad.Trabajador;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.mapper.MemorandumMapper;
import com.ec.prontiauto.repositoryImpl.MemorandumRepositoryImpl;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/memorandum")
// @CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST
// })
@Api(tags = "Memorandum", description = "Gestion de Memorandum")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class MemorandumController extends
		AbstractController<Memorandum, MemorandumRequestDao, MemorandumResponseDao, Integer> {

	private GenericMethods genericMethods = new GenericMethods();

	@Autowired
	private MemorandumRepositoryImpl memorandumRepositoryImpl;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public MemorandumResponseDao devolverRespuestaDao(Memorandum entity) {
		return MemorandumMapper.setEntityToDaoResponse.apply(entity);
	}

	@Override
	@Transactional
	public Memorandum devolverRespuestaUpdate(Memorandum entity, Integer id) {
		Memorandum antiguo = (Memorandum) genericMethods.findById("Memorandum",
				entityManager, id);
		if (antiguo != null) {
			antiguo = antiguo.setValoresDiferentes(antiguo, entity);
		}
		return antiguo;
	}

	@Override
	public Memorandum setDaoRequestToEntity(MemorandumRequestDao dao) {
		return MemorandumMapper.setDaoRequestToEntity.apply(dao);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> findBySearchAndUpdate(
			String sisHabilitado,
			Integer idTrabajador,
			Integer id,
			Integer skip,
			Integer take,
			String sortField,
			Boolean sortAscending) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("sisHabilitado", sisHabilitado == null ? "" : sisHabilitado);
			Trabajador trabajador = new Trabajador();
			trabajador.setId(idTrabajador);
			params.put("idTrabajador", idTrabajador == null ? "" : trabajador);
			params.put("id", id == null ? "" : id);
			String sortFieldDefault = "id";
			sortField = sortField == null ? sortFieldDefault : sortField;
			sortAscending = sortAscending == null ? true : sortAscending;

			List<Object> list = this.findWithSkipAndTake(
					this.memorandumRepositoryImpl,
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
