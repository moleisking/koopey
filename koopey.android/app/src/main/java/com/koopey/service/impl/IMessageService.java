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
    Call<Integer> countMessagesByReceiver();
    @GET("/message/count/by/receiver/or/sender")
    Call<Integer> countMessagesByReceiverOrSender();
    @GET("/message/count/by/sender")
    Call<Integer> countMessagesBySender();
    @POST("/message/create")
    Call<String> createMessage(@Body Message message);
    @POST("/message/delete")
    Call<Void> deleteMessage(@Body Message message);
    @GET("/message/read/{messageId}")
    Call<Message> readMessage(@Path("messageId") String messageId);
    @GET("/message/search/by/receiver/or/sender")
    Call<Messages> searchMessageByReceiverOrSender();
    @POST("/message/search")
    Call<Messages> searchMessage(@Body Search search);
    @POST("/message/update")
    Call<Void> updateMessage(@Body Message message);
}
