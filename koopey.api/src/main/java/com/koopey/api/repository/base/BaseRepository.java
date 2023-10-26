package com.koopey.api.repository.base;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.koopey.api.model.entity.base.BaseEntity;

@NoRepositoryBean
public interface BaseRepository<E extends BaseEntity, I extends Serializable> extends JpaRepository<E, I> {

    List<E> findByType(String type);

    List<E> findByName(String name);
}
