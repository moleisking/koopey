package com.koopey.service;

import androidx.appcompat.app.AppCompatActivity;

import com.koopey.helper.SerializeHelper;
import com.koopey.model.AuthUser;

public class AuthenticationService {

    private AppCompatActivity activity;
    private AuthUser authUser;

    public AuthenticationService(AppCompatActivity activity) {
        this.activity = activity;
    }

    public AuthUser getAuthenticationUser(){
        if (SerializeHelper.hasFile(activity, AuthUser.AUTH_USER_FILE_NAME)) {
            this.authUser = (AuthUser) SerializeHelper.loadObject(activity, AuthUser.AUTH_USER_FILE_NAME);
        } else {
            this.authUser = new AuthUser();
            this.authUser.token = "123";
            this.authUser.name = "test";
            this.authUser.email = "test@koopey";
        }
        return authUser;
    }

    public boolean isAuthenticated(){
        return this.authUser.token.isEmpty();
    }
}
