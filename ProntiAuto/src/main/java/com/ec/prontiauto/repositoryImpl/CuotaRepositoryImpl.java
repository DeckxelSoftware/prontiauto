package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.CuotaResponseDao;
import com.ec.prontiauto.entidad.Cuota;
import com.ec.prontiauto.mapper.CuotaMapper;

@Service
public class CuotaRepositoryImpl extends AbstractRepository<Cuota, Integer> {

    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
        String dbQuery = "SELECT e FROM Cuota e ";
        List<String> listFilters = new ArrayList<>();
        listFilters.add("sisHabilitado");
        Query queryEM = this.CreateQueryWithFiltersSinAnd(listFilters, params, dbQuery);
        int countReults = queryEM.getResultList().size();
        List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        List<CuotaResponseDao> listResponseDao = CuotaMapper.setEntityListToDaoResponseList
                .apply((List<Cuota>) listResponse);
        return this.getResponse(listResponseDao, countReults);
    }

    public String cuotasEnMora() {
        String[] params = {};
        Object[] values = {};
        return this.callStoreProcedure("cuotas_mora", params, values);
    }

}
