package com.ec.prontiauto.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;

import javax.transaction.Transactional;

import com.ec.prontiauto.enums.EnumConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

import com.ec.prontiauto.dao.ActualizarFechaCobroContratoDao;
import com.ec.prontiauto.dao.ActualizarMontoContratoDao;
import com.ec.prontiauto.dao.ActualizarPlanContratoDao;
import com.ec.prontiauto.dao.ActualizarPlazoContratoDao;
import com.ec.prontiauto.dao.CesionDerechosContratoDao;
import com.ec.prontiauto.dao.CobroRequestDao;
import com.ec.prontiauto.dao.CrearContratoDao;
import com.ec.prontiauto.dao.DesistirContratoDao;
import com.ec.prontiauto.dao.ReactivacionContratoDao;
import com.ec.prontiauto.dao.RefinanciamientoContratoDao;
import com.ec.prontiauto.dao.UnificacionContratoDao;
import com.ec.prontiauto.exception.ApiRequestException;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.repositoryImpl.ContratoRepositoryImpl;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/contrato")
// @CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST
// })
@Transactional
@Api(tags = "Contrato", description = "Gestion de Contrato")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class ContratoController {

	@Autowired
	private ContratoRepositoryImpl contratoRepository;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> findBySearchAndUpdate(String busqueda,
			Integer id,
			String estado,
			Date fechaInicio,
			Date fechaFin,
			Integer skip,
			Integer take,
			String sortField,
			Boolean sortAscending) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		try {
			httpHeaders.add("STATUS", "200");
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			map.put("estado", estado);
			map.put("fechaInicio", fechaInicio);
			map.put("fechaFin", fechaFin);
			Object[][] req = { { "busqueda", busqueda == null ? "" : busqueda }, { "skip", skip == null ? 0 : skip },
					{ "take", take == null ? 10 : take },
					{ "sortField", sortField == null ? "id" : sortField },
					{ "sortAscending", sortAscending == null || sortAscending == true ? "asc" : "desc" },
					{ map } };
			Object response = this.contratoRepository.obtenerContratos(req, map);
			return new ResponseEntity<Object>(response, httpHeaders, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("-------------------\n" + e.getMessage());
			throw new ApiRequestException(e.getMessage());
		}
	}

	@RequestMapping(value = "/crear", method = RequestMethod.POST)
	public ResponseEntity<?> createContrato(@RequestBody CrearContratoDao dao) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		try {
			httpHeaders.add("STATUS", "200");
			String response = this.contratoRepository.abstractProcedure(dao, "crear_contrato");
			return new ResponseEntity<ExceptionResponse>(
					new ExceptionResponse("200", response),
					httpHeaders,
					HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("-------------------\n" + e.getMessage());
			throw new ApiRequestException(e.getMessage());
		}
	}

	@RequestMapping(value = "/actualizar/plazo", method = RequestMethod.PUT)
	public ResponseEntity<?> actualizarPlazo(@RequestBody ActualizarPlazoContratoDao dao) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		try {
			httpHeaders.add("STATUS", "200");
			String response = this.contratoRepository.abstractProcedure(dao, "actualizar_plazo_contrato");

			return new ResponseEntity<ExceptionResponse>(
					new ExceptionResponse("200", response),
					httpHeaders,
					HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("-------------------\n" + e.getMessage());
			throw new ApiRequestException(e.getMessage());
		}
	}

	@RequestMapping(value = "/actualizar/monto", method = RequestMethod.PUT)
	public ResponseEntity<?> actualizarMonto(@RequestBody ActualizarMontoContratoDao dao) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		try {
			httpHeaders.add("STATUS", "200");
			String response = this.contratoRepository.abstractProcedure(dao, "actualizar_monto_contrato");
			return new ResponseEntity<ExceptionResponse>(
					new ExceptionResponse("200", response),
					httpHeaders,
					HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("-------------------\n" + e.getMessage());
			throw new ApiRequestException(e.getMessage());
		}
	}

	@RequestMapping(value = "/actualizar/plan", method = RequestMethod.PUT)
	public ResponseEntity<?> actualizarPlan(@RequestBody ActualizarPlanContratoDao dao) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		try {
			httpHeaders.add("STATUS", "200");
			String response = this.contratoRepository.abstractProcedure(dao, "actualizar_plan_contrato");
			return new ResponseEntity<ExceptionResponse>(
					new ExceptionResponse("200", response),
					httpHeaders,
					HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("-------------------\n" + e.getMessage());
			throw new ApiRequestException(e.getMessage());
		}
	}

	@RequestMapping(value = "/actualizar/unificacion", method = RequestMethod.PUT)
	public ResponseEntity<?> unificacionContratos(@RequestBody UnificacionContratoDao dao) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		try {
			httpHeaders.add("STATUS", "200");
			String response = this.contratoRepository.abstractProcedure(dao, "unificacion_contrato");
			return new ResponseEntity<ExceptionResponse>(
					new ExceptionResponse("200", response),
					httpHeaders,
					HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("-------------------\n" + e.getMessage());
			throw new ApiRequestException(e.getMessage());
		}
	}

	@RequestMapping(value = "/actualizar/reactivacion", method = RequestMethod.PUT)
	public ResponseEntity<?> reactivacionContrato(@RequestBody ReactivacionContratoDao dao) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		try {
			httpHeaders.add("STATUS", "200");
			String response = this.contratoRepository.abstractProcedure(dao, "reactivacion_contrato");
			return new ResponseEntity<ExceptionResponse>(
					new ExceptionResponse("200", response),
					httpHeaders,
					HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("-------------------\n" + e.getMessage());
			throw new ApiRequestException(e.getMessage());
		}
	}

	@RequestMapping(value = "/actualizar/refinanciamiento", method = RequestMethod.PUT)
	public ResponseEntity<?> refinanciamientoContrato(@RequestBody RefinanciamientoContratoDao dao) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		try {
			httpHeaders.add("STATUS", "200");
			String response = this.contratoRepository.abstractProcedure(dao, "refinanciamiento_contrato");
			return new ResponseEntity<ExceptionResponse>(
					new ExceptionResponse("200", response),
					httpHeaders,
					HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("-------------------\n" + e.getMessage());
			throw new ApiRequestException(e.getMessage());
		}
	}

	@RequestMapping(value = "/actualizar/desistir", method = RequestMethod.PUT)
	public ResponseEntity<?> desistirContrato(@RequestBody DesistirContratoDao dao) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		try {
			httpHeaders.add("STATUS", "200");
			String response = this.contratoRepository.abstractProcedure(dao, "desistir_contrato");
			return new ResponseEntity<ExceptionResponse>(
					new ExceptionResponse("200", response),
					httpHeaders,
					HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("-------------------\n" + e.getMessage());
			throw new ApiRequestException(e.getMessage());
		}
	}

	@RequestMapping(value = "/actualizar/cesion-derechos", method = RequestMethod.PUT)
	public ResponseEntity<?> cesionDerechosContrato(@RequestBody CesionDerechosContratoDao dao) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		try {
			httpHeaders.add("STATUS", "200");
			String response = this.contratoRepository.abstractProcedure(dao, "cesion_derechos_contrato");
			return new ResponseEntity<ExceptionResponse>(
					new ExceptionResponse("200", response),
					httpHeaders,
					HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("-------------------\n" + e.getMessage());
			throw new ApiRequestException(e.getMessage());
		}
	}

	@RequestMapping(value = "/actualizar/valor-fondo/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> actualizarFondoContrato(@PathVariable("id") Integer id) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		try {
			httpHeaders.add("STATUS", "200");
			String response = this.contratoRepository.actualizarFondoContrato(id);
			return new ResponseEntity<ExceptionResponse>(
					new ExceptionResponse("200", response),
					httpHeaders,
					HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("-------------------\n" + e.getMessage());
			throw new ApiRequestException(e.getMessage());
		}
	}

	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> actualizarContrato(@RequestBody CrearContratoDao dao, @PathVariable("id") Integer id) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		try {
			httpHeaders.add("STATUS", "200");
			String response = this.contratoRepository.actualizarContrato(dao, id);
			return new ResponseEntity<ExceptionResponse>(
					new ExceptionResponse("200", response),
					httpHeaders,
					HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("-------------------\n" + e.getMessage());
			throw new ApiRequestException(e.getMessage());
		}
	}

	@RequestMapping(value = "/actualizar/fecha-inicio-contrato", method = RequestMethod.PUT)
	public ResponseEntity<?> actualizarContrato(@RequestBody ActualizarFechaCobroContratoDao dao) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		try {
			httpHeaders.add("STATUS", "200");
			String response = this.contratoRepository.abstractProcedure(dao, "atualizar_fecha_inicia_cobro_contrato");
			return new ResponseEntity<ExceptionResponse>(
					new ExceptionResponse("200", response),
					httpHeaders,
					HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("-------------------\n" + e.getMessage());
			throw new ApiRequestException(e.getMessage());
		}
	}

	@RequestMapping(value = "/actualizar/liquidar", method = RequestMethod.PUT)
	public ResponseEntity<?> actualizarContrato(CobroRequestDao dao) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		try {
			httpHeaders.add("STATUS", "200");
			String response = this.contratoRepository.abstractProcedure(dao, "liquidar_contrato");
			return new ResponseEntity<ExceptionResponse>(
					new ExceptionResponse("200", response),
					httpHeaders,
					HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("-------------------\n" + e.getMessage());
			throw new ApiRequestException(e.getMessage());
		}
	}


	@RequestMapping(value = "/asamblea", method = RequestMethod.GET)
	public ResponseEntity<?> obtenerReporteAsamblea(String fecha) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		Object response;
		try {
			response = this.contratoRepository.generarReporteAsamblea(fecha);
		} catch (SQLException e) {
			throw new ApiRequestException(e.getMessage());
		}
		return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
	}

	public PageRequest getPageRequest(Integer skip, Integer take, String sortField, Boolean sortAscending) {
		return PageRequest.of(skip == null ? EnumConsulta.SKIP.getValor() : skip,
				take == null ? EnumConsulta.TAKE.getValor() : take,
				sortAscending ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
	}
}
