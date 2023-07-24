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
import com.ec.prontiauto.dao.LibroBibliotecaResponseDao;
import com.ec.prontiauto.entidad.LibroBiblioteca;
import com.ec.prontiauto.mapper.LibroBibliotecaMapper;

@Service
public class LibroBibliotecaRepositoryImpl extends AbstractRepository<LibroBiblioteca, Integer> {

    @Autowired
    private ArchivoRepositoryImpl archivoRepository;

    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {

        String dbQuery = "SELECT e FROM LibroBiblioteca e WHERE (lower(e.nombre) like lower(:busqueda) OR lower(e.isbn) like lower(:busqueda) ) ";
        List<String> listFilters = new ArrayList<>();
        listFilters.add("sisHabilitado");
        listFilters.add("generoLibro");
        Query queryEM = this.CreateQueryWithFilters(listFilters, params, dbQuery)
                .setParameter("busqueda", "%" + params.get("busqueda") + "%");
        int countReults = queryEM.getResultList().size();
        List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize()).getResultList();
        List<LibroBibliotecaResponseDao> listResponseDao = LibroBibliotecaMapper.setEntityListToDaoResponseList
                .apply((List<LibroBiblioteca>) listResponse);

        return this.getResponse(addFileImagen(listResponseDao), countReults);
    }

    public List<LibroBibliotecaResponseDao> addFileArchivo(List<LibroBibliotecaResponseDao> list) {
        List<LibroBibliotecaResponseDao> newList = new ArrayList<>();
        for (LibroBibliotecaResponseDao object : list) {
            LibroBibliotecaResponseDao libroBiblioteca = object;
            ArchivoRequestDao archivoDao = archivoRepository.getFilePrimary("libro_biblioteca",
                    libroBiblioteca.getId());
            libroBiblioteca.setSisArchivo(archivoDao);
            newList.add(libroBiblioteca);
        }
        return newList;
    }

    public List<LibroBibliotecaResponseDao> addFileImagen(List<LibroBibliotecaResponseDao> list) {
        List<LibroBibliotecaResponseDao> newList = new ArrayList<>();
        for (LibroBibliotecaResponseDao object : list) {
            LibroBibliotecaResponseDao libroBiblioteca = object;
            ArchivoRequestDao archivoDao = archivoRepository.getFilePrimary("libro_biblioteca",
                    libroBiblioteca.getId());
            libroBiblioteca.setSisImagen(archivoDao);
            newList.add(libroBiblioteca);
        }
        return newList;
    }
}
