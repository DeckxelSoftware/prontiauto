package com.ec.prontiauto.validations;

import com.ec.prontiauto.entidad.Articulo;
import com.ec.prontiauto.entidad.OrdenCompra;
import com.ec.prontiauto.repositoryImpl.ArticuloRepositoryImpl;

import javax.persistence.EntityManager;

public class OrdenCompraValidation {

    private EntityManager em;
    private OrdenCompra entity;
    private ArticuloRepositoryImpl articuloRepository;


    public OrdenCompraValidation(EntityManager em, OrdenCompra entity, ArticuloRepositoryImpl articuloRepositoryImpl, Boolean isUpdate){
        this.em = em;
        this.entity = entity;
        this.articuloRepository = articuloRepositoryImpl;

        if (isUpdate){
            initUpdate();
        } else {
            initCreate();
        }
    }

    private void initUpdate(){
        updateArticulo();
    }
    private void initCreate(){
        createArticulo();
    }

    private void validateRelationEntity() {

    }
    private void createArticulo(){
        /* crear un artículo cuando se crea una orden de compra*/

        Articulo articuloReq = entity.getArticulo();
        articuloReq.setId(null);
        articuloReq.setEstado("C");
        Articulo articulo = articuloRepository.createArticulo(articuloReq);

        articuloReq.setId(articulo.getId());
        entity.setArticulo(articuloReq);
    }

    private void updateArticulo(){

        if(entity.getUpdateEntity() != null && entity.getUpdateEntity()) {
            Articulo articuloReq = entity.getArticulo();
            Articulo articulo = articuloRepository.updateArticulo(articuloReq);

            articuloReq.setId(articulo.getId());
            entity.setArticulo(articuloReq);
        }
    }

    public OrdenCompra getEntity(){
        return this.entity;
    }
}
