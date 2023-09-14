package com.koopey.model;

import com.koopey.model.base.Base;

import java.io.Serializable;
import java.util.UUID;

public class Classification extends Base implements Serializable {

    public static final String CLASSIFICATION_FILE_NAME = "classification.dat";

    public String id = "";
    public String assetId = "";
    public String tagId = "";
}
