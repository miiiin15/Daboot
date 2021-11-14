package com.example.daboot.Login;

import android.content.Intent;
import android.graphics.Color;
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

import com.example.daboot.MainActivity;
import com.example.daboot.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Join extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증
    private DatabaseReference mDatabaseRef; // 리얼타임 DB
    private EditText edt_Id, edt_Pwd, edt_Pwdck;
    private Button btn_submit;

    private boolean isId(String input) {
        return Pattern.matches("^[a-z0-9A-Z._-]*@[a-z0-9A-Z]*.[a-zA-Z.]*$", input);
    }

    private boolean isPwd(String input){
        return Pattern.matches("^[a-zA-Z0-9]{4,12}$", input);
    }

    private void Call_Toast(String message){ Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();}

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
        edt_Pwdck = findViewById(R.id.edt_join_pwdCheck);

        btn_submit = findViewById(R.id.btn_join_Submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Join.this, JoinInfo.class);

                String strID = edt_Id.getText().toString();
                String strPWD = edt_Pwd.getText().toString();
                String strPWDck = edt_Pwdck.getText().toString();

                if(isId(strID)&&isPwd(strPWD)&&strPWD.equals(strPWDck)){
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

                                Call_Toast("아이디 비밀번호 등록 완료.");

                                mFirebaseAuth.signInWithEmailAndPassword(strID,strPWD).addOnCompleteListener(Join.this, new OnCompleteListener<AuthResult>() {//로그인 시키고 추가 정보 입력 페이지로
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){ //등록이 성공하면
                                            startActivity(intent); //추가 정보 입력 화면(JoinInfo.class)로 이동한다.
                                            finish();
                                        }
                                    }
                                });
                            }else{ Call_Toast("등록 실패.");}
                        }
                    });
                } else{ Call_Toast("입력 값을 확인해주세요."); }

            }
        });

    }
}
