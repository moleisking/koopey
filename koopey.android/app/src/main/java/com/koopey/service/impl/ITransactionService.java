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
    Call<Transaction> getTransaction(@Path("transactionId") String transactionId);

    @GET("/transaction/search/by/asset/{assetId}")
    Call<Transactions> getTransactionSearchByAsset(@Path("assetId") String assetId);

    @GET("/transaction/search/by/buyer")
    Call<Transactions> getTransactionSearchByBuyer();

    @GET("/transaction/search/by/buyer/or/seller")
    Call<Transactions> getTransactionSearchByBuyerOrSeller();

    @GET("/transaction/search/by/destination/{locationId}")
    Call<Transactions> getTransactionSearchByDestination(@Path("locationId") String locationId);

    @GET("/transaction/search/by/seller")
    Call<Transactions> getTransactionSearchBySeller();

    @GET("/transaction/search/by/source/{locationId}")
    Call<Transactions> getTransactionSearchBySource(@Path("locationId") String locationId);

    @POST("/transaction/create")
    Call<String> postTransactionCreate(@Body Transaction transaction);

    @POST("/transaction/delete")
    Call<Void> postTransactionDelete(@Body Transaction transaction);

    @POST("/transaction/search")
    Call<Transactions> postTransactionSearch(@Body Search search);

    @POST("/transaction/update")
    Call<Void> postTransactionUpdate(@Body Transaction transaction);
}
