package com.koopey.api.service;

import com.koopey.api.model.entity.BaseEntity;
import com.koopey.api.repository.BaseRepository;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;

import lombok.extern.slf4j.Slf4j;

/**
 * BaseService for the most common service operations.
 *
 * @param <T> Entity type
 * @param <Y> Entity ID type
 * @author sjohnston
 */
@Slf4j
public abstract class BaseService<T, Y extends Serializable> {

    abstract BaseRepository<T, Y> getRepository();

    public long count() {
        return this.getRepository().count();
    }

    public void delete(T entity) {
        log.info("delete(" + ((BaseEntity) entity).getId() + ")");
        this.getRepository().delete(entity);
    }

    protected void deleteAll(Iterable<? extends T> entities) {
        this.getRepository().deleteAll(entities);
    }

    protected void deleteById(Y id) {
        this.getRepository().deleteById(id);
    }

    public Optional<T> findById(Y id) {
        return this.getRepository().findById(id);
    }

    public List<T> findByName(String name) {
        return this.getRepository().findByName(name);
    }

    public List<T> findByType(String type) {
        return this.getRepository().findByType(type);
    }

    public List<T> findAll() {
        return this.getRepository().findAll();
    }

    public List<T> findAll(Sort sort) {
        return this.getRepository().findAll(sort);
    }

    protected List<T> findAllById(Iterable<Y> ids) {
        return this.getRepository().findAllById(ids);
    }

    public <S extends T> S update(S entity) {
        log.info("update(" + ((BaseEntity) entity).getId() + ")");
        return this.getRepository().save(entity);
    }

    public <S extends T> S save(S entity) {
        log.info("create(" + ((BaseEntity) entity).getId() + ")");
        return this.getRepository().save(entity);
    }

    protected <S extends T> List<S> saveAll(Iterable<S> entities) {
        return this.getRepository().saveAll(entities);
    }

    protected <S extends T> S saveAndFlush(S entity) {
        return this.getRepository().saveAndFlush(entity);
    }

}
