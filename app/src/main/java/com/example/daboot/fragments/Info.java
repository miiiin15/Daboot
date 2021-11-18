package com.example.daboot.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daboot.Login.JoinInfo;
import com.example.daboot.Login.Login;
import com.example.daboot.R;
import com.example.daboot.WelfareInfoDone;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class Info extends Fragment {

    private FirebaseAuth mFirebaseAuth;
    private ImageButton btn_logout;
    private Switch swt1, swt2, swt3;

    // 파이어베이스
    private FirebaseDatabase database;
    private FirebaseFirestore db;
    private FirebaseUser user;
    private DocumentReference docRef;
    private DatabaseReference databaseReference;

    private boolean a = true;
    private boolean b = false;
    private boolean c = false;

    // id값 가져오기
    private TextView tv_name, tv_id, tv_email, tv_qualification, tv_field;
    String id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_info, container, false);

        swt1 = view.findViewById(R.id.switch1);
        swt2 = view.findViewById(R.id.switch2);
        swt3 = view.findViewById(R.id.switch3);

        swt1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    a = true;
                    Toast.makeText(getActivity(), "정보공개 활성화", Toast.LENGTH_SHORT).show();
                } else {
                    a = false;
                    Toast.makeText(getActivity(), "정보공개 비활성화", Toast.LENGTH_SHORT).show();
                }
            }
        });

        swt2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    b = true;
                    Toast.makeText(getActivity(), "쪽지허용 활성화", Toast.LENGTH_SHORT).show();
                } else {
                    b = false;
                    Toast.makeText(getActivity(), "쪽지허용 비활성화", Toast.LENGTH_SHORT).show();
                }
            }
        });

        swt3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    c = true;
                    Toast.makeText(getActivity(), "매칭허용 활성화", Toast.LENGTH_SHORT).show();
                } else {
                    c = false;
                    Toast.makeText(getActivity(), "매칭허용 비활성화", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tv_name = view.findViewById(R.id.tv_info_name);
        tv_id = view.findViewById(R.id.tv_info_id);
        tv_email = view.findViewById(R.id.tv_info_email);
        tv_qualification = view.findViewById(R.id.tv_info_qualification);
        tv_field = view.findViewById(R.id.tv_info_field);

        mFirebaseAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser(); // 현재 로그인 한 유저
        db = FirebaseFirestore.getInstance(); // 파이어스토어 연동
        docRef = db.collection("UserInfo").document(user.getUid()); // 파이어스토어 UserInfo 테이블 연결
        database = FirebaseDatabase.getInstance("https://daboot-4979e-default-rtdb.asia-southeast1.firebasedatabase.app"); // 리얼타임데이터베이스 연동
        databaseReference = database.getReference("UserInfo"); //리얼타일데이터베이스 UserInfo 테이블 연결

        btn_logout = view.findViewById(R.id.btn_info_logout);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAuth.signOut();
                Intent intent = new Intent(getContext(), Login.class);
                startActivity(intent);
                getActivity().finish();
            }
        });


        id = user.getEmail();
        // 화면이 로딩시 내정보 띄우기
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    tv_name.setText((String)document.get("name"));
                    tv_id.setText(id);
                    tv_email.setText((String)document.get("email"));
                    tv_qualification.setText((String)document.get("qual"));
                    tv_field.setText((String)document.get("field"));
                } else {
                    Toast.makeText(getContext(), "실패",Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }
}