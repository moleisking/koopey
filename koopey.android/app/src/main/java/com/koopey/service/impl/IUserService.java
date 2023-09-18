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
    Call<User> readUser(@Path("userId") String userId);

    @POST("/user/create")
    Call<String> createUser(@Body User user);

    @POST("/user/delete")
    Call<Void> deleteUser(@Body User user);

    @POST("/user/search")
    Call<Users> searchUser(@Body Search search);

    @POST("/user/update")
    Call<Void> updateUser(@Body User user);

    @GET("/user/update/available/{available}")
    Call<Void> updateUserAvailable(@Body Boolean available);

    @GET("/user/update/track/{track}")
    Call<Void> updateUserTrack(@Body Boolean track);
}
