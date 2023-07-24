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
import com.ec.prontiauto.dao.CuentaBancariaEmpresaRequestDao;
import com.ec.prontiauto.dao.CuentaBancariaEmpresaResponseDao;
import com.ec.prontiauto.entidad.Banco;
import com.ec.prontiauto.entidad.CuentaBancariaEmpresa;
import com.ec.prontiauto.entidad.Empresa;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.mapper.CuentaBancariaEmpresaMapper;
import com.ec.prontiauto.repositoryImpl.CuentaBancariaEmpresaRepositoryImpl;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/cuenta-bancaria-empresa")
//@CrossOrigingin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@Api(tags = "Cuenta Bancaria Empresa", description = "Gestion de Cuenta Bancaria Empresa")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class CuentaBancariaEmpresaController extends
		AbstractController<CuentaBancariaEmpresa, CuentaBancariaEmpresaRequestDao, CuentaBancariaEmpresaResponseDao, Integer> {

	private GenericMethods genericMethods = new GenericMethods();

	@Autowired
	private CuentaBancariaEmpresaRepositoryImpl cuentaBancariaEmpresaRepository;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public CuentaBancariaEmpresaResponseDao devolverRespuestaDao(CuentaBancariaEmpresa entity) {
		return CuentaBancariaEmpresaMapper.setEntityToDaoResponse.apply(entity);
	}

	@Override
	@Transactional
	public CuentaBancariaEmpresa devolverRespuestaUpdate(CuentaBancariaEmpresa entity, Integer id) {
		CuentaBancariaEmpresa antiguo = (CuentaBancariaEmpresa) genericMethods.findById("CuentaBancariaEmpresa",
				entityManager, id);
		if (antiguo != null) {
			antiguo = antiguo.setValoresDiferentes(antiguo, entity);
		}
		return antiguo;
	}

	@Override
	public CuentaBancariaEmpresa setDaoRequestToEntity(CuentaBancariaEmpresaRequestDao dao) {
		return CuentaBancariaEmpresaMapper.setDaoRequestToEntity.apply(dao);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> findBySearchAndUpdate(String busqueda,
			String tipoCuenta,
			String idEmpresaRucEmpresa,
			Integer idEmpresa,
			Integer idBanco,
			Integer id,
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
			params.put("id", id == null ? "" : id);
			params.put("sisHabilitado", sisHabilitado == null ? "" : sisHabilitado);
			params.put("tipoCuenta", tipoCuenta == null ? "" : tipoCuenta);
			params.put("rucEmpresa", idEmpresaRucEmpresa == null ? "" : idEmpresaRucEmpresa);
			Empresa empresa = new Empresa();
			empresa.setId(idEmpresa);
			params.put("idEmpresa", idEmpresa == null ? "" : empresa);
			Banco banco = new Banco();
			banco.setId(idBanco);
			params.put("idBanco", idBanco == null ? "" : banco);
			String sortFieldDefault = "id";
			sortField = sortField == null ? sortFieldDefault : sortField;
			sortAscending = sortAscending == null ? true : sortAscending;
			List<Object> list = this.findWithSkipAndTake(
					this.cuentaBancariaEmpresaRepository,
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
