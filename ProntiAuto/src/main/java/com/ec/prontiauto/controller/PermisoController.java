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
import com.ec.prontiauto.dao.PermisoRequestDao;
import com.ec.prontiauto.dao.PermisoResponseDao;
import com.ec.prontiauto.entidad.Permiso;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.mapper.PermisoMapper;
import com.ec.prontiauto.repositoryImpl.PermisoRepositoryImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/permiso")
//@CrossOrigingin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
@Api(tags = "Permiso", description = "Gestion de lista Permiso")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class PermisoController extends AbstractController<Permiso, PermisoRequestDao, PermisoResponseDao, Integer> {

	private GenericMethods genericMethods = new GenericMethods();

	@Autowired
	private PermisoRepositoryImpl permisoRepository;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public PermisoResponseDao devolverRespuestaDao(Permiso entity) {
		return PermisoMapper.setEntityToDaoResponse.apply(entity);
	}

	@Override
	@Transactional
	public Permiso devolverRespuestaUpdate(Permiso entity, Integer id) {
		Permiso antiguo = (Permiso) genericMethods.findById("Permiso", entityManager, id);
		if (antiguo != null) {
			antiguo = antiguo.setValoresDiferentes(antiguo, entity);
		}
		return antiguo;
	}

	@Override
	public Permiso setDaoRequestToEntity(PermisoRequestDao dao) {
		return PermisoMapper.setDaoRequestToEntity.apply(dao);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	@ApiOperation(tags = "Permiso", value = "buscar por nombre y filtrar por sisHabilitado")
	public ResponseEntity<?> findBySearchAndUpdate(String busqueda,
			// Agregar los otros parametros de busqueda (queryParams)
			// EJ: String tipo,
			String sisHabilitado,
			Integer skip,
			Integer take,
			String sortField,
			Boolean sortAscending) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		try {
			// Se debe de crear el HashMap de todos los parametros que se van a enviar a la
			// busqueda
			Map<String, Object> params = new HashMap<>();
			params.put("busqueda", busqueda == null ? "" : busqueda);
			params.put("sisHabilitado", sisHabilitado == null ? "" : sisHabilitado);
			String sortFieldDefault = "id";
			sortField = sortField == null ? sortFieldDefault : sortField;
			sortAscending = sortAscending == null ? true : sortAscending;

			List<Object> list = this.findWithSkipAndTake(
					this.permisoRepository,
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
