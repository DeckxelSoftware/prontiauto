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
import com.ec.prontiauto.dao.ClienteEnGrupoRequestDao;
import com.ec.prontiauto.dao.ClienteEnGrupoResponseDao;
import com.ec.prontiauto.entidad.Cliente;
import com.ec.prontiauto.entidad.ClienteEnGrupo;
import com.ec.prontiauto.entidad.Grupo;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.mapper.ClienteEnGrupoMapper;
import com.ec.prontiauto.repositoryImpl.ClienteEnGrupoRepositoryImpl;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/cliente-en-grupo")
// @CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST
// })
@Api(tags = "Cliente En Grupo", description = "Gestion de Cliente En Grupo")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class ClienteEnGrupoController
		extends AbstractController<ClienteEnGrupo, ClienteEnGrupoRequestDao, ClienteEnGrupoResponseDao, Integer> {

	private GenericMethods genericMethods = new GenericMethods();

	@Autowired
	private ClienteEnGrupoRepositoryImpl clienteEnGrupoRepository;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public ClienteEnGrupoResponseDao devolverRespuestaDao(ClienteEnGrupo entity) {
		return ClienteEnGrupoMapper.setEntityToDaoResponse.apply(entity);
	}

	@Override
	@Transactional
	public ClienteEnGrupo devolverRespuestaUpdate(ClienteEnGrupo entity, Integer id) {
		ClienteEnGrupo antiguo = (ClienteEnGrupo) genericMethods.findById("ClienteEnGrupo", entityManager, id);
		if (antiguo != null) {
			antiguo = antiguo.setValoresDiferentes(antiguo, entity);
		}
		return antiguo;
	}

	@Override
	public ClienteEnGrupo setDaoRequestToEntity(ClienteEnGrupoRequestDao dao) {
		return ClienteEnGrupoMapper.setDaoRequestToEntity.apply(dao);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> findBySearchAndUpdate(String busqueda,
			String sisHabilitado,
			Integer idGrupo,
			Integer idCliente,
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
			Grupo grupo = new Grupo();
			grupo.setId(idGrupo);
			params.put("idGrupo", idGrupo == null ? "" : grupo);
			Cliente cliente = new Cliente();
			cliente.setId(idCliente);
			params.put("idCliente", idCliente == null ? "" : cliente);
			String sortFieldDefault = "id";
			sortField = sortField == null ? sortFieldDefault : sortField;
			sortAscending = sortAscending == null ? true : sortAscending;

			List<Object> list = this.findWithSkipAndTake(
					this.clienteEnGrupoRepository,
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
