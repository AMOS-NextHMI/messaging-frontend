package com.example.messaging_frontend;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MessageService extends Service {
    // See https://developer.android.com/guide/components/services
    // TODO: Service vs IntentService

    @Override
    public void onCreate() {
        // Service normally runs in the process's main thread, use HandlerThread if not desired
        // Do something here...
    }

    @Override
    public IBinder onBind(Intent intent) {
        // No binding implemented yet, so return null for now
        return null;
    }

    // Example for onStartCommand
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
    }
}
