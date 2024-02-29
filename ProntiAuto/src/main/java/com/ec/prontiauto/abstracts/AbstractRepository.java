package com.ec.prontiauto.abstracts;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.ec.prontiauto.exception.ApiRequestException;
import com.ec.prontiauto.helper.ListMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public abstract class AbstractRepository<Entity, IdType> implements GenericRepository<Entity, IdType> {

    @Autowired
    GenericRepository<Entity, IdType> repository;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Entity> findAll(Sort sort) {
        return this.repository.findAll(sort);
    }

    @Override
    public List<Entity> findAllById(Iterable<IdType> ids) {
        return this.repository.findAllById(ids);
    }

    @Override
    public <S extends Entity> List<S> saveAll(Iterable<S> entities) {
        return this.repository.saveAll(entities);
    }

    @Override
    public void flush() {
        this.repository.flush();
    }

    @Override
    public <S extends Entity> S saveAndFlush(S entity) {
        return this.repository.saveAndFlush(entity);
    }

    @Override
    public void deleteInBatch(Iterable<Entity> entities) {
        this.repository.deleteInBatch(entities);
    }

    @Override
    public void deleteAllInBatch() {
        this.repository.deleteAllInBatch();

    }

    @Override
    public Entity getOne(IdType id) {
        return this.repository.getOne(id);
    }

    @Override
    public <S extends Entity> List<S> findAll(Example<S> example) {
        return this.repository.findAll(example);
    }

    @Override
    public <S extends Entity> List<S> findAll(Example<S> example, Sort sort) {
        return this.repository.findAll(example, sort);
    }

    @Override
    public Page<Entity> findAll(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    @Override
    public <S extends Entity> S save(S entity) {
        return this.repository.save(entity);
    }

    @Override
    public boolean existsById(IdType id) {
        return this.repository.existsById(id);
    }

    @Override
    public long count() {
        return this.repository.count();
    }

    @Override
    public void deleteById(IdType id) {
        this.repository.deleteById(id);

    }

    @Override
    public void delete(Entity entity) {
        this.repository.delete(entity);

    }

    @Override
    public void deleteAll(Iterable<? extends Entity> entities) {
        this.repository.deleteAll(entities);

    }

    @Override
    public void deleteAll() {
        this.repository.deleteAll();

    }

    @Override
    public <S extends Entity> Optional<S> findOne(Example<S> example) {
        return this.repository.findOne(example);
    }

    @Override
    public <S extends Entity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return this.repository.findAll(example, pageable);
    }

    @Override
    public <S extends Entity> long count(Example<S> example) {
        return this.repository.count(example);
    }

    @Override
    public <S extends Entity> boolean exists(Example<S> example) {
        return this.repository.exists(example);
    }

    @Override
    public Optional<Entity> findById(IdType idT) {
        return this.repository.findById(idT);
    }

    @Override
    public List<Entity> findAll() {
        return this.repository.findAll();
    }

    public List<String> checkIfParameterExist(List<String> list, Map<String, Object> params) {
        List<String> listReturned = new ArrayList<>();
        for (String parameter : list) {
            Object existeObjeto = params.get(parameter);
            this.verifyParams(existeObjeto, listReturned, parameter);
        }
        return listReturned;
    }

    public List<String> checkIfParameterExistWithJoin(List<String> list, Map<String, Object> params) {
        List<String> listReturned = new ArrayList<>();
        for (String parameter : list) {
            Object existeObjeto = params.get(parameter.substring(2));
            this.verifyParams(existeObjeto, listReturned, parameter);
        }
        return listReturned;
    }

    public List<String> verifyParams(Object existeObjeto, List<String> listReturned, String parameter) {
        if (existeObjeto != null) {
            if (existeObjeto instanceof String) {
                if (!((String) existeObjeto).isEmpty()) {
                    listReturned.add(parameter);
                }
            }
            if (existeObjeto instanceof String) {
                if (!((String) existeObjeto).isEmpty() && !listReturned.contains(parameter)) {
                    listReturned.add(parameter);
                }
            }
            if (existeObjeto instanceof String) {
                if (!((String) existeObjeto).isEmpty() && !listReturned.contains(parameter)) {
                    listReturned.add(parameter);
                }
            }
            if (existeObjeto instanceof Integer) {
                if (!((Integer) existeObjeto).toString().isEmpty() && !listReturned.contains(parameter)) {
                    listReturned.add(parameter);
                }
            }
            if (existeObjeto instanceof Object) {
                if (!((Object) existeObjeto).toString().equals("") && !listReturned.contains(parameter)) {
                    listReturned.add(parameter);
                }
            }
        }
        return listReturned;
    }

    public String addQueryFor(List<String> listOfFilters, String initQuery) {
        int size = listOfFilters.size();
        int numberOfIterations = 0;
        for (String parameter : listOfFilters) {
            initQuery += " e." + parameter + "=:" + parameter;
            numberOfIterations++;
            if (numberOfIterations == size) {
                initQuery += ") ";
            } else {
                initQuery += " and ";
            }
        }
        return initQuery;
    }

    public String addQuery(List<String> listOfFilters) {
        String query = "and (";
        return addQueryFor(listOfFilters, query);
    }

    public String addQuerySinAnd(List<String> listOfFilters) {
        String query = " WHERE (";
        return addQueryFor(listOfFilters, query);
    }

    public String addQueryWithJoin(List<String> listOfFilters) {
        String query = "and (";
        int size = listOfFilters.size();
        int numberOfIterations = 0;
        for (String parameter : listOfFilters) {
            query += parameter + "=:" + parameter.substring(2);
            numberOfIterations++;
            if (numberOfIterations == size) {
                query += ") ";
            } else {
                query += " and ";
            }
        }
        return query;
    }

    public Query addParamsToQuery(List<String> listOfFilters, Map<String, Object> params, Query query) {
        for (String parameter : listOfFilters) {
            query.setParameter(parameter, params.get(parameter));
        }
        return query;
    }

    public Query addParamsToQueryWithJoin(List<String> listOfFilters, Map<String, Object> params, Query query) {
        for (String parameter : listOfFilters) {
            query.setParameter(parameter.substring(2), params.get(parameter.substring(2)));
        }
        return query;
    }

    public Query CreateQueryWithFilters(List<String> listFilters, Map<String, Object> params, String dbQuery) {
        List<String> listOfExistingValues = this.checkIfParameterExist(listFilters, params);
        boolean existFilter = listOfExistingValues.size() > 0;
        if (existFilter) {
            dbQuery = dbQuery + this.addQuery(listOfExistingValues);
        }
        Query query = this.entityManager.createQuery(
                dbQuery);
        query = this.addParamsToQuery(listOfExistingValues, params, query);
        return query;
    }

    public Query CreateQueryWithFiltersAndJoin(List<String> listFilters, Map<String, Object> params, String dbQuery) {

        try {
            List<String> listOfExistingValues = this.checkIfParameterExistWithJoin(listFilters, params);
            boolean existFilter = listOfExistingValues.size() > 0;
            if (existFilter) {
                dbQuery = dbQuery + this.addQueryWithJoin(listOfExistingValues);
            }
            Query query = this.entityManager.createQuery(
                    dbQuery);
            query = this.addParamsToQueryWithJoin(listOfExistingValues, params, query);
            return query;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Query CreateQueryWithFiltersSinAnd(List<String> listFilters, Map<String, Object> params, String dbQuery) {
        List<String> listOfExistingValues = this.checkIfParameterExist(listFilters, params);
        boolean existFilter = listOfExistingValues.size() > 0;
        if (existFilter) {
            dbQuery = dbQuery + this.addQuerySinAnd(listOfExistingValues);
        }
        Query query = this.entityManager.createQuery(
                dbQuery);
        query = this.addParamsToQuery(listOfExistingValues, params, query);
        return query;
    }

    public Query customQuery(String dbQuery) {
        Query query = this.entityManager.createQuery(
                dbQuery);
        return query;
    }

    public List<Object> getResponse(List<?> result, int count) {
        List<Object> response = new ArrayList<>();
        response.add(result);
        response.add(count);
        return response;
    }

    public void create(Entity entity) {
        entityManager.persist(entity);
        return;
    }

    public Entity createAndReturnValue(Entity entity) {
        entityManager.persist(entity);
        entityManager.flush();
        return entity;
    }

    public Entity updateAndReturnValue(Entity entity) {
        entityManager.merge(entity);
        entityManager.flush();
        return entity;
    }

    public void update(Entity entity) {
        entityManager.merge(entity);
        return;
    }

    public boolean existData(String dbQuery) {
        Query query = this.entityManager.createQuery(
                dbQuery);
        return query.getResultList().size() > 0;
    }

    public boolean exitsDataWithParamsGeneric(String dbQuery, List<ListMapper<?>> params) {
        Query query = this.entityManager.createQuery(dbQuery);

        if (params.size() > 0) {
            for (int i = 0; i < params.size(); i++) {
                query.setParameter(params.get(i).getClave(), params.get(i).getValue());
            }
        }

        return query.getResultList().size() > 0;
    }

    public String callStoreProcedure(String procedureName, String[] params, Object[] values) {
        StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery(procedureName);
        int i = 0;
        for (Object value : values) {
            Class<?> clazz = value.getClass();
            query.registerStoredProcedureParameter(params[i], clazz, ParameterMode.IN);
            query.setParameter(params[i], value);
            i++;
        }
        query.registerStoredProcedureParameter("responseValue", String.class, ParameterMode.OUT);
        query.execute();

        String json = (String) query.getOutputParameterValue("responseValue");
        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);
        boolean correcto = jsonObject.get("correcto").getAsBoolean();
        String message = jsonObject.get("message").getAsString();
        if (!correcto) {
            throw new ApiRequestException(message);
        }
        return message;
    }

    public Object callStoreProcedureGet(String procedureName, List<String> params, List<Object> values,
            HashMap<String, Object> filters) throws SQLException {
        StoredProcedureQuery query = createStoreProcedureQuery(procedureName, params, values);
        String parametros = "{";
        String valores = "{";
        int j = 0;
        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            if (entry.getValue() != null) {
                if (j == 0) {
                    parametros += entry.getKey();
                    valores += entry.getValue();
                } else if (filters.size() > 1) {
                    parametros += "," + entry.getKey();
                    valores += "," + entry.getValue();
                }
                j++;
            }
        }
        parametros += "}";
        valores += "}";
        ;
        query.registerStoredProcedureParameter("parametros_par", String.class, ParameterMode.IN);
        query.setParameter("parametros_par", parametros);
        query.registerStoredProcedureParameter("valores_par", String.class, ParameterMode.IN);
        query.setParameter("valores_par", valores);
        query.registerStoredProcedureParameter("responseValue", String.class, ParameterMode.OUT);
        query.execute();
        return query.getOutputParameterValue("responseValue");
    }

    public Object callSimpleStoreProcedure(String procedureName, List<String> params, List<Object> values) {
        StoredProcedureQuery query = createStoreProcedureQuery(procedureName, params, values);;
        query.registerStoredProcedureParameter("responseValue", String.class, ParameterMode.OUT);
        query.execute();
        return query.getOutputParameterValue("responseValue");
    }

    private StoredProcedureQuery createStoreProcedureQuery(String procedureName, List<String> params, List<Object> values) {
        StoredProcedureQuery query = this.entityManager.createStoredProcedureQuery(procedureName);
        int i = 0;
        for (Object value : values) {
            Class<?> clazz = value.getClass();
            query.registerStoredProcedureParameter(params.get(i), clazz, ParameterMode.IN);
            query.setParameter(params.get(i), value);
            i++;
        }
        return query;
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
