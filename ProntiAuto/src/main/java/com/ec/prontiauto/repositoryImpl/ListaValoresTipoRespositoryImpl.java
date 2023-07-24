package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.ListaValoresTipoResponseDao;
import com.ec.prontiauto.entidad.ListaValoresTipo;
import com.ec.prontiauto.mapper.ListaValoresTipoMapper;

@Service
public class ListaValoresTipoRespositoryImpl extends AbstractRepository<ListaValoresTipo, Integer> {
    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
        String dbQuery = "SELECT e FROM ListaValoresTipo e WHERE (lower(e.nombre) like lower(:busqueda)"
        +" or lower(e.codigoPrimario) like lower(:busqueda)"
        +" or lower(e.codigoSecundario) like lower(:busqueda)) ";
        List<String> listFilters = new ArrayList<>();
        listFilters.add("sisHabilitado");
        Query queryEM = this.CreateQueryWithFilters(listFilters, params, dbQuery)
                .setParameter("busqueda", "%" + params.get("busqueda") + "%");
        int countReults = queryEM.getResultList().size();
        List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize()).getResultList();
        List<ListaValoresTipoResponseDao> listResponseDao = ListaValoresTipoMapper.setEntityListToDaoResponseList
                .apply((List<ListaValoresTipo>) listResponse);
        return this.getResponse(listResponseDao, countReults);
    }
}
