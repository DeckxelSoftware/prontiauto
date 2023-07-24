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
import com.ec.prontiauto.dao.DivisionAdministrativaRequestDao;
import com.ec.prontiauto.dao.DivisionAdministrativaResponseDao;
import com.ec.prontiauto.entidad.DivisionAdministrativa;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.mapper.DivisionAdministrativaMapper;
import com.ec.prontiauto.repositoryImpl.DivisionAdministrativaRepositoryImpl;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/division-administrativa")
// @CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST
// })
@Api(tags = "Division Administrativa", description = "Gestion de Division Administrativa")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class DivisionAdministrativaController extends
		AbstractController<DivisionAdministrativa, DivisionAdministrativaRequestDao, DivisionAdministrativaResponseDao, Integer> {

	private GenericMethods genericMethods = new GenericMethods();

	@Autowired
	private DivisionAdministrativaRepositoryImpl divisionAdministrativaRepositoryImpl;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public DivisionAdministrativaResponseDao devolverRespuestaDao(DivisionAdministrativa entity) {
		return DivisionAdministrativaMapper.setEntityToDaoResponse.apply(entity);
	}

	@Override
	@Transactional
	public DivisionAdministrativa devolverRespuestaUpdate(DivisionAdministrativa entity, Integer id) {
		DivisionAdministrativa antiguo = (DivisionAdministrativa) genericMethods.findById("DivisionAdministrativa",
				entityManager, id);
		if (antiguo != null) {
			antiguo = antiguo.setValoresDiferentes(antiguo, entity);
		}
		return antiguo;
	}

	@Override
	public DivisionAdministrativa setDaoRequestToEntity(DivisionAdministrativaRequestDao dao) {
		return DivisionAdministrativaMapper.setDaoRequestToEntity.apply(dao);
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
					this.divisionAdministrativaRepositoryImpl,
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
