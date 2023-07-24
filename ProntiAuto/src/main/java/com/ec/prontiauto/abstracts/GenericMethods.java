package com.ec.prontiauto.abstracts;

import javax.persistence.EntityManager;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.ec.prontiauto.enums.EnumConsulta;

public class GenericMethods {

  public GenericMethods() {
  }

  public Object findById(String tabla, EntityManager em, Integer id) {
    Object antiguo = em
        .createQuery("SELECT a FROM " + tabla + " a WHERE a.id=:id").setParameter("id", id)
        .getSingleResult();
    return antiguo;
  }

  public <T> void updateData(String tabla, EntityManager em, T entity) {
    em.merge(entity);
  }

  public PageRequest getPageRequest(Integer skip, Integer take, String sortField, Boolean sortAscending) {
    return PageRequest.of(skip == null ? EnumConsulta.SKIP.getValor() : skip,
        take == null ? EnumConsulta.TAKE.getValor() : take,
        sortAscending ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
  }
}
