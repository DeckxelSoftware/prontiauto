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
import com.ec.prontiauto.dao.LicitacionResponseDao;
import com.ec.prontiauto.entidad.Licitacion;
import com.ec.prontiauto.mapper.LicitacionMapper;

@Service
public class LicitacionRepositoryImpl extends AbstractRepository<Licitacion, Integer> {

	@Override
	public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {

		String dbQuery = "SELECT e FROM Licitacion e "
				+ "Inner join Contrato ct on ct.id=e.idContrato "
				+ "inner join ClienteEnGrupo cg on cg.id=ct.idClienteEnGrupo "
				+ "inner join Cliente c on c.id=cg.idCliente "
				+ "inner join Usuario u on u.id=c.idUsuario "
				+ "WHERE (lower(cast(ct.numeroDeContrato as string)) like lower(:busqueda) "
				+ "or lower(u.nombres) like lower(:busqueda) "
				+ "or lower(u.apellidos) like lower(:busqueda) "
				+ "or lower(u.documentoIdentidad) like lower(:busqueda) "
				+ "or lower(cast(c.id as string)) like lower(:busqueda) "
				+ ") ";

		List<String> listFilters = new ArrayList<>();
		listFilters.add("sisHabilitado");
		listFilters.add("id");

		Query queryEM = this.CreateQueryWithFilters(listFilters, params, dbQuery).setParameter("busqueda", "%"
				+ params.get("busqueda") + "%");

		int countResults = queryEM.getResultList().size();
		List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber()).setMaxResults(pageable.getPageSize())
				.getResultList();
		List<LicitacionResponseDao> listResponseDao = LicitacionMapper.setEntityListToDaoReferenceList
				.apply((List<Licitacion>) listResponse);

		return this.getResponse(listResponseDao, countResults);
	}

	public Object obtenerLicitaciones(Object[][] req, HashMap<String, Object> filters) throws SQLException {
		List<String> params = new ArrayList<>();
		List<Object> values = new ArrayList<>();
		for (int i = 0; i < req.length - 1; i++) {
			params.add((String) req[i][0]);
			values.add(req[i][1]);
		}
		return this.callStoreProcedureGet("obtener_licitaciones", params, values, filters);
	}
}