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
import com.ec.prontiauto.dao.SupervisorResponseDao;
import com.ec.prontiauto.entidad.Supervisor;
import com.ec.prontiauto.mapper.SupervisorMapper;

@Service
public class SupervisorRepositoryImpl extends AbstractRepository<Supervisor, Integer> {

    @Autowired
    private ArchivoRepositoryImpl archivoRepository;

    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
        String dbQuery = "SELECT DISTINCT (e) FROM Supervisor e , Trabajador t, Usuario u, Agencia a WHERE (lower(u.nombres) like lower(:busqueda) OR lower(u.apellidos) like lower(:busqueda) OR lower(u.documentoIdentidad) like lower(:busqueda)) "
        		+ " and (e.idTrabajador=t.id) and (u.id=t.idUsuario) and (e.idAgencia=a.id) ";
        List<String> listFilters = new ArrayList<>();
        listFilters.add("e.sisHabilitado");
        listFilters.add("a.nombre");
        listFilters.add("e.idTrabajador");
        listFilters.add("u.idUsuario");
        listFilters.add("e.idAgencia");
     
        Query queryEM = this.CreateQueryWithFiltersAndJoin(listFilters, params, dbQuery)
                .setParameter("busqueda", "%" + params.get("busqueda") + "%");
        int countResults = queryEM.getResultList().size();
        List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize()).getResultList();
        List<SupervisorResponseDao> listResponseDao = SupervisorMapper.setEntityListToDaoResponseList
                .apply((List<Supervisor>) listResponse);

        return this.getResponse(listResponseDao, countResults);
    }

    public List<SupervisorResponseDao> addFileArchivo(List<SupervisorResponseDao> list) {
        List<SupervisorResponseDao> newList = new ArrayList<>();
        for (SupervisorResponseDao object : list) {
            SupervisorResponseDao Supervisor = object;
            ArchivoRequestDao archivoDao = archivoRepository.getFilePrimary("supervisor",
                    Supervisor.getId());
            Supervisor.setSisArchivo(archivoDao);
            newList.add(Supervisor);
        }
        return newList;
    }

    public List<SupervisorResponseDao> addFileImagen(List<SupervisorResponseDao> list) {
        List<SupervisorResponseDao> newList = new ArrayList<>();
        for (SupervisorResponseDao object : list) {
            SupervisorResponseDao Supervisor = object;
            ArchivoRequestDao archivoDao = archivoRepository.getFilePrimary("supervisor",
                    Supervisor.getId());
            Supervisor.setSisImagen(archivoDao);
            newList.add(Supervisor);
        }
        return newList;
    }

    public List<SupervisorResponseDao> addFileArchivoAndImagen(List<SupervisorResponseDao> list) {
        List<SupervisorResponseDao> newList = new ArrayList<>();
        newList = addFileArchivo(list);
        newList = addFileImagen(newList);
        return newList;
    }
}
