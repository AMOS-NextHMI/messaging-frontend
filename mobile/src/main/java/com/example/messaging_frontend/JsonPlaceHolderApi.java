package com.example.messaging_frontend;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {
    @GET("messages")
    Call<List<Message>> getMessages();
}
