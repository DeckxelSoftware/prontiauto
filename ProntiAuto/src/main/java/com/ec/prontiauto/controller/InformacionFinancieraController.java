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
import com.ec.prontiauto.dao.InformacionFinancieraRequestDao;
import com.ec.prontiauto.dao.InformacionFinancieraResponseDao;
import com.ec.prontiauto.entidad.InformacionFinanciera;
import com.ec.prontiauto.entidad.Trabajador;
import com.ec.prontiauto.exception.ApiRequestException;
import com.ec.prontiauto.mapper.InformacionFinancieraMapper;
import com.ec.prontiauto.repositoryImpl.InformacionFinancieraRepositoryImpl;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/informacion-financiera")
// @CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST
// })
@Api(tags = "Informacion Financiera", description = "Gestion de Informacion Financiera")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class InformacionFinancieraController extends
		AbstractController<InformacionFinanciera, InformacionFinancieraRequestDao, InformacionFinancieraResponseDao, Integer> {

	private GenericMethods genericMethods = new GenericMethods();

	@Autowired
	private InformacionFinancieraRepositoryImpl informacionFinancieraRepositoryImpl;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public InformacionFinancieraResponseDao devolverRespuestaDao(InformacionFinanciera entity) {
		return InformacionFinancieraMapper.setEntityToDaoResponse.apply(entity);
	}

	@Override
	@Transactional
	public InformacionFinanciera devolverRespuestaUpdate(InformacionFinanciera entity, Integer id) {
		InformacionFinanciera antiguo = (InformacionFinanciera) genericMethods.findById("InformacionFinanciera",
				entityManager, id);
		if (antiguo != null) {
			antiguo = antiguo.setValoresDiferentes(antiguo, entity);
		}
		return antiguo;
	}

	@Override
	public InformacionFinanciera setDaoRequestToEntity(InformacionFinancieraRequestDao dao) {
		return InformacionFinancieraMapper.setDaoRequestToEntity.apply(dao);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> findBySearchAndUpdate(
			Integer idTrabajador,
			String sisHabilitado,
			Integer id,
			Integer skip,
			Integer take,
			String sortField,
			Boolean sortAscending) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("sisHabilitado", sisHabilitado == null ? "" : sisHabilitado);
			Trabajador trabajador = new Trabajador();
			trabajador.setId(idTrabajador);
			params.put("idTrabajador", idTrabajador == null ? "" : trabajador);
			params.put("id", id == null ? "" : id);
			String sortFieldDefault = "id";
			sortField = sortField == null ? sortFieldDefault : sortField;
			sortAscending = sortAscending == null ? true : sortAscending;

			List<Object> list = this.findWithSkipAndTake(
					this.informacionFinancieraRepositoryImpl,
					params,
					skip,
					take,
					sortField,
					sortAscending);

			return new ResponseEntity<>(list, httpHeaders, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("-------------------\n" + e.getMessage());
			throw new ApiRequestException(e.getMessage());
		}
	}
}
