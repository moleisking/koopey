package com.koopey.service.impl;

import com.koopey.model.Tag;
import com.koopey.model.Tags;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ITagService {

    @Headers({"Content-Type: application/json"})
    @GET("/tag/read/many")
    Call<Tags> searchTags();

}
