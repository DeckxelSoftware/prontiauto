package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.PeriodoContableResponseDao;
import com.ec.prontiauto.entidad.PeriodoContable;
import com.ec.prontiauto.mapper.PeriodoContableMapper;
import com.ec.prontiauto.helper.ListMapper;

@Service
public class PeriodoContableRepositoryImpl extends AbstractRepository<PeriodoContable, Integer> {

	@Override
	public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
		String dbQuery = "SELECT e FROM PeriodoContable e WHERE (lower(cast(e.anio as string)) like lower(:busqueda)) ";
		List<String> listFilters = new ArrayList<>();
		listFilters.add("sisHabilitado");
		Query queryEM = this.CreateQueryWithFilters(listFilters, params, dbQuery).setParameter("busqueda",
				"%" + params.get("busqueda") + "%");
		int countResults = queryEM.getResultList().size();
		List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber()).setMaxResults(pageable.getPageSize())
				.getResultList();
		List<PeriodoContableResponseDao> listResponseDao = PeriodoContableMapper.setEntityListToDaoResponseList
				.apply((List<PeriodoContable>) listResponse);

		return this.getResponse(listResponseDao, countResults);
	}

	public boolean getExitsData() {
        String dbQuery = "SELECT e FROM PeriodoContable e where e.esPeriodoActual = 'A'";
        return this.existData(dbQuery);
    }

	public boolean getExistYear(Integer anio) {
		String query = "SELECT e FROM PeriodoContable e where e.anio=:anio";
		List<ListMapper<?>> params = new ArrayList<>();
		params.add(new ListMapper("anio", anio));
		
		return this.exitsDataWithParamsGeneric(query, params);
	}
	
}
