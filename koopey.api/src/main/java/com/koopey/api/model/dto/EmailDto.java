package com.koopey.api.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmailDto implements Serializable {
    private String password;
    private String oldEmail;
    private String newEmail;
}
