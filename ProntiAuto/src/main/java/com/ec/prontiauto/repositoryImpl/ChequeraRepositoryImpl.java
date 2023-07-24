package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.ChequeraResponseDao;
import com.ec.prontiauto.entidad.Chequera;
import com.ec.prontiauto.mapper.ChequeraMapper;

@Service
public class ChequeraRepositoryImpl extends AbstractRepository<Chequera, Integer> {

	@Override
	public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
		String dbQuery = "SELECT e FROM Chequera e "
				+ "inner join CuentaBancariaEmpresa cb on cb.id=e.idCuentaBancariaEmpresa "
				+ "WHERE (lower(cb.numeroCuenta) like lower(:busqueda) "
				+ ") ";

		List<String> listFilters = new ArrayList<>();
		listFilters.add("sisHabilitado");
		listFilters.add("fechaEmision");
		listFilters.add("idCuentaBancariaEmpresa");
		Query queryEM = this.CreateQueryWithFilters(listFilters, params, dbQuery)
				.setParameter("busqueda", "%" + params.get("busqueda") + "%");

		int countResults = queryEM.getResultList().size();
		List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
				.setMaxResults(pageable.getPageSize()).getResultList();
		List<ChequeraResponseDao> listResponseDao = ChequeraMapper.setEntityListToDaoResponseList
				.apply((List<Chequera>) listResponse);
		return this.getResponse(listResponseDao, countResults);
	}
}