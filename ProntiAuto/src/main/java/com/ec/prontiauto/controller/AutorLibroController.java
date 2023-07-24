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
import com.ec.prontiauto.dao.AutorLibroRequestDao;
import com.ec.prontiauto.dao.AutorLibroResponseDao;
import com.ec.prontiauto.entidad.AutorLibro;
import com.ec.prontiauto.entidad.LibroBiblioteca;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.mapper.AutorLibroMapper;
import com.ec.prontiauto.repositoryImpl.AutorLibroRepositoryImpl;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/autor-libro")
//@CrossOrigingin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
@Api(tags = "Autor Libro", description = "Gestion de lista autor libro")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class AutorLibroController
		extends AbstractController<AutorLibro, AutorLibroRequestDao, AutorLibroResponseDao, Integer> {

	private GenericMethods genericMethods = new GenericMethods();

	@Autowired
	private AutorLibroRepositoryImpl autorLibroRepository;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public AutorLibroResponseDao devolverRespuestaDao(AutorLibro entity) {
		return AutorLibroMapper.setEntityToDaoResponse.apply(entity);
	}

	@Override
	@Transactional
	public AutorLibro devolverRespuestaUpdate(AutorLibro entity, Integer id) {
		AutorLibro antiguo = (AutorLibro) genericMethods.findById("AutorLibro", entityManager, id);
		if (antiguo != null) {
			antiguo = antiguo.setValoresDiferentes(antiguo, entity);
		}
		return antiguo;
	}

	@Override
	public AutorLibro setDaoRequestToEntity(AutorLibroRequestDao dao) {
		return AutorLibroMapper.setDaoRequestToEntity.apply(dao);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> findBySearchAndUpdate(String busqueda,
			Integer idLibroBiblioteca, String sisHabilitado, Integer skip,
			Integer take, String sortField, Boolean sortAscending) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		try {

			Map<String, Object> params = new HashMap<>();
			params.put("busqueda", busqueda == null ? "" : busqueda);
			params.put("sisHabilitado", sisHabilitado == null ? "" : sisHabilitado);
			LibroBiblioteca libroBiblioteca = new LibroBiblioteca();
			libroBiblioteca.setId(idLibroBiblioteca);
			params.put("idLibroBiblioteca", idLibroBiblioteca == null ? "" : libroBiblioteca);
			String sortFieldDefault = "id";
			sortField = sortField == null ? sortFieldDefault : sortField;
			sortAscending = sortAscending == null ? true : sortAscending;

			List<Object> list = this.findWithSkipAndTake(
					this.autorLibroRepository,
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
