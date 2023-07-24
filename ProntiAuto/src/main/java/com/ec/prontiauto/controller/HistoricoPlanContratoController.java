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
import com.ec.prontiauto.dao.HistoricoPlanContratoRequestDao;
import com.ec.prontiauto.dao.HistoricoPlanContratoResponseDao;
import com.ec.prontiauto.entidad.Contrato;
import com.ec.prontiauto.entidad.HistoricoPlanContrato;
import com.ec.prontiauto.exception.ApiRequestException;
import com.ec.prontiauto.mapper.HistoricoPlanContratoMapper;
import com.ec.prontiauto.repositoryImpl.HistoricoPlanContratoRepositoryImpl;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/historico-planAnterior-contrato")
// @CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST
// })
@Api(tags = "Historico Plan Contrato", description = "Gestion de Historico Plan Contrato")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class HistoricoPlanContratoController extends
		AbstractController<HistoricoPlanContrato, HistoricoPlanContratoRequestDao, HistoricoPlanContratoResponseDao, Integer> {

	private GenericMethods genericMethods = new GenericMethods();

	@Autowired
	private HistoricoPlanContratoRepositoryImpl HistoricoPlanContratoRepository;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public HistoricoPlanContratoResponseDao devolverRespuestaDao(HistoricoPlanContrato entity) {
		return HistoricoPlanContratoMapper.setEntityToDaoResponse.apply(entity);
	}

	@Override
	@Transactional
	public HistoricoPlanContrato devolverRespuestaUpdate(HistoricoPlanContrato entity, Integer id) {
		HistoricoPlanContrato antiguo = (HistoricoPlanContrato) genericMethods.findById("HistoricoPlanContrato",
				entityManager, id);
		if (entity != null) {
			entity = entity.setValoresDiferentes(antiguo, entity);
		}
		return entity;
	}

	@Override
	public HistoricoPlanContrato setDaoRequestToEntity(HistoricoPlanContratoRequestDao dao) {
		return HistoricoPlanContratoMapper.setDaoRequestToEntity.apply(dao);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> findBySearchAndUpdate(String busqueda,
			// String ciudad,
			Integer idContrato,
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
			// params.put("ciudad", ciudad == null ? "" : ciudad);
			Contrato contrato = new Contrato();
			contrato.setId(idContrato);
			params.put("idContrato", idContrato == null ? "" : contrato);
			String sortFieldDefault = "id";
			sortField = sortField == null ? sortFieldDefault : sortField;
			sortAscending = sortAscending == null ? true : sortAscending;

			List<Object> list = this.findWithSkipAndTake(
					this.HistoricoPlanContratoRepository,
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
