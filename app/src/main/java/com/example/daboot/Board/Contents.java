package com.example.daboot.Board;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.daboot.R;

public class Contents extends AppCompatActivity {

    private Button btn_back;
    private EditText edt_coments;
    private LinearLayout coments_bar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_contents);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();//상단바 제거

        btn_back = findViewById(R.id.btn_board_contents_back);

        edt_coments = findViewById(R.id.edt_board_contents_coment);

        coments_bar = findViewById(R.id.board_contents_coments_BAR);


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
