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
import com.ec.prontiauto.dao.TrabajadorRequestDao;
import com.ec.prontiauto.dao.TrabajadorResponseDao;
import com.ec.prontiauto.entidad.Trabajador;
import com.ec.prontiauto.entidad.Usuario;
import com.ec.prontiauto.exception.ApiRequestException;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.mapper.TrabajadorMapper;
import com.ec.prontiauto.repositoryImpl.TrabajadorRepositoryImpl;
import com.ec.prontiauto.validations.TrabajadorValidation;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/trabajador")
// @CrossOrigingin(origins = "*", methods = { RequestMethod.GET,
// RequestMethod.POST, RequestMethod.PUT })
@Api(tags = "Trabajador", description = "Gestión de trabajador")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class TrabajadorController
        extends AbstractController<Trabajador, TrabajadorRequestDao, TrabajadorResponseDao, Integer> {

    private GenericMethods genericMethods = new GenericMethods();

    @Autowired
    private TrabajadorRepositoryImpl trabajadorRepositoryImpl;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public TrabajadorResponseDao devolverRespuestaDao(Trabajador entity) {
        return TrabajadorMapper.setEntityToDaoReference.apply(entity);
    }

    @Override
    @Transactional
    public Trabajador devolverRespuestaUpdate(Trabajador entity, Integer id) {
        Trabajador antiguo = (Trabajador) genericMethods.findById("Trabajador", entityManager, id);
        if (antiguo != null) {
            antiguo = antiguo.setValoresDiferentes(antiguo, entity);
        }
        return antiguo;
    }

    @Override
    public Trabajador setDaoRequestToEntity(TrabajadorRequestDao dao) {
        return TrabajadorMapper.setDaoRequestToEntity.apply(dao);
    }

    @Override
    public Trabajador validationRequestEntityOnlyUpdate(Trabajador entity) {
        TrabajadorValidation val = new TrabajadorValidation(entityManager, entity, true);

        entity = val.getEntity();
        return entity;
    }

    @Override
    public Trabajador validationRequestEntityOnlyPost(Trabajador entity) {
        TrabajadorValidation val = new TrabajadorValidation(entityManager, entity, false);
        entity.setId(null);
        entity = val.getEntity();
        return entity;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> findBySearchAndUpdate(
            String busqueda,
            String sisHabilitado,
            String estadoCivil,
            String genero,
            String grupoSanguineo,
            String nivelEstudios,
            String profesion,
            String estadoFamiliar,
            String discapacidad,
            String tipoDiscapacidad,
            Integer idUsuario,
            String idUsuarioPais,
            String idUsuarioProvincia,
            String idUsuarioCiudad,
            Integer id,
            Integer skip,
            Integer take, String sortField, Boolean sortAscending) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("busqueda", busqueda == null ? "" : busqueda);
            params.put("sisHabilitado", sisHabilitado == null ? "" : sisHabilitado);
            params.put("estadoCivil", estadoCivil == null ? "" : estadoCivil);
            params.put("genero", genero == null ? "" : genero);
            params.put("grupoSanguineo", grupoSanguineo == null ? "" : grupoSanguineo);
            params.put("nivelEstudios", nivelEstudios == null ? "" : nivelEstudios);
            params.put("profesion", profesion == null ? "" : profesion);
            params.put("estadoFamiliar", estadoFamiliar == null ? "" : estadoFamiliar);
            params.put("discapacidad", discapacidad == null ? "" : discapacidad);
            params.put("tipoDiscapacidad", tipoDiscapacidad == null ? "" : tipoDiscapacidad);

            Usuario usuario = new Usuario();
            usuario.setId(idUsuario);
            params.put("idUsuario", idUsuario == null ? "" : usuario);

            params.put("pais", idUsuarioPais == null ? "" : idUsuarioPais);
            params.put("provincia", idUsuarioProvincia == null ? "" : idUsuarioProvincia);
            params.put("ciudad", idUsuarioCiudad == null ? "" : idUsuarioCiudad);
            params.put("id", id == null ? "" : id);

            String sortFieldDefault = "id";
            sortField = sortField == null ? sortFieldDefault : sortField;
            sortAscending = sortAscending == null ? true : sortAscending;
            List<Object> list = this.findWithSkipAndTake(
                    this.trabajadorRepositoryImpl,
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

    @RequestMapping(value = "/custom", method = RequestMethod.GET)
    public ResponseEntity<?> busquedaCustom(String busqueda,
            Integer id,
            String sisHabilitado,
            String estadoCivil,
            String genero,
            String grupoSanguineo,
            String nivelEstudios,
            String profesion,
            String estadoFamiliar,
            String discapacidad,
            String tipoDiscapacidad,
            Integer idUsuario,
            String idUsuarioPais,
            String idUsuarioProvincia,
            String idUsuarioCiudad,
            Integer skip,
            Integer take,
            String sortField,
            Boolean sortAscending) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        try {
            httpHeaders.add("STATUS", "200");
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("tra.id", id);
            map.put("tra.\"sisHabilitado\"", sisHabilitado);
            map.put("tra.\"estadoCivil\"", estadoCivil);
            map.put("tra.genero", genero);
            map.put("tra.\"grupoSanguineo\"", grupoSanguineo);
            map.put("tra.\"nivelEstudios\"", nivelEstudios);
            map.put("tra.profesion", profesion);
            map.put("tra.\"estadoFamiliar\"", estadoFamiliar);
            map.put("tra.discapacidad", discapacidad);
            map.put("tra.\"tipoDiscapacidad\"", tipoDiscapacidad);
            map.put("tra.\"idUsuario\"", idUsuario);
            map.put("usu.pais", idUsuarioPais);
            map.put("usu.provincia", idUsuarioProvincia);
            map.put("usu.ciudad", idUsuarioCiudad);

            Object[][] req = { { "busqueda", busqueda == null ? "" : busqueda }, { "skip", skip == null ? 0 : skip },
                    { "take", take == null ? 10 : take },
                    { "sortField", sortField == null ? "id" : sortField },
                    { "sortAscending", sortAscending == null || sortAscending == true ? "asc" : "desc" },
                    { map } };
            Object response = this.trabajadorRepositoryImpl.obtenerTrabajadores(req, map);
            return new ResponseEntity<Object>(response, httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("-------------------\n" + e.getMessage());
            throw new ApiRequestException(e.getMessage());
        }
    }

}
