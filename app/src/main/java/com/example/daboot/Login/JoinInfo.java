package com.example.daboot.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
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

import java.util.regex.Pattern;

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
    private Switch btn_sex;
    private TextView tv_field;
    private EditText edt_Name, edt_Email;
    private LinearLayout field_form;

    private String name, sex = "남성", email, area="none", qual, field="none", contents = "자기소개를 적지 않았습니다.";
    private boolean form = true;

    private void Call_Toast(String message){ Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();}

    public boolean isName(String input) {
        return Pattern.matches("[가-힣]*$", input);
    }

    private boolean isEmail(String input) {
        return Pattern.matches("^[a-z0-9A-Z._-]*@[a-z0-9A-Z]*.[a-zA-Z.]*$", input);
    }

    private boolean isInfoS(String area, String field, boolean form){
        boolean ok = true;
        String msg ="";
        if(area.equals("none")){
            msg+="지역 ";
            ok = false;
        }
        if(form==false){
            if(field.equals("none")){
            msg+="분야 ";
            ok = false;
            }
        }
        if(!ok)
            Call_Toast(msg+"을(를) 선택해주세요.");
        return ok;
    }


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
        btn_sex = findViewById(R.id.btn_join_info_sex);

        field_form = findViewById(R.id.btns_join_info_feild_form);

        btn_sex.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    btn_sex.setText("여성");
                    sex = "여성";
                }
                else{
                    btn_sex.setText("남성");
                    sex = "남성";
                }
            }
        });

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
                    Call_Toast(area_keyword[index]+"를 선택하셨습니다.");
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
                    Call_Toast(field_keyword[index]+"를 선택하셨습니다.");
                }
            });
        }

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo : "form" [복지사입니다]버튼의 여부에 따라 지역, 분야 등 값들 기본값으로 넣을지 회원 정보 구조를 개편할것인지 결정 할 것
                //todo : 게시글 작성 댓글 작성 유효성 검사 및 edt의 maxlength 정하기
                name = edt_Name.getText().toString();
                email = edt_Email.getText().toString();
                contents = "자기소개를 적지 않았습니다.";
                qual = form ?  "nomal" : "volunteer";
                // [복지사입니다] 버튼으로 바뀌는 boolean타입의 form변수 값에 따라 nomal(일반 사용자) | volunteer(복지사)가 DB에 들어가게 됨

                if(isName(name)&&isEmail(email)&&isInfoS(area,field,form)) {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    MemberInfo memberInfo = new MemberInfo(name,sex,email,area,field,qual,contents);
                    db.collection("UserInfo").document(user.getUid()).set(memberInfo)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Call_Toast("정보를 등록하였습니다.");
                                    Intent intent = new Intent(JoinInfo.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Call_Toast("실패.");
                                }
                            });
                }else {Call_Toast("입력 값을 확인해주세요");}
            }
        });
    }
}
