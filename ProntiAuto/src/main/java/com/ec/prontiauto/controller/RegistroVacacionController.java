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
import com.ec.prontiauto.dao.RegistroVacacionRequestDao;
import com.ec.prontiauto.dao.RegistroVacacionResponseDao;
import com.ec.prontiauto.entidad.CargoVacacion;
import com.ec.prontiauto.entidad.RegistroVacacion;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.mapper.RegistroVacacionMapper;
import com.ec.prontiauto.repositoryImpl.RegistroVacacionRepositoryImpl;
import com.ec.prontiauto.validations.RegistroVacacionValidation;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/registro-vacacion")
// @CrossOrigingin(origins = "*", methods = { RequestMethod.GET,
// RequestMethod.POST, RequestMethod.PUT })
@Api(tags = "Registro Vacacion", description = "Gestion Registro Vacacion")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class RegistroVacacionController
		extends AbstractController<RegistroVacacion, RegistroVacacionRequestDao, RegistroVacacionResponseDao, Integer> {

	private GenericMethods genericMethods = new GenericMethods();

	@Autowired
	private RegistroVacacionRepositoryImpl registroVacacionRepositoryImpl;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public RegistroVacacionResponseDao devolverRespuestaDao(RegistroVacacion entity) {
		return RegistroVacacionMapper.setEntityToDaoResponse.apply(entity);
	}

	@Override
	@Transactional
	public RegistroVacacion devolverRespuestaUpdate(RegistroVacacion entity, Integer id) {
		RegistroVacacion antiguo = (RegistroVacacion) genericMethods.findById("RegistroVacacion",
				entityManager, id);
		if (antiguo != null) {
			antiguo = antiguo.setValoresDiferentes(antiguo, entity);
		}
		return antiguo;
	}

	@Override
	public RegistroVacacion setDaoRequestToEntity(RegistroVacacionRequestDao dao) {
		return RegistroVacacionMapper.setDaoRequestToEntity.apply(dao);
	}

	@Override
	public RegistroVacacion validationRequestEntity(RegistroVacacion entity) {

		RegistroVacacionValidation val = new RegistroVacacionValidation(this.entityManager,
				entity.getIdCargoVacacion().getId(), entity, false);

		val.valCreateRegistroVacacion();

		entity = val.getEntity();

		return entity;
	}

	@Override
	public RegistroVacacion validationRequestEntityOnlyPost(RegistroVacacion entity) {

		RegistroVacacionValidation val = new RegistroVacacionValidation(this.entityManager,
				entity.getIdCargoVacacion().getId(), entity, true);

		entity = val.getEntity();
		entity.setId(null);

		return entity;
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> findBySearchAndUpdate(String busqueda,
			String sisHabilitado,
			Integer id,
			Integer skip,
			Integer idCargoVacacion,
			Integer take,
			String sortField,
			Boolean sortAscending) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("busqueda", busqueda == null ? "" : busqueda);
			params.put("sisHabilitado", sisHabilitado == null ? "" : sisHabilitado);
			params.put("id", id == null ? "" : id);
			String sortFieldDefault = "id";
			sortField = sortField == null ? sortFieldDefault : sortField;
			sortAscending = sortAscending == null ? true : sortAscending;

			CargoVacacion cargo = new CargoVacacion();
			cargo.setId(idCargoVacacion);
			params.put("idCargoVacacion", idCargoVacacion == null ? "" : cargo);

			List<Object> list = this.findWithSkipAndTake(
					this.registroVacacionRepositoryImpl,
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
