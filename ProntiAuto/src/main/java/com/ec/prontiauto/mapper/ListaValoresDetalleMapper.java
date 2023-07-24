package com.ec.prontiauto.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.ec.prontiauto.dao.ListaValoresDetalleRequestDao;
import com.ec.prontiauto.dao.ListaValoresDetalleResponseDao;
import com.ec.prontiauto.entidad.ListaValoresDetalle;
import com.ec.prontiauto.entidad.ListaValoresTipo;

public class ListaValoresDetalleMapper {
    public static Function<ListaValoresDetalle, ListaValoresDetalleResponseDao> setEntityToDaoResponse;
    public static Function<ListaValoresDetalleRequestDao, ListaValoresDetalle> setDaoRequestToEntity;
    public static Function<List<ListaValoresDetalle>, List<ListaValoresDetalleResponseDao>> setEntityListToDaoResponseList;

    static {

        setDaoRequestToEntity = (daoRequest -> {
            ListaValoresDetalle entity = new ListaValoresDetalle();
            entity.setId(daoRequest.getId());
            entity.setNombre(daoRequest.getNombre());
            entity.setDescripcion(daoRequest.getDescripcion());
            entity.setSisHabilitado(daoRequest.getSisHabilitado());
            entity.setCodigoPrimario(daoRequest.getCodigoPrimario());
            entity.setCodigoSecundario(daoRequest.getCodigoSecundario());
            ListaValoresTipo listaValoresTipo = new ListaValoresTipo();
            listaValoresTipo.setId(daoRequest.getIdListaValoresTipo());
            entity.setIdListaValoresTipo(listaValoresTipo);
            return entity;
        });
        setEntityToDaoResponse = (entity -> {
            ListaValoresDetalleResponseDao dao = new ListaValoresDetalleResponseDao();
            dao.setCodigoPrimario(entity.getCodigoPrimario());
            dao.setCodigoSecundario(entity.getCodigoSecundario());
            dao.setNombre(entity.getNombre());
            dao.setDescripcion(entity.getDescripcion());
            dao.setSisHabilitado(entity.getSisHabilitado());
            dao.setId(entity.getId());
            dao.setSisActualizado(entity.getSisActualizado());
            dao.setSisCreado(entity.getSisCreado());
            dao.setIdListaValoresTipo(
                    ListaValoresTipoMapper.setEntityToDaoReference.apply(entity.getIdListaValoresTipo()));
            return dao;
        });

        setEntityListToDaoResponseList = (listListaValoresDetalle -> {
            List<ListaValoresDetalleResponseDao> daoResponseList = new ArrayList<>();
            listListaValoresDetalle.forEach(ListaValoresDetalle -> {
                daoResponseList.add(setEntityToDaoResponse.apply(ListaValoresDetalle));
            });
            return daoResponseList;
        });
    }
}
