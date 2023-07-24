package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.RegionResponseDao;
import com.ec.prontiauto.entidad.Region;
import com.ec.prontiauto.mapper.RegionMapper;

@Service
public class RegionRepositoryImpl extends AbstractRepository<Region, Integer> {

        @Override
        public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
                String dbQuery = "SELECT e FROM Region e WHERE lower(e.nombre) LIKE lower(:busqueda)";
                List<String> listFilters = new ArrayList<>();
                listFilters.add("sisHabilitado");
                listFilters.add("id");
                Query queryEM = this.CreateQueryWithFilters(listFilters, params, dbQuery).setParameter("busqueda",
                                "%" + params.get("busqueda") + "%");
                int countResults = queryEM.getResultList().size();
                List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                                .setMaxResults(pageable.getPageSize()).getResultList();
                List<RegionResponseDao> listResponseDao = RegionMapper.setEntityListToDaoResponseList
                                .apply((List<Region>) listResponse);
                return this.getResponse(listResponseDao, countResults);
        }

}
