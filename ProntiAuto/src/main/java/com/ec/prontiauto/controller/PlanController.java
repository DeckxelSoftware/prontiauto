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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ec.prontiauto.abstracts.AbstractController;
import com.ec.prontiauto.abstracts.GenericMethods;
import com.ec.prontiauto.dao.ArrayPlanRequetDao;
import com.ec.prontiauto.dao.PlanRequestDao;
import com.ec.prontiauto.dao.PlanResponseDao;
import com.ec.prontiauto.entidad.Plan;
import com.ec.prontiauto.exception.ApiRequestException;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.mapper.PlanMapper;
import com.ec.prontiauto.repositoryImpl.PlanRepositoryImpl;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/plan")
// @CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST
// })
@Api(tags = "Plan", description = "Gestion de Plan")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class PlanController extends
		AbstractController<Plan, PlanRequestDao, PlanResponseDao, Integer> {

	private GenericMethods genericMethods = new GenericMethods();

	@Autowired
	private PlanRepositoryImpl PlanRepository;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public PlanResponseDao devolverRespuestaDao(Plan entity) {
		return PlanMapper.setEntityToDaoResponse.apply(entity);
	}

	@Override
	@Transactional
	public Plan devolverRespuestaUpdate(Plan entity, Integer id) {
		Plan antiguo = (Plan) genericMethods.findById("Plan",
				entityManager, id);
		if (antiguo != null) {
			antiguo = antiguo.setValoresDiferentes(antiguo, entity);
		}
		return antiguo;
	}

	@Override
	public Plan setDaoRequestToEntity(PlanRequestDao dao) {
		return PlanMapper.setDaoRequestToEntity.apply(dao);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> findBySearchAndUpdate(String busqueda,
			Integer skip,
			Integer take,
			String sortField,
			Boolean sortAscending) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("busqueda", busqueda == null ? "" : busqueda);
			String sortFieldDefault = "id";
			sortField = sortField == null ? sortFieldDefault : sortField;
			sortAscending = sortAscending == null ? true : sortAscending;

			List<Object> list = this.findWithSkipAndTake(
					this.PlanRepository,
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

	@RequestMapping(path = "/array", method = RequestMethod.POST)
	public ResponseEntity<?> createArrayPlan(@RequestBody ArrayPlanRequetDao dao) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		try {
			String response = this.PlanRepository.crearPlan(dao, "crear_plan");
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
