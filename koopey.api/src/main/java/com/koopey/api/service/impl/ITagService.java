package com.koopey.api.service.impl;

import com.koopey.api.model.entity.Tag;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITagService {

    Page<Tag> find(String str, String language, Pageable pagable);

    List<Tag> findAll(String language);

    List<Tag> findSuggestions(String str, String language);

    List<Tag> findPopularTags();

    List<Tag> importJsonFromFile();

    long size();

    void insertTag(Tag t);
}
