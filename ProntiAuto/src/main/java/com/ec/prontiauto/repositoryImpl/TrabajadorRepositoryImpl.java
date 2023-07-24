package com.ec.prontiauto.repositoryImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.TrabajadorResponseDao;
import com.ec.prontiauto.entidad.Trabajador;
import com.ec.prontiauto.mapper.TrabajadorMapper;

@Service
public class TrabajadorRepositoryImpl extends AbstractRepository<Trabajador, Integer> {

        @Override
        public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
                String dbQuery = "SELECT DISTINCT (e) FROM Trabajador e, Usuario u WHERE (e.idUsuario = u.id) AND (lower(u.nombres) like lower(:busqueda) OR lower(u.apellidos) like lower(:busqueda) OR lower(u.documentoIdentidad) like lower(:busqueda) OR lower(u.correo) like lower(:busqueda) ) ";
                List<String> listFilters = new ArrayList<>();
                listFilters.add("u.pais");
                listFilters.add("u.provincia");
                listFilters.add("u.ciudad");
                listFilters.add("e.sisHabilitado");
                listFilters.add("e.idUsuario");
                listFilters.add("e.estadoCivil");
                listFilters.add("e.genero");
                listFilters.add("e.grupoSanguineo");
                listFilters.add("e.nivelEstudios");
                listFilters.add("e.profesion");
                listFilters.add("e.estadoFamiliar");
                listFilters.add("e.discapacidad");
                listFilters.add("e.tipoDiscapacidad");
                listFilters.add("e.id");
                Query queryEM = this.CreateQueryWithFilters(listFilters, params, dbQuery)
                                .setParameter("busqueda", "%" + params.get("busqueda") + "%");
                int countReults = queryEM.getResultList().size();
                List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                                .setMaxResults(pageable.getPageSize())
                                .getResultList();
                List<TrabajadorResponseDao> listResponseDao = TrabajadorMapper.setEntityListToDaoReferenceList
                                .apply((List<Trabajador>) listResponse);
                return this.getResponse(listResponseDao, countReults);
        }

        public Object obtenerTrabajadores(Object[][] req, HashMap<String, Object> filters) throws SQLException {
                List<String> params = new ArrayList<>();
                List<Object> values = new ArrayList<>();
                for (int i = 0; i < req.length - 1; i++) {
                        params.add((String) req[i][0]);
                        values.add(req[i][1]);
                }
                return this.callStoreProcedureGet("obtener_trabajadores", params, values, filters);
        }

}
