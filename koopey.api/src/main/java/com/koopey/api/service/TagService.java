package com.koopey.api.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koopey.api.model.entity.Tag;
import com.koopey.api.model.type.LanguageType;
import com.koopey.api.repository.TagRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TagService {

  @Value("${json.tags}")
  private String jsonFile;

  @Autowired
  private TagRepository tagRepository;

  @PostConstruct
  private void init() {
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

  public Page<Tag> findTag(String str, LanguageType language ,Pageable pagable ) {
   
    if (language.equals(LanguageType.CHINES)){
      return tagRepository.findByCnContains(LanguageType.CHINES,  pagable);
    } else     if (language.equals(LanguageType.DUTCH)){
      return tagRepository.findByDeContains(str,  pagable);
    } else    if (language.equals(LanguageType.ENGLISH)){
      return tagRepository.findByEnContains(str,  pagable);
    } else    if (language.equals(LanguageType.FRENCH)){
      return tagRepository.findByFrContains(str,  pagable);   
    } else    if (language.equals(LanguageType.ENGLISH)){
      return tagRepository.findByEnContains(str,  pagable);
    } else    if (language.equals(LanguageType.PORTUGUESE)){
      return tagRepository.findByEnContains(str,  pagable); 
    }  else {
      return tagRepository.findByEnContains(str,  pagable);
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
      File jsonFile = new ClassPathResource(this.jsonFile).getFile();
      tags = mapper.readValue(jsonFile, typeReference);
      // tags.add(tag );
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
