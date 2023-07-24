
package com.ec.prontiauto.facturacion.respository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.facturacion.dao.NotaCreditoResponseDao;
import com.ec.prontiauto.facturacion.entidad.NotaCredito;
import com.ec.prontiauto.facturacion.mapper.NotaCreditoMapper;

@Service
public class NotaCreditoRespositoryImpl extends AbstractRepository<NotaCredito, Integer> {
    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
        boolean existFilter = params.get("desde").toString().length() > 0
                && params.get("hasta").toString().length() > 0;
        String dbQuery = "SELECT e FROM NotaCredito e "
                + "WHERE (lower(cast(e.inIdentificacionComprador as string)) like lower(:busqueda) "
                + "OR lower(cast(e.inRazonSocialComprador as string)) like lower(:busqueda) "
                + "OR lower(cast(e.inDirEstablecimiento as string)) like lower(:busqueda) "
                + "OR lower(cast(e.inNumDocModificado as string)) like lower(:busqueda)"
                + ") ";
        List<String> listFilters = new ArrayList<>();
        listFilters.add("inNumDocModificado");
        if (existFilter) {
            dbQuery += "AND e.inFechaEmision BETWEEN :desde AND :hasta ";
        }
        Query queryEM = this.CreateQueryWithFilters(listFilters, params, dbQuery);
        queryEM.setParameter("busqueda", "%" + params.get("busqueda") + "%");

        if (existFilter) {
            queryEM.setParameter("desde", params.get("desde"));
            queryEM.setParameter("hasta", params.get("hasta"));
        }
        int countResults = queryEM.getResultList().size();
        List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize()).getResultList();
        List<NotaCreditoResponseDao> listResponseDao = NotaCreditoMapper.setEntityListToDaoResponseList
                .apply((List<NotaCredito>) listResponse);
        return this.getResponse(listResponseDao, countResults);
    }
}