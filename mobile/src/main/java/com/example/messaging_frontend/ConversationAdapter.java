package com.example.messaging_frontend;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.messaging_frontend.data.model.LoggedInUser;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import static android.widget.Toast.makeText;

public class ConversationAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private LoggedInUser loggedUser;
    private Context mContext;
    private List<ConvMessage> mMessageList;

    public ConversationAdapter(Context context, List<ConvMessage> messageList) {
        mContext = context;
        mMessageList = messageList;
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        ConvMessage message =  mMessageList.get(position);

        //if (message.getUserId().equals(loggedUser.getUserId()) ){
        if (message.getSender().getId() == "2" ){
            // If the current user is the sender of the message

            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message

            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    // Inflates the appropriate layout according to the ViewType.
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        Log.w("ConversationAdapter","viewHolder");

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_sent, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_received, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ConvMessage message = (ConvMessage) mMessageList.get(position);
        Log.w("ConversationAdapter","bindViewHolder");
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
        }
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
        }

        void bind(ConvMessage message) {
            messageText.setText(message.getBody());

            // Format the stored timestamp into a readable String using method.


            timeText.setText((new SimpleDateFormat("HH:mm")).format( message.getTimeStamp()));
        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;
        ImageView profileImage;

        ReceivedMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.message_time);
            nameText = (TextView) itemView.findViewById(R.id.text_message_name);
            profileImage = (ImageView) itemView.findViewById(R.id.image_message_profile);
        }

        void bind(ConvMessage message) {
            messageText.setText(message.getBody());

            // Format the stored timestamp into a readable String using method.
            timeText.setText((new SimpleDateFormat("HH:mm")).format( message.getTimeStamp()));

            nameText.setText(message.getSender().getName());

            // Insert the profile image from the URL into the ImageView.
//            if (message.getSender().getProfileUrl() != null) {
//                Utils.displayRoundImageFromUrl(mContext, message.getSender().getProfileUrl(), profileImage);
//            } else {
//
//            }
        }
    }
}