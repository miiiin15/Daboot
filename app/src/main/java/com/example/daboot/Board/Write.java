package com.example.daboot.Board;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.daboot.MainActivity;
import com.example.daboot.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Write extends AppCompatActivity {

    private Integer [] btn_id = {R.id.btn_board_write_Free,R.id.btn_board_write_Question,R.id.btn_board_write_Market}; //카테고리 버튼 "정보" 제외 4개의 아이디 값을 넣은 배열
    private String [] keyword = {"자유","질문","장터"}; // 4가지의 카테고리(전체, 자유, 질문, 장터)
    private Button [] category_btn = new Button[3]; //카테고리(전체,자유,장터 등..)를 담을 버튼 배열

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private String category = "자유";
    private String title,contents, writer,time,anonymous="true";
    private boolean want_anonymous = true;

    private Button btn_cancel, btn_finish;
    private EditText edt_title, edt_contents;
    private Switch btn_anonymous;

    private String getCurrentDate(){ // 현재 시간을 String형으로 반환해주는 함수
        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String getTime = simpleDate.format(mDate);

        return getTime;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write_form);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();//상단바 제거

        edt_title = findViewById(R.id.edt_board_write_title);
        edt_contents = findViewById(R.id.edt_board_write_contents);

        btn_anonymous = findViewById(R.id.btn_board_write_anonymous);
        btn_finish = findViewById(R.id.btn_baord_wrtie_finish);
        btn_cancel = findViewById(R.id.btn_board_write_cancel);

        /* 반복문을 이용한 4개의 카테고리 버튼에 대한 onClickListener 선언 부분이며 우리가 아는 setOnClickListener를 반복문으로 짧게 써놓은것   */
        for(int i=0; i<3; i++){
            category_btn[i] = findViewById((btn_id[i]));
            final int index = i;

            category_btn[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    category_btn[index].setSelected(true);
                    category = keyword[index];
                    Toast.makeText(getApplicationContext(),keyword[index],Toast.LENGTH_SHORT).show();
                }
            });
        }

        btn_anonymous.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                want_anonymous = !want_anonymous;
                anonymous = want_anonymous ? "true" : "false";
            }
        });

        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writer = user.getEmail();
                title = edt_title.getText().toString();
                contents = edt_contents.getText().toString();
                time = getCurrentDate();

                if(category.length()>0 && writer.length()>0 && title.length()>0 && contents.length()>0 && time.length()>0 && anonymous.length()>0){
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                   BoardInfo boardInfo = new BoardInfo(category,anonymous,title, writer,contents,time);
                    db.collection("Board").add(boardInfo)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(getApplicationContext(),"정보를 등록하였습니다..",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"실패.",Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                else
                    Toast.makeText(getApplicationContext(),"입력 값을 확인해주세요.",Toast.LENGTH_SHORT).show();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
