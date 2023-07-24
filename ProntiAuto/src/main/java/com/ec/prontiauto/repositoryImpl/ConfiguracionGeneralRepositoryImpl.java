package com.ec.prontiauto.repositoryImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.ConfiguracionGeneralResponseDao;
import com.ec.prontiauto.entidad.ConfiguracionGeneral;
import com.ec.prontiauto.mapper.ConfiguracionGeneralMapper;

@Service
public class ConfiguracionGeneralRepositoryImpl extends AbstractRepository<ConfiguracionGeneral, Integer> {

    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {

        String dbQuery = "SELECT e FROM ConfiguracionGeneral e";
        List<String> listFilters = new ArrayList<>();
        Query queryEM = this.CreateQueryWithFilters(listFilters, params, dbQuery);
        int countResults = queryEM.getResultList().size();
        List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize()).getResultList();
        List<ConfiguracionGeneralResponseDao> listResponseDao = ConfiguracionGeneralMapper.setEntityListToDaoResponseList
                .apply((List<ConfiguracionGeneral>) listResponse);
        return this.getResponse(listResponseDao, countResults);
    }

    public boolean getExitsData() {
        String dbQuery = "SELECT e FROM ConfiguracionGeneral e";
        return this.existData(dbQuery);
    }

    public String actualizarIvaPorcentaje(BigDecimal ivaPorcentaje, Integer id) {
        String[] params = { "iva", "id" };
        Object[] values = { ivaPorcentaje, id };
        return this.callStoreProcedure("actualizar_iva", params, values);
    }
}
