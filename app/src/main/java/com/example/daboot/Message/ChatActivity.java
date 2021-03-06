package com.example.daboot.Message;

import android.content.Intent;
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

import com.example.daboot.Adapter.BoardAdapter;
import com.example.daboot.Adapter.ChatAdapter;
import com.example.daboot.Board.BoardItem;
import com.example.daboot.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private FirebaseFirestore fsdb;
    private FirebaseUser user;
    private DocumentReference docRef;
    private DatabaseReference dbRef;
    private RecyclerView mRecyclerView;
    public RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<ChatData> chatList;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    protected String usremail;
    protected String nick;

    private EditText edt_chat;
    private Button btn_back;
    private Button btn_send;

    protected String writerEmail;
    protected String userEmail;
    protected String roomIdToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();//????????? ??????

        database = FirebaseDatabase.getInstance("https://daboot-4979e-default-rtdb.asia-southeast1.firebasedatabase.app"); // ?????????????????? ????????? ????????????
        //dbRef = database.getReference("Message");
        dbRef = database.getReference("ChatList");
        user = FirebaseAuth.getInstance().getCurrentUser(); // ?????? ????????? ??? ??????
        fsdb = FirebaseFirestore.getInstance(); //?????????????????? ??????
        docRef = fsdb.collection("UserInfo").document(user.getUid()); // ?????????????????? UserInfo ????????? ??????
        usremail = user.getEmail();
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                nick = (String) document.get("name");
            }
        });

        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        chatList = new ArrayList<>();
        mAdapter = new ChatAdapter(chatList, ChatActivity.this, nick);
        mRecyclerView.setAdapter(mAdapter);

        btn_back = findViewById(R.id.btn_back);
        btn_send = findViewById(R.id.btn_send);
        edt_chat = findViewById(R.id.edt_chat);

        Intent itt = getIntent();
        roomIdToken = itt.getStringExtra("roomId");
        Log.e("Message: ",""+roomIdToken);

        btn_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_send.setOnClickListener(view -> {
            String msg = edt_chat.getText().toString();
            String time = simpleDateFormat.format(new Date());
            //mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);


            if (msg != null) {
                ChatData chat = new ChatData(usremail, msg, nick, time);
                chat.setUsremail(usremail);
                chat.setNick(nick);
                chat.setMsg(msg);
                chat.setTime(time);
                dbRef.push().setValue(chat);
            }
            edt_chat.setText("");
        });


        //DB ??? ????????????
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
