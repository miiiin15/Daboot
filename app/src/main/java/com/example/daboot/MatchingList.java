package com.example.daboot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class MatchingList extends AppCompatActivity {

    private Button btn_back;
    private LinearLayout tmp; //todo: 나중에 이름 바꿀것

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_list);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();//상단바 제거

        btn_back = findViewById(R.id.btn_matching_list_back);

        tmp = findViewById(R.id.temp_id); //todo: 나중에 이름 바꿀것

        //뒤로가기
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MatchingInfo.class);
                startActivity(intent);
            }
        });

        /*
        TextView tv_name = (TextView)findViewById(R.id.tv_name);
        TextView tv_sex = (TextView)findViewById(R.id.tv_sex);
        TextView tv_area = (TextView)findViewById(R.id.tv_area);
        TextView tv_field = (TextView)findViewById(R.id.tv_field);
        TextView tv_career = (TextView)findViewById(R.id.tv_career);
        TextView tv_first = (TextView)findViewById(R.id.tv_first);
        Intent intent = getIntent();

        String name = intent.getExtras().getString("name");
        tv_name.setText(name);

        String sex = intent.getExtras().getString("sex");
        tv_sex.setText(sex);

        String area = intent.getExtras().getString("area");
        tv_area.setText(area);

        String field = intent.getExtras().getString("field");
        tv_field.setText(field);
        */



    }
}
