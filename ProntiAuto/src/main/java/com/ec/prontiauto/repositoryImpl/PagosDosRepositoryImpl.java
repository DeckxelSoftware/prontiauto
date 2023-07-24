package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.PagosDosRequestDao;
import com.ec.prontiauto.dao.PagosDosResponseDao;
import com.ec.prontiauto.entidad.PagosDos;
import com.ec.prontiauto.mapper.PagosDosMapper;

@Service
public class PagosDosRepositoryImpl extends AbstractRepository<PagosDos, Integer> {

    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
        String dbQuery = "SELECT e FROM PagosDos e ";
        List<String> listFilters = new ArrayList<>();
        listFilters.add("sisHabilitado");
        listFilters.add("id");

        Query queryEM = this.CreateQueryWithFiltersSinAnd(listFilters, params, dbQuery);
        int countReults = queryEM.getResultList().size();
        List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        List<PagosDosResponseDao> listResponseDao = PagosDosMapper.setEntityListToDaoResponseList
                .apply((List<PagosDos>) listResponse);
        return this.getResponse(listResponseDao, countReults);
    }

    public PagosDos create(PagosDosRequestDao dao) {
        PagosDos entity = null;
        try {
            entity = PagosDosMapper.setDaoRequestToEntity.apply(dao);
            this.save(entity);
        } catch (Exception e) {
            System.out.printf("\n----------\n%s\n", e.getMessage());
            throw new ApiException(e.getMessage());
        }

        return entity;
    }
}
