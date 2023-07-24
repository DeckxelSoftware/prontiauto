package com.ec.prontiauto.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ec.prontiauto.abstracts.AbstractController;
import com.ec.prontiauto.abstracts.GenericMethods;
import com.ec.prontiauto.dao.RolPermisoRequestDao;
import com.ec.prontiauto.dao.RolPermisoResponseDao;
import com.ec.prontiauto.entidad.Permiso;
import com.ec.prontiauto.entidad.Rol;
import com.ec.prontiauto.entidad.RolPermiso;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.mapper.RolPermisoMapper;
import com.ec.prontiauto.repositoryImpl.RolPermisoRepositoryImpl;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/rol-permiso")
//@CrossOrigingin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
@Api(tags = "Rol permisos", description = "Gestion de rolPermiso")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class RolPermisoController
        extends AbstractController<RolPermiso, RolPermisoRequestDao, RolPermisoResponseDao, Integer> {

    private GenericMethods genericMethods = new GenericMethods();

    @Autowired
    private RolPermisoRepositoryImpl rolPermisoRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public RolPermisoResponseDao devolverRespuestaDao(RolPermiso entity) {
        return RolPermisoMapper.setEntityToDaoResponse.apply(entity);
    }

    @Override
    @Transactional
    public RolPermiso devolverRespuestaUpdate(RolPermiso entity, Integer id) {
        RolPermiso antiguo = (RolPermiso) genericMethods.findById("RolPermiso", entityManager, id);
        if (antiguo != null) {
            antiguo = antiguo.setValoresDiferentes(antiguo, entity);
        }
        return antiguo;
    }

    @Override
    public RolPermiso setDaoRequestToEntity(RolPermisoRequestDao dao) {
        return RolPermisoMapper.setDaoRequestToEntity.apply(dao);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> findBySearchAndUpdate(String sisHabilitado, Integer idRol, Integer idPermiso,
            Integer skip,
            Integer take, String sortField, Boolean sortAscending) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("sisHabilitado", sisHabilitado == null ? "" : sisHabilitado);
            Rol rol = new Rol();
            rol.setId(idRol);
            params.put("idRol", idRol == null ? "" : rol);
            Permiso permiso = new Permiso();
            permiso.setId(idPermiso);
            params.put("idPermiso", idPermiso == null ? "" : permiso);
            String sortFieldDefault = "id";
            sortField = sortField == null ? sortFieldDefault : sortField;
            sortAscending = sortAscending == null ? true : sortAscending;

            List<Object> list = this.findWithSkipAndTake(
                    this.rolPermisoRepository,
                    params,
                    skip,
                    take,
                    sortField,
                    sortAscending);
            return new ResponseEntity<>(list, httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
            httpHeaders.add("STATUS", "400");
            return new ResponseEntity<ExceptionResponse>(new ExceptionResponse("400", e.getMessage()), httpHeaders,
                    HttpStatus.BAD_REQUEST);
        }
    }

}
