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
import com.ec.prontiauto.dao.EmpresaResponseDao;
import com.ec.prontiauto.entidad.Empresa;
import com.ec.prontiauto.exception.ApiRequestException;
import com.ec.prontiauto.mapper.EmpresaMapper;

@Service
public class EmpresaRepositoryImpl extends AbstractRepository<Empresa, Integer> {

    @Autowired
    private ArchivoRepositoryImpl archivoRepository;

    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
        String dbQuery = "SELECT e FROM Empresa e WHERE (lower(e.nombreComercial) like lower(:busqueda)"
                + " or lower(e.razonSocial) like lower(:busqueda)"
                + " or lower(e.rucEmpresa) like lower(:busqueda)) ";
        List<String> listFilters = new ArrayList<>();
        listFilters.add("sisHabilitado");
        listFilters.add("tipoEmpresa");
        listFilters.add("obligadoLlevarContabilidad");
        listFilters.add("agenteRetencion");
        Query queryEM = this.CreateQueryWithFilters(listFilters, params, dbQuery)
                .setParameter("busqueda", "%" + params.get("busqueda") + "%");
        int countResults = queryEM.getResultList().size();
        List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize()).getResultList();
        List<EmpresaResponseDao> listResponseDao = EmpresaMapper.setEntityListToDaoResponseList
                .apply((List<Empresa>) listResponse);
        return this.getResponse(listResponseDao, countResults);
    }

    public Empresa createEmpresa(Empresa empresa) {
        try {
            this.create(empresa);
            String query = "select u from Empresa u where u.rucEmpresa=:rucEmpresa";
            Empresa newEmpresa = (Empresa) this.customQuery(query).setParameter("rucEmpresa", empresa.getRucEmpresa())
                    .getSingleResult();
            return newEmpresa;
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage());
        }
    }

    public Empresa updateEmpresa(Empresa empresa) {
        try {
            this.update(empresa);
            String query = "select u from Empresa u where u.rucEmpresa=:rucEmpresa";
            Empresa newEmpresa = (Empresa) this.customQuery(query).setParameter("rucEmpresa", empresa.getRucEmpresa())
                    .getSingleResult();
            return newEmpresa;
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage());
        }
    }

    public List<EmpresaResponseDao> addFileArchivo(List<EmpresaResponseDao> list) {
        List<EmpresaResponseDao> newList = new ArrayList<>();
        for (EmpresaResponseDao object : list) {
            EmpresaResponseDao empresa = object;
            ArchivoRequestDao archivoDao = archivoRepository.getFilePrimary("empresa",
                    empresa.getId());
            empresa.setSisArchivo(archivoDao);
            newList.add(empresa);
        }
        return newList;
    }

    public List<EmpresaResponseDao> addFileImagen(List<EmpresaResponseDao> list) {
        List<EmpresaResponseDao> newList = new ArrayList<>();
        for (EmpresaResponseDao object : list) {
            EmpresaResponseDao empresa = object;
            ArchivoRequestDao archivoDao = archivoRepository.getFilePrimary("empresa",
                    empresa.getId());
            empresa.setSisImagen(archivoDao);
            newList.add(empresa);
        }
        return newList;
    }

    public List<EmpresaResponseDao> addFileArchivoAndImagen(List<EmpresaResponseDao> list) {
        List<EmpresaResponseDao> newList = new ArrayList<>();
        newList = addFileArchivo(list);
        newList = addFileImagen(newList);
        return newList;
    }
}
