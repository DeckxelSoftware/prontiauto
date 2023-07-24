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
import com.ec.prontiauto.dao.CargaFamiliarRequestDao;
import com.ec.prontiauto.dao.CargaFamiliarResponseDao;
import com.ec.prontiauto.entidad.CargaFamiliar;
import com.ec.prontiauto.entidad.Trabajador;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.mapper.CargaFamiliarMapper;
import com.ec.prontiauto.repositoryImpl.CargaFamiliarRepositoryImpl;
import com.ec.prontiauto.validations.CargaFamiliarValidation;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/carga-familiar")
// @CrossOrigingin(origins = "*", methods = { RequestMethod.GET,
// RequestMethod.POST, RequestMethod.PUT })
@Api(tags = "Carga Familiar", description = "Gestion Carga Familiar")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class CargaFamiliarController
		extends AbstractController<CargaFamiliar, CargaFamiliarRequestDao, CargaFamiliarResponseDao, Integer> {

	private GenericMethods genericMethods = new GenericMethods();

	@Autowired
	private CargaFamiliarRepositoryImpl cargaFamiliarRepositoryImpl;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public CargaFamiliarResponseDao devolverRespuestaDao(CargaFamiliar entity) {
		return CargaFamiliarMapper.setEntityToDaoResponse.apply(entity);
	}

	@Override
	@Transactional
	public CargaFamiliar devolverRespuestaUpdate(CargaFamiliar entity, Integer id) {
		CargaFamiliar antiguo = (CargaFamiliar) genericMethods.findById("CargaFamiliar", entityManager, id);
		if (antiguo != null) {
			antiguo = antiguo.setValoresDiferentes(antiguo, entity);
		}
		return antiguo;
	}

	@Override
	public CargaFamiliar setDaoRequestToEntity(CargaFamiliarRequestDao dao) {
		return CargaFamiliarMapper.setDaoRequestToEntity.apply(dao);
	}

	@Override
	public CargaFamiliar validationRequestEntityOnlyPost(CargaFamiliar entity) {
		entity.setId(null);
		return entity;
	}

	@Override
	public CargaFamiliar validationRequestEntity(CargaFamiliar entity) {
		CargaFamiliarValidation val = new CargaFamiliarValidation(entity);

		entity = val.getEntity();
		return entity;
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> findBySearchAndUpdate(String busqueda,
			String sisHabilitado,
			String parentesco,
			String genero,
			String discapacidad,
			String tipoDiscapacidad,
			String estudia,
			Integer idTrabajador,
			Integer skip,
			Integer take, String sortField, Boolean sortAscending) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("busqueda", busqueda == null ? "" : busqueda);
			params.put("sisHabilitado", sisHabilitado == null ? "" : sisHabilitado);
			params.put("parentesco", parentesco == null ? "" : parentesco);
			params.put("genero", genero == null ? "" : genero);
			params.put("discapacidad", discapacidad == null ? "" : discapacidad);
			params.put("tipoDiscapacidad", tipoDiscapacidad == null ? "" : tipoDiscapacidad);
			params.put("estudia", estudia == null ? "" : estudia);
			Trabajador trabajador = new Trabajador();
			trabajador.setId(idTrabajador);
			params.put("idTrabajador", idTrabajador == null ? "" : trabajador);
			String sortFieldDefault = "id";
			sortField = sortField == null ? sortFieldDefault : sortField;
			sortAscending = sortAscending == null ? true : sortAscending;

			List<Object> list = this.findWithSkipAndTake(
					this.cargaFamiliarRepositoryImpl,
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
