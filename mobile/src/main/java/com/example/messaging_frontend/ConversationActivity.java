package com.example.messaging_frontend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.messaging_frontend.auth.AuthenticationInterceptor;
import com.example.messaging_frontend.models.Contact;
import com.example.messaging_frontend.models.Message;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



/**
 * This activity displays the chat screen with a contact.
 *
 * TODO: Implement the following
 * This includes the following:
 * - User Pro-pic and name on top
 * - scrollable messages scrollView
 * - text box
 * - send button that works if text box isn't empty
 */




/*
* Readapted the Message class to have the same structure as the Post Json files simulated by JSONPlaceHolder
* in order to get back to the needed parameters and namings for the app,
* 1. delete all parts of the code with //DELETE in front of them
* 2. uncomment all parts of the code with //UNCOMMENT in front of them*/



public class ConversationActivity extends AppCompatActivity {

    /**
     * TODO: upon creation this activity is provided with an intent that includes an instance of a meta-conversation
     * TODO: This activity uses the meta-conversation to then request the messages history and display it.
     */
    private RecyclerView mMessageRecycler;
    private static String API_BASE_URL = "https://130.149.172.169/";
    private ConversationAdapter mMessageAdapter;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private  static  Retrofit retrofit;

    Button sendButton;
    EditText messageSent;

    List<Message> messageList;
    Contact mSender;
    Contact mReceiver;
    Date mDate;
    String authKey;

    JsonPlaceHolderApi jsonPlaceHolderApi;
    BroadcastReceiver br;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        messageList=new ArrayList<>();
        authKey="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVlMzcwMTJhY2Q1ZTRiMDAyYTQyMDE1NSIsInVzZXJuYW1lIjoiYWxsZW4iLCJpYXQiOjE1ODA2NjMwODJ9.sBYcYnntsH_AGN-ULvXkqJAsmcLS-vVbvSzuSiv_qDU";

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
               // Retrofit retrofit= createService(ConversationActivity.class);


                Call<String> call = jsonPlaceHolderApi.getConversation(authKey);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(!response.isSuccessful()){

                            return;
                        }
                        Log.i("Server","works");
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.i("ConversationActivityMESSAGE",t.getMessage());
                    }
                });



            }
        };


        registerReceiver(br, new IntentFilter("SERVER_NOTIFICATION"));


        mSender = new Contact("Feriel",2);
        mReceiver = new Contact("Mahmoud",3);
        mDate = new Date();
        mMessageRecycler = (RecyclerView) findViewById(R.id.reyclerview_message_list);
        mMessageAdapter = new ConversationAdapter(this, messageList);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
        mMessageRecycler.setAdapter(mMessageAdapter);
        sendButton= (Button) findViewById(R.id.button_send);

        sendButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                send_message();

            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.conversation_toolbar);
        toolbar.setTitle(mReceiver.getName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(br);
        super.onDestroy();
    }


    /**
     * returns a list of all messages in a conversation
     */
    private ArrayList<Message> get_conversation(/* an ID for the conversation */) {
        return new ArrayList<Message>();
    }


    /**
     * handles the UI aspect of sending a message and sends out a send_message query.
     * params can be added if/when needed
     */
    private void send_message(){

        messageSent = (EditText) findViewById(R.id.text_chatbox);
        Message message = new Message(mSender, messageSent.getText().toString(), mDate.getTime());
        messageSent.getText().clear();
        messageList.add(message);
        mMessageAdapter.notifyItemInserted(messageList.indexOf(message));
        Call<Message> call = jsonPlaceHolderApi.sendMessage(authKey,"Test","Test",11);



    }








}