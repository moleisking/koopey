package com.koopey.service.impl;

import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.model.authentication.ChangePassword;
import com.koopey.model.authentication.ForgotPassword;
import com.koopey.model.authentication.LoginUser;
import com.koopey.model.authentication.RegisterUser;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IAuthenticationService {

    @Headers("Content-Type: application/json")
    @POST("/authenticate/login")
    Call<AuthenticationUser> login(@Body LoginUser loginUser);

    @Headers("Content-Type: application/json")
    @POST("/authenticate/register")
    Call<Void> register(@Body RegisterUser register);

    @Headers("Content-Type: application/json")
    @POST("/authenticate/password/change")
    Call<Void> changePassword(@Body ChangePassword changePassword);

    @Headers("Content-Type: application/json")
    @POST("/authenticate/password/forget")
    Call<Void> forgotPassword(@Body ForgotPassword forgotPassword);

}
