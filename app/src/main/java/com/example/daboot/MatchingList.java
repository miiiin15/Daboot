package com.example.daboot;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class MatchingList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_list);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();//상단바 제거

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


    }
}