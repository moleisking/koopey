package com.koopey.api.service.base;

import com.koopey.api.model.entity.base.BaseEntity;
import com.koopey.api.repository.base.BaseRepository;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
public abstract class BaseService<T extends BaseEntity, Y extends Serializable> {

    protected abstract BaseRepository<T, Y> getRepository();
    // protected abstract KafkaTemplate<String, String> getKafkaTemplate();

    public long count() {
        return this.getRepository().count();
    }

    public void delete(T entity) {
        log.info("delete {}", ((BaseEntity) entity).getId());
        this.getRepository().delete(entity);
        // this.getKafkaTemplate().send(entity.getClass().getName(), entity.toString());
    }

    protected void deleteAll(Iterable<? extends T> entities) {
        this.getRepository().deleteAll(entities);
    }

    public void deleteById(Y id) {
        this.getRepository().deleteById(id);
    }

    public boolean exists(Y id) {
        return this.getRepository().existsById(id);
    }

    public Optional<T> findById(Y id) {
        return this.getRepository().findById(id);
    }

    public List<T> findAll() {
        return this.getRepository().findAll();
    }

    public List<T> findByName(String name) {
        return this.getRepository().findByName(name);
    }

    public List<T> findByType(String type) {
        return this.getRepository().findByType(type);
    }

    public List<T> findAll(Sort sort) {
        return this.getRepository().findAll(sort);
    }

    protected List<T> findAllById(Iterable<Y> ids) {
        return this.getRepository().findAllById(ids);
    }

    public <S extends T> S save(S entity) {
        log.info("save {}", ((BaseEntity) entity).getId());
        // this.getKafkaTemplate().send(entity.getClass().getName(), entity.toString());
        if (entity.getCreateTimeStamp().equals(0)) {
            entity.setCreateTimeStamp(System.currentTimeMillis());
            entity.setTimeZone("UTC/GMT");
        } else {
            entity.setUpdateTimeStamp(System.currentTimeMillis());
        }

        return this.getRepository().saveAndFlush(entity);
    }

    protected <S extends T> List<S> saveAll(Iterable<S> entities) {
        log.info("saveAll {}", entities.spliterator().estimateSize());
        return this.getRepository().saveAll(entities);
    }

    protected <S extends T> S saveAndFlush(S entity) {
        log.info("saveAndFlush {}", ((BaseEntity) entity).getId());
        // this.getKafkaTemplate().send(entity.getClass().getName(), entity.toString());
        return this.getRepository().saveAndFlush(entity);
    }

}
/*
 * 
 * @Slf4j
 * public abstract class BaseService<T, Y extends Serializable> {
 * 
 * protected abstract BaseRepository<T, Y> getRepository();
 * // protected abstract KafkaTemplate<String, String> getKafkaTemplate();
 * 
 * public long count() {
 * return this.getRepository().count();
 * }
 * 
 * public void delete(T entity) {
 * log.info("delete {}" , ((BaseEntity) entity).getId() );
 * this.getRepository().delete(entity);
 * // this.getKafkaTemplate().send(entity.getClass().getName(),
 * entity.toString());
 * }
 * 
 * protected void deleteAll(Iterable<? extends T> entities) {
 * this.getRepository().deleteAll(entities);
 * }
 * 
 * public void deleteById(Y id) {
 * this.getRepository().deleteById(id);
 * }
 * 
 * public boolean exists(Y id) {
 * return this.getRepository().existsById(id);
 * }
 * 
 * public Optional<T> findById(Y id) {
 * return this.getRepository().findById(id);
 * }
 * 
 * public List<T> findAll() {
 * return this.getRepository().findAll();
 * }
 * 
 * public List<T> findByName(String name) {
 * return this.getRepository().findByName(name);
 * }
 * 
 * public List<T> findByType(String type) {
 * return this.getRepository().findByType(type);
 * }
 * 
 * public List<T> findAll(Sort sort) {
 * return this.getRepository().findAll(sort);
 * }
 * 
 * protected List<T> findAllById(Iterable<Y> ids) {
 * return this.getRepository().findAllById(ids);
 * }
 * 
 * public <S extends T> S save(S entity) {
 * log.info("save {}" , ((BaseEntity) entity).getId());
 * // this.getKafkaTemplate().send(entity.getClass().getName(),
 * entity.toString());
 * 
 * return this.getRepository().saveAndFlush(entity);
 * }
 * 
 * protected <S extends T> List<S> saveAll(Iterable<S> entities) {
 * log.info("saveAll {}" , entities.spliterator().estimateSize());
 * return this.getRepository().saveAll(entities);
 * }
 * 
 * protected <S extends T> S saveAndFlush(S entity) {
 * log.info("saveAndFlush {}" , ((BaseEntity) entity).getId());
 * // this.getKafkaTemplate().send(entity.getClass().getName(),
 * entity.toString());
 * return this.getRepository().saveAndFlush(entity);
 * }
 * 
 * }
 * 
 */