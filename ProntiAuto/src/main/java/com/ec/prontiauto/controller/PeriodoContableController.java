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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ec.prontiauto.abstracts.AbstractController;
import com.ec.prontiauto.abstracts.GenericMethods;
import com.ec.prontiauto.dao.PeriodoContableRequestDao;
import com.ec.prontiauto.dao.PeriodoContableResponseDao;
import com.ec.prontiauto.entidad.PeriodoContable;
import com.ec.prontiauto.exception.ApiRequestException;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.mapper.PeriodoContableMapper;
import com.ec.prontiauto.repositoryImpl.PeriodoContableRepositoryImpl;
import com.ec.prontiauto.validations.PeriodoContableValidation;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/periodo-contable")
// @CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST
// })
@Api(tags = "Periodo Contable", description = "Gestion de Periodo Contable")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class PeriodoContableController extends
		AbstractController<PeriodoContable, PeriodoContableRequestDao, PeriodoContableResponseDao, Integer> {

	private GenericMethods genericMethods = new GenericMethods();

	@Autowired
	private PeriodoContableRepositoryImpl periodoRepositoryImpl;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public PeriodoContableResponseDao devolverRespuestaDao(PeriodoContable entity) {
		return PeriodoContableMapper.setEntityToDaoResponse.apply(entity);
	}

	@Override
	@Transactional
	public PeriodoContable devolverRespuestaUpdate(PeriodoContable entity, Integer id) {
		PeriodoContable antiguo = (PeriodoContable) genericMethods.findById("PeriodoContable",
				entityManager, id);
		if (antiguo != null) {
			antiguo = antiguo.setValoresDiferentes(antiguo, entity);
		}
		return antiguo;
	}

	@Override
	public PeriodoContable setDaoRequestToEntity(PeriodoContableRequestDao dao) {
		return PeriodoContableMapper.setDaoRequestToEntity.apply(dao);
	}

	@Override
	public PeriodoContable validationRequestEntity(PeriodoContable entity) {

		boolean exitsEnablePeriod = this.periodoRepositoryImpl.getExitsData();

		if (entity.getEsPeriodoActual().equals("A") && exitsEnablePeriod) {
			throw new ApiRequestException("Ya existe un periodo actual habilitado");
		}

		boolean existAnio = this.periodoRepositoryImpl.getExistYear(entity.getAnio());

		if (existAnio) {
			throw new ApiRequestException("Ya existe un periodo contable para el año " + entity.getAnio());
		}

		return entity;
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> findBySearchAndUpdate(String busqueda,
			String sisHabilitado,
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
			String sortFieldDefault = "id";
			sortField = sortField == null ? sortFieldDefault : sortField;
			sortAscending = sortAscending == null ? true : sortAscending;

			List<Object> list = this.findWithSkipAndTake(
					this.periodoRepositoryImpl,
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

	@Override
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody PeriodoContableRequestDao dao, @PathVariable("id") Integer id) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		try {
			httpHeaders.add("STATUS", "200");

			PeriodoContableValidation validation = new PeriodoContableValidation(this.entityManager);

			if (dao.getEsPeriodoActual() != null && dao.getEsPeriodoActual().equals("A")) {
				validation.actualizarPeriodoContableActivo();
			}

			PeriodoContable newEntity = setDaoRequestToEntity(dao);
			PeriodoContable entity = this.devolverRespuestaUpdate(newEntity, id);
			this.entityManager.merge(entity);

			return new ResponseEntity<>(devolverRespuestaDao(
					entity), httpHeaders,
					HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("-------------------\n" + e.getMessage());
			throw new ApiRequestException(e.getMessage());
		}
	}
}
