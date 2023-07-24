
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
import com.ec.prontiauto.dao.OrdenCompraRequestDao;
import com.ec.prontiauto.dao.OrdenCompraResponseDao;
import com.ec.prontiauto.entidad.Articulo;
import com.ec.prontiauto.entidad.OrdenCompra;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.mapper.OrdenCompraMapper;
import com.ec.prontiauto.repositoryImpl.ArticuloRepositoryImpl;
import com.ec.prontiauto.repositoryImpl.OrdenCompraRepositoryImpl;
import com.ec.prontiauto.validations.OrdenCompraValidation;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/orden-de-compra")
@Api(tags = "Orden de compra", description = "Gestion Orden de compra")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class OrdenCompraController
		extends AbstractController<OrdenCompra, OrdenCompraRequestDao, OrdenCompraResponseDao, Integer> {

	private GenericMethods genericMethods = new GenericMethods();

	@Autowired
	private OrdenCompraRepositoryImpl OrdenCompraRepositoryImpl;

	@Autowired
	private ArticuloRepositoryImpl articuloRepositoryImpl;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public OrdenCompra setDaoRequestToEntity(OrdenCompraRequestDao dao) {
		return OrdenCompraMapper.setDaoRequestToEntity.apply(dao);
	}

	@Override
	public OrdenCompraRequestDao validationRequestUpdate(OrdenCompraRequestDao dao, Integer id) {
		return dao;
	}

	@Override
	public OrdenCompraResponseDao devolverRespuestaDao(OrdenCompra entity) {
		return OrdenCompraMapper.setEntityToDaoResponse.apply(entity);
	}

	@Override
	@Transactional
	public OrdenCompra devolverRespuestaUpdate(OrdenCompra entity, Integer id) {
		OrdenCompra antiguo = (OrdenCompra) genericMethods.findById("OrdenCompra",
				entityManager, id);
		if (antiguo != null) {
			antiguo = antiguo.setValoresDiferentes(antiguo, entity);
		}
		if(entity.getIdArticulo() != null){
			Articulo articulo = antiguo.getIdArticulo();
			if(articulo != null) {
				articulo = articulo.setValoresDiferentes(articulo, entity.getIdArticulo());
			}
			antiguo.setIdArticulo(articulo);
			antiguo.setUpdateEntity(true);
		}
		return antiguo;
	}

	@Override
	public OrdenCompraRequestDao deleteIdOnSave(OrdenCompraRequestDao dao) {
		dao.setId(null);
		return dao;
	}

	@Override
	public OrdenCompra validationRequestEntityOnlyPost(OrdenCompra entity) {
		OrdenCompraValidation ordenCompraValidation = new OrdenCompraValidation(entityManager, entity, articuloRepositoryImpl,false);
		return ordenCompraValidation.getEntity();
	}


	@Override
	public OrdenCompra validationRequestEntityOnlyUpdate(OrdenCompra entity) {
		OrdenCompraValidation ordenCompraValidation = new OrdenCompraValidation(entityManager, entity, articuloRepositoryImpl,true);
		return ordenCompraValidation.getEntity();
	}


	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> findBySearchAndUpdate(String busqueda,
			String sisHabilitado,
			Integer anio,
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
			params.put("anio", anio == null ? "" : anio);

			String sortFieldDefault = "id";
			sortField = sortField == null ? sortFieldDefault : sortField;
			sortAscending = sortAscending == null ? true : sortAscending;

			List<Object> list = this.findWithSkipAndTake(
					this.OrdenCompraRepositoryImpl,
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
