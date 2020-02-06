package com.example.messaging_frontend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.messaging_frontend.ui.login.LoginActivity;
import com.example.messaging_frontend.ui.login.RegisterActivity;

public class MainActivity extends AppCompatActivity {

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

        // login
        init_login();
    }

    public void init_login(){
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        MainActivity.this.startActivityForResult(loginIntent, REQUEST_LOGIN);
    }

    public void init_conv_list(){
        // launch conversation list with token
        Intent convListIntent = new Intent(MainActivity.this, ConversationsListActivity.class);
        convListIntent.putExtra("token", token);
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
                // TODO: remove Anfuehrungszeichen at start and end of token
                token = data.getStringExtra("token");

                // The server adds quotation marks to the token and we remove them here if they still exist.
                // We do this, since it's too late to ask for the server to make some changes.
                if (token.charAt(0) == '\"' && token.charAt(token.length()-1) == '\"') {
                    token = token.substring(1, token.length() - 1);
                }



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
