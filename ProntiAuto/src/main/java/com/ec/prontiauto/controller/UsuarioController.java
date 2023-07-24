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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ec.prontiauto.abstracts.AbstractController;
import com.ec.prontiauto.abstracts.GenericMethods;
import com.ec.prontiauto.dao.ActualizarPasswordDao;
import com.ec.prontiauto.dao.UsuarioRequestDao;
import com.ec.prontiauto.dao.UsuarioResponseDao;
import com.ec.prontiauto.entidad.Usuario;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.mapper.UsuarioMapper;
import com.ec.prontiauto.repositoryImpl.UsuarioRepositoryImpl;
import org.springframework.web.bind.annotation.CrossOrigin;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
@Api(tags = "Usuarios", description = "Gestion de usuarios")
public class UsuarioController extends AbstractController<Usuario, UsuarioRequestDao, UsuarioResponseDao, Integer> {

    private GenericMethods genericMethods = new GenericMethods();

    @Autowired
    private UsuarioRepositoryImpl usuarioRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UsuarioResponseDao devolverRespuestaDao(Usuario entity) {
        return UsuarioMapper.setEntityToDaoResponse.apply(entity);
    }

    @Override
    @Transactional
    public Usuario devolverRespuestaUpdate(Usuario entity, Integer id) {
        Usuario antiguo = (Usuario) genericMethods.findById("Usuario", entityManager, id);
        if (antiguo != null) {
            antiguo = antiguo.setValoresDiferentes(antiguo, entity);
        }
        return antiguo;
    }

    @Override
    public Usuario setDaoRequestToEntity(UsuarioRequestDao dao) {
        return UsuarioMapper.setDaoRequestToEntity.apply(dao);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> findBySearchAndUpdate(String busqueda,
            String sisHabilitado, String tipoDocumentoIdentidad, String pais, String provincia,
            String ciudad, Integer skip,
            Integer take, String sortField, Boolean sortAscending) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("busqueda", busqueda == null ? "" : busqueda);
            params.put("sisHabilitado", sisHabilitado == null ? "" : sisHabilitado);
            params.put("tipoDocumentoIdentidad", tipoDocumentoIdentidad == null ? "" : tipoDocumentoIdentidad);
            params.put("pais", pais == null ? "" : pais);
            params.put("provincia", provincia == null ? "" : provincia);
            params.put("ciudad", ciudad == null ? "" : ciudad);
            String sortFieldDefault = "id";
            sortField = sortField == null ? sortFieldDefault : sortField;
            sortAscending = sortAscending == null ? true : sortAscending;
            List<Object> list = this.findWithSkipAndTake(
                    this.usuarioRepository,
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

    @RequestMapping(path = "/password/{id}", method = RequestMethod.PUT)
    @Transactional
    public ResponseEntity<?> updatePassword(@RequestBody ActualizarPasswordDao actualizarPasswordDao,
            @PathVariable Integer id) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        try {
            httpHeaders.add("STATUS", "200");
            usuarioRepository.updatePassword(actualizarPasswordDao.getPasswordActual(),
                    actualizarPasswordDao.getPasswordNuevo(), id);
            return new ResponseEntity<>(
                    new ExceptionResponse("200", "Contraseña actualizada correctamente"), httpHeaders,
                    HttpStatus.OK);
        } catch (Exception e) {
            httpHeaders.add("STATUS", "400");
            return new ResponseEntity<ExceptionResponse>(new ExceptionResponse("400", e.getMessage()), httpHeaders,
                    HttpStatus.BAD_REQUEST);
        }
    }
}
