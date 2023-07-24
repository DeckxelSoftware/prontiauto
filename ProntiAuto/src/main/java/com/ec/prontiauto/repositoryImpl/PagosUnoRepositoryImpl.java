package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.PagosUnoResponseDao;
import com.ec.prontiauto.entidad.PagosUno;
import com.ec.prontiauto.mapper.PagosUnoMapper;

@Service
public class PagosUnoRepositoryImpl extends AbstractRepository<PagosUno, Integer> {

    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
        String dbQuery = "SELECT e FROM PagosUno e ";
        List<String> listFilters = new ArrayList<>();
        listFilters.add("sisHabilitado");
        listFilters.add("id");

        Query queryEM = this.CreateQueryWithFiltersSinAnd(listFilters, params, dbQuery);
        int countReults = queryEM.getResultList().size();
        List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        List<PagosUnoResponseDao> listResponseDao = PagosUnoMapper.setEntityListToDaoResponseList
                .apply((List<PagosUno>) listResponse);
        return this.getResponse(listResponseDao, countReults);
    }

}
