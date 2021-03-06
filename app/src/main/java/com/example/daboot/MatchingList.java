package com.example.daboot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.daboot.Adapter.MatchingAdapter;
import com.example.daboot.Login.MemberInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MatchingList extends AppCompatActivity {
    // test
    private Button btn_back, btn_reload;
    String name, sex, area, field;
    private ArrayList<MemberInfo> arrayList;
    private MatchingAdapter matchingAdapter;

    // 파이어베이스 연동
    private FirebaseDatabase database;
    private FirebaseFirestore db;
    private FirebaseUser user;
    private DocumentReference docRef;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_list);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();//상단바 제거

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true); // 리사이클러뷰 성능[+]
        MatchingAdapter adapter = new MatchingAdapter();

        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                swipeRefreshLayout.setRefreshing(false);
            }
        });


            // 이름, 성별, 지역, 분야 값 전달받기
        Intent intent = getIntent();
        name = intent.getExtras().getString("name");
        sex = intent.getExtras().getString("sex");
        area = intent.getExtras().getString("area");
        field = intent.getExtras().getString("field");

        // 현재 로그인 한 유저
        user = FirebaseAuth.getInstance().getCurrentUser();
        // 리얼타임데이터베이스 연동
        database = FirebaseDatabase.getInstance("https://daboot-4979e-default-rtdb.asia-southeast1.firebasedatabase.app");
        // 리얼타일데이터베이스 Board 테이블 연결
        databaseReference = database.getReference("Board");
        // 파이어스토어 연동
        db = FirebaseFirestore.getInstance();
        // 파이어스토어 UserInfo 테이블 연결
        docRef = db.collection("UserInfo").document(user.getUid());

        // 뒤로가기
        btn_back = findViewById(R.id.btn_matching_list_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 값 넘어오는거 체크
        btn_reload = findViewById(R.id.reload);
        btn_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Toast.makeText(MatchingList.this, "이름:" + name + " 성별:" + sex + " 지역:" + area + " 분야:" + field ,Toast.LENGTH_SHORT).show();
            }
        });

        // 복지사 불러오기
        db.collection("UserInfo")
                .whereEqualTo("qual", "volunteer")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                    adapter.addItem(new MemberInfo(
                                            (String)document.get("name"),
                                            (String)document.get("sex"),
                                            (String)document.get("area"),
                                            (String)document.get("field"))
                                    );
                                    recyclerView.setAdapter(adapter);
                            }
                        }
                    }
                });


    }


}
