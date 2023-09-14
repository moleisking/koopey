package com.koopey.service.impl;

import com.koopey.model.Assets;
import com.koopey.model.Search;
import com.koopey.model.Transaction;
import com.koopey.model.Transactions;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ITransactionService {

    @GET("/transaction/read/{transactionId}")
    Call<String> getTransactionRead();

    @POST("/transaction/create")
    Call<String> postTransactionCreate(@Body Transaction transaction);

    @POST("/transaction/delete")
    Call<String> postTransactionDelete(@Body Transaction transaction);

    @POST("/transaction/search")
    Call<Transactions> postTransactionSearch(@Body Search search);

    @GET("/transaction/search/by/asset/{assetId}")
    Call<Transactions> postTransactionSearchByAsset(@Path("available") String assetId);
    @GET("/transaction/search/by/buyer")
    Call<Transactions> postTransactionSearchByBuyer();

    @GET("/transaction/search/by/buyer/or/seller")
    Call<Transactions> postTransactionSearchByBuyerOrSeller();

    @GET("/transaction/search/by/destination/{locationId}")
    Call<Transactions> postTransactionSearchByDestination(@Path("locationId") String locationId);

    @GET("/transaction/search/by/seller")
    Call<Transactions> postTransactionSearchBySeller();

    @GET("/transaction/search/by/source/{locationId}")
    Call<Transactions> postTransactionSearchBySource(@Path("locationId") String locationId);

    @POST("/transaction/update")
    Call<String> postTransactionUpdate(@Body Transaction transaction);
}
