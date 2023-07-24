package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.RubrosRolResponseDao;
import com.ec.prontiauto.entidad.RubrosRol;
import com.ec.prontiauto.mapper.RubrosRolMapper;

@Service
public class RubrosRolRepositoryImpl extends AbstractRepository<RubrosRol, Integer> {

	@Override
	public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
		String dbQuery = "SELECT e FROM RubrosRol e WHERE (lower(nombre) like lower(:busqueda)) ";
		List<String> listFilters = new ArrayList<>();
		listFilters.add("sisHabilitado");
		listFilters.add("codigoAuxiliar");
		Query queryEM = this.CreateQueryWithFilters(listFilters, params, dbQuery).setParameter("busqueda",
				"%" + params.get("busqueda") + "%");
		int countResults = queryEM.getResultList().size();
		List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber()).setMaxResults(pageable.getPageSize())
				.getResultList();
		List<RubrosRolResponseDao> listResponseDao = RubrosRolMapper.setEntityListToDaoReferenceList
				.apply((List<RubrosRol>) listResponse);

		return this.getResponse(listResponseDao, countResults);
	}
}