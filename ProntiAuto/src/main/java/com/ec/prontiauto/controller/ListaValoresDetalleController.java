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
import com.ec.prontiauto.dao.ListaValoresDetalleRequestDao;
import com.ec.prontiauto.dao.ListaValoresDetalleResponseDao;
import com.ec.prontiauto.entidad.ListaValoresDetalle;
import com.ec.prontiauto.entidad.ListaValoresTipo;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.mapper.ListaValoresDetalleMapper;
import com.ec.prontiauto.repositoryImpl.ListaValoresDetalleRespositoryImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/lista-valores-detalle")
//@CrossOrigingin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
@Api(tags = "Lista Valores Detalle", description = "Gestion de lista valores detalle")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class ListaValoresDetalleController
		extends
		AbstractController<ListaValoresDetalle, ListaValoresDetalleRequestDao, ListaValoresDetalleResponseDao, Integer> {

	private GenericMethods genericMethods = new GenericMethods();

	@Autowired
	private ListaValoresDetalleRespositoryImpl listaValoresDetalleRespository;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public ListaValoresDetalleResponseDao devolverRespuestaDao(ListaValoresDetalle entity) {
		return ListaValoresDetalleMapper.setEntityToDaoResponse.apply(entity);
	}

	@Override
	@Transactional
	public ListaValoresDetalle devolverRespuestaUpdate(ListaValoresDetalle entity, Integer id) {
		ListaValoresDetalle antiguo = (ListaValoresDetalle) genericMethods.findById("ListaValoresDetalle",
				entityManager, id);
		if (antiguo != null) {
			antiguo = antiguo.setValoresDiferentes(antiguo, entity);
		}
		return antiguo;
	}

	@Override
	public ListaValoresDetalle setDaoRequestToEntity(ListaValoresDetalleRequestDao dao) {
		return ListaValoresDetalleMapper.setDaoRequestToEntity.apply(dao);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	@ApiOperation(tags = "Lista Valores Detalle", value = "buscar por codigo primario, codigo secundario y filtrar por sisHabilitado, lista valores tipo")
	public ResponseEntity<?> findBySearchAndUpdate(String busqueda,
			Integer idListaValoresTipo, String idListaValoresTipoCodigoPrimario, String sisHabilitado,
			Integer skip,
			Integer take, String sortField, Boolean sortAscending) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		try {

			Map<String, Object> params = new HashMap<>();
			params.put("busqueda", busqueda == null ? "" : busqueda);
			params.put("sisHabilitado", sisHabilitado == null ? "" : sisHabilitado);
			params.put("codigoPrimario",
					idListaValoresTipoCodigoPrimario == null ? "" : idListaValoresTipoCodigoPrimario);
			ListaValoresTipo listaValoresTipo = new ListaValoresTipo();
			listaValoresTipo.setId(idListaValoresTipo);
			params.put("idListaValoresTipo", idListaValoresTipo == null ? "" : listaValoresTipo);
			String sortFieldDefault = "id";
			sortField = sortField == null ? sortFieldDefault : sortField;
			sortAscending = sortAscending == null ? true : sortAscending;

			List<Object> list = this.findWithSkipAndTake(
					this.listaValoresDetalleRespository,
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
