package com.koopey.api.model.Authentication;

import lombok.Data;

@Data
public class ChangePassword {
    private String oldPassword;
    private String newPassword;
}
