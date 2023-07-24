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
import com.ec.prontiauto.dao.LibroBibliotecaRequestDao;
import com.ec.prontiauto.dao.LibroBibliotecaResponseDao;
import com.ec.prontiauto.entidad.LibroBiblioteca;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.mapper.LibroBibliotecaMapper;
import com.ec.prontiauto.repositoryImpl.LibroBibliotecaRepositoryImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/libro-biblioteca")
//@CrossOrigingin(origins = "*", methods = { RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST })
@Api(tags = "Libro biblioteca", value = "ProntiAuto", description = "Creación, actualización y  consulta de libros biblioteca")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class LibroBibliotecaController
		extends AbstractController<LibroBiblioteca, LibroBibliotecaRequestDao, LibroBibliotecaResponseDao, Integer> {

	private GenericMethods genericMethods = new GenericMethods();

	@Autowired
	private LibroBibliotecaRepositoryImpl bookLibraryRepository;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public LibroBibliotecaResponseDao devolverRespuestaDao(LibroBiblioteca entity) {
		return LibroBibliotecaMapper.setEntityToDaoResponse.apply(entity);
	}

	@Override
	@Transactional
	public LibroBiblioteca devolverRespuestaUpdate(LibroBiblioteca entity, Integer id) {
		LibroBiblioteca antiguo = (LibroBiblioteca) genericMethods.findById("LibroBiblioteca", entityManager, id);
		if (antiguo != null) {
			antiguo = antiguo.setValoresDiferentes(antiguo, entity);
		}
		return antiguo;
	}

	@Override
	public LibroBiblioteca setDaoRequestToEntity(LibroBibliotecaRequestDao dao) {
		return LibroBibliotecaMapper.setDaoRequestToEntity.apply(dao);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	@ApiOperation(tags = "Libro biblioteca", value = "buscar por isbn, nombre y filtrar por sisHabilitado,genero libro")
	public ResponseEntity<?> findBySearchAndUpdate(
			String busqueda,
			String generoLibro, 
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
			params.put("generoLibro", generoLibro == null ? "" : generoLibro);
			params.put("sisHabilitado", sisHabilitado == null ? "" : sisHabilitado);
			String sortFieldDefault = "id";
			sortField = sortField == null ? sortFieldDefault : sortField;
			sortAscending = sortAscending == null ? true : sortAscending;

			List<Object> list = this.findWithSkipAndTake(
					this.bookLibraryRepository,
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
