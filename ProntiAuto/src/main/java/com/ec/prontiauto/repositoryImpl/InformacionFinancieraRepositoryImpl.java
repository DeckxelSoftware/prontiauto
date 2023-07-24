package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.InformacionFinancieraResponseDao;
import com.ec.prontiauto.entidad.InformacionFinanciera;
import com.ec.prontiauto.mapper.InformacionFinancieraMapper;

@Service
public class InformacionFinancieraRepositoryImpl extends AbstractRepository<InformacionFinanciera, Integer> {

	@Override
	public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
		String dbQuery = "SELECT e FROM InformacionFinanciera e ";
		List<String> listFilters = new ArrayList<>();
		listFilters.add("sisHabilitado");
		listFilters.add("idTrabajador");
		listFilters.add("id");
		Query queryEM = this.CreateQueryWithFiltersSinAnd(listFilters, params, dbQuery);
		int countResults = queryEM.getResultList().size();
		List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber()).setMaxResults(pageable.getPageSize())
				.getResultList();
		List<InformacionFinancieraResponseDao> listResponseDao = InformacionFinancieraMapper.setEntityListToDaoResponseList
				.apply((List<InformacionFinanciera>) listResponse);

		return this.getResponse(listResponseDao, countResults);
	}
}