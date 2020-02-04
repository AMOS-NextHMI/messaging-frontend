package com.example.messaging_frontend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.messaging_frontend.ui.login.LoginActivity;
import com.example.messaging_frontend.ui.login.RegisterActivity;

public class MainActivity extends AppCompatActivity {
    // TODO: outsource notifications
    // TODO: introduce a login activity, after which a ConversationsListActivity is called.
    // https://developer.android.com/guide/components/bound-services

    final String CHANNEL_ID = "123"; // This is a name for test purposes
    MessageService mService;
    boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Buttons
        Button button = findViewById(R.id.notificationButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotification();
            }
        });
        final EditText editText = findViewById(R.id.editText);

        Button openSocketButton = findViewById(R.id.openSocketButton);
        openSocketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBound) {
                    mService.startWebSocket("wss://echo.websocket.org");
                }
            }
        });

        Button sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBound) {
                    mService.sendRequest(editText.getText().toString());
                }
            }
        });

        Button conversationListButton = findViewById(R.id.conversationListButton);
        conversationListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBound) {
                    launchConversationListActivity("Baby Yoda's 50.", "Martha Stewart");
                }
            }
        });
        Button conversationButton = findViewById(R.id.conversationButton);
        conversationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBound) {
                    launchConversationActivity("1");
                }
            }
        });

        Button loginButton = findViewById(R.id.login_Button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBound) {
                    launchLoginActivity("1");
                }
            }
        });

        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBound) {
                    launchRegisterActivity("1");
                }
            }
        });

        Button appButton = findViewById(R.id.App_Button);
        appButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBound) {
                    launchAppActivity("1");
                }
            }
        });

        createNotificationChannel();



        // Start and bind service (only bind would also be an option)
        Intent intent = new Intent(this, MessageService.class);
        startService(intent);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);


        if(mBound){
            mService.testMethod();
        }


    }



    // Defines callbacks for service binding, passed to bindService()
    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MessageService.LocalBinder binder = (MessageService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    // TODO: Outsource notifications to shared module
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //CharSequence name = getString(R.string.channel_name);
            //String description = getString(R.string.channel_description);

            CharSequence name = "some name";
            String description = "some descriprion";

            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    // TODO: Outsource notifications to shared module
    private void sendNotification() {

        //NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentText("Much longer text that cannot fit one line...")
                //.setStyle(new NotificationCompat.BigTextStyle().bigText("Much longer text that cannot fit one line..."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(123, builder.build());
    }

    /**
     * Launches a conversation list and passes it a TBD value
     */
    private void launchConversationListActivity(String token, String userName) {
        // https://stackoverflow.com/questions/4186021/how-to-start-new-activity-on-button-click
        Intent myIntent = new Intent(MainActivity.this, ConversationsListActivity.class);
        myIntent.putExtra("token", token);
        myIntent.putExtra("display name", userName);
        MainActivity.this.startActivity(myIntent);
    }

    void launchConversationActivity(String value) {
        // https://stackoverflow.com/questions/4186021/how-to-start-new-activity-on-button-click
        Intent myIntent = new Intent(MainActivity.this, ConversationActivity.class);
        myIntent.putExtra("conv_id", value); //Optional parameters - This can be used to pass parameters to the new activity.
        MainActivity.this.startActivity(myIntent);
    }

    void launchLoginActivity(String value) {
        // https://stackoverflow.com/questions/4186021/how-to-start-new-activity-on-button-click
        Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
        myIntent.putExtra("conv_id", value); //Optional parameters - This can be used to pass parameters to the new activity.
        MainActivity.this.startActivity(myIntent);
    }

    void launchRegisterActivity(String value) {
        // https://stackoverflow.com/questions/4186021/how-to-start-new-activity-on-button-click
        Intent myIntent = new Intent(MainActivity.this, RegisterActivity.class);
        myIntent.putExtra("conv_id", value); //Optional parameters - This can be used to pass parameters to the new activity.
        MainActivity.this.startActivity(myIntent);
    }

    void launchAppActivity(String value) {
        // https://stackoverflow.com/questions/4186021/how-to-start-new-activity-on-button-click
        Intent myIntent = new Intent(MainActivity.this, AppActivity.class);
        MainActivity.this.startActivity(myIntent);
    }

}
