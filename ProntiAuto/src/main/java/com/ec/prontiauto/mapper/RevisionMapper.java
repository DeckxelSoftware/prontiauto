package com.ec.prontiauto.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.ec.prontiauto.dao.RevisionRequestDao;
import com.ec.prontiauto.dao.RevisionResponseDao;
import com.ec.prontiauto.entidad.Revision;
import com.ec.prontiauto.entidad.Articulo;

public class RevisionMapper {
    public static Function<RevisionRequestDao, Revision> setDaoRequestToEntity;
    public static Function<Revision, RevisionResponseDao> setEntityToDaoResponse;
    public static Function<Revision, RevisionResponseDao> setEntityToDaoReference;
    public static Function<List<Revision>, List<RevisionResponseDao>> setEntityListToDaoResponseList;
    public static Function<List<Revision>, List<RevisionResponseDao>> setEntityListToDaoReferenceList;

    static {
        setDaoRequestToEntity = ( daoReq -> {
            Revision entity = new Revision();
            entity.setId(daoReq.getId());

            entity.setCalificacion(daoReq.getCalificacion());
            entity.setCompresionMotor(daoReq.getCompresionMotor());
            entity.setCaja(daoReq.getCaja());
            entity.setObservaciones(daoReq.getObservaciones());
            entity.setFechaFirmaAprobacion(daoReq.getFechaFirmaAprobacion());

            Articulo articulo = new Articulo();
            articulo.setId(daoReq.getIdArticulo());
            entity.setIdArticulo(articulo);

            return entity;
        });
        setEntityToDaoResponse = ( entity -> {
            RevisionResponseDao daoRes = new RevisionResponseDao();

            daoRes.setId(entity.getId());

            daoRes.setCalificacion(entity.getCalificacion());
            daoRes.setCompresionMotor(entity.getCompresionMotor());
            daoRes.setCaja(entity.getCaja());
            daoRes.setObservaciones(entity.getObservaciones());
            daoRes.setFechaFirmaAprobacion(entity.getFechaFirmaAprobacion());

            if(entity.getIdArticulo() != null && entity.getIdArticulo().getId() != null) {
                daoRes.setIdArticulo(ArticuloMapper.setEntityToDaoReference.apply(entity.getIdArticulo()));
            }

            return daoRes;
        });


        setEntityToDaoReference = ( entity -> {
            RevisionResponseDao daoRes = new RevisionResponseDao();

            daoRes.setId(entity.getId());

            daoRes.setCalificacion(entity.getCalificacion());
            daoRes.setCompresionMotor(entity.getCompresionMotor());
            daoRes.setCaja(entity.getCaja());
            daoRes.setObservaciones(entity.getObservaciones());
            daoRes.setFechaFirmaAprobacion(entity.getFechaFirmaAprobacion());

            return daoRes;
        });

        setEntityListToDaoResponseList = (entityList -> {
			return (List<RevisionResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(RevisionMapper.setEntityToDaoResponse).collect(Collectors.toList());
		});

		setEntityListToDaoReferenceList = (entityList -> {
			return (List<RevisionResponseDao>) StreamSupport.stream(entityList.spliterator(), true)
					.map(RevisionMapper.setEntityToDaoReference).collect(Collectors.toList());
		});
    }
}
