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

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    // TODO: outsource notifications
    // TODO: introduce a login activity, after which a ConversationsListActivity is called.
    // https://developer.android.com/guide/components/bound-services

    final String CHANNEL_ID = "123"; // This is a name for test purposes
    MessageService mService;
    boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        createNotificationChannel();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Send notification on button press
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotification();

                if(mBound){
                    mService.testMethod();
                }

            }
        });


        /* Service Code */

        // Start Service
        Intent intent = new Intent(this, MessageService.class);
        startService(intent);

        // Bind Service
        // public abstract boolean bindService(Intent service, ServiceConnection conn, int flags);
        // ServiceConnection, which monitors the connection with the service.
        // The return value of bindService() indicates whether the requested service exists and whether the client is permitted access to it.
    
        bindService(intent, connection, Context.BIND_AUTO_CREATE);

        if(mBound){
            mService.testMethod();
        }


    }

    /** Defines callbacks for service binding, passed to bindService() */
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
}
