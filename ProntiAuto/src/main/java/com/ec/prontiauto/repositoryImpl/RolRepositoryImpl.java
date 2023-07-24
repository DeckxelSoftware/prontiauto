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
import com.ec.prontiauto.dao.RolResponseDao;
import com.ec.prontiauto.entidad.Rol;
import com.ec.prontiauto.mapper.RolMapper;

@Service
public class RolRepositoryImpl extends AbstractRepository<Rol, Integer> {

    @Autowired
    private ArchivoRepositoryImpl archivoRepository;

    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
        String dbQuery = "SELECT e FROM Rol e WHERE (lower(e.nombre) like lower(:busqueda) ) ";
        List<String> listFilters = new ArrayList<>();
        listFilters.add("sisHabilitado");
        Query queryEM = this.CreateQueryWithFilters(listFilters, params, dbQuery).setParameter("busqueda",
                "%" + params.get("busqueda") + "%");
        int countResults = queryEM.getResultList().size();
        List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize()).getResultList();
        List<RolResponseDao> listResponseDao = RolMapper.setEntityListToDaoResponseList
                .apply((List<Rol>) listResponse);
        return this.getResponse(listResponseDao, countResults);
    }

    public List<RolResponseDao> addFileArchivo(List<RolResponseDao> list) {
        List<RolResponseDao> newList = new ArrayList<>();
        for (RolResponseDao object : list) {
            RolResponseDao rol = object;
            ArchivoRequestDao archivoDao = archivoRepository.getFilePrimary("rol",
                    rol.getId());
            rol.setSisArchivo(archivoDao);
            newList.add(rol);
        }
        return newList;
    }

    public List<RolResponseDao> addFileImagen(List<RolResponseDao> list) {
        List<RolResponseDao> newList = new ArrayList<>();
        for (RolResponseDao object : list) {
            RolResponseDao rol = object;
            ArchivoRequestDao archivoDao = archivoRepository.getFilePrimary("rol",
                    rol.getId());
            rol.setSisImagen(archivoDao);
            newList.add(rol);
        }
        return newList;
    }

    public List<RolResponseDao> addFileArchivoAndImagen(List<RolResponseDao> list) {
        List<RolResponseDao> newList = new ArrayList<>();
        newList = addFileArchivo(list);
        newList = addFileImagen(newList);
        return newList;
    }
}
