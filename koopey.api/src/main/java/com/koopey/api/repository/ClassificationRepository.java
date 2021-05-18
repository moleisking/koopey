package com.koopey.api.repository;


import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import com.koopey.api.model.Classification;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassificationRepository extends CrudRepository<Classification, UUID> { 

    //Long deleteByItem(Item itemId);

   // Long deleteByCategoryId(UUID categoryId);

    @Query(nativeQuery = true, value = "SELECT * FROM Classification C WHERE asset_id = :asset_id AND tag_id = :tag_id ")
    public int findDuplicate(@Param("asset_id")UUID assetId, @Param("tag_id")UUID tagId);
}