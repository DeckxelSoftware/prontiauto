package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.RevisionResponseDao;
import com.ec.prontiauto.entidad.Revision;
import com.ec.prontiauto.mapper.RevisionMapper;

@Service
public class RevisionRepositoryImpl extends AbstractRepository<Revision, Integer> {

    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
        String dbQuery = "SELECT e FROM Revision e ";
        List<String> listFilters = new ArrayList<>();
        listFilters.add("id");

        Query queryEM = this.CreateQueryWithFiltersSinAnd(listFilters, params, dbQuery);
        int countReults = queryEM.getResultList().size();
        List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        List<RevisionResponseDao> listResponseDao = RevisionMapper.setEntityListToDaoResponseList
                .apply((List<Revision>) listResponse);
        return this.getResponse(listResponseDao, countReults);
    }
}
