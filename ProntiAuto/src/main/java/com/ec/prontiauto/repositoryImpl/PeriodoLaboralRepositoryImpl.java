package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.PeriodoLaboralResponseDao;
import com.ec.prontiauto.entidad.PeriodoLaboral;
import com.ec.prontiauto.mapper.PeriodoLaboralMapper;

@Service
public class PeriodoLaboralRepositoryImpl extends AbstractRepository<PeriodoLaboral, Integer> {

	@Override
	public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
		String dbQuery = "SELECT e FROM PeriodoLaboral e WHERE (lower(cast(e.anio as string)) like lower(:busqueda)) ";
		List<String> listFilters = new ArrayList<>();
		listFilters.add("sisHabilitado");
		listFilters.add("activo");
		Query queryEM = this.CreateQueryWithFilters(listFilters, params, dbQuery).setParameter("busqueda",
				"%" + params.get("busqueda") + "%");
		int countResults = queryEM.getResultList().size();
		List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber()).setMaxResults(pageable.getPageSize())
				.getResultList();
		List<PeriodoLaboralResponseDao> listResponseDao = PeriodoLaboralMapper.setEntityListToDaoReferenceList
				.apply((List<PeriodoLaboral>) listResponse);

		return this.getResponse(listResponseDao, countResults);
	}
}