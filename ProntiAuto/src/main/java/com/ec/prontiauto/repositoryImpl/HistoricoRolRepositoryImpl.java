package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ec.prontiauto.dao.ReporteProvisionesResponseDao;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.HistoricoRolResponseDao;
import com.ec.prontiauto.entidad.HistoricoRol;
import com.ec.prontiauto.mapper.HistoricoRolMapper;

@Service
public class HistoricoRolRepositoryImpl extends AbstractRepository<HistoricoRol, Integer> {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
        String dbQuery = "SELECT e FROM HistoricoRol e " +
                "INNER JOIN e.idPeriodoLaboral as p " +
                "INNER JOIN e.historialLaboral as hl " +
                "INNER JOIN Trabajador t on hl.idTrabajador=t.id " +
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


    public Object obtenerReporteProvisiones(Integer idPeriodoLaboral){
        Query query = em.createQuery("select u.nombres as nombres , u.apellidos as apellidos, t.fechaIngreso as fechaIngreso, " +
                "hr.provDecimoCuarto as provDecimoCuarto, hr.provDecimoTercero as provDecimoTercero," +
                "t.fondoReservaIess as fondoReservaIess, t.pagoFondosReservaMes as pagoFondosReservaMes, hr.pagoFondoReservaMes as pagoFondoReservaMes, " +
                "hr.provFondosReserva as provFondosReserva, t.provVacaciones as provVacaciones, t.provAportePatronal as provAportePatronal  " +
                "from HistoricoRol hr " +
                "INNER JOIN hr.idPeriodoLaboral as p " +
                "INNER JOIN hr.historialLaboral as hl " +
                "INNER JOIN Trabajador t on hl.idTrabajador=t.id " +
                "INNER JOIN Usuario u on t.idUsuario=u.id " +
                "where p.id = :idPeriodoLaboral");
        query.setParameter("idPeriodoLaboral", idPeriodoLaboral);


        List<Object[]> queryResponse = query.getResultList();
        List<ReporteProvisionesResponseDao> result = queryResponse.stream().map(ReporteProvisionesResponseDao::new).collect(Collectors.toList());

        return this.getResponse(result, result.size());
    }

}
