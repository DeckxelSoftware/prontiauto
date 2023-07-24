package com.ec.prontiauto.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ec.prontiauto.abstracts.GenericMethods;
import com.ec.prontiauto.dao.ConfiguracionGeneralRequestDao;
import com.ec.prontiauto.dao.ConfiguracionGeneralResponseDao;
import com.ec.prontiauto.entidad.ConfiguracionGeneral;
import com.ec.prontiauto.enums.EnumConsulta;
import com.ec.prontiauto.exception.ApiRequestException;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.mapper.ConfiguracionGeneralMapper;
import com.ec.prontiauto.repositoryImpl.ConfiguracionGeneralRepositoryImpl;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/configuracion-general")
@Api(tags = "Configuracion General", description = "Gestion de Configuracion General")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
@Transactional
public class ConfiguracionGeneralController {
	private GenericMethods genericMethods = new GenericMethods();

	@Autowired
	private ConfiguracionGeneralRepositoryImpl configuracionGeneralRepository;

	@PersistenceContext
	private EntityManager entityManager;

	public ConfiguracionGeneralResponseDao devolverRespuestaDao(ConfiguracionGeneral entity) {
		return ConfiguracionGeneralMapper.setEntityToDaoResponse.apply(entity);
	}

	public ConfiguracionGeneral devolverRespuestaUpdate(ConfiguracionGeneral entity, Integer id) {
		ConfiguracionGeneral antiguo = (ConfiguracionGeneral) genericMethods.findById("ConfiguracionGeneral",
				entityManager, id);
		if (antiguo != null) {
			antiguo = antiguo.setValoresDiferentes(antiguo, entity);
		}
		return antiguo;
	}

	public ConfiguracionGeneral setDaoRequestToEntity(ConfiguracionGeneralRequestDao dao) {
		return ConfiguracionGeneralMapper.setDaoRequestToEntity.apply(dao);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody ConfiguracionGeneralRequestDao dao) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		try {
			httpHeaders.add("STATUS", "200");
			boolean exits = this.configuracionGeneralRepository.getExitsData();
			if (exits) {
				throw new ApiRequestException("Ya existe una configuracion general");
			}
			ConfiguracionGeneral newEntity = setDaoRequestToEntity(dao);
			entityManager.persist(newEntity);
			Object response = devolverRespuestaDao(newEntity);
			return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("-------------------\n" + e.getMessage());
			throw new ApiRequestException(e.getMessage());
		}
	}

	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody ConfiguracionGeneralRequestDao dao, @PathVariable("id") Integer id) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		try {
			httpHeaders.add("STATUS", "200");
			ConfiguracionGeneral newEntity = setDaoRequestToEntity(dao);
			ConfiguracionGeneral entity = this.devolverRespuestaUpdate(newEntity, id);
			entityManager.merge(entity);
			return new ResponseEntity<>(devolverRespuestaDao(entity), httpHeaders,
					HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("-------------------\n" + e.getMessage());
			throw new ApiRequestException(e.getMessage());
		}
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> findBySearchAndUpdate(
			Integer skip,
			Integer take,
			String sortField,
			Boolean sortAscending) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		try {
			Map<String, Object> params = new HashMap<>();
			String sortFieldDefault = "id";
			sortField = sortField == null ? sortFieldDefault : sortField;
			sortAscending = sortAscending == null ? true : sortAscending;

			List<Object> list = this.configuracionGeneralRepository.findBySearchAndFilter(params,
					this.getPageRequest(skip, take, sortField, sortAscending));

			return new ResponseEntity<>(list, httpHeaders, HttpStatus.OK);
		} catch (Exception e) {
			httpHeaders.add("STATUS", "400");
			return new ResponseEntity<ExceptionResponse>(new ExceptionResponse("400", e.getMessage()), httpHeaders,
					HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/iva/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateIva(@RequestBody ConfiguracionGeneralRequestDao dao,
			@PathVariable("id") Integer id) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		try {
			httpHeaders.add("STATUS", "200");
			String response = this.configuracionGeneralRepository.actualizarIvaPorcentaje(dao.getIvaPorcentaje(), id);
			return new ResponseEntity<ExceptionResponse>(
					new ExceptionResponse("200", response),
					httpHeaders,
					HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("-------------------\n" + e.getMessage());
			throw new ApiRequestException(e.getMessage());
		}
	}

	public PageRequest getPageRequest(Integer skip, Integer take, String sortField, Boolean sortAscending) {
		return PageRequest.of(skip == null ? EnumConsulta.SKIP.getValor() : skip,
				take == null ? EnumConsulta.TAKE.getValor() : take,
				sortAscending ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
	}
}
