package com.ec.prontiauto.repositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.prontiauto.abstracts.AbstractRepository;
import com.ec.prontiauto.dao.ArchivoRequestDao;
import com.ec.prontiauto.entidad.Archivo;
import com.ec.prontiauto.mapper.ArchivoMapper;

@Service
public class ArchivoRepositoryImpl extends AbstractRepository<Archivo, Integer> {

    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
        String dbQuery = "SELECT e FROM Archivo e WHERE e.tipoArchivo=:tipoArchivo ";
        List<String> listFilters = new ArrayList<>();
        listFilters.add("sisHabilitado");
        listFilters.add("nombreTabla");
        listFilters.add("idTabla");
        Query queryEM = this.CreateQueryWithFilters(listFilters, params, dbQuery).setParameter("tipoArchivo",
                "S");
        int countReults = queryEM.getResultList().size();
        List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        List<ArchivoRequestDao> listResponseDao = ArchivoMapper.setEntityListToDaoResponseList
                .apply((List<Archivo>) listResponse);
        return this.getResponse(listResponseDao, countReults);
    }

    public ArchivoRequestDao getFilePrimary(String nombreTabla, Integer idTabla) {
        String dbQuery = "SELECT b FROM Archivo b WHERE b.nombreTabla=:nombreTabla AND b.idTabla=:idTabla and b.tipoArchivo=:tipoArchivo";
        List<?> list = this.customQuery(dbQuery)
                .setParameter("nombreTabla", nombreTabla)
                .setParameter("tipoArchivo", "P")
                .setParameter("idTabla", idTabla.toString()).getResultList();
        Archivo newArchivo = !list.isEmpty() ? (Archivo) list.get(0) : new Archivo();
        return newArchivo.getId() != null ? ArchivoMapper.setEntityToDaoResponse.apply(newArchivo) : null;
    }

    public boolean existFilePrimary(Archivo entity, Integer id) {

        boolean existDb = false;
        String query = "select f from Archivo f where f.tipoArchivo=:tipoArchivo and f.idTabla=:idTabla and f.nombreTabla=:nombreTabla";
        List<?> exist = this.customQuery(query).setParameter("tipoArchivo", "P")
                .setParameter("idTabla", entity.getIdTabla())
                .setParameter("nombreTabla", entity.getNombreTabla()).getResultList();
        if (exist.size() > 0) {
            Archivo archivo = (Archivo) exist.get(0);
            if (archivo.getIdTabla().equals(entity.getIdTabla())
                    && archivo.getNombreTabla().equals(entity.getNombreTabla())
                    && archivo.getTipoArchivo().equals(entity.getTipoArchivo())) {
                if (id != null && id != archivo.getId()) {
                    existDb = true;
                } else if (id == null) {
                    existDb = true;
                }
            }
        }
        return existDb;
    }

}
