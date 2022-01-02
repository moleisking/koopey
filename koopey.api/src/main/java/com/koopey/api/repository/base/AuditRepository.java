package com.koopey.api.repository.base;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AuditRepository<E, I extends Serializable> extends BaseRepository<E, I> {

    List<E> findByType(String type);

    List<E> findByName(String name);
}
