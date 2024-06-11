package com.koopey.api.model.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class PasswordDto implements Serializable {
    private String oldPassword;
    private String newPassword;
}
