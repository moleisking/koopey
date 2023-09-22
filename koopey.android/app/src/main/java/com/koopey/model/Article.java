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
    private Advert advert;
    private User user;
    private Images images;
    private Location location;
    private Tags tags;
    @Builder.Default
    private int distance = 0;
    @Builder.Default
    private int quantity = 0;
    @Builder.Default
    private boolean available = true;
}
