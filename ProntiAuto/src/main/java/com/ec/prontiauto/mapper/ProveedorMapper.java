package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.ProveedorRequestDao;
import com.ec.prontiauto.dao.ProveedorResponseDao;
import com.ec.prontiauto.entidad.Empresa;
import com.ec.prontiauto.entidad.Proveedor;
import com.ec.prontiauto.entidad.Usuario;
import org.codehaus.jackson.map.ObjectMapper;

public class ProveedorMapper {
    public static Function<ProveedorRequestDao, Proveedor> setDaoRequestToEntity;
    public static Function<Proveedor, ProveedorResponseDao> setEntityToDaoResponse;
    public static Function<List<Proveedor>, List<ProveedorResponseDao>> setEntityListToDaoResponseList;
    public static Function<List<Proveedor>, List<ProveedorResponseDao>> setEntityListToDaoReferenceList;
    public static Function<Proveedor, ProveedorResponseDao> setEntityToDaoReference;

    static {
        setDaoRequestToEntity = (daoRequest -> {
            Proveedor entity = new Proveedor();
            entity.setId(daoRequest.getId());
            entity.setSisHabilitado(daoRequest.getSisHabilitado());

            entity.setTipoProveedor(daoRequest.getTipoProveedor());
            entity.setNombrePersonaReferencia(daoRequest.getNombrePersonaReferencia());
            entity.setContactoReferencia(daoRequest.getcontactoReferencia());
            entity.setTipoCuentaContable(daoRequest.getTipoCuentaContable());
            entity.setClaseContribuyente(daoRequest.getClaseContribuyente());
            entity.setObligadoLlevarContabilidad(daoRequest.getObligadoLLevarContabilidad());
            entity.setAgenteRetencion(daoRequest.getAgenteRetencion());
            entity.setSisHabilitado("A");

            if (daoRequest.getIdUsuario() != null) {
                if (daoRequest.getIdUsuario() instanceof Integer) {
                    Usuario usuario = new Usuario();
                    usuario.setId((int) daoRequest.getIdUsuario());
                    entity.setIdUsuario(usuario);
                } else {
                    entity.setIdUsuario(new ObjectMapper().convertValue(daoRequest.getIdUsuario(), Usuario.class));
                }
            }

            if (daoRequest.getIdEmpresa() != null) {
                if (daoRequest.getIdEmpresa() instanceof Integer) {
                    Empresa empresa = new Empresa();
                    empresa.setId((int) daoRequest.getIdEmpresa());
                    entity.setIdEmpresa(empresa);
                } else {
                    entity.setIdEmpresa((Empresa) daoRequest.getIdEmpresa());
                }
            }

            return entity;
        });

        setEntityToDaoResponse = (entity -> {
            ProveedorResponseDao dao = new ProveedorResponseDao();
            dao.setId(entity.getId());
            dao.setSisHabilitado(entity.getSisHabilitado());

            dao.setTipoProveedor(entity.getTipoProveedor());
            dao.setNombrePersonaReferencia(entity.getNombrePersonaReferencia());
            dao.setContactoReferencia(entity.getContactoReferencia());
            dao.setTipoCuentaContable(entity.getTipoCuentaContable());

            dao.setClaseContribuyente(entity.getClaseContribuyente());
            dao.setObligadoLLevarContabilidad(entity.getObligadoLlevarContabilidad());
            dao.setAgenteRetencion(entity.getAgenteRetencion());

            if (entity.getIdEmpresa() != null && entity.getIdEmpresa().getId() != null) {
                dao.setIdEmpresa(EmpresaMapper.setEntityToDaoReference.apply(entity.getIdEmpresa()));
            }

            if (entity.getIdUsuario() != null && entity.getIdUsuario().getId() != null) {
                dao.setIdUsuario(UsuarioMapper.setEntityToDaoReference.apply(entity.getIdUsuario()));
            }

            if (entity.getOrdenCompraCollection() != null) {
                dao.setOrdenCompraCollection(
                        OrdenCompraMapper.setEntityListToDaoResponseList.apply(entity.getOrdenCompraCollection()));
            }

            return dao;

        });

        setEntityToDaoReference = (entity -> {
            ProveedorResponseDao dao = new ProveedorResponseDao();
            dao.setId(entity.getId());
            dao.setSisHabilitado(entity.getSisHabilitado());

            dao.setTipoProveedor(entity.getTipoProveedor());
            dao.setNombrePersonaReferencia(entity.getNombrePersonaReferencia());
            dao.setContactoReferencia(entity.getContactoReferencia());
            dao.setTipoCuentaContable(entity.getTipoCuentaContable());
            dao.setClaseContribuyente(entity.getClaseContribuyente());
            dao.setObligadoLLevarContabilidad(entity.getObligadoLlevarContabilidad());
            dao.setAgenteRetencion(entity.getAgenteRetencion());

            return dao;
        });

        setEntityListToDaoResponseList = (entityList -> {
            return (List<ProveedorResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(ProveedorMapper.setEntityToDaoResponse).collect(Collectors.toList());
        });

        setEntityListToDaoReferenceList = (entityList -> {
            return (List<ProveedorResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
                    .map(ProveedorMapper.setEntityToDaoReference).collect(Collectors.toList());
        });
    }

}