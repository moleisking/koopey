package com.koopey.api.model.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserChangeEmailDto implements Serializable {
    private String oldEmail;
    private String newEmail;
}
