package com.example.daboot.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

//import com.example.daboot.Message.ChatList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.daboot.Message.ChatListData;
import com.example.daboot.R;
import com.example.daboot.fragments.Message;
import com.google.firebase.database.core.Context;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder> {

    private ArrayList<ChatListData> arrayList;
    private Message context;


    public ChatListAdapter(ArrayList<ChatListData> arrayList, Message context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ChatListViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_chat_list,parent, false);
        ChatListViewHolder holder = new ChatListViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ChatListViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getProfile())
                .into(holder.profile);
        holder.nick.setText(arrayList.get(position).getNick());
        holder.chatContent.setText(arrayList.get(position).getChatContent());
        holder.time.setText(arrayList.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class ChatListViewHolder extends RecyclerView.ViewHolder{

        ImageView profile;
        TextView nick;
        TextView chatContent;
        TextView time;

        public ChatListViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            this.profile = itemView.findViewById(R.id.profile);
            this.nick = itemView.findViewById(R.id.nick);
            this.chatContent = itemView.findViewById(R.id.chatContent);
            this.time = itemView.findViewById(R.id.time);
        }
    }
}
/*extends BaseAdapter {

    Message mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<ChatListData> chatlist;

    public ChatListAdapter(Message context, ArrayList<ChatListData> data) {
        mContext = context;
        chatlist = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return chatlist.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ChatListData getItem(int position) {
        return chatlist.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.activity_chat_list, null);

        ImageView profile = (ImageView)view.findViewById(R.id.profile);
        TextView nick = (TextView)view.findViewById(R.id.nick);
        TextView chatContent = (TextView)view.findViewById(R.id.chatContent);
        TextView time = (TextView)view.findViewById(R.id.time);

        profile.setImageResource(chatlist.get(position).getProfile());
        nick.setText(chatlist.get(position).getNick());
        chatContent.setText(chatlist.get(position).getChatContent());
        time.setText(chatlist.get(position).getTime());

        return view;
    }
}*/
