package com.koopey.service.impl;

import com.koopey.model.User;
import com.koopey.model.Users;
import com.koopey.model.Search;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
    Call<Void> updateAvailable(@Path("available") Boolean available);

    @GET("/user/update/currency/{currency}")
    Call<Void> updateCurrency(@Path("currency") String currency);

    @GET("/user/update/language/{language}")
    Call<Void> updateLanguage(@Path("language") String language);

    @GET("/user/update/location")
    Call<Void> updateLocation(@Query("altitude") Double altitude,@Query("latitude") Double latitude, @Query("longitude") Double longitude);

    @GET("/user/update/measure/{measure}")
    Call<Void> updateMeasure(@Path("measure") String measure);

    @GET("/user/update/track/{track}")
    Call<Void> updateTrack(@Path("track") Boolean track);

    @GET("/user/update/term/{term}")
    Call<Void> updateTerm(@Path("term") Boolean term);

    @GET("/user/update/notify/by/email/{email}")
    Call<Void> updateNotifyByEmail(@Path("email") Boolean email);

    @GET("/user/update/notify/by/device/{device}")
    Call<Void> updateNotifyByDevice(@Path("device") Boolean device);
}
