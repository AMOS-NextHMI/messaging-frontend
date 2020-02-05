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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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


        builder.setTitle("Start new Chat");
        final View dialogView = inflater.inflate(R.layout.new_conversation_dialog, null);
        builder.setView(dialogView);

        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                editChatname  = (EditText) dialogView.findViewById(R.id.chatname);
                editUsernames  = (EditText) dialogView.findViewById(R.id.chatname);
                sendNewChatRequest(editChatname.getText().toString(), editUsernames.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                NewConversationFragment.this.getDialog().cancel();
            }
        });
        return builder.create();
    }

    public void sendNewChatRequest(String chatname, String receiver){
        try {
            String result = postNewChat(chatname, receiver);
            Log.i("myTest", result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String postNewChat(String chatname, String receiver) throws IOException, JSONException {

        String json = new JSONObject()
                .put("name", chatname)
                .put("members", receiver)
                .toString();


        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url("http://130.149.172.169/conversations/")
                .header("Authorization", "123")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();

        }
    }
}