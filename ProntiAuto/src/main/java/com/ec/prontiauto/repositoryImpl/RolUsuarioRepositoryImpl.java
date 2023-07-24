package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.RolUsuarioResponseDao;
import com.ec.prontiauto.entidad.RolUsuario;
import com.ec.prontiauto.mapper.RolUsuarioMapper;

@Service
public class RolUsuarioRepositoryImpl extends AbstractRepository<RolUsuario, Integer> {

    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
        String dbQuery = "SELECT e FROM RolUsuario e ";
        List<String> listFilters = new ArrayList<>();
        listFilters.add("sisHabilitado");
        listFilters.add("idRol");
        listFilters.add("idUsuario");
        Query queryEM = this.CreateQueryWithFiltersSinAnd(listFilters, params, dbQuery);
        int countResults = queryEM.getResultList().size();
        List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize()).getResultList();
        List<RolUsuarioResponseDao> listResponseDao = RolUsuarioMapper.setEntityListToDaoResponseList
                .apply((List<RolUsuario>) listResponse);
        return this.getResponse(listResponseDao, countResults);
    }

}
