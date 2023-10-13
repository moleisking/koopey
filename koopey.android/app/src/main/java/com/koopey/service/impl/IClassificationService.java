package com.koopey.service.impl;

import com.koopey.model.Assets;
import com.koopey.model.Locations;
import com.koopey.model.Search;
import com.koopey.model.Classification;
import com.koopey.model.Classifications;
import com.koopey.model.Tags;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IClassificationService {
    @POST("/classification/create")
    Call<String> create(@Body Classification classification);
    @POST("/classification/delete")
    Call<Void> delete(@Body Classification classification);
    @GET("/classification/read/{classificationId}")
    Call<Classification> read(@Path("classificationId") String classificationId);
    @POST("/classification/search/by/asset/{assetId}")
    Call<Tags> searchByAsset(@Path("assetId") String assetId);
    @POST("/classification/search/by/tags")
    Call<Assets> searchByTags(@Body Tags tags);
    @POST("/classification/update")
    Call<Void> update(@Body Classification classification);
}
