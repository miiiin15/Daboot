package com.example.daboot.Board;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.daboot.Login.JoinInfo;
import com.example.daboot.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Contents extends AppCompatActivity {

    private String uid;

    private Button btn_back;
    private TextView tv_title, tv_contetnts, tv_time;
    private EditText edt_coments;
    private LinearLayout coments_bar;

    private FirebaseFirestore db;
    private DocumentReference docRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_contents);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();//상단바 제거

        uid = getIntent().getStringExtra("uid");
        db = FirebaseFirestore.getInstance(); //파이어스토어 연동
        docRef = db.collection("Board").document(uid); // 파이어스토어 테이블 연결

        btn_back = findViewById(R.id.btn_board_contents_back);

        tv_title = findViewById(R.id.tv_board_contents_title);
        tv_time = findViewById(R.id.tv_board_contents_writeTime);
        tv_contetnts = findViewById(R.id.tv_board_contents);

        edt_coments = findViewById(R.id.edt_board_contents_coment);

        coments_bar = findViewById(R.id.board_contents_coments_BAR);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String time = (String) document.get("time");
                        tv_title.setText(document.get("title")+"");
                        tv_time.setText(time.substring(11,16));
                        tv_contetnts.setText(document.get("contents")+"");
                    } else {
                        Toast.makeText(getApplicationContext(), "정보입력 화면으로 이동합니다.",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "실패",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}