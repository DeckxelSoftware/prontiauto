package com.ec.prontiauto.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.ec.prontiauto.dao.ListaValoresTipoRequestDao;
import com.ec.prontiauto.dao.ListaValoresTipoResponseDao;
import com.ec.prontiauto.entidad.ListaValoresTipo;

public class ListaValoresTipoMapper {
    public static Function<ListaValoresTipoRequestDao, ListaValoresTipo> setDaoRequestToEntity;
    public static Function<ListaValoresTipo, ListaValoresTipoResponseDao> setEntityToDaoResponse;
    public static Function<ListaValoresTipo, ListaValoresTipoResponseDao> setEntityToDaoReference;
    public static Function<List<ListaValoresTipo>, List<ListaValoresTipoResponseDao>> setEntityListToDaoResponseList;

    static {
        setDaoRequestToEntity = (daoRequest -> {
            ListaValoresTipo entity = new ListaValoresTipo();
            entity.setId(daoRequest.getId());
            entity.setNombre(daoRequest.getNombre());
            entity.setDescripcion(daoRequest.getDescripcion());
            entity.setSisHabilitado(daoRequest.getSisHabilitado());
            entity.setCodigoPrimario(daoRequest.getCodigoPrimario());
            entity.setCodigoSecundario(daoRequest.getCodigoSecundario());
            return entity;
        });

        setEntityToDaoResponse = (entity -> {
            ListaValoresTipoResponseDao dao = new ListaValoresTipoResponseDao();
            dao.setId(entity.getId());
            dao.setNombre(entity.getNombre());
            dao.setDescripcion(entity.getDescripcion());
            dao.setSisHabilitado(entity.getSisHabilitado());
            dao.setCodigoPrimario(entity.getCodigoPrimario());
            dao.setCodigoSecundario(entity.getCodigoSecundario());
            dao.setSisActualizado(entity.getSisActualizado());
            dao.setSisCreado(entity.getSisCreado());
            if (entity.getListaValoresDetalleCollection() != null) {
                dao.setListaValorDetalleCollection(ListaValoresDetalleMapper.setEntityListToDaoResponseList
                        .apply(entity.getListaValoresDetalleCollection()));
            }
            return dao;
        });
        setEntityToDaoReference = (entity -> {
            ListaValoresTipoResponseDao dao = new ListaValoresTipoResponseDao();
            dao.setId(entity.getId());
            dao.setNombre(entity.getNombre());
            dao.setDescripcion(entity.getDescripcion());
            dao.setSisHabilitado(entity.getSisHabilitado());
            dao.setCodigoPrimario(entity.getCodigoPrimario());
            dao.setCodigoSecundario(entity.getCodigoSecundario());
            dao.setSisActualizado(entity.getSisActualizado());
            dao.setSisCreado(entity.getSisCreado());
            return dao;
        });

        setEntityListToDaoResponseList = (entityList -> {
            List<ListaValoresTipoResponseDao> daoList = new ArrayList<ListaValoresTipoResponseDao>();
            for (ListaValoresTipo entity : entityList) {
                daoList.add(setEntityToDaoResponse.apply(entity));
            }
            return daoList;
        });
    }
}
