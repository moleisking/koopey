package com.koopey.model;

import com.koopey.model.base.BaseCollection;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class Tags extends BaseCollection<Tag> {

    public static final String TAGS_FILE_NAME = "tags.dat";

}