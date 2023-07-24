package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.SriGastosResponseDao;
import com.ec.prontiauto.entidad.SriGastos;
import com.ec.prontiauto.mapper.SriGastosMapper;

@Service
public class SriGastosRepositoryImpl extends AbstractRepository<SriGastos, Integer> {

    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
        String dbQuery = "SELECT e FROM SriGastos e ";
        List<String> listFilters = new ArrayList<>();
        listFilters.add("sisHabilitado");
        listFilters.add("idTrabajador");
        listFilters.add("id");
        listFilters.add("anio");

        Query queryEM = this.CreateQueryWithFiltersSinAnd(listFilters, params, dbQuery);
        int countReults = queryEM.getResultList().size();
        List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        List<SriGastosResponseDao> listResponseDao = SriGastosMapper.setEntityListToDaoResponseList
                .apply((List<SriGastos>) listResponse);
        return this.getResponse(listResponseDao, countReults);
    }
}
