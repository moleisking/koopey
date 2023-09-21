package com.koopey.model;

import com.koopey.model.base.Base;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class Article extends Base {
    public static final String ARTICLE_FILE_NAME = "article.dat";
    Advert advert;
    User user;
    Images images;
    Location location;
    Tags tags;
    @Builder.Default
    int distance = 0;
    @Builder.Default
    int quantity = 0;
    @Builder.Default
    boolean available = true;
}
