package com.example.daboot.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

    private Button  btn_welfare_form,btn_identity, btn_submit;
    private EditText edt_Area,edt_Name, edt_Email, edt_Qualification, edt_Field;
    private LinearLayout welfare_form;

    private boolean form = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_join_info);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();//상단바 제거

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        edt_Name = findViewById(R.id.edt_join_info_Name);
        edt_Email = findViewById(R.id.edt_join_info_Email);
        edt_Area = findViewById(R.id.edt_join_info_Area);
        edt_Field = findViewById(R.id.edt_join_info_Field);
        edt_Qualification = findViewById(R.id.edt_join_info_Qualification);

        btn_welfare_form = findViewById(R.id.btn_join_info_welfare_mode);
        btn_identity = findViewById(R.id.btn_join_info_Identity);
        btn_submit = findViewById(R.id.btn_join_info_Submit);

        welfare_form = findViewById(R.id.join_info_welfare_form);

        btn_welfare_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(form){
                    welfare_form.setVisibility(View.VISIBLE);
                    btn_welfare_form.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.volunteer));
                    btn_identity.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.volunteer));
                    btn_submit.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.volunteer));
                    form = false;
                }
                else{
                    welfare_form.setVisibility(View.GONE);
                    btn_welfare_form.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.enable));
                    btn_identity.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.enable));
                    btn_submit.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.enable));
                    form = true;
                }
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edt_Name.getText().toString();
                String email = edt_Email.getText().toString();
                String area = edt_Area.getText().toString();
                String field = edt_Field.getText().toString();
                String qual = edt_Qualification.getText().toString();

                if(name.length()>0 && email.length()>0 && area.length()>0 && field.length()>0 && qual.length()>0){
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    MemberInfo memberInfo = new MemberInfo(name,email,area,field,qual);
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
            }
        });
    }
}
