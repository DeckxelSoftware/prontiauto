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
import com.ec.prontiauto.dao.VendedorResponseDao;
import com.ec.prontiauto.entidad.Vendedor;
import com.ec.prontiauto.mapper.VendedorMapper;

@Service
public class VendedorRepositoryImpl extends AbstractRepository<Vendedor, Integer> {

	@Autowired
	private ArchivoRepositoryImpl archivoRepository;

	@Override
	public List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable) {
		String dbQuery = "SELECT DISTINCT (e) FROM Vendedor e , Trabajador t, Usuario u, Agencia a WHERE (u.nombres like :busqueda OR u.apellidos like :busqueda OR u.documentoIdentidad like :busqueda) "
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
		List<VendedorResponseDao> listResponseDao = VendedorMapper.setEntityListToDaoResponseList
				.apply((List<Vendedor>) listResponse);

		return this.getResponse(listResponseDao, countResults);
	}

	public List<VendedorResponseDao> addFileArchivo(List<VendedorResponseDao> list) {
		List<VendedorResponseDao> newList = new ArrayList<>();
		for (VendedorResponseDao object : list) {
			VendedorResponseDao Vendedor = object;
			ArchivoRequestDao archivoDao = archivoRepository.getFilePrimary("Vendedor",
					Vendedor.getId());
			Vendedor.setSisArchivo(archivoDao);
			newList.add(Vendedor);
		}
		return newList;
	}

	public List<VendedorResponseDao> addFileImagen(List<VendedorResponseDao> list) {
		List<VendedorResponseDao> newList = new ArrayList<>();
		for (VendedorResponseDao object : list) {
			VendedorResponseDao Vendedor = object;
			ArchivoRequestDao archivoDao = archivoRepository.getFilePrimary("Vendedor",
					Vendedor.getId());
			Vendedor.setSisImagen(archivoDao);
			newList.add(Vendedor);
		}
		return newList;
	}

	public List<VendedorResponseDao> addFileArchivoAndImagen(List<VendedorResponseDao> list) {
		List<VendedorResponseDao> newList = new ArrayList<>();
		newList = addFileArchivo(list);
		newList = addFileImagen(newList);
		return newList;
	}

}
