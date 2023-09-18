package com.koopey.service.impl;

import com.koopey.model.Classification;
import com.koopey.model.Games;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IGameService {

    @GET("/game/read/{gameId}")
    Call<Classification> get(@Path("gameId") String gameId);

    @POST("/game/create")
    Call<String> postCreate(@Body Games game);

    @POST("/game/delete")
    Call<Void> postDelete(@Body Games game);

    @POST("/game/search")
    Call<com.koopey.helper.Games> postSearch(@Body Games game);

    @POST("/game/update")
    Call<Void> postUpdate(@Body Games game);
}
