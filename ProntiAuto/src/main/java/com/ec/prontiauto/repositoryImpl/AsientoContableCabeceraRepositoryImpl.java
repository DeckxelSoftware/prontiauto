package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.AsientoContableCabeceraResponseDao;
import com.ec.prontiauto.entidad.AsientoContableCabecera;
import com.ec.prontiauto.mapper.AsientoContableCabeceraMapper;

@Service
public class AsientoContableCabeceraRepositoryImpl extends AbstractRepository<AsientoContableCabecera, Integer> {

        @Override
        public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
                String dbQuery = "SELECT DISTINCT(e) FROM AsientoContableCabecera e "
//                                + "INNER JOIN SubgrupoContable sc on sc.id=e.idSubgrupoContable "
                                + "WHERE(lower(e.codigoReferencialAsientoContable) like lower(:busqueda)) ";

                List<String> listFilters = new ArrayList<>();
                listFilters.add("e.sisHabilitado");
                listFilters.add("e.tipoTransaccion");
                listFilters.add("e.tipoAsientoContable");
                listFilters.add("e.asientoCerrado");
//                listFilters.add("e.idSubgrupoContable");
                listFilters.add("e.id");

                Query queryEM = this.CreateQueryWithFiltersAndJoin(listFilters, params, dbQuery)
                                .setParameter("busqueda", "%" + params.get("busqueda") + "%");

                int countResults = queryEM.getResultList().size();
                List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                                .setMaxResults(pageable.getPageSize()).getResultList();
                List<AsientoContableCabeceraResponseDao> listResponseDao = AsientoContableCabeceraMapper.setEntityListToDaoResponseList
                                .apply((List<AsientoContableCabecera>) listResponse);
                return this.getResponse(listResponseDao, countResults);
        }
}
