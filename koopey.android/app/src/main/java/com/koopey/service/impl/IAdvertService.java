package com.koopey.service.impl;

import com.koopey.model.Search;
import com.koopey.model.Advert;
//import com.koopey.model.Adverts;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IAdvertService {

    @GET("/advert/read/{advertId}")
    Call<Advert> getAdvert(@Path("AdvertId") String advertId);

    @POST("/advert/create")
    Call<String> postAdvertCreate(@Body Advert advert);

    @DELETE("/advert/delete")
    Call<Void> postAdvertDelete(@Body Advert advert);

   // @POST("/advert/search")
   // Call<Adverts> postAdvertSearch(@Body Search search);

    @PUT("/advert/update")
    Call<String> postAdvertUpdate(@Body Advert advert);
}
