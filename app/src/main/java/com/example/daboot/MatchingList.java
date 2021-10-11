package com.example.daboot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.daboot.Login.JoinInfo;
import com.example.daboot.fragments.Matching;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MatchingList extends AppCompatActivity {

    private Button btn_back, btn_reload;
    String name, sex, area, field;

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
        //리얼타일데이터베이스 Board 테이블 연결
        databaseReference = database.getReference("Board");
        //파이어스토어 연동
        db = FirebaseFirestore.getInstance();
        // 파이어스토어 UserInfo 테이블 연결
        docRef = db.collection("UserInfo").document(user.getUid());

        btn_back = findViewById(R.id.btn_matching_list_back);
        btn_reload = findViewById(R.id.reload);

        //뒤로가기
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 값 넘어오는거 체크용
        btn_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MatchingList.this, "이름:" + name + " 성별:" + sex + " 지역:" + area + " 분야:" + field ,Toast.LENGTH_SHORT).show();
            }
        });

        //
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                // 데이를 가져오는 작업이 작 동작했을 때
                if (task.isSuccessful()) {
                    //문서의 데이터를 담을 DocumentSnapshot 에 작업의 결과를 담는다.
                    DocumentSnapshot document = task.getResult();


                } else {
                    // 데이터를 가져오는 작업이 에러났을 때
                }
            }
        });








    }
}
