package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.RolPermisoResponseDao;
import com.ec.prontiauto.entidad.RolPermiso;
import com.ec.prontiauto.mapper.RolPermisoMapper;

@Service
public class RolPermisoRepositoryImpl extends AbstractRepository<RolPermiso, Integer> {

    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
        String dbQuery = "SELECT e FROM RolPermiso e ";
        List<String> listFilters = new ArrayList<>();
        listFilters.add("sisHabilitado");
        listFilters.add("idRol");
        listFilters.add("idPermiso");
        Query queryEM = this.CreateQueryWithFiltersSinAnd(listFilters, params, dbQuery);
        int countResults = queryEM.getResultList().size();
        List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize()).getResultList();
        List<RolPermisoResponseDao> listResponseDao = RolPermisoMapper.setEntityListToDaoResponseList
                .apply((List<RolPermiso>) listResponse);
        return this.getResponse(listResponseDao, countResults);
    }

}