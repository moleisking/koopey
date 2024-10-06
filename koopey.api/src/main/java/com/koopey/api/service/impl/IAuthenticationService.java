package com.koopey.api.service.impl;

import com.koopey.api.exception.AuthenticationException;
import com.koopey.api.model.authentication.AuthenticationUser;
import com.koopey.api.model.dto.AuthenticationDto;
import com.koopey.api.model.entity.User;

import java.util.UUID;

public interface IAuthenticationService {

    AuthenticationUser login(AuthenticationDto loginUser) throws AuthenticationException;
    Boolean register(User user);
    Boolean changeAlias(UUID userId, String oldAlias, String newAlias, String password);
    Boolean changeEmail(UUID userId,String oldEmail, String newEmail, String password);
    Boolean changePassword(UUID userId, String oldPassword, String newPassword);
    Boolean checkUserExistence(User user);
    Boolean checkAliasExistence(User user);
    Boolean sendForgotPasswordLink(String email);
    Boolean sendVerifyLink(String email);
}
