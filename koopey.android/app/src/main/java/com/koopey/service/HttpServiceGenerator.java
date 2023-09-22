package com.koopey.service;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpServiceGenerator {

    private static OkHttpClient.Builder httpClient
            = new OkHttpClient.Builder();

    public static <S> S createService(Class<S> serviceClass, final String baseURL) {
         Retrofit.Builder builder
                = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create());

         Retrofit retrofit = builder.build();

        return retrofit.create(serviceClass);
    }

    public static <S> S createService(Class<S> serviceClass, final String baseURL, final String token, final String language) {
        Retrofit.Builder builder
                = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        if (token != null) {
            httpClient.interceptors().clear();
            httpClient.addInterceptor( chain -> {
                Request original = chain.request();
                Request.Builder builder1 = original.newBuilder()
                        .header("Authorization", "Bearer " + token)
                        .addHeader("Content-Language", language)
                        .addHeader("Content-Type", "application/json");
                Request request = builder1.build();
                return chain.proceed(request);
            });
            builder.client(httpClient.build());
            retrofit = builder.build();
        }
        return retrofit.create(serviceClass);
    }
}
