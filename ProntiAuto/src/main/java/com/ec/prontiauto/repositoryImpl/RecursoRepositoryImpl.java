package com.ec.prontiauto.repositoryImpl;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.RecursoResponseDao;
import com.ec.prontiauto.entidad.Recurso;
import com.ec.prontiauto.mapper.RecursoMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RecursoRepositoryImpl extends AbstractRepository<Recurso, Integer> {
    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
        String dbQuery = "SELECT e FROM Recurso e WHERE lower(e.nombre) like lower(:busqueda)";
        List<String> listFilters = new ArrayList<>();
        listFilters.add("sisHabilitado");
        listFilters.add("id");
        Query queryEM = this.CreateQueryWithFilters(listFilters, params, dbQuery)
                .setParameter("busqueda", "%" + params.get("busqueda") + "%");
        List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize()).getResultList();
        List<RecursoResponseDao> response = RecursoMapper.setEntityListToDaoResponseList.apply((List<Recurso>) listResponse);
        return this.getResponse(response, response.size());
    }
}
