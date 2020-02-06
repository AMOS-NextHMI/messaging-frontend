package com.example.messaging_frontend;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.messaging_frontend.models.Message;
import com.example.messaging_frontend.models.MetaConversation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
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
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    private static String API_BASE_URL = "https://130.149.172.169/";
    private List<MetaConversation> metaConversations;
    public Handler handler = null;
    public static Runnable runnable = null;

    private List<Activity> boundActivity;
    MetaConversation conversation;
    String token;

    //String  authKey="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVlMzczNjYyOGNjN2FjMDAzYjNmYzhhZSIsInVzZXJuYW1lIjoiU1RSSU5HIiwiaWF0IjoxNTgwNjc2NzA2fQ.zGGnDZ2qEeBZ9FbpkUmtry_pL594lIjoYXELziKDAnQ";
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

        boundActivity = new ArrayList<>();
        metaConversations = new ArrayList<>();

        handler = new Handler();

        runnable = new Runnable() {
            public void run() {


                    Log.i("MessageService", boundActivity.toString());

                    getConversationOverview();


                handler.postDelayed(runnable, 1000);
            }
        };
        handler.postDelayed(runnable, 1500);

    }

    @Override
    public IBinder onBind(Intent intent) {

        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
        // If we get killed, after returning from here, restart
        return START_STICKY;
    }



    public void getConversationOverview()  {
        if(boundActivity.size()!= 0) {
        //    Log.i("token",token);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://130.149.172.169/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
            if (token != null) {
                Call<List<MetaConversation>> call = jsonPlaceHolderApi.getConversationOverview(token);

                call.enqueue(new Callback<List<MetaConversation>>() {
                    @Override
                    public void onResponse(Call<List<MetaConversation>> call, Response<List<MetaConversation>> response) {
                        if (!response.isSuccessful()) {

                      //      Log.i("MessageService, getConversationOverview()", "Unsuccessful: " + response);
                            return;
                        }

                     //   Log.i("MessageService, getConversationOverview()", "Successful" + response.body());


                        //if activityListView  is open
                        if (boundActivity.get(boundActivity.size() - 1).toString().contains("ConversationsListActivity")) {

                            ConversationsListActivity conversationsListActivity = (ConversationsListActivity) boundActivity.get(boundActivity.size() - 1);
                            conversationsListActivity.metaConversations.removeAll(conversationsListActivity.metaConversations);
                            conversationsListActivity.metaConversations.addAll(response.body());

                            for (MetaConversation mc : response.body()) {
                         //       Log.i("PLEASE", mc.get_id());
                            }
                            conversationsListActivity.mAdapter.notifyDataSetChanged();

                        }


                        if (boundActivity.get(boundActivity.size() - 1).toString().contains("ConversationActivity")) {
                            ConversationActivity conversationActivity = (ConversationActivity) boundActivity.get(boundActivity.size() - 1);
                            String currentConversationID = conversationActivity.conversationId;

                            for (MetaConversation mc : response.body()) {

                                if (currentConversationID.contains(mc.get_id())) {
                                    conversation = mc;
                                   // Log.i("BLABLOU",currentConversationID);


                                }

                                if(conversationActivity.mConversation.getMessages()==null){
                                    conversationActivity.mConversation.getMessages().addAll(conversation.getMessages()) ;
                                }
                                else {
                                    conversationActivity.mConversation.getMessages().removeAll(conversationActivity.mConversation.getMessages());
                                    conversationActivity.mMessageAdapter.notifyDataSetChanged();
                                    conversationActivity.mConversation.getMessages().addAll(conversation.getMessages());
                                }
                                conversationActivity.mMessageAdapter.notifyDataSetChanged();

                            }
                        }


//                                    updatedConversations.removeAll(metaConversations);
//
//                                    for (MetaConversation updatedConv : updatedConversations) {
//
//                                        for (MetaConversation oldConv : metaConversations) {
//                                            if (oldConv.getConversationId() == updatedConv.getConversationId()) {
//                                                int indexOldConv = metaConversations.indexOf(oldConv);
//                                                metaConversations.set(indexOldConv, updatedConv); //TO DO : MAKE SURE IT UPDATES THE LIST
//
//                                            }
//                                        }
//                                    }
//
//                                    for (MetaConversation conv : updatedConversations) {
//                                        if (conv.getConversationId() != null) {
//                                            if (conv.getConversationId().contentEquals(currentConversationID)) {
//
//                                                Conversation conversation = getConversation(currentConversationID, token);
//                                                conversationActivity.mConversation = conversation;
//                                                conversationActivity.mMessageAdapter.notifyDataSetChanged();
//                                            }
//                                        }
//                                    }
//                                }

                        //if none of the above, then notification

                    }






                    @Override
                    public void onFailure(Call<List<MetaConversation>> call, Throwable t) {
                        Log.i("MessageService, getConversationOverview()", t.getMessage());
                    }
                });
            }
            return;
        }
    }




    public MetaConversation getConversation(String token , String conversationId){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://130.149.172.169/conversations/conversationId="+conversationId+"/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<MetaConversation> call = jsonPlaceHolderApi.getConversation(token);

        call.enqueue(new Callback<MetaConversation>() {
            @Override
            public void onResponse(Call<MetaConversation> call, Response<MetaConversation> response) {
                if(!response.isSuccessful()){
              //      Log.i("MessageService, getConversation()","Unsuccessful: "+ response);

                    return ;
                }
             //   Log.i("MessageService, getConversation()",   "ConversationID: "+"\n"+ response.body().get_id()+"\n"+"Conversation members: "+response.body().getMembers().toString()+"\n"+ "Conversation messages: "+response.body().getMessages().toString());

                conversation = new MetaConversation(response.body().get_id(),response.body().getName(),response.body().getMembers(),response.body().getMessages());

            }
            @Override
            public void onFailure(Call<MetaConversation> call, Throwable t) {
                Log.i("MessageService, getConversation()",t.getMessage());
            }
        });

        return conversation;
    }





