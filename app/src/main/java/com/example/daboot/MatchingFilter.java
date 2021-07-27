package com.example.daboot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class MatchingFilter extends AppCompatActivity {
    String[] items = {"전체", "서울", "경기", "충남", "충북", "경남", "경북", "전남", "전북", "제주"};
    TextView textView;
    ImageButton imageButton;
    String area = ""; // 분야
    String career = ""; // 경력
    String sex = ""; // 성별
    String first = ""; // 우선도

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_filter);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();//상단바 제거

        textView = findViewById(R.id.textView);
        Spinner spinner = findViewById(R.id.spinner);
        imageButton = findViewById(R.id.img_search);

        final CheckBox cb1 = (CheckBox)findViewById(R.id.chk_box1);
        final CheckBox cb2 = (CheckBox)findViewById(R.id.chk_box2);
        final CheckBox cb3 = (CheckBox)findViewById(R.id.chk_box3);
        final CheckBox cb4 = (CheckBox)findViewById(R.id.chk_box4);
        final CheckBox cb5 = (CheckBox)findViewById(R.id.chk_box5);
        final CheckBox cb6 = (CheckBox)findViewById(R.id.chk_box6);
        final CheckBox cb7 = (CheckBox)findViewById(R.id.chk_box7);
        final CheckBox cb8 = (CheckBox)findViewById(R.id.chk_box8);
        final CheckBox cb9 = (CheckBox)findViewById(R.id.chk_box9);
        final CheckBox cb10 = (CheckBox)findViewById(R.id.chk_box10);
        final CheckBox cb11 = (CheckBox)findViewById(R.id.chk_box11);
        final CheckBox cb12 = (CheckBox)findViewById(R.id.chk_box12);
        final CheckBox cb13 = (CheckBox)findViewById(R.id.chk_box13);
        final CheckBox cb14 = (CheckBox)findViewById(R.id.chk_box14);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, items);
        // 드롭다운 클릭시 선택창
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 스피너에 어댑터 설정
        spinner.setAdapter(adapter);

        // 스니퍼 이벤트 처리
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // textView.setText(items[position]);
                Toast.makeText(getApplicationContext(), items[position] + "지역을 선택하셨습니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                textView.setText("");
            }
        });

        /*
            버튼 체크 및 체크된 버튼의 값을 담아 MatchingList.class에 값 넘겨준다.
        */
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cb1.isChecked()) area = "노인";
                if(cb2.isChecked()) area = "아동";
                if(cb3.isChecked()) area = "가족";
                if(cb4.isChecked()) area = "의료";

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

                Intent intent = new Intent(getApplicationContext(), MatchingList.class);
                intent.putExtra("area", area);
                intent.putExtra("career", career);
                intent.putExtra("sex", sex);
                intent.putExtra("first", first);
                startActivity(intent);
            }
        });



    }
}