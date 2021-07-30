package com.koopey.api.model.authentication;

import lombok.Data;

@Data
public class ChangeEmail {
    private String oldEmail;
    private String newEmail;
}
