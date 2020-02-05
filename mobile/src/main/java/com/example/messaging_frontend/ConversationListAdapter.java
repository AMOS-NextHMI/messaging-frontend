package com.example.messaging_frontend;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messaging_frontend.models.MetaConversation;

import java.util.ArrayList;
import java.util.List;

public class ConversationListAdapter extends RecyclerView.Adapter<ConversationListAdapter.ConversationListViewHolder> {
    List<MetaConversation> mDataset;
    String conversationId;
    String userId;

    // TODO: Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    public ConversationListAdapter(List<MetaConversation> myConversationList, String displayName) {

        this.mDataset = myConversationList;
        this.userId = userId;

    }


    public class ConversationListViewHolder extends RecyclerView.ViewHolder { //TODO:
        // each data item is just a string in this case
        public TextView conversation_name, last_message;



        public ConversationListViewHolder(View v) {
            super(v);

            conversation_name = v.findViewById(R.id.textViewConversationName);





            if(conversation_name!=null) {
                for (MetaConversation mc : mDataset){
                    if(mc.getName() == conversation_name.getText()){
                        conversationId = mc.get_id();


                    }
                }
                conversation_name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Context context = view.getContext();
                        Intent  intent =  new Intent(context, ConversationActivity.class);
                        intent.putExtra("ConversationId",conversationId);
                        intent.putExtra("UserId",userId);

                        ((Context) context).startActivity(intent);

                    }
                });
            }



            last_message = v.findViewById(R.id.textViewLastMessage);
        }
    }




    // Create new views (invoked by the layout manager)
    @Override
    public ConversationListAdapter.ConversationListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_conversationslistactivity_listitem, parent, false);

        return new ConversationListViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ConversationListViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        // TODO: introduce the missing compleixity
        MetaConversation metaConversation = this.mDataset.get(position);
        Log.i("ConvListAdapter HEEE",this.mDataset.toString());
        if(metaConversation.getMessages().size()!=0)
            holder.last_message.setText(metaConversation.getMessages().get(0).getMessageText()); //TODO: prone to bug if latest message doesn't exist.
        else holder.last_message.setText("");
        holder.conversation_name.setText(metaConversation.getName());


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (mDataset != null)
            return mDataset.size();
        else  return 0;
    }


}
