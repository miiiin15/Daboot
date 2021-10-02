package com.example.daboot.Message;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daboot.Adapter.ChatAdapter;
import com.example.daboot.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class ChatActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference dbRef;
    private RecyclerView mRecyclerView;
    public RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<ChatData> chatList;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    private  final String nick = "nick1"; //추후 로그인 계정 연동하여 값 추가

    private EditText edt_chat;
    private Button btn_back;
    private Button btn_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();//상단바 제거

        btn_back = findViewById(R.id.btn_back);
        btn_send = findViewById(R.id.btn_send);
        edt_chat = findViewById(R.id.edt_chat);

        btn_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_send.setOnClickListener(view -> {
            String msg = edt_chat.getText().toString();
            //String time = String.valueOf(ServerValue.TIMESTAMP);
            /*long unixTime = System.currentTimeMillis();
            Date date = new Date(unixTime);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));*/
            String time = simpleDateFormat.format(new Date());

            if (msg != null) {
                ChatData chat = new ChatData();
                chat.setNick(nick);
                chat.setMsg(msg);
                chat.setTime(time);
                dbRef.push().setValue(chat);
            }
            edt_chat.setText("");
        });

        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        chatList = new ArrayList<>();
        mAdapter = new ChatAdapter(chatList, ChatActivity.this, nick);
        mRecyclerView.setAdapter(mAdapter);

        database = FirebaseDatabase.getInstance("https://daboot-4979e-default-rtdb.asia-southeast1.firebasedatabase.app"); // 파이어베이스 기능을 연동해라
        dbRef = database.getReference("Message");

        dbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ChatData chat = dataSnapshot.getValue(ChatData.class);
                ((ChatAdapter) mAdapter).addChat(chat);
                mRecyclerView.scrollToPosition(chatList.size() - 1);
            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {
            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });

    }
}
