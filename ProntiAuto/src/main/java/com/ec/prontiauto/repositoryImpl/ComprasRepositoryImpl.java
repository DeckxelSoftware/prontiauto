package com.ec.prontiauto.repositoryImpl;

import com.ec.prontiauto.dao.CabeceraCompraRequestDao;
import com.ec.prontiauto.dao.DetalleCompraFacturaDao;
import com.ec.prontiauto.dao.DetalleImpuestoFacturaDao;
import com.ec.prontiauto.entidad.Archivo;
import com.ec.prontiauto.entidad.CabeceraCompra;
import com.ec.prontiauto.entidad.DetalleCompra;
import com.ec.prontiauto.entidad.DetalleImpuesto;
import com.ec.prontiauto.mapper.ComprasMapper;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;

@Repository
@Transactional
public class ComprasRepositoryImpl {

    @PersistenceContext
    EntityManager em;

    public Object crearFactura(CabeceraCompraRequestDao cabeceraCompraRequestDao){
        CabeceraCompra cabeceraCompra = ComprasMapper.setDaoRequestToCabeceraCompraEntity.apply(cabeceraCompraRequestDao);
        em.persist(cabeceraCompra);

        for (DetalleCompraFacturaDao detalleCompraFacturaDao:
                cabeceraCompraRequestDao.getDetalleCompraCollection()) {

            DetalleCompra detalle = new DetalleCompra(detalleCompraFacturaDao);

            detalle.setCabecera(cabeceraCompra);
            em.persist(detalle);

            for (DetalleImpuestoFacturaDao impuestoFacturaDao:
                    detalleCompraFacturaDao.getDetalleImpuestoCollection()) {
                DetalleImpuesto impuesto = new DetalleImpuesto(impuestoFacturaDao);
                impuesto.setDetalle(detalle);
                em.persist(impuesto);
            }
        }

        Archivo archivo = new Archivo();
        archivo.setTipoArchivo(cabeceraCompraRequestDao.getArchivo().getTipoArchivo());
        archivo.setNombreTabla("cabecera_compra");
        archivo.setIdTabla(String.valueOf(cabeceraCompra.getId()));
        archivo.setTipoDocumento(cabeceraCompraRequestDao.getArchivo().getTipoDocumento());
        archivo.setNombreOriginal(cabeceraCompraRequestDao.getArchivo().getNombreOriginal());
        archivo.setSisHabilitado("A");
        archivo.setTypeFile(cabeceraCompraRequestDao.getArchivo().getTipoArchivo());
        archivo.setBuffer(Base64.decodeBase64(cabeceraCompraRequestDao.getArchivo().getBuffer()));
        archivo.setSisCreado(new Date());

        em.persist(archivo);


        return ComprasMapper.setEntityToDaoResponse.apply(cabeceraCompra);
    }
}
