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
import com.ec.prontiauto.dao.RolRequestDao;
import com.ec.prontiauto.dao.RolResponseDao;
import com.ec.prontiauto.entidad.Rol;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.mapper.RolMapper;
import com.ec.prontiauto.repositoryImpl.RolRepositoryImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/rol")
//@CrossOrigingin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
@Api(tags = "Rol", description = "Gestion de lista Rol")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class RolController extends AbstractController<Rol, RolRequestDao, RolResponseDao, Integer> {

	private GenericMethods genericMethods = new GenericMethods();

	@Autowired
	private RolRepositoryImpl rolRepository;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public RolResponseDao devolverRespuestaDao(Rol entity) {
		return RolMapper.setEntityToDaoResponse.apply(entity);
	}

	@Override
	@Transactional
	public Rol devolverRespuestaUpdate(Rol entity, Integer id) {
		Rol antiguo = (Rol) genericMethods.findById("Rol", entityManager, id);
		if (antiguo != null) {
			antiguo = antiguo.setValoresDiferentes(antiguo, entity);
		}
		return antiguo;
	}

	@Override
	public Rol setDaoRequestToEntity(RolRequestDao dao) {
		return RolMapper.setDaoRequestToEntity.apply(dao);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	@ApiOperation(tags = "Rol", value = "buscar por nombre y filtrar por sishabilitado")
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
					this.rolRepository,
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
