package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import com.ec.prontiauto.dao.CuentaContableResponseDao;
import com.ec.prontiauto.entidad.CuentaContable;
import com.ec.prontiauto.mapper.CuentaContableMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.CuentaContableResponseDao;
import com.ec.prontiauto.entidad.CuentaContable;
import com.ec.prontiauto.mapper.CuentaContableMapper;

@Service
public class CuentaContableRepositoryImpl extends AbstractRepository<CuentaContable, Integer> {

	@Override
	public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {

		String dbQuery = "SELECT DISTINCT (e) "+
				"FROM CuentaContable e " +
				"INNER JOIN PeriodoContable p " +
				"ON e.idPeriodoContable=p.id " +
				"WHERE lower(e.nombre) like lower(:busqueda) ";

		List<String> listFilters = new ArrayList<>();

		listFilters.add("e.nivel");
		listFilters.add("e.sisHabilitado");
		listFilters.add("e.nombre");
		listFilters.add("p.anio");
		listFilters.add("e.identificador");
		listFilters.add("e.id");

		Query queryEM = this.CreateQueryWithFiltersAndJoin(listFilters, params, dbQuery)
				.setParameter("busqueda", "%" + params.get("busqueda") + "%");
		int countReults = queryEM.getResultList().size();
		List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
				.setMaxResults(pageable.getPageSize())
				.getResultList();
		List<CuentaContableResponseDao> listResponseDao = CuentaContableMapper.setEntityListToDaoResponseList
				.apply((List<CuentaContable>) listResponse);
		return this.getResponse(listResponseDao, countReults);
	}
}
