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
import com.ec.prontiauto.dao.BancoResponseDao;
import com.ec.prontiauto.entidad.Banco;
import com.ec.prontiauto.mapper.BancoMapper;

@Service
public class BancoRepositoryImpl extends AbstractRepository<Banco, Integer> {

    @Autowired
    private ArchivoRepositoryImpl archivoRepository;

    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
        String dbQuery = "SELECT e FROM Banco e WHERE (lower(e.nombre) like lower(:busqueda)) ";
        List<String> listFilters = new ArrayList<>();
        listFilters.add("sisHabilitado");
        Query queryEM = this.CreateQueryWithFilters(listFilters, params, dbQuery)
                .setParameter("busqueda", "%" + params.get("busqueda") + "%");
        int countResults = queryEM.getResultList().size();
        List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize()).getResultList();
        List<BancoResponseDao> listResponseDao = BancoMapper.setEntityListToDaoReferenceList
                .apply((List<Banco>) listResponse);

        return this.getResponse(listResponseDao, countResults);
    }

    public List<BancoResponseDao> addFileArchivo(List<BancoResponseDao> list) {
        List<BancoResponseDao> newList = new ArrayList<>();
        for (BancoResponseDao object : list) {
            BancoResponseDao banco = object;
            ArchivoRequestDao archivoDao = archivoRepository.getFilePrimary("banco",
                    banco.getId());
            banco.setSisArchivo(archivoDao);
            newList.add(banco);
        }
        return newList;
    }

    public List<BancoResponseDao> addFileImagen(List<BancoResponseDao> list) {
        List<BancoResponseDao> newList = new ArrayList<>();
        for (BancoResponseDao object : list) {
            BancoResponseDao banco = object;
            ArchivoRequestDao archivoDao = archivoRepository.getFilePrimary("banco",
                    banco.getId());
            banco.setSisImagen(archivoDao);
            newList.add(banco);
        }
        return newList;
    }

    public List<BancoResponseDao> addFileArchivoAndImagen(List<BancoResponseDao> list) {
        List<BancoResponseDao> newList = new ArrayList<>();
        newList = addFileArchivo(list);
        newList = addFileImagen(newList);
        return newList;
    }
}
