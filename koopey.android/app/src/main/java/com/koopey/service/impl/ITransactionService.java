package com.koopey.service.impl;

import com.koopey.model.Locations;
import com.koopey.model.Search;
import com.koopey.model.Transaction;
import com.koopey.model.Transactions;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ITransactionService {

    @POST("/transaction/create")
    Call<String> create(@Body Transaction transaction);
    @DELETE("/transaction/delete")
    Call<Void> delete(@Body Transaction transaction);
    @GET("/transaction/read/{transactionId}")
    Call<Transaction> read(@Path("transactionId") String transactionId);
    @GET("/transaction/first/by/{assetId}")
    Call<Transaction> first(@Path("assetId") String assetId);
    @POST("/transaction/search")
    Call<Transactions> search(@Body Search search);
    @GET("/transaction/search/by/{assetId}")
    Call<Transactions> searchByAsset(@Path("assetId") String assetId);
    @GET("/transaction/search/by/location/{locationId}")
    Call<Transactions> searchByLocation(@Path("locationId") String locationId);
    @GET("/transaction/search/by/buyer")
    Call<Transactions> searchByBuyer();
    @GET("/transaction/search/by/buyer/or/seller")
    Call<Transactions> searchByBuyerOrSeller();
    @GET("/transaction/search/by/destination/{locationId}")
    Call<Transactions> searchByDestination(@Path("locationId") String locationId);
    @GET("/transaction/search/by/seller")
    Call<Transactions> searchBySeller();
    @GET("/transaction/search/by/source/{locationId}")
    Call<Transactions> searchBySource(@Path("locationId") String locationId);
    @PUT("/transaction/update")
    Call<Void> update(@Body Transaction transaction);
}
