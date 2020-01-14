package com.example.messaging_frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.function.Consumer;


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
        // TODO: Initialize conversationList
        // query all meta-conversations
        ArrayList<MetaConversation> myConversations = get_conversation_list();

        // TODO: create a conversation for each element in the list
        /* Attempt #1
        try {
            myConversations.forEach(new Consumer<MetaConversation>() {
                @Override
                public void accept(MetaConversation converation) {
                    ConversationsListActivity.add_conversation(converation);
                }
            });
        } catch (Exception e){
            Log.e("Conversations", "Error adding meta lists: " + e.toString());
        }
         */




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

    /**
     * adds a conversation conversation
     */
    private void add_conversation(MetaConversation myConversation){
        //TODO
        ListView conversation_list = (ListView) findViewById(R.id.conversation_list_view);
        

    }

}