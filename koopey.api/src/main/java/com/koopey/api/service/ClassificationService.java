package com.koopey.api.service;

import java.util.List;
import java.util.UUID;

import com.koopey.api.model.entity.Asset;
import com.koopey.api.model.entity.Classification;
import com.koopey.api.model.entity.Tag;
import com.koopey.api.repository.BaseRepository;
import com.koopey.api.repository.ClassificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassificationService extends BaseService<Classification, UUID> {

  @Autowired
  ClassificationRepository classificationRepository;

  BaseRepository<Classification, UUID> getRepository() {
    return classificationRepository;
  }

  /*public List<Asset> findAssets(List<Tag> tags) {
    return classificationRepository.findAssets(tags);
  }*/

  public List<Tag> findTags(UUID assetId) {
    return classificationRepository.findTags(assetId);
  }
}
