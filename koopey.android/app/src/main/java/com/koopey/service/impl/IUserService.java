package com.koopey.service.impl;

import com.koopey.model.User;
import com.koopey.model.Users;
import com.koopey.model.Search;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IUserService {

    @GET("/user/read/{userId}")
    Call<User> getUser(@Path("UserId") String userId);

    @POST("/user/create")
    Call<String> postUserCreate(@Body User user);

    @POST("/user/delete")
    Call<Void> postUserDelete(@Body User user);

    @POST("/user/search")
    Call<Users> postUserSearch(@Body Search search);

    @POST("/user/update")
    Call<String> postUserUpdate(@Body User user);
}
