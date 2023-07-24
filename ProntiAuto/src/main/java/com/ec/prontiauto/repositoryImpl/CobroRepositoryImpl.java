package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.entidad.Cobro;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Service
public class CobroRepositoryImpl extends AbstractRepository<Cobro, Integer> {
    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
        return new ArrayList<>();
    }

    public <T> String abstractProcedure(T dao, String method) {
        Gson gson = new Gson();
        String jsonObject = gson.toJson(dao);
        JsonObject json = gson.fromJson(jsonObject, JsonObject.class);
        String[] params = { "json" };
        Object[] values = { gson.toJson(json) };
        return this.callStoreProcedure(method, params, values);
    }
}
