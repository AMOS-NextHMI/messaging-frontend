package com.example.messaging_frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);


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