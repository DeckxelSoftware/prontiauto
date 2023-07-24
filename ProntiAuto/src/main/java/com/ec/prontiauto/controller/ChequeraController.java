package com.ec.prontiauto.controller;

import java.sql.Date;
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
import com.ec.prontiauto.dao.ChequeraRequestDao;
import com.ec.prontiauto.dao.ChequeraResponseDao;
import com.ec.prontiauto.entidad.Chequera;
import com.ec.prontiauto.entidad.CuentaBancariaEmpresa;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.mapper.ChequeraMapper;
import com.ec.prontiauto.repositoryImpl.ChequeraRepositoryImpl;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/chequera")
// @CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST
// })
@Api(tags = "Chequera", description = "Gestion de Chequera")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class ChequeraController extends
		AbstractController<Chequera, ChequeraRequestDao, ChequeraResponseDao, Integer> {

	private GenericMethods genericMethods = new GenericMethods();

	@Autowired
	private ChequeraRepositoryImpl chequeraRepositoryImpl;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public ChequeraResponseDao devolverRespuestaDao(Chequera entity) {
		return ChequeraMapper.setEntityToDaoResponse.apply(entity);
	}

	@Override
	@Transactional
	public Chequera devolverRespuestaUpdate(Chequera entity, Integer id) {
		Chequera antiguo = (Chequera) genericMethods.findById("Chequera",
				entityManager, id);
		if (antiguo != null) {
			antiguo = antiguo.setValoresDiferentes(antiguo, entity);
		}
		return antiguo;
	}

	@Override
	public Chequera setDaoRequestToEntity(ChequeraRequestDao dao) {
		return ChequeraMapper.setDaoRequestToEntity.apply(dao);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> findBySearchAndUpdate(String busqueda,
			String sisHabilitado,
			Integer idCuentaBancariaEmpresa,
			Date fechaEmision,
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
			params.put("fechaEmision", fechaEmision == null ? "" : fechaEmision);
			CuentaBancariaEmpresa cuentaBancariaEmpresa = new CuentaBancariaEmpresa();
			cuentaBancariaEmpresa.setId(idCuentaBancariaEmpresa);
			params.put("idCuentaBancariaEmpresa", idCuentaBancariaEmpresa == null ? "" : cuentaBancariaEmpresa);

			String sortFieldDefault = "id";
			sortField = sortField == null ? sortFieldDefault : sortField;
			sortAscending = sortAscending == null ? true : sortAscending;

			List<Object> list = this.findWithSkipAndTake(
					this.chequeraRepositoryImpl,
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
