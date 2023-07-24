package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.DivisionAdministrativaResponseDao;
import com.ec.prontiauto.entidad.DivisionAdministrativa;
import com.ec.prontiauto.mapper.DivisionAdministrativaMapper;

@Service
public class DivisionAdministrativaRepositoryImpl extends AbstractRepository<DivisionAdministrativa, Integer> {

	@Override
	public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
		String dbQuery = "SELECT e FROM DivisionAdministrativa e WHERE (lower(nombreDivision) Like lower (:busqueda)) ";
		List<String> listFilters = new ArrayList<>();
		listFilters.add("sisHabilitado");
		Query queryEM = this.CreateQueryWithFilters(listFilters, params, dbQuery).setParameter("busqueda",
				"%" + params.get("busqueda") + "%");
		int countResults = queryEM.getResultList().size();
		List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber()).setMaxResults(pageable.getPageSize())
				.getResultList();
		List<DivisionAdministrativaResponseDao> listResponseDao = DivisionAdministrativaMapper.setEntityListToDaoReferenceList
				.apply((List<DivisionAdministrativa>) listResponse);

		return this.getResponse(listResponseDao, countResults);
	}
}