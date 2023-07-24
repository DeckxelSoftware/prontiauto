package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.PrestamoResponseDao;
import com.ec.prontiauto.entidad.Prestamo;
import com.ec.prontiauto.mapper.PrestamoMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Service
public class PrestamoRepositoryImpl extends AbstractRepository<Prestamo, Integer> {

        @Override
        public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
                String dbQuery = "SELECT DISTINCT (e) FROM Prestamo e "
                                + "INNER JOIN Trabajador t on t.id=e.idTrabajador "
                                + "INNER JOIN Usuario u on u.id=t.idUsuario "
                                + "WHERE (lower(u.nombres) LIKE lower(:busqueda) "
                                + "OR lower(u.apellidos) LIKE lower(:busqueda) "
                                + "OR lower(u.documentoIdentidad) LIKE lower(:busqueda) "
                                + ")";
                List<String> listFilters = new ArrayList<>();
                listFilters.add("e.sisHabilitado");
                listFilters.add("e.id");
                listFilters.add("e.idTrabajador");
                listFilters.add("t.idUsuario");
                listFilters.add("e.fechaPrestamo");
                listFilters.add("e.tipoPrestamo");
                listFilters.add("e.estadoSolicitud");
                listFilters.add("e.modalidadDescuento");
                Query queryEM = this.CreateQueryWithFiltersAndJoin(listFilters, params, dbQuery).setParameter(
                                "busqueda",
                                "%" + params.get("busqueda") + "%");
                int countResults = queryEM.getResultList().size();
                List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                                .setMaxResults(pageable.getPageSize()).getResultList();
                List<PrestamoResponseDao> listResponseDao = PrestamoMapper.setEntityListToDaoResponseList
                                .apply((List<Prestamo>) listResponse);

                return this.getResponse(listResponseDao, countResults);
        }

        public <T> String abstractProcedure(T dao, String method) {
		Gson gson = new Gson();
		String jsonObject = gson.toJson(dao);
		JsonObject json = gson.fromJson(jsonObject, JsonObject.class);
		String[] params = { "json" };
		Object[] values = { gson.toJson(json) };
		return this.callStoreProcedure(method, params, values);
	}

}