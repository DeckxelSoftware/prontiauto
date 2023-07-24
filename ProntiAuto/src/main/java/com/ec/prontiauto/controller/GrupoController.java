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
import com.ec.prontiauto.dao.GrupoRequestDao;
import com.ec.prontiauto.dao.GrupoResponseDao;
import com.ec.prontiauto.entidad.Grupo;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.mapper.GrupoMapper;
import com.ec.prontiauto.repositoryImpl.GrupoRepositoryImpl;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/grupo")
// @CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST
// })
@Api(tags = "Grupo", description = "Gestion de Grupo")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class GrupoController extends AbstractController<Grupo, GrupoRequestDao, GrupoResponseDao, Integer> {

	private GenericMethods genericMethods = new GenericMethods();

	@Autowired
	private GrupoRepositoryImpl grupoRepository;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public GrupoResponseDao devolverRespuestaDao(Grupo entity) {
		return GrupoMapper.setEntityToDaoResponse.apply(entity);
	}

	@Override
	@Transactional
	public Grupo devolverRespuestaUpdate(Grupo entity, Integer id) {
		Grupo antiguo = (Grupo) genericMethods.findById("Grupo", entityManager, id);
		if (antiguo != null) {
			antiguo = antiguo.setValoresDiferentes(antiguo, entity);
		}
		return antiguo;
	}

	@Override
	public Grupo setDaoRequestToEntity(GrupoRequestDao dao) {
		return GrupoMapper.setDaoRequestToEntity.apply(dao);
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
					this.grupoRepository,
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
