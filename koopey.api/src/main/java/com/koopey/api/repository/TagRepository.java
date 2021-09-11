package com.koopey.api.repository;

import com.koopey.api.model.entity.Tag;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends BaseRepository<Tag, UUID> {

    Page<Tag> findByCnContains(String str, Pageable pagable);

    List<Tag> findTop10ByCnContains(String str);

    Page<Tag> findByEnContains(String str, Pageable pagable);

    List<Tag> findTop10ByEnContains(String str);

    Page<Tag> findByDeContains(String str, Pageable pagable);

    List<Tag> findTop10ByDeContains(String str);

    Page<Tag> findByEsContains(String str, Pageable pagable);

    List<Tag> findTop10ByEsContains(String str);

    Page<Tag> findByFrContains(String str, Pageable pagable);

    List<Tag> findTop10ByFrContains(String str);

    Page<Tag> findByItContains(String str, Pageable pagable);

    List<Tag> findTop10ByItContains(String str);

    Page<Tag> findByNlContains(String str, Pageable pagable);

    List<Tag> findTop10ByNlContains(String str);

    Page<Tag> findByPtContains(String str, Pageable pagable);

    List<Tag> findTop10ByPtContains(String str);

    List<Tag> findByType(String type);

}