package com.koopey.api.repository;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository <E, I extends Serializable> extends JpaRepository<E,I>, JpaSpecificationExecutor<E>{   
}
