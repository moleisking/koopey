package com.koopey.service.impl;

import com.koopey.model.User;
import com.koopey.model.Users;
import com.koopey.model.Search;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IUserService {

    @GET("/user/read/{userId}")
    Call<User> read(@Path("userId") String userId);

    @POST("/user/search")
    Call<Users> search(@Body Search search);

    @PUT("/user/update")
    Call<Void> update(@Body User user);

    @PATCH("/user/update/available/{available}")
    Call<Void> updateAvailable(@Path("available") Boolean available);

    @PATCH("/user/update/currency/{currency}")
    Call<Void> updateCurrency(@Path("currency") String currency);

    @PATCH("/user/update/language/{language}")
    Call<Void> updateLanguage(@Path("language") String language);

    @PATCH("/user/update/location")
    Call<Void> updateLocation(@Query("altitude") Double altitude,@Query("latitude") Double latitude, @Query("longitude") Double longitude);

    @PATCH("/user/update/measure/{measure}")
    Call<Void> updateMeasure(@Path("measure") String measure);

    @PATCH("/user/update/track/{track}")
    Call<Void> updateTrack(@Path("track") Boolean track);

    @PATCH("/user/update/term/{term}")
    Call<Void> updateTerm(@Path("term") Boolean term);

    @PATCH("/user/update/notify/by/email/{email}")
    Call<Void> updateNotifyByEmail(@Path("email") Boolean email);

    @PATCH("/user/update/notify/by/device/{device}")
    Call<Void> updateNotifyByDevice(@Path("device") Boolean device);
}
