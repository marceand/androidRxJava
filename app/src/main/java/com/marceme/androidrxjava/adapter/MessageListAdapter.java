package com.marceme.androidrxjava.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.marceme.androidrxjava.R;
import com.marceme.androidrxjava.model.Message;

import java.util.Collections;
import java.util.List;

/**
 * Created by Marcel on 7/9/2016.
 */
public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageListHolder>{

    List<Message> messageList = Collections.emptyList();


    @Override
    public MessageListHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View messageRowView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.message_row, viewGroup, false);
        return new MessageListHolder(messageRowView);
    }

    @Override
    public void onBindViewHolder(MessageListHolder holder, int position) {

        Message message = messageList.get(position);

        holder.getMessageView().setText("hi there");
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }


    public class MessageListHolder extends RecyclerView.ViewHolder {;

        private TextView messageView;

        public MessageListHolder(View messageRowView) {
            super(messageRowView);

            messageView = (TextView)messageRowView.findViewById(R.id.messageText);
        }

        public TextView getMessageView(){
            return messageView;
        }
    }
}
