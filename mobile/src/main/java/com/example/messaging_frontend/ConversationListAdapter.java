package com.example.messaging_frontend;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ConversationListAdapter extends RecyclerView.Adapter<ConversationListAdapter.ConversationListViewHolder> {
    private List<ConversationOverview> mDataset;

    // TODO: Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ConversationListViewHolder extends RecyclerView.ViewHolder { //TODO:
        // each data item is just a string in this case
        public TextView conversation_name, last_message;
        public ConversationListViewHolder(View v) {
            super(v);
            conversation_name = v.findViewById(R.id.textViewConversationName);
            last_message = v.findViewById(R.id.textViewLastMessage);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ConversationListAdapter(List<ConversationOverview> myConversationList) {
        this.mDataset = myConversationList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ConversationListAdapter.ConversationListViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_conversationslistactivity_listitem, parent, false);
//        v.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i("Button list", "Click clack. Put money in bag" + );
//
//            }
//        });
//        ConversationListViewHolder vh = new ConversationListViewHolder(v);
        return new ConversationListViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ConversationListViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        // TODO: introduce the missing compleixity
        final ConversationOverview mconversationOverview = this.mDataset.get(position);

        holder.last_message.setText(mconversationOverview.getLatestMessage().getBody()); //TODO: prone to bug if latest message doesn't exist.
//        holder.conversation_name.setText(mconversationOverview.getContact().getName());
        holder.conversation_name.setText(String.valueOf(mconversationOverview.getConvID()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Button list", "Click clack. Put money in bag" + String.valueOf(mconversationOverview.getConvID()));
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
