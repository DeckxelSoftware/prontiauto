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
import com.ec.prontiauto.dao.PermisoResponseDao;
import com.ec.prontiauto.entidad.Permiso;
import com.ec.prontiauto.mapper.PermisoMapper;

@Service
public class PermisoRepositoryImpl extends AbstractRepository<Permiso, Integer> {
    @Autowired
    private ArchivoRepositoryImpl archivoRepository;

    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
        String dbQuery = "SELECT e FROM Permiso e WHERE (lower(e.nombre) like lower(:busqueda) )";
        List<String> listFilters = new ArrayList<>();
        listFilters.add("sisHabilitado");
        Query queryEM = this.CreateQueryWithFilters(listFilters, params, dbQuery)
                .setParameter("busqueda", "%" + params.get("busqueda") + "%");
        int countResults = queryEM.getResultList().size();
        List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize()).getResultList();
        List<PermisoResponseDao> listResponseDao = PermisoMapper.setEntityListToDaoResponseList
                .apply((List<Permiso>) listResponse);
        return this.getResponse(listResponseDao, countResults);
    }

    public List<PermisoResponseDao> addFileArchivo(List<PermisoResponseDao> list) {
        List<PermisoResponseDao> newList = new ArrayList<>();
        for (PermisoResponseDao object : list) {
            PermisoResponseDao permiso = object;
            ArchivoRequestDao archivoDao = archivoRepository.getFilePrimary("permiso",
                    permiso.getId());
            permiso.setSisArchivo(archivoDao);
            newList.add(permiso);
        }
        return newList;
    }

    public List<PermisoResponseDao> addFileImagen(List<PermisoResponseDao> list) {
        List<PermisoResponseDao> newList = new ArrayList<>();
        for (PermisoResponseDao object : list) {
            PermisoResponseDao permiso = object;
            ArchivoRequestDao archivoDao = archivoRepository.getFilePrimary("permiso",
                    permiso.getId());
            permiso.setSisImagen(archivoDao);
            newList.add(permiso);
        }
        return newList;
    }

    public List<PermisoResponseDao> addFileArchivoAndImagen(List<PermisoResponseDao> list) {
        List<PermisoResponseDao> newList = new ArrayList<>();
        newList = addFileArchivo(list);
        newList = addFileImagen(newList);
        return newList;
    }
}
