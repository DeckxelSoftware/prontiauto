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
import com.ec.prontiauto.dao.DetalleNovedadRolPagoRequestDao;
import com.ec.prontiauto.dao.DetalleNovedadRolPagoResponseDao;
import com.ec.prontiauto.entidad.Agencia;
import com.ec.prontiauto.entidad.DetalleNovedadRolPago;
import com.ec.prontiauto.exception.ExceptionResponse;
import com.ec.prontiauto.mapper.DetalleNovedadRolPagoMapper;
import com.ec.prontiauto.repositoryImpl.DetalleNovedadRolPagoRepositoryImpl;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/detalle-novedad-rol-pago")
@Api(tags = "DetalleNovedadRolPago", description = "Gestion DetalleNovedadRolPago")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class DetalleNovedadRolPagoController extends
        AbstractController<DetalleNovedadRolPago, DetalleNovedadRolPagoRequestDao, DetalleNovedadRolPagoResponseDao, Integer> {

    private GenericMethods genericMethods = new GenericMethods();

    @Autowired
    private DetalleNovedadRolPagoRepositoryImpl detalleNovedadRolPagoRepositoryImpl;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public DetalleNovedadRolPagoResponseDao devolverRespuestaDao(DetalleNovedadRolPago entity) {
        return DetalleNovedadRolPagoMapper.setEntityToDaoResponse.apply(entity);
    }

    @Override
    @Transactional
    public DetalleNovedadRolPago devolverRespuestaUpdate(DetalleNovedadRolPago entity, Integer id) {
        DetalleNovedadRolPago antiguo = (DetalleNovedadRolPago) genericMethods.findById("DetalleNovedadRolPago",
                entityManager, id);
        if (antiguo != null) {
            antiguo = antiguo.setValoresDiferentes(antiguo, entity);
        }
        return antiguo;
    }

    @Override
    public DetalleNovedadRolPago setDaoRequestToEntity(DetalleNovedadRolPagoRequestDao dao) {
        return DetalleNovedadRolPagoMapper.setDaoRequestToEntity.apply(dao);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> findBySearchAndUpdate(
            String busqueda,
            Integer idAgencia,
            String sisHabilitado,
            String tipoNovedad,
            Integer skip,
            Integer take, String sortField, Boolean sortAscending) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("busqueda", busqueda == null ? "" : busqueda);
            Agencia agencia = new Agencia();
            agencia.setId(idAgencia);
            params.put("idAgencia", idAgencia == null ? "" : agencia);
            params.put("tipoNovedad", tipoNovedad == null ? "" : tipoNovedad);
            params.put("sisHabilitado", sisHabilitado == null ? "" : sisHabilitado);
            String sortFieldDefault = "id";
            sortField = sortField == null ? sortFieldDefault : sortField;
            sortAscending = sortAscending == null ? true : sortAscending;
            List<Object> list = this.findWithSkipAndTake(
                    this.detalleNovedadRolPagoRepositoryImpl,
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
