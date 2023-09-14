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

    @GET("/classification/read/{classificationId}")
    Call<Classification> getClassification(@Path("classificationId") String classificationId);

    @POST("/classification/create")
    Call<String> postClassificationCreate(@Body Classification classification);

    @POST("/classification/delete")
    Call<Void> postClassificationDelete(@Body Classification classification);

    @POST("/classification/search/by/tags")
    Call<Assets> postClassificationSearchByTags(@Body Tags tags);

    @POST("/classification/search/by/asset")
    Call<Tags> postClassificationSearchByAsset(@Body String assetId);

    @POST("/classification/update")
    Call<Void> postClassificationUpdate(@Body Classification classification);
}
