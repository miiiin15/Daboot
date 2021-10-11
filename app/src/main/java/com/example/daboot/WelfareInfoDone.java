package com.example.daboot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.daboot.Login.JoinInfo;
import com.example.daboot.Login.MemberInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class WelfareInfoDone extends AppCompatActivity {

    Button btn_done;
    EditText edt_introduction;

    // 파이어베이스 연동
    private FirebaseDatabase database;
    private FirebaseFirestore db;
    private FirebaseUser user;
    private DocumentReference docRef;
    private DatabaseReference databaseReference;

    private String name;
    private String email;
    private String area;
    private String field;
    private String quall;

    @Override // @Nullable
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welfare_info_done);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide(); // 상단바 제거
        edt_introduction = findViewById(R.id.edit_introduction);

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

        // 복지사 자기소개 화면에서 자기소개 내용을 긁어온다.
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        edt_introduction.setText(document.get("contents").toString());
                        name = document.get("name").toString();
                        email = document.get("email").toString();
                        area = document.get("area").toString();
                        quall = document.get("qual").toString();
                        field = document.get("field").toString();
                    } else {
                        /* 정보가 없음 */
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "실패",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_done = findViewById(R.id.info_done);
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contents = edt_introduction.getText().toString();

                if(name.length()>0 && email.length()>0 && area.length()>0 && field.length()>0){
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    MemberInfo memberInfo = new MemberInfo(name,email,area,field,quall,contents);
                    db.collection("UserInfo").document(user.getUid()).set(memberInfo)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(WelfareInfoDone.this,"자기소개 등록성공!",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(WelfareInfoDone.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(WelfareInfoDone.this,"실패.",Toast.LENGTH_SHORT).show();
                                }
                            });

                }

            }
        });


    }
}
