package com.example.daboot.fragments;

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

import androidx.fragment.app.Fragment;

import com.example.daboot.MatchingList;
import com.example.daboot.R;

public class Matching extends Fragment {

    String[] items = {"전체", "서울", "경기", "충남", "충북", "경남", "경북", "전남", "전북", "제주"};
    TextView textView;
    ImageButton imageButton;
    /*
        분야, 지역, 경력, 성별, 우선도, 복지사 이름
    */
    String field = "";
    String area = "";
    String career = "";
    String sex = "";
    String first = "";
    String name = "";
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
        final CheckBox cb5 = view.findViewById(R.id.chk_box5);
        final CheckBox cb6 = view.findViewById(R.id.chk_box6);
        final CheckBox cb7 = view.findViewById(R.id.chk_box7);
        final CheckBox cb8 = view.findViewById(R.id.chk_box8);
        final CheckBox cb9 = view.findViewById(R.id.chk_box9);
        final CheckBox cb10 = view.findViewById(R.id.chk_box10);
        final CheckBox cb11 = view.findViewById(R.id.chk_box11);
        final CheckBox cb12 = view.findViewById(R.id.chk_box12);
        final CheckBox cb13 = view.findViewById(R.id.chk_box13);
        final CheckBox cb14 = view.findViewById(R.id.chk_box14);

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
                Toast.makeText(getContext(), items[position] + "지역을 선택하셨습니다.", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                textView.setText("");
            }
        });

        /*
            버튼 체크 및 체크된 버튼의 값을 담아 MatchingList.class 값 넘겨준다.
        */
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cb1.isChecked()) field = "노인";
                if(cb2.isChecked()) field = "아동";
                if(cb3.isChecked()) field = "가족";
                if(cb4.isChecked()) field = "의료";

                if(cb5.isChecked()) career = "경력없음";
                if(cb6.isChecked()) career = "1년이상";
                if(cb7.isChecked()) career = "5년이상";
                if(cb8.isChecked()) career = "10년이상";

                if(cb9.isChecked()) sex = "무관";
                if(cb10.isChecked()) sex = "남자";
                if(cb11.isChecked()) sex = "여자";

                if(cb12.isChecked()) first = "무관";
                if(cb13.isChecked()) first = "거리";
                if(cb14.isChecked()) first = "경력";

                Intent intent = new Intent(getContext(), MatchingList.class);
                intent.putExtra("field", field);
                intent.putExtra("area", area);
                intent.putExtra("career", career);
                intent.putExtra("sex", sex);
                intent.putExtra("first", first);
                /*
                 복지사 이름 공백체크
                */
                name = edt_name.getText().toString();
                if(edt_name.length() == 0) {
                    Toast.makeText(getContext(), "값을 모두 입력해주세요", Toast.LENGTH_LONG).show();
                } else {
                    intent.putExtra("name", edt_name.getText().toString());
                }

                startActivity(intent);
            }
        });

        return view;

    }
}