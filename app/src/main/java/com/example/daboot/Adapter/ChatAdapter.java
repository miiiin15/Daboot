package com.example.daboot.Adapter;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.daboot.Message.ChatActivity;
import com.example.daboot.Message.ChatData;
import com.example.daboot.R;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    private List<ChatData> mDataset;
    private String myNick;
    //private final FirebaseUser user;
    //private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:MM");

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView TextView_nick;
        public TextView TextView_msg;
        public TextView TextView_time;
        public LinearLayout row_chat_main;
        public View rootView;

        public MyViewHolder(View v){
            super(v);
            TextView_nick = v.findViewById(R.id.TextView_nick);
            TextView_msg = v.findViewById(R.id.TextView_msg);
            TextView_time = v.findViewById(R.id.TextView_time);
            row_chat_main =  v.findViewById(R.id.row_chat_main);
            rootView = v;
        }
    }

    public ChatAdapter(List<ChatData> myDataset, ChatActivity context, String nick){
        //현재 여기에 null값이 들어온다...
        this.mDataset = myDataset;
        this.myNick = nick;
    }

    @NonNull
    @NotNull
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_chat, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position){
        ChatData chat = mDataset.get(position);
        holder.TextView_nick.setText(chat.getNick());
        holder.TextView_msg.setText(chat.getMsg());
        holder.TextView_time.setText(chat.getTime());//DTO

        //내가 보낸 메세지
        if(chat.getNick() != null && chat.getNick().equals(this.myNick)){
            //holder.TextView_nick.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            holder.TextView_msg.setBackgroundResource(R.drawable.right_bubble);
            //holder.TextView_msg.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            holder.TextView_nick.setVisibility(View.INVISIBLE);
            holder.row_chat_main.setGravity(Gravity.RIGHT);
        }
        //상대방이 보낸 메세지
        else {
            //holder.TextView_nick.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            holder.TextView_msg.setBackgroundResource(R.drawable.left_bubble);
            //holder.TextView_msg.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            holder.row_chat_main.setGravity(Gravity.LEFT);
        }
        /*long unixTime = System.currentTimeMillis();
        Date date = new Date(unixTime);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        String time = simpleDateFormat.format(date);*/
    }

    @Override
    public int getItemCount(){
        return mDataset == null ? 0 : mDataset.size();
    }

    public ChatData getChat(int position){
        return mDataset != null ? mDataset.get(position) : null;
    }

    public void addChat(ChatData chat){
        mDataset.add(chat);
        notifyItemInserted(mDataset.size()-1);
    }
}
