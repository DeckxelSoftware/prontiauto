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
import com.ec.prontiauto.dao.ClienteResponseDao;
import com.ec.prontiauto.entidad.Cliente;
import com.ec.prontiauto.entidad.Empresa;
import com.ec.prontiauto.entidad.Usuario;
import com.ec.prontiauto.mapper.ClienteMapper;

@Service
public class ClienteRepositoryImpl extends AbstractRepository<Cliente, Integer> {

    @Autowired
    private ArchivoRepositoryImpl archivoRepository;

    @Override
    public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
        String dbQuery = "SELECT DISTINCT(e) FROM Cliente e, Usuario u, Empresa em WHERE (e.idUsuario = u.id AND e.idEmpresa = em.id)"
                + " and (lower(u.nombres) LIKE lower(:busqueda) OR lower(u.apellidos) LIKE lower(:busqueda)"
                + " OR lower(u.documentoIdentidad) LIKE lower(:busqueda) OR lower(em.nombreComercial) LIKE lower(:busqueda)"
                + " OR lower(em.razonSocial) LIKE lower(:busqueda) OR lower(em.rucEmpresa) LIKE lower(:busqueda)) ";
        List<String> listFilters = new ArrayList<>();
        listFilters.add("e.sisHabilitado");
        listFilters.add("e.tipoCliente");
        listFilters.add("e.idUsuario");
        listFilters.add("e.idEmpresa");
        Query queryEM = this.CreateQueryWithFiltersAndJoin(listFilters, params, dbQuery)
                .setParameter("busqueda", "%" + params.get("busqueda") + "%");
        int countResults = queryEM.getResultList().size();
        List<?> listResponse = queryEM.setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize()).getResultList();
        List<ClienteResponseDao> listResponseDao = ClienteMapper.setEntityListToDaoReferenceList
                .apply((List<Cliente>) listResponse);
        return this.getResponse(listResponseDao, countResults);
    }

    public List<ClienteResponseDao> addFileArchivo(List<ClienteResponseDao> list) {
        List<ClienteResponseDao> newList = new ArrayList<>();
        for (ClienteResponseDao object : list) {
            ClienteResponseDao cliente = object;
            ArchivoRequestDao archivoDao = archivoRepository.getFilePrimary("cliente",
                    cliente.getId());
            cliente.setSisArchivo(archivoDao);
            newList.add(cliente);
        }
        return newList;
    }

    public List<ClienteResponseDao> addFileImagen(List<ClienteResponseDao> list) {
        List<ClienteResponseDao> newList = new ArrayList<>();
        for (ClienteResponseDao object : list) {
            ClienteResponseDao cliente = object;
            ArchivoRequestDao archivoDao = archivoRepository.getFilePrimary("cliente",
                    cliente.getId());
            cliente.setSisImagen(archivoDao);
            newList.add(cliente);
        }
        return newList;
    }

    public List<ClienteResponseDao> addFileArchivoAndImagen(List<ClienteResponseDao> list) {
        List<ClienteResponseDao> newList = new ArrayList<>();
        newList = addFileArchivo(list);
        newList = addFileImagen(newList);
        return newList;
    }

    public Cliente findByIdCliente(Integer id) {
        Cliente antiguo = (Cliente) this.customQuery("SELECT a FROM Cliente a WHERE a.id=:id").setParameter("id", id)
                .getSingleResult();
        if (antiguo.getIdUsuario() != null && antiguo.getIdUsuario().getId() != null) {
            Usuario usuario = (Usuario) this.customQuery("SELECT u FROM Usuario u WHERE u.id=:id")
                    .setParameter("id", antiguo.getIdUsuario().getId())
                    .getSingleResult();
            antiguo.setIdUsuario(usuario);
        }
        if (antiguo.getIdEmpresa() != null && antiguo.getIdEmpresa().getId() != null) {
            Empresa empresa = (Empresa) this.customQuery("SELECT e FROM Empresa e WHERE e.id=:id")
                    .setParameter("id", antiguo.getIdEmpresa().getId())
                    .getSingleResult();
            antiguo.setIdEmpresa(empresa);
        }
        return antiguo;
    }
}