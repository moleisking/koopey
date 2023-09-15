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

    @GET("/message/count/by/receiver")
    Call<Integer> getCountByReceiver();

    @GET("/message/count/by/receiver/or/sender")
    Call<Integer> getCountByReceiverOrSender();

    @GET("/message/count/by/sender")
    Call<Integer> getCountBySender();

    @GET("/message/read/{messageId}")
    Call<Message> getMessage(@Path("MessageId") String messageId);

    @GET("/message/search/by/receiver/or/sender")
    Call<Messages> getMessageSearchReceiverOrSender();

    @POST("/message/create")
    Call<String> postMessageCreate(@Body Message message);

    @POST("/message/delete")
    Call<Void> postMessageDelete(@Body Message message);

    @POST("/message/search")
    Call<Messages> postMessageSearch(@Body Search search);

    @POST("/message/update")
    Call<Void> postMessageUpdate(@Body Message message);
}
