package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.ListaValoresDetalleResponseDao;
import com.ec.prontiauto.entidad.ListaValoresDetalle;
import com.ec.prontiauto.mapper.ListaValoresDetalleMapper;

@Service
public class ListaValoresDetalleRespositoryImpl extends AbstractRepository<ListaValoresDetalle, Integer> {

    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
        String dbQuery = "SELECT e FROM ListaValoresDetalle e, ListaValoresTipo t WHERE (e.idListaValoresTipo=t.id) and"
        +" (lower(e.nombre) like lower(:busqueda)"
        +" or lower(e.codigoSecundario) like lower(:busqueda)"
        +" or lower(e.codigoPrimario) like lower(:busqueda)) ";
        List<String> listFilters = new ArrayList<>();
        listFilters.add("e.sisHabilitado");
        listFilters.add("e.idListaValoresTipo");
        listFilters.add("t.codigoPrimario");
        Query queryEM = this.CreateQueryWithFiltersAndJoin(listFilters, params, dbQuery)
                .setParameter("busqueda", "%" + params.get("busqueda") + "%");
        int countReults = queryEM.getResultList().size();
        List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize()).getResultList();
        List<ListaValoresDetalleResponseDao> listResponseDao = ListaValoresDetalleMapper.setEntityListToDaoResponseList
                .apply((List<ListaValoresDetalle>) listResponse);
        return this.getResponse(listResponseDao, countReults);
    }

}
