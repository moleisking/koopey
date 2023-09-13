package com.koopey.service.impl;

import com.koopey.model.Tag;
import com.koopey.model.Tags;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ITagService {
    @GET("/tag/read/many")
    Call<Tags> getTags();

}
