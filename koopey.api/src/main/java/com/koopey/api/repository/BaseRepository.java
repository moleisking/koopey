package com.koopey.api.repository;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository <E, I extends Serializable> extends JpaRepository<E,I>{     

    Optional<E> findByType(E type);

    Optional<E> findByName(E name);
}
