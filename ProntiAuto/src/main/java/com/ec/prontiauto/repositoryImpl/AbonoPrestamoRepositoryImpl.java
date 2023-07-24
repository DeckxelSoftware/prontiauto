package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.AbonoPrestamoResponseDao;
import com.ec.prontiauto.entidad.AbonoPrestamo;
import com.ec.prontiauto.mapper.AbonoPrestamoMapper;

@Service
public class AbonoPrestamoRepositoryImpl extends AbstractRepository<AbonoPrestamo, Integer> {

        @Override
        public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
                String dbQuery = "SELECT e FROM AbonoPrestamo e "
                                + "INNER JOIN Prestamo p on p.id = e.idPrestamo "
                                + "INNER JOIN Trabajador t on t.id = p.idTrabajador "
                                + "INNER JOIN Usuario u on u.id = t.idUsuario "
                                + "WHERE (lower(u.nombres) like lower(:busqueda)) ";
                List<String> listFilters = new ArrayList<>();
                listFilters.add("p.tipoPrestamo");
                listFilters.add("p.fechaPrestamo");
                listFilters.add("e.idPrestamo");
                listFilters.add("e.sisHabilitado");
                Query queryEM = this.CreateQueryWithFiltersAndJoin(listFilters, params, dbQuery).setParameter(
                                "busqueda",
                                "%" + params.get("busqueda") + "%");
                int countResults = queryEM.getResultList().size();
                List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                                .setMaxResults(pageable.getPageSize())
                                .getResultList();
                List<AbonoPrestamoResponseDao> listResponseDao = AbonoPrestamoMapper.setEntityListToDaoResponseList
                                .apply((List<AbonoPrestamo>) listResponse);

                return this.getResponse(listResponseDao, countResults);
        }

}
