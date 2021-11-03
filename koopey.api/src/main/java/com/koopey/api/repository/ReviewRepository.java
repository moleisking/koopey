package com.koopey.api.repository;

import com.koopey.api.model.entity.Review;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends BaseRepository<Review, UUID>{

    public Long countByAssetId(UUID assetId);

    public Long countByBuyerId(UUID clientId);

    public Long countBySellerId(UUID clientId);

    public List<Review> findByAssetId(UUID assetId);

    public List<Review> findByBuyerId(UUID usertId);

    public List<Review> findBySellerId(UUID userId);

}