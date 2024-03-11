package com.ec.prontiauto.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ec.prontiauto.abstracts.AbstractController;
import com.ec.prontiauto.abstracts.GenericMethods;
import com.ec.prontiauto.dao.ClienteRequestDao;
import com.ec.prontiauto.dao.ClienteResponseDao;
import com.ec.prontiauto.entidad.Cliente;
import com.ec.prontiauto.entidad.Empresa;
import com.ec.prontiauto.entidad.Usuario;
import com.ec.prontiauto.exception.ApiRequestException;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.mapper.ClienteMapper;
import com.ec.prontiauto.repositoryImpl.ClienteRepositoryImpl;
import com.ec.prontiauto.repositoryImpl.EmpresaRepositoryImpl;
import com.ec.prontiauto.repositoryImpl.UsuarioRepositoryImpl;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/cliente")
// @CrossOrigingin(origins = "*", methods = { RequestMethod.GET,
// RequestMethod.POST })
@Api(tags = "Cliente", description = "Gestion de Cliente")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class ClienteController extends AbstractController<Cliente, ClienteRequestDao, ClienteResponseDao, Integer> {

	private GenericMethods genericMethods = new GenericMethods();

	@Autowired
	private ClienteRepositoryImpl clienteRepository;
	@Autowired
	private EmpresaRepositoryImpl empresaRepository;
	@Autowired
	private UsuarioRepositoryImpl usuarioRepository;

	@PersistenceContext
	private EntityManager entityManager;

	public ClienteResponseDao devolverRespuestaDao(Cliente entity) {
		return ClienteMapper.setEntityToDaoResponse.apply(entity);
	}

	@Transactional
	public Cliente devolverRespuestaUpdate(Cliente entity, Integer id) {
		Cliente antiguo = (Cliente) genericMethods.findById("Cliente", entityManager,
				id);
		if (antiguo != null) {
			antiguo = antiguo.setValoresDiferentes(antiguo, entity);
		}
		return antiguo;
	}

	public Cliente setDaoRequestToEntityClient(ClienteRequestDao dao, boolean isUpdate, Integer id) {
		Cliente client = isUpdate ? (Cliente) genericMethods.findById("Cliente", entityManager,
				id) : null;
		if (dao.getIdEmpresa() != null && dao.getIdEmpresa() instanceof Object
				&& !(dao.getIdEmpresa() instanceof Integer)) {
			ObjectMapper mapper = new ObjectMapper();
			Empresa empresa = dao.getIdEmpresa() != null ? mapper.convertValue(dao.getIdEmpresa(), Empresa.class)
					: null;
			if (isUpdate && empresa != null && client.getIdEmpresa() != null) {
				Empresa newEmpresa = this.updateEmpresa(empresa, client.getIdEmpresa().getId());
				dao.setIdEmpresa(newEmpresa.getId());
			} else if (!isUpdate && empresa != null && dao.getIdEmpresa() != null) {
				Empresa newEmpresa = empresaRepository.createEmpresa(empresa);
				dao.setIdEmpresa(newEmpresa.getId());
			} else if (isUpdate && empresa != null && client.getIdEmpresa() == null) {
				Empresa newEmpresa = empresaRepository.createEmpresa(empresa);
				dao.setIdEmpresa(newEmpresa.getId());
			} else {
				dao.setIdEmpresa(empresa.getId());
			}
		}
		if (dao.getIdUsuario() != null && dao.getIdUsuario() instanceof Object
				&& !(dao.getIdUsuario() instanceof Integer)) {
			ObjectMapper mapper = new ObjectMapper();
			Usuario usuario = mapper.convertValue(dao.getIdUsuario(), Usuario.class);
			Usuario newUser = isUpdate ? this.updateUser(usuario, client.getIdUsuario().getId())
					: usuarioRepository.createUser(usuario);
			if(!Objects.isNull(usuario.getId())) dao.setIdUsuario(newUser.getId());
		}
		return ClienteMapper.setDaoRequestToEntity.apply(dao);
	}

	public Usuario updateUser(Usuario usuario, Integer id) {
		Usuario usuarioAntiguo = (Usuario) genericMethods.findById("Usuario", entityManager, id);
		if (usuarioAntiguo != null) {
			usuarioAntiguo = usuarioAntiguo.setValoresDiferentes(usuarioAntiguo, usuario);
		}
		return usuarioRepository.updateUser(usuarioAntiguo);
	}

	public Empresa updateEmpresa(Empresa empresa, Integer id) {
		Empresa empresaAntiguo = (Empresa) genericMethods.findById("Empresa", entityManager, id);
		if (empresaAntiguo != null) {
			empresaAntiguo = empresaAntiguo.setValoresDiferentes(empresaAntiguo, empresa);
		}
		return empresaRepository.updateEmpresa(empresaAntiguo);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody ClienteRequestDao dao) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		try {
			httpHeaders.add("STATUS", "200");
			Cliente newEntity = this.setDaoRequestToEntityClient(dao, false, null);
			this.entityManager.persist(newEntity);
			Object response = this.devolverRespuestaDao(newEntity);
			return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("-------------------\n" + e.getCause().getCause().getMessage());
			throw new ApiRequestException(e.getMessage());
		}
	}

	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody ClienteRequestDao dao, @PathVariable("id") Integer id) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		try {
			httpHeaders.add("STATUS", "200");
			Cliente newEntity = this.setDaoRequestToEntityClient(dao, true, id);
			Cliente entity = this.devolverRespuestaUpdate(newEntity, id);
			this.entityManager.merge(entity);
			Cliente newClient = this.clienteRepository.findByIdCliente(id);
			return new ResponseEntity<>(this.devolverRespuestaDao(newClient), httpHeaders,
					HttpStatus.OK);
		} catch (Exception e) {
			throw new ApiRequestException(e.getMessage());
		}
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> findBySearchAndUpdate(String busqueda,
			String tipoCliente,
			String sisHabilitado,
			Integer idUsuario,
			Integer idEmpresa,
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
			params.put("tipoCliente", tipoCliente == null ? "" : tipoCliente);
			Usuario usuario = new Usuario();
			usuario.setId(idUsuario);
			params.put("idUsuario", idUsuario == null ? "" : usuario);
			Empresa empresa = new Empresa();
			empresa.setId(idEmpresa);
			params.put("idEmpresa", idEmpresa == null ? "" : empresa);
			String sortFieldDefault = "id";
			sortField = sortField == null ? sortFieldDefault : sortField;
			sortAscending = sortAscending == null ? true : sortAscending;

			List<Object> list = this.findWithSkipAndTake(
					this.clienteRepository,
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
