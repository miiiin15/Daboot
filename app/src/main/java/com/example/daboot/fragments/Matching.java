package com.example.daboot.fragments;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.daboot.Login.JoinInfo;
import com.example.daboot.Login.Login;
import com.example.daboot.MatchingList;
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

public class Matching extends Fragment {

    String[] items = {"전체", "서울", "경기", "충남", "충북", "경남", "경북", "전남", "전북", "제주"};
    TextView textView;
    ImageButton imageButton;
    ImageButton imageInfoWrite;

    // 분야, 지역, 경력, 성별, 우선도, 복지사 이름
    String field = "";
    String area = "";
    String career = "";
    String sex = "";
    String first = "";
    String name = "";

    // 파이어베이스 연동
    private FirebaseDatabase database;
    private FirebaseFirestore db;
    private FirebaseUser user;
    private DocumentReference docRef;
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_matching, container, false);

        textView = view.findViewById(R.id.textView);
        Spinner spinner = view.findViewById(R.id.spinner);
        imageButton = view.findViewById(R.id.img_search);
        EditText edt_name = view.findViewById(R.id.edt_name);

        final CheckBox cb1 = view.findViewById(R.id.chk_box1);
        final CheckBox cb2 = view.findViewById(R.id.chk_box2);
        final CheckBox cb3 = view.findViewById(R.id.chk_box3);
        final CheckBox cb4 = view.findViewById(R.id.chk_box4);
        final CheckBox cb9 = view.findViewById(R.id.chk_box9);
        final CheckBox cb10 = view.findViewById(R.id.chk_box10);
        final CheckBox cb11 = view.findViewById(R.id.chk_box11);


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

        //화면이 로딩되자마자 파이어스토어에 현재 로그인한 유저 정보를 조회해서 있으면 toast로 띄워주고 없으면 정보 입력화면으로 이동
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    String eq = new String("nomal");

                    if (eq.equals(document.get("qual"))) {
                        Toast.makeText(getContext(),document.get("qual")+ " 회원 ",Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(getContext(), WelfareInfoDone.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                    if (document.exists()) {
                        Toast.makeText(getContext(),document.get("qual")+ " 회원 ",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "정보입력 화면으로 이동합니다.",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), JoinInfo.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                } else {
                    Toast.makeText(getContext(), "실패",Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 스피너
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_spinner_item, items);
        // 드롭다운 클릭시 선택창
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 스피너에 어댑터 설정
        spinner.setAdapter(adapter);
        // 스니퍼 이벤트 처리
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                area = items[position];
                // Toast.makeText(getContext(), items[position] + "지역을 선택하셨습니다.", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                textView.setText("");
            }
        });

        //버튼 체크 및 체크된 버튼의 값을 담아 MatchingList.class 값 넘겨준다.
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cb1.isChecked()) field = "노인";
                if(cb2.isChecked()) field = "아동";
                if(cb3.isChecked()) field = "가족";
                if(cb4.isChecked()) field = "의료";

                if(cb9.isChecked()) sex = "무관";
                if(cb10.isChecked()) sex = "남자";
                if(cb11.isChecked()) sex = "여자";

                Intent intent = new Intent(getContext(), MatchingList.class);
                intent.putExtra("field", field);
                intent.putExtra("area", area);
                intent.putExtra("sex", sex);
                intent.putExtra("first", first);

                if(edt_name.length() > 0) {
                    name = edt_name.getText().toString();
                    intent.putExtra("name", name);
                } else {
                    intent.putExtra("name", name);
                }

                startActivity(intent);
            }
        });

        return view;

    }
}