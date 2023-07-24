package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.ArticuloResponseDao;
import com.ec.prontiauto.entidad.Articulo;
import com.ec.prontiauto.mapper.ArticuloMapper;

@Service
public class ArticuloRepositoryImpl extends AbstractRepository<Articulo, Integer> {

    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {

        String dbQuery = "SELECT e FROM Articulo e WHERE (lower(e.placa) like lower(:busqueda) OR lower(e.chasis) like lower(:busqueda) ) ";
        List<String> listFilters = new ArrayList<>();
        listFilters.add("sisHabilitado");
        listFilters.add("estado");
        listFilters.add("ubicacionFisica");
        listFilters.add("id");
        Query queryEM = this.CreateQueryWithFilters(listFilters, params, dbQuery)
                .setParameter("busqueda", "%" + params.get("busqueda") + "%");
        int countReults = queryEM.getResultList().size();
        List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize()).getResultList();
        List<ArticuloResponseDao> listResponseDao = ArticuloMapper.setEntityListToDaoResponseList
                .apply((List<Articulo>) listResponse);

        return this.getResponse(listResponseDao, countReults);
    }

    public Articulo createArticulo(Articulo entity) {
        Articulo articulo = null;
        try {
            articulo = this.createAndReturnValue(entity);
        } catch (Exception e) {
            System.out.printf("\n%s\n", "------------------------------", e.getMessage());
        }

        return articulo;
    }

    public Articulo updateArticulo(Articulo entity) {
        Articulo articulo = null;
        try {
            articulo = this.updateAndReturnValue(entity);
        } catch (Exception e) {
            System.out.printf("\n%s\n", "------------------------------", e.getMessage());
        }

        return articulo;
    }

}
