package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.DetalleNovedadRolPagoResponseDao;
import com.ec.prontiauto.entidad.DetalleNovedadRolPago;
import com.ec.prontiauto.mapper.DetalleNovedadRolPagoMapper;

@Service
public class DetalleNovedadRolPagoRepositoryImpl extends AbstractRepository<DetalleNovedadRolPago, Integer> {

        @Override
        public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
                String dbQuery = "SELECT e FROM DetalleNovedadRolPago e "
                                + "INNER JOIN RubrosRol r ON r.id=e.idRubrosRol "
                                + "INNER JOIN PeriodoLaboral p ON p.id=e.idPeriodoLaboral "
                                + "INNER JOIN Trabajador t ON t.id=e.idTrabajador "
                                + "INNER JOIN Usuario u ON u.id=t.idUsuario "
                                + "INNER JOIN Supervisor s ON s.idTrabajador=t.id "
                                + "INNER JOIN Agencia a ON a.id=s.idAgencia "
                                + "WHERE (lower(u.nombres) like lower(:busqueda) "
                                + "OR lower(u.apellidos) like lower(:busqueda) "
                                + "OR lower(e.tipoNovedad) like lower(:busqueda) "
                                + ") ";
                List<String> listFilters = new ArrayList<>();
                listFilters.add("e.sisHabilitado");
                listFilters.add("s.idAgencia");
                listFilters.add("e.tipoNovedad");
                Query queryEM = this.CreateQueryWithFiltersAndJoin(listFilters, params, dbQuery)
                                .setParameter("busqueda", "%" + params.get("busqueda") + "%");
                int countReults = queryEM.getResultList().size();
                List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                                .setMaxResults(pageable.getPageSize())
                                .getResultList();
                List<DetalleNovedadRolPagoResponseDao> listResponseDao = DetalleNovedadRolPagoMapper.setEntityListToDaoResponseList
                                .apply((List<DetalleNovedadRolPago>) listResponse);
                return this.getResponse(listResponseDao, countReults);
        }
}
