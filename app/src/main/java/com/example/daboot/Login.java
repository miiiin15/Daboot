package com.example.daboot;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {


    Button btnlogin, btnfind, btnjoin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();//상단바 제거

        final LinearLayout find_id = (LinearLayout) View.inflate(Login.this, R.layout.find_id, null);
        final LinearLayout find_pwd = (LinearLayout) View.inflate(Login.this, R.layout.find_password, null);
        btnlogin = (Button)findViewById(R.id.btn_login);
        btnfind = (Button)findViewById(R.id.btn_find);
        btnjoin = (Button)findViewById(R.id.btn_join);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        btnjoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), Join.class);
                startActivity(intent);
            }
        });

        btnfind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(Login.this);

                builder.setTitle("아이디/비밀번호 찾기");

                builder.setItems(R.array.find, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int pos)
                    {
                        String[] items = getResources().getStringArray(R.array.find);
                        switch (pos){
                            case 0:
                                new AlertDialog.Builder(Login.this)
                                        .setView(find_id)
                                        .setPositiveButton("탐색", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                EditText in_name = (EditText) find_id.findViewById(R.id.input_name);
                                                EditText in_num = (EditText) find_id.findViewById(R.id.input_num);

                                                String str_name = in_name.getText().toString();
                                                String str_num = in_num.getText().toString();

                                                //Find(str_name,str_num,0); 찾기

                                            }
                                        })
                                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                dialog.dismiss();
                                            }
                                        }).show();
                                return;
                            case 1:
                                new AlertDialog.Builder(Login.this)
                                        .setView(find_pwd)
                                        .setPositiveButton("탐색", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                EditText in_id = (EditText) find_pwd.findViewById(R.id.input_id);
                                                EditText in_num = (EditText) find_pwd.findViewById(R.id.input_num);

                                                String str_id = in_id.getText().toString();
                                                String str_num = in_num.getText().toString();

                                                //Find(str_id,str_num,1); 찾기

                                            }
                                        })
                                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                dialog.dismiss();
                                            }
                                        }).show();
                                return;
                        }
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }
}
