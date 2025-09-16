package com.koopey.api.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koopey.api.configuration.properties.CustomProperties;
import com.koopey.api.model.dto.TagDto;
import com.koopey.api.model.entity.Tag;
import com.koopey.api.model.parser.TagParser;
import com.koopey.api.model.type.LanguageType;
import com.koopey.api.repository.TagRepository;
import com.koopey.api.repository.base.BaseRepository;
import com.koopey.api.service.base.BaseService;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

@Slf4j
@Service
public class TagService extends BaseService<Tag, UUID> {

  private final CustomProperties customProperties;
  private final KafkaTemplate<String, String> kafkaTemplate;
  private final TagRepository tagRepository;
  private final EntityManager entityManager;

  TagService(CustomProperties customProperties, KafkaTemplate<String, String> kafkaTemplate,
      @Lazy TagRepository tagRepository, EntityManager entityManager) {
    this.customProperties = customProperties;
    this.kafkaTemplate = kafkaTemplate;
    this.tagRepository = tagRepository;
    this.entityManager = entityManager;
  }

  protected BaseRepository<Tag, UUID> getRepository() {
    return tagRepository;
  }

  protected KafkaTemplate<String, String> getKafkaTemplate() {
    return kafkaTemplate;
  }

  @PostConstruct
  @Transactional
  protected void init() {
    log.info("Tags file found: {}", customProperties.getTagsFileName());

    List<Tag> tags = importJsonFromFile();

    long size = tagRepository.count();
    if (size == 0 || size != tags.size()) {
      log.info("Tags repository synchronized with new data");
      tagRepository.saveAll(tags);
      tagRepository.flush();
    } else {
      log.info("Tags repository not synchronized with new data, old data is fine");
    }
  }

  public Page<Tag> find(String str, String language, Pageable pagable) {

    if (language.equals(LanguageType.CHINES)) {
      return tagRepository.findByCnContains(str, pagable);
    } else if (language.equals(LanguageType.DUTCH)) {
      return tagRepository.findByNlContains(str, pagable);
    } else if (language.equals(LanguageType.ENGLISH)) {
      return tagRepository.findByEnContains(str, pagable);
    } else if (language.equals(LanguageType.FRENCH)) {
      return tagRepository.findByFrContains(str, pagable);
    } else if (language.equals(LanguageType.ITALIAN)) {
      return tagRepository.findByItContains(str, pagable);
    } else if (language.equals(LanguageType.GERMAN)) {
      return tagRepository.findByDeContains(str, pagable);
    } else if (language.equals(LanguageType.PORTUGUESE)) {
      return tagRepository.findByPtContains(str, pagable);
    } else {
      return tagRepository.findByEnContains(str, pagable);
    }
  }

  public List<Tag> findAll(String language) {
    if (language.equals(LanguageType.CHINES)) {
      return tagRepository.findAllChinese();
    } else if (language.equals(LanguageType.DUTCH)) {
      return tagRepository.findAllDutch();
    } else if (language.equals(LanguageType.ENGLISH)) {
      return tagRepository.findAllEnglish();
    } else if (language.equals(LanguageType.FRENCH)) {
      return tagRepository.findAllFrench();
    } else if (language.equals(LanguageType.ITALIAN)) {
      return tagRepository.findAllItalian();
    } else if (language.equals(LanguageType.GERMAN)) {
      return tagRepository.findAllGerman();
    } else if (language.equals(LanguageType.PORTUGUESE)) {
      return tagRepository.findAllPortuguese();
    } else {
      return tagRepository.findAll();
    }
  }

  public List<Tag> findSuggestions(String str, String language) {
    if (language.equals(LanguageType.CHINES)) {
      return tagRepository.findTop10ByCnContains(str);
    } else if (language.equals(LanguageType.DUTCH)) {
      return tagRepository.findTop10ByNlContains(str);
    } else if (language.equals(LanguageType.ENGLISH)) {
      return tagRepository.findTop10ByEnContains(str);
    } else if (language.equals(LanguageType.FRENCH)) {
      return tagRepository.findTop10ByFrContains(str);
    } else if (language.equals(LanguageType.ITALIAN)) {
      return tagRepository.findTop10ByItContains(str);
    } else if (language.equals(LanguageType.GERMAN)) {
      return tagRepository.findTop10ByDeContains(str);
    } else if (language.equals(LanguageType.PORTUGUESE)) {
      return tagRepository.findTop10ByPtContains(str);
    } else {
      return tagRepository.findTop10ByEnContains(str);
    }
  }

  public List<Tag> findPopularTags() {
    return tagRepository.findByType("popluar");
  }

  public List<Tag> importJsonFromFile() {

    List<Tag> tags = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    TypeReference<List<Tag>> typeReference = new TypeReference<List<Tag>>() {
    };

    try {
      File jsonFile = new ClassPathResource(customProperties.getTagsFileName()).getFile();
      tags = mapper.readValue(jsonFile, typeReference);
      log.info("Import tags from JSON file success");
    } catch (IOException e) {
      log.info("Import tags from JSON file failed: " + e.getMessage());
    }

    return tags;
  }

  public long size() {
    return tagRepository.count();
  }

}
