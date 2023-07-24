package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.HistorialLaboralResponseDao;
import com.ec.prontiauto.entidad.HistorialLaboral;
import com.ec.prontiauto.mapper.HistorialLaboralMapper;

@Service
public class HistorialLaboralRepositoryImpl extends AbstractRepository<HistorialLaboral, Integer> {

    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
        String dbQuery = "SELECT e FROM HistorialLaboral e ";
        List<String> listFilters = new ArrayList<>();
        listFilters.add("sisHabilitado");
        listFilters.add("fechaIngreso");
        listFilters.add("idTrabajador");
        listFilters.add("idCargo");
		listFilters.add("id");
        Query queryEM = this.CreateQueryWithFiltersSinAnd(listFilters, params, dbQuery);
        int countReults = queryEM.getResultList().size();
        List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        List<HistorialLaboralResponseDao> listResponseDao = HistorialLaboralMapper.setEntityListToDaoResponseList
                .apply((List<HistorialLaboral>) listResponse);
        return this.getResponse(listResponseDao, countReults);
    }
}
