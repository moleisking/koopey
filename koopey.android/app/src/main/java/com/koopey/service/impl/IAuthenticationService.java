package com.koopey.service.impl;

import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.model.authentication.RegisterUser;
import com.koopey.model.authentication.Token;
import com.koopey.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IAuthenticationService {

    @Headers("Content-Type: application/json")
    @POST("/authenticate/login")
    Call<Token> login(@Body AuthenticationUser authenticationUser);

    @Headers("Content-Type: application/json")
    @POST("/authenticate/register")
    Call<User> register(@Body RegisterUser register);

}
