package com.example.messaging_frontend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.messaging_frontend.data.model.UserMessage;

import java.security.Timestamp;
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
    List<UserMessage> messageList;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        messageList = mockMessageList();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        mMessageRecycler = (RecyclerView) findViewById(R.id.reyclerview_message_list);
        mMessageAdapter = new ConversationAdapter(this, messageList);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<UserMessage> mockMessageList() {


        Date date1 = new Date();
        UserMessage message1 = new UserMessage("1","Mahmoud","Hey Feriel, how are you?", date1.getTime());
        Date date2 = new Date();
        UserMessage message2 = new UserMessage("2","Feriel","I'm tired but I will survive", date2.getTime());
        List<UserMessage> messageList = new ArrayList<>();
        messageList.add(message1);
        messageList.add(message2);
        return messageList;
    }

    /**
     * returns a list of all messages in a conversation
     */
    private ArrayList<Message> get_conversation(/* an ID for the conversation */) {
        return new ArrayList<Message>();
    }


    /**
     * handles the UI aspect of sending a message and sends out a send_message query.
     * @param myMessage
     */
    private void send_message(Message myMessage){

    }

}