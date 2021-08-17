package com.koopey.api.repository;

import com.koopey.api.model.entity.Tag;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends BaseRepository<Tag, UUID> {

    List<Tag> findByType(String type);

    Page<Tag> findByCnContains(String str , Pageable pagable);

    Page<Tag> findByEnContains(String str , Pageable pagable);

    Page<Tag> findByDeContains(String str , Pageable pagable);

    Page<Tag> findByEsContains(String str , Pageable pagable);

    Page<Tag> findByFrContains(String str , Pageable pagable);

    Page<Tag> findByItContains(String str , Pageable pagable);

    Page<Tag> findByPtContains(String str , Pageable pagable);


}