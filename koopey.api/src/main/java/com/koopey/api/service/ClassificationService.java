package com.koopey.api.service;

import com.koopey.api.model.entity.Asset;
import com.koopey.api.model.entity.Classification;
import com.koopey.api.model.entity.Tag;
import com.koopey.api.repository.ClassificationRepository;
import com.koopey.api.repository.base.BaseRepository;
import com.koopey.api.service.base.BaseService;
import com.koopey.api.service.impl.IClassificationService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ClassificationService extends BaseService<Classification, UUID> implements IClassificationService{

  private final ClassificationRepository classificationRepository;
  private final KafkaTemplate<String, String> kafkaTemplate;

  ClassificationService(KafkaTemplate<String, String> kafkaTemplate,
      @Lazy ClassificationRepository classificationRepository) {
    this.kafkaTemplate = kafkaTemplate;
    this.classificationRepository = classificationRepository;
  }

  protected BaseRepository<Classification, UUID> getRepository() {
    return classificationRepository;
  }

  protected KafkaTemplate<String, String> getKafkaTemplate() {
    return kafkaTemplate;
  }

   public void deleteByAsset(UUID assetId) {
     classificationRepository.deleteByAssetId(assetId);
  }

  public List<Asset> findAssets(List<Tag> tags) {
    List<UUID> tagIds = new ArrayList<>();
    tags.forEach((Tag tag) -> {
      tagIds.add(tag.getId());
    });
    return classificationRepository.findAssets(tagIds);
  }

  public List<Tag> findTags(UUID assetId) {
    return classificationRepository.findTags(assetId);
  }
}
