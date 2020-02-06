package com.example.messaging_frontend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.messaging_frontend.auth.AuthenticationInterceptor;
import com.example.messaging_frontend.models.Contact;
import com.example.messaging_frontend.models.Conversation;
import com.example.messaging_frontend.models.Message;
import com.example.messaging_frontend.models.MetaConversation;

import org.json.JSONException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.jackson.io.JacksonDeserializer;
//import io.jsonwebtoken.lang.Maps;
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

    ConversationAdapter mMessageAdapter;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();


    Button sendButton;


    MetaConversation mConversation;
     String myUserId;
    String myDisplayName;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    MessageService messageService;
    Activity myActivity;
     String token;
    String conversationId;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        myActivity= this;
        token = this.getIntent().getStringExtra("token");
        myDisplayName = this.getIntent().getStringExtra("displayName");
        conversationId = this.getIntent().getStringExtra("ConversationId");
        try {
            myUserId=  ConversationActivity.decoded(token);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Message> msgs = new ArrayList<>();
        bindService(new Intent(this, MessageService.class), connection, 0);

        mConversation = new MetaConversation(conversationId,"",null,msgs);
        mMessageRecycler = (RecyclerView) findViewById(R.id.reyclerview_message_list);
        mMessageAdapter = new ConversationAdapter(this, mConversation.getMessages(), myUserId);


        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
        mMessageRecycler.setAdapter(mMessageAdapter);
        sendButton= (Button) findViewById(R.id.button_send);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        sendButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    sendMessage();
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Defines callbacks for service binding, passed to bindService()
    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MessageService.LocalBinder binder = (MessageService.LocalBinder) service;
            MessageService mService = binder.getService();
            messageService = mService;
            mService.addBoundActivity(myActivity);

        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {

        }
    };

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
        messageService.removeBoundActivity(myActivity);
        unbindService(connection);
        super.onDestroy();
    }



    /**
     * handles the UI aspect of sending a message and sends out a send_message query.
     * params can be added if/when needed
     */
    private void sendMessage() throws IOException, JSONException {
        Date date = new Date();
        EditText messageSent = (EditText) findViewById(R.id.text_chatbox);

        Message message = new Message(myUserId,"",String.valueOf(date.getTime()),conversationId, messageSent.getText().toString());
        messageSent.getText().clear();
        mConversation.getMessages().add(message);
    //    mMessageAdapter.notifyItemInserted(mConversation.getMessages().indexOf(message));




        final String id_HTTP = mConversation.get_id();
        final String message_HTTP = message.getMessageText();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    messageService.sendMessage(id_HTTP, message_HTTP);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();





    }

    public static String decoded(String JWTEncoded)  {
        try {
            String[] split = JWTEncoded.split("\\.");
            String userId =getJson(split[1]);
            userId =userId.substring(7,userId.indexOf(",")-1);
            return userId;


        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    static String getJson(String token) throws UnsupportedEncodingException {

        byte[] decodedBytes = Base64.decode(token, Base64.URL_SAFE);
        return new String(decodedBytes, "UTF-8");


//        Keys.secretKeyFor(SignatureAlgorithm.HS256)


    }
}