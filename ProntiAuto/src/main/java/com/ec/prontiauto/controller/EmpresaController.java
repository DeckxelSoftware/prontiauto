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
import com.ec.prontiauto.dao.EmpresaRequestDao;
import com.ec.prontiauto.dao.EmpresaResponseDao;
import com.ec.prontiauto.entidad.Empresa;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.mapper.EmpresaMapper;
import com.ec.prontiauto.repositoryImpl.EmpresaRepositoryImpl;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/empresa")
// @CrossOrigingin(origins = "*", methods = { RequestMethod.GET,
// RequestMethod.POST })
@Api(tags = "Empresa", description = "Gestion de Empresa")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class EmpresaController extends AbstractController<Empresa, EmpresaRequestDao, EmpresaResponseDao, Integer> {

	private GenericMethods genericMethods = new GenericMethods();

	@Autowired
	private EmpresaRepositoryImpl empresaRepository;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public EmpresaResponseDao devolverRespuestaDao(Empresa entity) {
		return EmpresaMapper.setEntityToDaoResponse.apply(entity);
	}

	@Override
	@Transactional
	public Empresa devolverRespuestaUpdate(Empresa entity, Integer id) {
		Empresa antiguo = (Empresa) genericMethods.findById("Empresa", entityManager, id);
		if (antiguo != null) {
			antiguo = antiguo.setValoresDiferentes(antiguo, entity);
		}
		return antiguo;
	}

	@Override
	public Empresa setDaoRequestToEntity(EmpresaRequestDao dao) {
		return EmpresaMapper.setDaoRequestToEntity.apply(dao);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> findBySearchAndUpdate(String busqueda,
			String tipoEmpresa,
			String sisHabilitado,
			String obligadoLlevarContabilidad,
			String agenteRetencion,
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
			params.put("tipoEmpresa", tipoEmpresa == null ? "" : tipoEmpresa);
			params.put("obligadoLlevarContabilidad",
					obligadoLlevarContabilidad == null ? "" : obligadoLlevarContabilidad);
			params.put("agenteRetencion", agenteRetencion == null ? "" : agenteRetencion);
			String sortFieldDefault = "id";
			sortField = sortField == null ? sortFieldDefault : sortField;
			sortAscending = sortAscending == null ? true : sortAscending;

			List<Object> list = this.findWithSkipAndTake(
					this.empresaRepository,
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
