package com.example.messaging_frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;


/**
 * This activity displays the chat list screen with a list of all contacts.
 *
 * TODO: Implement the following
 * It includes the following:
 * - scrollable chats scrollView that contains a user list where a user template has a:
 *      - User Pro-pic
 *      - name
 *      - last sent message
 * - an add user button
 * - a search button to search for a user
 *
 */
public class ConversationsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversations_list);
        //TODO: Initialize conversationList
    }

    /**
     * creates a ConversationActivity for a specified metaConversation
     * @param myMetaConversation
     */
    private void open_conversation(MetaConversation myMetaConversation){
        //TODO
    }

    /**
     * returns a list of all messages in a conversation
     */
    private ArrayList<MetaConversation> get_conversation_list() {
        //TODO
        return new ArrayList<MetaConversation>();
    }

}