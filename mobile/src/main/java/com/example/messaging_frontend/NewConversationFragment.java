package com.example.messaging_frontend;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class NewConversationFragment extends DialogFragment {
    private EditText editUsernames, editChatname;


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

    public void sendNewChatRequest(String chatnameContent, String usernameContent){

    }
}