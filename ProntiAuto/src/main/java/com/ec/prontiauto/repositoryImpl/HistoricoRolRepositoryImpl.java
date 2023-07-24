package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.HistoricoRolResponseDao;
import com.ec.prontiauto.entidad.HistoricoRol;
import com.ec.prontiauto.mapper.HistoricoRolMapper;

@Service
public class HistoricoRolRepositoryImpl extends AbstractRepository<HistoricoRol, Integer> {

    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
        String dbQuery = "SELECT e FROM HistoricoRol e " +
                "INNER JOIN RolPago r on e.idRolPago=r.id " +
                "INNER JOIN PeriodoLaboral p on e.idPeriodoLaboral=p.id " +
                "INNER JOIN Pagos2 p2 on p2.idPeriodoLaboral=p.id " +
                "INNER JOIN Trabajador t on p2.idTrabajador=t.id " +
                "INNER JOIN Usuario u on t.idUsuario=u.id " +
                "WHERE lower(u.nombres) like lower(:busqueda) " +
                "OR lower(u.apellidos) like lower(:busqueda) ";

        List<String> listFilters = new ArrayList<>();
        listFilters.add("e.id");
        listFilters.add("e.sisHabilitado");

        Query queryEM = this.CreateQueryWithFiltersAndJoin(listFilters, params, dbQuery).setParameter(
                "busqueda", "%" + params.get("busqueda") + "%");

        int countResults = queryEM.getResultList().size();
        List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        List<HistoricoRolResponseDao> listResponseDao = HistoricoRolMapper.setEntityListToDaoResponseList
                .apply((List<HistoricoRol>) listResponse);

        return this.getResponse(listResponseDao, countResults);
    }

}
