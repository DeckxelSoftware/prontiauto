package com.ec.prontiauto.abstracts;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericRepository<Entity, IdType> extends JpaRepository<Entity, IdType> {
    List<Object> findBySearchAndFilter(Map<String, Object> params, Pageable pageable);
}
