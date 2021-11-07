package com.example.daboot.fragments;

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
    private FirebaseUser user;
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

        /*btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

        user = FirebaseAuth.getInstance().getCurrentUser(); //현재 로그인한 유저
        database = FirebaseDatabase.getInstance("https://daboot-4979e-default-rtdb.asia-southeast1.firebasedatabase.app"); //realtime database 연동
        databaseReference = database.getReference("ChatList"); //rtdb chatlist 테이블 연결
        fsdb = FirebaseFirestore.getInstance(); //firestore 연동
        docRef = fsdb.collection("UserInfo").document(user.getUid()); //fs UserInfo 테이블 연결
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                //파이어베이스의 데이터베이스를 받아오는 곳
                arrayList.clear();// 기존 데이터베이스가 존재하지 않도록 초기화
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){//반복문을 이용하여 list의 데이터를 추출
                    ChatListData chatlistdata = snapshot.getValue(ChatListData.class); //ChatListData 객체에 데이터를 담는다.
                    arrayList.add(chatlistdata); //담은 데이터를 배열 리스트에 넣고 recyclerView로 보낼 준비
                }
                chatListAdapter.notifyDataSetChanged();  //리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError databaseError) {
                //DB를 가져오던 중 에러 발생 시,
                Log.e("Message",String.valueOf(databaseError.toException())); //에러문 출력
            }
        });

        chatListAdapter = new ChatListAdapter(arrayList, this);
        recyclerView.setAdapter(chatListAdapter);

        return view;
    }
}