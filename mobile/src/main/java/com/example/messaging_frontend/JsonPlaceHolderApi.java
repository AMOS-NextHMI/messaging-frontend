package com.example.messaging_frontend;


import com.example.messaging_frontend.models.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {
    @GET("posts")
    Call<List<Message>> getMessages(@Header("Authorization") String authKey);


    @GET("conversationId=STRING")
    Call<String> getConversation(@Header("Authorization") String authKey);


    @POST("new_message")
    @FormUrlEncoded
    Call<Message> sendMessage(@Header("Authorization") String authKey, @Field("title") String title,
                        @Field("body") String body,
                        @Field("userId") long userId);


}
