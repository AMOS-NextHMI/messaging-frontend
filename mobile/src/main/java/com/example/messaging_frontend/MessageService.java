package com.example.messaging_frontend;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.messaging_frontend.models.Conversation;
import com.example.messaging_frontend.models.Message;
import com.example.messaging_frontend.models.MetaConversation;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MessageService extends Service {
    // For help see:
    // https://developer.android.com/guide/components/services and
    // https://developer.android.com/guide/components/bound-services

    private static final String TAG = "MessageService";
    private final IBinder binder = new LocalBinder();

    private OkHttpClient client;
    private WebSocket ws;
    private static String API_BASE_URL = "https://130.149.172.169/";
    private List<MetaConversation> metaConversations;
    public Handler handler = null;
    public static Runnable runnable = null;
    private List<Intent> boundIntent ;
    private List<Activity> boundActivity;
    Conversation conversation;

    String  authKey="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVlMzczNjYyOGNjN2FjMDAzYjNmYzhhZSIsInVzZXJuYW1lIjoiU1RSSU5HIiwiaWF0IjoxNTgwNjc2NzA2fQ.zGGnDZ2qEeBZ9FbpkUmtry_pL594lIjoYXELziKDAnQ";
    JsonPlaceHolderApi jsonPlaceHolderApi;




    // The binder which we give to the client via onBind()
    public class LocalBinder extends Binder {
        MessageService getService() {
            // Return this instance of MessageService so clients can call public methods
            return MessageService.this;
        }
    }

    @Override
    public void onCreate() {
        boundIntent = new ArrayList<>();
        boundActivity = new ArrayList<>();
        metaConversations = new ArrayList<>();

        handler = new Handler();

        runnable = new Runnable() {
            public void run() {

                try {
                    getConversationOverview();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                handler.postDelayed(runnable, 10000);
            }
        };
        handler.postDelayed(runnable, 15000);

    }

    @Override
    public IBinder onBind(Intent intent) {

        boundIntent.add(intent);
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
        // If we get killed, after returning from here, restart
        return START_STICKY;
    }



    public List<MetaConversation> getConversationOverview() throws InterruptedException {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://130.149.172.169/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);



       // Log.i("MessageService", String.valueOf(boundActivity));

        Call<List<MetaConversation>> call = jsonPlaceHolderApi.getConversationOverview(authKey, "application/json");

        call.enqueue(new Callback<List<MetaConversation>>() {
            @Override
            public void onResponse(Call<List<MetaConversation>> call, Response<List<MetaConversation>> response) {
                if (!response.isSuccessful()) {

                    Log.i("MessageService, getConversationOverview()", "Unsuccessful: " + response);
                    return;
                }

                Log.i("MessageService, getConversationOverview()", "Successful" + response.body());
                if (metaConversations.size() == 0) {
                    metaConversations = response.body();
                    if(boundActivity.toString().contains("ConversationListActivity")){

                        ConversationsListActivity conversationsListActivity = (ConversationsListActivity) boundActivity.get(0);
                        conversationsListActivity.metaConversations = response.body();

                    }

                }

                if (metaConversations.size() != 0) {
                    if (metaConversations.size() != response.body().size()) {
                        // a new conversation started
                        List<MetaConversation> newConversation  = new ArrayList<>();
                        newConversation = metaConversations;
                        newConversation.remove(response.body());
                        metaConversations.addAll(newConversation);
                    } else if (!metaConversations.equals(response.body())) {
                        List<MetaConversation> updatedConversations  = new ArrayList<>();
                        updatedConversations = response.body();
                        updatedConversations.removeAll(metaConversations);
                        for (MetaConversation updatedConv : updatedConversations){
                            for(MetaConversation oldConv : metaConversations ){
                                if (oldConv.getConversationId()== updatedConv.getConversationId()){
                                    oldConv= updatedConv; //TO DO : MAKE SURE IT UPDATES THE LIST
                                }
                            }
                        }

                        if(boundActivity.toString().contains("ConversationActivity")){

                            ConversationActivity conversationActivity = (ConversationActivity) boundActivity.get(0);
                            String currentConversationID = conversationActivity.mConversation.getConversationId() ; //put  the id of the current convo  here
                            //if in activityView
                            for (MetaConversation  conv : updatedConversations) {
                                if (conv.getConversationId().contentEquals(currentConversationID)){

                                    Conversation updatedConversation = getConversation(currentConversationID);
                                    conversationActivity.mConversation =updatedConversation;
                                }




                            }

                        }

                        //if activityListView  is open
                        if(boundActivity.toString().contains("ConversationListActivity")){

                            ConversationsListActivity conversationsListActivity = (ConversationsListActivity) boundActivity.get(0);
                            conversationsListActivity.metaConversations = response.body();

                            }


                        //if none of the above, then notification

                    }
                }
            }

            @Override
            public void onFailure(Call<List<MetaConversation>> call, Throwable t) {
                Log.i("MessageService, getConversationOverview()", t.getMessage());
            }
        });

        return null;

    }
    public Conversation getConversation(String conversationId){

         Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://130.149.172.169/conversations/conversationId="+conversationId)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<Conversation> call = jsonPlaceHolderApi.getConversation(authKey);

        call.enqueue(new Callback<Conversation>() {
            @Override
            public void onResponse(Call<Conversation> call, Response<Conversation> response) {
                if(!response.isSuccessful()){
                    Log.i("MessageService, getConversation()","Unsuccessful: "+ response);

                    return ;
                }
                Log.i("MessageService, getConversation()",   "ConversationID: "+"\n"+ response.body().getConversationId()+"\n"+"Conversation members: "+response.body().getMember().toString()+"\n"+ "Conversation messages: "+response.body().getMessages().toString());
                //update the convo list in the  ConversationActivity
                conversation = new Conversation(response.body().getConversationId(),response.body().getName(),response.body().getMember(),response.body().getMessages());

            }
            @Override
            public void onFailure(Call<Conversation> call, Throwable t) {
                Log.i("MessageService, getConversation()",t.getMessage());
            }
        });

     return conversation;
    }

    public void addBoundIntent(Intent intent){
        boundIntent.add(intent);
    }
    public void removeBoundIntent(Intent intent){
        boundIntent.remove(intent);
    }

    public void addBoundActivity(Activity activity) {boundActivity.add(activity);
    }
    public void removeBoundActivity(Activity activity){

        boundActivity.remove(activity);
    }
    @Override
    public void onDestroy() {
        handler.removeCallbacks(runnable);

        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();

    }

    public void testMethod() {
        Toast.makeText(this, "hello, this is the background service", Toast.LENGTH_SHORT).show();
    }








/*
    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        Log.i(TAG, "RECEIVED BYTES: " + bytes.hex());
        Intent intent = new Intent("SERVER_NOTIFICATION");
        //NOT THE BEST WAY TO DO IT, BUT I DONT KNOW HOW TO USE THE NOTIFICATION, FURKAN IMPLEMENTED THAT
        Context context=getApplicationContext();
        context.sendBroadcast(intent);
    }
    */



}
