package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.OrdenCompraResponseDao;
import com.ec.prontiauto.entidad.OrdenCompra;
import com.ec.prontiauto.mapper.OrdenCompraMapper;

@Service
public class OrdenCompraRepositoryImpl extends AbstractRepository<OrdenCompra, Integer> {

        @Override
        public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {

                String dbQuery = "SELECT e FROM OrdenCompra e WHERE (lower(e.placa) like lower(:busqueda) OR lower(e.chasis) like lower(:busqueda) OR lower(e.numeroOrdenContrato) like lower(:busqueda) OR lower(e.nombreCliente) like lower(:busqueda) OR lower(e.beneficiarioCheque) like lower(:busqueda) )";
                List<String> listFilters = new ArrayList<>();
                listFilters.add("sisHabilitado");
                listFilters.add("anio");
                listFilters.add("id");

                Query queryEM = this.CreateQueryWithFilters(listFilters, params, dbQuery)
                                .setParameter("busqueda", "%" + params.get("busqueda") + "%");
                int countReults = queryEM.getResultList().size();
                List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                                .setMaxResults(pageable.getPageSize()).getResultList();
                List<OrdenCompraResponseDao> listResponseDao = OrdenCompraMapper.setEntityListToDaoResponseList
                                .apply((List<OrdenCompra>) listResponse);

                return this.getResponse(listResponseDao, countReults);
        }

}
