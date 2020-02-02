package com.example.messaging_frontend;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class MessageService extends Service {
    // For help see:
    // https://developer.android.com/guide/components/services and
    // https://developer.android.com/guide/components/bound-services

    private static final String TAG = "MessageService";
    private final IBinder binder = new LocalBinder();
    private OkHttpClient client;
    private WebSocket ws;

    // The binder which we give to the client via onBind()
    public class LocalBinder extends Binder {
        MessageService getService() {
            // Return this instance of MessageService so clients can call public methods
            return MessageService.this;
        }
    }

    @Override
    public void onCreate() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

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

    public void testMethod() {
        Toast.makeText(this, "hello, this is the background service", Toast.LENGTH_SHORT).show();
    }
}
