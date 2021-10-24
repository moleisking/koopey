package com.koopey.api.repository;

import com.koopey.api.model.entity.Asset;
import com.koopey.api.model.entity.Classification;
import com.koopey.api.model.entity.Tag;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ClassificationRepository extends BaseRepository<Classification, UUID> {

  public int countByAssetIdAndTagId(UUID assetId, UUID tagId);

  @Transactional
  public void deleteByAssetId(UUID assetId);

  @Transactional
  public void deleteByTagId(UUID tagId);

  public List<Classification> findByAssetId(UUID assetId);

  public List<Classification> findByTagId(UUID tagId);

  /*@Query(nativeQuery = true, value = "SELECT Asset.* FROM Classification C "
      + "INNER JOIN Asset A ON A.asset_id = T.tag_id " + " WHERE asset_id = :asset_id AND tag_id = :tag_id ")
  public List<Asset> findAssets(@Param("tags") List<Tag> tags);*/

  @Query(nativeQuery = true, value = "SELECT Tag.* FROM Classification C " + "INNER JOIN Asset A ON A.id = C.asset_id "
      + " WHERE id = :asset_id")
  public List<Tag> findTags(@Param("asset_id") UUID assetId);

}