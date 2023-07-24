package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.AsientoContableDetAdicionalResponseDao;
import com.ec.prontiauto.entidad.AsientoContableDetAdicional;
import com.ec.prontiauto.mapper.AsientoContableDetAdicionalMapper;

@Service
public class AsientoContableDetAdicionalRepositoryImpl
                extends AbstractRepository<AsientoContableDetAdicional, Integer> {

        @Override
        public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {

                String dbQuery = "SELECT e FROM AsientoContableDetAdicional e WHERE (lower(e.llave) like lower(:busqueda)) ";
                List<String> listFilters = new ArrayList<>();
                listFilters.add("e.sisHabilitado");
                listFilters.add("e.idAsientoContableCabecera");
                Query queryEM = this.CreateQueryWithFiltersAndJoin(listFilters, params, dbQuery)
                                .setParameter("busqueda", "%" + params.get("busqueda") + "%");
                int countResults = queryEM.getResultList().size();
                List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                                .setMaxResults(pageable.getPageSize()).getResultList();
                List<AsientoContableDetAdicionalResponseDao> listResponseDao = AsientoContableDetAdicionalMapper.setEntityListToDaoResponseList
                                .apply((List<AsientoContableDetAdicional>) listResponse);
                return this.getResponse(listResponseDao, countResults);
        }
}
