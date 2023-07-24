package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.RefinanciamientoResponseDao;
import com.ec.prontiauto.entidad.Refinanciamiento;
import com.ec.prontiauto.mapper.RefinanciamientoMapper;

@Service
public class RefinanciamientoRepositoryImpl extends AbstractRepository<Refinanciamiento, Integer> {

    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
        String dbQuery = "SELECT e FROM Refinanciamiento e ";
        List<String> listFilters = new ArrayList<>();
        listFilters.add("sisHabilitado");
        Query queryEM = this.CreateQueryWithFiltersSinAnd(listFilters, params, dbQuery);
        int countReults = queryEM.getResultList().size();
        List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        List<RefinanciamientoResponseDao> listResponseDao = RefinanciamientoMapper.setEntityListToDaoResponseList
                .apply((List<Refinanciamiento>) listResponse);
        return this.getResponse(listResponseDao, countReults);
    }

}
