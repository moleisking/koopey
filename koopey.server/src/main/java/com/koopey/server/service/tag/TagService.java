package com.koopey.server.service.tag;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koopey.server.data.TagRepository;
import com.koopey.server.model.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class TagService {

  private static Logger LOGGER = Logger.getLogger(TagService.class.getName());

  @Value("${json.tags}")
  private String jsonFile;

  @Autowired
  private TagRepository tagRepository;

  @PostConstruct
  private void init() {
    List<Tag> tags = importJsonFromFile();

    long size = tagRepository.count();
    if (size == 0 || size != tags.size()) {
      LOGGER.warning("Tags repository synchronized with new data");
      tagRepository.saveAll(tags);
      tagRepository.flush();
    } else {
      LOGGER.warning("Tags repository not synchronized with new data, old data is fine");
    }
  }

  /*
   * public Tag findTag(String deviceName) {
   * 
   * List<Tag> families = tagRepository.findAll(deviceName.substring(0, 3));
   * return families.get(0);
   * 
   * }
   */

  public List<Tag> importJsonFromFile() {

    List<Tag> tags = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    
    TypeReference<List<Tag>> typeReference = new TypeReference<List<Tag>>() {
    };

    try {
      File jsonFile = new ClassPathResource(this.jsonFile).getFile();
      tags = mapper.readValue(jsonFile, typeReference);
      //tags.add(tag );
      LOGGER.info("Import tags from JSON file success");
    } catch (IOException e) {
      LOGGER.warning("Import tags from JSON file failed: " + e.getMessage());
    }

    return tags;
  }

  public long size() {
    return tagRepository.count();
  }
}
