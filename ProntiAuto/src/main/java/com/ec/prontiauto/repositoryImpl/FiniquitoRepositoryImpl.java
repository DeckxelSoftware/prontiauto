package com.ec.prontiauto.repositoryImpl;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.FiniquitoRequestDao;
import com.ec.prontiauto.entidad.Finiquito;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class FiniquitoRepositoryImpl extends AbstractRepository<Finiquito, Integer> {

    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
        String dbQuery = "SELECT f FROM Finiquito f " +
                "INNER JOIN f.trabajador t " +
                "INNER JOIN t.idUsuario u o"
                + "WHERE ( lower(u.nombres) like lower(:busqueda) " +
                " OR lower(u.apellidos) like lower(:busqueda)  " +
                " OR u.documentoIdentidad like (:busqueda) " +
                " or u.correo like (:busqueda) ) ";
        List<String> listFilters = new ArrayList<>();
        listFilters.add("f.id");
        listFilters.add("f.sisHabilitado");
        Query query = this.CreateQueryWithFiltersAndJoin(listFilters, params, dbQuery).setParameter(
                "busqueda",
                "%" + params.get("busqueda") + "%");

        List resultList = (List) query.setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize())
                .getResultStream().collect(Collectors.toList());

        return this.getResponse(resultList, resultList.size());
    }
}
