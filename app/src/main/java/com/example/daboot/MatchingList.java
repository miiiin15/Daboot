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

        TextView tx1 = (TextView)findViewById(R.id.tv_01);
        TextView tx2 = (TextView)findViewById(R.id.tv_02);
        TextView tx3 = (TextView)findViewById(R.id.tv_03);
        TextView tx4 = (TextView)findViewById(R.id.tv_04);

        Intent intent = getIntent();

        String area = intent.getExtras().getString("area");
        tx1.setText(area);

        String career = intent.getExtras().getString("career");
        tx2.setText(career);

        String sex = intent.getExtras().getString("sex");
        tx3.setText(sex);

        String first = intent.getExtras().getString("first");
        tx4.setText(first);





    }
}