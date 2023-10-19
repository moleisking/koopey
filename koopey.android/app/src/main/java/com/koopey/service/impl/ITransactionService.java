package com.koopey.service.impl;

import com.koopey.model.Locations;
import com.koopey.model.Search;
import com.koopey.model.Transaction;
import com.koopey.model.Transactions;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ITransactionService {

    @POST("/transaction/create")
    Call<String> create(@Body Transaction transaction);
    @POST("/transaction/delete")
    Call<Void> delete(@Body Transaction transaction);
    @GET("/transaction/read/{transactionId}")
    Call<Transaction> read(@Path("transactionId") String transactionId);
    @POST("/transaction/search")
    Call<Transactions> search(@Body Search search);
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
    @POST("/transaction/update")
    Call<Void> update(@Body Transaction transaction);
}
