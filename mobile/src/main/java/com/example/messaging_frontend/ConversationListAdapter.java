package com.example.messaging_frontend;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.messaging_frontend.models.MetaConversation;

import java.util.List;

public class ConversationListAdapter extends RecyclerView.Adapter<ConversationListAdapter.ConversationListViewHolder> {
    private List<MetaConversation> mDataset;

    // TODO: Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    public ConversationListAdapter(List<MetaConversation> myConversationList) {

        this.mDataset = myConversationList;
    }


    public class ConversationListViewHolder extends RecyclerView.ViewHolder { //TODO:
        // each data item is just a string in this case
        public TextView conversation_name, last_message;
        public ConversationListViewHolder(View v) {
            super(v);

            conversation_name = v.findViewById(R.id.textViewConversationName);
            if(conversation_name!=null) {
                conversation_name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Context context = view.getContext();
                        Intent  intent =  new Intent(context, ConversationActivity.class);
                        intent.putExtra("ConversationID",1);
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
        //Log.i("ConvListAdapter",this.mDataset.toString());
//       ConversationListViewHolder vh = new ConversationListViewHolder(v);
        return new ConversationListViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ConversationListViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        // TODO: introduce the missing compleixity
        MetaConversation metaConversation = this.mDataset.get(position);
        Log.i("ConvListAdapter",this.mDataset.toString());
        holder.last_message.setText(metaConversation.getLastMessage().getMessageText()); //TODO: prone to bug if latest message doesn't exist.
        holder.conversation_name.setText(metaConversation.getConversationId());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (mDataset != null)
            return mDataset.size();
        else  return 0;
    }


}
