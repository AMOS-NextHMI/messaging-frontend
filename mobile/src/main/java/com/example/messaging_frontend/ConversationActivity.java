package com.example.messaging_frontend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private ConversationAdapter mMessageAdapter;
    List<Message> messageList;
    Button sendButton;
    Button returnButton;
    EditText messageSent;
    Contact mSender;
    Contact mReceiver;
    Date mDate;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

      //UNCOMMENT messageList =  mockMessageList();
        messageList=new ArrayList<>();



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

        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/posts/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Message>> call = jsonPlaceHolderApi.getMessages();
        //call.execute() runs the call on the main  thread
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if(!response.isSuccessful()){
                  Log.i("ConversationActivityMESSAGE", "IMHERE");
                  return;
                }
                List<Message> messages  = response.body();
                //UNCOMMENT
//                for (Message m: messages) {
//                    String content = "";
//                    content += "ID: " + m.getSender().getId()+"\n";
//                    content += "Name: " + m.getSender().getName()+"\n";
//                    content += "Text:" + m.getBody()+"\n\n";
//                    Log.i("ConversationActivity",content);
//
//                }
                for (Message m: messages) {
                    String content = "";
                    content += "UserID: " + m.getUserID()+"\n";

                    content += "Title:" + m.getTitle()+"\n";
                    content += "Body:" + m.getBody()+"\n\n";
                    Log.i("ConversationActivityMESSAGE",content);
                    messageList.add(new Message(m.getUserID(),m.getTitle(),m.getBody()));

                }




            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Log.i("ConversationActivityMESSAGE",t.getMessage());
            }
        });




    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
//UNCOMMENT
//    private List<Message> mockMessageList() {
//         Bundle convID=  getIntent().getExtras();
//         if(convID.containsKey("ConversationID")) {
//            //this will be dynamic once we have the actual messages
//             if (convID.get("ConversationID").equals(1)) {
//                 List<Message> messageList = new ArrayList<>();
//
//                 Date date1 = new Date();
//                 Contact sender = new Contact("Mahmoud", 1);
//                 Message message1 = new Message(sender, "Hey Feriel, how are you?", date1.getTime());
//
//                 Date date2 = new Date();
//                 Contact receiver = new Contact("Feriel", 2);
//                 Message message2 = new Message(receiver, "I'm tired but I will survive", date2.getTime());
//
//                 messageList.add(message1);
//                 messageList.add(message2);
//
//                 return messageList;
//             }
//         }
//
//         //I created this to test directly
//         else{
//             List<Message> messageList = new ArrayList<>();
//
//             Date date1 = new Date();
//             Contact sender = new Contact("Mahmoud", 1);
//             Message message1 = new Message(sender, "Hey Feriel, how are you?", date1.getTime());
//
//             Date date2 = new Date();
//             Contact receiver = new Contact("Feriel", 2);
//             Message message2 = new Message(receiver, "I'm tired but I will survive", date2.getTime());
//
//             messageList.add(message1);
//             messageList.add(message2);
//
//             return messageList;
//         }
//
//
//         return null;
//    }

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
//UNCOMMENT
//        messageSent = (EditText) findViewById(R.id.text_chatbox);
//        Message message = new Message(mSender, messageSent.getText().toString(), mDate.getTime());
//        messageSent.getText().clear();
//        messageList.add(message);
//        mMessageAdapter.notifyItemInserted(messageList.indexOf(message));

        messageSent = (EditText) findViewById(R.id.text_chatbox);

        Message message = new Message(1,"test", messageSent.getText().toString());
        messageSent.getText().clear();
        messageList.add(message);
        mMessageAdapter.notifyItemInserted(messageList.indexOf(message));


    }

}