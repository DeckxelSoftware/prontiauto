
package com.ec.prontiauto.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.codehaus.jackson.map.DeserializationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ec.prontiauto.abstracts.AbstractController;
import com.ec.prontiauto.abstracts.GenericMethods;
import com.ec.prontiauto.dao.ProveedorRequestDao;
import com.ec.prontiauto.dao.ProveedorResponseDao;
import com.ec.prontiauto.entidad.Empresa;
import com.ec.prontiauto.entidad.Proveedor;
import com.ec.prontiauto.exception.ApiRequestException;
import com.ec.prontiauto.entidad.Usuario;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.mapper.ProveedorMapper;
import com.ec.prontiauto.repositoryImpl.EmpresaRepositoryImpl;
import com.ec.prontiauto.repositoryImpl.UsuarioRepositoryImpl;
import com.ec.prontiauto.repositoryImpl.ProveedorRepositoryImpl;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.annotations.Api;

import com.ec.prontiauto.helper.ValidateReqMapper;

@RestController
@RequestMapping("/api/proveedor")
@Api(tags = "Proveedor", description = "Gestion Proveedor")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class ProveedorController
		extends AbstractController<Proveedor, ProveedorRequestDao, ProveedorResponseDao, Integer> {

	private GenericMethods genericMethods = new GenericMethods();

	@Autowired
	private ProveedorRepositoryImpl ProveedorRepositoryImpl;
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private EmpresaRepositoryImpl empresaRepository;

	@Autowired
	private UsuarioRepositoryImpl usuarioRepository;

	@Override
	public ProveedorResponseDao devolverRespuestaDao(Proveedor entity) {
		return ProveedorMapper.setEntityToDaoResponse.apply(entity);
	}

	@Override
	@Transactional
	public Proveedor devolverRespuestaUpdate(Proveedor entity, Integer id) {
		Proveedor antiguo = (Proveedor) genericMethods.findById("Proveedor", entityManager, id);
		if (antiguo != null) {
			antiguo = antiguo.setValoresDiferentes(antiguo, entity);
		}
		return antiguo;
	}

	@Override
	public Proveedor setDaoRequestToEntity(ProveedorRequestDao dao) {
		return ProveedorMapper.setDaoRequestToEntity.apply(dao);
	}

	@Override
	public ProveedorRequestDao deleteIdOnSave(ProveedorRequestDao dao) {
		dao.setId(null);
		return dao;
	}

	public Empresa updateEmpresa(Empresa empresa, Integer id) {
		Empresa empresaAntiguo = (Empresa) genericMethods.findById("Empresa", entityManager, id);
		if (empresaAntiguo != null) {
			empresaAntiguo = empresaAntiguo.setValoresDiferentes(empresaAntiguo, empresa);
		}
		return empresaRepository.updateEmpresa(empresaAntiguo);
	}

	public Usuario updateUsuario(Usuario usuario, Integer id) {
		Usuario usuarioAntiguo = (Usuario) genericMethods.findById("Usuario", entityManager, id);
		if (usuarioAntiguo != null) {
			usuarioAntiguo = usuarioAntiguo.setValoresDiferentes(usuarioAntiguo, usuario);
		}
		return usuarioRepository.updateUser(usuarioAntiguo);
	}

	public Proveedor setDaoRequestToEntityProveedorEmpresa(ProveedorRequestDao dao, boolean isUpdate, Integer id) {

		Proveedor proveedor = isUpdate ? (Proveedor) genericMethods.findById("Proveedor", entityManager, id) : null;

		if (dao.getIdEmpresa() != null && dao.getIdEmpresa() instanceof Object) {
			ObjectMapper mapper = new ObjectMapper();
			Empresa empresa = dao.getIdEmpresa() != null ? mapper.convertValue(dao.getIdEmpresa(), Empresa.class)
					: null;

			if (isUpdate && empresa != null && proveedor.getIdEmpresa() != null) {
				Empresa newEmpresa = this.updateEmpresa(empresa, proveedor.getIdEmpresa().getId());
				dao.setIdEmpresa(newEmpresa.getId());
			} else if (!isUpdate && empresa != null && dao.getIdEmpresa() != null) {

				Empresa newEmpresa = empresaRepository.createEmpresa(empresa);
				dao.setIdEmpresa(newEmpresa);

			} else if (isUpdate && empresa != null && proveedor.getIdEmpresa() == null) {

				Empresa newEmpresa = empresaRepository.createEmpresa(empresa);
				dao.setIdEmpresa(newEmpresa);

			} else {
				dao.setIdEmpresa(empresa.getId());
			}
		}

		return ProveedorMapper.setDaoRequestToEntity.apply(dao);

	}

	public Proveedor setDaoRequestToEntityProveedorUsuario(ProveedorRequestDao dao, boolean isUpdate, Integer id) {


		Proveedor proveedor = isUpdate ? (Proveedor) genericMethods.findById("Proveedor", entityManager, id) : null;

		if (dao.getIdUsuario() != null && dao.getIdUsuario() instanceof Object) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			Usuario usuario = dao.getIdUsuario() != null ? mapper.convertValue(dao.getIdUsuario(), Usuario.class)
					: null;
			if (isUpdate && usuario != null && proveedor.getIdUsuario() != null) {
				Usuario newUsuario = this.updateUsuario(usuario, proveedor.getIdUsuario().getId());
				dao.setIdUsuario(newUsuario.getId());
			} else if (!isUpdate && usuario != null && dao.getIdUsuario() != null) {
				usuario.setSisHabilitado("A");
				Usuario newUsuario = usuarioRepository.createUser(usuario);
				dao.setIdUsuario(newUsuario.getId());
			} else if (isUpdate && usuario != null && proveedor.getIdUsuario() == null) {
				Usuario newUsuario = usuarioRepository.createUser(usuario);
				dao.setIdUsuario(newUsuario.getId());
			} else {
				dao.setIdUsuario(usuario.getId());
			}
		}

		return ProveedorMapper.setDaoRequestToEntity.apply(dao);
	}

	public ProveedorRequestDao validationRequest(ProveedorRequestDao dao) {
		ValidateReqMapper validateReqMapper = new ValidateReqMapper();
		List<String> listParams = new ArrayList<String>();

		if (dao.getTipoProveedor() == null) {
			throw new ApiRequestException("El tipo de proveedor es requerido");
		} else {
			listParams.add("N");
			listParams.add("E");
			listParams.add("P");

			validateReqMapper.setKey(dao.getTipoProveedor());
			validateReqMapper.setParams(listParams);

			if (!this.ValidateFields(validateReqMapper)) {
				throw new ApiRequestException(
						"El campo tipoProveedor es invalido, las opciones disponibles son: " + listParams.toString());
			}

			listParams.clear();
		}

		if (dao.getObligadoLLevarContabilidad() != null) {
			listParams.add("S");
			listParams.add("N");

			validateReqMapper.setKey(dao.getObligadoLLevarContabilidad());
			validateReqMapper.setParams(listParams);

			if (!this.ValidateFields(validateReqMapper)) {
				throw new ApiRequestException(
						"El campo obligadoLlevarContabilidad es invalido, las opciones disponibles son: "
								+ listParams.toString());
			}

			listParams.clear();
		}

		return dao;
	}

	public Boolean ValidateFields(ValidateReqMapper validations) {
		boolean res = false;

		for (int i = 0; i < validations.getParams().size(); i++) {
			if (validations.getKey().equals(validations.getParams().get(i))) {
				res = true;
				break;
			}
		}

		return res;
	}

	@Override
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody ProveedorRequestDao dao) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		try {

			if (dao.getId() != null) {
				dao.setId(null);
			}

			ProveedorRequestDao valDao = this.validationRequest(dao);

			httpHeaders.add("STATUS", "200");
			Proveedor entity = null;

			if (dao.getIdEmpresa() != null) {
				if (dao.getIdEmpresa() instanceof Integer) {
					Empresa empresa = this.entityManager.find(Empresa.class, dao.getIdEmpresa());
					if (empresa != null) {
						dao.setIdEmpresa(empresa);
						entity = ProveedorMapper.setDaoRequestToEntity.apply(dao);
					} else {
						throw new ApiRequestException("La empresa no existe");
					}
				} else {
					entity = this.setDaoRequestToEntityProveedorEmpresa(valDao, false, null);
				}
			}

			if (dao.getIdUsuario() != null) {
				if (dao.getIdUsuario() instanceof Integer) {
					Usuario usuario = this.entityManager.find(Usuario.class, dao.getIdUsuario());
					if (usuario != null) {
						dao.setIdUsuario(usuario);
						entity = ProveedorMapper.setDaoRequestToEntity.apply(dao);
					} else {
						throw new ApiRequestException("El usuario no existe");
					}

				} else {
					entity = this.setDaoRequestToEntityProveedorUsuario(valDao, false, null);
				}
			}

			this.entityManager.persist(entity);

			Object response = devolverRespuestaDao(entity);

			return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("-------------------\n" + e.getMessage());
			throw new ApiRequestException(e.getMessage());
		}
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> findBySearchAndUpdate(String busqueda,
			String sisHabilitado,
			Integer idEmpresa,
			Integer idUsuario,
			String tipoProveedor,
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

			Empresa empresa = new Empresa();
			empresa.setId(idEmpresa);
			params.put("idEmpresa", idEmpresa == null ? "" : empresa);

			Usuario usuario = new Usuario();
			usuario.setId(idUsuario);
			params.put("idUsuario", idUsuario == null ? "" : usuario);

			params.put("id", id == null ? "" : id);
			params.put("tipoProveedor", tipoProveedor == null ? "" : tipoProveedor);

			String sortFieldDefault = "id";
			sortField = sortField == null ? sortFieldDefault : sortField;
			sortAscending = sortAscending == null ? true : sortAscending;

			List<Object> list = this.findWithSkipAndTake(
					this.ProveedorRepositoryImpl,
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

	@Override
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody ProveedorRequestDao dao, @PathVariable("id") Integer id) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		try {
			httpHeaders.add("STATUS", "200");
			Proveedor newEntity = setDaoRequestToEntity(dao);
			Proveedor entity = this.devolverRespuestaUpdate(newEntity, id);

			if (dao.getIdUsuario() != null) {
				Usuario usuario = this.entityManager.find(Usuario.class, dao.getIdUsuario());
				if (usuario != null) {
					entity.setIdUsuario(usuario);
				} else {
					throw new ApiRequestException("El usuario no existe");
				}
			}

			if (dao.getIdEmpresa() != null) {
				Empresa empresa = this.entityManager.find(Empresa.class, dao.getIdEmpresa());
				if (empresa != null) {
					entity.setIdEmpresa(empresa);
				} else {
					throw new ApiRequestException("La empresa no existe");
				}
			}

			this.entityManager.merge(entity);

			return new ResponseEntity<>(devolverRespuestaDao(entity), httpHeaders,
					HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("-------------------\n" + e.getMessage());
			throw new ApiRequestException(e.getMessage());
		}
	}

}
