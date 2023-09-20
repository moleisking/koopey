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
    Call<Location> readLocation(@Path("locationId") String locationId);
    @GET("/location/search/by/buyer/and/destination")
    Call<Locations> searchLocationByBuyerAndDestination();
    @GET("/location/search/by/buyer/and/source")
    Call<Locations> searchLocationByBuyerAndSource();
    @GET("/location/search/by/destination/and/seller")
    Call<Locations> searchLocationByDestinationAndSeller();
    @GET("/location/search/by/seller/and/source")
    Call<Locations> searchLocationBySellerAndSource();
    @POST("/location/create")
    Call<String> createLocation(@Body Location location);
    @POST("/location/delete")
    Call<Void> deleteLocation(@Body Location location);
    @POST("/location/search")
    Call<Locations> searchLocation(@Body Search search);
    @POST("/location/search/by/geocode")
    Call<Location> searchLocationByGeocode(@Body Location location);
    @POST("/location/search/by/place")
    Call<Location> searchLocationByPlace(@Body Location location);
    @POST("/location/search/by/range/in/kilometers")
    Call<Locations> searchLocationByRangeInKilometers(@Body Search search);
    @POST("/location/search/by/range/in/miles")
    Call<Locations> searchLocationByRangeInMiles(@Body Search search);
    @POST("/location/update")
    Call<Void> updateLocation(@Body Location location);
}
