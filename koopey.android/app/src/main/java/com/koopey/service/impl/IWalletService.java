package com.koopey.service.impl;

import com.koopey.model.Search;
import com.koopey.model.Wallet;
import com.koopey.model.Wallets;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IWalletService {

    @GET("/wallet/read/{walletId}")
    Call<Wallet> read(@Path("walletId") String walletId);
    @POST("/wallet/delete")
    Call<Void> delete(@Body Wallet wallet);
    @POST("/wallet/create")
    Call<String> create(@Body Wallet wallet);
    @POST("/wallet/search")
    Call<Wallets> search(@Body Search search);
    @POST("/wallet/update")
    Call<Void> update(@Body Wallet wallet);
}
