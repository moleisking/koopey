package com.koopey.service.impl;

import com.koopey.model.Asset;
import com.koopey.model.Assets;
import com.koopey.model.Search;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IAssetService {

    @GET("/asset/read/{assetId}")
    Call<Asset> getAsset(@Path("assetId") String assetId);

    @GET("/asset/search/by/buyer")
    Call<Assets> getAssetsSearchByBuyer();

    @GET("/asset/search/by/buyer/or/seller")
    Call<Assets> getAssetsSearchByBuyerOrSeller();

    @GET("/asset/search/by/seller")
    Call<Assets> getAssetsSearchBySeller();

    @GET("/asset/update/available/{available}")
    Call<Void> getAssetUpdateAvailable(@Path("available") Boolean available);

    @POST("/asset/create")
    Call<String> postAssetCreate(@Body Asset asset);

    @POST("/asset/delete")
    Call<Void> postAssetDelete(@Body Asset asset);

    @POST("/asset/search")
    Call<Assets> postAssetSearch(@Body Search search);

    @POST("/asset/update")
    Call<Void> postAssetUpdate(@Body Asset asset);

}
