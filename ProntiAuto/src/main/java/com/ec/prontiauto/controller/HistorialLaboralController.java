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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ec.prontiauto.abstracts.AbstractController;
import com.ec.prontiauto.abstracts.GenericMethods;
import com.ec.prontiauto.dao.HistorialLaboralRequestDao;
import com.ec.prontiauto.dao.HistorialLaboralResponseDao;
import com.ec.prontiauto.entidad.Cargo;
import com.ec.prontiauto.entidad.HistorialLaboral;
import com.ec.prontiauto.entidad.Trabajador;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.mapper.HistorialLaboralMapper;
import com.ec.prontiauto.repositoryImpl.HistorialLaboralRepositoryImpl;
import com.ec.prontiauto.validations.HistorialLabloralValidation;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/historial-laboral")
// @CrossOrigingin(origins = "*", methods = { RequestMethod.GET,
// RequestMethod.POST, RequestMethod.PUT })
@Api(tags = "Historial Laboral", description = "Gestion Historial Laboral")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class HistorialLaboralController
		extends AbstractController<HistorialLaboral, HistorialLaboralRequestDao, HistorialLaboralResponseDao, Integer> {

	private GenericMethods genericMethods = new GenericMethods();

	@Autowired
	private HistorialLaboralRepositoryImpl historialLaboralRepositoryImpl;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public HistorialLaboralResponseDao devolverRespuestaDao(HistorialLaboral entity) {
		return HistorialLaboralMapper.setEntityToDaoResponse.apply(entity);
	}

	@Override
	@Transactional
	public HistorialLaboral devolverRespuestaUpdate(HistorialLaboral entity, Integer id) {
		HistorialLaboral antiguo = (HistorialLaboral) genericMethods.findById("HistorialLaboral",
				entityManager, id);
		if (antiguo != null) {
			antiguo = antiguo.setValoresDiferentes(antiguo, entity);
		}
		return antiguo;
	}

	@Override
	public HistorialLaboral setDaoRequestToEntity(HistorialLaboralRequestDao dao) {
		return HistorialLaboralMapper.setDaoRequestToEntity.apply(dao);
	}

	@Override
	public HistorialLaboral validationRequestEntityOnlyUpdate(HistorialLaboral entity) {
		HistorialLabloralValidation val = new HistorialLabloralValidation(entityManager, entity, true);
		entity = val.getEntity();
		return entity;
	}

	@Override
	public HistorialLaboral validationRequestEntityOnlyPost(HistorialLaboral entity) {
		HistorialLabloralValidation val = new HistorialLabloralValidation(entityManager, entity, false);
		entity = val.getEntity();
		entity.setId(null);
		return entity;
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> findBySearchAndUpdate(String busqueda,
			String sisHabilitado,
			Date fechaIngreso,
			Integer idTrabajador,
			Integer idCargo,
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
			params.put("fechaIngreso", fechaIngreso == null ? "" : fechaIngreso);
			Trabajador trabajador = new Trabajador();
			trabajador.setId(idTrabajador);
			params.put("idTrabajador", idTrabajador == null ? "" : trabajador);
			Cargo cargo = new Cargo();
			cargo.setId(idCargo);
			params.put("idCargo", idCargo == null ? "" : cargo);

			params.put("id", id == null ? "" : id);
			String sortFieldDefault = "id";
			sortField = sortField == null ? sortFieldDefault : sortField;
			sortAscending = sortAscending == null ? true : sortAscending;

			List<Object> list = this.findWithSkipAndTake(
					this.historialLaboralRepositoryImpl,
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
