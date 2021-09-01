package com.koopey.api.repository;

import com.koopey.api.model.entity.Classification;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassificationRepository extends BaseRepository<Classification, UUID> { 

    //Long deleteByItem(Item itemId);

   // Long deleteByCategoryId(UUID categoryId);

    @Query(nativeQuery = true, value = "SELECT * FROM Classification C WHERE asset_id = :asset_id AND tag_id = :tag_id ")
    public int findDuplicate(@Param("asset_id")UUID assetId, @Param("tag_id")UUID tagId);
}