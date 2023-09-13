package com.koopey.service.impl;

import com.koopey.model.Asset;
import com.koopey.model.Assets;
import com.koopey.model.Search;

import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IAssetService {

    @POST("/asset/create")
    Call<String> postAssetCreate(@Body Asset asset);

    @POST("/asset/delete")
    Call<Void> postAssetDelete(@Body Asset asset);

    @POST("/asset/update")
    Call<String> postAssetUpdate(@Body Asset asset);

    @GET("/asset/update/available/{available}")
    Call<String> postAssetUpdateAvailable();

    @GET("/asset/{username}")
    Call<Asset> getAssets(@Path("assetId") String assetId);

    @GET("/asset/read/{username}")
    Call<Asset> readAsset(@Path("username") String username);

    @POST("/asset/search")
    Call<Assets> postAssetSearch(@Body Search search);

    @GET("/asset/search/by/buyer")
    Call<Assets> postAssetSearchByBuyer();

    @GET("/asset/search/by/seller")
    Call<Assets> postAssetSearchBySeller(
    );

    @GET("/asset/search/by/buyer/or/seller")
    Call<Assets> postAssetSearchByBuyerOrSeller(
    );
}
