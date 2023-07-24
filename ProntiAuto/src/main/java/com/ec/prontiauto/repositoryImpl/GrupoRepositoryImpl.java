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
import com.ec.prontiauto.dao.GrupoResponseDao;
import com.ec.prontiauto.entidad.Grupo;
import com.ec.prontiauto.mapper.GrupoMapper;

@Service
public class GrupoRepositoryImpl extends AbstractRepository<Grupo, Integer> {

    @Autowired
    private ArchivoRepositoryImpl archivoRepository;

    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
        String busqueda = params.get("busqueda").toString();
        String dbQuery = "SELECT e FROM Grupo e "
                + " left join ClienteEnGrupo cg on cg.idGrupo = e.id "
                + " left join Cliente c on c.id=cg.idCliente "
                + " left join Usuario u on u.id=c.idUsuario WHERE "
                + " lower(cast(e.nombreGrupo as string)) like lower(:busqueda) "
                + " OR lower(u.nombres) like lower(:busqueda) "
                + " OR lower(u.apellidos) like lower(:busqueda) ";
        List<String> listFilters = new ArrayList<>();
        listFilters.add("sisHabilitado");
        Query queryEM = this.CreateQueryWithFilters(listFilters, params, dbQuery).setParameter("busqueda",
                "%" + busqueda + "%");
        int countResults = queryEM.getResultList().size();
        List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize()).getResultList();
        List<GrupoResponseDao> listResponseDao = GrupoMapper.setEntityListToDaoResponseList
                .apply((List<Grupo>) listResponse);
        return this.getResponse(listResponseDao, countResults);
    }

    public List<GrupoResponseDao> addFileArchivo(List<GrupoResponseDao> list) {
        List<GrupoResponseDao> newList = new ArrayList<>();
        for (GrupoResponseDao object : list) {
            GrupoResponseDao grupo = object;
            ArchivoRequestDao archivoDao = archivoRepository.getFilePrimary("grupo",
                    grupo.getId());
            grupo.setSisArchivo(archivoDao);
            newList.add(grupo);
        }
        return newList;
    }

    public List<GrupoResponseDao> addFileImagen(List<GrupoResponseDao> list) {
        List<GrupoResponseDao> newList = new ArrayList<>();
        for (GrupoResponseDao object : list) {
            GrupoResponseDao grupo = object;
            ArchivoRequestDao archivoDao = archivoRepository.getFilePrimary("grupo",
                    grupo.getId());
            grupo.setSisImagen(archivoDao);
            newList.add(grupo);
        }
        return newList;
    }

    public List<GrupoResponseDao> addFileArchivoAndImagen(List<GrupoResponseDao> list) {
        List<GrupoResponseDao> newList = new ArrayList<>();
        newList = addFileArchivo(list);
        newList = addFileImagen(newList);
        return newList;
    }

    public String actializarMontoMeta(Integer idGrupo) {
        String[] params = { "idGrupo" };
        Object[] values = { idGrupo };
        return this.callStoreProcedure("actualizar_monto_meta", params, values);
    }
}