//
//
//    public void getDummyConversationOverview() throws InterruptedException {
//
//        if (boundActivity.size() > 0) {
//            Log.i("Feriel","is getting smwhere");
//
//
//
//                 //new conversation started
//
//
//
//              //  if activityListView  is open
//                if (boundActivity.get(boundActivity.size() - 1).toString().contains("ConversationsListActivity")) {
//                    Log.i("MessageService", "in ConvListActivity");
//
//                    ConversationsListActivity conversationsListActivity = (ConversationsListActivity) boundActivity.get(boundActivity.size() - 1);
//                    conversationsListActivity.metaConversations.removeAll(conversationsListActivity.metaConversations);
//                    conversationsListActivity.metaConversations.addAll(get_dummy_conversation_list());
//                    conversationsListActivity.mAdapter.notifyDataSetChanged();
//                }
//
//
//                if (boundActivity.get(boundActivity.size() - 1).toString().contains("ConversationActivity")) {
//                    ConversationActivity conversationActivity = (ConversationActivity) boundActivity.get(boundActivity.size() - 1);
//
//
//                    String currentConversationID = conversationActivity.mConversation.getConversationId();
//
//                    List<MetaConversation> updatedConversations = metaConversations;
//                    updatedConversations.removeAll(metaConversations);
//
//                    for (MetaConversation updatedConv : updatedConversations) {
//
//                        for (MetaConversation oldConv : metaConversations) {
//                            if (oldConv.getConversationId() == updatedConv.getConversationId()) {
//                                int indexOldConv = metaConversations.indexOf(oldConv);
//                                metaConversations.set(indexOldConv, updatedConv); //TO DO : MAKE SURE IT UPDATES THE LIST
//
//                            }
//                        }
//                    }
//
//                    for (MetaConversation conv : updatedConversations) {
//                        if (conv.getConversationId().contentEquals(currentConversationID)) {
//
//                            Conversation conversation = getDummyConversation();
//                            conversationActivity.mConversation = conversation;
//                            conversationActivity.mMessageAdapter.notifyDataSetChanged();
//                        }
//                    }
//                }
//
//                //if none of the above, then notification
//
//
//        }
//    }
//
//
//


    public void sendMessage(String conversationId, String messageText) {



        String json = null;
        try {
            json = new JSONObject()
                    .put("messageText", messageText)
                    .toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url("http://130.149.172.169/conversations/"+conversationId+"/messages")
//                .url("https://httpdump.io/kjkc0")
                .addHeader("Authorization", token)
                .post(body)
                .build();
        try (okhttp3.Response response = client.newCall(request).execute()) {
            Log.i("myTest", "" + response.code());
        } catch (IOException e) {
            e.printStackTrace();
        }


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


}
