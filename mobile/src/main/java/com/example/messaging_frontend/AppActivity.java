package com.example.messaging_frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.messaging_frontend.ui.login.LoginActivity;

public class AppActivity extends AppCompatActivity {

    // Request codes
    public static final int REQUEST_BLANK = 0;
    public static final int REQUEST_LOGIN = 1;

    // login variables
    private boolean login_success = false;
    private static String token;
    private static String displayName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        // login
       // init_login(); //comment out

        // TODO: remove the things here.  They're only there to emulate a valid login
        //comment in
        //register
        displayName = "string";

        token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVlM2FmMzMxNTQwMmVlMDE2MWQzY2EwOSIsImlhdCI6MTU4MDkyMTY0OX0.FW4LaQoCyK3X4LI4IpEQC21e0PQcfmutWDCBXgzPgA9I3R9AqaR2vC_ytTR40AFUzHbPOjtgFiUvJBub6xM-Sg";
        init_conv_list();
    }


    public void init_login(){
        Intent loginIntent = new Intent(AppActivity.this, LoginActivity.class);
        AppActivity.this.startActivityForResult(loginIntent, REQUEST_LOGIN);
    }

    public void init_conv_list(){
        // launch conversation list with token
        Intent convListIntent = new Intent(AppActivity.this, ConversationsListActivity.class);
        convListIntent.putExtra("token", token);
        Log.i("AppActivity",token);
        convListIntent.putExtra("display name", displayName);
        convListIntent.putExtra("userId", displayName);
        finish();
        startActivity(convListIntent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_LOGIN) {
            if (resultCode == Activity.RESULT_OK) {
                token = data.getStringExtra("token");
                displayName = data.getStringExtra("display name");
//                login_success = data.getBooleanExtra("login_success", false);
                init_conv_list();
            }
            else if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                init_login();
            }
        }
    }

    
}
