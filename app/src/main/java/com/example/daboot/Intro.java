package com.example.daboot;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class Intro extends AppCompatActivity {

    public int counter;
    TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();//상단바 제거
        new CountDownTimer(3000, 1000){
            public void onTick(long millisUntilFinished){

                tv = (TextView)findViewById(R.id.tv1);

                tv.setText(String.valueOf(counter+1));
                counter++;
            }
            public  void onFinish(){
                Intent intent =new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        }.start();
    }
}
