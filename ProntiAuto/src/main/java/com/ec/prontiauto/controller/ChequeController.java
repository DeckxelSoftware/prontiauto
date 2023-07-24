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
import com.ec.prontiauto.dao.ChequeRequestDao;
import com.ec.prontiauto.dao.ChequeResponseDao;
import com.ec.prontiauto.entidad.Cheque;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.mapper.ChequeMapper;
import com.ec.prontiauto.repositoryImpl.ChequeRepositoryImpl;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/cheque")
// @CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST
// })
@Api(tags = "Cheque", description = "Gestion de Cheque")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class ChequeController extends
		AbstractController<Cheque, ChequeRequestDao, ChequeResponseDao, Integer> {

	private GenericMethods genericMethods = new GenericMethods();

	@Autowired
	private ChequeRepositoryImpl chequeRepositoryImpl;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public ChequeResponseDao devolverRespuestaDao(Cheque entity) {
		return ChequeMapper.setEntityToDaoResponse.apply(entity);
	}

	@Override
	@Transactional
	public Cheque devolverRespuestaUpdate(Cheque entity, Integer id) {
		Cheque antiguo = (Cheque) genericMethods.findById("Cheque",
				entityManager, id);
		if (antiguo != null) {
			antiguo = antiguo.setValoresDiferentes(antiguo, entity);
		}
		return antiguo;
	}

	@Override
	public Cheque setDaoRequestToEntity(ChequeRequestDao dao) {
		return ChequeMapper.setDaoRequestToEntity.apply(dao);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> findBySearchAndUpdate(String busqueda,
			String sisHabilitado,
			String estadoCheque,
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
			params.put("estadoCheque", estadoCheque == null ? "" : estadoCheque);
			String sortFieldDefault = "id";
			sortField = sortField == null ? sortFieldDefault : sortField;
			sortAscending = sortAscending == null ? true : sortAscending;

			List<Object> list = this.findWithSkipAndTake(
					this.chequeRepositoryImpl,
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
