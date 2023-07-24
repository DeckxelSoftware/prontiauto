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
import com.ec.prontiauto.dao.AutorLibroResponseDao;
import com.ec.prontiauto.entidad.AutorLibro;
import com.ec.prontiauto.mapper.AutorLibroMapper;

@Service
public class AutorLibroRepositoryImpl extends AbstractRepository<AutorLibro, Integer> {

    @Autowired
    private ArchivoRepositoryImpl archivoRepository;

    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
        String dbQuery = "SELECT e FROM AutorLibro e WHERE (lower(e.nombres) like lower(:busqueda) OR lower(e.apellidos) like lower(:busqueda) ) ";
        List<String> listFilters = new ArrayList<>();
        listFilters.add("sisHabilitado");
        listFilters.add("idLibroBiblioteca");
        Query queryEM = this.CreateQueryWithFilters(listFilters, params, dbQuery)
                .setParameter("busqueda", "%" + params.get("busqueda") + "%");
        int countReults = queryEM.getResultList().size();
        List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        List<AutorLibroResponseDao> listResponseDao = AutorLibroMapper.setEntityListToDaoResponseList
                .apply((List<AutorLibro>) listResponse);
        return this.getResponse(addFileArchivo(listResponseDao), countReults);
    }

    public List<AutorLibroResponseDao> addFileArchivo(List<AutorLibroResponseDao> list) {
        List<AutorLibroResponseDao> newList = new ArrayList<>();
        for (AutorLibroResponseDao object : list) {
            AutorLibroResponseDao libroBiblioteca = object;
            ArchivoRequestDao archivoDao = archivoRepository.getFilePrimary("autor_libro",
                    libroBiblioteca.getId());
            libroBiblioteca.setSisArchivo(archivoDao);
            newList.add(libroBiblioteca);
        }
        return newList;
    }

    public List<AutorLibroResponseDao> addFileImagen(List<AutorLibroResponseDao> list) {
        List<AutorLibroResponseDao> newList = new ArrayList<>();
        for (AutorLibroResponseDao object : list) {
            AutorLibroResponseDao libroBiblioteca = object;
            ArchivoRequestDao archivoDao = archivoRepository.getFilePrimary("autor_libro",
                    libroBiblioteca.getId());
            libroBiblioteca.setSisImagen(archivoDao);
            newList.add(libroBiblioteca);
        }
        return newList;
    }

}
