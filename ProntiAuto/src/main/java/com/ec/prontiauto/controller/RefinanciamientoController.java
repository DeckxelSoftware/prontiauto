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
import com.ec.prontiauto.dao.RefinanciamientoRequestDao;
import com.ec.prontiauto.dao.RefinanciamientoResponseDao;
import com.ec.prontiauto.entidad.Refinanciamiento;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.mapper.RefinanciamientoMapper;
import com.ec.prontiauto.repositoryImpl.RefinanciamientoRepositoryImpl;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/refinanciamiento")
// @CrossOrigingin(origins = "*", methods = { RequestMethod.GET,
// RequestMethod.POST, RequestMethod.PUT })
@Api(tags = "Refinanciamiento", description = "Gestion de lista Refinanciamiento")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class RefinanciamientoController
		extends AbstractController<Refinanciamiento, RefinanciamientoRequestDao, RefinanciamientoResponseDao, Integer> {

	private GenericMethods genericMethods = new GenericMethods();

	@Autowired
	private RefinanciamientoRepositoryImpl refinanciamientoRepositoryImpl;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public RefinanciamientoResponseDao devolverRespuestaDao(Refinanciamiento entity) {
		return RefinanciamientoMapper.setEntityToDaoResponse.apply(entity);
	}

	@Override
	@Transactional
	public Refinanciamiento devolverRespuestaUpdate(Refinanciamiento entity, Integer id) {
		Refinanciamiento antiguo = (Refinanciamiento) genericMethods.findById("Refinanciamiento", entityManager, id);
		if (antiguo != null) {
			antiguo = antiguo.setValoresDiferentes(antiguo, entity);
		}
		return antiguo;
	}

	@Override
	public Refinanciamiento setDaoRequestToEntity(RefinanciamientoRequestDao dao) {
		return RefinanciamientoMapper.setDaoRequestToEntity.apply(dao);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> findBySearchAndUpdate(String sisHabilitado,
			Integer skip,
			Integer take, String sortField, Boolean sortAscending) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("sisHabilitado", sisHabilitado == null ? "" : sisHabilitado);
			String sortFieldDefault = "id";
			sortField = sortField == null ? sortFieldDefault : sortField;
			sortAscending = sortAscending == null ? true : sortAscending;

			List<Object> list = this.findWithSkipAndTake(
					this.refinanciamientoRepositoryImpl,
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
