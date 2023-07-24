
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
import com.ec.prontiauto.dao.SriGastosRequestDao;
import com.ec.prontiauto.dao.SriGastosResponseDao;
import com.ec.prontiauto.entidad.SriGastos;
import com.ec.prontiauto.entidad.Trabajador;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.mapper.SriGastosMapper;
import com.ec.prontiauto.repositoryImpl.SriGastosRepositoryImpl;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/sri-gastos")
@Api(tags = "Sri Gastos", description = "Gestion Sri Gastos")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class SriGastosController
		extends AbstractController<SriGastos, SriGastosRequestDao, SriGastosResponseDao, Integer> {

	private GenericMethods genericMethods = new GenericMethods();

	@Autowired
	private SriGastosRepositoryImpl SriGastosRepositoryImpl;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public SriGastosResponseDao devolverRespuestaDao(SriGastos entity) {
		return SriGastosMapper.setEntityToDaoResponse.apply(entity);
	}

	@Override
	@Transactional
	public SriGastos devolverRespuestaUpdate(SriGastos entity, Integer id) {
		SriGastos antiguo = (SriGastos) genericMethods.findById("SriGastos", entityManager, id);
		if (antiguo != null) {
			antiguo = antiguo.setValoresDiferentes(antiguo, entity);
		}
		return antiguo;
	}

	@Override
	public SriGastos setDaoRequestToEntity(SriGastosRequestDao dao) {
		return SriGastosMapper.setDaoRequestToEntity.apply(dao);
	}

	@Override
	public SriGastosRequestDao deleteIdOnSave(SriGastosRequestDao dao) {
		dao.setId(null);
		return dao;
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> findBySearchAndUpdate(String busqueda,
			String sisHabilitado,
			Integer idTrabajador,
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

			Trabajador trabajador = new Trabajador();
			trabajador.setId(idTrabajador);
			params.put("idTrabajador", idTrabajador == null ? "" : trabajador);
			params.put("id", id == null ? "" : id);

			String sortFieldDefault = "id";
			sortField = sortField == null ? sortFieldDefault : sortField;
			sortAscending = sortAscending == null ? true : sortAscending;

			List<Object> list = this.findWithSkipAndTake(
					this.SriGastosRepositoryImpl,
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
