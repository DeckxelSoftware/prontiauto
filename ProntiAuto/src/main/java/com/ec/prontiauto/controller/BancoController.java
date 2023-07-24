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
import com.ec.prontiauto.dao.BancoRequestDao;
import com.ec.prontiauto.dao.BancoResponseDao;
import com.ec.prontiauto.entidad.Banco;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.mapper.BancoMapper;
import com.ec.prontiauto.repositoryImpl.BancoRepositoryImpl;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/banco")
//@CrossOrigingin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@Api(tags = "Banco", description = "Gestion de Banco")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class BancoController extends AbstractController<Banco, BancoRequestDao, BancoResponseDao, Integer> {

	private GenericMethods genericMethods = new GenericMethods();

	@Autowired
	private BancoRepositoryImpl bancoRepository;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public BancoResponseDao devolverRespuestaDao(Banco entity) {
		return BancoMapper.setEntityToDaoResponse.apply(entity);
	}

	@Override
	@Transactional
	public Banco devolverRespuestaUpdate(Banco entity, Integer id) {
		Banco antiguo = (Banco) genericMethods.findById("Banco", entityManager, id);
		if (antiguo != null) {
			antiguo = antiguo.setValoresDiferentes(antiguo, entity);
		}
		return antiguo;
	}

	@Override
	public Banco setDaoRequestToEntity(BancoRequestDao dao) {
		return BancoMapper.setDaoRequestToEntity.apply(dao);
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
					this.bancoRepository,
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
