package com.koopey.service.impl;

import com.koopey.model.Search;
import com.koopey.model.Message;
import com.koopey.model.Messages;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IMessageService {

    @GET("/message/read/{messageId}")
    Call<Message> getMessage(@Path("MessageId") String messageId);

    @POST("/message/create")
    Call<String> postMessageCreate(@Body Message message);

    @POST("/message/delete")
    Call<Void> postMessageDelete(@Body Message message);

    @POST("/message/search")
    Call<Messages> postMessageSearch(@Body Search search);

    @POST("/message/update")
    Call<String> postMessageUpdate(@Body Message message);
}
