package com.example.daboot.Login;

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

import com.example.daboot.MainActivity;
import com.example.daboot.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class JoinInfo extends AppCompatActivity {

    private Integer [] area_btn_id ={R.id.btn_join_info_Area_Seoul,R.id.btn_join_info_Area_Gyeonggi, R.id.btn_join_info_Area_Chungnam, R.id.btn_join_info_Area_Chungbuk,
            R.id.btn_join_info_Area_Gangwon, R.id.btn_join_info_Area_Gyeongnam, R.id.btn_join_info_Area_Gyeongbuk,
            R.id.btn_join_info_Area_Jeonnam, R.id.btn_join_info_Area_Jeonbuk, R.id.btn_join_info_Area_Jeju};
    private String [] area_keyword = {"서울", "경기", "충남", "충북", "강원", "경남", "경북", "전남", "전북", "제주"};
    private Button [] area_btn = new Button[10];

    private Integer [] field_btn_id ={R.id.btn_join_info_field_disable, R.id.btn_join_info_field_old, R.id.btn_join_info_field_child, R.id.btn_join_info_field_family, R.id.btn_join_info_field_medical};
    private String [] field_keyword = {"장애", "노인", "아동", "가족", "의료"};
    private Button [] field_btn = new Button[5];

    private Button  btn_welfare_form,btn_identity, btn_submit;
    private TextView tv_field;
    private EditText edt_Name, edt_Email;
    private LinearLayout field_form;

    private String name, email, area, qual, field="none", contents = "Test Contents";
    private boolean form = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_join_info);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();//상단바 제거

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        tv_field = findViewById(R.id.tv_join_info_field);

        edt_Name = findViewById(R.id.edt_join_info_Name);
        edt_Email = findViewById(R.id.edt_join_info_Email);

        btn_welfare_form = findViewById(R.id.btn_join_info_welfare_mode);
        btn_identity = findViewById(R.id.btn_join_info_Identity);
        btn_submit = findViewById(R.id.btn_join_info_Submit);

        field_form = findViewById(R.id.btns_join_info_feild_form);

        btn_welfare_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(form){
                    tv_field.setVisibility(View.VISIBLE);
                    field_form.setVisibility(View.VISIBLE);
                    btn_welfare_form.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.volunteer));
                    btn_identity.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.volunteer));
                    btn_submit.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.volunteer));
                    for(int i =0; i<10; i++)
                        area_btn[i].setBackgroundColor(getApplicationContext().getResources().getColor(R.color.volunteer));
                    form = false;
                }
                else{
                    tv_field.setVisibility(View.GONE);
                    field_form.setVisibility(View.GONE);
                    btn_welfare_form.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.enable));
                    btn_identity.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.enable));
                    btn_submit.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.enable));
                    for(int i =0; i<10; i++)
                        area_btn[i].setBackgroundColor(getApplicationContext().getResources().getColor(R.color.enable));
                    field="none";
                    form = true;
                }
            }
        });

        for(int i=0; i<10; i++){ //지역 선택 버튼들 리스너 반복 선언
            area_btn[i] = findViewById((area_btn_id[i]));
            final int index = i;

            area_btn[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    area = area_keyword[index];
                    Toast.makeText(getApplicationContext(),area_keyword[index]+"를 선택하셨습니다.",Toast.LENGTH_SHORT).show();
                }
            });
        }

        for(int i=0; i<5; i++){ //분야 선택 버튼들 리스너 반복 선언
            field_btn[i] = findViewById((field_btn_id[i]));
            final int index = i;

            field_btn[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    field = field_keyword[index];
                    Toast.makeText(getApplicationContext(),field_keyword[index]+"를 선택하셨습니다.",Toast.LENGTH_SHORT).show();
                }
            });
        }

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo : "form" [복지사입니다]버튼의 여부에 따라 지역, 분야 등 값들 기본값으로 넣을지 회원 정보 구조를 개편할것인지 결정 할 것
                name = edt_Name.getText().toString();
                email = edt_Email.getText().toString();
                contents = "Test Contents";
                qual = form ?  "nomal" : "volunteer";  // [복지사입니다] 버튼으로 바뀌는 boolean타입의 form변수 값에 따라 nomal(일반 사용자) | volunteer(복지사)가 DB에 들어가게 됨

                if(name.length()>0 && email.length()>0 && area.length()>0 && field.length()>0 && qual.length()>0 && !(form==false&&field=="none")){
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    MemberInfo memberInfo = new MemberInfo(name,email,area,field,qual,contents);
                    db.collection("UserInfo").document(user.getUid()).set(memberInfo)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(JoinInfo.this,"정보를 등록하였습니다..",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(JoinInfo.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(JoinInfo.this,"실패.",Toast.LENGTH_SHORT).show();
                                }
                            });

                }
                else
                    Toast.makeText(JoinInfo.this,"입력 값을 확인해주세요.",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
