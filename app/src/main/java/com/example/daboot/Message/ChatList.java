package com.example.daboot.Message;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daboot.Adapter.ChatListAdapter;
import com.example.daboot.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChatList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ChatListData> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
//아마 지워져야 할 클래스 파일
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_message);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();//상단바 제거

        recyclerView = findViewById(R.id.chat_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("ChatList");
    }




    /*ArrayList<ChatListData> chatListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_message);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();//상단바 제거

        this.InitializeChatListData();

        ListView listView = (ListView)findViewById(R.id.chat_list);
        final ChatListAdapter chatListAdapter = new ChatListAdapter(this,chatListData);

        listView.setAdapter(chatListAdapter);
    }

    public void InitializeChatListData()
    {
        chatListData = new ArrayList<ChatListData>();

        chatListData.add(new ChatListData(R.drawable.baseline_account_circle_black_24dp, "홍길동","동해 번쩍, 서해 번쩍", "12:30"));
        chatListData.add(new ChatListData(R.drawable.baseline_account_circle_black_24dp, "이순신","필사즉생, 필생즉사", "15:18"));
        chatListData.add(new ChatListData(R.drawable.baseline_account_circle_black_24dp, "유관순","대한 독립 만세!","21:38"));
    }*/


}