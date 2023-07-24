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
import com.ec.prontiauto.dao.RolUsuarioRequestDao;
import com.ec.prontiauto.dao.RolUsuarioResponseDao;
import com.ec.prontiauto.entidad.Rol;
import com.ec.prontiauto.entidad.RolUsuario;
import com.ec.prontiauto.entidad.Usuario;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.mapper.RolUsuarioMapper;
import com.ec.prontiauto.repositoryImpl.RolUsuarioRepositoryImpl;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/rol-usuario")
//@CrossOrigingin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
@Api(tags = "Rol usuarios", description = "Gestion de rolUsuario")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class RolUsuarioController
        extends AbstractController<RolUsuario, RolUsuarioRequestDao, RolUsuarioResponseDao, Integer> {

    private GenericMethods genericMethods = new GenericMethods();

    @Autowired
    private RolUsuarioRepositoryImpl rolUsuarioRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public RolUsuarioResponseDao devolverRespuestaDao(RolUsuario entity) {
        return RolUsuarioMapper.setEntityToDaoResponse.apply(entity);
    }

    @Override
    @Transactional
    public RolUsuario devolverRespuestaUpdate(RolUsuario entity, Integer id) {
        RolUsuario antiguo = (RolUsuario) genericMethods.findById("RolUsuario", entityManager, id);
        if (antiguo != null) {
            antiguo = antiguo.setValoresDiferentes(antiguo, entity);
        }
        return antiguo;
    }

    @Override
    public RolUsuario setDaoRequestToEntity(RolUsuarioRequestDao dao) {
        return RolUsuarioMapper.setDaoRequestToEntity.apply(dao);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> findBySearchAndUpdate(String sisHabilitado, Integer idRol, Integer idUsuario,
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
            Usuario usuario = new Usuario();
            usuario.setId(idUsuario);
            params.put("idUsuario", idUsuario == null ? "" : usuario);
            String sortFieldDefault = "id";
            sortField = sortField == null ? sortFieldDefault : sortField;
            sortAscending = sortAscending == null ? true : sortAscending;
            List<Object> list = this.findWithSkipAndTake(
                    this.rolUsuarioRepository,
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
