package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.AgenciaResponseDao;
import com.ec.prontiauto.dao.ArchivoRequestDao;
import com.ec.prontiauto.entidad.Agencia;
import com.ec.prontiauto.mapper.AgenciaMapper;

@Service
public class AgenciaRepositoryImpl extends AbstractRepository<Agencia, Integer> {

    @Autowired
    private ArchivoRepositoryImpl archivoRepository;

    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
    	 String dbQuery = "SELECT e FROM Agencia e WHERE (lower(e.nombre) like lower(:busqueda) )";
        List<String> listFilters = new ArrayList<>();
        listFilters.add("e.ciudad");
        Query queryEM = this.CreateQueryWithFiltersAndJoin(listFilters, params, dbQuery)
                .setParameter("busqueda", "%" + params.get("busqueda") + "%");
        int countResults = queryEM.getResultList().size();
        List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize()).getResultList();
        List<AgenciaResponseDao> listResponseDao = AgenciaMapper.setEntityListToDaoResponseList
                .apply((List<Agencia>) listResponse);

        return this.getResponse(listResponseDao, countResults);
    }

    public List<AgenciaResponseDao> addFileArchivo(List<AgenciaResponseDao> list) {
        List<AgenciaResponseDao> newList = new ArrayList<>();
        for (AgenciaResponseDao object : list) {
            AgenciaResponseDao Agencia = object;
            ArchivoRequestDao archivoDao = archivoRepository.getFilePrimary("cuenta_bancaria_empresa",
                    Agencia.getId());
            Agencia.setSisArchivo(archivoDao);
            newList.add(Agencia);
        }
        return newList;
    }

    public List<AgenciaResponseDao> addFileImagen(List<AgenciaResponseDao> list) {
        List<AgenciaResponseDao> newList = new ArrayList<>();
        for (AgenciaResponseDao object : list) {
            AgenciaResponseDao Agencia = object;
            ArchivoRequestDao archivoDao = archivoRepository.getFilePrimary("cuenta_bancaria_empresa",
                    Agencia.getId());
            Agencia.setSisImagen(archivoDao);
            newList.add(Agencia);
        }
        return newList;
    }

    public List<AgenciaResponseDao> addFileArchivoAndImagen(List<AgenciaResponseDao> list) {
        List<AgenciaResponseDao> newList = new ArrayList<>();
        newList = addFileArchivo(list);
        newList = addFileImagen(newList);
        return newList;
    }
}
