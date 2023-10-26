package com.koopey.service.impl;

import com.koopey.model.Search;
import com.koopey.model.Location;
import com.koopey.model.Locations;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ILocationService {

    @GET("/location/read/{locationId}")
    Call<Location> read(@Path("locationId") String locationId);
    @GET("/location/search/by/buyer/and/destination")
    Call<Locations> searchByBuyerAndDestination();
    @GET("/location/search/by/buyer/and/source")
    Call<Locations> searchByBuyerAndSource();
    @GET("/location/search/by/destination/and/seller")
    Call<Locations> searchLocationByDestinationAndSeller();
    @GET("/location/search/by/seller/and/source")
    Call<Locations> searchBySellerAndSource();
    @POST("/location/create")
    Call<String> create(@Body Location location);
    @POST("/location/delete")
    Call<Void> delete(@Body Location location);
    @POST("/location/search")
    Call<Locations> search(@Body Search search);
    @POST("/location/search/by/geocode")
    Call<Location> searchByGeocode(@Body Location location);
    @POST("/location/search/by/place")
    Call<Location> searchByPlace(@Body Location location);
    @POST("/location/search/by/range/in/kilometers")
    Call<Locations> searchByRangeInKilometers(@Body Search search);
    @POST("/location/search/by/range/in/miles")
    Call<Locations> searchByRangeInMiles(@Body Search search);
    @POST("/location/update")
    Call<Void> update(@Body Location location);
}
