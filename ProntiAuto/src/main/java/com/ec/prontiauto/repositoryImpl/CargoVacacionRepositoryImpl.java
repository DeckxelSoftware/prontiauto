package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.CargoVacacionResponseDao;
import com.ec.prontiauto.entidad.CargoVacacion;
import com.ec.prontiauto.mapper.CargoVacacionMapper;

@Service
public class CargoVacacionRepositoryImpl extends AbstractRepository<CargoVacacion, Integer> {

    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
        String dbQuery = "SELECT e FROM CargoVacacion e ";
        List<String> listFilters = new ArrayList<>();
        listFilters.add("sisHabilitado");
        listFilters.add("idTrabajador");
        listFilters.add("id");

        Query queryEM = this.CreateQueryWithFiltersSinAnd(listFilters, params, dbQuery);
        int countReults = queryEM.getResultList().size();
        List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        List<CargoVacacionResponseDao> listResponseDao = CargoVacacionMapper.setEntityListToDaoResponseList
                .apply((List<CargoVacacion>) listResponse);
        return this.getResponse(listResponseDao, countReults);
    }
}
