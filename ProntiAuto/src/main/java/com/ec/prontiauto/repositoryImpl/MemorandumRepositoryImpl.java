package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.MemorandumResponseDao;
import com.ec.prontiauto.entidad.Memorandum;
import com.ec.prontiauto.mapper.MemorandumMapper;

@Service
public class MemorandumRepositoryImpl extends AbstractRepository<Memorandum, Integer> {

    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
        String dbQuery = "SELECT e FROM Memorandum e ";
        List<String> listFilters = new ArrayList<>();
        listFilters.add("sisHabilitado");
        listFilters.add("idTrabajador");
		listFilters.add("id");
        Query queryEM = this.CreateQueryWithFiltersSinAnd(listFilters, params, dbQuery);
        int countResults = queryEM.getResultList().size();
        List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize()).getResultList();
        List<MemorandumResponseDao> listResponseDao = MemorandumMapper.setEntityListToDaoResponseList
                .apply((List<Memorandum>) listResponse);
        return this.getResponse(listResponseDao, countResults);
    }

}