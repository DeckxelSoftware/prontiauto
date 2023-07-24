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
import com.ec.prontiauto.dao.CuentaContableRequestDao;
import com.ec.prontiauto.dao.CuentaContableResponseDao;
import com.ec.prontiauto.entidad.CuentaContable;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.mapper.CuentaContableMapper;
import com.ec.prontiauto.repositoryImpl.CuentaContableRepositoryImpl;
import com.ec.prontiauto.validations.CuentaContableValidation;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/cuenta-contable")
// @CrossOrigingin(origins = "*", methods = { RequestMethod.GET,
// RequestMethod.POST, RequestMethod.PUT })
@Api(tags = "Cuenta Contable", description = "Gestion Cuenta Contable")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class CuentaContableController
		extends AbstractController<CuentaContable, CuentaContableRequestDao, CuentaContableResponseDao, Integer> {

	private GenericMethods genericMethods = new GenericMethods();

	@Autowired
	private CuentaContableRepositoryImpl cuentaContableRepositoryImpl;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public CuentaContableResponseDao devolverRespuestaDao(CuentaContable entity) {
		return CuentaContableMapper.setEntityToDaoResponse.apply(entity);
	}

	@Override
	@Transactional
	public CuentaContable devolverRespuestaUpdate(CuentaContable entity, Integer id) {
		CuentaContable antiguo = (CuentaContable) genericMethods.findById("CuentaContable", entityManager, id);
		if (antiguo != null) {
			antiguo = antiguo.setValoresDiferentes(antiguo, entity);
		}
		return antiguo;
	}

	@Override
	public CuentaContable setDaoRequestToEntity(CuentaContableRequestDao dao) {
		return CuentaContableMapper.setDaoRequestToEntity.apply(dao);
	}

	@Override
	public CuentaContable validationRequestEntityOnlyPost(CuentaContable entity) {

		CuentaContableValidation validation = new CuentaContableValidation(this.entityManager, entity, false);
		entity = validation.getEntity();
		return entity;
	}

	@Override
	public CuentaContable validationRequestEntityOnlyUpdate(CuentaContable entity) {
		CuentaContableValidation validation = new CuentaContableValidation(this.entityManager, entity, true);

		entity = validation.getEntity();
		return entity;
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> findBySearchAndUpdate(
			String busqueda,
			String sisHabilitado,
			Integer nivel,
			String nombre,
			Integer anio,
			Integer identificador,
			Integer id,
			Integer skip,
			Integer take, String sortField, Boolean sortAscending) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("busqueda", busqueda == null ? "" : busqueda);
			params.put("sisHabilitado", sisHabilitado == null ? "" : sisHabilitado);
			params.put("nivel", nivel == null ? "" : nivel);
			params.put("nombre", nombre == null ? "" : nombre);
			params.put("anio", anio == null ? "" : anio);
			params.put("identificador", identificador == null ? "" : identificador);
			params.put("id", id == null ? "" : id);

			String sortFieldDefault = "id";
			sortField = sortField == null ? sortFieldDefault : sortField;
			sortAscending = sortAscending == null ? true : sortAscending;

			List<Object> list = this.findWithSkipAndTake(
					this.cuentaContableRepositoryImpl,
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
