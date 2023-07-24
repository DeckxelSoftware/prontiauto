package com.ec.prontiauto.controller;

import java.sql.Date;
import java.util.HashMap;

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
import com.ec.prontiauto.dao.LicitacionRequestDao;
import com.ec.prontiauto.dao.LicitacionResponseDao;
import com.ec.prontiauto.entidad.Licitacion;
import com.ec.prontiauto.exception.ApiRequestException;
import com.ec.prontiauto.mapper.LicitacionMapper;
import com.ec.prontiauto.repositoryImpl.LicitacionRepositoryImpl;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/licitacion")
// @CrossOrigingin(origins = "*", methods = { RequestMethod.GET,
// RequestMethod.POST, RequestMethod.PUT })
@Api(tags = "Licitacion", description = "Gestion Licitacion")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class LicitacionController
		extends AbstractController<Licitacion, LicitacionRequestDao, LicitacionResponseDao, Integer> {

	private GenericMethods genericMethods = new GenericMethods();

	@Autowired
	private LicitacionRepositoryImpl licitacionRepositoryImpl;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public LicitacionResponseDao devolverRespuestaDao(Licitacion entity) {
		return LicitacionMapper.setEntityToDaoResponse.apply(entity);
	}

	@Override
	@Transactional
	public Licitacion devolverRespuestaUpdate(Licitacion entity, Integer id) {
		Licitacion antiguo = (Licitacion) genericMethods.findById("Licitacion",
				entityManager, id);
		if (antiguo != null) {
			antiguo = antiguo.setValoresDiferentes(antiguo, entity);
		}
		return antiguo;
	}

	@Override
	public Licitacion setDaoRequestToEntity(LicitacionRequestDao dao) {
		return LicitacionMapper.setDaoRequestToEntity.apply(dao);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> findBySearchAndUpdate(String busqueda,
			Integer id,
			String sisHabilitado,
			Date fechaInicio,
			Date fechaFin,
			Integer skip,
			Integer take,
			String sortField,
			Boolean sortAscending) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		try {
			HashMap<String, Object> map = new HashMap<>();
			map.put("id", id);
			map.put("sisHabilitado", sisHabilitado);
			map.put("fechaInicio", fechaInicio);
			map.put("fechaFin", fechaFin);

			Object[][] req = { { "busqueda", busqueda == null ? "" : busqueda }, { "skip", skip == null ? 0 : skip },
					{ "take", take == null ? 10 : take },
					{ "sortField", sortField == null ? "id" : sortField },
					{ "sortAscending", sortAscending == null || sortAscending == true ? "asc" : "desc" },
					{ map } };
			Object response = this.licitacionRepositoryImpl.obtenerLicitaciones(req, map);
			return new ResponseEntity<Object>(response, httpHeaders, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("-------------------\n" + e.getMessage());
			throw new ApiRequestException(e.getMessage());
		}
	}
}
