package com.ec.prontiauto.repositoryImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.CrearContratoDao;
import com.ec.prontiauto.entidad.Contrato;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Service
public class ContratoRepositoryImpl extends AbstractRepository<Contrato, Integer> {

	@Override
	public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
		return new ArrayList<>();
	}

	public <T> String abstractProcedure(T dao, String method) {
		Gson gson = new Gson();
		String jsonObject = gson.toJson(dao);
		JsonObject json = gson.fromJson(jsonObject, JsonObject.class);
		String[] params = { "json" };
		Object[] values = { gson.toJson(json) };
		return this.callStoreProcedure(method, params, values);
	}

	public Object obtenerContratos(Object[][] req, HashMap<String, Object> filters) throws SQLException {
		List<String> params = new ArrayList<>();
		List<Object> values = new ArrayList<>();
		for (int i = 0; i < req.length - 1; i++) {
			params.add((String) req[i][0]);
			values.add(req[i][1]);
		}
		return this.callStoreProcedureGet("obtener_contratos", params, values, filters);
	}

	public String actualizarContrato(CrearContratoDao dao, Integer idContrato) {
		Gson gson = new Gson();
		String jsonObject = gson.toJson(dao);
		JsonObject json = gson.fromJson(jsonObject, JsonObject.class);
		json.addProperty("idContrato", idContrato);
		String[] params = { "json" };
		Object[] values = { gson.toJson(json) };
		return this.callStoreProcedure("actualizar_contrato", params, values);
	}

	public String actualizarFondoContrato(Integer idContrato) {
		String[] params = { "id_contrato" };
		Object[] values = { idContrato };
		return this.callStoreProcedure("actualizar_fondo_contrato", params, values);
	}

	public Object generarReporteAsamblea(String fechaInicio) throws SQLException {
		return this.callSimpleStoreProcedure("generar_reporte_asamblea", Collections.singletonList("fecha_inicio"), Collections.singletonList(fechaInicio));
	}
}
