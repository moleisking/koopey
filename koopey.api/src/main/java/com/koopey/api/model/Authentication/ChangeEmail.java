package com.koopey.api.model.Authentication;

import lombok.Data;

@Data
public class ChangeEmail {
    private String oldEmail;
    private String newEmail;
}
