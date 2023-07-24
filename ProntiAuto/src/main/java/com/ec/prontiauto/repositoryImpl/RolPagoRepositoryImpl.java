package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.RolPagoResponseDao;
import com.ec.prontiauto.entidad.RolPago;
import com.ec.prontiauto.mapper.RolPagoMapper;

@Service
public class RolPagoRepositoryImpl extends AbstractRepository<RolPago, Integer> {

    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
        String dbQuery = "SELECT e FROM RolPago e "
                + "INNER JOIN HistorialLaboral h on e.idHistorialLaboral=h.id "
                + "INNER JOIN Trabajador t on h.idTrabajador=t.id "
                + "INNER JOIN Usuario u on t.idUsuario=u.id "
                + "WHERE lower(u.nombres) like lower(:busqueda) "
                + "OR lower(u.apellidos) like lower(:busqueda) ";


        List<String> listFilters = new ArrayList<>();
        listFilters.add("e.id");
        listFilters.add("e.sisHabilitado");

        Query queryEM = this.CreateQueryWithFiltersAndJoin(listFilters, params, dbQuery)
                .setParameter(
                "busqueda", "%" + params.get("busqueda") + "%");

        int countResults = queryEM.getResultList().size();
        List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        List<RolPagoResponseDao> listResponseDao = RolPagoMapper.setEntityListToDaoResponseList
                .apply((List<RolPago>) listResponse);

        return this.getResponse(listResponseDao, countResults);
    }

}
