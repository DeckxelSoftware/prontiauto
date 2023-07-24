package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.ProveedorResponseDao;
import com.ec.prontiauto.entidad.Proveedor;
import com.ec.prontiauto.mapper.ProveedorMapper;

@Service
public class ProveedorRepositoryImpl extends AbstractRepository<Proveedor, Integer> {

    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
        String dbQuery = "SELECT e FROM Proveedor e ";
        List<String> listFilters = new ArrayList<>();
        listFilters.add("sisHabilitado");
        listFilters.add("idEmpresa");
        listFilters.add("idUsuario");
        listFilters.add("id");

        Query queryEM = this.CreateQueryWithFiltersSinAnd(listFilters, params, dbQuery);
        int countReults = queryEM.getResultList().size();
        List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        List<ProveedorResponseDao> listResponseDao = ProveedorMapper.setEntityListToDaoResponseList
                .apply((List<Proveedor>) listResponse);
        return this.getResponse(listResponseDao, countReults);
    }
}
