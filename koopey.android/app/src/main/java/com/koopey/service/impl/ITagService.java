package com.koopey.service.impl;

import com.koopey.model.Tag;
import com.koopey.model.Tags;
import com.koopey.model.Wallet;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ITagService {

    @POST("/tag/create")
    Call<String> create(@Body Tag tag);
    @POST("/tag/delete")
    Call<Void> delete(@Body Tag tag);
    @GET("/tag/read/{tagId}")
    Call<Tag> read(@Path("tagId") String tagId);
    @Headers({"Content-Type: application/json"})
    @GET("/tag/read/many")
    Call<Tags> search();
    @POST("/tag/update")
    Call<Void> update(@Body Tag tag);

}
