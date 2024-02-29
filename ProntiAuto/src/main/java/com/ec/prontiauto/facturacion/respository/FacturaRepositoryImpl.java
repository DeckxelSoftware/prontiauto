package com.ec.prontiauto.facturacion.respository;

import java.util.*;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.facturacion.dao.FacturaResponseDao;
import com.ec.prontiauto.facturacion.entidad.Factura;
import com.ec.prontiauto.facturacion.mapper.FacturaMapper;

@Service
public class FacturaRepositoryImpl extends AbstractRepository<Factura, Integer> {
    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {

        boolean existFilter = params.get("desde").toString().length() > 0
                && params.get("hasta").toString().length() > 0;
        String dbQuery = "SELECT e FROM Factura e WHERE ("
                + "lower(cast(e.ifIdentificacionComprador as string)) like lower(:busqueda) "
                + "OR lower(cast(e.ifRazonSocialComprador as string)) like lower(:busqueda) "
                + "OR lower(cast(e.ifDirEstablecimiento as string)) like lower(:busqueda) "
                + "OR lower(cast(e.estado as string)) like lower(:busqueda) "
                + ") ";
        List<String> listFilters = new ArrayList<>();
        listFilters.add("itNumeroDocumento");
        if (existFilter) {
            dbQuery += "AND e.ifFechaEmision BETWEEN :desde AND :hasta ";
        }
        Query queryEM = this.CreateQueryWithFilters(listFilters, params, dbQuery);
        queryEM.setParameter("busqueda", "%" + params.get("busqueda") + "%");

        if (existFilter) {
            queryEM.setParameter("desde", params.get("desde"));
            queryEM.setParameter("hasta", params.get("hasta"));
        }
        //int countResults = queryEM.getResultList().size();
        List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize()).getResultList();
        List<FacturaResponseDao> listResponseDao = FacturaMapper.setEntityListToDaoResponseList
                .apply((List<Factura>) listResponse);

        //return new ArrayList<>(listResponseDao);

        //return response;
        return this.getResponse(listResponseDao, listResponse.size());
    }

    @Override
    public Optional<Factura> findById(Integer idT) {
        String dbQuery = "SELECT e FROM Factura e WHERE e.id = :id";
        Query query = this.CreateQueryWithFilters(Collections.emptyList(),  Collections.emptyMap(), dbQuery);
        query.setParameter("id", idT);
        return Optional.of((Factura)query.getSingleResult());
    }
}
