package com.koopey.service.impl;

import com.koopey.model.Search;
import com.koopey.model.Message;
import com.koopey.model.Messages;
import com.koopey.model.type.MessageType;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IMessageService {

    @GET("/message/count/by/receiver")
    Call<Integer> countByReceiver(@Query("type") MessageType type);
    @GET("/message/count/by/receiver/or/sender")
    Call<Integer> countMessagesByReceiverOrSender(@Query("type") MessageType type);
    @GET("/message/count/by/sender")
    Call<Integer> countBySender(@Query("type") MessageType type);
    @POST("/message/create")
    Call<String> create(@Body Message message);
    @DELETE("/message/delete")
    Call<Void> delete(@Body Message message);
    @GET("/message/read/{messageId}")
    Call<Message> read(@Path("messageId") String messageId);
    @GET("/message/search/by/receiver")
    Call<Messages> searchByReceiver(@Query("type") String type);
    @GET("/message/search/by/receiver/or/sender")
    Call<Messages> searchByReceiverOrSender(@Query("type") String type);
    @GET("/message/search/by/sender")
    Call<Messages> searchBySender(@Query("type") String type);
    @POST("/message/search")
    Call<Messages> search(@Body Search search);
    @PUT("/message/update")
    Call<Void> update(@Body Message message);
}
