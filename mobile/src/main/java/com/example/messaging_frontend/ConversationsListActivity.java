package com.example.messaging_frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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


    /* start of recycler view crap */
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    /* end of recycler view crap */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();
        /* value should contain relevant information that we received from main */
        String value = intent.getStringExtra("key");
        Toast.makeText(this, "started conv. list act. w/ value: " + value.toString(), Toast.LENGTH_LONG).show();

        // Set up layout
        setContentView(R.layout.activity_conversations_list);
        // TODO: connect those two somehow: R.id.toolbar and R.menu.conversation_list_bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.conversation_list_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chats");

        // TODO: Initialize conversationList
        // query all meta-conversations

        ArrayList<MetaConversation> myConversations = get_conversation_list();
//        mAdapter.notifyDataSetChanged();
        // TODO: create a conversation for each element in the list
        /* start of recycler view crap */
        recyclerView = (RecyclerView) findViewById(R.id.conversation_list_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new ConversationListAdapter(myConversations);

        // create seperators between items
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));



        recyclerView.setAdapter(mAdapter);

        /* end of recycler view crap */


    }

    @Override
    /**
     * This method is there for the app bar but I'd like to integrate it into the real thing
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.conversation_list_bar, menu);
//        return super.onCreateOptionsMenu(menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_add_conversation:
                Toast.makeText(this, "Add conversation", Toast.LENGTH_SHORT).show();

            default:
                return super.onOptionsItemSelected(item);
        }
    }






    /**
     * creates a ConversationActivity for a specified metaConversation
     * @param myMetaConversation
     */
    private void launchConversationActivity(MetaConversation myMetaConversation) {
        // https://stackoverflow.com/questions/4186021/how-to-start-new-activity-on-button-click
        Intent myIntent = new Intent(ConversationsListActivity.this, ConversationActivity.class);
        myIntent.putExtra("key", myMetaConversation.toString()); //Optional parameters - This can be used to pass parameters to the new activity.
        ConversationsListActivity.this.startActivity(myIntent);
    }



    /**
     * returns a list of all messages in a conversation
     */
    private ArrayList<MetaConversation> get_conversation_list() {
        //TODO

//        return new ArrayList<MetaConversation>();

        return get_dummy_conversation_list();
    }

    /**
     * returns a list of all messages in a conversation
     */
    private ArrayList<MetaConversation> get_dummy_conversation_list() {
        //TODO
        ArrayList<MetaConversation> myConvList = new ArrayList<>();

        MetaConversation myConv = new_conv("Thomas Shelby", 665353, "By order of the peaky blinders", 1579127933);
        myConvList.add(myConv);

        myConv = new_conv("Arthur Shelby", 665354, "Linda!", 1579127920);
        myConvList.add(myConv);


        myConv = new_conv("John Shelby", 665355, "", 1579126033);
        myConvList.add(myConv);


        myConv = new_conv("Muh boy2", 665357, "I didn't do it.", 1579127800);
        myConvList.add(myConv);

        myConv = new_conv("Muh boy3", 665358, "I didn't do it.", 1579127223);
        myConvList.add(myConv);

        myConv = new_conv("Muh boy4", 665359, "I didn't do it.", 1579127132);
        myConvList.add(myConv);

        myConv = new_conv("Muh boy5", 665360, "I didn't do it.", 1579124252);
        myConvList.add(myConv);

        myConv = new_conv("Muh boy6", 665361, "I didn't do it.", 1579128763);
        myConvList.add(myConv);

        return myConvList;
    }


    public MetaConversation new_conv(String name, int id, String body, long timeStamp){
        Contact myContact = new Contact(name, id);
        Message myMessage = new Message(myContact, body, timeStamp);
        MetaConversation myConv = MetaConversation.Builder.newInstance()
                .setMyContact(myContact)
                .setLatestMessage(myMessage)
                .build();

        return myConv;
    }


    /**
     * adds a conversation conversation --  UNNECESSARY DUE TO ADAPTER
     */
    private void add_conversation(MetaConversation myConversation){
        //TODO
        // create and instantiate an item
//        ConstraintLayout myLayout = new ConstraintLayout(R.id.conversationListItem);
//        TextView conversation_name = (TextView) myLayout.getViewById(R.id.textViewConversationName);
//        TextView conversation_name = new TextView();
//        conversation_name.setText(myConversation.getContact().getName());


        // TODO: add said instance to the RecyclerView: conversation_list_view
//        RecyclerView conversation_list = (RecyclerView) findViewById(R.id.conversation_list_view);


//        conversation_list.addView(conversation_name);

    }

    public void add_conversation_dummy(){
        Contact myContact = new Contact("Thomas Shelby", 665151);
        MetaConversation myConvo = MetaConversation.Builder.newInstance()
                .setMyContact(myContact)
                .build();

        add_conversation(myConvo);
    }


}