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
import com.ec.prontiauto.dao.CargoVacacionRequestDao;
import com.ec.prontiauto.dao.CargoVacacionResponseDao;
import com.ec.prontiauto.entidad.CargoVacacion;
import com.ec.prontiauto.entidad.Trabajador;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.mapper.CargoVacacionMapper;
import com.ec.prontiauto.repositoryImpl.CargoVacacionRepositoryImpl;
import com.ec.prontiauto.validations.CargoVacacionValidation;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/cargo-vacacion")
// @CrossOrigingin(origins = "*", methods = { RequestMethod.GET,
// RequestMethod.POST, RequestMethod.PUT })
@Api(tags = "Cargo Vacacion", description = "Gestion Cargo Vacacion")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class CargoVacacionController
		extends AbstractController<CargoVacacion, CargoVacacionRequestDao, CargoVacacionResponseDao, Integer> {

	private GenericMethods genericMethods = new GenericMethods();

	@Autowired
	private CargoVacacionRepositoryImpl cargoVacacionRepositoryImpl;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public CargoVacacionResponseDao devolverRespuestaDao(CargoVacacion entity) {
		return CargoVacacionMapper.setEntityToDaoResponse.apply(entity);
	}

	@Override
	@Transactional
	public CargoVacacion devolverRespuestaUpdate(CargoVacacion entity, Integer id) {
		CargoVacacion antiguo = (CargoVacacion) genericMethods.findById("CargoVacacion",
				entityManager, id);
		if (antiguo != null) {
			antiguo = antiguo.setValoresDiferentes(antiguo, entity);
		}
		return antiguo;
	}

	@Override
	public CargoVacacion setDaoRequestToEntity(CargoVacacionRequestDao dao) {
		return CargoVacacionMapper.setDaoRequestToEntity.apply(dao);
	}

	@Override
	public CargoVacacion validationRequestEntity(CargoVacacion entity) {

		CargoVacacionValidation val = new CargoVacacionValidation(this.entityManager, entity.getIdTrabajador().getId());

		// el total de ingreso al año es ingresado por el frontend
		entity.setValorVacacion(val.valValorDiasVacaciones(entity.getTotalIngresosAnio()));
		// los dias tomados es ingresado por el frontend
		entity.setDiasTomados(val.valDiasTomados(entity.getDiasTomados()));

		entity.setFechaDesde(val.valFechaDesde());
		entity.setFechaHasta(val.valFechaHasta(entity.getFechaDesde()));
		entity.setDiasVacaciones(val.valDiasVacaciones());

		entity.setNumAnioAcumulado(val.valAniosAcumulados());
		entity.setDiasAntiguedad(val.valDiasAntiguedad(entity.getNumAnioAcumulado()));

		entity.setDiasTeoricos(val.valDiasTeoricos(entity.getDiasVacaciones(), entity.getDiasAntiguedad()));
		entity.setDiasSaldo(val.valDiasSaldo(entity.getDiasTeoricos(), entity.getDiasTomados()));
		entity.setValorDias(val.valValorDias(entity.getValorVacacion(), entity.getDiasVacaciones()));
		entity.setValorAntiguedad(val.valAntiguedad(entity.getValorDias(), entity.getDiasAntiguedad()));
		entity.setValorTeorico(val.valValorTeorico(entity.getValorAntiguedad(), entity.getValorVacacion()));
		entity.setValorTomado(val.valValorTomado(entity.getValorDias(), entity.getDiasTomados()));
		entity.setValorSaldo(val.valValorSaldo(entity.getValorTeorico(), entity.getValorTomado()));

		entity.setTotalIngresosAnio(val.valTotalIngresosAnio());
		entity.setIdTrabajador(val.getTrabajador());

		return entity;
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> findBySearchAndUpdate(String busqueda,
			String sisHabilitado,
			Integer idTrabajador,
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

			Trabajador trabajador = new Trabajador();
			trabajador.setId(idTrabajador);
			params.put("idTrabajador", idTrabajador == null ? "" : trabajador);
			params.put("id", id == null ? "" : id);

			String sortFieldDefault = "id";
			sortField = sortField == null ? sortFieldDefault : sortField;
			sortAscending = sortAscending == null ? true : sortAscending;

			List<Object> list = this.findWithSkipAndTake(
					this.cargoVacacionRepositoryImpl,
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
