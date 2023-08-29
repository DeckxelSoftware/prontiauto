package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import com.ec.prontiauto.dao.CuentaContableResponseDao;
import com.ec.prontiauto.entidad.CuentaContable;
import com.ec.prontiauto.mapper.CuentaContableMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;

@Service
public class CuentaContableRepositoryImpl extends AbstractRepository<CuentaContable, Integer> {

	@PersistenceContext
	EntityManager em;

	@Override
	public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {

		String dbQuery = "SELECT DISTINCT (e) "+
				"FROM CuentaContable e " +
				"INNER JOIN PeriodoContable p " +
				"ON e.idPeriodoContable=p.id " +
				"WHERE lower(e.nombre) like lower(:busqueda) ";

		List<String> listFilters = new ArrayList<>();

		listFilters.add("e.nivel");
		listFilters.add("e.sisHabilitado");
		listFilters.add("e.nombre");
		listFilters.add("p.anio");
		listFilters.add("e.identificador");
		listFilters.add("e.id");

		Query queryEM = this.CreateQueryWithFiltersAndJoin(listFilters, params, dbQuery)
				.setParameter("busqueda", "%" + params.get("busqueda") + "%");
		List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
				.setMaxResults(pageable.getPageSize())
				.getResultList();
		List<CuentaContableResponseDao> listResponseDao = CuentaContableMapper.setEntityListToDaoResponseList
				.apply((List<CuentaContable>) listResponse);
		return this.getResponse(listResponseDao, listResponseDao.size());
	}


	public String obtenerBalanceComprobacion(Integer anio, Integer mesDesde, Integer mesHasta, Integer cuentaDesde, Integer cuentaHasta){
		String[] params = { "anio_periodo","id_mes_inicio","id_mes_fin","cuenta_inicio","cuenta_fin" };
		Object[] values = { anio, mesDesde, mesHasta, cuentaDesde, cuentaHasta };

		StoredProcedureQuery storedProcedureQuery = this.em.createStoredProcedureQuery("generar_balance_comprobacion");
		storedProcedureQuery.registerStoredProcedureParameter("anio_periodo", Integer.class, ParameterMode.IN);
		storedProcedureQuery.registerStoredProcedureParameter("id_mes_inicio", Integer.class, ParameterMode.IN);
		storedProcedureQuery.registerStoredProcedureParameter("id_mes_fin", Integer.class, ParameterMode.IN);
		storedProcedureQuery.registerStoredProcedureParameter("cuenta_inicio", Integer.class, ParameterMode.IN);
		storedProcedureQuery.registerStoredProcedureParameter("cuenta_fin", Integer.class, ParameterMode.IN);
		storedProcedureQuery.registerStoredProcedureParameter("result", String.class, ParameterMode.OUT);

		storedProcedureQuery.setParameter("anio_periodo", anio);
		storedProcedureQuery.setParameter("id_mes_inicio", mesDesde);
		storedProcedureQuery.setParameter("id_mes_fin", mesHasta);
		storedProcedureQuery.setParameter("cuenta_inicio", cuentaDesde);
		storedProcedureQuery.setParameter("cuenta_fin", cuentaHasta);

		storedProcedureQuery.execute();

		return (String)storedProcedureQuery.getOutputParameterValue("result");
	}
}
