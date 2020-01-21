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

    // Open and maintain websocket, for help see https://medium.com/@ssaurel/learn-to-use-websockets-on-android-with-okhttp-ba5f00aea988
    // Currently using echo-test-server wss://echo.websocket.org
    public final class WebSocketListenerHelper extends WebSocketListener {
        private static final int NORMAL_CLOSURE_STATUS = 1000;

        @Override
        public void onOpen(WebSocket webSocket, okhttp3.Response response) {
            webSocket.send("Hello!");
            Log.i(TAG, "SENT: " + "Hello!");
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            Log.i(TAG, "RECEIVED: " + text);
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            Log.i(TAG, "RECEIVED BYTES: " + bytes.hex());
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            Log.i(TAG, "CLOSING: " + code + " / " + reason);
            webSocket.close(NORMAL_CLOSURE_STATUS, null);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, okhttp3.Response response) {
            Log.i(TAG, "ERROR: " + t.getMessage());
        }
    }

    public void startWebSocket(String url) {
        Request request = new Request.Builder().url(url).build();
        WebSocketListenerHelper listener = new WebSocketListenerHelper();
        client = new OkHttpClient();
        ws = client.newWebSocket(request, listener);
        //client.dispatcher().executorService().shutdown();
    }

    public void sendRequest(String request) {
        if (ws != null) {
            ws.send(request);
            Log.i(TAG, "SENT: " + request);
        } else {
            Log.i(TAG, "ws == null");
        }
    }
}
