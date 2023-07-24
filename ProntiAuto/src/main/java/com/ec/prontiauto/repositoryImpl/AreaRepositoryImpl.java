package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.AreaResponseDao;
import com.ec.prontiauto.entidad.Area;
import com.ec.prontiauto.mapper.AreaMapper;

@Service
public class AreaRepositoryImpl extends AbstractRepository<Area, Integer> {

    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
        String dbQuery = "SELECT e FROM Area e WHERE (lower(cast(e.nombre as string)) like lower(:busqueda)) ";
        List<String> listFilters = new ArrayList<>();
        listFilters.add("sisHabilitado");
        Query queryEM = this.CreateQueryWithFilters(listFilters, params, dbQuery).setParameter("busqueda",
                "%" + params.get("busqueda") + "%");
        int countResults = queryEM.getResultList().size();
        List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber()).setMaxResults(pageable.getPageSize())
                .getResultList();
        List<AreaResponseDao> listResponseDao = AreaMapper.setEntityListToDaoResponseList
                .apply((List<Area>) listResponse);

        return this.getResponse(listResponseDao, countResults);
    }
}
