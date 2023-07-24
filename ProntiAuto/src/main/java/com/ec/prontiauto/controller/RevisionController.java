
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
import com.ec.prontiauto.dao.RevisionRequestDao;
import com.ec.prontiauto.dao.RevisionResponseDao;
import com.ec.prontiauto.entidad.Revision;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.mapper.RevisionMapper;
import com.ec.prontiauto.repositoryImpl.RevisionRepositoryImpl;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/revision")
@Api(tags = "Revision", description = "Gestion Revision")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class RevisionController
		extends AbstractController<Revision, RevisionRequestDao, RevisionResponseDao, Integer> {

	private GenericMethods genericMethods = new GenericMethods();

	@Autowired
	private RevisionRepositoryImpl revisionRepositoryImpl;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public RevisionResponseDao devolverRespuestaDao(Revision entity) {
		return RevisionMapper.setEntityToDaoResponse.apply(entity);
	}

	@Override
	@Transactional
	public Revision devolverRespuestaUpdate(Revision entity, Integer id) {
		Revision antiguo = (Revision) genericMethods.findById("Revision",
				entityManager, id);
		if (antiguo != null) {
			antiguo = antiguo.setValoresDiferentes(antiguo, entity);
		}
		return antiguo;
	}

	@Override
	public Revision setDaoRequestToEntity(RevisionRequestDao dao) {
		return RevisionMapper.setDaoRequestToEntity.apply(dao);
	}
	
	@Override
	public RevisionRequestDao deleteIdOnSave(RevisionRequestDao dao) {
		dao.setId(null);
		return dao;
	}


	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> findBySearchAndUpdate(
			Integer id,
			Integer skip,
			Integer take, 
			String sortField, 
			Boolean sortAscending) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("id", id == null ? "" : id);

			String sortFieldDefault = "id";
			sortField = sortField == null ? sortFieldDefault : sortField;
			sortAscending = sortAscending == null ? true : sortAscending;

			List<Object> list = this.findWithSkipAndTake(
					this.revisionRepositoryImpl,
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
