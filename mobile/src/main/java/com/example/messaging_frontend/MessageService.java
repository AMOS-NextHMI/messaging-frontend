package com.example.messaging_frontend;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import com.example.messaging_frontend.models.Contact;
import com.example.messaging_frontend.models.Conversation;
import com.example.messaging_frontend.models.Message;
import com.example.messaging_frontend.models.MetaConversation;
import java.util.ArrayList;
import java.util.List;
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


    private static String API_BASE_URL = "https://130.149.172.169/";
    private List<MetaConversation> metaConversations;
    public Handler handler = null;
    public static Runnable runnable = null;

    private List<Activity> boundActivity;
    Conversation conversation;
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


                handler.postDelayed(runnable, 10000);
            }
        };
        handler.postDelayed(runnable, 15000);

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

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://130.149.172.169/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
            if (token != null) {
                Call<List<MetaConversation>> call = jsonPlaceHolderApi.getConversationOverview(token, "application/json");

                call.enqueue(new Callback<List<MetaConversation>>() {
                    @Override
                    public void onResponse(Call<List<MetaConversation>> call, Response<List<MetaConversation>> response) {
                        if (!response.isSuccessful()) {

                            Log.i("MessageService, getConversationOverview()", "Unsuccessful: " + response);
                            return;
                        }

                        Log.i("MessageService, getConversationOverview()", "Successful" + response.body());
                     //   if (metaConversations.size() == 0) {
//                            if (boundActivity.get(boundActivity.size() - 1).toString().contains("ConversationsListActivity")) {
//
//                                metaConversations = response.body();
//                                ConversationsListActivity conversationsListActivity = (ConversationsListActivity) boundActivity.get(boundActivity.size() - 1);
//                                conversationsListActivity.metaConversations = response.body();
//
//
//                                conversationsListActivity.mAdapter.notifyDataSetChanged();
//
//                            }
//
//                        }


                        //   if (metaConversations.size() != response.body().size()) {
                          //      // a new conversation started
                          //      metaConversations = response.body();

                        //    } else if (!metaConversations.equals(response.body())) {

                                //if activityListView  is open
                                if (boundActivity.get(boundActivity.size() - 1).toString().contains("ConversationsListActivity")) {

                                    ConversationsListActivity conversationsListActivity = (ConversationsListActivity) boundActivity.get(boundActivity.size() - 1);
                                    conversationsListActivity.metaConversations.removeAll(conversationsListActivity.metaConversations);
                                    conversationsListActivity.metaConversations.addAll(response.body());
                                    conversationsListActivity.mAdapter.notifyDataSetChanged();

                                }


                                if (boundActivity.get(boundActivity.size() - 1).toString().contains("ConversationActivity")) {
                                    ConversationActivity conversationActivity = (ConversationActivity) boundActivity.get(boundActivity.size() - 1);
                                    String currentConversationID = conversationActivity.mConversation.getConversationId();

                                    List<MetaConversation> updatedConversations = response.body();
                                    updatedConversations.removeAll(metaConversations);

                                    for (MetaConversation updatedConv : updatedConversations) {

                                        for (MetaConversation oldConv : metaConversations) {
                                            if (oldConv.getConversationId() == updatedConv.getConversationId()) {
                                                int indexOldConv = metaConversations.indexOf(oldConv);
                                                metaConversations.set(indexOldConv, updatedConv); //TO DO : MAKE SURE IT UPDATES THE LIST

                                            }
                                        }
                                    }

                                    for (MetaConversation conv : updatedConversations) {
                                        if (conv.getConversationId().contentEquals(currentConversationID)) {

                                            Conversation conversation = getConversation(currentConversationID, token);
                                            conversationActivity.mConversation = conversation;
                                            conversationActivity.mMessageAdapter.notifyDataSetChanged();
                                        }
                                    }
                                }

                                //if none of the above, then notification

                      //      }



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




    public Conversation getConversation( String token , String conversationId){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://130.149.172.169/conversations/conversationId="+conversationId)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<Conversation> call = jsonPlaceHolderApi.getConversation(token);

        call.enqueue(new Callback<Conversation>() {
            @Override
            public void onResponse(Call<Conversation> call, Response<Conversation> response) {
                if(!response.isSuccessful()){
                    Log.i("MessageService, getConversation()","Unsuccessful: "+ response);

                    return ;
                }
                Log.i("MessageService, getConversation()",   "ConversationID: "+"\n"+ response.body().getConversationId()+"\n"+"Conversation members: "+response.body().getMember().toString()+"\n"+ "Conversation messages: "+response.body().getMessages().toString());

                conversation = new Conversation(response.body().getConversationId(),response.body().getName(),response.body().getMember(),response.body().getMessages());

            }
            @Override
            public void onFailure(Call<Conversation> call, Throwable t) {
                Log.i("MessageService, getConversation()",t.getMessage());
            }
        });

        return conversation;
    }
    /**
     * returns a list of all messages in a conversation
     */
    private ArrayList<MetaConversation> get_dummy_conversation_list() {
        ArrayList<MetaConversation> myConvList = new ArrayList<>();

        MetaConversation myConv = new_conv("Thomas Shelby", "1", "By order of the peaky blinders", 0);
        myConvList.add(myConv);

        myConv = new_conv("Arthur Shelby", "U665354", "Linda!", 0);
        myConvList.add(myConv);


        myConv = new_conv("John Shelby", "665355", "", 0);
        myConvList.add(myConv);


        myConv = new_conv("Muh boy2", "665357", "I didn't do it.", 0);
        myConvList.add(myConv);

        myConv = new_conv("Muh boy3", "665358", "I didn't do it.", 0);
        myConvList.add(myConv);

        myConv = new_conv("Muh boy4", "665359", "I didn't do it.", 0);
        myConvList.add(myConv);

        myConv = new_conv("Muh boy5", "665360", "I didn't do it.", 0);
        myConvList.add(myConv);

        myConv = new_conv("Muh boy6", "665361", "I didn't do it.", 0);
        myConvList.add(myConv);

        return myConvList;
    }

    public MetaConversation new_conv(String name, String id, String body, int timeStamp){

        Message myMessage = new Message(id, body, timeStamp);
        MetaConversation myConv = new MetaConversation(id, myMessage);

        return myConv;
    }


    private Conversation getDummyConversation() {
        ArrayList<Message> myMsgs = new ArrayList<>();
        ArrayList<Contact> members = new ArrayList<>();
        members.add( new Contact("Mahmoud", "1"));
        myMsgs.add(new Message("bla","bla",0));

        Conversation myConv = new Conversation("conv1", "",members,myMsgs) ;

        return myConv;
    }

    public void getDummyConversationOverview() throws InterruptedException {

        if (boundActivity.size() > 0) {
            Log.i("Feriel","is getting smwhere");



                 //new conversation started



              //  if activityListView  is open
                if (boundActivity.get(boundActivity.size() - 1).toString().contains("ConversationsListActivity")) {
                    Log.i("MessageService", "in ConvListActivity");

                    ConversationsListActivity conversationsListActivity = (ConversationsListActivity) boundActivity.get(boundActivity.size() - 1);
                    conversationsListActivity.metaConversations.removeAll(conversationsListActivity.metaConversations);
                    conversationsListActivity.metaConversations.addAll(get_dummy_conversation_list());
                    conversationsListActivity.mAdapter.notifyDataSetChanged();
                }


                if (boundActivity.get(boundActivity.size() - 1).toString().contains("ConversationActivity")) {
                    ConversationActivity conversationActivity = (ConversationActivity) boundActivity.get(boundActivity.size() - 1);


                    String currentConversationID = conversationActivity.mConversation.getConversationId();

                    List<MetaConversation> updatedConversations = metaConversations;
                    updatedConversations.removeAll(metaConversations);

                    for (MetaConversation updatedConv : updatedConversations) {

                        for (MetaConversation oldConv : metaConversations) {
                            if (oldConv.getConversationId() == updatedConv.getConversationId()) {
                                int indexOldConv = metaConversations.indexOf(oldConv);
                                metaConversations.set(indexOldConv, updatedConv); //TO DO : MAKE SURE IT UPDATES THE LIST

                            }
                        }
                    }

                    for (MetaConversation conv : updatedConversations) {
                        if (conv.getConversationId().contentEquals(currentConversationID)) {

                            Conversation conversation = getDummyConversation();
                            conversationActivity.mConversation = conversation;
                            conversationActivity.mMessageAdapter.notifyDataSetChanged();
                        }
                    }
                }

                //if none of the above, then notification


        }
    }





    public void sendMessage(String conversationId, String messageText) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://130.149.172.169/conversations/conversationId="+conversationId)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<Message> call = jsonPlaceHolderApi.sendMessage(token,messageText);
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
