package com.koopey.service.impl;

import com.koopey.model.Asset;
import com.koopey.model.Assets;
import com.koopey.model.Search;
import com.koopey.model.Wallet;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IWalletService {

    @GET("/wallet/read/{walletId}")
    Call<Asset> get(@Path("walletId") String walletId);
    @POST("/wallet/delete")
    Call<Void> delete(@Body Wallet wallet);
    @POST("/wallet/create")
    Call<String> post(@Body Wallet wallet);
    @POST("/wallet/search")
    Call<Assets> post(@Body Search search);
    @POST("/wallet/update")
    Call<Void> put(@Body Asset asset);
}
