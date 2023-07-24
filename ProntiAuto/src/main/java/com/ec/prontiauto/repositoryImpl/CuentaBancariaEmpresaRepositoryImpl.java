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
import com.ec.prontiauto.dao.CuentaBancariaEmpresaResponseDao;
import com.ec.prontiauto.entidad.CuentaBancariaEmpresa;
import com.ec.prontiauto.mapper.CuentaBancariaEmpresaMapper;

@Service
public class CuentaBancariaEmpresaRepositoryImpl extends AbstractRepository<CuentaBancariaEmpresa, Integer> {

    @Autowired
    private ArchivoRepositoryImpl archivoRepository;

    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
        String dbQuery = "SELECT DISTINCT(e) FROM CuentaBancariaEmpresa e, Empresa j WHERE (lower(e.numeroCuenta) like lower(:busqueda)) and (e.idEmpresa=j.id) ";
        List<String> listFilters = new ArrayList<>();
        listFilters.add("e.sisHabilitado");
        listFilters.add("e.tipoCuenta");
        listFilters.add("j.rucEmpresa");
        listFilters.add("e.idEmpresa");
        listFilters.add("e.idBanco");
        listFilters.add("e.id");
        Query queryEM = this.CreateQueryWithFiltersAndJoin(listFilters, params, dbQuery)
                .setParameter("busqueda", "%" + params.get("busqueda") + "%");
        int countResults = queryEM.getResultList().size();
        List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize()).getResultList();
        List<CuentaBancariaEmpresaResponseDao> listResponseDao = CuentaBancariaEmpresaMapper.setEntityListToDaoResponseList
                .apply((List<CuentaBancariaEmpresa>) listResponse);

        return this.getResponse(listResponseDao, countResults);
    }

    public List<CuentaBancariaEmpresaResponseDao> addFileArchivo(List<CuentaBancariaEmpresaResponseDao> list) {
        List<CuentaBancariaEmpresaResponseDao> newList = new ArrayList<>();
        for (CuentaBancariaEmpresaResponseDao object : list) {
            CuentaBancariaEmpresaResponseDao cuentaBancariaEmpresa = object;
            ArchivoRequestDao archivoDao = archivoRepository.getFilePrimary("cuenta_bancaria_empresa",
                    cuentaBancariaEmpresa.getId());
            cuentaBancariaEmpresa.setSisArchivo(archivoDao);
            newList.add(cuentaBancariaEmpresa);
        }
        return newList;
    }

    public List<CuentaBancariaEmpresaResponseDao> addFileImagen(List<CuentaBancariaEmpresaResponseDao> list) {
        List<CuentaBancariaEmpresaResponseDao> newList = new ArrayList<>();
        for (CuentaBancariaEmpresaResponseDao object : list) {
            CuentaBancariaEmpresaResponseDao cuentaBancariaEmpresa = object;
            ArchivoRequestDao archivoDao = archivoRepository.getFilePrimary("cuenta_bancaria_empresa",
                    cuentaBancariaEmpresa.getId());
            cuentaBancariaEmpresa.setSisImagen(archivoDao);
            newList.add(cuentaBancariaEmpresa);
        }
        return newList;
    }

    public List<CuentaBancariaEmpresaResponseDao> addFileArchivoAndImagen(List<CuentaBancariaEmpresaResponseDao> list) {
        List<CuentaBancariaEmpresaResponseDao> newList = new ArrayList<>();
        newList = addFileArchivo(list);
        newList = addFileImagen(newList);
        return newList;
    }
}
