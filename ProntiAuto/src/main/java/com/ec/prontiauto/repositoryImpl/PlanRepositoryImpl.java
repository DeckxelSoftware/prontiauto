package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.ArchivoRequestDao;
import com.ec.prontiauto.dao.ArrayPlanRequetDao;
import com.ec.prontiauto.dao.PlanResponseDao;
import com.ec.prontiauto.entidad.Plan;
import com.ec.prontiauto.mapper.PlanMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Service
public class PlanRepositoryImpl extends AbstractRepository<Plan, Integer> {

    @Autowired
    private ArchivoRepositoryImpl archivoRepository;

    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
        String dbQuery = "SELECT e FROM Plan e WHERE (lower(e.modelo) like lower(:busqueda) OR lower(cast(e.id as string)) like lower(:busqueda) ) ";
        List<String> listFilters = new ArrayList<>();
        Query queryEM = this.CreateQueryWithFiltersAndJoin(listFilters, params, dbQuery)
                .setParameter("busqueda", "%" + params.get("busqueda") + "%");
        int countResults = queryEM.getResultList().size();
        List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize()).getResultList();
        List<PlanResponseDao> listResponseDao = PlanMapper.setEntityListToDaoReferenceList
                .apply((List<Plan>) listResponse);
        return this.getResponse(listResponseDao, countResults);
    }

    public List<PlanResponseDao> addFileArchivo(List<PlanResponseDao> list) {
        List<PlanResponseDao> newList = new ArrayList<>();
        for (PlanResponseDao object : list) {
            PlanResponseDao Plan = object;
            ArchivoRequestDao archivoDao = archivoRepository.getFilePrimary("cuenta_bancaria_empresa",
                    Plan.getId());
            Plan.setSisArchivo(archivoDao);
            newList.add(Plan);
        }
        return newList;
    }

    public List<PlanResponseDao> addFileImagen(List<PlanResponseDao> list) {
        List<PlanResponseDao> newList = new ArrayList<>();
        for (PlanResponseDao object : list) {
            PlanResponseDao Plan = object;
            ArchivoRequestDao archivoDao = archivoRepository.getFilePrimary("cuenta_bancaria_empresa",
                    Plan.getId());
            Plan.setSisImagen(archivoDao);
            newList.add(Plan);
        }
        return newList;
    }

    public List<PlanResponseDao> addFileArchivoAndImagen(List<PlanResponseDao> list) {
        List<PlanResponseDao> newList = new ArrayList<>();
        newList = addFileArchivo(list);
        newList = addFileImagen(newList);
        return newList;
    }

    public String crearPlan(ArrayPlanRequetDao plan, String method) {
        Gson gson = new Gson();
        String jsonObject = gson.toJson(plan);
        JsonObject json = gson.fromJson(jsonObject, JsonObject.class);
        String[] params = { "json" };
        Object[] values = { gson.toJson(json) };
        return this.callStoreProcedure(method, params, values);
    }
}
