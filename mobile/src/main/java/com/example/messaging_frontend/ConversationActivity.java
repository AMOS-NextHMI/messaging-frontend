package com.example.messaging_frontend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



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
public class ConversationActivity extends AppCompatActivity {

    /**
     * TODO: upon creation this activity is provided with an intent that includes an instance of a meta-conversation
     * TODO: This activity uses the meta-conversation to then request the messages history and display it.
     */
    private RecyclerView mMessageRecycler;
    private ConversationAdapter mMessageAdapter;
    List<ConvMessage> messageList;
    Button sendButton;
    Button returnButton;
    EditText messageSent;
    Contact mSender;
    Contact mReceiver;
    Date mDate;

    private String conv_id = "1"; // TODO: ID is missing.  Should be initialized as empty string.

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

//        conv_id = new String(getIntent().getByteArrayExtra("conv_id")); // TODO: ID is missing.
        /* hard coded info starts -- They should be queried using the conv_id */
        messageList =  mockMessageList();
        mSender = new Contact("Feriel","2");
        mReceiver = new Contact("Mahmoud","3");
        mDate = new Date();
        /* hard coded info ends */


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


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(mReceiver.getName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * creates dummy messages for testing purposes.
     * @return
     */
    private List<ConvMessage> mockMessageList() {
         Bundle convID=  getIntent().getExtras();
         if(convID.containsKey("ConversationID")) {
            //this will be dynamic once we have the actual messages
             if (convID.get("ConversationID").equals("1")) { /* hard coded ID */

                 /* hard coded info begins */
                 List<ConvMessage> messageList = new ArrayList<>();
                 Date date1 = new Date(); // TODO: CHANGE TIME FORMAT TO A BETTER ONE
                 Contact sender = new Contact("Mahmoud", "1");

                 ConvMessage message1 = ConvMessage.Builder.newInstance()
                         .setBody("Hey Feriel, how are you?")
                         .setConvID("112")
                         .setTime_stamp(date1)
                         .setSender(sender)
                         .build();


                 Date date2 = new Date(); // TODO: CHANGE TIME FORMAT TO A BETTER ONE
                 Contact receiver = new Contact("Feriel", "2");
                 ConvMessage message2 = ConvMessage.Builder.newInstance()
                         .setBody("I'm tired but I will survive")
                         .setConvID("112")
                         .setTime_stamp(date2)
                         .setSender(receiver)
                         .build();

                 messageList.add(message1);
                 messageList.add(message2);
                 /* hard coded info ends */
                 return messageList;
             }
         }
         //I created this to test directly
         else{
             List<ConvMessage> messageList = new ArrayList<>();

             Date date1 = new Date();
             Contact sender = new Contact("Mahmoud", "1");
             ConvMessage message1 = ConvMessage.Builder.newInstance()
                     .setBody("Hey Feriel, how are you?")
                     .setConvID("112")
                     .setSender(sender)
                     .setTime_stamp(date1)
                     .build();

             Date date2 = new Date();
             Contact receiver = new Contact("Feriel", "2");
             ConvMessage message2 = ConvMessage.Builder.newInstance()
                     .setBody("I'm tired but I will survive")
                     .setConvID("112")
                     .setSender(receiver)
                     .setTime_stamp(date2)
                     .build();

             messageList.add(message1);
             messageList.add(message2);

             return messageList;
         }
         return null;
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
        ConvMessage UImessage = ConvMessage.Builder.newInstance()
                .setBody(messageSent.getText().toString())
                .setConvID(conv_id)
                .setSender(mSender)
                .setTime_stamp(new Date())
                .build();
        messageSent.getText().clear();
        messageList.add(UImessage);
        mMessageAdapter.notifyItemInserted(messageList.indexOf(UImessage));

        // TODO: send out a query
        Message queryMessage = Message.Builder.newInstance()
                .setBody(UImessage.getBody())
                .setConvID(UImessage.getConvID())
                .setTime_stamp(UImessage.getTimeStamp())
                .build();
    }

}