package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.ItemCobroPagoResponseDao;
import com.ec.prontiauto.entidad.ItemCobroPago;
import com.ec.prontiauto.mapper.ItemCobroPagoMapper;

@Service
public class ItemCobroPagoRepositoryImpl extends AbstractRepository<ItemCobroPago, Integer> {

    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {

        String dbQuery = "SELECT e FROM ItemCobroPago e " +
                "WHERE lower(e.nombreItem) like lower(:busqueda) ";
        List<String> listFilters = new ArrayList<>();
        listFilters.add("sisHabilitado");
        listFilters.add("idCuentaContable");
        listFilters.add("nombreCuenta");
        listFilters.add("nombreItem");
        listFilters.add("id");

        Query queryEM = this.CreateQueryWithFilters(listFilters, params, dbQuery)
                .setParameter("busqueda", "%" + params.get("busqueda") + "%");
        int countReults = queryEM.getResultList().size();
        List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize()).getResultList();
        List<ItemCobroPagoResponseDao> listResponseDao = ItemCobroPagoMapper.setEntityListToDaoResponseList
                .apply((List<ItemCobroPago>) listResponse);

        return this.getResponse(listResponseDao, countReults);
    }
}
