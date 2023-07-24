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
import com.ec.prontiauto.dao.CuotaRequestDao;
import com.ec.prontiauto.dao.CuotaResponseDao;
import com.ec.prontiauto.entidad.Cuota;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.mapper.CuotaMapper;
import com.ec.prontiauto.repositoryImpl.CuotaRepositoryImpl;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/cuotas")
// @CrossOrigingin(origins = "*", methods = { RequestMethod.GET,
// RequestMethod.POST, RequestMethod.PUT })
@Api(tags = "Cuota", description = "Gestion Cuota")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class CuotaController
		extends AbstractController<Cuota, CuotaRequestDao, CuotaResponseDao, Integer> {

	private GenericMethods genericMethods = new GenericMethods();

	@Autowired
	private CuotaRepositoryImpl cuotasRepositoryImpl;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public CuotaResponseDao devolverRespuestaDao(Cuota entity) {
		return CuotaMapper.setEntityToDaoResponse.apply(entity);
	}

	@Override
	@Transactional
	public Cuota devolverRespuestaUpdate(Cuota entity, Integer id) {
		Cuota antiguo = (Cuota) genericMethods.findById("Cuota", entityManager, id);
		if (antiguo != null) {
			antiguo = antiguo.setValoresDiferentes(antiguo, entity);
		}
		return antiguo;
	}

	@Override
	public Cuota setDaoRequestToEntity(CuotaRequestDao dao) {
		return CuotaMapper.setDaoRequestToEntity.apply(dao);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> findBySearchAndUpdate(String sisHabilitado,
			Integer skip,
			Integer take, String sortField, Boolean sortAscending) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("sisHabilitado", sisHabilitado == null ? "" : sisHabilitado);
			String sortFieldDefault = "id";
			sortField = sortField == null ? sortFieldDefault : sortField;
			sortAscending = sortAscending == null ? true : sortAscending;

			List<Object> list = this.findWithSkipAndTake(
					this.cuotasRepositoryImpl,
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
