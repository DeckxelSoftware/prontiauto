package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.RegistroVacacionResponseDao;
import com.ec.prontiauto.entidad.RegistroVacacion;
import com.ec.prontiauto.mapper.RegistroVacacionMapper;

@Service
public class RegistroVacacionRepositoryImpl extends AbstractRepository<RegistroVacacion, Integer> {

    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
        String dbQuery = "SELECT e FROM RegistroVacacion e ";
        List<String> listFilters = new ArrayList<>();
        listFilters.add("sisHabilitado");
        listFilters.add("idCargoVacacion");
		listFilters.add("id");
        Query queryEM = this.CreateQueryWithFiltersSinAnd(listFilters, params, dbQuery);
        int countReults = queryEM.getResultList().size();
        List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        List<RegistroVacacionResponseDao> listResponseDao = RegistroVacacionMapper.setEntityListToDaoResponseList
                .apply((List<RegistroVacacion>) listResponse);
        return this.getResponse(listResponseDao, countReults);
    }
}
