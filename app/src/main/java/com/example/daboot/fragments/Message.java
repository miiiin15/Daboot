package com.example.daboot.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daboot.Adapter.ChatListAdapter;
import com.example.daboot.MainActivity;
import com.example.daboot.Message.ChatActivity;
import com.example.daboot.Message.ChatListData;
import com.example.daboot.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Message extends Fragment {

    private RecyclerView recyclerView;
    //private RecyclerView.Adapter adapter;
    private ChatListAdapter chatListAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ChatListData> arrayList;
    private Button btn_back;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseFirestore fsdb;
    private String userEmail;
    private DocumentReference docRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_message, container, false);

        recyclerView = view.findViewById(R.id.chat_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();

        btn_back = view.findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail(); //?????? ???????????? ??????
        database = FirebaseDatabase.getInstance("https://daboot-4979e-default-rtdb.asia-southeast1.firebasedatabase.app"); //realtime database ??????
        databaseReference = database.getReference("ChatList"); //rtdb chatlist ????????? ??????
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                //????????????????????? ????????????????????? ???????????? ???
                arrayList.clear();// ?????? ????????????????????? ???????????? ????????? ?????????
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){//???????????? ???????????? list??? ???????????? ??????
                    ChatListData chatlistdata = snapshot.getValue(ChatListData.class); //ChatListData ????????? ???????????? ?????????.
                    chatlistdata.setNick("park");
                    chatlistdata.setChatContent("Bye");
                    arrayList.add(chatlistdata); //?????? ???????????? ?????? ???????????? ?????? recyclerView??? ?????? ??????
                }
                chatListAdapter.notifyDataSetChanged();  //????????? ?????? ??? ????????????
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError databaseError) {
                //DB??? ???????????? ??? ?????? ?????? ???,
                Log.e("Message",String.valueOf(databaseError.toException()));//????????? ??????
            }
        });

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ChatActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        chatListAdapter = new ChatListAdapter(arrayList, this);
        recyclerView.setAdapter(chatListAdapter);

        return view;
    }
}