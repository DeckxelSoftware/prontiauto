package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.TransaccionAsientoContableResponseDao;
import com.ec.prontiauto.entidad.TransaccionAsientoContable;
import com.ec.prontiauto.mapper.TransaccionAsientoContableMapper;

@Service
public class TransaccionAsientoContableRepositoryImpl extends AbstractRepository<TransaccionAsientoContable, Integer> {


    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
    	
    	 String dbQuery = "SELECT e FROM TransaccionAsientoContable e WHERE (lower(e.detalle) like lower(:busqueda)) ";
        List<String> listFilters = new ArrayList<>();
        listFilters.add("e.sisHabilitado");
        listFilters.add("e.idAsientoContableCabecera");
        Query queryEM = this.CreateQueryWithFiltersAndJoin(listFilters, params, dbQuery)
                .setParameter("busqueda", "%" + params.get("busqueda") + "%");
        int countResults = queryEM.getResultList().size();
        List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize()).getResultList();
        List<TransaccionAsientoContableResponseDao> listResponseDao = TransaccionAsientoContableMapper.setEntityListToDaoResponseList
                .apply((List<TransaccionAsientoContable>) listResponse);
        return this.getResponse(listResponseDao, countResults);
    }
}
