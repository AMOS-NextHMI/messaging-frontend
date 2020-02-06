package com.example.messaging_frontend;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.messaging_frontend.data.ReturnPair;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NewConversationFragment extends DialogFragment {
    private EditText editUsernames, editChatname;
    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        final String token = getArguments().getString("token");


        builder.setTitle("Start new Chat");
        final View dialogView = inflater.inflate(R.layout.new_conversation_dialog, null);
        builder.setView(dialogView);

        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                editChatname = (EditText) dialogView.findViewById(R.id.chatname);
                editUsernames = (EditText) dialogView.findViewById(R.id.usernames);
                sendNewChatRequest(editChatname.getText().toString(), editUsernames.getText().toString(), token);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                NewConversationFragment.this.getDialog().cancel();
            }
        });
        return builder.create();
    }

    public void sendNewChatRequest(String chatname, String receiver, String token) {

        Log.i("myTest", chatname + "," + receiver + "," + token);

        final String chatname_HTTP = chatname;
        final String receiver_HTTP = receiver;
        final String token_HTTP = token;


        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    postNewChat(chatname_HTTP, receiver_HTTP, token_HTTP);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public void postNewChat(String chatname, String receiver, String token) throws IOException, JSONException {


        if (token.charAt(0) == '\"' && token.charAt(token.length()-1) == '\"') {
            token = token.substring(1, token.length() - 1);
        }


        String json = new JSONObject()
                .put("name", chatname)
                .put("members", receiver)
                .toString();


        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url("http://130.149.172.169/conversations")
//                .url("https://httpdump.io/kjkc0")
                .addHeader("Authorization", token)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            Log.i("myTest", "" + response.code());
        }
    }
}