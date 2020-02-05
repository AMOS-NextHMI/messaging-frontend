package com.example.messaging_frontend;


import com.example.messaging_frontend.models.Conversation;
import com.example.messaging_frontend.models.Message;
import com.example.messaging_frontend.models.MetaConversation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {



    @GET("/")
    Call<Conversation> getConversation(@Header("Authorization") String authKey);


    @GET("conversations")
    Call<List<MetaConversation>> getConversationOverview(@Header("Authorization") String authKey,@Header("Content-Type") String contentType);


    @POST("messages")
    @FormUrlEncoded
    Call<Message> sendMessage(@Header("Authorization") String authKey,String messageText);


}
