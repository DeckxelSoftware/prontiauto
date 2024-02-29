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
import com.ec.prontiauto.dao.HistoricoPlanContratoResponseDao;
import com.ec.prontiauto.entidad.HistoricoPlanContrato;
import com.ec.prontiauto.mapper.HistoricoPlanContratoMapper;

@Service
public class HistoricoPlanContratoRepositoryImpl extends AbstractRepository<HistoricoPlanContrato, Integer> {

    @Autowired
    private ArchivoRepositoryImpl archivoRepository;

    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
        String dbQuery = "SELECT DISTINCT (e) FROM HistoricoPlanContrato e WHERE 1=1  ";
        List<String> listFilters = new ArrayList<>();
        listFilters.add("idContrato");
        listFilters.add("id");
        Query queryEM = this.archivoRepository.CreateQueryWithFilters(listFilters, params, dbQuery);
       List<Object> objects= queryEM.getResultList();
        int countResults = objects!=null?objects.size():0;
        List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize()).getResultList();
        List<HistoricoPlanContratoResponseDao> listResponseDao = HistoricoPlanContratoMapper.setEntityListToDaoResponseList
                .apply((List<HistoricoPlanContrato>) listResponse);

        return this.getResponse(listResponseDao, countResults);
    }

    public List<HistoricoPlanContratoResponseDao> addFileArchivo(List<HistoricoPlanContratoResponseDao> list) {
        List<HistoricoPlanContratoResponseDao> newList = new ArrayList<>();
        for (HistoricoPlanContratoResponseDao object : list) {
            HistoricoPlanContratoResponseDao HistoricoPlanContrato = object;
            ArchivoRequestDao archivoDao = archivoRepository.getFilePrimary("cuenta_bancaria_empresa",
                    HistoricoPlanContrato.getId());
            HistoricoPlanContrato.setSisArchivo(archivoDao);
            newList.add(HistoricoPlanContrato);
        }
        return newList;
    }

    public List<HistoricoPlanContratoResponseDao> addFileImagen(List<HistoricoPlanContratoResponseDao> list) {
        List<HistoricoPlanContratoResponseDao> newList = new ArrayList<>();
        for (HistoricoPlanContratoResponseDao object : list) {
            HistoricoPlanContratoResponseDao HistoricoPlanContrato = object;
            ArchivoRequestDao archivoDao = archivoRepository.getFilePrimary("cuenta_bancaria_empresa",
                    HistoricoPlanContrato.getId());
            HistoricoPlanContrato.setSisImagen(archivoDao);
            newList.add(HistoricoPlanContrato);
        }
        return newList;
    }

    public List<HistoricoPlanContratoResponseDao> addFileArchivoAndImagen(List<HistoricoPlanContratoResponseDao> list) {
        List<HistoricoPlanContratoResponseDao> newList = new ArrayList<>();
        newList = addFileArchivo(list);
        newList = addFileImagen(newList);
        return newList;
    }
}
