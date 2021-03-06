package com.example.daboot.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.daboot.MainActivity;
import com.example.daboot.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
 

    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증
    private DatabaseReference mDatabaseRef; // 리얼타임 DB
    private EditText edt_Id, edt_Pwd;
    private Button btn_login, btn_find, btn_join;
    private String strID=".",strPWD=".";

    private void Call_Toast(String message){ Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();}


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();//상단바 제거

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance("https://daboot-4979e-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("UserInfo");

        edt_Id = findViewById(R.id.edt_input_ID);
        edt_Pwd = findViewById(R.id.edt_input_Password);

        btn_login = findViewById(R.id.btn_login);
        btn_join = findViewById(R.id.btn_join);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    strID = edt_Id.getText().toString();
                    strPWD = edt_Pwd.getText().toString();
                    mFirebaseAuth.signInWithEmailAndPassword(strID,strPWD).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Call_Toast("아이디 비밀번호를 확인해주세요.");
                            }
                        }
                    });
                }catch (Exception e){
                    Call_Toast("입력값을 확인해 주세요.");
                }


            }
        });

        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,Join.class);
                startActivity(intent);
            }
        });


    }
}
