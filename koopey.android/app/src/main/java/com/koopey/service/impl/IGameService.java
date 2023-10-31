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
    Call<Classification> read(@Path("gameId") String gameId);

    @POST("/game/create")
    Call<String> create(@Body Games game);

    @POST("/game/delete")
    Call<Void> delete(@Body Games game);

    @POST("/game/search")
    Call<Games> search(@Body Games game);

    @POST("/game/update")
    Call<Void> update(@Body Games game);
}
