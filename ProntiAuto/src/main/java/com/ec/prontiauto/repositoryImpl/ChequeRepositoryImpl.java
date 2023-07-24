package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.ChequeResponseDao;
import com.ec.prontiauto.entidad.Cheque;
import com.ec.prontiauto.mapper.ChequeMapper;

@Service
public class ChequeRepositoryImpl extends AbstractRepository<Cheque, Integer> {

	@Override
	public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
		String dbQuery = "SELECT e FROM Cheque e WHERE (lower(cast(e.numeroCheque as string)) like lower(:busqueda)) ";
		List<String> listFilters = new ArrayList<>();
		listFilters.add("sisHabilitado");
		listFilters.add("estadoCheque");
		Query queryEM = this.CreateQueryWithFilters(listFilters, params, dbQuery).setParameter("busqueda",
				"%" + params.get("busqueda") + "%");
		int countResults = queryEM.getResultList().size();
		List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber()).setMaxResults(pageable.getPageSize())
				.getResultList();
		List<ChequeResponseDao> listResponseDao = ChequeMapper.setEntityListToDaoResponseList
				.apply((List<Cheque>) listResponse);

		return this.getResponse(listResponseDao, countResults);
	}
}
