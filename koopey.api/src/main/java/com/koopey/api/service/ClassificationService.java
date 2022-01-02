package com.koopey.api.service;

import com.koopey.api.model.entity.Asset;
import com.koopey.api.model.entity.Classification;
import com.koopey.api.model.entity.Tag;
import com.koopey.api.repository.ClassificationRepository;
import com.koopey.api.repository.base.BaseRepository;
import com.koopey.api.service.base.BaseService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassificationService extends BaseService<Classification, UUID> {

  @Autowired
  ClassificationRepository classificationRepository;

  protected BaseRepository<Classification, UUID> getRepository() {
    return classificationRepository;
  }

  public List<Asset> findAssets(List<Tag> tags) {
    List<UUID> tagIds =  new ArrayList<>();    
    tags.forEach((Tag tag) -> {
      tagIds.add(tag.getId());
    });
    return classificationRepository.findAssets(tagIds);
  }

  public List<Tag> findTags(UUID assetId) {
    return classificationRepository.findTags(assetId);
  }
}
