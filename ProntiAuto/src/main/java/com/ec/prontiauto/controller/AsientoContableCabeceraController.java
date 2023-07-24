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
import com.ec.prontiauto.dao.AsientoContableCabeceraRequestDao;
import com.ec.prontiauto.dao.AsientoContableCabeceraResponseDao;
import com.ec.prontiauto.entidad.AsientoContableCabecera;
import com.ec.prontiauto.exception.ApiRequestException;
import com.ec.prontiauto.mapper.AsientoContableCabeceraMapper;
import com.ec.prontiauto.repositoryImpl.AsientoContableCabeceraRepositoryImpl;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/asiento-contable-cabecera")
// @CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST
// })
@Api(tags = "Asiento Contable Cabecera", description = "Gestion de Asiento Contable Cabecera")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class AsientoContableCabeceraController extends
		AbstractController<AsientoContableCabecera, AsientoContableCabeceraRequestDao, AsientoContableCabeceraResponseDao, Integer> {

	private GenericMethods genericMethods = new GenericMethods();

	@Autowired
	private AsientoContableCabeceraRepositoryImpl asientoContableCabeceraRepositoryImpl;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public AsientoContableCabeceraResponseDao devolverRespuestaDao(AsientoContableCabecera entity) {
		return AsientoContableCabeceraMapper.setEntityToDaoResponse.apply(entity);
	}

	@Override
	@Transactional
	public AsientoContableCabecera devolverRespuestaUpdate(AsientoContableCabecera entity, Integer id) {
		AsientoContableCabecera antiguo = (AsientoContableCabecera) genericMethods.findById("AsientoContableCabecera",
				entityManager, id);
		if (entity != null) {
			entity = entity.setValoresDiferentes(antiguo, entity);
		}
		return entity;
	}

	@Override
	public AsientoContableCabecera setDaoRequestToEntity(AsientoContableCabeceraRequestDao dao) {
		return AsientoContableCabeceraMapper.setDaoRequestToEntity.apply(dao);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> findBySearchAndUpdate(String busqueda,
			String sisHabilitado,
			Integer id,
			String tipoTransaccion,
			String tipoAsientoContable,
			String asientoCerrado,
			Integer idSubgrupoContable,
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
			params.put("tipoTransaccion", tipoTransaccion == null ? "" : tipoTransaccion);
			params.put("tipoAsientoContable", tipoAsientoContable == null ? "" : tipoAsientoContable);
			params.put("asientoCerrado", asientoCerrado == null ? "" : asientoCerrado);
			params.put("id", id == null ? "" : id);

//			SubgrupoContable subgrupoContable = new SubgrupoContable();
//			subgrupoContable.setId(idSubgrupoContable);
//			params.put("idSubgrupoContable", idSubgrupoContable == null ? "" : subgrupoContable);

			String sortFieldDefault = "id";
			sortField = sortField == null ? sortFieldDefault : sortField;
			sortAscending = sortAscending == null ? true : sortAscending;

			List<Object> list = this.findWithSkipAndTake(
					this.asientoContableCabeceraRepositoryImpl,
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
