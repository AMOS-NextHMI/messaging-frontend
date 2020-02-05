package com.example.messaging_frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
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

import com.example.messaging_frontend.models.Contact;
import com.example.messaging_frontend.models.Message;
import com.example.messaging_frontend.models.MetaConversation;

import java.util.ArrayList;
import java.util.List;


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
     RecyclerView recyclerView;
    ConversationListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    MessageService messageService;
    Activity myActivity ;
    List<MetaConversation> metaConversations;
    List<MetaConversation> dummyMetaConversations;
    String token;
    public Handler handler = null;
    public static Runnable runnable = null;
    /* end of recycler view crap */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("ConvListActivity","THE CONTEXT IS:"+String.valueOf(getApplicationContext()));
        myActivity = this;
        metaConversations=new ArrayList<>();
       // dummyMetaConversations = get_dummy_conversation_list();
        Intent intent = getIntent();
        /* value should contain relevant information that we received from main */
        token = intent.getStringExtra("token");
        Log.i("token",token);
        bindService(new Intent(this, MessageService.class), connection, 0);
        String displayName = intent.getStringExtra("display name");
        Toast.makeText(this, "started conv. list act. w/ token: " + token.toString(), Toast.LENGTH_LONG).show();

        // Set up layout
        setContentView(R.layout.activity_conversations_list);
        // TODO: connect those two somehow: R.id.toolbar and R.menu.conversation_list_bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.conversation_list_toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Chats");
        getSupportActionBar().setTitle(displayName);


//        mAdapter.notifyDataSetChanged();
        // TODO: create a conversation for each element in the list
        /* start of recycler view crap */
        recyclerView = (RecyclerView) findViewById(R.id.conversation_list_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
       // recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        Log.i("ConvListAdapter",metaConversations.toString());




        mAdapter = new ConversationListAdapter(metaConversations);




        // create seperators between items
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));



        recyclerView.setAdapter(mAdapter);
        Log.i("ConvListActivity", "I've set the adapter, muh boy.");
        /* end of recycler view crap */



    }




    // Defines callbacks for service binding, passed to bindService()
    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MessageService.LocalBinder binder = (MessageService.LocalBinder) service;
            MessageService mService = binder.getService();
            messageService = mService;
            messageService.token = token;

            mService.addBoundActivity(myActivity);

        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {

        }
    };
    @Override
    protected void onDestroy() {
        // Unregister since the activity is about to be closed.
        messageService.removeBoundActivity(myActivity);
        unbindService(connection);
        super.onDestroy();
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
        myIntent.putExtra("conversationId", myMetaConversation.getConversationId());
        myIntent.putExtra("token",token);
        ConversationsListActivity.this.startActivity(myIntent);
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
        Contact myContact = new Contact("Thomas Shelby", "665151");

        MetaConversation myConvo = new MetaConversation("Conv ID example",new Message("senderUserId","convMessage",0));

        add_conversation(myConvo);
    }


}