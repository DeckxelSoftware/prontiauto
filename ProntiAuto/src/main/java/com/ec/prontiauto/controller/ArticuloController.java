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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ec.prontiauto.abstracts.AbstractController;
import com.ec.prontiauto.abstracts.GenericMethods;
import com.ec.prontiauto.dao.ArticuloRequestDao;
import com.ec.prontiauto.dao.ArticuloResponseDao;
import com.ec.prontiauto.entidad.Articulo;
import com.ec.prontiauto.exception.ApiRequestException;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.mapper.ArticuloMapper;
import com.ec.prontiauto.repositoryImpl.ArticuloRepositoryImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/articulo")
// @CrossOrigingin(origins = "*", methods = { RequestMethod.GET,
// RequestMethod.PUT, RequestMethod.POST })
@Api(tags = "Articulo", value = "ProntiAuto", description = "Creación, actualización y  consulta de articulo")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class ArticuloController
		extends AbstractController<Articulo, ArticuloRequestDao, ArticuloResponseDao, Integer> {

	private GenericMethods genericMethods = new GenericMethods();

	@Autowired
	private ArticuloRepositoryImpl articuloRepository;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public ArticuloResponseDao devolverRespuestaDao(Articulo entity) {
		return ArticuloMapper.setEntityToDaoResponse.apply(entity);
	}

	@Override
	@Transactional
	public Articulo devolverRespuestaUpdate(Articulo entity, Integer id) {
		Articulo antiguo = (Articulo) genericMethods.findById("Articulo", entityManager, id);
		if (antiguo != null) {
			antiguo = antiguo.setValoresDiferentes(antiguo, entity);
		}
		return antiguo;
	}

	@Override
	public Articulo setDaoRequestToEntity(ArticuloRequestDao dao) {
		return ArticuloMapper.setDaoRequestToEntity.apply(dao);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	@ApiOperation(tags = "Articulo", value = "buscar por placa, chasis y filtrar por estado")
	public ResponseEntity<?> findBySearchAndUpdate(String busqueda,
			String sisHabilitado,
			String estado,
			String ubicacionFisica,
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
			params.put("estado", estado == null ? "" : estado);
			params.put("ubicacionFisica", ubicacionFisica == null ? "" : ubicacionFisica);
			params.put("id", id == null ? "" : id);
			String sortFieldDefault = "id";
			sortField = sortField == null ? sortFieldDefault : sortField;
			sortAscending = sortAscending == null ? true : sortAscending;

			List<Object> list = this.findWithSkipAndTake(
					this.articuloRepository,
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

	@RequestMapping(value = "/custom/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody ArticuloRequestDao dao, @PathVariable("id") Integer id) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		try {
			httpHeaders.add("STATUS", "200");
			dao.setId(id);
			Articulo entity = this.setDaoRequestToEntity(dao);
			Articulo articulo = this.devolverRespuestaUpdate(entity, id);
			this.entityManager.merge(articulo);
			String response = this.articuloRepository.abstractProcedure(dao, "estado_adjudicado");
			return new ResponseEntity<ExceptionResponse>(
					new ExceptionResponse("200", response),
					httpHeaders,
					HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("-------------------\n" + e.getMessage());
			throw new ApiRequestException(e.getMessage());
		}
	}

}
