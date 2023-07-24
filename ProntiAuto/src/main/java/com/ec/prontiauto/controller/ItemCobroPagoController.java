
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
import com.ec.prontiauto.dao.ItemCobroPagoRequestDao;
import com.ec.prontiauto.dao.ItemCobroPagoResponseDao;
import com.ec.prontiauto.entidad.CuentaContable;
import com.ec.prontiauto.entidad.ItemCobroPago;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.mapper.ItemCobroPagoMapper;
import com.ec.prontiauto.repositoryImpl.ItemCobroPagoRepositoryImpl;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/item-cobro-pago")
@Api(tags = "Item Cobro Pago", description = "Gestion Item Cobro Pago")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class ItemCobroPagoController
		extends AbstractController<ItemCobroPago, ItemCobroPagoRequestDao, ItemCobroPagoResponseDao, Integer> {

	private GenericMethods genericMethods = new GenericMethods();

	@Autowired
	private ItemCobroPagoRepositoryImpl ItemCobroPagoRepositoryImpl;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public ItemCobroPagoResponseDao devolverRespuestaDao(ItemCobroPago entity) {
		return ItemCobroPagoMapper.setEntityToDaoResponse.apply(entity);
	}

	@Override
	@Transactional
	public ItemCobroPago devolverRespuestaUpdate(ItemCobroPago entity, Integer id) {
		ItemCobroPago antiguo = (ItemCobroPago) genericMethods.findById("ItemCobroPago",
				entityManager, id);
		if (antiguo != null) {
			antiguo = antiguo.setValoresDiferentes(antiguo, entity);
		}
		return antiguo;
	}

	@Override
	public ItemCobroPago setDaoRequestToEntity(ItemCobroPagoRequestDao dao) {
		return ItemCobroPagoMapper.setDaoRequestToEntity.apply(dao);
	}
	
	@Override
	public ItemCobroPagoRequestDao deleteIdOnSave(ItemCobroPagoRequestDao dao) {
		dao.setId(null);
		return dao;
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> findBySearchAndUpdate(
            String busqueda,
			String sisHabilitado,
			Integer id,
			Integer idCuentaContable,
			String nombreCuenta,
			String nombreItem,
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
			params.put("id", id == null ? "" : id);
			params.put("nombreCuenta", nombreCuenta == null ? "" : nombreCuenta);
			params.put("nombreItem", nombreItem == null ? "" : nombreItem);

			CuentaContable cuenta = new CuentaContable();
			cuenta.setId(idCuentaContable);
			params.put("idCuentaContable", idCuentaContable == null ? "" : cuenta);
			params.put("id", id == null ? "" : id);

			String sortFieldDefault = "id";
			sortField = sortField == null ? sortFieldDefault : sortField;
			sortAscending = sortAscending == null ? true : sortAscending;

			List<Object> list = this.findWithSkipAndTake(
					this.ItemCobroPagoRepositoryImpl,
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
