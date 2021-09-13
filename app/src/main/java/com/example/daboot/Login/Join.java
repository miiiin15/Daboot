package com.example.daboot.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.daboot.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Join extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증
    private DatabaseReference mDatabaseRef; // 리얼타임 DB
    private EditText edt_Id, edt_Pwd, edt_Area, edt_Qualification, edt_Field;
    private Button btn_submit, btn_welfare_mode;
    private TextView tv_Area, tv_Field, tv_Qualification;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_join);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();//상단바 제거

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance("https://daboot-4979e-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("UserInfo");

        edt_Id = findViewById(R.id.edt_join_ID);
        edt_Pwd = findViewById(R.id.edt_join_pwd);
        btn_submit = findViewById(R.id.btn_join_Submit);
        edt_Area = findViewById(R.id.edt_join_Area);
        edt_Field = findViewById(R.id.edt_join_Field);
        edt_Qualification = findViewById(R.id.edt_join_Qualification);
        btn_welfare_mode = findViewById(R.id.btn_welfare_mode);
        tv_Area = findViewById(R.id.tv_join_Area);
        tv_Field = findViewById(R.id.tv_join_Field);
        tv_Qualification = findViewById(R.id.tv_join_Qualification);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Join.this, Login.class);

                String strID = edt_Id.getText().toString();
                String strPWD = edt_Pwd.getText().toString();

                mFirebaseAuth.createUserWithEmailAndPassword(strID,strPWD).addOnCompleteListener(Join.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //회원가입 성공시
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

                            UserAccount account = new UserAccount();
                            account.setIdToken(firebaseUser.getUid());
                            account.setUserID(firebaseUser.getEmail());
                            account.setUserPWD(strPWD);

                            mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);

                           Toast.makeText(getApplicationContext(),"회원가입 신청 완료",Toast.LENGTH_SHORT).show();
                           startActivity(intent);
                           finish();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"실패",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        // 복지사 회원가입 활성화 보이지않던 edt_text 보이기
        btn_welfare_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_Area.setVisibility(View.VISIBLE);
                edt_Field.setVisibility(View.VISIBLE);
                edt_Qualification.setVisibility(View.VISIBLE);
                tv_Area.setVisibility(View.VISIBLE);
                tv_Field.setVisibility(View.VISIBLE);
                tv_Qualification.setVisibility(View.VISIBLE);
            }
        });


    }
}
