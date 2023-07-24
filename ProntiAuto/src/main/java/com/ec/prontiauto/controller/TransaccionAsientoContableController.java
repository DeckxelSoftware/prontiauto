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
import com.ec.prontiauto.dao.TransaccionAsientoContableRequestDao;
import com.ec.prontiauto.dao.TransaccionAsientoContableResponseDao;
import com.ec.prontiauto.entidad.AsientoContableCabecera;
import com.ec.prontiauto.entidad.TransaccionAsientoContable;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.mapper.TransaccionAsientoContableMapper;
import com.ec.prontiauto.repositoryImpl.TransaccionAsientoContableRepositoryImpl;
import com.ec.prontiauto.validations.TransaccionAsientoContableValidation;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/transaccion-asiento-contable")
@Api(tags = "Transaccion Asiento Contable", description = "Gestion de TransaccionAsientoContable")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class TransaccionAsientoContableController extends
		AbstractController<TransaccionAsientoContable, TransaccionAsientoContableRequestDao, TransaccionAsientoContableResponseDao, Integer> {

	private GenericMethods genericMethods = new GenericMethods();

	@Autowired
	private TransaccionAsientoContableRepositoryImpl transaccionAsientoContableRepositoryImpl;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public TransaccionAsientoContableResponseDao devolverRespuestaDao(TransaccionAsientoContable entity) {
		return TransaccionAsientoContableMapper.setEntityToDaoResponse.apply(entity);
	}

	@Override
	@Transactional
	public TransaccionAsientoContable devolverRespuestaUpdate(TransaccionAsientoContable entity, Integer id) {
		TransaccionAsientoContable antiguo = (TransaccionAsientoContable) genericMethods
				.findById("TransaccionAsientoContable", entityManager, id);
		if (antiguo != null) {
			antiguo = antiguo.setValoresDiferentes(antiguo, entity);
		}
		return antiguo;
	}

	@Override
	public TransaccionAsientoContable setDaoRequestToEntity(TransaccionAsientoContableRequestDao dao) {
		return TransaccionAsientoContableMapper.setDaoRequestToEntity.apply(dao);
	}

	@Override
	public TransaccionAsientoContableRequestDao validationRequest(TransaccionAsientoContableRequestDao dao) {
		TransaccionAsientoContableValidation validation = new TransaccionAsientoContableValidation(entityManager,
				dao, false);

		return validation.getEntity();
	}

	@Override
	public TransaccionAsientoContableRequestDao validationRequestUpdate(TransaccionAsientoContableRequestDao dao, Integer id) {
		dao.setId(id);
		TransaccionAsientoContableValidation validation = new TransaccionAsientoContableValidation(entityManager,
				dao, true);

		return validation.getEntity();
	}


	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> findBySearchAndUpdate(String busqueda,
			String sisHabilitado,
			Integer idAsientoContableCabecera,
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
			AsientoContableCabecera asientoContableCabecera = new AsientoContableCabecera();
			asientoContableCabecera.setId(idAsientoContableCabecera);
			params.put("idAsientoContableCabecera", idAsientoContableCabecera == null ? "" : asientoContableCabecera);
			String sortFieldDefault = "id";
			sortField = sortField == null ? sortFieldDefault : sortField;
			sortAscending = sortAscending == null ? true : sortAscending;

			List<Object> list = this.findWithSkipAndTake(
					this.transaccionAsientoContableRepositoryImpl,
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
