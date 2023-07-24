package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.ClienteEnGrupoResponseDao;
import com.ec.prontiauto.entidad.ClienteEnGrupo;
import com.ec.prontiauto.mapper.ClienteEnGrupoMapper;

@Service
public class ClienteEnGrupoRepositoryImpl extends AbstractRepository<ClienteEnGrupo, Integer> {

  @Override
  public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
    String dbQuery = "SELECT e FROM ClienteEnGrupo e "
        + "inner join Grupo g on g.id = e.idGrupo "
        + "inner join Cliente c on c.id = e.idCliente "
        + "inner join Usuario u on u.id = c.idUsuario "
        + "where (lower(u.nombres) like lower(:busqueda) or lower(u.apellidos) like lower(:busqueda) or lower(cast(g.nombreGrupo as string)) like lower(:busqueda)) ";
    List<String> listFilters = new ArrayList<>();
    listFilters.add("sisHabilitado");
    listFilters.add("idGrupo");
    listFilters.add("idCliente");
    Query queryEM = this.CreateQueryWithFilters(listFilters, params, dbQuery).setParameter("busqueda",
        "%" + params.get("busqueda") + "%");
    int countResults = queryEM.getResultList().size();
    List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber()).setMaxResults(pageable.getPageSize())
        .getResultList();
    List<ClienteEnGrupoResponseDao> listResponseDao = ClienteEnGrupoMapper.setEntityListToDaoReferenceList
        .apply((List<ClienteEnGrupo>) listResponse);

    return this.getResponse(listResponseDao, countResults);
  }
}
