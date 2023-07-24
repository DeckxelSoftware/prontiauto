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
import com.ec.prontiauto.dao.AsientoContableDetAdicionalRequestDao;
import com.ec.prontiauto.dao.AsientoContableDetAdicionalResponseDao;
import com.ec.prontiauto.entidad.AsientoContableCabecera;
import com.ec.prontiauto.entidad.AsientoContableDetAdicional;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.mapper.AsientoContableDetAdicionalMapper;
import com.ec.prontiauto.repositoryImpl.AsientoContableDetAdicionalRepositoryImpl;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/asiento-contable-det-adicional")
// @CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST
// })
@Api(tags = "Asiento Contable Det Adicional", description = "Gestion de AsientoContableDetAdicional")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class AsientoContableDetAdicionalController extends
		AbstractController<AsientoContableDetAdicional, AsientoContableDetAdicionalRequestDao, AsientoContableDetAdicionalResponseDao, Integer> {

	private GenericMethods genericMethods = new GenericMethods();

	@Autowired
	private AsientoContableDetAdicionalRepositoryImpl asientoContableDetAdicionalRepositoryImpl;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public AsientoContableDetAdicionalResponseDao devolverRespuestaDao(AsientoContableDetAdicional entity) {
		return AsientoContableDetAdicionalMapper.setEntityToDaoResponse.apply(entity);
	}

	@Override
	@Transactional
	public AsientoContableDetAdicional devolverRespuestaUpdate(AsientoContableDetAdicional entity, Integer id) {
		AsientoContableDetAdicional antiguo = (AsientoContableDetAdicional) genericMethods.findById("AsientoContableDetAdicional",
				entityManager, id);
		if (antiguo != null) {
			antiguo = antiguo.setValoresDiferentes(antiguo, entity);
		}
		return antiguo;
	}

	@Override
	public AsientoContableDetAdicional setDaoRequestToEntity(AsientoContableDetAdicionalRequestDao dao) {
		return AsientoContableDetAdicionalMapper.setDaoRequestToEntity.apply(dao);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> findBySearchAndUpdate(String busqueda,
			String sisHabilitado,
			Integer idAsientoContableCabecera,
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
			AsientoContableCabecera asientoContableCabecera = new AsientoContableCabecera();
			asientoContableCabecera.setId(idAsientoContableCabecera);
			params.put("idAsientoContableCabecera", idAsientoContableCabecera == null ? "" : asientoContableCabecera);
			String sortFieldDefault = "id";
			sortField = sortField == null ? sortFieldDefault : sortField;
			sortAscending = sortAscending == null ? true : sortAscending;

			List<Object> list = this.findWithSkipAndTake(
					this.asientoContableDetAdicionalRepositoryImpl,
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
