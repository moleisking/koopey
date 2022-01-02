package com.koopey.api.service;

import com.koopey.api.model.entity.Review;
import com.koopey.api.repository.ReviewRepository;
import com.koopey.api.repository.base.AuditRepository;
import com.koopey.api.repository.base.BaseRepository;
import com.koopey.api.service.base.AuditService;
import com.koopey.api.service.base.BaseService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService extends AuditService<Review, UUID> {

  @Autowired
  ReviewRepository reviewRepository;

  protected AuditRepository<Review, UUID> getRepository() {
    return reviewRepository;
  }

  public Long countByAsset(UUID assetId) {
    return reviewRepository.countByAssetId(assetId);
  }

  public Long countByBuyer(UUID userId) {
    return reviewRepository.countByBuyerId(userId);
  }

  public Long countBySeller(UUID userId) {
    return reviewRepository.countBySellerId(userId);
  }

  public List<Review> findByAsset(UUID assetId) {
    return reviewRepository.findByAssetId(assetId);
  }

  public List<Review> findByBuyer(UUID userId) {
    return reviewRepository.findByBuyerId(userId);
  }

  public List<Review> findBySeller(UUID userId) {
    return reviewRepository.findBySellerId(userId);
  }
}
