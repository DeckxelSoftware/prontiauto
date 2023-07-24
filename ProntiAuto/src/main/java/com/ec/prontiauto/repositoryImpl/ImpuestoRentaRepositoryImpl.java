package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.ImpuestoRentaResponseDao;
import com.ec.prontiauto.entidad.ImpuestoRenta;
import com.ec.prontiauto.mapper.ImpuestoRentaMapper;

@Service
public class ImpuestoRentaRepositoryImpl extends AbstractRepository<ImpuestoRenta, Integer> {

    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {

        String dbQuery = "SELECT e FROM ImpuestoRenta e WHERE lower(e.nombre) LIKE lower(:busqueda) ";
        List<String> listFilters = new ArrayList<>();
        listFilters.add("anio");
        listFilters.add("sisHabilitado");
        Query queryEM = this.CreateQueryWithFilters(listFilters, params, dbQuery).setParameter("busqueda",
                "%" + params.get("busqueda") + "%");
        int countResults = queryEM.getResultList().size();
        List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize()).getResultList();
        List<ImpuestoRentaResponseDao> listResponseDao = ImpuestoRentaMapper.setEntityListToDaoResponseList
                .apply((List<ImpuestoRenta>) listResponse);

        return this.getResponse(listResponseDao, countResults);
    }

}
