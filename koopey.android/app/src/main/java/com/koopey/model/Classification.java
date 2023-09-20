package com.koopey.model;

import com.koopey.model.base.Base;

import java.io.Serializable;
import java.util.UUID;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class Classification extends Base implements Serializable {

    public static final String CLASSIFICATION_FILE_NAME = "classification.dat";

    public String assetId ;
    public String tagId;
}
