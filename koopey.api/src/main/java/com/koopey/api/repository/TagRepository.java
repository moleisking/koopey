package com.koopey.api.repository;

import com.koopey.api.model.entity.Tag;
import com.koopey.api.repository.base.BaseRepository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface TagRepository extends BaseRepository<Tag, UUID> {

    @Query("SELECT t.id, t.cn, t.type FROM Tag t" )
    List<Tag> findAllChinese();

    Page<Tag> findByCnContains(String str, Pageable pageable);

    List<Tag> findTop10ByCnContains(String str);

    @Query("SELECT t.id, t.en, t.type FROM Tag t" )
    List<Tag> findAllEnglish();

    Page<Tag> findByEnContains(String str, Pageable pageable);

    List<Tag> findTop10ByEnContains(String str);

    @Query("SELECT t.id, t.de, t.type FROM Tag t" )
    List<Tag> findAllGerman();

    Page<Tag> findByDeContains(String str, Pageable pageable);

    List<Tag> findTop10ByDeContains(String str);

    @Query("SELECT t.id, t.es, t.type FROM Tag t" )
    List<Tag> findAllSpanish();

    Page<Tag> findByEsContains(String str, Pageable pageable);

    List<Tag> findTop10ByEsContains(String str);

    @Query("SELECT t.id, t.fr, t.type FROM Tag t" )
    List<Tag> findAllFrench();

    Page<Tag> findByFrContains(String str, Pageable pageable);

    List<Tag> findTop10ByFrContains(String str);

    @Query("SELECT t.id, t.it, t.type FROM Tag t" )
    List<Tag> findAllItalian();

    Page<Tag> findByItContains(String str, Pageable pageable);

    List<Tag> findTop10ByItContains(String str);

    @Query("SELECT t.id, t.nl, t.type FROM Tag t" )
    List<Tag> findAllDutch();

    Page<Tag> findByNlContains(String str, Pageable pageable);

    List<Tag> findTop10ByNlContains(String str);

    @Query("SELECT t.id, t.pt, t.type FROM Tag t" )
    List<Tag> findAllPortuguese();

    Page<Tag> findByPtContains(String str, Pageable pageable);

    List<Tag> findTop10ByPtContains(String str);

    List<Tag> findByType(String type);

    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO tag (id,type,cn,en,es,de,fr,it,pt) VALUES (:id, :type, :cn, :en, :es, :de, :fr, :it, :pt)" )
    @Transactional
    void insertTag(@Param("id") String id ,@Param("type") String type, @Param("cn") String cn, @Param("en") String en, @Param("es") String es, @Param("de") String de, @Param("fr") String fr, @Param("it") String it , @Param("pt") String pt);

}