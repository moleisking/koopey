package com.koopey.api.service;

import com.koopey.api.model.entity.Review;
import com.koopey.api.repository.BaseRepository;
import com.koopey.api.repository.ReviewRepository;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService extends BaseService<Review, UUID> {

  @Autowired
  ReviewRepository reviewRepository;

  BaseRepository<Review, UUID> getRepository() {
    return reviewRepository;
  }

  public Long countByAsset(UUID assetId) {
    return reviewRepository.countByAssetId(assetId);
  }

  public Long countByClient(UUID assetId) {
    return reviewRepository.countByClientId(assetId);
  }

  public List<Review> findByAsset(UUID assetId) {
    return reviewRepository.findByAssetId(assetId);
  }

  public List<Review> findByClient(UUID clientId) {
    return reviewRepository.findByClientId(clientId);
  }
}
