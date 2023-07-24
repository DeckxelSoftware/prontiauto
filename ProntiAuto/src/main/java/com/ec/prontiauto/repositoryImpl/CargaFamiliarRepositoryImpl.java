package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.CargaFamiliarResponseDao;
import com.ec.prontiauto.entidad.CargaFamiliar;
import com.ec.prontiauto.mapper.CargaFamiliarMapper;

@Service
public class CargaFamiliarRepositoryImpl extends AbstractRepository<CargaFamiliar, Integer> {

        @Override
        public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
                String dbQuery = "SELECT e FROM CargaFamiliar e WHERE (lower(e.nombres) like lower(:busqueda) OR lower(e.apellidos) like lower(:busqueda) OR lower(e.documentoIdentidad) like lower(:busqueda) ) ";
                List<String> listFilters = new ArrayList<>();
                listFilters.add("sisHabilitado");
                listFilters.add("parentesco");
                listFilters.add("genero");
                listFilters.add("discapacidad");
                listFilters.add("tipoDiscapacidad");
                listFilters.add("estudia");
                listFilters.add("idTrabajador");
                Query queryEM = this.CreateQueryWithFilters(listFilters, params, dbQuery)
                                .setParameter("busqueda", "%" + params.get("busqueda") + "%");
                int countReults = queryEM.getResultList().size();
                List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                                .setMaxResults(pageable.getPageSize())
                                .getResultList();
                List<CargaFamiliarResponseDao> listResponseDao = CargaFamiliarMapper.setEntityListToDaoResponseList
                                .apply((List<CargaFamiliar>) listResponse);
                return this.getResponse((listResponseDao), countReults);
        }
}
