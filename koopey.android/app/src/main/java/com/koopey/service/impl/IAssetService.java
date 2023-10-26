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

    @POST("/asset/create")
    Call<String> create(@Body Asset asset);
    @POST("/asset/delete")
    Call<Void> delete(@Body Asset asset);
    @GET("/asset/read/{assetId}")
    Call<Asset> read(@Path("assetId") String assetId);
    @GET("/asset/search/by/buyer")
    Call<Assets> searchByBuyer();
    @GET("/asset/search/by/buyer/or/seller")
    Call<Assets> searchByBuyerOrSeller();
    @GET("/asset/search/by/seller")
    Call<Assets> searchBySeller();
    @POST("/asset/search")
    Call<Assets> search(@Body Search search);
    @POST("/asset/update")
    Call<Void> update(@Body Asset asset);
    @GET("/asset/update/available/{available}")
    Call<Void> updateAssetAvailable(@Path("available") Boolean available);

}
