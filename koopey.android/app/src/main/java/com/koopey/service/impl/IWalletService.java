package com.koopey.service.impl;

import com.koopey.model.Location;
import com.koopey.model.Locations;
import com.koopey.model.Search;
import com.koopey.model.Wallet;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IWalletService {

    @GET("/wallet/read/{walletId}")
    Call<Location> get(@Path("walletId") String walletId);
    @POST("/wallet/delete")
    Call<Void> delete(@Body Wallet wallet);
    @POST("/wallet/create")
    Call<String> post(@Body Wallet wallet);
    @POST("/wallet/search")
    Call<Locations> post(@Body Search search);
    @POST("/wallet/update")
    Call<Void> put(@Body Location location);
}
