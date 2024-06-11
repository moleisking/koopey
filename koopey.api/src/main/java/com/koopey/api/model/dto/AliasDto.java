package com.koopey.api.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AliasDto implements Serializable {
    private String password;
    private String oldAlias;
    private String newAlias;
}
