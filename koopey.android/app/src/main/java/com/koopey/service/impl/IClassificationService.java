package com.koopey.service.impl;

import com.koopey.model.Assets;
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
    Call<String> createClassification(@Body Classification classification);
    @POST("/classification/delete")
    Call<Void> deleteClassification(@Body Classification classification);
    @GET("/classification/read/{classificationId}")
    Call<Classification> readClassification(@Path("classificationId") String classificationId);
    @POST("/classification/search/by/tags")
    Call<Assets> searchClassificationByTags(@Body Tags tags);
    @POST("/classification/search/by/asset")
    Call<Tags> searchClassificationByAsset(@Body String assetId);
    @POST("/classification/update")
    Call<Void> updateClassification(@Body Classification classification);
}
